package com.example.school_project;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;


public class MainActivity extends AppCompatActivity implements View.OnClickListener
{

    Button playButton;
    SharedPreferences savePrefs;
    Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN
                , WindowManager.LayoutParams.FLAG_FULLSCREEN); // make it fullscreen

        setContentView(R.layout.activity_main);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);


        playButton = findViewById(R.id.play);

        playButton.setOnClickListener(this);



    }

    @Override
    public void onClick(View v)
    {
        if (v == playButton)
        {
            intent = new Intent(MainActivity.this, GameActivity.class);
            // put extra..
            startActivity(intent);
        }
    }
}