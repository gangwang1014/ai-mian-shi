package com.xxx.aimianshi.permission.controller;

import com.xxx.aimianshi.permission.domain.entity.Permission;
import com.xxx.aimianshi.permission.service.PermissionService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/permission")
public class PermissionController {

    private final PermissionService permissionService;

    public PermissionController(PermissionService permissionService) {
        this.permissionService = permissionService;
    }

    @PostMapping
    public void addPermission(@RequestBody Permission permission) {
        permissionService.add(permission);
    }


}
