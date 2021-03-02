package com.example.parstagram;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

public class LoginActivity extends AppCompatActivity {

    public static final String TAG = "LoginActivity";
    private EditText tv_username;
    private EditText tv_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if (ParseUser.getCurrentUser()!=null)
        {
            //if someone has already sign in
            gotoMainActivity();
        }

        tv_username = findViewById(R.id.tv_username);
        tv_password = findViewById(R.id.tv_password);

    }

    public void handle_login(View view) {
        String username = tv_username.getText().toString();
        String pass = tv_password.getText().toString();
        loginUser(username,pass);

    }
    private void loginUser(String username, String password)
    {
        Log.i(TAG, "Login user: - username: "+username+ " - password:"+password);

        ParseUser.logInInBackground(username,password, new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                // if request success, e will be NULL;
                if (e != null) {
                    Log.e(TAG, "Issue with Login", e);
                    Toast.makeText(LoginActivity.this,"Cannot login to your account!\n Please check your username and password", Toast.LENGTH_LONG);
                }
                else {
                    Log.i(TAG, "Login success!");
                    gotoMainActivity();
                }
            }
        });
    }

    private void gotoMainActivity() {
        Log.i(TAG, "Start MainActivity!");
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        finish(); //remove activity from the back stack
    }
}