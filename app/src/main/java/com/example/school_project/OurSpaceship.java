package com.example.school_project;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

public class OurSpaceship {
    int width, height;
    float x, y;                     // coordinates of our spaceship
    Bitmap SpaceshipBitmap;         // image of our spaceship
    boolean actionDown = false;     // down => touchDown (on the screen).
    // our spaceship

    int shootCounter = 1;
    int toShoot = 0;
    Bitmap shot1, shot2, shot3, shot4, shot5;
    private GameView gameView;
    // our shots


    OurSpaceship (GameView gameView, int ScreenX, int ScreenY, Resources res,
                  float screenRatioX, float screenRatioY)
    {
        this.gameView = gameView;

        width = 200;
        height = 220;

        SpaceshipBitmap = BitmapFactory
                .decodeResource(res, R.drawable.spaceship);         // set bitmap's image

        SpaceshipBitmap = Bitmap.createScaledBitmap
                (SpaceshipBitmap, (int) (width * screenRatioX), (int) (height * screenRatioY), false);
        // scale the image

        x = ScreenX; // x coordinate on the screen
        y = ScreenY; // y coordinate on the screen



        shot1 = BitmapFactory.decodeResource(res, R.drawable.our_shot);
        shot2 = BitmapFactory.decodeResource(res, R.drawable.our_shot);
        shot3 = BitmapFactory.decodeResource(res, R.drawable.our_shot);
        shot4 = BitmapFactory.decodeResource(res, R.drawable.our_shot);
        shot5 = BitmapFactory.decodeResource(res, R.drawable.our_shot);


        shot1 = Bitmap.createScaledBitmap(shot1, 50, 50, false);
        shot2 = Bitmap.createScaledBitmap(shot2, 50, 50, false);
        shot3 = Bitmap.createScaledBitmap(shot3, 50, 50, false);
        shot4 = Bitmap.createScaledBitmap(shot4, 50, 50, false);
        shot5 = Bitmap.createScaledBitmap(shot5, 50, 50, false);

    }


    public Bitmap getSpaceshipBitmap()
    {
        if (toShoot != 0) {

            if (shootCounter == 1) {
                shootCounter++;
                return shot1;
            }

            if (shootCounter == 2) {
                shootCounter++;
                return shot2;
            }

            if (shootCounter == 3) {
                shootCounter++;
                return shot3;
            }

            if (shootCounter == 4) {
                shootCounter++;
                return shot4;
            }

            shootCounter = 1;
            toShoot--;
            gameView.newShot();

            return shot5;
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
