package com.xxx.mianshiya.rolepermission.repository.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xxx.mianshiya.rolepermission.domain.entity.RolePermission;
import com.xxx.mianshiya.rolepermission.mapper.RolePermissionMapper;
import com.xxx.mianshiya.rolepermission.repository.RolePermissionRepository;
import org.springframework.stereotype.Repository;

@Repository
public class RolePermissionRepositoryImpl extends ServiceImpl<RolePermissionMapper, RolePermission>
        implements RolePermissionRepository {

}
