package com.ashish.errorhandling.network.response;

import com.google.gson.annotations.SerializedName;

/**
 * @author ashish
 * @since 28/02/18
 */
public class ResponseBaseModel {

    @SerializedName("status")
    private int status;
    @SerializedName("msg")
    private String msg;

    public int getStatus() {
        return status;
    }

    public String getMsg() {
        return msg;
    }

}
