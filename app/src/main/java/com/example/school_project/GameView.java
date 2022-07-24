package com.example.school_project;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatDialogFragment;

import java.util.ArrayList;
import java.util.List;

public class GameView extends SurfaceView implements Runnable
{ // the game's content view

    private Background background1, background2;
    private Paint paint;                  // The paint is the thing that "draws"// the image/Bitmap.
    public int score = 0;
    private SoundPool soundPool;
    private int intSound;
    private  int life = 3;
    private Pause pause;
    // ⩕ game

    private SharedPreferences prefs;
    private Thread thread;
    private boolean isPlaying, isGameOver = false;
    Context GameViewContext;
    private GameActivity activity;
    int sleepMS = 17;
    // ⩕ threads

    private int screenX, screenY;                   // size of screen
    private float screenRatioX, screenRatioY;       // for 1080p.
    // ⩕ movements

    private OurSpaceship ourSpaceship;
    private List<OurShot> listOurShot;
    private Flame flame;
    // ⩕ our spaceship

    private EnemySpaceship enemySpaceship;
    private boolean goRight;
    private List<EnemyShot> listEnemyShot;
    private int shootCounter = 0;
    // ⩕ enemy spaceship


    public GameView (GameActivity activity, int screenX, int screenY)
    {
        super(activity); //  <- super(Context);

        this.activity = activity;

        prefs = activity.getSharedPreferences("game", Context.MODE_PRIVATE);
        // not really that important, but this will hide the game from other apps on the phone


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) // M = 23
        {
            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .setUsage(AudioAttributes.USAGE_GAME)
                    .build();

            soundPool = new SoundPool.Builder()
                    .setAudioAttributes(audioAttributes)
                    .build();
        } else
            soundPool = new SoundPool(1, AudioManager.STREAM_MUSIC,
                    0);

        intSound = soundPool.load(activity, R.raw.shoot, 1);



        this.screenX = screenX;
        this.screenY = - screenY;
        /* NOTICE: The ( - ) is there because the background moves up in the 4th quarter ↓
         * and we want to make it like the 2nd quarter */

        screenRatioX = 1080 / screenX; // side to side
        screenRatioY = 1920 / screenY; // top to bottom
        //for equal resolution ( 1920x1080 ) on all devices.

        ourSpaceship = new OurSpaceship(this,screenX /2 -100,
                screenY -250 , getResources(), screenRatioX, screenRatioY);

        flame = new Flame((ourSpaceship.x +10) *screenRatioX,
                (ourSpaceship.y +300) *screenRatioY ,
                getResources(), screenRatioX, screenRatioY);

        listOurShot = new ArrayList<>();


        enemySpaceship = new EnemySpaceship(this, screenX / 2 -100,
                200, getResources(), screenRatioX, screenRatioY);

        listEnemyShot = new ArrayList<>();


        pause = new Pause(50, 50 , 20,20,getResources());


        background1 = new Background(screenX, screenY, getResources());
        background2 = new Background(screenX, screenY, getResources());
        // there are two backgrounds because it needs to move endlessly.

        background2.y = screenY;
        // set the second on above the first one at the start.

        goRight = true; // At first, enemySpaceship will go right.

    }// Constructor


    @Override
    public void run() //most of the game happens here.
    {
        while (isPlaying)
        {
            update();  // the bitmaps on the screen
            draw();    // redraw everything on screen
            sleep();   // for 17 milliseconds
        }

        if (isPlaying == false && isGameOver == false) // if paused
        {

        }
    }

    public void newShot()
    {
        OurShot ourShot = new OurShot(getResources());// whats getResources()?
        ourShot.x = ourSpaceship.x + 75;
        ourShot.y = ourSpaceship.y;

        listOurShot.add(ourShot);
        Log.d("ARR", listOurShot.size() + "");
    }

    public void newEnemyShot()
    {
        if (! prefs.getBoolean("isMute", false))
            soundPool.play(intSound,1,1,0,0,1);

        EnemyShot enemyShot = new EnemyShot (getResources());
        enemyShot.x = enemySpaceship.x + enemySpaceship.width /2;
        enemyShot.y = enemySpaceship.y + enemySpaceship.height;

        listEnemyShot.add(enemyShot);
        Log.d("ARR", listEnemyShot.size() + "");
    }

    private void draw() // the main one.
    {
        if (getHolder().getSurface().isValid()) // is the surface valid?
        {
            Canvas screenCanvas = getHolder().lockCanvas(); // create the canvas

            screenCanvas.drawBitmap(background1.background, background1.x, background1.y, paint);
            screenCanvas.drawBitmap(background2.background, background2.x, background2.y, paint);

            screenCanvas.drawBitmap(enemySpaceship.EnemyBitmap, enemySpaceship.x,
                    enemySpaceship.y, paint);
            screenCanvas.drawBitmap(ourSpaceship.SpaceshipBitmap, ourSpaceship.x,
                    ourSpaceship.y, paint);

            screenCanvas.drawBitmap(pause.bitmap, pause.x, pause.y, paint);

            enemySpaceship.toShoot = 1;

            screenCanvas.drawBitmap(enemySpaceship.getEnemyBitmap(), enemySpaceship.x,
                    enemySpaceship.y, paint);

            for (EnemyShot enemyShot : listEnemyShot)
                screenCanvas.drawBitmap(enemyShot.EnemyShotBitmap, enemyShot.x, enemyShot.y, paint);


            // pressed mode ↓↓↓
            if (ourSpaceship.getActionDown())
            {
                ourSpaceship.toShoot = 1;

                screenCanvas.drawBitmap(flame.flameBitmap ,
                        (ourSpaceship.x - flame.width /2 + ourSpaceship.width /2) ,
                        (ourSpaceship.y + ourSpaceship.height) * screenRatioY, paint);

                screenCanvas.drawBitmap(ourSpaceship.getSpaceshipBitmap(), ourSpaceship.x,
                        ourSpaceship.y, paint);

                for (OurShot ourShot : listOurShot)
                    screenCanvas.drawBitmap(ourShot.OurShotBitmap, ourShot.x, ourShot.y, paint);
            }
            // ⩕⩕⩕⩕⩕⩕


            /* ⩔ */
            GoLeft();
            GoRight();
            if (goRight)
                enemySpaceship.setPosition(enemySpaceship.x + 12, enemySpaceship.y);
            else
                enemySpaceship.setPosition(enemySpaceship.x - 12, enemySpaceship.y);
            /* ⩕  Enemy movement ~(￣▽￣)~ */


//            screenCanvas.drawText(score + "", screenX / 2, 10, paint);

            if (isGameOver)
            {
                isPlaying = false;
                getHolder().unlockCanvasAndPost(screenCanvas);
                saveIfHighScore();
                waitBeforeExiting();
                return; // what the fuck
            }


            getHolder().unlockCanvasAndPost(screenCanvas);
        }
    } // Draw everything on screen.


    private void waitBeforeExiting() {

        try {
            Thread.sleep(3000);
            activity.startActivity(new Intent(activity, MainActivity.class));
            activity.finish();

            /* wait 3 seconds -> start MainActivity -> exterminate (this) GameActivity */
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    private void saveIfHighScore()
    {
        if (prefs.getInt("highscore", 0) < score) // if highscore > score
        {
            SharedPreferences.Editor editor = prefs.edit(); // edit SharedPreferences
            editor.putInt("highscore", score);
            editor.apply();
        }

    }


    private void update()
    {
        background1.y += 20 * screenRatioY; // ↓ i can change the speed of the background here
        background2.y += 20 * screenRatioY; // and here.

        if (background1.y > background1.background.getHeight())
            background1.y = screenY; // 1
        if (background2.y > background2.background.getHeight())
            background2.y = screenY; // 2


        List <EnemyShot> listEnemyShotTRASH = new ArrayList<>();

        for (EnemyShot enemyShot : listEnemyShot)
        {
            if (enemyShot.y > screenY)
                listEnemyShotTRASH.add(enemyShot);

            enemyShot.y += 30; // enemyShot.speed...

            if (Rect.intersects(enemyShot.getRect(), ourSpaceship.getRect()))
            {
                life--;
                if (life == 0)
                    isGameOver = true;

                enemyShot.y = 3000; // off-screen
                listEnemyShotTRASH.add(enemyShot);
            }
        }

        for (EnemyShot enemyShot : listEnemyShotTRASH)
            listOurShot.remove(enemyShot);



        List <OurShot> listOurShotTRASH = new ArrayList<>();

        for (OurShot ourShot : listOurShot)
        {
            if (ourShot.y < 0)
                listOurShotTRASH.add(ourShot);

            ourShot.y -= 40; // ourShot.speed...(for diffeculty)

            if (Rect.intersects(enemySpaceship.getRect(), ourShot.getRect())) // enemy gets hit
            {
                listOurShotTRASH.add(ourShot);
                score++;
            }
        }

        for (OurShot ourShotTrash : listOurShotTRASH)
            listOurShot.remove(ourShotTrash);








    } // update the objects on screen

    private void sleep() {
        try {
            Thread.sleep(sleepMS); // = 17
        }
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

    } // end


    /* ⩔ */
    @Override
    public boolean onTouchEvent (MotionEvent event)
    {
        Log.d("TAG", event.getY()+ "");
        Log.d("TAG", event.getX()+ "");
        // in case I need to check coordinates.

        switch (event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                if (ourSpaceship.didTouchInBounds(event.getX(),event.getY()))
                    ourSpaceship.setActionDown(true);

                if (pause.didTouchInBounds(event.getX(), event.getY()))
                    pause();
                break;
            case MotionEvent.ACTION_MOVE:
                if (ourSpaceship.getActionDown() && (ourSpaceship.didTouchInBounds(event.getX()
                        ,event.getY())))
                    ourSpaceship.setPosition(event.getX() - ourSpaceship.width /2,
                            event.getY() - ourSpaceship.height /2);
                break;
            case MotionEvent.ACTION_UP:
                ourSpaceship.setActionDown(false);

                break;
        }
        return true;
    }
    /** ⩕  our movement. @_@ */

    private void GoLeft() { //iteration #1,3,5...
        if (enemySpaceship.x + enemySpaceship.width + 30 >= screenX)
            goRight = false;
    }
    private void GoRight() { //iteration #2,4,6...
        if (enemySpaceship.x <= 0)
            goRight = true;
    }
}



class exampleDialog extends AppCompatDialogFragment
{
    void watchYoutubeVideo(String id)
    {
        Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + id));
        Intent webIntent = new Intent(Intent.ACTION_VIEW,
                Uri.parse("http://www.youtube.com/watch?v=" + id));
        try {
            startActivity(appIntent);
        } catch (ActivityNotFoundException ex) {
            startActivity(webIntent);
        }
    }


    public Dialog onCreateDialog (Bundle savedInstanceState)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle("Totaly legit %100!!!!!")
                .setMessage("This is a Dialog.")
                .setPositiveButton("cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int i) {
                                watchYoutubeVideo("dQw4w9WgXcQ");
                            }});

        return builder.create();
    }
}
