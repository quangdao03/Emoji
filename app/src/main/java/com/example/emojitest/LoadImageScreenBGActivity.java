package com.example.emojitest;

import static com.example.emojitest.CreateIconActivity.isCreateIconActivityActive;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.emojitest.adapter.MyCreationAdapter;
import com.example.emojitest.adapter.MyCreationAdapterBackground;

import java.util.ArrayList;
import java.util.List;

public class LoadImageScreenBGActivity extends AppCompatActivity {

    List<Uri> imageList = new ArrayList<>();
    MyCreationAdapterBackground myCreationAdapterBackground;
    RecyclerView rcvListImg;
    TextView tv_toolbar;
    ImageView back;
    private boolean isselected1 = false;
    private int selectedPosition = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_image);
        rcvListImg = findViewById(R.id.rcvListImg);
        back = findViewById(R.id.back);
        tv_toolbar = findViewById(R.id.tv_toolbar);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
//        tv_toolbar.setText(getText(R.string.Choose));
        requestPermissions(this);


    }


    private void getImage() {
        // Lấy danh sách ảnh từ thư viện
        String[] projection = {MediaStore.Images.Media.DATA};
        String sortOrder = MediaStore.Images.Media.DATE_TAKEN + " DESC";
        Cursor cursor = getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                projection,
                null,
                null,
                sortOrder
        );

        if (cursor != null) {
            while (cursor.moveToNext()) {
                // Thêm Uri của ảnh vào danh sách
                @SuppressLint("Range")
                String imagePath = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
                Uri imageUri = Uri.parse(imagePath);
                imageList.add(imageUri);
            }
            cursor.close();
        }

        // Hiển thị danh sách ảnh trên RecyclerView
        myCreationAdapterBackground = new MyCreationAdapterBackground(this, imageList, new MyCreationAdapterBackground.OnClickImage() {
            @Override
            public void onClickImage(int pos) {

                if (!isselected1) {
                    selectedPosition = pos;
                    if (isCreateIconActivityActive) {
                        CreateIconActivity existingActivity = (CreateIconActivity) ActivityManager.getInstance().getActivity(CreateIconActivity.class);
                        if (existingActivity != null) {
                            existingActivity.updateBackground(Uri.parse(imageList.get(pos).getPath()));
                        }
                    }
                    isselected1 = true;
                    finish();
                }


            }
        });
        Log.d("All", imageList.size() + "");
        rcvListImg.setAdapter(myCreationAdapterBackground);
        if (imageList.size() == 0) {
            findViewById(R.id.layoutNoPics).setVisibility(View.VISIBLE);
        } else {
            findViewById(R.id.layoutNoPics).setVisibility(View.GONE);
        }
    }

    private void refreshImage() {
        // Xóa danh sách ảnh cũ
        imageList.clear();

        // Load lại danh sách ảnh mới
        getImage();

        // Cập nhật lại adapter trên RecyclerView
        myCreationAdapterBackground.notifyDataSetChanged();
    }


    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //quay lai vi tri image da chon truoc do
        if (selectedPosition != -1) {
            rcvListImg.scrollToPosition(selectedPosition);
            selectedPosition = -1; // Reset the selected position
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            refreshImage();
        } catch (Exception e) {
            e.printStackTrace();
        }
        isselected1 = false;
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    public static void requestPermissions(Activity activity) {
        if (ContextCompat.checkSelfPermission(activity.getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(activity.getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                activity.requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 922);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 922) {
            try {
                refreshImage();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
}