package com.example.emojitest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.emojitest.adapter.BackgroundAdapter;
import com.example.emojitest.model.Background;

import java.util.ArrayList;
import java.util.List;

public class CreateTextActivity extends AppCompatActivity {

    RecyclerView rcy_bg;
    com.example.emojitest.adapter.BackgroundAdapter BackgroundAdapter;
    List<Background> backgroundList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_text);
        rcy_bg = findViewById(R.id.rcy_bg);

        getBackground();
    }
    private void getBackground() {
        GridLayoutManager linearLayoutManager = new GridLayoutManager( this,6, RecyclerView. VERTICAL, false);
        rcy_bg.setLayoutManager (linearLayoutManager);
        rcy_bg.setHasFixedSize(true);
        BackgroundAdapter = new BackgroundAdapter(this,backgroundList);
        backgroundList.add(new Background(R.drawable.icon1));
        backgroundList.add(new Background(R.drawable.icon2));
        backgroundList.add(new Background(R.drawable.icon3));
        backgroundList.add(new Background(R.drawable.icon4));
        backgroundList.add(new Background(R.drawable.icon5));
        backgroundList.add(new Background(R.drawable.icon4));
        backgroundList.add(new Background(R.drawable.icon5));
        backgroundList.add(new Background(R.drawable.icon4));
        backgroundList.add(new Background(R.drawable.icon5));
        backgroundList.add(new Background(R.drawable.icon4));
        backgroundList.add(new Background(R.drawable.icon5));
        backgroundList.add(new Background(R.drawable.icon4));
        backgroundList.add(new Background(R.drawable.icon5));
        rcy_bg.setAdapter(BackgroundAdapter);
    }
}