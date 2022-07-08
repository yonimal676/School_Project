package com.example.school_project;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Flame
{
    float x, y;
    Bitmap flameBitmap;
    int width, height;

    Flame (float x, float y, Resources res)
    {
        width = 110;
        height = 200;

        flameBitmap = BitmapFactory.decodeResource(res, R.drawable.flame);
        flameBitmap = Bitmap.createScaledBitmap(flameBitmap,width,height,false);

        this.x = x;
        this.y = y;
    }
}
