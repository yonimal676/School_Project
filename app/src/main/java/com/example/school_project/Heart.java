package com.example.school_project;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Heart
{
    int x, y;
    int width, height;
    Bitmap heartBitmap;

    Heart (int x, int y, float screenRatioX, float screenRatioY, Resources res)
    {
        width = (int) (50 * screenRatioX);
        height = (int) (50 * screenRatioY);


        heartBitmap = BitmapFactory
                .decodeResource(res, R.drawable.heart);

        heartBitmap = Bitmap.createScaledBitmap
                (heartBitmap, width , height ,false);



        this.x = x;
        this.y = y;
    }
}
