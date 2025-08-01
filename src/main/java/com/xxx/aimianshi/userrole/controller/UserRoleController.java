package com.xxx.aimianshi.userrole.controller;

import com.xxx.aimianshi.role.enums.RoleEnum;
import com.xxx.aimianshi.userrole.service.UserRoleService;
import jakarta.validation.constraints.NotNull;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user-role")
public class UserRoleController {

    private final UserRoleService userRoleService;

    public UserRoleController(UserRoleService userRoleService) {
        this.userRoleService = userRoleService;
    }

    /**
     * 管理员添加用户角色
     */
    @PostMapping
    @PreAuthorize("hasRole('admin') or hasAnyAuthority('add', 'all')")
    public void addUserRole(@RequestParam @NotNull Long userId, @RequestParam @NotNull RoleEnum roleEnum) {
        userRoleService.addUserRole(userId, roleEnum);
    }

    /**
     * 删除用户所有角色
     * @param userId userId
     */
    @DeleteMapping
    @PreAuthorize("hasRole('admin') or hasAnyAuthority('delete', 'all')")
    public void deleteUserRoles(@RequestParam @NotNull Long userId) {
        userRoleService.deleteUserRoles(userId);
    }

    /**
     * 管理员删除用户某一个角色
     * @param userId userId
     * @param roleEnum roleEnum
     */
    @DeleteMapping
    @PreAuthorize("hasRole('admin') or hasAnyAuthority('delete', 'all')")
    public void deleteUserRole(@RequestParam @NotNull Long userId, @RequestParam @NotNull RoleEnum roleEnum) {
        userRoleService.deleteUserRole(userId, roleEnum);
    }

}
