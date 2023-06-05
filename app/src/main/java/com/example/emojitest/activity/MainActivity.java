package com.example.emojitest.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.emojitest.R;
import com.example.emojitest.util.SharePrefUtils;
import com.example.emojitest.util.SomeThingApp;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {


    int selectedPosition = -1;
    ImageView setting;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setting = findViewById(R.id.setting);
        setting.setOnClickListener(view -> {
            startActivity(new Intent(MainActivity.this,SettingScreenActivity.class));
        });
        findViewById(R.id.mcv_create_text).setOnClickListener(view -> {
            startActivity(new Intent(MainActivity.this,CreateTextActivity.class));
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putInt("select_background_text", selectedPosition);
            editor.putString("select_bg_create_text", "file:///android_asset/backgroundcolor/icon1.png");
            editor.apply();
        });
        findViewById(R.id.ll_create).setOnClickListener(v -> {
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putInt("selected_position", selectedPosition);
            editor.putInt("selected_position_plan", selectedPosition);
            editor.putInt("selected_position_nose", selectedPosition);
            editor.putInt("selected_position_mouth", selectedPosition);
            editor.putInt("selected_position_hair", selectedPosition);
            editor.putInt("selected_position_glass", selectedPosition);
            editor.putInt("selected_position_eye", selectedPosition);
            editor.putInt("selected_position_brow", selectedPosition);
            editor.putInt("selected_position_bread", selectedPosition);
            editor.putInt("selected_position_addition", selectedPosition);
            editor.putInt("selected_position_background", selectedPosition);
            editor.apply();
            startActivity(new Intent(MainActivity.this,CreateIconActivity.class));
        });
        findViewById(R.id.mcv_mu_creation).setOnClickListener(view -> {
            startActivity(new Intent(MainActivity.this,MyCreationScreenActivity.class));
        });

    }
    ArrayList<String> remoteRate = new ArrayList<String>(Arrays.asList("2", "4", "6", "8", "10"));
    @Override
    public void onBackPressed() {
        if (!SharePrefUtils.isRated(this)) {
            String cout = String.valueOf(SharePrefUtils.getCountOpenApp(this));
            if (remoteRate.contains(cout)) {
                SomeThingApp.rateApp(this,1);
            } else {
                dialogExit();
            }
        } else {
            dialogExit();
        }
    }
    private void dialogExit(){
        Button cancel, ok;
        final Dialog dialog = new Dialog(MainActivity.this);
        dialog.setContentView(R.layout.layout_dialog_exit);
        dialog.getWindow().setGravity(Gravity.CENTER);
        dialog.getWindow().setLayout(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);
        cancel = dialog.findViewById(R.id.cancel);
        ok = dialog.findViewById(R.id.ok);
        cancel.setOnClickListener(view -> {
            dialog.dismiss();
        });
        ok.setOnClickListener(view -> {
            dialog.dismiss();
            finishAffinity();
        });
        dialog.show();
    }
}