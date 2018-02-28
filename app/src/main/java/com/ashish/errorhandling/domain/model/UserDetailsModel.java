package com.ashish.errorhandling.domain.model;

/**
 * @author ashish
 * @since 28/02/18
 */
public class UserDetailsModel {

    private String userName;
    private int age;
    private String gender;
    private String address;
    private String bloodGroup;
    private int height;
    private float weight;

    public UserDetailsModel(String userName, int age, String gender, String address,
                                String bloodGroup, int height, float weight) {
        this.userName = userName;
        this.age = age;
        this.gender = gender;
        this.address = address;
        this.bloodGroup = bloodGroup;
        this.height = height;
        this.weight = weight;
    }

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
