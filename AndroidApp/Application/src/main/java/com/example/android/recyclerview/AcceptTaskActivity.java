package com.example.android.recyclerview;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

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

        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
