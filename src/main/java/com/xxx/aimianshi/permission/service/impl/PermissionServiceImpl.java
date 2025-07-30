package com.xxx.aimianshi.permission.service.impl;

import com.xxx.aimianshi.permission.domain.entity.Permission;
import com.xxx.aimianshi.permission.repository.PermissionRepository;
import com.xxx.aimianshi.permission.service.PermissionService;
import org.springframework.stereotype.Service;

@Service
public class PermissionServiceImpl implements PermissionService {

    private final PermissionRepository permissionRepository;

    public PermissionServiceImpl(PermissionRepository permissionRepository) {
        this.permissionRepository = permissionRepository;
    }
    @Override
    public void add(Permission permission) {
        permissionRepository.save(permission);
    }
}
