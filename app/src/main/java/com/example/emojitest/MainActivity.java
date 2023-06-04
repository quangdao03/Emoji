package com.example.emojitest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {


    int selectedPosition = -1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.rl_create_text).setOnClickListener(view -> {
            startActivity(new Intent(MainActivity.this,CreateTextActivity.class));

        });
        findViewById(R.id.rl_create).setOnClickListener(v -> {
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
            editor.apply();
            startActivity(new Intent(MainActivity.this,CreateIconActivity.class));
        });

    }


}