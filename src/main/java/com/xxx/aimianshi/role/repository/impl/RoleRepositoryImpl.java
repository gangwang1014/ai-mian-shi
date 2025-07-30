package com.xxx.aimianshi.role.repository.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xxx.aimianshi.role.domain.entity.Role;
import com.xxx.aimianshi.role.mapper.RoleMapper;
import com.xxx.aimianshi.role.repository.RoleRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class RoleRepositoryImpl extends ServiceImpl<RoleMapper, Role> implements RoleRepository {

    @Override
    public Optional<Role> findByRoleName(String roleName) {
        return lambdaQuery()
                .eq(Role::getName, roleName)
                .oneOpt();
    }
}
