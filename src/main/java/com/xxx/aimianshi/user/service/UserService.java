package com.xxx.aimianshi.user.service;

import com.xxx.aimianshi.user.domain.req.ChangePasswordReq;
import com.xxx.aimianshi.user.domain.req.UserLoginReq;
import com.xxx.aimianshi.user.domain.req.UserRegisterReq;
import com.xxx.aimianshi.user.domain.req.UserUpdateReq;
import com.xxx.aimianshi.user.domain.resp.UserDetailResp;
import com.xxx.aimianshi.user.domain.resp.UserLoginResp;

import java.util.List;

public interface UserService {

    void register(UserRegisterReq userRegisterReq);

    UserLoginResp login(UserLoginReq userLoginReq);

    UserDetailResp detail(Long id);

    void update(UserUpdateReq userUpdateReq);

    void delete(Long id);

    void changePassword(ChangePasswordReq changePasswordReq);

    void userSignIn();

    List<Integer> userSignInRecord(Integer year);
}
