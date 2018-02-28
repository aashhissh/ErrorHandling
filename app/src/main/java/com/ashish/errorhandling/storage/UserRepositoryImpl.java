package com.ashish.errorhandling.storage;

import com.ashish.errorhandling.domain.repository.UserRepository;
import com.ashish.errorhandling.utils.PrefUtil;

/**
 * @author ashish
 * @since 28/02/18
 */
public class UserRepositoryImpl implements UserRepository {

    private final String KEY_AUTH_TOKEN = "auth_token";
    private final String KEY_USER_ID = "user_id";

    @Override
    public String getAuthToken() {
        return PrefUtil.getString(KEY_AUTH_TOKEN);
    }

    @Override
    public void saveAuthToken(String token) {
        PrefUtil.setString(KEY_AUTH_TOKEN, token);
    }

    @Override
    public String getUserId() {
        return PrefUtil.getString(KEY_USER_ID);
    }

    @Override
    public void saveUserId(String userId) {
        PrefUtil.setString(KEY_USER_ID, userId);
    }

}
