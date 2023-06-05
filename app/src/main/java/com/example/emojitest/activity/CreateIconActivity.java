package com.example.emojitest.activity;

import android.Manifest;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.emojitest.MultiTouchTool.MultiTouchListener;
import com.example.emojitest.R;
import com.example.emojitest.adapter.BackgroundIconAdapter;
import com.example.emojitest.adapter.IconAdapter;
import com.example.emojitest.adapter.IconAdditionAdapter;
import com.example.emojitest.adapter.IconBreadAdapter;
import com.example.emojitest.adapter.IconBrowAdapter;
import com.example.emojitest.adapter.IconEyeAdapter;
import com.example.emojitest.adapter.IconGlassAdapter;
import com.example.emojitest.adapter.IconHairAdapter;
import com.example.emojitest.adapter.IconMouthAdapter;
import com.example.emojitest.adapter.IconNoseAdapter;
import com.example.emojitest.adapter.IconPlanAdapter;
import com.example.emojitest.databinding.ActivityCreateIconBinding;
import com.example.emojitest.model.Icon;
import com.example.emojitest.stickerviewclass.StickerImageView;
import com.example.emojitest.stickerviewclass.StickerView;
import com.example.emojitest.util.SavingUtils;
import com.example.emojitest.util.ViewState;
import com.flask.colorpicker.ColorPickerView;
import com.flask.colorpicker.OnColorSelectedListener;
import com.flask.colorpicker.builder.ColorPickerClickListener;
import com.flask.colorpicker.builder.ColorPickerDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

import jp.wasabeef.blurry.Blurry;

public class CreateIconActivity extends AppCompatActivity {
    RecyclerView rcy_icon;
    IconAdapter iconAdapter;
    IconPlanAdapter iconPlanAdapter;
    IconEyeAdapter iconEyeAdapter;
    IconBrowAdapter iconBrowAdapter;
    IconMouthAdapter iconMouthAdapter;
    IconAdditionAdapter iconAdditionAdapter;
    IconNoseAdapter iconNoseAdapter;
    IconBreadAdapter iconBreadAdapter;
    IconHairAdapter iconHairAdapter;
    IconGlassAdapter iconGlassAdapter;
    BackgroundIconAdapter backgroundIconAdapter;
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
    ArrayList<Icon> iconArrayListBackground = new ArrayList<>();

    ActivityCreateIconBinding binding;
    private StickerImageView sticker;
    private StickerImageView sticker1;
    private StickerImageView stickermouth;
    private StickerImageView stickergesture;
    private StickerImageView stickernose;
    private StickerImageView stickerbread;
    private StickerImageView stickerhair;
    private StickerImageView stickerglass;

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


    private String delete = "";
    View selectedView = null;
    TextView tv_toolbar;
    Animation fabOpen, fabClose, rotateForward, rotateBackForWard;
    FloatingActionButton fab, fab1, camera, color;
    boolean isOpen = false;
    public static RelativeLayout rl_image;
    public static ImageView rl_view;
    private static final int CAMERA_REQUEST_CODE = 101;
    private static final int IMAGE_PICK_CAMERA_CODE = 102;
    private String[] cameraPermissions;
    Uri image_uri;
    String path;
    TextView tv_export;
    Dialog dialog;
    public static SeekBar seekBar;
    int selectedPosition = -1;
    boolean isFlipped = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCreateIconBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        cameraPermissions = new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
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
        iconArrayListBackground = listSticker1("background_icon", "background_icon/", iconArrayListBackground);
        back = findViewById(R.id.back);
        rl_view = findViewById(R.id.rl_view);
        rl_image = findViewById(R.id.rl_image);
        seekBar = findViewById(R.id.seek_bar);
        tv_toolbar = findViewById(R.id.tv_toolbar);
        rcy_icon = findViewById(R.id.rcy_icon);
        img_bg = findViewById(R.id.img_bg);
        tv_export = findViewById(R.id.tv_export);
        getIcon();
        binding.iconBg.setImageResource(R.drawable.ic_bg_choose);
        onclickItem();
        binding.rcyIcon.setVisibility(View.VISIBLE);
        binding.rcyBackground.setVisibility(View.GONE);
        fab = (FloatingActionButton) findViewById(R.id.add);
        fab1 = (FloatingActionButton) findViewById(R.id.album);
        camera = (FloatingActionButton) findViewById(R.id.camera);
        color = (FloatingActionButton) findViewById(R.id.color);
        fabOpen = AnimationUtils.loadAnimation(this, R.anim.fab_open);
        fabClose = AnimationUtils.loadAnimation(this, R.anim.fab_close);
        rotateForward = AnimationUtils.loadAnimation(this, R.anim.rotate_forward);
        rotateBackForWard = AnimationUtils.loadAnimation(this, R.anim.rotate_backforward);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                animateFab();
                fab1.setVisibility(View.VISIBLE);
                camera.setVisibility(View.VISIBLE);
                color.setVisibility(View.VISIBLE);
            }
        });
        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CreateIconActivity.this, LoadImageScreenBGActivity.class));
                animateFab();
            }
        });
        camera.setOnClickListener(view -> {
            if (checkCameraPermission()) {
                ImagePickFromCamera();
            } else {
                requestCameraPermission();
            }
            animateFab();
        });
        color.setOnClickListener(view -> {
            animateFab();
            SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
            ColorPickerDialogBuilder.with(CreateIconActivity.this).setTitle(R.string.choose).wheelType(ColorPickerView.WHEEL_TYPE.FLOWER).density(12).initialColor(sharedPreferences.getInt("background_color", Color.WHITE)).setOnColorSelectedListener(new OnColorSelectedListener() {
                @Override
                public void onColorSelected(int selectedColor) {

                }
            }).setPositiveButton("" + getText(R.string.Ok), new ColorPickerClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int selectedColor, Integer[] allColors) {
                    Drawable drawable = new ColorDrawable(selectedColor);
                    rl_view.setImageDrawable(drawable);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putInt("background_color", selectedColor);
                    editor.apply();
                    binding.rlSeekbar.setVisibility(View.VISIBLE);
                }
            }).setNegativeButton("" + getText(R.string.Cancel), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            }).build().show();
        });
    }

    private boolean checkCameraPermission() {
        boolean result = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == (PackageManager.PERMISSION_GRANTED);
        boolean result1 = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == (PackageManager.PERMISSION_GRANTED);
        return result && result1;

    }

    private void requestCameraPermission() {
        ActivityCompat.requestPermissions(this, cameraPermissions, CAMERA_REQUEST_CODE);
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
                rl_view.setImageURI(image_uri);
                binding.rlSeekbar.setVisibility(View.VISIBLE);
            }
        }
    }

    private void animateFab() {
        if (isOpen) {
            fab.startAnimation(rotateBackForWard);
            fab1.startAnimation(fabClose);
            camera.startAnimation(fabClose);
            color.startAnimation(fabClose);
            fab1.setClickable(false);
            camera.setClickable(false);
            color.setClickable(false);
            isOpen = false;
            fab.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.white)));
            fab.setImageResource(R.drawable.add_float);
        } else {
            fab.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.color_DE3730)));
            fab.setImageResource(R.drawable.add_float_white);
            fab.startAnimation(rotateForward);
            fab1.startAnimation(fabOpen);
            camera.startAnimation(fabOpen);
            color.startAnimation(fabOpen);
            fab1.setClickable(true);
            camera.setClickable(true);
            color.setClickable(true);
            isOpen = true;
        }
    }


    private void onclickItem() {
        tv_export.setOnClickListener(view -> {
            new saveAndGo().execute(new Void[0]);
        });
        tv_toolbar.setText(getText(R.string.new_emoji));
        back.setOnClickListener(view -> {
            onBackPressed();
            finish();
        });
        binding.seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int blurRadius = progress;
                Blurry.with(CreateIconActivity.this)
                        .radius(blurRadius)
                        .capture(rl_view)
                        .into(rl_view);
                Log.d("process",""+progress);


            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
        binding.rlImage.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                removeBorder();
                return false;
            }
        });
        binding.iconEye.setOnClickListener(v -> {
            binding.rcyIcon.setVisibility(View.VISIBLE);
            binding.rcyBackground.setVisibility(View.GONE);
            binding.lnFab.setVisibility(View.GONE);
            getDefaultIcon();
            getIconEye();
            delete = "eye";
            binding.iconEye.setImageResource(R.drawable.ic_eye_selected);
            binding.rlSeekbar.setVisibility(View.GONE);
        });
        binding.iconBg.setOnClickListener(v -> {
            binding.rcyIcon.setVisibility(View.VISIBLE);
            binding.rcyBackground.setVisibility(View.GONE);
            binding.lnFab.setVisibility(View.GONE);
            getDefaultIcon();
            getIcon();
            binding.iconBg.setImageResource(R.drawable.ic_bg_choose);
            binding.rlSeekbar.setVisibility(View.GONE);
        });
        binding.iconEyebrow.setOnClickListener(v -> {
            binding.rcyIcon.setVisibility(View.VISIBLE);
            binding.rcyBackground.setVisibility(View.GONE);
            binding.lnFab.setVisibility(View.GONE);
            getDefaultIcon();
            getEyeBrow();
            delete = "eye_brow";
            binding.iconEyebrow.setImageResource(R.drawable.ic_eye_brow_selected);
            binding.rlSeekbar.setVisibility(View.GONE);
        });
        binding.iconMouth.setOnClickListener(v -> {
            binding.rcyIcon.setVisibility(View.VISIBLE);
            binding.rcyBackground.setVisibility(View.GONE);
            binding.lnFab.setVisibility(View.GONE);
            getDefaultIcon();
            getMouth();
            delete = "mouth";
            binding.iconMouth.setImageResource(R.drawable.ic_mouth_selected);
            binding.rlSeekbar.setVisibility(View.GONE);
        });
        binding.iconAddition.setOnClickListener(v -> {
            binding.rcyIcon.setVisibility(View.VISIBLE);
            binding.rcyBackground.setVisibility(View.GONE);
            binding.lnFab.setVisibility(View.GONE);
            getDefaultIcon();
            delete = "gesture";
            getAddition();
            binding.iconAddition.setImageResource(R.drawable.ic_addtion_selected);
            binding.rlSeekbar.setVisibility(View.GONE);
        });
        binding.iconNose.setOnClickListener(view -> {
            binding.rcyIcon.setVisibility(View.VISIBLE);
            binding.rcyBackground.setVisibility(View.GONE);
            binding.lnFab.setVisibility(View.GONE);
            getDefaultIcon();
            delete = "nose";
            getNose();
            binding.iconNose.setImageResource(R.drawable.ic_nose_selected);
            binding.rlSeekbar.setVisibility(View.GONE);
        });
        binding.iconBeard.setOnClickListener(view -> {
            binding.rcyIcon.setVisibility(View.VISIBLE);
            binding.rcyBackground.setVisibility(View.GONE);
            binding.lnFab.setVisibility(View.GONE);
            getDefaultIcon();
            getBread();
            delete = "bread";
            binding.iconBeard.setImageResource(R.drawable.ic_bread_selected);
            binding.rlSeekbar.setVisibility(View.GONE);
        });
        binding.iconHair.setOnClickListener(view -> {
            binding.rcyIcon.setVisibility(View.VISIBLE);
            binding.rcyBackground.setVisibility(View.GONE);
            binding.lnFab.setVisibility(View.GONE);
            getDefaultIcon();
            getHair();
            delete = "hair";
            binding.iconHair.setImageResource(R.drawable.ic_hair_selected);
            binding.rlSeekbar.setVisibility(View.GONE);
        });
        binding.iconGlass.setOnClickListener(view -> {
            binding.rcyIcon.setVisibility(View.VISIBLE);
            binding.rcyBackground.setVisibility(View.GONE);
            binding.lnFab.setVisibility(View.GONE);
            getDefaultIcon();
            getGlass();
            delete = "glass";
            binding.iconGlass.setImageResource(R.drawable.ic_glass_selected);
            binding.rlSeekbar.setVisibility(View.GONE);

        });
        binding.iconAlbum.setOnClickListener(view -> {
            binding.rcyIcon.setVisibility(View.GONE);
            binding.rcyBackground.setVisibility(View.VISIBLE);
            getDefaultIcon();
            binding.iconAlbum.setImageResource(R.drawable.ic_album_selected);
            getBackground();
            binding.lnFab.setVisibility(View.VISIBLE);
            Drawable thumb1 = ContextCompat.getDrawable(this, R.drawable.ic_seek_bar);
            binding.seekBar.setThumb(thumb1);
        });
        binding.iconIcon.setOnClickListener(view -> {
            binding.rcyIcon.setVisibility(View.VISIBLE);
            binding.rcyBackground.setVisibility(View.GONE);
            binding.lnFab.setVisibility(View.GONE);
            getDefaultIcon();
            binding.iconIcon.setImageResource(R.drawable.ic_choose_selected);
            getIconChoose();
            binding.rlSeekbar.setVisibility(View.GONE);


        });
        binding.iconAdd.setOnClickListener(view -> {
            getDefaultIcon();
            binding.iconAdd.setImageResource(R.drawable.customize_face);
            startActivity(new Intent(CreateIconActivity.this, CustomizeSmiley.class));
        });
        binding.delete.setOnClickListener(v -> {

            if (delete.equals("eye")) {
                if (sticker == null) {
                    Toast.makeText(this, "Please select Icon to delete", Toast.LENGTH_SHORT).show();
                } else {
                    if (sticker.getParent() != null) {
                        ViewGroup myCanvas = ((ViewGroup) sticker.getParent());
                        myCanvas.removeView(sticker);
                    }
                }
            } else if (delete.equals("eye_brow")) {
                if (sticker1 == null) {
                    Toast.makeText(this, "Please select Icon to delete", Toast.LENGTH_SHORT).show();
                } else {
                    if (sticker1.getParent() != null) {
                        ViewGroup myCanvas = ((ViewGroup) sticker1.getParent());
                        myCanvas.removeView(sticker1);
                    }
                }
            } else if (delete.equals("mouth")) {
                if (stickermouth == null) {
                    Toast.makeText(this, "Please select Icon to delete", Toast.LENGTH_SHORT).show();
                } else {
                    if (stickermouth.getParent() != null) {
                        ViewGroup myCanvas = ((ViewGroup) stickermouth.getParent());
                        myCanvas.removeView(stickermouth);
                    }
                }
            } else if (delete.equals("gesture")) {
                if (stickergesture == null) {
                    Toast.makeText(this, "Please select Icon to delete", Toast.LENGTH_SHORT).show();
                } else {
                    if (stickergesture.getParent() != null) {
                        ViewGroup myCanvas = ((ViewGroup) stickergesture.getParent());
                        myCanvas.removeView(stickergesture);
                    }
                }
            } else if (delete.equals("nose")) {
                if (stickernose == null) {
                    Toast.makeText(this, "Please select Icon to delete", Toast.LENGTH_SHORT).show();
                } else {
                    if (stickernose.getParent() != null) {
                        ViewGroup myCanvas = ((ViewGroup) stickernose.getParent());
                        myCanvas.removeView(stickernose);
                    }
                }
            } else if (delete.equals("bread")) {
                if (stickerbread == null) {
                    Toast.makeText(this, "Please select Icon to delete", Toast.LENGTH_SHORT).show();
                } else {
                    if (stickerbread.getParent() != null) {
                        ViewGroup myCanvas = ((ViewGroup) stickerbread.getParent());
                        myCanvas.removeView(stickerbread);
                    }
                }
            } else if (delete.equals("hair")) {
                if (stickerbread == null) {
                    Toast.makeText(this, "Please select Icon to delete", Toast.LENGTH_SHORT).show();
                } else {
                    if (stickerbread.getParent() != null) {
                        ViewGroup myCanvas = ((ViewGroup) stickerbread.getParent());
                        myCanvas.removeView(stickerbread);
                    }
                }
            } else if (delete.equals("glass")) {
                if (stickerglass == null) {
                    Toast.makeText(this, "Please select Icon to delete", Toast.LENGTH_SHORT).show();
                } else {
                    if (stickerglass.getParent() != null) {
                        ViewGroup myCanvas = ((ViewGroup) stickerglass.getParent());
                        myCanvas.removeView(stickerglass);
                    }
                }
            } else {
                Toast.makeText(this, "please add sticker to remove", Toast.LENGTH_SHORT).show();
            }
        });
        binding.imgReset.setOnClickListener(view -> {
            if (sticker == null && sticker1 == null && stickermouth == null && stickernose == null && stickergesture == null && stickerbread == null && stickerhair == null && stickerglass == null) {
                Toast.makeText(this, "please add sticker to remove", Toast.LENGTH_SHORT).show();
            } else {
                Button cancel, ok;
                final Dialog dialog = new Dialog(CreateIconActivity.this);
                dialog.setContentView(R.layout.layout_dialog_reset);
                dialog.getWindow().setGravity(Gravity.CENTER);
                dialog.getWindow().setLayout(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.setCancelable(false);
                cancel = dialog.findViewById(R.id.cancel);
                ok = dialog.findViewById(R.id.ok);
                cancel.setOnClickListener(view1 -> {
                    dialog.dismiss();
                });
                ok.setOnClickListener(view1 -> {
                    if (sticker != null && sticker.getParent() != null) {
                        ViewGroup myCanvas = (ViewGroup) sticker.getParent();
                        myCanvas.removeView(sticker);
                    }
                    if (sticker1 != null && sticker1.getParent() != null) {
                        ViewGroup myCanvas1 = (ViewGroup) sticker1.getParent();
                        myCanvas1.removeView(sticker1);
                    }
                    if (stickermouth != null && stickermouth.getParent() != null) {
                        ViewGroup myCanvas2 = (ViewGroup) stickermouth.getParent();
                        myCanvas2.removeView(stickermouth);
                    }
                    if (stickernose != null && stickernose.getParent() != null) {
                        ViewGroup myCanvas3 = (ViewGroup) stickernose.getParent();
                        myCanvas3.removeView(stickernose);
                    }
                    if (stickergesture != null && stickergesture.getParent() != null) {
                        ViewGroup myCanvas4 = (ViewGroup) stickergesture.getParent();
                        myCanvas4.removeView(stickergesture);
                    }
                    if (stickerglass != null && stickerglass.getParent() != null) {
                        ViewGroup myCanvas4 = (ViewGroup) stickerglass.getParent();
                        myCanvas4.removeView(stickerglass);
                    }
                    if (stickerhair != null && stickerhair.getParent() != null) {
                        ViewGroup myCanvas4 = (ViewGroup) stickerhair.getParent();
                        myCanvas4.removeView(stickerhair);
                    }
                    if (stickerbread != null && stickerbread.getParent() != null) {
                        ViewGroup myCanvas4 = (ViewGroup) stickerbread.getParent();
                        myCanvas4.removeView(stickerbread);
                    }
                    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(CreateIconActivity.this);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putInt("selected_position", selectedPosition);
                    editor.putInt("selected_position_plan", selectedPosition);
                    editor.putInt("selected_position_nose", selectedPosition);
                    editor.putInt("selected_position_mouth", selectedPosition);
                    editor.putInt("selected_position_hair", selectedPosition);
                    editor.putInt("selected_position_glass", selectedPosition);
                    editor.putInt("selected_position_eye", selectedPosition);
                    editor.putInt("selected_position_brow", selectedPosition);
                    editor.putInt("selected_position_bread", selectedPosition);
                    editor.putInt("selected_position_addition", selectedPosition);
                    editor.apply();
                    dialog.dismiss();
                });
                dialog.show();
            }


        });
        binding.flipHozi.setOnClickListener(view -> {
            if (sticker == null && sticker1 == null && stickermouth == null && stickernose == null && stickergesture == null && stickerbread == null && stickerhair == null && stickerglass == null) {
                Toast.makeText(this, "please add sticker to remove", Toast.LENGTH_SHORT).show();
            } else {
                if (delete.equals("eye")) {
                    if (sticker == null) {
                        Toast.makeText(this, "please add sticker to flip", Toast.LENGTH_SHORT).show();
                    } else {
                        View mainView = sticker;
                        mainView.setRotationY(isFlipped ? 0f : -180f);
                        mainView.invalidate();
                        sticker.requestLayout();

                        isFlipped = !isFlipped;
                    }
                }
                if (delete.equals("eye_brow")) {
                    if (sticker1 == null) {
                        Toast.makeText(this, "please add sticker to flip", Toast.LENGTH_SHORT).show();

                    } else {
                        View mainView = sticker1;
                        mainView.setRotationY(isFlipped ? 0f : -180f);
                        mainView.invalidate();
                        sticker1.requestLayout();

                        isFlipped = !isFlipped;
                    }
                }
                if (delete.equals("mouth")) {
                    if (stickermouth == null) {
                        Toast.makeText(this, "please add sticker to flip", Toast.LENGTH_SHORT).show();
                    } else {
                        View mainView = stickermouth;
                        mainView.setRotationY(isFlipped ? 0f : -180f);
                        mainView.invalidate();
                        stickermouth.requestLayout();

                        isFlipped = !isFlipped;
                    }
                }
                if (delete.equals("gesture")) {
                    if (stickergesture == null) {
                        Toast.makeText(this, "please add sticker to flip", Toast.LENGTH_SHORT).show();
                    } else {
                        View mainView = stickergesture;
                        mainView.setRotationY(isFlipped ? 0f : -180f);
                        mainView.invalidate();
                        stickergesture.requestLayout();

                        isFlipped = !isFlipped;
                    }
                }
                if (delete.equals("nose")) {
                    if (stickernose == null) {
                        Toast.makeText(this, "please add sticker to flip", Toast.LENGTH_SHORT).show();
                    } else {
                        View mainView = stickernose;
                        mainView.setRotationY(isFlipped ? 0f : -180f);
                        mainView.invalidate();
                        stickernose.requestLayout();

                        isFlipped = !isFlipped;
                    }
                }
                if (delete.equals("bread")) {
                    if (stickerbread == null) {
                        Toast.makeText(this, "please add sticker to flip", Toast.LENGTH_SHORT).show();
                    } else {
                        View mainView = stickerbread;
                        mainView.setRotationY(isFlipped ? 0f : -180f);
                        mainView.invalidate();
                        stickerbread.requestLayout();

                        isFlipped = !isFlipped;
                    }
                }
                if (delete.equals("hair")) {
                    if (stickerhair == null) {
                        Toast.makeText(this, "please add sticker to flip", Toast.LENGTH_SHORT).show();
                    } else {
                        View mainView = stickerhair;
                        mainView.setRotationY(isFlipped ? 0f : -180f);
                        mainView.invalidate();
                        stickerhair.requestLayout();

                        isFlipped = !isFlipped;
                    }
                }
                if (delete.equals("glass")) {
                    if (stickerglass == null) {
                        Toast.makeText(this, "please add sticker to flip", Toast.LENGTH_SHORT).show();
                    } else {
                        View mainView = stickerglass;
                        mainView.setRotationY(isFlipped ? 0f : -180f);
                        mainView.invalidate();
                        stickerglass.requestLayout();

                        isFlipped = !isFlipped;
                    }
                }
            }

        });
        binding.flipVertical.setOnClickListener(view -> {

            if (sticker == null && sticker1 == null && stickermouth == null && stickernose == null && stickergesture == null && stickerbread == null && stickerhair == null && stickerglass == null) {
                Toast.makeText(this, "please add sticker to remove", Toast.LENGTH_SHORT).show();
            } else {
                if (delete.equals("eye")) {
                    if (sticker == null) {
                        Toast.makeText(this, "please add sticker to flip", Toast.LENGTH_SHORT).show();
                    } else {
                        View mainView = sticker;
                        mainView.setRotationX(isFlipped ? 0f : -180f);
                        mainView.invalidate();
                        sticker.requestLayout();

                        isFlipped = !isFlipped;
                    }
                }
                if (delete.equals("eye_brow")) {
                    if (sticker1 == null) {
                        Toast.makeText(this, "please add sticker to flip", Toast.LENGTH_SHORT).show();

                    } else {
                        View mainView = sticker1;
                        mainView.setRotationX(isFlipped ? 0f : -180f);
                        mainView.invalidate();
                        sticker1.requestLayout();

                        isFlipped = !isFlipped;
                    }
                }
                if (delete.equals("mouth")) {
                    if (stickermouth == null) {
                        Toast.makeText(this, "please add sticker to flip", Toast.LENGTH_SHORT).show();
                    } else {
                        View mainView = stickermouth;
                        mainView.setRotationX(isFlipped ? 0f : -180f);
                        mainView.invalidate();
                        stickermouth.requestLayout();

                        isFlipped = !isFlipped;
                    }
                }
                if (delete.equals("gesture")) {
                    if (stickergesture == null) {
                        Toast.makeText(this, "please add sticker to flip", Toast.LENGTH_SHORT).show();
                    } else {
                        View mainView = stickergesture;
                        mainView.setRotationX(isFlipped ? 0f : -180f);
                        mainView.invalidate();
                        stickergesture.requestLayout();

                        isFlipped = !isFlipped;
                    }
                }
                if (delete.equals("nose")) {
                    if (stickernose == null) {
                        Toast.makeText(this, "please add sticker to flip", Toast.LENGTH_SHORT).show();
                    } else {
                        View mainView = stickernose;
                        mainView.setRotationX(isFlipped ? 0f : -180f);
                        mainView.invalidate();
                        stickernose.requestLayout();

                        isFlipped = !isFlipped;
                    }
                }
                if (delete.equals("bread")) {
                    if (stickerbread == null) {
                        Toast.makeText(this, "please add sticker to flip", Toast.LENGTH_SHORT).show();
                    } else {
                        View mainView = stickerbread;
                        mainView.setRotationX(isFlipped ? 0f : -180f);
                        mainView.invalidate();
                        stickerbread.requestLayout();

                        isFlipped = !isFlipped;
                    }
                }
                if (delete.equals("hair")) {
                    if (stickerhair == null) {
                        Toast.makeText(this, "please add sticker to flip", Toast.LENGTH_SHORT).show();
                    } else {
                        View mainView = stickerhair;
                        mainView.setRotationX(isFlipped ? 0f : -180f);
                        mainView.invalidate();
                        stickerhair.requestLayout();

                        isFlipped = !isFlipped;
                    }
                }
                if (delete.equals("glass")) {
                    if (stickerglass == null) {
                        Toast.makeText(this, "please add sticker to flip", Toast.LENGTH_SHORT).show();
                    } else {
                        View mainView = stickerglass;
                        mainView.setRotationX(isFlipped ? 0f : -180f);
                        mainView.invalidate();
                        stickerglass.requestLayout();

                        isFlipped = !isFlipped;
                    }
                }
            }
        });
    }

    private void getIconChoose() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        int selectedPosition = preferences.getInt("selected_position_plan", -1);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(CreateIconActivity.this, 6, GridLayoutManager.VERTICAL, false);

        rcy_icon.setLayoutManager(gridLayoutManager);
        rcy_icon.setHasFixedSize(true);

        iconPlanAdapter = new IconPlanAdapter(CreateIconActivity.this, new IconPlanAdapter.iClickListener() {
            @Override
            public void onClickItem(Icon icon) {
                Glide.with(CreateIconActivity.this).load(Uri.parse(icon.getStickerpath())).into(img_bg);
            }
        }, selectedPosition);
        rcy_icon.setAdapter(iconPlanAdapter);
        iconPlanAdapter.addAll(iconArrayListIcon);
        rcy_icon.scrollToPosition(selectedPosition);
    }

    private void getGlass() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        int selectedPosition = preferences.getInt("selected_position_glass", -1);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(CreateIconActivity.this, 6, GridLayoutManager.VERTICAL, false);

        rcy_icon.setLayoutManager(gridLayoutManager);
        rcy_icon.setHasFixedSize(true);

        iconGlassAdapter = new IconGlassAdapter(CreateIconActivity.this, new IconGlassAdapter.iClickListener() {
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
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(size, size);
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
        }, selectedPosition);
        rcy_icon.setAdapter(iconGlassAdapter);
        iconGlassAdapter.addAll(iconArrayListGlass);
        rcy_icon.scrollToPosition(selectedPosition);
    }

    private void getHair() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        int selectedPosition = preferences.getInt("selected_position_hair", -1);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(CreateIconActivity.this, 6, GridLayoutManager.VERTICAL, false);

        rcy_icon.setLayoutManager(gridLayoutManager);
        rcy_icon.setHasFixedSize(true);

        iconHairAdapter = new IconHairAdapter(CreateIconActivity.this, new IconHairAdapter.iClickListener() {
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
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(size, size);
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
        }, selectedPosition);
        rcy_icon.setAdapter(iconHairAdapter);
        iconHairAdapter.addAll(iconArrayListHair);
        rcy_icon.scrollToPosition(selectedPosition);
    }

    private void getBread() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        int selectedPosition = preferences.getInt("selected_position_bread", -1);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(CreateIconActivity.this, 6, GridLayoutManager.VERTICAL, false);

        rcy_icon.setLayoutManager(gridLayoutManager);
        rcy_icon.setHasFixedSize(true);

        iconBreadAdapter = new IconBreadAdapter(CreateIconActivity.this, new IconBreadAdapter.iClickListener() {
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
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(size, size);
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
        }, selectedPosition);
        rcy_icon.setAdapter(iconBreadAdapter);
        iconBreadAdapter.addAll(iconArrayListBread);
        rcy_icon.scrollToPosition(selectedPosition);
    }

    private void getNose() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        int selectedPosition = preferences.getInt("selected_position_nose", -1);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(CreateIconActivity.this, 6, GridLayoutManager.VERTICAL, false);

        rcy_icon.setLayoutManager(gridLayoutManager);
        rcy_icon.setHasFixedSize(true);

        iconNoseAdapter = new IconNoseAdapter(CreateIconActivity.this, new IconNoseAdapter.iClickListener() {
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
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(size, size);
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
        }, selectedPosition);
        rcy_icon.setAdapter(iconNoseAdapter);
        iconNoseAdapter.addAll(iconArrayListNose);
        rcy_icon.scrollToPosition(selectedPosition);
    }

    private void getAddition() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        int selectedPosition = preferences.getInt("selected_position_addition", -1);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(CreateIconActivity.this, 6, GridLayoutManager.VERTICAL, false);

        rcy_icon.setLayoutManager(gridLayoutManager);
        rcy_icon.setHasFixedSize(true);

        iconAdditionAdapter = new IconAdditionAdapter(CreateIconActivity.this, new IconAdditionAdapter.iClickListener() {
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
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(size, size);
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
        }, selectedPosition);
        rcy_icon.setAdapter(iconAdditionAdapter);
        iconAdditionAdapter.addAll(iconArrayListGesture);
        rcy_icon.scrollToPosition(selectedPosition);
    }

    private void getMouth() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        int selectedPosition = preferences.getInt("selected_position_mouth", -1);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(CreateIconActivity.this, 6, GridLayoutManager.VERTICAL, false);

        rcy_icon.setLayoutManager(gridLayoutManager);
        rcy_icon.setHasFixedSize(true);

        iconMouthAdapter = new IconMouthAdapter(CreateIconActivity.this, new IconMouthAdapter.iClickListener() {
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
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(size, size);
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
        }, selectedPosition);
        rcy_icon.setAdapter(iconMouthAdapter);
        iconMouthAdapter.addAll(iconArrayListMouth);
        rcy_icon.scrollToPosition(selectedPosition);
    }

    private void getEyeBrow() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        int selectedPosition = preferences.getInt("selected_position_brow", -1);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(CreateIconActivity.this, 6, GridLayoutManager.VERTICAL, false);

        rcy_icon.setLayoutManager(gridLayoutManager);
        rcy_icon.setHasFixedSize(true);

        iconBrowAdapter = new IconBrowAdapter(CreateIconActivity.this, new IconBrowAdapter.iClickListener() {
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
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(size, size);
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
        }, selectedPosition);
        rcy_icon.setAdapter(iconBrowAdapter);
        iconBrowAdapter.addAll(iconArrayListEyeBrow);
        rcy_icon.scrollToPosition(selectedPosition);
    }

    private void getIconEye() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        int selectedPosition = preferences.getInt("selected_position_eye", -1);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(CreateIconActivity.this, 6, GridLayoutManager.VERTICAL, false);

        rcy_icon.setLayoutManager(gridLayoutManager);
        rcy_icon.setHasFixedSize(true);

        iconEyeAdapter = new IconEyeAdapter(CreateIconActivity.this, new IconEyeAdapter.iClickListener() {
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
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(size, size);
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
        }, selectedPosition);
        rcy_icon.setAdapter(iconEyeAdapter);
        iconEyeAdapter.addAll(iconArrayList1);
        rcy_icon.scrollToPosition(selectedPosition);

    }

    private void getIcon() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        int selectedPosition = preferences.getInt("selected_position", -1);
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
        }, selectedPosition);

        rcy_icon.setAdapter(iconAdapter);
        iconAdapter.addAll(iconArrayList);

    }

    private void getBackground() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        int selectedPosition = preferences.getInt("selected_position_background", -1);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(CreateIconActivity.this, 3, GridLayoutManager.VERTICAL, false);

        binding.rcyBackground.setLayoutManager(gridLayoutManager);
        binding.rcyBackground.setHasFixedSize(true);

        backgroundIconAdapter = new BackgroundIconAdapter(CreateIconActivity.this, new BackgroundIconAdapter.iClickListener() {
            @Override
            public void onClickItem(Icon icon) {
                Glide.with(CreateIconActivity.this).load(Uri.parse(icon.getStickerpath())).into(rl_view);
                binding.rlSeekbar.setVisibility(View.VISIBLE);
            }
        }, selectedPosition);

        binding.rcyBackground.setAdapter(backgroundIconAdapter);
        backgroundIconAdapter.addAll(iconArrayListBackground);
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    class saveAndGo extends AsyncTask<Void, Void, String> {

        saveAndGo() {
        }

        protected void onPreExecute() {
            dialog = new Dialog(CreateIconActivity.this);
            dialog.setContentView(R.layout.dialogloading);
            dialog.getWindow().setGravity(Gravity.CENTER);
            dialog.getWindow().setLayout(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.setCancelable(false);
            dialog.show();
        }

        protected String doInBackground(Void... dataArr) {

            return null;
        }

        protected void onPostExecute(String result) {
            path = storeImage(viewToBitmap());
            sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + path)));
            goSave();
        }


    }

    private void goSave() {
        Intent intent = new Intent(CreateIconActivity.this, ResultActivity.class);
        intent.putExtra("uri_path", path);
        startActivity(intent);
        finish();
    }

    private Bitmap viewToBitmap() {
        Bitmap bitmap;
        removeBorder();
        bitmap = Bitmap.createBitmap(binding.rlImage.getWidth(), binding.rlImage.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        binding.rlImage.draw(canvas);
        return bitmap;

    }

    private String storeImage(Bitmap image) {
        File pictureFile = getOutputMediaFile();
        if (pictureFile == null) {
            Log.e("save",
                    "Error creating media file, check storage permissions: ");
            return null;
        }
        try {
            FileOutputStream fos = new FileOutputStream(pictureFile);
            image.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.close();
        } catch (FileNotFoundException e) {
            Log.e("save1", "File not found: " + e.getMessage());
        } catch (IOException e) {
            Log.e("save2", "Error accessing file: " + e.getMessage());
        }
        return pictureFile.toString();
    }

    private File getOutputMediaFile() {
        File mediaStorageDir = new File(SavingUtils.getAppDir() + "/" + getResources().getString(R.string.app_name));

        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                return null;
            }
        }

        String timeStamp = new SimpleDateFormat("ddMMyyyy_HHmm").format(new Date());
        File mediaFile;
        String mImageName = "Emoji_Maker-" + timeStamp + ".jpg";

        // Kiểm tra xem file đã tồn tại chưa, nếu có thì thêm số đếm vào tên file
        int count = 1;
        while (new File(mediaStorageDir.getPath() + File.separator + mImageName).exists()) {
            mImageName = "Emoji_Maker-" + timeStamp + "_" + count + ".jpg";
            count++;
        }

        mediaFile = new File(mediaStorageDir.getPath() + File.separator + mImageName);
        return mediaFile;
    }
}