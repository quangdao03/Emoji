package com.example.emojitest.util;

import android.graphics.Bitmap;
import android.widget.ImageView;

public class ViewState {
    public Bitmap bitmap;
    public boolean flipState;
    public boolean selectionState;
    public ImageView underView;

    public ViewState(Bitmap bitmap2, boolean z, boolean z2, ImageView imageView) {
        this.bitmap = bitmap2;
        this.selectionState = z;
        this.flipState = z2;
        this.underView = imageView;
    }
}
