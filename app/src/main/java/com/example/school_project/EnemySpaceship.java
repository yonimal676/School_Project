package com.example.school_project;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

public class EnemySpaceship {
    float x, y; // coordinates of our spaceship
    Bitmap EnemyBitmap; // image of our spaceship
    int width, height;

    EnemySpaceship (int ScreenX, int ScreenY, Resources res)
    {
        width = 220;
        height = 120;

        EnemyBitmap = BitmapFactory
                .decodeResource(res, R.drawable.enemy);// set bitmap's image

        EnemyBitmap = Bitmap.createScaledBitmap(EnemyBitmap, width, height, false);
        // scale the image

        x = ScreenX; // x coordinate on the screen
        y = ScreenY; // y coordinate on the screen
    }

    public void setPosition(float x, float y)
    {
        this.x = x;
        this.y = y;
    }

    public Rect getCollisionShape () {
        return new Rect((int) x,(int) y, (int) (x + width), (int) (y + height -15));
    }


}
