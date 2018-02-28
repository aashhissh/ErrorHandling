package com.ashish.errorhandling.network.response;

import com.google.gson.annotations.SerializedName;

/**
 * @author ashish
 * @since 28/02/18
 */
public class AuthenticateResponse extends ResponseBaseModel {

    @SerializedName("accessToken")
    private String accessToken;

    public String getAccessToken() {
        return accessToken;
    }
}
