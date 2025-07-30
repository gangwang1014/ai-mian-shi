package com.xxx.mianshiya.role.service.impl;

import com.xxx.mianshiya.role.domain.entity.Role;
import com.xxx.mianshiya.role.repository.RoleRepository;
import com.xxx.mianshiya.role.service.RoleService;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public void add(Role role) {
        roleRepository.save(role);
    }

    @Override
    public void delete(Long id) {
        roleRepository.removeById(id);
    }
}
