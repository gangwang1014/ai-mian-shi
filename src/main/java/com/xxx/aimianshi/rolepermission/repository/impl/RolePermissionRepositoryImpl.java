package com.xxx.aimianshi.rolepermission.repository.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xxx.aimianshi.rolepermission.domain.entity.RolePermission;
import com.xxx.aimianshi.rolepermission.mapper.RolePermissionMapper;
import com.xxx.aimianshi.rolepermission.repository.RolePermissionRepository;
import org.springframework.stereotype.Repository;

@Repository
public class RolePermissionRepositoryImpl extends ServiceImpl<RolePermissionMapper, RolePermission>
        implements RolePermissionRepository {

}
