package com.xxx.mianshiya.userrole.service.impl;

import com.xxx.mianshiya.role.domain.entity.Role;
import com.xxx.mianshiya.role.enums.RoleEnum;
import com.xxx.mianshiya.role.repository.RoleRepository;
import com.xxx.mianshiya.userrole.domain.entity.UserRole;
import com.xxx.mianshiya.userrole.repository.UserRoleRepository;
import com.xxx.mianshiya.userrole.service.UserRoleService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserRoleServiceImpl implements UserRoleService {
    private final UserRoleRepository userRoleRepository;
    private final RoleRepository roleRepository;

    public UserRoleServiceImpl(UserRoleRepository userRoleRepository, RoleRepository roleRepository) {
        this.userRoleRepository = userRoleRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public boolean addUserRole(Long userId, RoleEnum roleEnum) {
        Role role = roleRepository.findByRoleName(roleEnum.getName())
                .orElseThrow(() -> new RuntimeException("Role not found"));
        return userRoleRepository.save(new UserRole(null, userId, role.getId()));
    }

    /**
     * 清理用户所有角色
     */
    @Override
    public boolean deleteUserRoles(Long userId) {
        List<UserRole> userRoles = userRoleRepository.getByUserId(userId);
        return userRoleRepository.removeByIds(userRoles);
    }

    @Override
    public void add(UserRole userRole) {
        userRoleRepository.save(userRole);
    }

}
