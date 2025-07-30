package com.xxx.mianshiya.permission.service.impl;

import com.xxx.mianshiya.permission.domain.entity.Permission;
import com.xxx.mianshiya.permission.repository.PermissionRepository;
import com.xxx.mianshiya.permission.service.PermissionService;
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
