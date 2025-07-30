package com.xxx.aimianshi.permission.repository.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xxx.aimianshi.permission.domain.entity.Permission;
import com.xxx.aimianshi.permission.mapper.PermissionMapper;
import com.xxx.aimianshi.permission.repository.PermissionRepository;
import org.springframework.stereotype.Repository;

@Repository
public class PermissionRepositoryImpl extends ServiceImpl<PermissionMapper, Permission>
        implements PermissionRepository {

}
