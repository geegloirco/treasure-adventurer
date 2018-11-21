package ir.geeglo.dev.treasure.rest;

import ir.geeglo.dev.treasure.GeegloSpringServiceProvider;
import ir.geeglo.dev.treasure.data.entity.AddressEntity;
import ir.geeglo.dev.treasure.data.entity.UserEntity;
import ir.geeglo.dev.treasure.model.ResponseModel;
import ir.geeglo.dev.treasure.model.UserInfoModel;
import ir.piana.dev.core.annotation.*;
import ir.piana.dev.core.response.PianaResponse;
import ir.piana.dev.core.role.RoleType;
import ir.piana.dev.core.session.Session;

import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response.Status;
import java.util.Map;

/**
 * @author Mohammad Rahmati, 10/14/2018
 */
@Handler(baseUrl = "address", handlerType = HandlerType.METHOD_HANDLER)
public class AddressHandler {
    @MethodHandler(requiredRole = RoleType.USER, sync = false)
    public static PianaResponse getUserInfo(@SessionParam Session session) {
        UserEntity userEntity = (UserEntity) session.getExistance();
        UserInfoModel userInfoModel = new UserInfoModel(userEntity);
        return new PianaResponse(Status.OK,
                new ResponseModel(0, userEntity.getAddressEntities()));
    }

    @MethodHandler(requiredRole = RoleType.USER, httpMethod = "POST")
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

    @MethodHandler(requiredRole = RoleType.USER, httpMethod = "PUT")
    public static PianaResponse updateAddress(
            @SessionParam Session session, @BodyObjectParam Map map) {
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

    @MethodHandler(requiredRole = RoleType.USER, httpMethod = "DELETE")
    public static PianaResponse removeAddress(
            @SessionParam Session session, @QueryParam("id") int id) {
        UserEntity existance = (UserEntity) session.getExistance();
        AddressEntity address = null;
        for(AddressEntity addressEntity : existance.getAddressEntities()) {
            if(addressEntity.getId() == id) {
                address = addressEntity;
                break;
            }
        }
        if(address != null) {
            existance.removeAddressEntity(address);
            GeegloSpringServiceProvider.getAddressService().delete(address);
            GeegloSpringServiceProvider.getUserService().update(existance);
            return new PianaResponse(Status.OK,
                    new ResponseModel(0, null));
        }

        return new PianaResponse(Status.OK,
                new ResponseModel(1, null));
    }
}
