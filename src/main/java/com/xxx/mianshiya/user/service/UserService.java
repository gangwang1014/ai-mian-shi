package com.xxx.mianshiya.user.service;

import com.xxx.mianshiya.user.domain.req.UserLoginReq;
import com.xxx.mianshiya.user.domain.req.UserRegisterReq;
import com.xxx.mianshiya.user.domain.req.UserUpdateReq;
import com.xxx.mianshiya.user.domain.resp.UserDetailResp;
import com.xxx.mianshiya.user.domain.resp.UserLoginResp;

public interface UserService {

    void register(UserRegisterReq userRegisterReq);

    UserLoginResp login(UserLoginReq userLoginReq);

    UserDetailResp detail(Long id);

    void update(UserUpdateReq userUpdateReq);

    void delete(Long id);
}
