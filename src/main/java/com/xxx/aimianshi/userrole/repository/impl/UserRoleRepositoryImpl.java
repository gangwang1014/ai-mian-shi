package com.xxx.aimianshi.userrole.repository.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xxx.aimianshi.userrole.domain.entity.UserRole;
import com.xxx.aimianshi.userrole.mapper.UserRoleMapper;
import com.xxx.aimianshi.userrole.repository.UserRoleRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class UserRoleRepositoryImpl extends ServiceImpl<UserRoleMapper, UserRole> implements UserRoleRepository {

    @Override
    public List<UserRole> getByUserId(Long userId) {
        return lambdaQuery()
                .eq(UserRole::getUserId, userId)
                .list();
    }

    @Override
    public Optional<UserRole> getOptByUserIdAndRole(Long userId, String roleName) {
        return lambdaQuery()
                .eq(UserRole::getUserId, userId)
                .eq(UserRole::getRoleId, roleName)
                .oneOpt();
    }
}
