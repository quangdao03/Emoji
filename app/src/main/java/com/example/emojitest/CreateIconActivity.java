package com.example.emojitest;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

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
    ArrayList<Icon> iconArrayListEyeBrow = new ArrayList<>();
    ArrayList<Icon> iconArrayListMouth = new ArrayList<>();
    ArrayList<Icon> iconArrayListGesture = new ArrayList<>();
    ArrayList<Icon> iconArrayListNose = new ArrayList<>();

    ActivityCreateIconBinding binding;
    private StickerImageView sticker;
    private StickerImageView sticker1;
    private StickerImageView stickermouth;
    private StickerImageView stickergesture;
    private StickerImageView stickernose;
    ArrayList<Integer> stickerviewId = new ArrayList<>();
    int view_id, flag = 0, flagVisibility = 0;
    View previousView = null;
    View previousView1 = null;
    View previousViewmouth = null;
    View previousViewgesture = null;
    View previousViewnose = null;

    private String delete  = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCreateIconBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        iconArrayList = listSticker1("icon", "icon/", iconArrayList);
        iconArrayList1 = listSticker1("icon_eye", "icon_eye/", iconArrayList1);
        iconArrayListEyeBrow = listSticker1("eye_brow", "eye_brow/", iconArrayListEyeBrow);
        iconArrayListMouth = listSticker1("mouth", "mouth/", iconArrayListMouth);
        iconArrayListGesture = listSticker1("gesture", "gesture/", iconArrayListGesture);
        iconArrayListNose = listSticker1("nose", "nose/", iconArrayListNose);
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
            getIconEye();
            delete = "eye";
        });
        binding.iconBg.setOnClickListener(v -> {
            getIcon();
        });
        binding.iconEyebrow.setOnClickListener(v -> {
            getEyeBrow();
            delete = "eye_brow";

        });
        binding.iconMouth.setOnClickListener(v -> {
            getMouth();
            delete = "mouth";
        });
        binding.iconAddition.setOnClickListener(v -> {
            delete = "gesture";
            getAddition();
        });
        binding.iconNose.setOnClickListener(view -> {
            getNose();
        });
        binding.delete.setOnClickListener(v -> {

            if (delete.equals("eye")){
                if (sticker.getParent() != null) {
                    ViewGroup myCanvas = ((ViewGroup) sticker.getParent());
                    myCanvas.removeView(sticker);
                }
            }else if (delete.equals("eye_brow")){
                if (sticker1.getParent() != null) {
                    ViewGroup myCanvas = ((ViewGroup) sticker1.getParent());
                    myCanvas.removeView(sticker1);
                }
            }else if (delete.equals("mouth")){
                if (stickermouth.getParent() != null) {
                    ViewGroup myCanvas = ((ViewGroup) stickermouth.getParent());
                    myCanvas.removeView(stickermouth);
                }
            }
            else if (delete.equals("gesture")){
                if (stickermouth.getParent() != null) {
                    ViewGroup myCanvas = ((ViewGroup) stickermouth.getParent());
                    myCanvas.removeView(stickermouth);
                }
            }
            else {
                Toast.makeText(this, "please add sticker to remove", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getNose() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(CreateIconActivity.this, 6, GridLayoutManager.VERTICAL, false);

        rcy_icon.setLayoutManager(gridLayoutManager);
        rcy_icon.setHasFixedSize(true);

        iconAdapter = new IconAdapter(CreateIconActivity.this, new IconAdapter.iClickListener() {
            @Override
            public void onClickItem(Icon icon) {

                stickernose = new StickerImageView(CreateIconActivity.this, onTouchSticker);
                Glide.with(CreateIconActivity.this).asBitmap().load(Uri.parse(icon.getStickerpath())).into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        stickernose.setImageBitmap(resource);
                    }
                });
                int size = convertDpToPixel(140, CreateIconActivity.this);
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                        size,
                        size
                );
                params.addRule(RelativeLayout.CENTER_HORIZONTAL);  // Đặt sticker ở giữa theo chiều ngang
                params.addRule(RelativeLayout.ALIGN_PARENT_TOP);  // Đặt sticker ở phía trên
                params.topMargin = getResources().getDimensionPixelSize(R.dimen.dp100);  // Đặt khoảng cách topMargin bằng một nửa chiều cao ảnh
                stickernose.setLayoutParams(params);
                Random r = new Random();
                view_id = r.nextInt();
                if (view_id < 0) {
                    view_id = view_id - (view_id * 2);
                }
                if (previousViewnose  != null) {
                    binding.rlImage.removeView(previousViewnose );
                }
                stickernose.setId(view_id);
                stickerviewId.add(view_id);
                binding.rlImage.addView(stickernose);
                previousViewnose = stickernose;
            }
        });
        rcy_icon.setAdapter(iconAdapter);
        iconAdapter.addAll(iconArrayListNose);
    }

    private void getAddition() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(CreateIconActivity.this, 6, GridLayoutManager.VERTICAL, false);

        rcy_icon.setLayoutManager(gridLayoutManager);
        rcy_icon.setHasFixedSize(true);

        iconAdapter = new IconAdapter(CreateIconActivity.this, new IconAdapter.iClickListener() {
            @Override
            public void onClickItem(Icon icon) {

                stickergesture = new StickerImageView(CreateIconActivity.this, onTouchSticker);
                Glide.with(CreateIconActivity.this).asBitmap().load(Uri.parse(icon.getStickerpath())).into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        stickergesture.setImageBitmap(resource);
                    }
                });
                int size = convertDpToPixel(150, CreateIconActivity.this);
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                        size,
                        size
                );
                params.addRule(RelativeLayout.CENTER_HORIZONTAL);  // Đặt sticker ở giữa theo chiều ngang
                params.addRule(RelativeLayout.ALIGN_PARENT_TOP);  // Đặt sticker ở phía trên
                params.topMargin = getResources().getDimensionPixelSize(R.dimen.dp120);  // Đặt khoảng cách topMargin bằng một nửa chiều cao ảnh
                stickergesture.setLayoutParams(params);
                Random r = new Random();
                view_id = r.nextInt();
                if (view_id < 0) {
                    view_id = view_id - (view_id * 2);
                }
                if (previousViewgesture  != null) {
                    binding.rlImage.removeView(previousViewgesture );
                }
                stickergesture.setId(view_id);
                stickerviewId.add(view_id);
                binding.rlImage.addView(stickergesture);
                previousViewgesture = stickergesture;
            }
        });
        rcy_icon.setAdapter(iconAdapter);
        iconAdapter.addAll(iconArrayListGesture);
    }

    private void getMouth() {

        GridLayoutManager gridLayoutManager = new GridLayoutManager(CreateIconActivity.this, 6, GridLayoutManager.VERTICAL, false);

        rcy_icon.setLayoutManager(gridLayoutManager);
        rcy_icon.setHasFixedSize(true);

        iconAdapter = new IconAdapter(CreateIconActivity.this, new IconAdapter.iClickListener() {
            @Override
            public void onClickItem(Icon icon) {

                stickermouth = new StickerImageView(CreateIconActivity.this, onTouchSticker);
                Glide.with(CreateIconActivity.this).asBitmap().load(Uri.parse(icon.getStickerpath())).into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        stickermouth.setImageBitmap(resource);
                    }
                });
                int size = convertDpToPixel(200, CreateIconActivity.this);
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                        size,
                        size
                );
                params.addRule(RelativeLayout.CENTER_HORIZONTAL);  // Đặt sticker ở giữa theo chiều ngang
                params.addRule(RelativeLayout.ALIGN_PARENT_TOP);  // Đặt sticker ở phía trên
                params.topMargin = getResources().getDimensionPixelSize(R.dimen.dp150);  // Đặt khoảng cách topMargin bằng một nửa chiều cao ảnh
                stickermouth.setLayoutParams(params);
                Random r = new Random();
                view_id = r.nextInt();
                if (view_id < 0) {
                    view_id = view_id - (view_id * 2);
                }
                if (previousViewmouth  != null) {
                    binding.rlImage.removeView(previousViewmouth );
                }
                stickermouth.setId(view_id);
                stickerviewId.add(view_id);
                binding.rlImage.addView(stickermouth);
                previousViewmouth = stickermouth;
            }
        });
        rcy_icon.setAdapter(iconAdapter);
        iconAdapter.addAll(iconArrayListMouth);
    }

    private void getEyeBrow() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(CreateIconActivity.this, 6, GridLayoutManager.VERTICAL, false);

        rcy_icon.setLayoutManager(gridLayoutManager);
        rcy_icon.setHasFixedSize(true);

        iconAdapter = new IconAdapter(CreateIconActivity.this, new IconAdapter.iClickListener() {
            @Override
            public void onClickItem(Icon icon) {

                sticker1 = new StickerImageView(CreateIconActivity.this, onTouchSticker);
                Glide.with(CreateIconActivity.this).asBitmap().load(Uri.parse(icon.getStickerpath())).into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        sticker1.setImageBitmap(resource);
                    }
                });

                int size = convertDpToPixel(200, CreateIconActivity.this);
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                        size,
                        size
                );
                params.addRule(RelativeLayout.CENTER_HORIZONTAL);  // Đặt sticker ở giữa theo chiều ngang
                params.addRule(RelativeLayout.ALIGN_PARENT_TOP);  // Đặt sticker ở phía trên
                params.topMargin = getResources().getDimensionPixelSize(R.dimen.dp26);  // Đặt khoảng cách topMargin bằng một nửa chiều cao ảnh
                sticker1.setLayoutParams(params);
                Random r = new Random();
                view_id = r.nextInt();
                if (view_id < 0) {
                    view_id = view_id - (view_id * 2);
                }
                if (previousView1  != null) {
                    binding.rlImage.removeView(previousView1 );
                }
                sticker1.setId(view_id);
                stickerviewId.add(view_id);
                binding.rlImage.addView(sticker1);
                previousView1 = sticker1;
            }
        });
        rcy_icon.setAdapter(iconAdapter);
        iconAdapter.addAll(iconArrayListEyeBrow);
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
                int size = convertDpToPixel(200, CreateIconActivity.this);
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                        size,size
                );
                params.addRule(RelativeLayout.CENTER_HORIZONTAL);
                params.addRule(RelativeLayout.ALIGN_PARENT_TOP);  // Đặt sticker ở phía trên
                params.topMargin = getResources().getDimensionPixelSize(R.dimen.dp36);  // Đặt khoảng cách topMargin bằng một nửa chiều cao ảnh
                sticker.setLayoutParams(params);
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
    private static int convertDpToPixel(float dp, Context context) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float px = dp * (metrics.densityDpi / 160f);
        return (int) px;
    }

}