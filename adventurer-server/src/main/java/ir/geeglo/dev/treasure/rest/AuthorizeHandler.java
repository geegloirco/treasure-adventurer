package ir.geeglo.dev.treasure.rest;

import ir.geeglo.dev.treasure.GeegloSpringServiceProvider;
import ir.geeglo.dev.treasure.business.KavenegarBusiness;
import ir.geeglo.dev.treasure.data.entity.*;
import ir.geeglo.dev.treasure.data.service.UserService;
import ir.geeglo.dev.treasure.model.ResponseModel;
import ir.geeglo.dev.treasure.model.UserInfoModel;
import ir.geeglo.dev.treasure.model.UserLoginInfoModel;
import ir.geeglo.dev.treasure.model.UserModel;
import ir.piana.dev.core.annotation.*;
import ir.piana.dev.core.response.PianaResponse;
import ir.piana.dev.core.role.RoleType;
import ir.piana.dev.core.session.Session;

import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response.Status;
import java.sql.Timestamp;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Mohammad Rahmati, 10/14/2018
 */
@Handler(baseUrl = "authorize", handlerType = HandlerType.METHOD_HANDLER)
public class AuthorizeHandler {
    @MethodHandler(requiredRole = RoleType.GUEST)
    public static PianaResponse init(@SessionParam Session session) {
        Object existance = session.getExistance();
        if (existance != null && existance instanceof UserEntity) {
            UserEntity userEntity = (UserEntity) existance;
            return new PianaResponse(Status.OK,
                    new ResponseModel(0, new UserModel(
                            userEntity.getUsername(),
                            userEntity.getImage(),
                            session.getSessionKey(),
                            null)));
        }else {
            /**
             * fake
             */
//            UserEntity userEntity = GeegloSpringServiceProvider.getUserService()
//                    .selectByMobile("09391366128");
//            createCartEntityIfNotExist(session);
//            session.setExistance(userEntity);
//            session.setRoleType(RoleType.USER);
//            OpenCartEntity cartEntity = setSessionCartAfterLogin(session, userEntity.getId());
//            return new PianaResponse(Status.OK,
//                    new ResponseModel(0, new UserModel(
//                            userEntity.getUsername(),
//                            userEntity.getImage(),
//                            session.getSessionKey(),
//                            cartEntity.getItems())));

            return new PianaResponse(Status.OK,
                    new ResponseModel(1, new UserModel(null, null,
                            session.getSessionKey(), null)));
        }
    }

    @MethodHandler(requiredRole = RoleType.GUEST, httpMethod = "POST")
    @Path("login")
    public static PianaResponse login(@BodyObjectParam UserLoginInfoModel model,
                                      @SessionParam Session session) throws Exception {
        UserEntity userEntity = GeegloSpringServiceProvider.getUserService()
                .selectByMobile(model.getUsername());
        if(userEntity != null && userEntity.getPassword() != null) {
            String hashPass = GeegloSpringServiceProvider.getSecurityBusiness()
                    .encryptDesEcbPkcs5paddingAsBase64(model.getPassword());
            if (userEntity.getPassword()
                    .equalsIgnoreCase(hashPass)) {
                session.setExistance(userEntity);
                session.setRoleType(RoleType.USER);

                return new PianaResponse(Status.OK,
                        new ResponseModel(0, new UserModel(
                                userEntity.getUsername(),
                                userEntity.getImage(),
                                session.getSessionKey(),
                                null)));
            }
        }

        return new PianaResponse(Status.OK,
                new ResponseModel(1, "not correct"));
    }

    @MethodHandler(requiredRole = RoleType.GUEST, httpMethod = "POST")
    @Path("register")
    public static PianaResponse register(@SessionParam Session session,
                                    @BodyObjectParam UserLoginInfoModel model) {
        KavenegarBusiness kavenegarBusiness = GeegloSpringServiceProvider.getKavenegarBusiness();
        String otp = kavenegarBusiness.sendOtp(model.getUsername());
//        String otp = "123";
        session.setExistance(model);
        session.setRoleType(RoleType.USER);
        session.setString("otp", otp);

        return new PianaResponse(Status.OK,
                new ResponseModel(0, session.getSessionKey()));
    }

    @MethodHandler(requiredRole = RoleType.GUEST, httpMethod = "POST")
    @Path("verify")
    public static PianaResponse verify(@SessionParam Session session,
                                    @QueryParam("otp") String otp) throws Exception {
        String otp1 = session.getString("otp");
        if(otp.equalsIgnoreCase(otp1)) {
            Object existance = session.getExistance();
            if(existance instanceof UserLoginInfoModel) {
                UserService userService = GeegloSpringServiceProvider.getUserService();
                UserLoginInfoModel model = (UserLoginInfoModel)existance;
                UserEntity userEntity = userService.selectByMobile(model.getUsername());
                if(userEntity == null) {
                    userEntity = new UserEntity();
                    userEntity.setMobile(model.getUsername());
                    userEntity.setUsername(model.getUsername());
                    userEntity.setImage("customer.png");
                    String hashPass = GeegloSpringServiceProvider.getSecurityBusiness()
                            .encryptDesEcbPkcs5paddingAsBase64(model.getPassword());
                    userEntity.setPassword(hashPass);

                    userEntity.setEnterDate(new Timestamp(System.currentTimeMillis()));
                    userService.save(userEntity, new UserInfoEntity());
                } else {
                    String hashPass = GeegloSpringServiceProvider.getSecurityBusiness()
                            .encryptDesEcbPkcs5paddingAsBase64(model.getPassword());
                    userEntity.setPassword(hashPass);
                    userService.update(userEntity);
                }
                session.setExistance(userEntity);
                session.setRoleType(RoleType.USER);
                return new PianaResponse(Status.OK,
                        new ResponseModel(0, new UserModel(
                                userEntity.getUsername(),
                                userEntity.getImage(),
                                session.getSessionKey(),
                                null)));
            }
        }
        return new PianaResponse(Status.OK,
                new ResponseModel(1, "not correct"));
    }

    @MethodHandler(requiredRole = RoleType.USER, httpMethod = "POST")
    @Path("logout")
    public static PianaResponse logout(@SessionParam Session session) {
        session.setExistance(null);
        session.setRoleType(RoleType.GUEST);
        return new PianaResponse(Status.OK,
                new ResponseModel(0, new UserModel(null, null,
                        session.getSessionKey(), null)));
    }

    @MethodHandler(requiredRole = RoleType.USER)
    @Path("user-info")
    public static PianaResponse getUserInfo(@SessionParam Session session) {
        UserEntity userEntity = (UserEntity) session.getExistance();
        UserInfoModel userInfoModel = new UserInfoModel(userEntity);
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("user-info", userInfoModel);
        map.put("addresses", userEntity.getAddressEntities());
        return new PianaResponse(Status.OK,
                new ResponseModel(0, map));
    }

    @MethodHandler(requiredRole = RoleType.USER, httpMethod = "POST")
    @Path("update-user-info")
    public static PianaResponse updateUserInfo(
            @SessionParam Session session,
            @BodyObjectParam UserInfoModel model) {
        UserEntity userEntity = (UserEntity) session.getExistance();
        UserInfoEntity userInfo = userEntity.getUserInfos().get(0);
        userInfo.setFirstName(model.getFirstName());
        userInfo.setLastName(model.getLastName());
        userInfo.setNationalCode(model.getNationalCode());
        GeegloSpringServiceProvider.getUserService().update(userEntity);
        return new PianaResponse(Status.OK,
                new ResponseModel(0, model));
    }

    @MethodHandler(requiredRole = RoleType.USER, httpMethod = "POST")
    @Path("register-address")
    public static PianaResponse registerAddress(
            @SessionParam Session session, @BodyObjectParam Map map) {
        AddressEntity addressEntity = new AddressEntity();
        addressEntity.setTitle(String.valueOf(map.get("title")));
        addressEntity.setDetail((String)map.get("detail"));
        addressEntity.setLatitude((Double) map.get("latitude"));
        addressEntity.setLongitude((Double) map.get("longitude"));
        addressEntity.setPhoneNumber((String) map.get("phoneNumber"));
        addressEntity.setPostCode((String) map.get("postCode"));
        UserEntity existance = (UserEntity) session.getExistance();
        existance.addAddressEntity(addressEntity);
//        addressEntity.setUserEntity(existance);
//        GeegloSpringServiceProvider.getUserService().update(existance);
        GeegloSpringServiceProvider.getAddressService().save(addressEntity);
        GeegloSpringServiceProvider.getUserService().update(existance);

        return new PianaResponse(Status.OK,
                new ResponseModel(0, addressEntity));
    }

    @MethodHandler(requiredRole = RoleType.USER, httpMethod = "POST")
    @Path("update-address")
    public static PianaResponse updateAddress(@SessionParam Session session,
                                                @BodyObjectParam Map map) {
        UserEntity existance = (UserEntity) session.getExistance();
        for(AddressEntity addressEntity : existance.getAddressEntities()) {
            if(addressEntity.getId() == Integer.parseInt(String.valueOf(map.get("id")))) {
                addressEntity.setTitle(String.valueOf(map.get("title")));
                addressEntity.setDetail((String)map.get("detail"));
                addressEntity.setLatitude((Double) map.get("latitude"));
                addressEntity.setLongitude((Double) map.get("longitude"));
                addressEntity.setPhoneNumber((String) map.get("phoneNumber"));
                addressEntity.setPostCode((String) map.get("postCode"));
                GeegloSpringServiceProvider.getAddressService().update(addressEntity);
                return new PianaResponse(Status.OK,
                        new ResponseModel(0, addressEntity));
            }
        }
        return new PianaResponse(Status.OK,
                new ResponseModel(1, null));
    }

    @MethodHandler(requiredRole = RoleType.USER, httpMethod = "POST")
    @Path("remove-address")
    public static PianaResponse removeAddress(@SessionParam Session session,
                                              @BodyObjectParam Map map) {
        UserEntity existance = (UserEntity) session.getExistance();
        for(AddressEntity addressEntity : existance.getAddressEntities()) {
            if(addressEntity.getId() == Integer.parseInt(String.valueOf(map.get("id")))) {
                existance.removeAddressEntity(addressEntity);
                GeegloSpringServiceProvider.getUserService().update(existance);
//                existance.getAddressEntities().remove(addressEntity);
                return new PianaResponse(Status.OK,
                        new ResponseModel(0, null));
            }
        }
        return new PianaResponse(Status.OK,
                new ResponseModel(1, null));
    }
}
