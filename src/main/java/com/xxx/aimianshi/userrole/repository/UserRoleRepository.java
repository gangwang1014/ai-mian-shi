package com.xxx.aimianshi.userrole.repository;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xxx.aimianshi.userrole.domain.entity.UserRole;

import java.util.List;
import java.util.Optional;

public interface UserRoleRepository extends IService<UserRole> {

    List<UserRole> getByUserId(Long userId);

    Optional<UserRole> getOptByUserIdAndRole(Long userId, String roleEnum);
}
