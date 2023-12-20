package com.impulse.supportticket.model;

import androidx.annotation.NonNull;

public class UserModel {


    private String id;



    private String username;
    private String password;
    private int isLogin;
    private String createdOn;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @NonNull
    public String getUsername() {
        return username;
    }

    public void setUsername(@NonNull String username) {
        this.username = username;
    }
    public int getIsLogin() {
        return isLogin;
    }

    public void setIsLogin(int isLogin) {
        this.isLogin = isLogin;
    }

    public String getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserModel( String id, String username, String password,  String createdOn) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.createdOn = createdOn;
    }


}
