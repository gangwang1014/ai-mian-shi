package com.xxx.aimianshi.user.repository.impl;

import com.xxx.aimianshi.common.client.RedisCache;
import com.xxx.aimianshi.common.constant.RedisKeyManger;
import com.xxx.aimianshi.user.constant.UserConstant;
import com.xxx.aimianshi.user.domain.token.UserToken;
import com.xxx.aimianshi.user.repository.TokenRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
@Slf4j
public class TokenRepositoryImpl implements TokenRepository {
    private final RedisCache redisCache;

    public TokenRepositoryImpl(RedisCache redisCache) {
        this.redisCache = redisCache;
    }

    @Override
    public void saveUserToken(UserToken userToken) {
        String tokenId = userToken.getTokenId();
        String token = userToken.getToken();
        Long userId = userToken.getUserId();
        redisCache.set(RedisKeyManger.getUserTokenKey(userId, tokenId), token,
                RedisKeyManger.TOKEN_EXPIRE_TIME, RedisKeyManger.TIME_UNIT);

        // 同时向 user:tokens 的 zSet 里面加入 tokenId
        String key = RedisKeyManger.getUserTokenSetKey(userId);
        Long userTokensCount = redisCache.zSetSize(key);
        if (userTokensCount >= UserConstant.MAX_LOGIN_COUNT) {
            long count = userTokensCount - UserConstant.MAX_LOGIN_COUNT + 1;
            List<String> deleteTokenIds = redisCache.zSetPopMin(key, count, String.class)
                    .stream()
                    .toList();
            // 删除 token
            deleteTokenIds.forEach(deleteToken -> deleteUserToken(userId, deleteToken));
        }
        redisCache.zSetAdd(key, tokenId, System.currentTimeMillis(),
                RedisKeyManger.TOKEN_EXPIRE_TIME, RedisKeyManger.TIME_UNIT);
    }

    public String getUserToken(Long userId, String tokenId) {
        return redisCache.get(RedisKeyManger.getUserTokenKey(userId, tokenId), String.class)
                .orElseThrow(() -> new RuntimeException("get user token fail, user token is not exist, please login again"));
    }

    /**
     * key token:{userId}:{tokenId}
     * 删除 token
     *
     * @param userId  userId
     * @param tokenId tokenId
     */
    @Override
    public void deleteUserToken(Long userId, String tokenId) {
        redisCache.delete(RedisKeyManger.getUserTokenKey(userId, tokenId));
    }

    /**
     * ZSet key user:tokens:{userId}
     * 删除 tokens 集合中的 tokenId
     *
     * @param userId  userId
     * @param tokenId tokenId
     */
    @Override
    public void deleteUserTokenSetTokenId(Long userId, String tokenId) {
        redisCache.zSetRemove(RedisKeyManger.getUserTokenSetKey(userId), tokenId);
    }

    /**
     * 清理用户所有的 token
     *
     * @param userId userId
     */
    @Override
    public void deleteUserTokenSetAndUserAllToken(Long userId) {
        Set<String> tokenIds = redisCache
                .zSetRange(RedisKeyManger.getUserTokenSetKey(userId), 0, -1, String.class);
        tokenIds.forEach(tokenId -> {
            deleteUserToken(userId, tokenId);
            deleteUserTokenSetTokenId(userId, tokenId);
        });
        redisCache.delete(RedisKeyManger.getUserTokenSetKey(userId));
    }
}
