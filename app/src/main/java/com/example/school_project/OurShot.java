package com.example.school_project;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

public class OurShot
{
    float x, y;
    Bitmap OurShotBitmap;
    int width, height;

    OurShot (Resources res)
    {
        width = 50;
        height = 50;

        OurShotBitmap = BitmapFactory
                .decodeResource(res, R.drawable.our_shot);// set bitmap image

        OurShotBitmap = Bitmap.createScaledBitmap(OurShotBitmap, width, height, false);
        // scale the image in the bitmap
    }

    public Rect getRect () {
        return new Rect((int)x +5,(int)y +5, (int) (x + width -5), (int) (y + height -5));
    }
}
