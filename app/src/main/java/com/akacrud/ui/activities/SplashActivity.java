package com.akacrud.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.akacrud.R;
import com.akacrud.async.AsyncTaskSplash;

public class SplashActivity extends AppCompatActivity {

    ProgressBar progressBar;
    TextView textViewTitle;
    Button buttonEnter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setMax(100);

        // start animation
        textViewTitle = (TextView) findViewById(R.id.textViewTitle);
        textViewTitle.startAnimation(AnimationUtils.loadAnimation(this, R.anim.zoom_back_in));

        buttonEnter = (Button) findViewById(R.id.buttonEnter);
        buttonEnter.setVisibility(View.INVISIBLE);

        // starting new Async Task
        new AsyncTaskSplash(this).execute("Cargando ...");
    }

    public void finishLoader() {
        progressBar.setVisibility(View.INVISIBLE);

        buttonEnter.setVisibility(View.VISIBLE);
        buttonEnter.startAnimation(AnimationUtils.loadAnimation(this, R.anim.fade_in));
        buttonEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

}
