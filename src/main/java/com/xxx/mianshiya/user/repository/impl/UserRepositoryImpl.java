package com.xxx.mianshiya.user.repository.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xxx.mianshiya.user.domain.entity.User;
import com.xxx.mianshiya.user.mapper.UserMapper;
import com.xxx.mianshiya.user.repository.UserRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UserRepositoryImpl extends ServiceImpl<UserMapper, User> implements UserRepository {

    @Override
    public Optional<User> getOptByAccount(String account) {
        return lambdaQuery()
                .eq(User::getAccount, account)
                .oneOpt();
    }
}
