package com.example.school_project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;


public class MainActivity extends AppCompatActivity implements View.OnClickListener
{

    Button playButton;

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
            startActivity(new Intent(MainActivity.this, GameActivity.class));
        }
    }
}