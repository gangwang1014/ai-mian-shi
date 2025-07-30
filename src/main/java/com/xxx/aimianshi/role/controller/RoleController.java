package com.xxx.aimianshi.role.controller;

import com.xxx.aimianshi.role.domain.entity.Role;
import com.xxx.aimianshi.role.service.RoleService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/role")
public class RoleController {

    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @PostMapping
    public void addRole(@RequestBody Role role) {
        roleService.add(role);
    }

    @DeleteMapping("/{id:\\d+}")
    public void deleteRole(@PathVariable Long id) {
        roleService.delete(id);
    }
}
