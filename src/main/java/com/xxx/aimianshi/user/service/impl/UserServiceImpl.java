package com.xxx.aimianshi.user.service.impl;

import com.xxx.aimianshi.common.client.JwtClient;
import com.xxx.aimianshi.common.constant.RedisKeyManger;
import com.xxx.aimianshi.common.exception.BizException;
import com.xxx.aimianshi.common.utils.ThrowUtils;
import com.xxx.aimianshi.common.utils.UserContext;
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
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBitSet;
import org.redisson.api.RedissonClient;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.BitSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final UserConverter userConverter;

    private final PasswordEncoder passwordEncoder;

    private final JwtClient jwtClient;

    private final UserRoleService userRoleService;

    private final RedissonClient redissonClient;

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

    @Override
    public void userSignIn() {
        Long userId = UserContext.getCurrentUserId();
        ThrowUtils.throwIf(Objects.isNull(userId), "userId is null");

        LocalDate date = LocalDate.now();
        String userSignKey = RedisKeyManger.getUserSignKey(date.getYear(), userId);
        RBitSet bitSet = redissonClient.getBitSet(userSignKey);
        int offset = date.getDayOfYear();

        boolean alreadySigned = bitSet.set(offset, true);

        if (!alreadySigned) {
            log.info("user {} sign in, date: {}", userId, date);
        } else {
            log.info("user {} already signed, date: {}", userId, date);
        }
    }

    @Override
    public List<Integer> userSignInRecord(Integer year) {
        if (Objects.isNull(year)) {
            year = LocalDate.now().getYear();
        }
        Long userId = UserContext.getCurrentUserId();

        String userSignKey = RedisKeyManger.getUserSignKey(year, userId);
        RBitSet signInBitSet = redissonClient.getBitSet(userSignKey);
        // 加载到 bitset 中, 避免多次访问 redis
        BitSet bitSet = signInBitSet.asBitSet();
        List<Integer> signDays = new LinkedList<>();
        int idx = bitSet.nextSetBit(0);
        while (idx >= 0) {
            signDays.add(idx);
            idx = bitSet.nextSetBit(idx + 1);
        }
        return signDays;
    }

    private String createToken(Long userId) {
        return jwtClient.createToken(UserConstant.USER_ID, userId);
    }
}
