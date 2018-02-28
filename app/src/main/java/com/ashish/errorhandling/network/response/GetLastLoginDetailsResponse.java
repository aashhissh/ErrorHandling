package com.ashish.errorhandling.network.response;

import com.google.gson.annotations.SerializedName;

/**
 * @author ashish
 * @since 28/02/18
 */
public class GetLastLoginDetailsResponse extends ResponseBaseModel {

    @SerializedName("userId")
    private String userId;
    @SerializedName("userName")
    private String userName;
    @SerializedName("lastLoginDate")
    private String lastLoginDate;
    @SerializedName("device")
    private String device;

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
