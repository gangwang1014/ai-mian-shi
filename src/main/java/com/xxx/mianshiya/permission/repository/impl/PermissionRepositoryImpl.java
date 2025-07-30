package com.xxx.mianshiya.permission.repository.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xxx.mianshiya.permission.domain.entity.Permission;
import com.xxx.mianshiya.permission.mapper.PermissionMapper;
import com.xxx.mianshiya.permission.repository.PermissionRepository;
import org.springframework.stereotype.Repository;

@Repository
public class PermissionRepositoryImpl extends ServiceImpl<PermissionMapper, Permission>
        implements PermissionRepository {

}
