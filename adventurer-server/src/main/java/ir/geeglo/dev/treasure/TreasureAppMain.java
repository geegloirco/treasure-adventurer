package ir.geeglo.dev.treasure;

import ir.piana.dev.core.PianaAnnotationAppMain;
import ir.piana.dev.core.annotation.*;
import ir.piana.dev.grizzly.http.GrizzlyPianaHttpServer;

/**
 * @author Mohammad Rahmati, 10/13/2018
 */
@PianaServer(
        serverCORS = @PianaServerCORS(allowOrigin = "*"),
        serverSession = @PianaServerSession(sessionExpiredSecond = 9999)
//        sslServer = @SSLServer(httpsHost = "192.168.43.111", keyStoreName = "keystore.jks", keyStorePassword = "password")
)
public class TreasureAppMain {
    public static void main(String[] args) throws Exception {
        PianaAnnotationAppMain.start(new GrizzlyPianaHttpServer(), TreasureAppMain.class);

//        UserEntity userEntity = GeegloSpringServiceProvider.getUserService().selectByMobile("09391366128");
//        AddressEntity addressEntity = new AddressEntity();
//        addressEntity.setTitle("a");
//        addressEntity.setDetail("2");
//        addressEntity.setLatitude(12d);
//        addressEntity.setLongitude(13d);
//        addressEntity.setPhoneNumber("09");
//        addressEntity.setPostCode("028");
//        userEntity.addAddress(addressEntity);
//        GeegloSpringServiceProvider.getUserService().update(userEntity);
    }
}
