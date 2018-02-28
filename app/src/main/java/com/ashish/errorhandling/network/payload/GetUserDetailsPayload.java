package com.ashish.errorhandling.network.payload;

import com.google.gson.annotations.SerializedName;

/**
 * @author ashish
 * @since 28/02/18
 */
public class GetUserDetailsPayload {
    @SerializedName("userId")
    private String userId;

    public GetUserDetailsPayload(String userId) {
        this.userId = userId;
    }
}
