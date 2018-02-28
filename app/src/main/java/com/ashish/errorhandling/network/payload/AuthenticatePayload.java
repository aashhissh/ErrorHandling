package com.ashish.errorhandling.network.payload;

import com.google.gson.annotations.SerializedName;

/**
 * @author ashish
 * @since 28/02/18
 */
public class AuthenticatePayload {

    @SerializedName("authType")
    private String authType;
    @SerializedName("userName")
    private String userName;
    @SerializedName("password")
    private String password;
    @SerializedName("token")
    private String token;

    public AuthenticatePayload(String userName, String password) {
        this.userName = userName;
        this.password = password;
        this.authType = "PASSWD";
    }

    public AuthenticatePayload(String token) {
        this.token = token;
        this.authType = "TKN";
    }

}
