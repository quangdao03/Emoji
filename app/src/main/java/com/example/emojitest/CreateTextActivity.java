package com.example.emojitest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.emojitest.adapter.BackgroundAdapter;
import com.example.emojitest.adapter.IconAdapter;
import com.example.emojitest.databinding.ActivityCreateTextBinding;
import com.example.emojitest.model.Background;
import com.example.emojitest.model.Icon;
import com.example.emojitest.stickerviewclass.StickerImageView;
import com.example.emojitest.textclass.TextImageView;
import com.example.emojitest.textclass.TextStickerView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CreateTextActivity extends AppCompatActivity {

    RecyclerView rcy_bg;
    com.example.emojitest.adapter.BackgroundAdapter BackgroundAdapter;
    ArrayList<Background> backgroundList = new ArrayList<>();
    ActivityCreateTextBinding binding;
    ArrayList<Integer> stickerviewId = new ArrayList<>();
    public static Bitmap editTextBitmap;
    EditText edit_text;
    ImageView back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCreateTextBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        backgroundList = listSticker1("backgroundcolor", "backgroundcolor/", backgroundList);
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
            binding
            Button cancel,ok;
            final Dialog dialog = new Dialog(CreateTextActivity.this);
            dialog.setContentView(R.layout.layout_dialog_text);
            dialog.getWindow().setGravity(Gravity.CENTER);
            dialog.getWindow().setLayout(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.setCancelable(false);
            cancel = dialog.findViewById(R.id.cancel);
            ok = dialog.findViewById(R.id.ok);
            edit_text = dialog.findViewById(R.id.edit_text);
            cancel.setOnClickListener(v -> {
                dialog.dismiss();
            });
            ok.setOnClickListener(v -> {
                if (edit_text.getText().toString().isEmpty() || edit_text.getText().toString().matches("^\\s*$")) {
                    Toast.makeText(CreateTextActivity.this, "" + getText(R.string.text_isemty), Toast.LENGTH_SHORT).show();
                } else {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
                    editTextBitmap = getMainFrameBitmap();
                    addtext(editTextBitmap);
                    dialog.dismiss();
                }
            });
            dialog.show();
        });
        binding.rlBg.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                removeBorder();
                return false;
            }
        });
    }

    private void getBackground() {
        GridLayoutManager linearLayoutManager = new GridLayoutManager( this,6, RecyclerView. VERTICAL, false);
        rcy_bg.setLayoutManager (linearLayoutManager);
        rcy_bg.setHasFixedSize(true);
        BackgroundAdapter = new BackgroundAdapter(this, new BackgroundAdapter.iClickListener() {
            @Override
            public void onClickItem(Background background) {
                Glide.with(CreateTextActivity.this).load(background.getBg()).into(binding.imageColor);
            }
        });
        rcy_bg.setAdapter(BackgroundAdapter);
        BackgroundAdapter.addAll(backgroundList);
    }
    public ArrayList<Background> listSticker1(String dirFrom, String path, ArrayList<Background> emojiLists) {

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
                emojiLists.add(new Background("file:///android_asset/" + path + fileList[i], fileList[i]));
            }
        }
        return emojiLists;
    }
    private void addtext(Bitmap bitmap) {
        final TextImageView textSticker = new TextImageView(CreateTextActivity.this, onTouchSticker1);
        textSticker.setImageBitmap(bitmap);
        int size = convertDpToPixel(100, CreateTextActivity.this);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                size,
                size
        );
        params.addRule(RelativeLayout.CENTER_IN_PARENT);
        textSticker.setLayoutParams(params);
        Random r = new Random();
        int view_id = r.nextInt();
        if (view_id < 0) {
            view_id = view_id - (view_id * 2);
        }
        textSticker.setId(view_id);
        stickerviewId.add(view_id);
        textSticker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textSticker.setControlItemsHidden(true);
            }
        });
        binding.rlBg.addView(textSticker);

    }
    public TextStickerView.OnTouchSticker onTouchSticker1 = new TextStickerView.OnTouchSticker() {
        @Override
        public void onTouchedSticker() {
            removeBorder();
        }
    };
    private void removeBorder() {

        for (int i = 0; i < stickerviewId.size(); i++) {
            View view = binding.rlBg.findViewById(stickerviewId.get(i));
            if (view instanceof TextStickerView) {
                TextStickerView textStickerView = (TextStickerView) view;
                textStickerView.setControlItemsHidden(true);
            }
        }
    }
    private Bitmap getMainFrameBitmap() {
        edit_text.setCursorVisible(false);
        edit_text.setDrawingCacheEnabled(true);
        edit_text.buildDrawingCache();

        Bitmap bitmap = Bitmap.createBitmap(edit_text.getDrawingCache());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            bitmap.setConfig(Bitmap.Config.ARGB_8888);
        }
        edit_text.setDrawingCacheEnabled(false);
        Bitmap bmp = bitmap;

        int imgHeight = bmp.getHeight();
        int imgWidth = bmp.getWidth();
        int smallX = 0, largeX = imgWidth, smallY = 0, largeY = imgHeight;
        int left = imgWidth, right = imgWidth, top = imgHeight, bottom = imgHeight;
        for (int i = 0; i < imgWidth; i++) {
            for (int j = 0; j < imgHeight; j++) {
                if (bmp.getPixel(i, j) != Color.TRANSPARENT) {
                    if ((i - smallX) < left) {
                        left = (i - smallX);
                    }
                    if ((largeX - i) < right) {
                        right = (largeX - i);
                    }
                    if ((j - smallY) < top) {
                        top = (j - smallY);
                    }
                    if ((largeY - j) < bottom) {
                        bottom = (largeY - j);
                    }
                }
            }
        }
        bmp = Bitmap.createBitmap(bmp, left, top, imgWidth - left - right, imgHeight - top - bottom);
        return bmp;
    }
    private static int convertDpToPixel(float dp, Context context) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float px = dp * (metrics.densityDpi / 160f);
        return (int) px;
    }
}