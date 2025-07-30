package com.xxx.mianshiya.user.controller;

import com.xxx.mianshiya.user.domain.req.UserLoginReq;
import com.xxx.mianshiya.user.domain.req.UserRegisterReq;
import com.xxx.mianshiya.user.domain.req.UserUpdateReq;
import com.xxx.mianshiya.user.domain.resp.UserDetailResp;
import com.xxx.mianshiya.user.domain.resp.UserLoginResp;
import com.xxx.mianshiya.user.service.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public void register(@RequestBody UserRegisterReq userRegisterReq) {
         userService.register(userRegisterReq);
    }

    @PostMapping("/login")
    public UserLoginResp login(@RequestBody UserLoginReq userLoginReq) {
        return userService.login(userLoginReq);
    }

    @PostMapping("/logout")
    public void logout() {
        // todo logout
    }

    @GetMapping("/{id:\\d+}")
    public UserDetailResp detail(@PathVariable Long id) {
        return userService.detail(id);
    }

    @PostMapping("/update")
    public void update(@RequestBody UserUpdateReq userUpdateReq) {
        userService.update(userUpdateReq);
    }

    @DeleteMapping("/{id:\\d+}")
    public void delete(@PathVariable Long id) {
        userService.delete(id);
    }
}
