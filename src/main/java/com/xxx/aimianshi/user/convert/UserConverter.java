package com.xxx.aimianshi.user.convert;

import cn.hutool.core.util.RandomUtil;
import com.xxx.aimianshi.user.constant.UserConstant;
import com.xxx.aimianshi.user.domain.entity.User;
import com.xxx.aimianshi.user.domain.req.UserRegisterReq;
import com.xxx.aimianshi.user.domain.req.UserUpdateReq;
import com.xxx.aimianshi.user.domain.resp.UserDetailResp;
import com.xxx.aimianshi.user.domain.resp.UserLoginResp;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface UserConverter {

    @Mappings({
            @Mapping(target = "nickname", expression = "java(generateNickname())"),
            @Mapping(target = "workExperience", ignore = true),
            @Mapping(target = "updateTime", ignore = true),
            @Mapping(target = "unionId", ignore = true),
            @Mapping(target = "profile", ignore = true),
            @Mapping(target = "phone", ignore = true),
            @Mapping(target = "mpOpenId", ignore = true),
            @Mapping(target = "isDelete", ignore = true),
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "grade", ignore = true),
            @Mapping(target = "expertiseDirection", ignore = true),
            @Mapping(target = "email", ignore = true),
            @Mapping(target = "createTime", ignore = true),
            @Mapping(target = "avatar", ignore = true)
    })
    User toEntity(UserRegisterReq userRegisterReq);

    // 自动生成昵称
    default String generateNickname() {
        return UserConstant.TEMP_NICKNAME_PRE + RandomUtil.randomString(UserConstant.TEMP_NICKNAME_LENGTH);
    }

    @Mapping(target = "token", source = "token")
    UserLoginResp toUserLoginResp(User user, String token);

    UserDetailResp toUserDetailResp(User user);

    @Mappings({
            @Mapping(target = "updateTime", ignore = true),
            @Mapping(target = "unionId", ignore = true),
            @Mapping(target = "phone", ignore = true),
            @Mapping(target = "mpOpenId", ignore = true),
            @Mapping(target = "isDelete", ignore = true),
            @Mapping(target = "email", ignore = true),
            @Mapping(target = "createTime", ignore = true),
            @Mapping(target = "avatar", ignore = true),
            @Mapping(target = "account", ignore = true),
            @Mapping(target = "password", ignore = true)
    })
    User toEntity(UserUpdateReq userUpdateReq);
}
