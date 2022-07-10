package com.example.android.recyclerview;

import android.app.Application;

import com.example.android.RetrofitApi.POJO.User;

public class UserWrapper {
    private static User instance;

    public static void setInstance(User instance) {
        UserWrapper.instance = instance;
    }

    public static User getInstance() {
        return instance;
    }
}
