package com.forest.api.core.User;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class User {

    private int userId;
    private String name;
    private String familyName;
    private String gender;
    private String address;
    private String phoneNumber;
    private String serviceAddress;

    public User(int userId, String name, String familyName, String gender, String address, String phoneNumber, String serviceAddress) {
        this.userId = userId;
        this.name = name;
        this.familyName = familyName;
        this.gender = gender;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.serviceAddress = serviceAddress;
    }
}
