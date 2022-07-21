package com.example.android.RetrofitApi;

import com.example.android.RetrofitApi.POJO.Task;
import com.example.android.RetrofitApi.POJO.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Query;


public interface APIInterface {
    @GET("/getTasks")
    Call<List<Task>> doGetListTasksAll();

    @GET("/getTasks")
    Call<List<Task>> doGetListTasksStatus(@Query("status") String status);

    @POST("/newTask")
    Call<Integer> createTask(@Body Task task);

    @POST("/login")
    Call<Integer> login(@Body User user);

    @POST("/newUser")
    Call<Integer> newUser(@Body User user);

    @PATCH("/acceptTask")
    Call<Integer> acceptTasks(@Body String taskName);

    @PATCH("/finishTask")
    Call<Integer> finishTask(@Body String taskName);
}
