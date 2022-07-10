package com.example.android.RetrofitApi;

import com.example.android.RetrofitApi.POJO.Task;
import com.example.android.RetrofitApi.POJO.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;


public interface APIInterface {
    @GET("/getTasks")
    Call<List<Task>> doGetListTasks();

    @POST("/newTask")
    Call<Integer> createTask(@Body Task task);

    @POST("/login")
    Call<Integer> login(@Body User user);

    @POST("/newUser")
    Call<Integer> newUser(@Body User user);
}
