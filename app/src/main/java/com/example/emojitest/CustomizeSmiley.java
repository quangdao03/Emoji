package com.example.emojitest;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.emojitest.MultiTouchTool.MultiTouchListener;
import com.example.emojitest.databinding.ActivityCustomizeSmileyBinding;

public class CustomizeSmiley extends AppCompatActivity {
    ActivityCustomizeSmileyBinding binding;
    public static ImageView back, done, background;
    Bitmap bitmap;
    Uri image_uri;

    TextView tv_toolbar;
    private static final int CAMERA_REQUEST_CODE = 101;
    private static final int IMAGE_PICK_CAMERA_CODE = 102;
    private String[] cameraPermissions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCustomizeSmileyBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        cameraPermissions = new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        mapping();
        initView();
        binding.igCustom.setImageResource(R.drawable.face_frame_8);
    }

    private void initView() {
        tv_toolbar.setText(getText(R.string.custom_smiley));
        back.setOnClickListener(view -> {
            onBackPressed();
        });
        binding.face.setOnClickListener(view -> {
            getDefault();
            Drawable customDrawable = this.getResources().getDrawable(R.drawable.bg_select);
            binding.face.setBackgroundDrawable(customDrawable);
            binding.igCustom.setImageResource(R.drawable.face_frame_8);
        });
        binding.love.setOnClickListener(view -> {
            getDefault();
            Drawable customDrawable = this.getResources().getDrawable(R.drawable.bg_select);
            binding.love.setBackgroundDrawable(customDrawable);
            binding.igCustom.setImageResource(R.drawable.face_frame_7);
        });
        binding.fram6.setOnClickListener(view -> {
            getDefault();
            Drawable customDrawable = this.getResources().getDrawable(R.drawable.bg_select);
            binding.fram6.setBackgroundDrawable(customDrawable);
            binding.igCustom.setImageResource(R.drawable.face_frame_6);
        });
        binding.fram5.setOnClickListener(view -> {
            getDefault();
            Drawable customDrawable = this.getResources().getDrawable(R.drawable.bg_select);
            binding.fram5.setBackgroundDrawable(customDrawable);
            binding.igCustom.setImageResource(R.drawable.face_frame_5);
        });
        binding.fram4.setOnClickListener(view -> {
            getDefault();
            Drawable customDrawable = this.getResources().getDrawable(R.drawable.bg_select);
            binding.fram4.setBackgroundDrawable(customDrawable);
            binding.igCustom.setImageResource(R.drawable.face_frame_4);

        });
        binding.fram3.setOnClickListener(view -> {
            getDefault();
            Drawable customDrawable = this.getResources().getDrawable(R.drawable.bg_select);
            binding.fram3.setBackgroundDrawable(customDrawable);
            binding.igCustom.setImageResource(R.drawable.face_frame_3);

        });
        binding.fram2.setOnClickListener(view -> {
            getDefault();
            Drawable customDrawable = this.getResources().getDrawable(R.drawable.bg_select);
            binding.fram2.setBackgroundDrawable(customDrawable);
            binding.igCustom.setImageResource(R.drawable.face_frame_2);

        });
        binding.fram1.setOnClickListener(view -> {
            getDefault();
            Drawable customDrawable = this.getResources().getDrawable(R.drawable.bg_select);
            binding.fram1.setBackgroundDrawable(customDrawable);
            binding.igCustom.setImageResource(R.drawable.face_frame_1);

        });
        binding.fram.setOnClickListener(view -> {
            getDefault();
            Drawable customDrawable = this.getResources().getDrawable(R.drawable.bg_select);
            binding.fram.setBackgroundDrawable(customDrawable);
            binding.igCustom.setImageResource(R.drawable.face_frame);

        });
        done.setOnClickListener(view -> {
            onBackPressed();
            CreateIconActivity.img_bg.setImageBitmap(viewToBitmap());
            finish();
        });
        binding.album.setOnClickListener(view -> {
            startActivity(new Intent(CustomizeSmiley.this, LoadImageScreenActivity.class));
        });
        binding.camera.setOnClickListener(view -> {
            if (checkCameraPermission()) {
                ImagePickFromCamera();
            } else {
                requestCameraPermission();
            }
        });
    }

    private void mapping() {
        background = findViewById(R.id.background);
        tv_toolbar = findViewById(R.id.tv_toolbar);
        back = findViewById(R.id.back);
        done = findViewById(R.id.done);
    }

    private Bitmap viewToBitmap() {
        bitmap = Bitmap.createBitmap(binding.rlControl.getWidth(), binding.rlControl.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        binding.rlControl.draw(canvas);
        return bitmap;

    }

    @Override
    protected void onResume() {
        super.onResume();
        final MultiTouchListener multiTouchListener = new MultiTouchListener();
        multiTouchListener.setOnSpiralTouch(new CreateIconActivity.OnSpiralTouch() {
            @Override
            public void OnTouch(int action) {

            }
        });
        binding.background.setOnTouchListener(multiTouchListener);
    }

    private void ImagePickFromCamera() {
        ContentValues contentValues = new ContentValues();
        contentValues.put(MediaStore.Images.Media.TITLE, "Temp_Image Title");
        contentValues.put(MediaStore.Images.Media.DESCRIPTION, "Temp_Image Description");
        image_uri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, image_uri);
        startActivityForResult(intent, IMAGE_PICK_CAMERA_CODE);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {

            case CAMERA_REQUEST_CODE: {
                if (grantResults.length > 0) {
                    boolean cameraAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean storageAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    if (cameraAccepted && storageAccepted) {
                        ImagePickFromCamera();
                    } else {
                        Toast.makeText(this, "" + getText(R.string.cam_per), Toast.LENGTH_SHORT).show();
                    }
                }
            }
            break;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == IMAGE_PICK_CAMERA_CODE) {
                binding.background.setImageURI(image_uri);
            }
        }
    }

    private boolean checkCameraPermission() {
        boolean result = ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA) == (PackageManager.PERMISSION_GRANTED);
        boolean result1 = ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == (PackageManager.PERMISSION_GRANTED);
        return result && result1;

    }

    private void getDefault(){
        binding.face.setBackgroundColor(Color.WHITE);
        binding.love.setBackgroundColor(Color.WHITE);
        binding.fram6.setBackgroundColor(Color.WHITE);
        binding.fram5.setBackgroundColor(Color.WHITE);
        binding.fram4.setBackgroundColor(Color.WHITE);
        binding.fram3.setBackgroundColor(Color.WHITE);
        binding.fram2.setBackgroundColor(Color.WHITE);
        binding.fram1.setBackgroundColor(Color.WHITE);
        binding.fram.setBackgroundColor(Color.WHITE);

    }

    private void requestCameraPermission() {
        ActivityCompat.requestPermissions(this, cameraPermissions, CAMERA_REQUEST_CODE);
    }
}