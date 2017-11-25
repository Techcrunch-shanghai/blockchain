import cn.bubi.baas.account.Account;
import cn.bubi.baas.account.AccountManageService;
import cn.bubi.baas.account.UserInfo;
import cn.bubi.baas.account.tenant.Tenant;
import cn.bubi.baas.account.tenant.TenantInfo;
import cn.bubi.baas.account.tenant.TenantManageService;
import cn.bubi.baas.base.BlockchainAddress;
import cn.bubi.baas.base.BlockchainCertificate;
import cn.bubi.baas.base.security.SecureIdentity;
import cn.bubi.baas.sdk.*;
import cn.bubi.baas.account.Application;
import cn.bubi.baas.base.PublicKey;

public class Main {
    public String newadd;
    private static Tenant addTenant() {
        //区块链BAAS服务的IP地址和端口；
        String host = "39.106.70.242";
        int port = 8080;
        String address="a001d3d4a9f436505c02f00945dced61cb9a5fd01df144";
        String priv_key="c00155912ecc141ba36968b7f5e1452f9f09659e16a19638127b45c0956bc7389d0aee";
        String pub_key="b00168ca4b4015c3da114849f6ea172d5291b1c3e332b4dc61784197d3b9e23ba7a76f";

        BlockchainServiceFactory blockchainServiceFactory = new BlockchainServiceFactory(host, port);
        SecureIdentity channel = new SecureIdentity(address, priv_key);
        System.out.println("old address is" + address);
        BlockchainSession session = blockchainServiceFactory.createSession(channel);

        TransactionTemplate tx = session.beginTransaction();
        TenantManageService tenantManageService = tx.forService(TenantManageService.class);
        TenantInfo tenantInfo = new TenantInfo();
        tenantInfo.setName("hacker");
        tenantInfo.setDescription("TC hackathon");
        BlockchainCertificate tenantCert = session.getSecureKeyGenerator().generateBubiCertificate();
        ActionResultHolder<Tenant> insertResultHolder =  tx.prepare(tenantManageService.register(tenantCert, tenantInfo), Tenant.class);

        // 完成一次 transaction
        PreparedTransaction ptx = tx.complete();
        String txHash = ptx.getHash();
        Tenant tenant = insertResultHolder.getResult();
        System.out.println("new address is" + tenant.getAddress());
        ptx.sign(address, priv_key); // WTFFFFFFF
        ptx.commit();
        System.out.println("tenant done");
        session.close();
        return tenant;
    }

    private static void addApp(Tenant tenant) {
        //区块链BAAS服务的IP地址和端口；
        String host = "39.106.70.242";
        int port = 8080;
        String address="a001d3d4a9f436505c02f00945dced61cb9a5fd01df144";
        String priv_key="c00155912ecc141ba36968b7f5e1452f9f09659e16a19638127b45c0956bc7389d0aee";
        String pub_key="b00168ca4b4015c3da114849f6ea172d5291b1c3e332b4dc61784197d3b9e23ba7a76f";

//        BlockchainServiceFactory blockchainServiceFactory = new BlockchainServiceFactory(host, port);
//        SecureIdentity channel = new SecureIdentity(tenant.getAddress(), );
//        System.out.println("old address is" + address);
//        BlockchainSession session = blockchainServiceFactory.createSession(channel);
//        TransactionTemplate tx = session.beginTransaction();
//        BlockchainCertificate appCert = session.getSecureKeyGenerator().generateBubiCertificate();
//        Application app = new Application(tenant.getAddress(), tenant.getAuthPubKey(), tenant.getDataPubKey());
//        app.setTenant("hacker");
//
////        TenantManageService tenantManageService = tx.forService(TenantManageService.class);
////        TenantInfo tenantInfo = new TenantInfo();
////        tenantInfo.setName("hack");
////        tenantInfo.setDescription("TC hackathon");
//        BlockchainCertificate tenantCert = session.getSecureKeyGenerator().generateBubiCertificate();


//        // 完成一次 transaction
//        PreparedTransaction ptx = tx.complete();
//        String txHash = ptx.getHash();
////        Tenant dataAddress = insertResultHolder.getResult();
//        ptx.sign(address, priv_key);
//        ptx.commit();
//
//        System.out.println("tenant done");
    }


    private static void addUser() {
        //区块链BAAS服务的IP地址和端口；
        String host = "39.106.70.242";
        int port = 8080;
        String address="a001d3d4a9f436505c02f00945dced61cb9a5fd01df144";
        String priv_key="c00155912ecc141ba36968b7f5e1452f9f09659e16a19638127b45c0956bc7389d0aee";
        String pub_key="b00168ca4b4015c3da114849f6ea172d5291b1c3e332b4dc61784197d3b9e23ba7a76f";

        BlockchainServiceFactory blockchainServiceFactory = new BlockchainServiceFactory(host, port);
        SecureIdentity channel = new SecureIdentity(address, priv_key);
        BlockchainSession session = blockchainServiceFactory.createSession(channel);
        BlockchainAddress dataBlockchainAddress = session.getSecureKeyGenerator().generateBubiAddress();

        TransactionTemplate tx = session.beginTransaction();
        AccountManageService accountManageService = tx.forService(AccountManageService.class);
        UserInfo userInfo = new UserInfo();
        userInfo.setDescription("Demo 用户");
        BlockchainCertificate userCert = session.getSecureKeyGenerator().generateBubiCertificate();
        tx.prepare(accountManageService.register(userCert, userInfo), cn.bubi.baas.account.User.class);
        PreparedTransaction ptx = tx.complete();
        try {
            String txHash = ptx.getHash();
//            ptx.sign(a);
        } catch (Exception e) {

        }
    }
    private static void addApp1() {
//        BlockchainAddress dataBlockchainAddress = session.getSecureKeyGenerator().generateBubiAddress();

//        String address2 = dataBlockchainAddress.getAddress();
//        PublicKey authPubKey = dataBlockchainAddress.getAuthPubKey();
//
//        String name = authPubKey.getAlgorithm().name();
//        String encodedValue = authPubKey.getEncodedValue();
//        System.out.println("address2:" + address2);
//        System.out.println("authPubKey name : " + name);
//        System.out.println("encodedValue :  " + encodedValue);
    }
    public static void main(String[] args) {
        addTenant();
//        addUser();

    }
}
