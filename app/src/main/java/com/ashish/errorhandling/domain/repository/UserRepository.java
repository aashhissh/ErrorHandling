package com.ashish.errorhandling.domain.repository;

/**
 * @author ashish
 * @since 28/02/18
 */
public interface UserRepository {

    String getAuthToken();

    void saveAuthToken(String token);

    String getUserId();

    void saveUserId(String userId);

}
