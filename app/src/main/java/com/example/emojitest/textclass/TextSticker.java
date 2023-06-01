package com.example.emojitest.textclass;

import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

public class TextSticker extends TextStickerView{
    private String owner_id;
    private TextView tv_main;
    public TextSticker(Context context, OnTouchSticker onTouchSticker) {
        super(context, onTouchSticker);
    }

    public TextSticker(Context context, AttributeSet attrs, OnTouchSticker onTouchSticker) {
        super(context, attrs, onTouchSticker);
    }

    public TextSticker(Context context, AttributeSet attrs, int defStyle, OnTouchSticker onTouchSticker) {
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
        if (this.tv_main == null) {
            this.tv_main = new TextView(getContext());
        }
        return tv_main;
    }

    public void setText(String text) {
        if (tv_main != null) {
            tv_main.setText(text);
        }
    }

    public void setTextSize(float size) {
        if (tv_main != null) {
            tv_main.setTextSize(size);
        }
    }
    public void getTextSize(float size) {
        if (tv_main != null) {
            tv_main.setTextSize(size);
        }
    }

    public void setTextColor(int color) {
        if (tv_main != null) {
            tv_main.setTextColor(color);
        }
    }
    public void setTypeface(Typeface typeface) {
        if (tv_main != null) {
            tv_main.setTypeface(typeface);
        }
    }

    public void setTextBackground(Drawable background) {
        if (tv_main != null) {
            tv_main.setBackground(background);
        }
    }

    public String getText() {
        if (tv_main != null) {
            return tv_main.getText().toString();
        }
        return "";
    }
    public void setShadowEffect(float radius, float dx, float dy, int shadowColor) {
        if (tv_main != null) {
            tv_main.setLayerType(View.LAYER_TYPE_SOFTWARE, null); // Enable software rendering for the shadow effect
            tv_main.setShadowLayer(radius, dx, dy, shadowColor);
        }
    }

}
