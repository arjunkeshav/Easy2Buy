package com.example.arjun.easy2buy.newadmin;

import java.io.Serializable;

public class UserModel implements Serializable {
    private String email;
    private String password;
    private String userId;
    private String userType;

    public String getDp() {
        return dp;
    }

    public void setDp(String dp) {
        this.dp = dp;
    }

    private String dp;

    public UserModel(String email, String password, String userId, String userType) {
        this.email = email;
        this.password = password;
        this.userId = userId;
        this.userType = userType;
    }

    public UserModel() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }
}
