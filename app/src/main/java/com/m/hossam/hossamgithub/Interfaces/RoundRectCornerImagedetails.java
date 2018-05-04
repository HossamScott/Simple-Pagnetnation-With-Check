package com.m.hossam.hossamgithub.Interfaces;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;

/**
 * Created by hossam on 11/11/2017.
 */

public class RoundRectCornerImagedetails extends android.support.v7.widget.AppCompatImageView {

    private Path path;

    public RoundRectCornerImagedetails(Context context) {
        super(context);
        init();
    }

    public RoundRectCornerImagedetails(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RoundRectCornerImagedetails(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        path = new Path();

    }

    @Override
    protected void onDraw(Canvas canvas) {
        RectF rect = new RectF(0, 0, this.getWidth(), this.getHeight());
        float radius = 40.0f;
        path.addRoundRect(rect, radius, radius, Path.Direction.CW);
        canvas.clipPath(path);
        super.onDraw(canvas);
    }
}