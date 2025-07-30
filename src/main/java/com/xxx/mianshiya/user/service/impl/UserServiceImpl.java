package com.xxx.mianshiya.user.service.impl;

import com.xxx.mianshiya.common.client.JwtClient;
import com.xxx.mianshiya.common.exception.BizException;
import com.xxx.mianshiya.common.utils.ThrowUtils;
import com.xxx.mianshiya.role.enums.RoleEnum;
import com.xxx.mianshiya.user.constant.UserConstant;
import com.xxx.mianshiya.user.convert.UserConverter;
import com.xxx.mianshiya.user.domain.entity.User;
import com.xxx.mianshiya.user.domain.req.UserLoginReq;
import com.xxx.mianshiya.user.domain.req.UserRegisterReq;
import com.xxx.mianshiya.user.domain.req.UserUpdateReq;
import com.xxx.mianshiya.user.domain.resp.UserDetailResp;
import com.xxx.mianshiya.user.domain.resp.UserLoginResp;
import com.xxx.mianshiya.user.repository.UserRepository;
import com.xxx.mianshiya.user.service.UserService;
import com.xxx.mianshiya.userrole.service.UserRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final UserConverter userConverter;

    private final PasswordEncoder passwordEncoder;

    private final JwtClient jwtClient;

    private final UserRoleService userRoleService;

    @Override
    @Transactional
    public void register(UserRegisterReq userRegisterReq) {
        User user = userConverter.toEntity(userRegisterReq);
        synchronized (user.getAccount().intern()) {
            try {
                user.setPassword(passwordEncoder.encode(user.getPassword()));
                userRepository.save(user);
                userRoleService.save(user.getId(), RoleEnum.USER);
            } catch (DuplicateKeyException e) {
                throw new BizException("user already register");
            }
        }
    }

    @Override
    public UserLoginResp login(UserLoginReq userLoginReq) {
        User user = userRepository.getOptByAccount(userLoginReq.getAccount())
                .orElseThrow(() -> new BizException("Incorrect account or password"));
        String token = createToken(user.getId());
        return userConverter.toUserLoginResp(user, token);
    }

    @Override
    public UserDetailResp detail(Long id) {
        User user = userRepository.getOptById(id)
                .orElseThrow(() -> new BizException("Account does not exist"));
        return userConverter.toUserDetailResp(user);
    }

    @Override
    public void update(UserUpdateReq userUpdateReq) {
        User user = userConverter.toEntity(userUpdateReq);
        boolean update = userRepository.updateById(user);
        ThrowUtils.throwIf(!update, "User update failed, maybe the user does not exist or has not been changed");
    }

    @Override
    @Transactional
    public void delete(Long id) {
        boolean remove = userRepository.removeById(id);
        ThrowUtils.throwIf(!remove, "User delete failed, maybe the user does not exist");
        boolean delete = userRoleService.deleteUserRoles(id);
        ThrowUtils.throwIf(!delete, "User roles delete failed, maybe the user roles does not exist");
    }

    private String createToken(Long userId) {
        return jwtClient.createToken(UserConstant.USER_ID, userId);
    }
}
