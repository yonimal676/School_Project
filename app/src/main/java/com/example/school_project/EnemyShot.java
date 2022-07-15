package com.example.school_project;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

public class EnemyShot
{
    float x, y;
    int width, height;
    Bitmap EnemyShotBitmap;

    EnemyShot (Resources res)
    {
        width = 80;
        height = 80;

        EnemyShotBitmap = BitmapFactory
                .decodeResource(res, R.drawable.enemy_shot);// set bitmap image

        EnemyShotBitmap = Bitmap.createScaledBitmap (EnemyShotBitmap, width, height, false);
        // scale the image in the bitmap
    }

    public Rect getRect () {return new Rect
            ((int)x +5,(int)y +10, (int) (x + width -5), (int) (y + height +5));
    }

}
