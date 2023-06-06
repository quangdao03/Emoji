package com.example.emojitest.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.example.emojitest.R;
import com.example.emojitest.util.SharePrefUtils;
import com.example.emojitest.util.SystemUtil;


public class SplashScreenActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SystemUtil.setLocale(this);
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

        SharePrefUtils.increaseCountOpenApp(SplashScreenActivity.this);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }


    public void startAct() {
        if (SharePrefUtils.getCountOpenFirstHelp(this) == 0) {
            startActivity(new Intent(this, LanguageStartScreenActivity.class));
            finish();
        } else {
            startActivity(new Intent(this, IntroScreenActivity.class));
            finish();
        }

    }

    @Override
    public void onBackPressed() {
    }
}
