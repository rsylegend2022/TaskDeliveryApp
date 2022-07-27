package com.example.android.recyclerview;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.RetrofitApi.APIClient;
import com.example.android.RetrofitApi.APIInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AcceptTaskActivity extends AppCompatActivity {
    private TextView taskName;
    private TextView taskDescription;
    private TextView taskAddress;
    private TextView taskType;
    private TextView taskUrgency;
    private TextView taskWorkload;
    private Button createButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.accept_task);

        taskName = (TextView) findViewById(R.id.taskName);
        taskDescription = (TextView) findViewById(R.id.taskDescription);
        taskAddress = (TextView) findViewById(R.id.taskAddress);
        taskUrgency = (TextView) findViewById(R.id.taskUrgency);
        taskType = (TextView) findViewById(R.id.taskType);
        taskWorkload = (TextView) findViewById(R.id.taskWorkload);
        createButton = (Button) findViewById(R.id.AcceptButton);

        Intent intent = getIntent();
        if (null != intent) {
            String taskNameInput = intent.getStringExtra("taskName");
            taskName.setText(taskNameInput);
            String taskDesInput = intent.getStringExtra("taskDescription");
            taskDescription.setText(taskDesInput);
            String taskAddressInput = intent.getStringExtra("taskAddress");
            taskAddress.setText(taskAddressInput);
            String taskTypeInput = intent.getStringExtra("taskType");
            taskType.setText(taskTypeInput);
            String taskUrgencyInput = intent.getStringExtra("taskEmergency");
            taskUrgency.setText(taskUrgencyInput);
            String taskWorkloadInput = intent.getStringExtra("taskWorkload");
            taskWorkload.setText(taskWorkloadInput);
        }

        if (RoleWrapper.getInstance().getRoleIsVolunteer()) {
            if (RoleWrapper.getInstance().getTaskMode() == 0) {
                createButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
                        Call<Integer> call1 = apiInterface.acceptTasks(taskName.getText().toString());
                        call1.enqueue(new Callback<Integer>() {
                            @Override
                            public void onResponse(Call<Integer> call, Response<Integer> response) {
                                Integer resCode = response.body();
                                if (resCode == 200) {
                                    Toast.makeText(getApplicationContext(), "Successfully accepted new task", Toast.LENGTH_SHORT).show();
                                    finish();
                                } else {
                                    Toast.makeText(getApplicationContext(), "Accept new task failed. Please check your internet connection.", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<Integer> call, Throwable t) {
                                call.cancel();
                            }
                        });
                    }
                });
            } else if (RoleWrapper.getInstance().getTaskMode() == 1) {
                createButton.setText("finish task");
                createButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
                        Call<Integer> call1 = apiInterface.finishTask(taskName.getText().toString());
                        call1.enqueue(new Callback<Integer>() {
                            @Override
                            public void onResponse(Call<Integer> call, Response<Integer> response) {
                                Integer resCode = response.body();
                                if (resCode == 200) {
                                    Toast.makeText(getApplicationContext(), "Successfully finish this task", Toast.LENGTH_SHORT).show();
                                    finish();
                                } else {
                                    Toast.makeText(getApplicationContext(), "Finish task failed. Please check your internet connection.", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<Integer> call, Throwable t) {
                                call.cancel();
                            }
                        });
                    }
                });
            } else {
                createButton.setVisibility(View.GONE);
            }
        } else {
            createButton.setVisibility(View.GONE);
        }
    }
}
