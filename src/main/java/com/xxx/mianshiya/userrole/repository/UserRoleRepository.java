package com.xxx.mianshiya.userrole.repository;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xxx.mianshiya.userrole.domain.entity.UserRole;

import java.util.List;

public interface UserRoleRepository extends IService<UserRole> {

    List<UserRole> getByUserId(Long userId);
}
