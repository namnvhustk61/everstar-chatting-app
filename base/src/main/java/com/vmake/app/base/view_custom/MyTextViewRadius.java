package com.vmake.app.base.view_custom;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.annotation.ColorRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.vmake.app.base.R;

public class MyTextViewRadius extends MyTextView {

    private float[] radii = new float[]{0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f};

    private int bgColor = Color.WHITE;

    private int strokeWidth = 0;
    private int strokeColor = Color.TRANSPARENT;

    public MyTextViewRadius(@NonNull Context context) {
        super(context);
        init(context, null);
    }

    public MyTextViewRadius(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public MyTextViewRadius(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        if (attrs != null) {
            obtain(context, attrs);
        }
        setupBackground();
    }

    private void obtain(Context context, AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.RadiusLayout);
        bgColor = ta.getColor(R.styleable.RadiusLayout_backgroundColor, Color.WHITE);
        if (ta.hasValue(R.styleable.RadiusLayout_radius)) {
            float radius = ta.getDimensionPixelSize(R.styleable.RadiusLayout_radius, convertDp(0f));
            for (int i = 0; i < radii.length; i++) {
                radii[i] = radius;
            }

        } else {
            float tl = ta.getDimensionPixelSize(R.styleable.RadiusLayout_radiusTopLeft, 0);
            float tr = ta.getDimensionPixelSize(R.styleable.RadiusLayout_radiusTopRight, 0);
            float br = ta.getDimensionPixelSize(R.styleable.RadiusLayout_radiusBottomRight, 0);
            float bl = ta.getDimensionPixelSize(R.styleable.RadiusLayout_radiusBottomLeft, 0);
            radii = new float[]{tl, tl, tr, tr, br, br, bl, bl};
        }
        strokeWidth = ta.getDimensionPixelSize(R.styleable.RadiusLayout_strokeWidth, 0);
        strokeColor = ta.getColor(R.styleable.RadiusLayout_strokeColor, Color.TRANSPARENT);

        if (!ta.getBoolean(R.styleable.RadiusLayout_offStateOnPressed, false)) {
            setStateOnPressed();
        }
        ta.recycle();
    }

    private void setupBackground() {
        if (getBackground() instanceof GradientDrawable) {
            getBackground().mutate();
            ((GradientDrawable) getBackground()).setCornerRadii(radii);
            ((GradientDrawable) getBackground()).setStroke(strokeWidth, strokeColor);

        } else if (getBackground() instanceof ColorDrawable) {
            getBackground().mutate();
            GradientDrawable drawable = new GradientDrawable();
            drawable.setColor(ColorStateList.valueOf(((ColorDrawable) getBackground()).getColor()));
            drawable.setCornerRadii(radii);
            drawable.setStroke(strokeWidth, strokeColor);
            setBackground(drawable);
        }
        if (getBackground() == null && strokeColor != Color.TRANSPARENT) {
            GradientDrawable drawable = new GradientDrawable();
            drawable.setCornerRadii(radii);
            drawable.setStroke(strokeWidth, strokeColor);
            setBackground(drawable);
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    public void setStateOnPressed() {
//        setOnClickListener(new OnClickListener() {@Override public void onClick(View v) { }});

        setOnTouchListener((v, event) -> {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN: {
                    if (v.getBackground() == null) {
                        GradientDrawable drawable = new GradientDrawable();
                        drawable.setCornerRadius(15f);
                        v.setBackground(drawable);
                    }
//                    v.getBackground().setColorFilter(getColorResource(getContext(), R.color.btn_click), PorterDuff.Mode.SRC_ATOP);

                    v.invalidate();
                    break;
                }
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL: {
                    if (v.getBackground() == null) {
                        GradientDrawable drawable = new GradientDrawable();
                        drawable.setCornerRadius(10f);
                        v.setBackground(drawable);
                    }
                    v.getBackground().clearColorFilter();
                    v.invalidate();
                    break;
                }
            }
            return false;
        });
    }

    @Override
    public void setBackgroundColor(int color) {
        super.setBackgroundColor(color);
        setupBackground();
    }

    @Override
    public void setBackgroundResource(int resid) {
        super.setBackgroundResource(resid);
        setupBackground();
    }

    public void setRadii(float radii) {
        this.radii = new float[]{radii, radii, radii, radii, radii, radii, radii, radii};
        setupBackground();
    }

    public void setRadii(float topLeft, float topRight, float bottomLeft, float bottomRight) {
        this.radii = new float[]{topLeft, topLeft, topRight, topRight, bottomRight, bottomRight, bottomLeft, bottomLeft};
        setupBackground();
    }

    public float getRadius() {
        return radii[0];
    }

    public void setStrokeWidth(int width, int strokeColor) {
        this.strokeWidth = width;
        this.strokeColor = strokeColor;
        setupBackground();
    }

    private int convertDp(float dp) {

        return Math.round((dp * getResources().getDisplayMetrics().density));
    }

    private int getColorResource(Context context, @ColorRes int id) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            return context.getColor(id);
        } else {
            return ContextCompat.getColor(context, id);
        }
    }
}
