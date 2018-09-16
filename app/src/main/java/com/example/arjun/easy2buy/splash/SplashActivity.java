package com.example.arjun.easy2buy.splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.arjun.easy2buy.R;
import com.example.arjun.easy2buy.login.SigninActivity;

public class SplashActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        logincheck();
    }

    private void logincheck() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // check logged in or not, if logged in then go to dashboasrd else go to login page

                Intent intent = new Intent(SplashActivity.this, SigninActivity.class);
                startActivity(intent);

            }
        },3000);
    }
}
