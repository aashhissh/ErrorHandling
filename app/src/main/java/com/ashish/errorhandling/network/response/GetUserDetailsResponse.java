package com.ashish.errorhandling.network.response;

import com.google.gson.annotations.SerializedName;

/**
 * @author ashish
 * @since 28/02/18
 */
public class GetUserDetailsResponse extends ResponseBaseModel {

    @SerializedName("userName")
    private String userName;
    @SerializedName("age")
    private int age;
    @SerializedName("gender")
    private String gender;
    @SerializedName("address")
    private String address;
    @SerializedName("bloodGroup")
    private String bloodGroup;
    @SerializedName("height")
    private int height;
    @SerializedName("weight")
    private float weight;

    public String getUserName() {
        return userName;
    }

    public int getAge() {
        return age;
    }

    public String getGender() {
        return gender;
    }

    public String getAddress() {
        return address;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public int getHeight() {
        return height;
    }

    public float getWeight() {
        return weight;
    }
}
