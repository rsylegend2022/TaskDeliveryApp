package com.example.android.recyclerview;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class SelectRoleActivity extends AppCompatActivity {
    public static final String TAG = "SelectRoleActivity";

    private Button volunteer;
    private Button landOwner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.role_select_login);
        final Intent mIntent = new Intent(this, LogInActivity.class);
        final Bundle mBundle = new Bundle();

        volunteer = (Button) findViewById(R.id.volunteer);
        landOwner = (Button) findViewById(R.id.landOwner);

        volunteer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mBundle.putBoolean("role", true);
                mIntent.putExtras(mBundle);
                startActivity(mIntent);
                finish();
            }
        });

        landOwner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mBundle.putBoolean("role", false);
                mIntent.putExtras(mBundle);
                startActivity(mIntent);
                finish();
            }
        });
    }
}
