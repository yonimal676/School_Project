package com.example.school_project;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;


public class MainActivity extends AppCompatActivity
{

    private boolean isMute;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN
                , WindowManager.LayoutParams.FLAG_FULLSCREEN); // make it fullscreen

        setContentView(R.layout.activity_main);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);


        findViewById(R.id.play).setOnClickListener(view -> // lambdas are cool
                startActivity(new Intent(MainActivity.this, GameActivity.class)));


        TextView highScoreText = findViewById(R.id.highScoreTXT);

        SharedPreferences prefs = getSharedPreferences("game", MODE_PRIVATE);
        highScoreText.setTextColor(Color.WHITE);
        highScoreText.setText("Highscore: " + prefs.getInt("highscore", 0));

        isMute = prefs.getBoolean("isMute", false);


        ImageView volumeImage = findViewById(R.id.volumeIV);

        if (isMute)
            volumeImage.setImageResource(R.drawable.volume_off);
        else
            volumeImage.setImageResource(R.drawable.volume_iv);

        volumeImage.setOnClickListener(view -> {
            isMute = !isMute;
            // true to false or false to true.
            if (isMute)
                volumeImage.setImageResource(R.drawable.volume_off);
            else
                volumeImage.setImageResource(R.drawable.volume_iv);

            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean("isMute", isMute);
            editor.apply();


        });

    }

}