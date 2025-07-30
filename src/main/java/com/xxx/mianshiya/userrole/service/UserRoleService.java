package com.xxx.mianshiya.userrole.service;

import com.xxx.mianshiya.role.enums.RoleEnum;

public interface UserRoleService {

    boolean save(Long userId, RoleEnum roleEnum);

    boolean deleteUserRoles(Long userId);
}
