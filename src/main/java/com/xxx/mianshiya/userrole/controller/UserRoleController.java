package com.xxx.mianshiya.userrole.controller;

import com.xxx.mianshiya.userrole.service.UserRoleService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user-role")
public class UserRoleController {

    private final UserRoleService userRoleService;

    public UserRoleController(UserRoleService userRoleService) {
        this.userRoleService = userRoleService;
    }

    /**
     * todo 待完善
     * 管理员更改用户角色
     */
    @PostMapping
    public void updateUserRole() {

    }

}
