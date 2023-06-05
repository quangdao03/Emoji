package com.example.emojitest.activity;

import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.emojitest.BuildConfig;
import com.example.emojitest.R;
import com.example.emojitest.util.SystemUtil;


public class AboutScreenActivity extends AppCompatActivity {
    ImageView back;
    TextView tv_toolbar, version, policy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SystemUtil.setLocale(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        back = (ImageView) findViewById(R.id.back);
        tv_toolbar = (TextView) findViewById(R.id.tv_toolbar);
        version = (TextView) findViewById(R.id.version);
        policy = (TextView) findViewById(R.id.policy);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        policy.setPaintFlags(policy.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        version.setText(getResources().getString(R.string.version) + " " + BuildConfig.VERSION_NAME);
        tv_toolbar.setText(R.string.about);
        policy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent();
//                intent.setAction(Intent.ACTION_VIEW);
//                intent.setData(Uri.parse("https://firebasestorage.googleapis.com/v0/b/neon-photo-editor-dae0e.appspot.com/o/Privacy%20Policy.html?alt=media&token=91dc48df-fff5-4030-b7c6-e5ea84861a04"));
//                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}