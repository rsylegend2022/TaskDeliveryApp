package com.example.android.recyclerview;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.RetrofitApi.APIClient;
import com.example.android.RetrofitApi.APIInterface;
import com.example.android.RetrofitApi.POJO.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LogInActivity extends AppCompatActivity {

    private boolean asVolunteer;
    private TextView role;
    private EditText userName;
    private EditText userPassWord;
    private Button signUp;
    private Button signIn;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        Intent intent = getIntent();
        if (null != intent) { //Null Checking
            asVolunteer = intent.getBooleanExtra("role", false);
        }
        role = (TextView) findViewById(R.id.userRole);
        userName = (EditText) findViewById(R.id.userName);
        userPassWord = (EditText) findViewById(R.id.userPwd);
        signUp = (Button) findViewById(R.id.signUp);
        signIn = (Button) findViewById(R.id.signIn);
        if (asVolunteer) {
            role.setText("volunteer");
        } else {
            role.setText("land owner");
        }
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = userName.getText().toString();
                String pwd = userPassWord.getText().toString();
                Boolean role = asVolunteer;
                user = new User(name, pwd, role);
                APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
                Call<Integer> call1 = apiInterface.newUser(user);
                call1.enqueue(new Callback<Integer>() {
                    @Override
                    public void onResponse(Call<Integer> call, Response<Integer> response) {
                        Integer resCode = response.body();
                        if (resCode == 200) {
                            Toast.makeText(getApplicationContext(), "Successfully sign up.", Toast.LENGTH_SHORT).show();
                            startMainActivity();
                        } else {
                            Toast.makeText(getApplicationContext(), "Sign Up failed, the given username already existed.", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Integer> call, Throwable t) {
                        call.cancel();
                    }
                });
            }
        });
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = userName.getText().toString();
                String pwd = userPassWord.getText().toString();
                Boolean role = asVolunteer;
                user = new User(name, pwd, role);
                APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
                Call<Integer> call1 = apiInterface.login(user);
                call1.enqueue(new Callback<Integer>() {
                    @Override
                    public void onResponse(Call<Integer> call, Response<Integer> response) {
                        Integer resCode = response.body();
                        if (resCode == 200) {
                            Toast.makeText(getApplicationContext(), "Successfully sign in.", Toast.LENGTH_SHORT).show();
                            startMainActivity();
                        } else {
                            Toast.makeText(getApplicationContext(), "Sign in failed, the user/password is not correct.", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Integer> call, Throwable t) {
                        call.cancel();
                    }
                });
            }
        });
    }

    private void startMainActivity(){
        final Intent mIntent = new Intent(this, MainActivity.class);
        UserWrapper.setInstance(user);
        final Bundle mBundle = new Bundle();
        mBundle.putString("userName", userName.getText().toString());
        mBundle.putBoolean("userRole", asVolunteer);
        mIntent.putExtras(mBundle);
        startActivity(mIntent);
    }
}
