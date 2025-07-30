package com.xxx.aimianshi.userrole.controller;

import com.xxx.aimianshi.userrole.domain.entity.UserRole;
import com.xxx.aimianshi.userrole.service.UserRoleService;
import org.springframework.web.bind.annotation.*;

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
    @PutMapping
    public void updateUserRole() {

    }

    /**
     * 管理员添加用户角色
     */
    @PostMapping
    public void addUserRole(@RequestBody UserRole userRole) {
        userRoleService.add(userRole);
    }

}
