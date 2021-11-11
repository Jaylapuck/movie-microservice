package com.forest.api.core.User;

public class User {

    private int userId;
    private String name;
    private String familyName;
    private String gender;
    private String address;
    private String phoneNumber;
    private String serviceAddress;

    public User(int userId, String name, String familyName, String gender, String address, String phoneNumber, String ServiceAddress) {
        this.userId = userId;
        this.name = name;
        this.familyName = familyName;
        this.gender = gender;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.serviceAddress = ServiceAddress;
    }

    public User() {
        userId = 0;
        name = null;
        familyName = null;
        gender = null;
        address = null;
        phoneNumber = null;
        serviceAddress = null;
    }

    public int getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public String getFamilyName() {
        return familyName;
    }

    public String getGender() {
        return gender;
    }

    public String getAddress() {
        return address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getServiceAddress() {
        return serviceAddress;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setServiceAddress(String serviceAddress) {
        this.serviceAddress = serviceAddress;
    }
}
