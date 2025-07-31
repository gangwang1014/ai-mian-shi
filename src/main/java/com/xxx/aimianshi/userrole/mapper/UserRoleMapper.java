package com.xxx.aimianshi.userrole.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xxx.aimianshi.userrole.domain.entity.UserRole;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserRoleMapper extends BaseMapper<UserRole> {

    List<String> getUserRoles(Long userId);
}
