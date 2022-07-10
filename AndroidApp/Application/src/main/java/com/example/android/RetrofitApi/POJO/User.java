package com.example.android.RetrofitApi.POJO;

import com.google.gson.annotations.SerializedName;

public class User {

    @SerializedName("name")
    private String name;
    @SerializedName("password")
    private String password;
    @SerializedName("role")
    private boolean role;

    public User(String name, String password, Boolean role) {
        this.name = name;
        this.password = password;
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public boolean isRole() {
        return role;
    }
}
