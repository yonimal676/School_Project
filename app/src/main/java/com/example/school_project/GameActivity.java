package com.example.school_project;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

public class GameActivity extends AppCompatActivity
{
    private GameView gameView;
    Bundle savedInstanceState;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        this.savedInstanceState = savedInstanceState;

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        // play the game on the whole screen

        Point point = new Point();
        getWindowManager().getDefaultDisplay().getSize(point);

        gameView = new GameView(this, point.x, point.y);



        setContentView(gameView);
        // NOTICE! this is possible because GameView extends SurfaceView


        // HERE
    }
    @Override
    protected void onPause() {
        super.onPause();
        gameView.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        gameView.resume();
    }

    public void PauseMenu ()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(gameView.gameViewContext, R.layout.pause_menu);

        LayoutInflater inflater = this.getLayoutInflater();
        View inflaterView = inflater.inflate(R.layout.pause_menu, null); // no need for root layout in pause_menu so =null
        builder.setView(inflaterView); // make dialog's view from pause_menu.xml



        AlertDialog finishDialog = builder.create();

        // for example
        finishDialog.setButton(AlertDialog.BUTTON_POSITIVE, "POSITIVE", (dialog, id) -> {
            finishDialog.dismiss();
            onResume();
        });
        // see 'DialogInterface.OnClickListener()' for more information on this.

        finishDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "RETURN TO MAIN MENU", (dialog, id) -> finish()); // lambdas are awesome. (same as above)

        finishDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "( ͡° ͜ʖ ͡°)", (dialog, id) -> watchYoutubeVideo());


        finishDialog.show();
    }


    void watchYoutubeVideo()
    {
        Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + "dQw4w9WgXcQ"));
        Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=dQw4w9WgXcQ"));

        try { startActivity(appIntent); }
        catch (ActivityNotFoundException ex) { startActivity(webIntent);}
        /* the purpose of this try catch block is to try and enter the YouTube app ↓
           and if YouTube isn't downloaded, enter YouTube through the web . */
    }

}
