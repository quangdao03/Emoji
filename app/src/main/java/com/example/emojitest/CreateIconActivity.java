package com.example.emojitest;

import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.emojitest.adapter.IconAdapter;
import com.example.emojitest.databinding.ActivityCreateIconBinding;
import com.example.emojitest.model.Icon;
import com.example.emojitest.stickerviewclass.StickerImageView;
import com.example.emojitest.stickerviewclass.StickerView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class CreateIconActivity extends AppCompatActivity {
    RecyclerView rcy_icon;
    IconAdapter iconAdapter;
    ImageView img_bg, img_bg1, back;

    ArrayList<Icon> iconArrayList = new ArrayList<>();
    ArrayList<Icon> iconArrayList1 = new ArrayList<>();

    ActivityCreateIconBinding binding;
    private StickerImageView sticker;
    ArrayList<Integer> stickerviewId = new ArrayList<>();
    int view_id, flag = 0, flagVisibility = 0;
    View previousView = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCreateIconBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        iconArrayList = listSticker1("icon", "icon/", iconArrayList);
        iconArrayList1 = listSticker1("icon_eye", "icon_eye/", iconArrayList1);
        back = findViewById(R.id.back);
        rcy_icon = findViewById(R.id.rcy_icon);
        img_bg = findViewById(R.id.img_bg);
        img_bg1 = findViewById(R.id.img_bg1);
        getIcon();
        onclickItem();
        back.setOnClickListener(view -> {
            onBackPressed();
        });
        binding.rlImage.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                removeBorder();
                return false;
            }
        });
    }

    private void onclickItem() {
        binding.iconEye.setOnClickListener(v -> {
//            if (iconArrayList1 != null) {
//                iconArrayList1.clear();
//
//            }
            getIconEye();
        });
        binding.iconBg.setOnClickListener(v -> {
//            if (iconArrayList != null) {
//                iconArrayList.clear();
//
//            }
            getIcon();
        });
    }

    private void getIconEye() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(CreateIconActivity.this, 6, GridLayoutManager.VERTICAL, false);

        rcy_icon.setLayoutManager(gridLayoutManager);
        rcy_icon.setHasFixedSize(true);

        iconAdapter = new IconAdapter(CreateIconActivity.this, new IconAdapter.iClickListener() {
            @Override
            public void onClickItem(Icon icon) {

                sticker = new StickerImageView(CreateIconActivity.this, onTouchSticker);
                Glide.with(CreateIconActivity.this).asBitmap().load(Uri.parse(icon.getStickerpath())).into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        sticker.setImageBitmap(resource);
                    }
                });
                Random r = new Random();
                view_id = r.nextInt();
                if (view_id < 0) {
                    view_id = view_id - (view_id * 2);
                }
                if (previousView  != null) {
                    binding.rlImage.removeView(previousView );
                }
                sticker.setId(view_id);
                stickerviewId.add(view_id);
                binding.rlImage.addView(sticker);
                previousView = sticker;
            }
        });
        rcy_icon.setAdapter(iconAdapter);
        iconAdapter.addAll(iconArrayList1);
    }

    private void getIcon() {

        GridLayoutManager gridLayoutManager = new GridLayoutManager(CreateIconActivity.this, 6, GridLayoutManager.VERTICAL, false);

        rcy_icon.setLayoutManager(gridLayoutManager);
        rcy_icon.setHasFixedSize(true);

        iconAdapter = new IconAdapter(CreateIconActivity.this, new IconAdapter.iClickListener() {
            @Override
            public void onClickItem(Icon icon) {
                Glide.with(CreateIconActivity.this).load(Uri.parse(icon.getStickerpath())).into(img_bg);
            }
        });

        rcy_icon.setAdapter(iconAdapter);
        iconAdapter.addAll(iconArrayList);

    }
    private StickerView.OnTouchSticker onTouchSticker = new StickerView.OnTouchSticker() {
        @Override
        public void onTouchedSticker(StickerView stickerImageView) {
            sticker = (StickerImageView) stickerImageView;
            removeBorder();
        }
    };
    public ArrayList<Icon> listSticker1(String dirFrom, String path, ArrayList<Icon> emojiLists) {

        emojiLists = new ArrayList<>();
        Resources res = getResources();
        AssetManager am = res.getAssets();
        String fileList[] = new String[0];
        try {
            fileList = am.list(dirFrom);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (fileList != null) {
            for (int i = 0; i < fileList.length; i++) {
                emojiLists.add(new Icon("file:///android_asset/" + path + fileList[i], fileList[i]));
            }
        }
        return emojiLists;
    }
    private void removeBorder() {

        for (int i = 0; i < stickerviewId.size(); i++) {
            View view = binding.rlImage.findViewById(stickerviewId.get(i));
            if (view instanceof StickerImageView) {
                StickerImageView stickerView = (StickerImageView) view;
                stickerView.setControlItemsHidden(true);
            }

        }
    }

}