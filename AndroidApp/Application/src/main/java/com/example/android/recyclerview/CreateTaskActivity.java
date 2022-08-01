package com.example.android.recyclerview;

/*
 * Copyright 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.android.RetrofitApi.APIClient;
import com.example.android.RetrofitApi.APIInterface;
import com.example.android.RetrofitApi.POJO.Task;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple launcher activity containing a summary sample description, sample log and a custom
 * {@link Fragment} which can display a view.
 * <p>
 * For devices with displays with a width of 720dp or greater, the sample log is always visible,
 * on other devices it's visibility is controlled by an item on the Action Bar.
 */
public class CreateTaskActivity extends AppCompatActivity {

    public static final String TAG = "CreateTaskActivity";

    private EditText taskName;
    private EditText taskDescription;
    private EditText taskAddress;
    private Spinner taskType;
    private Spinner taskUrgency;
    private EditText taskWorkload;
    private Button createButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_task);

        taskName = (EditText) findViewById(R.id.taskName);
        taskDescription = (EditText) findViewById(R.id.taskDescription);
        taskAddress = (EditText) findViewById(R.id.taskAddress);
        taskUrgency = (Spinner) findViewById(R.id.taskUrgency);
        taskType = (Spinner) findViewById(R.id.taskType);
        taskWorkload = (EditText) findViewById(R.id.taskWorkload);
        createButton = (Button) findViewById(R.id.elevatedButton);
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Task newTask = new Task(taskName.getText().toString(),
                        taskDescription.getText().toString(),
                        taskAddress.getText().toString(),
                        taskType.getSelectedItem().toString(),
                        taskUrgency.getSelectedItem().toString(),
                        taskWorkload.getText().toString(),
                        TaskStatus.Created, UserWrapper.getInstance().getName(), "null");
                createTask(newTask);
            }
        });
    }

    private void createTask(Task newTask) {
        APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
        Call<Integer> call1 = apiInterface.createTask(newTask);
        call1.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                Integer resCode = response.body();
                if (resCode == 200) {
                    Toast.makeText(getApplicationContext(), "Successfully created new task", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "Created new task failed. Please check your internet connection.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                call.cancel();
            }
        });
        //TaskHolderWapper.getInstance().addTask(newTask);
    }


}
