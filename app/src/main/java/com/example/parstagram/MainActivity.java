package com.example.parstagram;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.parstagram.fragments.ComposeFragment;
import com.example.parstagram.fragments.PostFragment;
import com.example.parstagram.fragments.ProfileFragment;
import com.example.parstagram.models.Post;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.File;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    //test line
    public static final String TAG = "MainActivity";
    final FragmentManager fragmentManager = getSupportFragmentManager();
    private ImageView iv_logout;
    private BottomNavigationView bottomNavigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        iv_logout = findViewById(R.id.iv_logout);
        bottomNavigationView = findViewById(R.id.bottom_navigation);

        iv_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handle_logout();
            }
        });
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Fragment fragment;
                fragment = new Fragment(); //init fragment
                switch (menuItem.getItemId()) {
                    case R.id.menu_home:
                        Log.i(TAG, "Home button is clicked!");
                        fragment = new PostFragment();
                        break;
                    case R.id.menu_compose:
                        Log.i(TAG, "Compose button is clicked! Invokes Compose Fragment...");
                        fragment = new ComposeFragment();
                        break;
                    case R.id.menu_userAccount:
                        Log.i(TAG, "User button is clicked!");
                        fragment = new ProfileFragment();
                        break;
                }
                fragmentManager.beginTransaction().replace(R.id.frame_layout_inMain, fragment).commit();
                return true;
            }
        });
        //init bottom nav
        bottomNavigationView.setSelectedItemId(R.id.menu_home);
       // queryPost();

    }

    public void handle_logout() {
        Log.i(TAG,"Logining user out....");
        ParseUser.logOut();
        ParseUser currentUser = ParseUser.getCurrentUser();
        if (currentUser==null)
        {
            Log.i(TAG,"Successfully logged out. Exit activity....");
        }
        finish();
    }
}