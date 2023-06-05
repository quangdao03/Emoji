package com.example.emojitest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.emojitest.databinding.ActivityResultBinding;

import java.io.File;

public class ResultActivity extends AppCompatActivity {
    ImageView back;
    TextView tv_toolbar;
    String path;
    ActivityResultBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityResultBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        mapping();
    }

    private void mapping() {
        back = (ImageView) findViewById(R.id.back);
        tv_toolbar = (TextView) findViewById(R.id.tv_toolbar);
        tv_toolbar.setText("Result");
        Intent intent = getIntent();
        path = intent.getStringExtra("uri_path");

        Glide.with(this)
                .asBitmap()
                .load(path)
                .into(binding.imagResult);
        back.setOnClickListener(view -> {
            onBackPressed();
            finish();
        });
        binding.home.setOnClickListener(view -> {
            onBackPressed();
            finish();
        });
        binding.share.setOnClickListener(view -> {
            shareFileImage(path);
        });

    }
    String uri1 = "";

    private void shareFileImage(String path) {
        Log.d("TAG", "shareFileVideo: " + path);
        File file;
        if (path.contains("content://com."))
            intentFile(new File(Uri.parse(path).getPath()));
        else if (path.contains("content://")) {
            uri1 = "file://" + path;
            file = new File(Uri.parse(uri1).getPath());
            intentFile(file);
        } else {
            uri1 = "file://" + path;
            file = new File(Uri.parse(uri1).getPath());
            intentFile(file);
        }
    }

    private void intentFile(File file) {
        String packageName = getApplicationContext().getPackageName();

        if (file.exists()) {
            Uri _uri = FileProvider.getUriForFile(this,
                    packageName + ".provider", file);
            Intent intent2 = new Intent(Intent.ACTION_SEND);
            intent2.putExtra("android.intent.extra.SUBJECT", getResources().getString(R.string.app_name));
            intent2.setType("video/*");
            intent2.putExtra("android.intent.extra.STREAM", _uri);
            intent2.putExtra("android.intent.extra.TEXT", "Image");
            startActivity(Intent.createChooser(intent2, "Where to Share?"));
        }
    }

}