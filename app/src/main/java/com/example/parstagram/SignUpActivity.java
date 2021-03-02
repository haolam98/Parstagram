package com.example.parstagram;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class SignUpActivity extends AppCompatActivity {

    public static final String TAG = "LoginActivity";
    private EditText tv_username;
    private EditText tv_password;
    private EditText tv_email;
    private String username;
    private String password;
    private Button bttn_loginNow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        tv_username = findViewById(R.id.tv_username_signup);
        tv_password = findViewById(R.id.tv_password_signup);
        tv_email = findViewById(R.id.tv_email_signup);
        bttn_loginNow = findViewById(R.id.button_loginNow);


    }

    public void handle_signup(View view) {
        Log.i(TAG,"Signup button clicked!! Handling input data...");
        //temporarily save username & password for login purpose
        username = tv_username.getText().toString();
        password = tv_password.getText().toString();
        String email = tv_email.getText().toString();
        signup_user(username,password,email);

    }

    private void signup_user(String username, String password, String email) {
        // Create the ParseUser
        ParseUser user = new ParseUser();
        // Set core properties
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);
        // Invoke signUpInBackground
        user.signUpInBackground(new SignUpCallback() {
            public void done(ParseException e) {
                if (e == null) {
                    // Hooray! Let them use the app now.
                    Log.i(TAG,"Account created sucessfully!!");
                    Toast.makeText(SignUpActivity.this,"Account successfully created", Toast.LENGTH_SHORT).show();

                    // erase info to prevent sign up twice
                    tv_username.setText("");
                    tv_email.setText("");
                    tv_password.setText("");

                    // make loginNow button visible
                    bttn_loginNow.setVisibility(View.VISIBLE);
                    return;

                } else {
                    // Sign up didn't succeed. Look at the ParseException
                    // to figure out what went wrong
                    Log.e(TAG,"Issue with Signing up...",e);
                    Toast.makeText(SignUpActivity.this,"Sorry, fail to sign you up! Please try again ", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        });

    }

    public void handle_loginNow(View view) {
        Log.i(TAG,"Login Now button clicked!! Logining in...");
        loginUser(username,password);
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
                    Toast.makeText(SignUpActivity.this,"Cannot login to your account!\n Please check your username and password", Toast.LENGTH_LONG);
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