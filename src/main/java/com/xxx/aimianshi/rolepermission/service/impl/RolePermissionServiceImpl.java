package com.xxx.aimianshi.rolepermission.service.impl;

import com.xxx.aimianshi.rolepermission.domain.entity.RolePermission;
import com.xxx.aimianshi.rolepermission.repository.RolePermissionRepository;
import com.xxx.aimianshi.rolepermission.service.RolePermissionService;
import org.springframework.stereotype.Service;

@Service
public class RolePermissionServiceImpl implements RolePermissionService {

    private final RolePermissionRepository rolePermissionRepository;

    public RolePermissionServiceImpl(RolePermissionRepository rolePermissionRepository) {
        this.rolePermissionRepository = rolePermissionRepository;
    }

    @Override
    public void add(RolePermission rolePermission) {
        rolePermissionRepository.save(rolePermission);
    }
}
