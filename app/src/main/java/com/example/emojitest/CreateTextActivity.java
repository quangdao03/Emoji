package com.example.emojitest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.emojitest.adapter.BackgroundAdapter;
import com.example.emojitest.databinding.ActivityCreateTextBinding;
import com.example.emojitest.model.Background;

import java.util.ArrayList;
import java.util.List;

public class CreateTextActivity extends AppCompatActivity {

    RecyclerView rcy_bg;
    com.example.emojitest.adapter.BackgroundAdapter BackgroundAdapter;
    List<Background> backgroundList = new ArrayList<>();
    ActivityCreateTextBinding binding;
    ImageView back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCreateTextBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        rcy_bg = findViewById(R.id.rcy_bg);
        back = findViewById(R.id.back);
        initView();
        getBackground();
    }

    private void initView() {
        back.setOnClickListener(view -> {
            onBackPressed();
        });
        binding.igKeyboard.setOnClickListener(view -> {
            Button cancel,ok;
            final Dialog dialog = new Dialog(CreateTextActivity.this);
            dialog.setContentView(R.layout.layout_dialog_text);
            dialog.getWindow().setGravity(Gravity.CENTER);
            dialog.getWindow().setLayout(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.setCancelable(false);
            cancel = dialog.findViewById(R.id.cancel);
            ok = dialog.findViewById(R.id.ok);
            cancel.setOnClickListener(v -> {
                dialog.dismiss();
            });
            ok.setOnClickListener(v -> {
                dialog.dismiss();
            });
            dialog.show();
        });
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