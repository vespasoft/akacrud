package com.akacrud.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.akacrud.R;
import com.akacrud.async.AsyncTaskSplash;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SplashActivity extends AppCompatActivity {

    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.textViewTitle)
    TextView textViewTitle;
    @BindView(R.id.buttonEnter)
    Button buttonEnter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);

        progressBar.setMax(100);

        // start animation
        textViewTitle.startAnimation(AnimationUtils.loadAnimation(this, R.anim.zoom_back_in));

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
