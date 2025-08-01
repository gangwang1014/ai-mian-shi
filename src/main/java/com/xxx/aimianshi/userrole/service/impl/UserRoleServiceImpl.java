package com.xxx.aimianshi.userrole.service.impl;

import com.xxx.aimianshi.common.exception.BizException;
import com.xxx.aimianshi.common.utils.ThrowUtils;
import com.xxx.aimianshi.role.domain.entity.Role;
import com.xxx.aimianshi.role.enums.RoleEnum;
import com.xxx.aimianshi.role.repository.RoleRepository;
import com.xxx.aimianshi.userrole.domain.entity.UserRole;
import com.xxx.aimianshi.userrole.repository.UserRoleRepository;
import com.xxx.aimianshi.userrole.service.UserRoleService;
import org.springframework.dao.DuplicateKeyException;
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
    public void addUserRole(Long userId, RoleEnum roleEnum) {
        Role role = roleRepository.findByRoleName(roleEnum.getName())
                .orElseThrow(() -> new RuntimeException("Role not found"));
        try {
            userRoleRepository.save(new UserRole(null, userId, role.getId()));
        } catch (DuplicateKeyException e) {
            throw new BizException("user already has role: " + roleEnum.getName());
        }
    }

    /**
     * 清理用户所有角色
     */
    @Override
    public void deleteUserRoles(Long userId) {
        List<UserRole> userRoles = userRoleRepository.getByUserId(userId);
        if (userRoles.isEmpty()) {
            return;
        }
        userRoleRepository.removeByIds(userRoles);
    }

    @Override
    public void deleteUserRole(Long userId, RoleEnum roleEnum) {
        UserRole userRole = userRoleRepository.getOptByUserIdAndRole(userId, roleEnum.getName())
                .orElseThrow(() -> new RuntimeException("user role not found"));
        boolean remove = userRoleRepository.removeById(userRole);
        ThrowUtils.throwIf(!remove, "delete failed, maybe the role does not exist");
    }

}
