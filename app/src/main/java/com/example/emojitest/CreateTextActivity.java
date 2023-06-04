package com.example.emojitest;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.emojitest.adapter.BackgroundAdapter;
import com.example.emojitest.adapter.ColorCodeAdapter;
import com.example.emojitest.adapter.FontAdapter;
import com.example.emojitest.databinding.ActivityCreateTextBinding;
import com.example.emojitest.model.Background;
import com.example.emojitest.model.ObjColorCode;
import com.example.emojitest.model.ObjFont;
import com.example.emojitest.stickerviewclass.StickerImageView;
import com.example.emojitest.stickerviewclass.StickerView;
import com.example.emojitest.textclass.TextSticker;
import com.example.emojitest.textclass.TextStickerView;
import com.example.emojitest.util.ColorList;
import com.example.emojitest.util.SavingUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

public class CreateTextActivity extends AppCompatActivity {

    RecyclerView rcy_bg;
    com.example.emojitest.adapter.BackgroundAdapter BackgroundAdapter;
    ArrayList<Background> backgroundList = new ArrayList<>();
    ArrayList<Background> textIconList = new ArrayList<>();
    ArrayList<ObjColorCode> objColorCodes = new ArrayList<>();
    ArrayList<ObjColorCode> objColorCodesShadow = new ArrayList<>();
    ArrayList<ObjFont> objFont = new ArrayList<>();
    ActivityCreateTextBinding binding;
    ArrayList<Integer> stickerviewId = new ArrayList<>();
    public static Bitmap editTextBitmap;
    EditText edit_text;
    ImageView back;
    ColorCodeAdapter colorCodeAdapter;
    FontAdapter fontAdapter;
    TextSticker textSticker;
    private StickerImageView sticker;
    int view_id, flag = 0, flagVisibility = 0;
    View previousView = null;

    Bitmap bitmap;
    String path;
    TextView tv_export,tv_toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCreateTextBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        backgroundList = listSticker1("backgroundcolor", "backgroundcolor/", backgroundList);
        textIconList = listSticker1("text_icon", "text_icon/", textIconList);
        rcy_bg = findViewById(R.id.rcy_bg);
        tv_export = findViewById(R.id.tv_export);
        back = findViewById(R.id.back);
        tv_toolbar = findViewById(R.id.tv_toolbar);
        initView();
        getBackground();
        getFont();
        objColorCodes = ColorList.ObjColorCodeList();
        objColorCodesShadow = ColorList.ObjColorCodeListShadow();
    }

    private void initView() {
        tv_toolbar.setText(getText(R.string.new_text));
        tv_export.setOnClickListener(view -> {
            new saveAndGo().execute(new Void[0]);
        });
        back.setOnClickListener(view -> {
            onBackPressed();
        });
        binding.imgBackground.setOnClickListener(view -> {
            rcy_bg.setVisibility(View.VISIBLE);
            binding.rlTextControl.setVisibility(View.GONE);
            binding.imgFontColor.setImageResource(R.drawable.ic_text);
            binding.imgTextOk.setImageResource(R.drawable.ic_symbol);
            binding.imgBackground.setImageResource(R.drawable.ic_message);
            binding.igKeyboard.setImageResource(R.drawable.ic_keyboard);
            getBackground();
            binding.rcyTextIcon.setVisibility(View.GONE);
        });
        binding.imgFontColor.setOnClickListener(view -> {
            rcy_bg.setVisibility(View.GONE);
            binding.rlTextControl.setVisibility(View.VISIBLE);
            binding.imgFontColor.setImageResource(R.drawable.ic_text_yes);
            binding.imgTextOk.setImageResource(R.drawable.ic_symbol);
            binding.imgBackground.setImageResource(R.drawable.ic_selected_no);
            binding.igKeyboard.setImageResource(R.drawable.ic_keyboard);
            getColor();
            getShadowColor();
            binding.rcyTextIcon.setVisibility(View.GONE);
        });
        binding.imgTextOk.setOnClickListener(view -> {
            binding.rlTextControl.setVisibility(View.GONE);
            rcy_bg.setVisibility(View.GONE);
            binding.imgFontColor.setImageResource(R.drawable.ic_text);
            binding.imgTextOk.setImageResource(R.drawable.ic_symbol_on);
            binding.imgBackground.setImageResource(R.drawable.ic_selected_no);
            binding.igKeyboard.setImageResource(R.drawable.ic_keyboard);
            getTextIcon();
            binding.rcyTextIcon.setVisibility(View.VISIBLE);
        });
        binding.igKeyboard.setOnClickListener(view -> {
            binding.rcyTextIcon.setVisibility(View.GONE);
            binding.rlTextControl.setVisibility(View.GONE);
            rcy_bg.setVisibility(View.VISIBLE);
            binding.igKeyboard.setImageResource(R.drawable.ic_keyboard_on);
            binding.imgTextOk.setImageResource(R.drawable.ic_symbol);
            binding.imgFontColor.setImageResource(R.drawable.ic_text);
            binding.imgBackground.setImageResource(R.drawable.ic_selected_no);
            Button cancel, ok;
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
        Drawable thumb1 = ContextCompat.getDrawable(this, R.drawable.see_bar);
        binding.seeBar.setThumb(thumb1);
        int min = 0;
        int max = 50;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            binding.seeBar.setMin(min);
        }
        binding.seeBar.setMax(max);
        binding.seeBar.setProgress(16);
        final int minSize = convertDpToPixel(10, CreateTextActivity.this);
        final int maxSize = convertDpToPixel(50, CreateTextActivity.this);
        binding.seeBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if (textSticker == null) {
                    Toast.makeText(CreateTextActivity.this, "please add text", Toast.LENGTH_SHORT).show();
                } else {
                    int initialProgress = seekBar.getProgress();
                    int size = minSize + (int) ((maxSize - minSize) * (float) initialProgress / seekBar.getMax());

                    // Cập nhật kích thước của TextSticker
                    textSticker.setTextSize(size);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        binding.chooseFont.setOnClickListener(view -> {
            binding.chooseFont.setImageResource(R.drawable.ic_style_font_click);
            binding.chooseText.setImageResource(R.drawable.ic_text_scale_no);
            binding.rcyFont.setVisibility(View.VISIBLE);
            binding.rlFontSize.setVisibility(View.GONE);
            binding.rlColorText.setVisibility(View.GONE);
            binding.rlColorTextShadow.setVisibility(View.GONE);
            font();
        });
        binding.chooseText.setOnClickListener(view -> {
            binding.chooseFont.setImageResource(R.drawable.ic_style_font);
            binding.chooseText.setImageResource(R.drawable.ic_text_scale);
            binding.rcyFont.setVisibility(View.GONE);
            binding.rlFontSize.setVisibility(View.VISIBLE);
            binding.rlColorText.setVisibility(View.VISIBLE);
            binding.rlColorTextShadow.setVisibility(View.VISIBLE);
            getColor();
            getShadowColor();
        });
    }

    private void getBackground() {
        GridLayoutManager linearLayoutManager = new GridLayoutManager(this, 6, RecyclerView.VERTICAL, false);
        rcy_bg.setLayoutManager(linearLayoutManager);
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
        textSticker = new TextSticker(CreateTextActivity.this, onTouchSticker1);
        textSticker.setTextColor(Color.BLACK);
        textSticker.setTextSize(20);
        textSticker.setText(edit_text.getText().toString().trim());
        textSticker.setShadowEffect(8, 4, 4, Color.BLACK);

        int size = convertDpToPixel(150, CreateTextActivity.this);
        int sizeh = convertDpToPixel(100, CreateTextActivity.this);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                size,
                sizeh
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

    public TextStickerView.OnTouchSticker onTouchSticker1 = new TextStickerView.OnTouchSticker() {
        @Override
        public void onTouchedSticker() {
            removeBorder();
        }
    };


    private StickerView.OnTouchSticker onTouchSticker = new StickerView.OnTouchSticker() {
        @Override
        public void onTouchedSticker(StickerView stickerImageView) {
            sticker = (StickerImageView) stickerImageView;
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
            if (view instanceof StickerImageView){
                StickerImageView stickerImageView = (StickerImageView) view;
                stickerImageView.setControlItemsHidden(true);
            }
        }
    }

    private static int convertDpToPixel(float dp, Context context) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float px = dp * (metrics.densityDpi / 160f);
        return (int) px;
    }

    private void getColor() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        binding.rcyColor.setLayoutManager(linearLayoutManager);
        binding.rcyColor.setHasFixedSize(true);
        colorCodeAdapter = new ColorCodeAdapter(CreateTextActivity.this, new ColorCodeAdapter.Interfacecolor() {

            @Override
            public void onClick(String colorpath) {
                if (textSticker == null) {
                    Toast.makeText(CreateTextActivity.this, "please add text", Toast.LENGTH_SHORT).show();
                } else {
                    textSticker.setTextColor(Color.parseColor(colorpath));
                }
            }
        });
        binding.rcyColor.setItemAnimator(new DefaultItemAnimator());
        binding.rcyColor.setAdapter(colorCodeAdapter);
        colorCodeAdapter.addAll(objColorCodes);
    }

    private void getShadowColor() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        binding.rcyColorShadow.setLayoutManager(linearLayoutManager);
        binding.rcyColorShadow.setHasFixedSize(true);
        colorCodeAdapter = new ColorCodeAdapter(CreateTextActivity.this, new ColorCodeAdapter.Interfacecolor() {

            @Override
            public void onClick(String colorpath) {
                if (textSticker == null) {
                    Toast.makeText(CreateTextActivity.this, "please add text", Toast.LENGTH_SHORT).show();
                } else {
                    textSticker.setShadowEffect(8, 4, 4, Color.parseColor(colorpath));
                }
            }
        });
        binding.rcyColorShadow.setItemAnimator(new DefaultItemAnimator());
        binding.rcyColorShadow.setAdapter(colorCodeAdapter);
        colorCodeAdapter.addAll(objColorCodesShadow);
    }

    private void font() {
        GridLayoutManager linearLayoutManager = new GridLayoutManager(this, 4, RecyclerView.VERTICAL, false);
        binding.rcyFont.setLayoutManager(linearLayoutManager);
        binding.rcyFont.setHasFixedSize(true);
        fontAdapter = new FontAdapter(CreateTextActivity.this, new FontAdapter.interfacetext() {
            @Override
            public void onClick(String fontpath) {
                if (textSticker == null) {
                    Toast.makeText(CreateTextActivity.this, "please add text", Toast.LENGTH_SHORT).show();
                } else {
                    textSticker.setTypeface(Typeface.createFromAsset(CreateTextActivity.this.getAssets(), fontpath));
                }
            }
        });
        binding.rcyFont.setAdapter(fontAdapter);
        fontAdapter.addAll(objFont);
    }

    private void getFont() {
        AssetManager am = getAssets();
        String[] aplist = new String[0];
        try {
            aplist = am.list("font");
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < aplist.length; i++) {
            objFont.add(new ObjFont(aplist[i], "font/" + aplist[i]));
        }
    }

    private void getTextIcon() {
        GridLayoutManager linearLayoutManager = new GridLayoutManager(this, 6, RecyclerView.VERTICAL, false);
        binding.rcyTextIcon.setLayoutManager(linearLayoutManager);
        binding.rcyTextIcon.setHasFixedSize(true);
        BackgroundAdapter = new BackgroundAdapter(this, new BackgroundAdapter.iClickListener() {
            @Override
            public void onClickItem(Background background) {
                sticker = new StickerImageView(CreateTextActivity.this, onTouchSticker);
                Glide.with(CreateTextActivity.this).asBitmap().load(Uri.parse(background.getBg())).into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        sticker.setImageBitmap(resource);
                    }
                });
                int size = convertDpToPixel(100, CreateTextActivity.this);
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                        size, size
                );
                params.addRule(RelativeLayout.CENTER_IN_PARENT);
                sticker.setLayoutParams(params);
                Random r = new Random();
                view_id = r.nextInt();
                if (view_id < 0) {
                    view_id = view_id - (view_id * 2);
                }
                if (previousView != null) {
                    binding.rlBg.removeView(previousView);
                }
                sticker.setId(view_id);
                stickerviewId.add(view_id);
                binding.rlBg.addView(sticker);
                previousView = sticker;
            }
        });
        binding.rcyTextIcon.setAdapter(BackgroundAdapter);
        BackgroundAdapter.addAll(textIconList);
    }
    class saveAndGo extends AsyncTask<Void, Void, String> {

        saveAndGo() {
        }

        protected void onPreExecute() {
//            dialog = new Dialog(CreateTextActivity.this);
//            dialog.setContentView(R.layout.layout_dialog_export);
//            dialog.getWindow().setGravity(Gravity.CENTER);
//            dialog.getWindow().setLayout(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
//            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//            dialog.setCancelable(false);
//            ProgressBar progressBar = dialog.findViewById(R.id.spin_kit);
//            progressBar.setIndeterminateDrawable(new FadingCircle());
//            dialog.show();
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
        Intent intent = new Intent(CreateTextActivity.this, MainActivity.class);
        intent.putExtra("path", path);
        startActivity(intent);
        finish();
    }
    private Bitmap viewToBitmap() {
        removeBorder();
        binding.rlBg.setBackgroundColor(Color.WHITE);
        bitmap = Bitmap.createBitmap(binding.rlBg.getWidth(), binding.rlBg.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        binding.rlBg.draw(canvas);
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