package com.example.blogbackend.dto;

import java.util.Date;

public class UserDto {
    private String firstName;
    private String lastName;
    private String username;
    private String mobile;
    private String email;
    private Date registeredAt;
    private Date lastLogin;
    private String intro;
    private String profile;

    public UserDto(){}

    public UserDto(String firstName, String lastName, String username, String mobile, String email, Date registeredAt, Date lastLogin, String intro, String profile) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.mobile = mobile;
        this.email = email;
        this.registeredAt = registeredAt;
        this.lastLogin = lastLogin;
        this.intro = intro;
        this.profile = profile;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getRegisteredAt() {
        return registeredAt;
    }

    public void setRegisteredAt(Date registeredAt) {
        this.registeredAt = registeredAt;
    }

    public Date getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Date lastLogin) {
        this.lastLogin = lastLogin;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }
}
