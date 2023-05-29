package com.example.emojitest;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;



public class SplashScreenActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        if (!isTaskRoot()
                && getIntent().hasCategory(Intent.CATEGORY_LAUNCHER)
                && getIntent().getAction() != null
                && getIntent().getAction().equals(Intent.ACTION_MAIN)) {
            finish();
            return;
        }


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startAct();
            }
        }, 4500);


    }

    @Override
    protected void onResume() {
        super.onResume();
    }


    public void startAct() {

            startActivity(new Intent(this, IntroScreenActivity.class));
            finish();

    }

    @Override
    public void onBackPressed() {
    }
}
