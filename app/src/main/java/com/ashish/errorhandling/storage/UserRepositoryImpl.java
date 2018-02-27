package com.ashish.errorhandling.storage;

import com.ashish.errorhandling.domain.repository.UserRepository;
import com.ashish.errorhandling.utils.PrefUtil;

/**
 * @author ashish
 * @since 28/02/18
 */
public class UserRepositoryImpl implements UserRepository {

    private final String KEY_AUTH_TOKEN = "auth_token";

    @Override
    public String getAuthToken() {
        return PrefUtil.getString(KEY_AUTH_TOKEN);
    }

}
