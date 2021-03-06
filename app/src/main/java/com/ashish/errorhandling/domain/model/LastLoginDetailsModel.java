package com.ashish.errorhandling.domain.model;

/**
 * @author ashish
 * @since 28/02/18
 */
public class LastLoginDetailsModel {

    private String userId;
    private String userName;
    private String lastLoginDate;
    private String device;

    public LastLoginDetailsModel(String userId, String userName, String lastLoginDate, String device) {
        this.userId = userId;
        this.userName = userName;
        this.lastLoginDate = lastLoginDate;
        this.device = device;
    }

    public String getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public String getLastLoginDate() {
        return lastLoginDate;
    }

    public String getDevice() {
        return device;
    }

}
