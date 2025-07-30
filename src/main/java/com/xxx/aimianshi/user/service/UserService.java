package com.xxx.aimianshi.user.service;

import com.xxx.aimianshi.user.domain.req.UserLoginReq;
import com.xxx.aimianshi.user.domain.req.UserRegisterReq;
import com.xxx.aimianshi.user.domain.req.UserUpdateReq;
import com.xxx.aimianshi.user.domain.resp.UserDetailResp;
import com.xxx.aimianshi.user.domain.resp.UserLoginResp;

public interface UserService {

    void register(UserRegisterReq userRegisterReq);

    UserLoginResp login(UserLoginReq userLoginReq);

    UserDetailResp detail(Long id);

    void update(UserUpdateReq userUpdateReq);

    void delete(Long id);
}
