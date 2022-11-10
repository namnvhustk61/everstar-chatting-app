package com.vmake.app.base.view_custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.ColorInt;
import androidx.annotation.Nullable;

import com.vmake.app.base.R;

public class TabBarItem extends LinearLayout {

    private Drawable activeIcon;
    private Drawable inactiveIcon;
    private String title = "";

    private ImageView imageView;
    private MyTextView textView;
    private boolean isActive = false;

    private int sizeIcon = -1;
    private int sizeMarginTextIcon = -1;
    private float sizeText = -1f;

    @ColorInt
    private int colorTextActive = -1;
    private int colorTextInactive = -1;

    public TabBarItem(Context context) {
        super(context);
        init(context, null);
    }

    public TabBarItem(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);

    }

    public TabBarItem(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);

    }

    private void init(Context context, AttributeSet attrs) {
        if (attrs != null) {
            obtainValue(context, attrs);
        }
        createIcon();
        createTitle(context);
        setOrientation(VERTICAL);
        setGravity(Gravity.CENTER);
    }

    private void obtainValue(Context context, AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.TabBarItem);
        activeIcon = ta.getDrawable(R.styleable.TabBarItem_activeIcon);
        inactiveIcon = ta.getDrawable(R.styleable.TabBarItem_inactiveIcon);
        title = ta.getString(R.styleable.TabBarItem_title);
        colorTextActive = ta.getColor(R.styleable.TabBarItem_colorTextActive, Color.BLACK);
        colorTextInactive = ta.getColor(R.styleable.TabBarItem_colorTextInactive, Color.GRAY);
        sizeIcon = ta.getLayoutDimension(R.styleable.TabBarItem_size_icon, 24);
        sizeMarginTextIcon = ta.getLayoutDimension(R.styleable.TabBarItem_size_margin_text_icon, 0);
        sizeText = ta.getDimensionPixelSize(R.styleable.TabBarItem_size_text, 14);
        ta.recycle();
    }

    private void createIcon() {
        imageView = new ImageView(getContext());
        LayoutParams params = new LayoutParams(sizeIcon, sizeIcon);
        params.bottomMargin = sizeMarginTextIcon;
        imageView.setLayoutParams(params);
        imageView.setImageDrawable(isActive ? activeIcon : inactiveIcon);
        addView(imageView);
    }

    private void createTitle(Context context) {
        textView = new MyTextView(context);
        textView.setText(title);
        textView.setTextColor(isActive ? colorTextActive : colorTextInactive);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, sizeText);
        addView(textView);
    }

    public void setActive(boolean isActive) {
        if (isActive) {
            imageView.setImageDrawable(activeIcon);
            textView.setTextColor(colorTextActive);
            textView.setCustomFont(Typeface.BOLD);
        } else {
            imageView.setImageDrawable(inactiveIcon);
            textView.setTextColor(colorTextInactive);
            textView.setCustomFont(Typeface.NORMAL);
        }
    }
}
