package com.xxx.aimianshi.user.repository;

import com.xxx.aimianshi.user.domain.token.UserToken;

public interface TokenRepository {

    void saveUserToken(UserToken userToken);

    String getUserToken(Long userId, String tokenId);

    void deleteUserToken(Long userId, String tokenId);

    void deleteUserTokenSetTokenId(Long userId, String tokenId);

    void deleteUserTokenSetAndUserAllToken(Long userId);
}
