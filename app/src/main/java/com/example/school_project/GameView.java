package com.example.school_project;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.media.AudioAttributes;
import android.media.SoundPool;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.List;

public class GameView extends SurfaceView implements Runnable
{ // the game's content view

    private GameActivity activity;
    private Background background1, background2;
    private Paint paint;                             // The paint is the thing that "draws" the image/Bitmap.
    public int score = 0;                            // notice the initialization may be wrong
    // ⩕ game

    private Thread thread;
    private boolean isPlaying, isGameOver = false;
    Context GameViewContext;
    // ⩕ threads

    private int screenX, screenY;                   // size of screen
    private float screenRatioX, screenRatioY;       // for 1080p.
    // ⩕ movements

    private OurSpaceship ourSpaceship;
    private Flame flame;
    private SoundPool rocketBoosterSound;
    private int intSound;
    // ⩕ our spaceship

    private List<OurShot> listOurShot;
    // ⩕ our shots

    private EnemySpaceship enemySpaceship;
    private boolean goRight;
    // enemy laser
    // ⩕ enemy spaceship


    public GameView (GameActivity activity, int screenX, int screenY)
    {
        super(activity); //  <- super(Context);
        this.activity = activity;
        GameViewContext = this.activity;

        this.screenX = screenX;
        this.screenY = - screenY;
        /* NOTICE: The ( - ) is there because the background moves up in the 4th quarter */

        screenRatioX = 1080f / screenX; // side to side
        screenRatioY = 1920f / screenY; // top to bottom
        //for equal resolution on all devices.

        ourSpaceship = new OurSpaceship(this,screenX /2 -100,           // screenX : half of the size of the spaceship
                screenY -250 , getResources(), screenRatioX, screenRatioY);       // screenY : 250 above the bottom of the screen

        flame = new Flame((ourSpaceship.x + 10) * screenRatioX, 200 * screenRatioY, getResources());

        listOurShot = new ArrayList<>();

        enemySpaceship = new EnemySpaceship(screenX / 2 -100,
                200, getResources());


        AudioAttributes aa = new AudioAttributes.Builder().setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .setUsage(AudioAttributes.USAGE_GAME).build();
        rocketBoosterSound = new SoundPool.Builder().setMaxStreams(10000).setAudioAttributes(aa).build();
        intSound = rocketBoosterSound.load(this.activity,R.raw.shoot,1);

        background1 = new Background(screenX, screenY, getResources());
        background2 = new Background(screenX, screenY, getResources());
        // there are two backgrounds because it needs to move endlessly.
        background2.y = screenY;
        // set the second on above the first one at the start.

        goRight = true; // At first, enemySpaceship will go right.
    }// Constructor


    @Override
    public void run() // Basically, most of the game happens here.
    {
        while (isPlaying) {
            update();  // stuff
            draw();   // everything on screen
            sleep(); // for 17 milliseconds



            //ourSpaceship.y -= 3.5 * screenRatioY;
            // By default, the spaceship is going forward at all times to increase chaos O_O
        }
    }

    public void newShot()
    {
        OurShot ourShot = new OurShot(getResources());// whats getResources()?
        ourShot.x = ourSpaceship.x + 75;
        ourShot.y = ourSpaceship.y;

        listOurShot.add(ourShot);
        Log.d("ARR", listOurShot.size() + "");


        /*** stays at 0 */
    }

    private void draw() // the main one.
    {
        if (getHolder().getSurface().isValid()) // is the surface valid?
        {/** ... canvas.drawRect....x/y  ?? i don't think I need this..*/

            Canvas screenCanvas = getHolder().lockCanvas(); // create the canvas

            screenCanvas.drawBitmap(background1.background, background1.x, background1.y, paint);
            screenCanvas.drawBitmap(background2.background, background2.x, background2.y, paint);
            screenCanvas.drawBitmap(enemySpaceship.EnemyBitmap, enemySpaceship.x, enemySpaceship.y, paint);


            screenCanvas.drawBitmap(ourSpaceship.SpaceshipBitmap, ourSpaceship.x, ourSpaceship.y, paint);

            // pressed mode ↓↓↓
            if (ourSpaceship.getActionDown())
            {
                ourSpaceship.toShoot = 1;

                screenCanvas.drawBitmap(flame.flameBitmap
                        , (ourSpaceship.x - ourSpaceship.width /2  + flame.width - (4 * screenRatioX))
                        , ourSpaceship.y + 200, paint);


                screenCanvas.drawBitmap(ourSpaceship.getSpaceshipBitmap(), ourSpaceship.x, ourSpaceship.y, paint);

                for (OurShot ourShot : listOurShot)
                    screenCanvas.drawBitmap(ourShot.OurShotBitmap, ourShot.x, ourShot.y, paint);


                rocketBoosterSound.play(intSound, 1, 1, 0, 0, 2);
                // unintentional but cool 👌($ _ $ )
            }
            // ⩕⩕⩕⩕⩕⩕
            else rocketBoosterSound.stop(intSound);


            /* ⩔ */
            GoLeft();
            GoRight();
            if (goRight)
                enemySpaceship.setPosition(enemySpaceship.x + 12, enemySpaceship.y);
            else
                enemySpaceship.setPosition(enemySpaceship.x - 12, enemySpaceship.y);
            /* ⩕  Enemy movement ~(￣▽￣)~ */


            if (isGameOver) {
                isPlaying = false;
                getHolder().unlockCanvasAndPost(screenCanvas);
                return;
            }// what the fuck



            getHolder().unlockCanvasAndPost(screenCanvas);
        }
    } // Draw everything on screen.






    private void update()
    {
        background1.y += 20 * screenRatioY; // ↓ if I want to change the speed of the background, I can do it here
        background2.y += 20 * screenRatioY; // and here.

        if (background1.y > background1.background.getHeight())
            background1.y = screenY; // 1
        if (background2.y > background2.background.getHeight())
            background2.y = screenY; // 2


        List <OurShot> listOurShotTRASH = new ArrayList<>();

        for (OurShot ourShot : listOurShot)
        {
            if (ourShot.y < 0)
                listOurShotTRASH.add(ourShot);

            ourShot.y -= 40; /*************/

            if (Rect.intersects(enemySpaceship.getCollisionShape(), ourShot.getCollisionShape())) // enemy gets hit
            {

                listOurShotTRASH.add(ourShot);
                score++;
                ourShot.y =  - 500; // offscreen
            }
        }

        for (OurShot ourShotTrash : listOurShotTRASH)
            listOurShot.remove(ourShotTrash);

    } // update the movements



    private void sleep() {
        try {   Thread.sleep(17);   } // sleep for 17 milliseconds makes everything move smoothly.
        catch (InterruptedException e) {e.printStackTrace();}
    } // So the background would seem to be moving

    public void resume() {
        isPlaying = true;
        thread = new Thread(this);
        thread.start();





    } // resume the game
    public void pause()
    {
        try {
            isPlaying = false;
            thread.join(); // join = stop
        }
        catch (InterruptedException e) {e.printStackTrace();}
    } // pause the game

    public void dead () {
        // if (ourSpaceship.y > 0) => above the screen.
    } // end


    /* ⩔ */
    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        Log.d("TAG", event.getY()+ "");
        Log.d("TAG", event.getX()+ "");
        // in case I need to check coordinates.

        switch (event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                if (ourSpaceship.didTouchInBounds(event.getX(),event.getY()))
                {
                    ourSpaceship.setActionDown(true);
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (ourSpaceship.getActionDown() && (ourSpaceship.didTouchInBounds(event.getX(),event.getY())))
                    ourSpaceship.setPosition(event.getX() - ourSpaceship.width /2, event.getY() - ourSpaceship.width /2);
                break;
            case MotionEvent.ACTION_UP:
                ourSpaceship.setActionDown(false);
                break;
        }
        return true;
    }
    /** ⩕  our movement. @_@ */

    private void GoLeft() { //iteration #1,3,5...
        if (enemySpaceship.x + 220 >= screenX)
            goRight = false;
    }
    private void GoRight() { //iteration #2,4,6...
        if (enemySpaceship.x <= 0)
            goRight = true;
    }


}
