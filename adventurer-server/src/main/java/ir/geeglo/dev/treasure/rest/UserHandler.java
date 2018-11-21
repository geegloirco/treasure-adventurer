package ir.geeglo.dev.treasure.rest;

import ir.geeglo.dev.treasure.GeegloSpringServiceProvider;
import ir.geeglo.dev.treasure.data.entity.UserEntity;
import ir.geeglo.dev.treasure.data.entity.UserInfoEntity;
import ir.geeglo.dev.treasure.model.ResponseModel;
import ir.geeglo.dev.treasure.model.UserInfoModel;
import ir.piana.dev.core.annotation.*;
import ir.piana.dev.core.response.PianaResponse;
import ir.piana.dev.core.role.RoleType;
import ir.piana.dev.core.session.Session;

import javax.ws.rs.core.Response.Status;

/**
 * @author Mohammad Rahmati, 10/14/2018
 */
@Handler(baseUrl = "user", handlerType = HandlerType.METHOD_HANDLER)
public class UserHandler {
    @MethodHandler(requiredRole = RoleType.USER)
    public static PianaResponse getUserInfo(@SessionParam Session session) {
        UserEntity userEntity = (UserEntity) session.getExistance();
        UserInfoModel userInfoModel = new UserInfoModel(userEntity);
        return new PianaResponse(Status.OK,
                new ResponseModel(0, userInfoModel));
    }

    @MethodHandler(requiredRole = RoleType.USER, httpMethod = "PUT")
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
}
