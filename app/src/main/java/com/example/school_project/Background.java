package com.example.school_project;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Background
{
    int x = 0, y = 0;
    Bitmap background;

    public Background(int ScreenWidth, int ScreenHeight, Resources res)
    {
        background = BitmapFactory.decodeResource(res, R.drawable.black_hole_sky_map_cover);
        background = Bitmap.createScaledBitmap(background, ScreenWidth, ScreenHeight, false);
    }// Constructor

}
