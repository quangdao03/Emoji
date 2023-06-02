package com.example.emojitest;

import android.content.Context;
import android.content.Intent;
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
import com.example.emojitest.MultiTouchTool.MultiTouchListener;
import com.example.emojitest.MultiTouchTool.SelectionListener;
import com.example.emojitest.adapter.IconAdapter;
import com.example.emojitest.databinding.ActivityCreateIconBinding;
import com.example.emojitest.model.Icon;
import com.example.emojitest.stickerviewclass.StickerImageView;
import com.example.emojitest.stickerviewclass.StickerView;
import com.example.emojitest.util.ViewState;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class CreateIconActivity extends AppCompatActivity {
    RecyclerView rcy_icon;
    IconAdapter iconAdapter;
    public static ImageView img_bg, back;

    ArrayList<Icon> iconArrayList = new ArrayList<>();
    ArrayList<Icon> iconArrayList1 = new ArrayList<>();
    ArrayList<Icon> iconArrayListEyeBrow = new ArrayList<>();
    ArrayList<Icon> iconArrayListMouth = new ArrayList<>();
    ArrayList<Icon> iconArrayListGesture = new ArrayList<>();
    ArrayList<Icon> iconArrayListNose = new ArrayList<>();
    ArrayList<Icon> iconArrayListBread = new ArrayList<>();
    ArrayList<Icon> iconArrayListHair = new ArrayList<>();
    ArrayList<Icon> iconArrayListGlass = new ArrayList<>();
    ArrayList<Icon> iconArrayListIcon = new ArrayList<>();

    ActivityCreateIconBinding binding;
    private StickerImageView sticker;
    private StickerImageView sticker1;
    private StickerImageView stickermouth;
    private StickerImageView stickergesture;
    private StickerImageView stickernose;
    private StickerImageView stickerbread;
    private StickerImageView stickerhair;
    private StickerImageView stickerglass;
    private StickerImageView stickericon;
    ArrayList<Integer> stickerviewId = new ArrayList<>();
    int view_id, flag = 0, flagVisibility = 0;
    View previousView = null;
    View previousView1 = null;
    View previousViewmouth = null;
    View previousViewgesture = null;
    View previousViewnose = null;
    View previousViewbread = null;
    View previousViewhair = null;
    View previousViewglass = null;
    View previousViewicon = null;

    private String delete = "";
    View selectedView = null;


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
        iconArrayListBread = listSticker1("bread", "bread/", iconArrayListBread);
        iconArrayListHair = listSticker1("hair", "hair/", iconArrayListHair);
        iconArrayListGlass = listSticker1("glass", "glass/", iconArrayListGlass);
        iconArrayListIcon = listSticker1("icon_icon", "icon_icon/", iconArrayListIcon);
        back = findViewById(R.id.back);
        rcy_icon = findViewById(R.id.rcy_icon);
        img_bg = findViewById(R.id.img_bg);
        getIcon();
        binding.iconBg.setImageResource(R.drawable.ic_bg_choose);
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
            getDefaultIcon();
            getIconEye();
            delete = "eye";
            binding.iconEye.setImageResource(R.drawable.ic_eye_selected);
        });
        binding.iconBg.setOnClickListener(v -> {
            getDefaultIcon();
            getIcon();
            binding.iconBg.setImageResource(R.drawable.ic_bg_choose);
        });
        binding.iconEyebrow.setOnClickListener(v -> {
            getDefaultIcon();
            getEyeBrow();
            delete = "eye_brow";
            binding.iconEyebrow.setImageResource(R.drawable.ic_eye_brow_selected);
        });
        binding.iconMouth.setOnClickListener(v -> {
            getDefaultIcon();
            getMouth();
            delete = "mouth";
            binding.iconMouth.setImageResource(R.drawable.ic_mouth_selected);
        });
        binding.iconAddition.setOnClickListener(v -> {
            getDefaultIcon();
            delete = "gesture";
            getAddition();
            binding.iconAddition.setImageResource(R.drawable.ic_addtion_selected);
        });
        binding.iconNose.setOnClickListener(view -> {
            getDefaultIcon();
            delete = "nose";
            getNose();
            binding.iconNose.setImageResource(R.drawable.ic_nose_selected);
        });
        binding.iconBeard.setOnClickListener(view -> {
            getDefaultIcon();
            getBread();
            delete = "bread";
            binding.iconBeard.setImageResource(R.drawable.ic_bread_selected);
        });
        binding.iconHair.setOnClickListener(view -> {
            getDefaultIcon();
            getHair();
            delete = "hair";
            binding.iconHair.setImageResource(R.drawable.ic_hair_selected);
        });
        binding.iconGlass.setOnClickListener(view -> {
            getDefaultIcon();
            getGlass();
            delete = "glass";
            binding.iconGlass.setImageResource(R.drawable.ic_glass_selected);
        });
        binding.iconAlbum.setOnClickListener(view -> {
            getDefaultIcon();
            binding.iconAlbum.setImageResource(R.drawable.ic_album_selected);
        });
        binding.iconIcon.setOnClickListener(view -> {
            getDefaultIcon();
            binding.iconIcon.setImageResource(R.drawable.ic_choose_selected);
            getIconChoose();
        });
        binding.iconAdd.setOnClickListener(view -> {
            getDefaultIcon();
            binding.iconAdd.setImageResource(R.drawable.customize_face);
            startActivity(new Intent(CreateIconActivity.this,CustomizeSmiley.class));
        });
        binding.delete.setOnClickListener(v -> {

            if (delete.equals("eye")) {
                if (sticker == null){
                    Toast.makeText(this, "Please select Icon to delete", Toast.LENGTH_SHORT).show();
                }else {
                    if (sticker.getParent() != null){
                        ViewGroup myCanvas = ((ViewGroup) sticker.getParent());
                        myCanvas.removeView(sticker);
                    }
                }
            } else if (delete.equals("eye_brow")) {
                if (sticker1 == null){
                    Toast.makeText(this, "Please select Icon to delete", Toast.LENGTH_SHORT).show();
                }else {
                    if (sticker1.getParent() != null){
                        ViewGroup myCanvas = ((ViewGroup) sticker1.getParent());
                        myCanvas.removeView(sticker1);
                    }
                }
            } else if (delete.equals("mouth")) {
                if(stickermouth == null) {
                    Toast.makeText(this, "Please select Icon to delete", Toast.LENGTH_SHORT).show();
                }else{
                    if (stickermouth.getParent() != null){
                        ViewGroup myCanvas = ((ViewGroup) stickermouth.getParent());
                        myCanvas.removeView(stickermouth);
                    }
                }
            } else if (delete.equals("gesture")) {
                if (stickergesture == null){
                    Toast.makeText(this, "Please select Icon to delete", Toast.LENGTH_SHORT).show();
                }else {
                    if (stickergesture.getParent() != null){
                        ViewGroup myCanvas = ((ViewGroup) stickergesture.getParent());
                        myCanvas.removeView(stickergesture);
                    }
                }
            }else if (delete.equals("nose")){
                if (stickernose == null){
                    Toast.makeText(this, "Please select Icon to delete", Toast.LENGTH_SHORT).show();
                }else {
                    if (stickernose.getParent() != null){
                        ViewGroup myCanvas = ((ViewGroup) stickernose.getParent());
                        myCanvas.removeView(stickernose);
                    }
                }
            }
            else if (delete.equals("bread")){
                if (stickerbread == null){
                    Toast.makeText(this, "Please select Icon to delete", Toast.LENGTH_SHORT).show();
                }else {
                    if (stickerbread.getParent() != null){
                        ViewGroup myCanvas = ((ViewGroup) stickerbread.getParent());
                        myCanvas.removeView(stickerbread);
                    }
                }
            }
            else if (delete.equals("hair")){
                if (stickerbread == null){
                    Toast.makeText(this, "Please select Icon to delete", Toast.LENGTH_SHORT).show();
                }else {
                    if (stickerbread.getParent() != null){
                        ViewGroup myCanvas = ((ViewGroup) stickerbread.getParent());
                        myCanvas.removeView(stickerbread);
                    }
                }
            }
            else if (delete.equals("glass")){
                if (stickerglass == null){
                    Toast.makeText(this, "Please select Icon to delete", Toast.LENGTH_SHORT).show();
                }else {
                    if (stickerglass.getParent() != null){
                        ViewGroup myCanvas = ((ViewGroup) stickerglass.getParent());
                        myCanvas.removeView(stickerglass);
                    }
                }
            }
            else {
                Toast.makeText(this, "please add sticker to remove", Toast.LENGTH_SHORT).show();
            }
        });
        binding.imgReset.setOnClickListener(view -> {
            if (sticker == null && sticker1 == null && stickermouth == null && stickernose == null && stickergesture == null){
                Toast.makeText(this, "please add sticker to remove", Toast.LENGTH_SHORT).show();
            }else {
//                if (sticker.getParent() != null && sticker1.getParent() != null){
//                    ViewGroup myCanvas = ((ViewGroup) sticker.getParent());
//                    myCanvas.removeView(sticker);
//                    ViewGroup myCanvas1 = ((ViewGroup) sticker1.getParent());
//                    myCanvas1.removeView(sticker1);
//                }
                binding.rlImage.removeAllViews();

            }

        });
    }
    private void getIconChoose(){
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
        iconAdapter.addAll(iconArrayListIcon);
    }
    private void getGlass(){
        GridLayoutManager gridLayoutManager = new GridLayoutManager(CreateIconActivity.this, 6, GridLayoutManager.VERTICAL, false);

        rcy_icon.setLayoutManager(gridLayoutManager);
        rcy_icon.setHasFixedSize(true);

        iconAdapter = new IconAdapter(CreateIconActivity.this, new IconAdapter.iClickListener() {
            @Override
            public void onClickItem(Icon icon) {

                stickerglass = new StickerImageView(CreateIconActivity.this, onTouchSticker);
                Glide.with(CreateIconActivity.this).asBitmap().load(Uri.parse(icon.getStickerpath())).into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        stickerglass.setImageBitmap(resource);
                    }
                });
                int size = convertDpToPixel(200, CreateIconActivity.this);
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                        size,
                        size
                );
                params.addRule(RelativeLayout.CENTER_HORIZONTAL);
                params.addRule(RelativeLayout.ALIGN_PARENT_TOP);  // Đặt sticker ở phía trên
                params.topMargin = getResources().getDimensionPixelSize(R.dimen.dp48);
                stickerglass.setLayoutParams(params);
                Random r = new Random();
                view_id = r.nextInt();
                if (view_id < 0) {
                    view_id = view_id - (view_id * 2);
                }
                if (previousViewglass != null) {
                    binding.rlImage.removeView(previousViewglass);
                }
                stickerglass.setId(view_id);
                stickerviewId.add(view_id);
                binding.rlImage.addView(stickerglass);
                previousViewglass = stickerglass;
                final MultiTouchListener multiTouchListener = new MultiTouchListener();
                multiTouchListener.setOnSpiralTouch(new OnSpiralTouch() {
                    @Override
                    public void OnTouch(int action) {
                        removeBorder();
                    }
                });
                stickerglass.setOnTouchListener(multiTouchListener);
            }
        });
        rcy_icon.setAdapter(iconAdapter);
        iconAdapter.addAll(iconArrayListGlass);
    }
    private void getHair(){
        GridLayoutManager gridLayoutManager = new GridLayoutManager(CreateIconActivity.this, 6, GridLayoutManager.VERTICAL, false);

        rcy_icon.setLayoutManager(gridLayoutManager);
        rcy_icon.setHasFixedSize(true);

        iconAdapter = new IconAdapter(CreateIconActivity.this, new IconAdapter.iClickListener() {
            @Override
            public void onClickItem(Icon icon) {

                stickerhair = new StickerImageView(CreateIconActivity.this, onTouchSticker);
                Glide.with(CreateIconActivity.this).asBitmap().load(Uri.parse(icon.getStickerpath())).into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        stickerhair.setImageBitmap(resource);
                    }
                });
                int size = convertDpToPixel(150, CreateIconActivity.this);
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                        size,
                        size
                );
                params.addRule(RelativeLayout.CENTER_HORIZONTAL);
                params.addRule(RelativeLayout.ALIGN_PARENT_TOP);
                stickerhair.setLayoutParams(params);
                Random r = new Random();
                view_id = r.nextInt();
                if (view_id < 0) {
                    view_id = view_id - (view_id * 2);
                }
                if (previousViewhair != null) {
                    binding.rlImage.removeView(previousViewhair);
                }
                stickerhair.setId(view_id);
                stickerviewId.add(view_id);
                binding.rlImage.addView(stickerhair);
                previousViewhair = stickerhair;
                final MultiTouchListener multiTouchListener = new MultiTouchListener();
                multiTouchListener.setOnSpiralTouch(new OnSpiralTouch() {
                    @Override
                    public void OnTouch(int action) {
                        removeBorder();
                    }
                });
                stickerhair.setOnTouchListener(multiTouchListener);
            }
        });
        rcy_icon.setAdapter(iconAdapter);
        iconAdapter.addAll(iconArrayListHair);
    }
    private void getBread(){
        GridLayoutManager gridLayoutManager = new GridLayoutManager(CreateIconActivity.this, 6, GridLayoutManager.VERTICAL, false);

        rcy_icon.setLayoutManager(gridLayoutManager);
        rcy_icon.setHasFixedSize(true);

        iconAdapter = new IconAdapter(CreateIconActivity.this, new IconAdapter.iClickListener() {
            @Override
            public void onClickItem(Icon icon) {

                stickerbread = new StickerImageView(CreateIconActivity.this, onTouchSticker);
                Glide.with(CreateIconActivity.this).asBitmap().load(Uri.parse(icon.getStickerpath())).into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        stickerbread.setImageBitmap(resource);
                    }
                });
                int size = convertDpToPixel(150, CreateIconActivity.this);
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                        size,
                        size
                );
                params.addRule(RelativeLayout.CENTER_HORIZONTAL);
                params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
                params.topMargin = getResources().getDimensionPixelSize(R.dimen.dp100);
                stickerbread.setLayoutParams(params);
                Random r = new Random();
                view_id = r.nextInt();
                if (view_id < 0) {
                    view_id = view_id - (view_id * 2);
                }
                if (previousViewbread != null) {
                    binding.rlImage.removeView(previousViewbread);
                }
                stickerbread.setId(view_id);
                stickerviewId.add(view_id);
                binding.rlImage.addView(stickerbread);
                previousViewbread = stickerbread;
                final MultiTouchListener multiTouchListener = new MultiTouchListener();
                multiTouchListener.setOnSpiralTouch(new OnSpiralTouch() {
                    @Override
                    public void OnTouch(int action) {
                        removeBorder();
                    }
                });
                stickerbread.setOnTouchListener(multiTouchListener);
            }
        });
        rcy_icon.setAdapter(iconAdapter);
        iconAdapter.addAll(iconArrayListBread);
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
                params.topMargin = getResources().getDimensionPixelSize(R.dimen.dp120);  // Đặt khoảng cách topMargin bằng một nửa chiều cao ảnh
                stickernose.setLayoutParams(params);
                Random r = new Random();
                view_id = r.nextInt();
                if (view_id < 0) {
                    view_id = view_id - (view_id * 2);
                }
                if (previousViewnose != null) {
                    binding.rlImage.removeView(previousViewnose);
                }
                stickernose.setId(view_id);
                stickerviewId.add(view_id);
                binding.rlImage.addView(stickernose);
                previousViewnose = stickernose;
                final MultiTouchListener multiTouchListener = new MultiTouchListener();
                multiTouchListener.setOnSpiralTouch(new OnSpiralTouch() {
                    @Override
                    public void OnTouch(int action) {
                        removeBorder();
                    }
                });
                stickernose.setOnTouchListener(multiTouchListener);
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
                if (previousViewgesture != null) {
                    binding.rlImage.removeView(previousViewgesture);
                }
                stickergesture.setId(view_id);
                stickerviewId.add(view_id);
                binding.rlImage.addView(stickergesture);
                previousViewgesture = stickergesture;
                final MultiTouchListener multiTouchListener = new MultiTouchListener();
                multiTouchListener.setOnSpiralTouch(new OnSpiralTouch() {
                    @Override
                    public void OnTouch(int action) {
                        removeBorder();
                    }
                });
                stickergesture.setOnTouchListener(multiTouchListener);
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
                int size = convertDpToPixel(180, CreateIconActivity.this);
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
                if (previousViewmouth != null) {
                    binding.rlImage.removeView(previousViewmouth);
                }
                stickermouth.setId(view_id);
                stickerviewId.add(view_id);
                binding.rlImage.addView(stickermouth);
                previousViewmouth = stickermouth;
                final MultiTouchListener multiTouchListener = new MultiTouchListener();
                multiTouchListener.setOnSpiralTouch(new OnSpiralTouch() {
                    @Override
                    public void OnTouch(int action) {
                        removeBorder();
                    }
                });
                stickermouth.setOnTouchListener(multiTouchListener);
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
                params.topMargin = getResources().getDimensionPixelSize(R.dimen.dp36);  // Đặt khoảng cách topMargin bằng một nửa chiều cao ảnh
                sticker1.setLayoutParams(params);
                Random r = new Random();
                view_id = r.nextInt();
                if (view_id < 0) {
                    view_id = view_id - (view_id * 2);
                }
                if (previousView1 != null) {
                    binding.rlImage.removeView(previousView1);
                }
                sticker1.setId(view_id);
                stickerviewId.add(view_id);
                binding.rlImage.addView(sticker1);
                previousView1 = sticker1;
                final MultiTouchListener multiTouchListener = new MultiTouchListener();
                multiTouchListener.setOnSpiralTouch(new OnSpiralTouch() {
                    @Override
                    public void OnTouch(int action) {
                        removeBorder();
                    }
                });
                sticker1.setOnTouchListener(multiTouchListener);

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
                        size, size
                );
                params.addRule(RelativeLayout.CENTER_HORIZONTAL);
                params.addRule(RelativeLayout.ALIGN_PARENT_TOP);  // Đặt sticker ở phía trên
                params.topMargin = getResources().getDimensionPixelSize(R.dimen.dp48);  // Đặt khoảng cách topMargin bằng một nửa chiều cao ảnh
                sticker.setLayoutParams(params);
                Random r = new Random();
                view_id = r.nextInt();
                if (view_id < 0) {
                    view_id = view_id - (view_id * 2);
                }
                if (previousView != null) {
                    binding.rlImage.removeView(previousView);
                }
                sticker.setId(view_id);
                stickerviewId.add(view_id);
                binding.rlImage.addView(sticker);
                previousView = sticker;
                final MultiTouchListener multiTouchListener = new MultiTouchListener();
                multiTouchListener.setOnSpiralTouch(new OnSpiralTouch() {
                    @Override
                    public void OnTouch(int action) {
                        removeBorder();
                    }
                });
                sticker.setOnTouchListener(multiTouchListener);
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
                final MultiTouchListener multiTouchListener = new MultiTouchListener();
                multiTouchListener.setOnSpiralTouch(new OnSpiralTouch() {
                    @Override
                    public void OnTouch(int action) {
                        removeBorder();
                    }
                });
                img_bg.setOnTouchListener(multiTouchListener);
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
    public interface OnSpiralTouch {
        void OnTouch(int action);
    }
    public void selectView(View view) {

        ((ViewState) view.getTag()).selectionState = true;
        selectedView = view;
    }

    private void getDefaultIcon() {
        binding.iconBg.setImageResource(R.drawable.ic_bg_choose_no);
        binding.iconIcon.setImageResource(R.drawable.ic_choose2);
        binding.iconEye.setImageResource(R.drawable.ic_age);
        binding.iconEyebrow.setImageResource(R.drawable.ic_eyebrow);
        binding.iconMouth.setImageResource(R.drawable.ic_mouth);
        binding.iconAddition.setImageResource(R.drawable.ic_addition);
        binding.iconNose.setImageResource(R.drawable.ic_nose);
        binding.iconBeard.setImageResource(R.drawable.ic_beard);
        binding.iconHair.setImageResource(R.drawable.ic_hair);
        binding.iconGlass.setImageResource(R.drawable.ic_glass);
        binding.iconAlbum.setImageResource(R.drawable.ic_bg_album);
        binding.iconAdd.setImageResource(R.drawable.ic_add);
    }

    @Override
    protected void onResume() {
        super.onResume();
        final MultiTouchListener multiTouchListener = new MultiTouchListener();
        multiTouchListener.setOnSpiralTouch(new OnSpiralTouch() {
            @Override
            public void OnTouch(int action) {
                removeBorder();
            }
        });
        img_bg.setOnTouchListener(multiTouchListener);
    }
}