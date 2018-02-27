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
    private int status;
    private String msg;

    public LastLoginDetailsModel(String userId, String userName, String lastLoginDate, String device,
                                 int status, String msg) {
        this.userId = userId;
        this.userName = userName;
        this.lastLoginDate = lastLoginDate;
        this.device = device;
        this.status = status;
        this.msg = msg;
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

    public int getStatus() {
        return status;
    }

    public String getMsg() {
        return msg;
    }
}
