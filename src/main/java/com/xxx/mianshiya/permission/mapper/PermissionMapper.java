package com.xxx.mianshiya.permission.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xxx.mianshiya.permission.domain.entity.Permission;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PermissionMapper extends BaseMapper<Permission> {

    List<String> findPermissionsByUserId(@Param("userId") Long userId);
}
