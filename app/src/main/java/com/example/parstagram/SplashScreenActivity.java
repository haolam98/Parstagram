package com.example.parstagram;


import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import gr.net.maroulis.library.EasySplashScreen;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EasySplashScreen config = new EasySplashScreen(SplashScreenActivity.this)
                .withFullScreen()
                .withTargetActivity(LoginActivity.class)
                .withSplashTimeOut(3000)
                .withBackgroundColor(Color.parseColor("#FFFFFF"))
                .withBeforeLogoText("Parstagram")
                .withLogo(R.drawable.icon);
        config.getBeforeLogoTextView().setTextColor(Color.BLACK);
        config.getBeforeLogoTextView().setTextSize(30);

        View ESScreen = config.create();
        setContentView(ESScreen);

    }
}
