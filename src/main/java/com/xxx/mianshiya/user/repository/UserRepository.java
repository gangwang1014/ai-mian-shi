package com.xxx.mianshiya.user.repository;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xxx.mianshiya.user.domain.entity.User;

import java.util.Optional;

public interface UserRepository extends IService<User> {

    Optional<User> getOptByAccount(String account);
}
