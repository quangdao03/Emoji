package com.example.emojitest.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.emojitest.R;
import com.example.emojitest.util.SharePrefUtils;
import com.example.emojitest.util.SomeThingApp;
import com.example.emojitest.util.SystemUtil;


public class SettingScreenActivity extends AppCompatActivity {
    ImageView back;
    public static TextView tv_text_language;
    TextView tv_toolbar;
    public static LinearLayout ln_language, ln_rate, ln_share, ln_more, ln_about;
    public static View v2;


    private boolean isShareClicked = false;
    private boolean isClick = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        SystemUtil.setLocale(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        mapping();
        view();


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();

            }
        });
        tv_toolbar.setText(getText(R.string.setting));
        String codeLang = SystemUtil.getPreLanguage(getBaseContext());
        switch (codeLang) {
            case "en":
                tv_text_language.setText("English");
                break;
            case "pt":
                tv_text_language.setText("Portuguese");
                break;
            case "es":
                tv_text_language.setText("Spanish");
                break;
            case "de":
                tv_text_language.setText("German");
                break;
            case "fr":
                tv_text_language.setText("French");
                break;
            case "zh":
                tv_text_language.setText("China");
                break;
            case "hi":
                tv_text_language.setText("Hindi");
                break;
            case "in":
                tv_text_language.setText("Indonesia");
                break;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        isShareClicked = false;
        isClick = false;
        if (SharePrefUtils.isRated(this)) {
            ln_rate.setVisibility(View.GONE);
            v2.setVisibility(View.GONE);
        }

    }

    private void view() {
        ln_about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isShareClicked) {
                    startActivity(new Intent(SettingScreenActivity.this, AboutScreenActivity.class));
                    isShareClicked = true;
                }
            }
        });
        ln_language.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isShareClicked) {
                    startActivity(new Intent(SettingScreenActivity.this, LanguageScreenActivity.class));
                    isShareClicked = true;
                }
            }
        });
        ln_rate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isClick) {

                    if (!SharePrefUtils.isRated(SettingScreenActivity.this)) {
                        SomeThingApp.rateApp(SettingScreenActivity.this, 0);
                    }
                    isClick = true;
                }
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        isClick = false;
                    }
                }, 1000);

            }
        });
        ln_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isShareClicked) {
                    SomeThingApp.shareApp(SettingScreenActivity.this);
                    isShareClicked = true;
                }
            }
        });
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    private void mapping() {
        back = findViewById(R.id.back);
        tv_toolbar = findViewById(R.id.tv_toolbar);
        ln_language = findViewById(R.id.ln_language);
        ln_rate = findViewById(R.id.ln_rate);
        ln_share = findViewById(R.id.ln_share);
        ln_more = findViewById(R.id.ln_more);
        ln_about = findViewById(R.id.ln_about);
        tv_text_language = findViewById(R.id.tv_text_language);
        v2 = findViewById(R.id.v2);
    }
}