package com.example.emojitest.MultiTouchTool;

import android.view.MotionEvent;
import android.view.View;

public interface SelectionListener {
    void onDown(View view);
    boolean onTouch(View view, MotionEvent motionEvent);
}
