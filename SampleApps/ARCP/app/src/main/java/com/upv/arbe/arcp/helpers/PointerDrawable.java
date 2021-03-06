package com.upv.arbe.arcp.helpers;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class PointerDrawable extends Drawable {

    private int centerX;
    private int centerY;
    private boolean enabled;

    private final Paint paint = new Paint();

    public PointerDrawable(int x, int y) {
        centerX = x;
        centerY = y;
    }

    private boolean isEnabled() { return enabled; }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        if (isEnabled()) {
            paint.setColor(Color.GREEN);
            canvas.drawCircle(centerX, centerY, 10, paint);
        } else {
            paint.setColor(Color.RED);
            canvas.drawCircle(centerX, centerY, 15, paint);
        }
    }

    @Override
    public void setAlpha(int alpha) {

    }

    @Override
    public void setColorFilter(@Nullable ColorFilter colorFilter) {

    }

    @Override
    public int getOpacity() {
        return PixelFormat.UNKNOWN;
    }
}
