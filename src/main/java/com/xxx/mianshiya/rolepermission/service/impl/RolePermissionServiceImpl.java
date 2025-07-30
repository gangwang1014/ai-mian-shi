package com.xxx.mianshiya.rolepermission.service.impl;

import com.xxx.mianshiya.rolepermission.domain.entity.RolePermission;
import com.xxx.mianshiya.rolepermission.repository.RolePermissionRepository;
import com.xxx.mianshiya.rolepermission.service.RolePermissionService;
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
