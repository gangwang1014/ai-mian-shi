package com.xxx.aimianshi.userrole.service;

import com.xxx.aimianshi.role.enums.RoleEnum;

public interface UserRoleService {

    void addUserRole(Long userId, RoleEnum roleEnum);

    void deleteUserRoles(Long userId);

    void deleteUserRole(Long userId, RoleEnum roleEnum);
}
