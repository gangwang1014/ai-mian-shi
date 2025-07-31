package com.xxx.aimianshi.user.service.impl;

import com.xxx.aimianshi.common.client.JwtClient;
import com.xxx.aimianshi.common.exception.BizException;
import com.xxx.aimianshi.common.utils.ThrowUtils;
import com.xxx.aimianshi.role.enums.RoleEnum;
import com.xxx.aimianshi.user.constant.UserConstant;
import com.xxx.aimianshi.user.convert.UserConverter;
import com.xxx.aimianshi.user.domain.entity.User;
import com.xxx.aimianshi.user.domain.req.ChangePasswordReq;
import com.xxx.aimianshi.user.domain.req.UserLoginReq;
import com.xxx.aimianshi.user.domain.req.UserRegisterReq;
import com.xxx.aimianshi.user.domain.req.UserUpdateReq;
import com.xxx.aimianshi.user.domain.resp.UserDetailResp;
import com.xxx.aimianshi.user.domain.resp.UserLoginResp;
import com.xxx.aimianshi.user.repository.UserRepository;
import com.xxx.aimianshi.user.service.UserService;
import com.xxx.aimianshi.userrole.service.UserRoleService;
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
                userRoleService.addUserRole(user.getId(), RoleEnum.USER);
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

    @Override
    public void changePassword(ChangePasswordReq changePasswordReq) {
        User user = userConverter.toEntity(changePasswordReq);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        boolean update = userRepository.updateById(user);
        ThrowUtils.throwIf(!update, "password update failed");

        // todo 踢人下线
    }

    private String createToken(Long userId) {
        return jwtClient.createToken(UserConstant.USER_ID, userId);
    }
}
