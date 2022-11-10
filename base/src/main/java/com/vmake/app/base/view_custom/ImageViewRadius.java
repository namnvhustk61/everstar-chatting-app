package com.vmake.app.base.view_custom;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatImageView;

import com.vmake.app.base.R;

public class ImageViewRadius extends AppCompatImageView {

    public ImageViewRadius(Context context) {
        super(context);
        init(context, null);
    }

    public ImageViewRadius(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public ImageViewRadius(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }
    private float[] radii ;
    private void init(Context context, AttributeSet attrs) {
        if (attrs != null) {
            obtain(context, attrs);
        }

    }

    @SuppressLint("CustomViewStyleable")
    private void obtain(Context context, AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.RadiusLayout);
        if (ta.hasValue(R.styleable.RadiusLayout_radius)) {
            float radius = ta.getDimensionPixelSize(R.styleable.RadiusLayout_radius, 0);
            radii = new float[]{0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f};
            for(int i = 0; i< radii.length; i++){
                radii[i] = convertDp2Float(radius);
            }

        } else {
            float tl = ta.getDimensionPixelSize(R.styleable.RadiusLayout_radiusTopLeft, 0);
            float tr = ta.getDimensionPixelSize(R.styleable.RadiusLayout_radiusTopRight, 0);
            float br = ta.getDimensionPixelSize(R.styleable.RadiusLayout_radiusBottomRight, 0);
            float bl = ta.getDimensionPixelSize(R.styleable.RadiusLayout_radiusBottomLeft, 0);
            radii = new float[]{tl, tl, tr, tr, br, br, bl, bl};
        }

        ta.recycle();
    }


    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        // Round some corners betch!
        Drawable myDrawable = getDrawable();


        if (myDrawable instanceof BitmapDrawable && radii != null) {
            final int color = 0xff000000;
            Rect bitmapBounds = myDrawable.getBounds();
            final RectF rectF = new RectF(bitmapBounds);

            // Resize the rounded rect we'll clip by this view's current bounds
            // (super.onDraw() will do something similar with the drawable to draw)
            getImageMatrix().mapRect(rectF);


            canvas.drawARGB(0, 0, 0, 0);

            final Path path = new Path();
            path.addRoundRect(rectF, radii, Path.Direction.CW);
            canvas.clipPath(path);


        }else if(myDrawable instanceof LayerDrawable && radii != null){
            if(((LayerDrawable) myDrawable).getDrawable(0) instanceof GradientDrawable){
                GradientDrawable drawBg = (GradientDrawable)((LayerDrawable) myDrawable).getDrawable(0);
                drawBg.setCornerRadii(radii);
                setImageDrawable(myDrawable);
            }

        }

        super.onDraw(canvas);
    }


    private float convertDp2Float(float dp) {

        return  Math.round((dp / getResources().getDisplayMetrics().density));
    }
}
