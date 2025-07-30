package com.xxx.mianshiya.permission.controller;

import com.xxx.mianshiya.permission.domain.entity.Permission;
import com.xxx.mianshiya.permission.service.PermissionService;
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
