package com.ashish.errorhandling.presentation.models;

/**
 * @author ashish
 * @since 28/02/18
 */
public class WelcomeModel {

    private String userName;
    private String lastLoginTime;
    private String device;

    public WelcomeModel(String userName, String lastLoginTime, String device) {
        this.userName = userName;
        this.lastLoginTime = lastLoginTime;
        this.device = device;
    }

    public String getUserName() {
        return userName;
    }

    public String getLastLoginTime() {
        return lastLoginTime;
    }

    public String getDevice() {
        return device;
    }
}
