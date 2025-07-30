package com.xxx.mianshiya.userrole.service;

import com.xxx.mianshiya.role.enums.RoleEnum;
import com.xxx.mianshiya.userrole.domain.entity.UserRole;

public interface UserRoleService {

    boolean addUserRole(Long userId, RoleEnum roleEnum);

    boolean deleteUserRoles(Long userId);

    void add(UserRole userRole);
}
