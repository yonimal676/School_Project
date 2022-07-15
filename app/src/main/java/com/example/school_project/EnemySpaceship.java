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
    Bitmap shot;


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


        shot = BitmapFactory.decodeResource(res, R.drawable.enemy_shot);

        shot = Bitmap.createScaledBitmap(shot, 1, 1, false);

    }

    public Bitmap getEnemyBitmap ()   // try some sort of array...
    {
        if (toShoot != 0)
        {
            switch (shootCounter) // the less cases, the bigger the shooting frequency
            {
                case 1:
                    shootCounter++;
                    return shot;
                case 2:
                    shootCounter++;
                    return shot;
                case 3:
                    shootCounter++;
                    return shot;
                case 4:
                    shootCounter++;
                    return shot;
                case 5:
                    shootCounter++;
                    return shot;
                case 6:
                    shootCounter++;
                    return shot;
                case 7:
                    shootCounter++;
                    return shot;
                case 8:
                    shootCounter++;
                    return shot;
                case 9:
                    shootCounter++;
                    return shot;
                case 10:
                    shootCounter++;
                    return shot;
                case 11:
                    shootCounter++;
                    return shot;
                case 12:
                    shootCounter++;
                    return shot;
                case 14:
                    shootCounter++;
                    return shot;
                case 15:
                    shootCounter++;
                    return shot;


                default:
                    shootCounter = 1;
                    toShoot --;

                    gameView.newEnemyShot();

                    return shot;
            }
        }
        //else..
            return EnemyBitmap;
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
