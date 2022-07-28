package com.example.school_project;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

public class OurSpaceship {
    int width, height;
    float x, y;                     // coordinates of our spaceship
    Bitmap SpaceshipBitmap, DeadBitmap;         // image of our spaceship
    boolean actionDown = false;     // down => touchDown (on the screen).

    int shootCounter = 1;
    int toShoot = 0;
    Bitmap shot;
    private GameView gameView;


    OurSpaceship (GameView gameView, int ScreenX, int ScreenY, Resources res,
                  float screenRatioX, float screenRatioY)
    {
        this.gameView = gameView;

        width = 150;
        height = 155;

        SpaceshipBitmap = BitmapFactory
                .decodeResource(res, R.drawable.spaceship);         // set bitmap's image

        SpaceshipBitmap = Bitmap.createScaledBitmap
                (SpaceshipBitmap, (int) (width * screenRatioX), (int) (height * screenRatioY), false);
        // scale the image

        DeadBitmap = BitmapFactory
                .decodeResource(res, R.drawable.spaceship_dead);

        DeadBitmap = Bitmap.createScaledBitmap
                (DeadBitmap, (int) (width * screenRatioX), (int) (height * screenRatioY), false);


        x = ScreenX; // x coordinate on the screen
        y = ScreenY; // y coordinate on the screen



        shot = BitmapFactory.decodeResource(res, R.drawable.our_shot);


        shot = Bitmap.createScaledBitmap(shot, 1, 1, false);
        // the first one would be invisible, to understand, change the 1,1 to 50,50.

    }


    public Bitmap getSpaceshipBitmap() // cannot be in an array because then it would shoot like a laser.
    {
        if (toShoot != 0) {
            switch (shootCounter) {
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


                default:
                    shootCounter = 1;
                    toShoot--;
                    gameView.newShot();

                    return shot;
            }
        }

        return SpaceshipBitmap; // if no shot is needed, just redraw the ourSpaceship again.
    }


    public void setActionDown (boolean ActionDown)
    {
        this.actionDown = ActionDown;
    }

    public boolean getActionDown()
    {
        return actionDown;
    }

    public void setPosition(float x, float y)
    {
        this.x = x;
        this.y = y;
        // Life is good ヽ(✿ﾟ▽ﾟ)ノ (only when your code works)
    }

    public boolean didTouchInBounds(float x, float y)
    {
        boolean isXinside = (x >= this.x) && (x <= this.x +200);
        boolean isYinside = (y >= this.y) && (y <= this.y +200);

        return isXinside && isYinside;
    }

    Rect getRect () {
        return new Rect((int) x,(int) y, (int) (x + width), (int) (y + height ));
    }
}
