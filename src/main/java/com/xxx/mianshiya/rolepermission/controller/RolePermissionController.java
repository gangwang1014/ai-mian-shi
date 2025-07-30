package com.xxx.mianshiya.rolepermission.controller;

import com.xxx.mianshiya.rolepermission.domain.entity.RolePermission;
import com.xxx.mianshiya.rolepermission.service.RolePermissionService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/role-permission")
public class RolePermissionController {
    private final RolePermissionService rolePermissionService;

    public RolePermissionController(RolePermissionService rolePermissionService) {
        this.rolePermissionService = rolePermissionService;
    }

    @PostMapping
    public void add(@RequestBody RolePermission rolePermission) {
        rolePermissionService.add(rolePermission);
    }

}
