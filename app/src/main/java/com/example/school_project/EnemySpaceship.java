package com.example.school_project;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

public class EnemySpaceship
{
    float x, y;             // coordinates of our spaceship
    Bitmap EnemyBitmap;     // image of our spaceship
    int width, height;      // of the spaceship

    private GameView gameView;
    boolean shootOrNot = false; // every few iterations...
    int shootCounter = 1;
    int toShoot = 0;
    Bitmap shot1, shot2, shot3, shot4, shot5;



    public EnemySpaceship (GameView gameView, int ScreenX, int ScreenY, Resources res,
                           float screenRatioX, float screenRatioY)
    {
        this.gameView = gameView;

        width = 200;
        height = 110;

        EnemyBitmap = BitmapFactory
                .decodeResource(res, R.drawable.enemy);// set bitmap's image

        EnemyBitmap = Bitmap.createScaledBitmap(EnemyBitmap,
                (int)(width * screenRatioX), (int)(height * screenRatioY), false);
        // scale the image

        x = ScreenX; // x coordinate on the screen
        y = ScreenY; // y coordinate on the screen
    }

    public Bitmap getEnemyBitmap ()
    {
        if (toShoot != 0)
        {
            if (shootCounter == 1)
            {
                shootCounter++;
                return shot1;
            }
            if (shootCounter == 2)
            {
                shootCounter++;
                return shot1;
            }
            if (shootCounter == 3)
            {
                shootCounter++;
                return shot1;
            }
            if (shootCounter == 4)
            {
                shootCounter++;
                return shot1;
            }

            shootCounter = 1;
            toShoot --;
            gameView.newEnemyShot();

            return shot5;
        }
        return EnemyBitmap;
    }

    public void main(String[] args) {
        System.out.println(toShoot);
    }



    public void setPosition(float x, float y)
    {
        this.x = x;
        this.y = y;
    }

    public Rect getRect () {
        return new Rect((int) x,(int) y, (int) (x + width), (int) (y + height -15));
    }


}
