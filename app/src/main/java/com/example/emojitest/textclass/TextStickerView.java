package com.example.emojitest.textclass;


import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.example.emojitest.R;


public abstract class TextStickerView extends FrameLayout implements View.OnClickListener {

    public static final String TAG = "com.stickerView";
    private BorderView iv_border;
    private ImageView iv_scale;
    private ImageView iv_delete;



    private float this_orgX = -1, this_orgY = -1;
    private float scale_orgX = -1, scale_orgY = -1;
    private double scale_orgWidth = -1, scale_orgHeight = -1;

    private float rotate_orgX = -1, rotate_orgY = -1, rotate_newX = -1, rotate_newY = -1;

    private float move_orgX = -1, move_orgY = -1;

    private double centerX, centerY;

    private final static int BUTTON_SIZE_DP = 20;
    private final static int SELF_SIZE_DP = 100;
    OnTouchSticker onTouchSticker;

    public interface OnTouchSticker {
        void onTouchedSticker();
    }

    public TextStickerView(Context context, OnTouchSticker onTouchSticker) {
        super(context);
        init(context);
        this.onTouchSticker = onTouchSticker;
    }

    public TextStickerView(Context context, AttributeSet attrs, OnTouchSticker onTouchSticker) {
        super(context, attrs);
        init(context);
        this.onTouchSticker = onTouchSticker;
    }

    public TextStickerView(Context context, AttributeSet attrs, int defStyle, OnTouchSticker onTouchSticker) {
        super(context, attrs, defStyle);
        init(context);
        this.onTouchSticker = onTouchSticker;
    }

    private void init(Context context) {
        this.iv_border = new BorderView(context);
        this.iv_scale = new ImageView(context);
        this.iv_delete = new ImageView(context);


        this.iv_scale.setImageResource(R.drawable.ic_scale_text);
        this.iv_delete.setImageResource(R.drawable.ic_close_text);
//        this.iv_flip.setImageResource(R.drawable.flip);

        this.setTag("DraggableViewGroup");
        this.iv_border.setTag("iv_border");
        this.iv_scale.setTag("iv_scale");
        this.iv_delete.setTag("iv_delete");


        int margin = convertDpToPixel(BUTTON_SIZE_DP, getContext()) / 2;
        int size = convertDpToPixel(SELF_SIZE_DP, getContext());

        LayoutParams this_params =
                new LayoutParams(
                        size,
                        size
                );
        this_params.gravity = Gravity.CENTER;

        LayoutParams iv_main_params =
                new LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                );
        iv_main_params.setMargins(40, 40, 40, 40);

        LayoutParams iv_border_params =
                new LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                );
        iv_border_params.setMargins(margin, margin, margin, margin);

        LayoutParams iv_scale_params =
                new LayoutParams(
                        convertDpToPixel(BUTTON_SIZE_DP, getContext()),
                        convertDpToPixel(BUTTON_SIZE_DP, getContext())
                );
        iv_scale_params.gravity = Gravity.BOTTOM | Gravity.RIGHT;

        LayoutParams iv_delete_params =
                new LayoutParams(
                        convertDpToPixel(BUTTON_SIZE_DP, getContext()),
                        convertDpToPixel(BUTTON_SIZE_DP, getContext())
                );
        iv_delete_params.gravity = Gravity.TOP | Gravity.LEFT;


        this.setLayoutParams(this_params);
        this.addView(getMainView(), iv_main_params);
        this.addView(iv_border, iv_border_params);
        this.addView(iv_scale, iv_scale_params);
        this.addView(iv_delete, iv_delete_params);
        this.setOnTouchListener(mTouchListener);
        this.setOnClickListener(this);
        this.iv_scale.setOnTouchListener(mTouchListener);
        this.iv_delete.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextStickerView.this.getParent() != null) {
                    ViewGroup myCanvas = ((ViewGroup) TextStickerView.this.getParent());
                    myCanvas.removeView(TextStickerView.this);
                }
            }
        });

    }

    public boolean isFlip() {
        return getMainView().getRotationY() == -180f;
    }

    protected abstract View getMainView();

    private OnTouchListener mTouchListener = new OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent event) {

            if (view.getTag().equals("DraggableViewGroup")) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        setControlItemsHidden(true);
                        onTouchSticker.onTouchedSticker();
                        Log.v(TAG, "sticker view action down");
                        move_orgX = event.getRawX();
                        move_orgY = event.getRawY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        Log.v(TAG, "sticker view action move_right");
                        float offsetX = event.getRawX() - move_orgX;
                        float offsetY = event.getRawY() - move_orgY;
                        TextStickerView.this.setX(TextStickerView.this.getX() + offsetX);
                        TextStickerView.this.setY(TextStickerView.this.getY() + offsetY);
                        move_orgX = event.getRawX();
                        move_orgY = event.getRawY();
                        break;
                    case MotionEvent.ACTION_UP:
                        Log.v(TAG, "sticker view action up");
                        setControlItemsHidden(false);
                        break;
                }
            } else if (view.getTag().equals("iv_scale")) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        Log.v(TAG, "iv_scale action down");

                        this_orgX = TextStickerView.this.getX();
                        this_orgY = TextStickerView.this.getY();

                        scale_orgX = event.getRawX();
                        scale_orgY = event.getRawY();
                        scale_orgWidth = TextStickerView.this.getLayoutParams().width;
                        scale_orgHeight = TextStickerView.this.getLayoutParams().height;

                        rotate_orgX = event.getRawX();
                        rotate_orgY = event.getRawY();

                        centerX = TextStickerView.this.getX() +
                                ((View) TextStickerView.this.getParent()).getX() +
                                (float) TextStickerView.this.getWidth() / 2;



                        int result = 0;
                        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
                        if (resourceId > 0) {
                            result = getResources().getDimensionPixelSize(resourceId);
                        }
                        double statusBarHeight = result;
                        centerY = TextStickerView.this.getY() +
                                ((View) TextStickerView.this.getParent()).getY() +
                                statusBarHeight +
                                (float) TextStickerView.this.getHeight() / 2;

                        break;
                    case MotionEvent.ACTION_MOVE:
                        Log.v(TAG, "iv_scale action move_right");

                        rotate_newX = event.getRawX();
                        rotate_newY = event.getRawY();

                        double angle_diff = Math.abs(
                                Math.atan2(event.getRawY() - scale_orgY, event.getRawX() - scale_orgX)
                                        - Math.atan2(scale_orgY - centerY, scale_orgX - centerX)) * 180 / Math.PI;

                        Log.v(TAG, "angle_diff: " + angle_diff);

                        double length1 = getLength(centerX, centerY, scale_orgX, scale_orgY);
                        double length2 = getLength(centerX, centerY, event.getRawX(), event.getRawY());

                        int size = convertDpToPixel(SELF_SIZE_DP, getContext());
                        if (length2 > length1
                                && (angle_diff < 25 || Math.abs(angle_diff - 180) < 25)
                                ) {

                            double offsetX = Math.abs(event.getRawX() - scale_orgX);
                            double offsetY = Math.abs(event.getRawY() - scale_orgY);
                            double offset = Math.max(offsetX, offsetY);
                            offset = Math.round(offset);
                            TextStickerView.this.getLayoutParams().width += offset;
                            TextStickerView.this.getLayoutParams().height += offset;
                            onScaling(true);


                        } else if (length2 < length1
                                && (angle_diff < 25 || Math.abs(angle_diff - 180) < 25)
                                && TextStickerView.this.getLayoutParams().width > size / 2
                                && TextStickerView.this.getLayoutParams().height > size / 2) {

                            double offsetX = Math.abs(event.getRawX() - scale_orgX);
                            double offsetY = Math.abs(event.getRawY() - scale_orgY);
                            double offset = Math.max(offsetX, offsetY);
                            offset = Math.round(offset);
                            TextStickerView.this.getLayoutParams().width -= offset;
                            TextStickerView.this.getLayoutParams().height -= offset;
                            onScaling(false);
                        }



                        double angle = Math.atan2(event.getRawY() - centerY, event.getRawX() - centerX) * 180 / Math.PI;
                        Log.v(TAG, "log angle: " + angle);


                        setRotation((float) angle - 70);
                        Log.v(TAG, "getRotation(): " + getRotation());

                        onRotating();

                        rotate_orgX = rotate_newX;
                        rotate_orgY = rotate_newY;

                        scale_orgX = event.getRawX();
                        scale_orgY = event.getRawY();

                        postInvalidate();
                        requestLayout();
                        break;
                    case MotionEvent.ACTION_UP:
                        Log.v(TAG, "iv_scale action up");
                        break;
                }
            }
            return true;
        }
    };

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    private double getLength(double x1, double y1, double x2, double y2) {
        return Math.sqrt(Math.pow(y2 - y1, 2) + Math.pow(x2 - x1, 2));
    }

    private float[] getRelativePos(float absX, float absY) {
        Log.v("ken", "getRelativePos getX:" + ((View) this.getParent()).getX());
        Log.v("ken", "getRelativePos getY:" + ((View) this.getParent()).getY());
        float[] pos = new float[]{
                absX - ((View) this.getParent()).getX(),
                absY - ((View) this.getParent()).getY()
        };
        Log.v(TAG, "getRelativePos absY:" + absY);
        Log.v(TAG, "getRelativePos relativeY:" + pos[1]);
        return pos;
    }

    public void setControlItemsHidden(boolean isHidden) {
        if (isHidden) {
            iv_border.setVisibility(View.INVISIBLE);
            iv_scale.setVisibility(View.INVISIBLE);
            iv_delete.setVisibility(View.INVISIBLE);
        } else {
            iv_border.setVisibility(View.VISIBLE);
            iv_scale.setVisibility(View.VISIBLE);
            iv_delete.setVisibility(View.VISIBLE);
        }
    }


    protected void onScaling(boolean scaleUp) {
    }

    protected void onRotating() {
    }

    @Override
    public void onClick(View view) {
        setControlItemsHidden(false);
    }

    private class BorderView extends View {

        public BorderView(Context context) {
            super(context);
        }

        public BorderView(Context context, AttributeSet attrs) {
            super(context, attrs);
        }

        public BorderView(Context context, AttributeSet attrs, int defStyle) {
            super(context, attrs, defStyle);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);


            LayoutParams params = (LayoutParams) this.getLayoutParams();

            Log.v(TAG, "params.leftMargin: " + params.leftMargin);

            Rect border = new Rect();
            border.left = this.getLeft() - params.leftMargin;
            border.top = this.getTop() - params.topMargin;
            border.right = this.getRight() - params.rightMargin;
            border.bottom = this.getBottom() - params.bottomMargin;
            Paint borderPaint = new Paint();
            borderPaint.setStrokeWidth(8);
            borderPaint.setColor(getResources().getColor(R.color.white));
            borderPaint.setStyle(Paint.Style.STROKE);
            canvas.drawRect(border, borderPaint);

        }
    }

    private static int convertDpToPixel(float dp, Context context) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float px = dp * (metrics.densityDpi / 160f);
        return (int) px;
    }

    public void setControlsVisibility(boolean isVisible) {
        if (!isVisible) {
            iv_border.setVisibility(View.GONE);
            iv_delete.setVisibility(View.GONE);
            iv_scale.setVisibility(View.GONE);
        } else {
            iv_border.setVisibility(View.VISIBLE);
            iv_delete.setVisibility(View.VISIBLE);
            iv_scale.setVisibility(View.VISIBLE);
        }

    }
}
