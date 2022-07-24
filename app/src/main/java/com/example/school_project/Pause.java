package com.example.school_project;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Pause
{
    Bitmap bitmap;
    int x, y;
    int width, height;

    Pause (int width, int height, int x, int y, Resources res)
    {
        this.width = width;
        this.height = height;

        bitmap = BitmapFactory.decodeResource(res, R.drawable.ic_baseline_pause_24);
        bitmap = Bitmap.createScaledBitmap(bitmap, width, height,false);

        this.x = x;
        this.y = y;
    }

    public boolean didTouchInBounds (float x, float y)
    {
        boolean isXinside = (x >= this.x) && (x <= this.x +200);
        boolean isYinside = (y >= this.y) && (y <= this.y +200);

        return isXinside && isYinside;
    }

}
