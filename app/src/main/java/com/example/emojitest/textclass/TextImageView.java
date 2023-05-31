package com.example.emojitest.textclass;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;


public class TextImageView extends TextStickerView {

    private String owner_id;
    private ImageView iv_main;

    public TextImageView(Context context, OnTouchSticker onTouchSticker) {
        super(context, onTouchSticker);
    }

    public TextImageView(Context context, AttributeSet attrs, OnTouchSticker onTouchSticker) {
        super(context, attrs, onTouchSticker);
    }

    public TextImageView(Context context, AttributeSet attrs, int defStyle, OnTouchSticker onTouchSticker) {
        super(context, attrs, defStyle, onTouchSticker);
    }

    public void setOwnerId(String owner_id) {
        this.owner_id = owner_id;
    }

    public String getOwnerId() {
        return this.owner_id;
    }

    @Override
    public View getMainView() {
        if (this.iv_main == null) {
            this.iv_main = new ImageView(getContext());

            
        }
        return iv_main;
    }

    public void setImageBitmap(Bitmap bmp) {
        this.iv_main.setImageBitmap(bmp);
    }

    public void setImageResource(int res_id) {
        this.iv_main.setImageResource(res_id);
    }

    public void setImageDrawable(Drawable drawable) {
        this.iv_main.setImageDrawable(drawable);
    }

    public Bitmap getImageBitmap() {
        return ((BitmapDrawable) this.iv_main.getDrawable()).getBitmap();
    }

}
