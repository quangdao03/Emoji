package com.example.emojitest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.rl_create_text).setOnClickListener(view -> {
            startActivity(new Intent(MainActivity.this,CreateTextActivity.class));
        });
        findViewById(R.id.rl_create).setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this,CreateIconActivity.class));
        });
    }


}