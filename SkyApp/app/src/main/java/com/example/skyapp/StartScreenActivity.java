package com.example.skyapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.agrawalsuneet.dotsloader.loaders.LazyLoader;
import com.example.skyapp.Adapter.FireBase;

public class StartScreenActivity extends AppCompatActivity {
    LazyLoader lazyLoader;
    ProgressDialog pd ;
    Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_screen);
        pd = new ProgressDialog(this);
        pd.show();
        pd.setContentView(R.layout.progress_dialog);
        pd.getWindow().setBackgroundDrawableResource(R.color.transparent);


        lazyLoader = findViewById(R.id.LazyLoader);

        LazyLoader  loader = new LazyLoader(this, 30, 20, ContextCompat.getColor(this, R.color.loader_selected),
                ContextCompat.getColor(this, R.color.loader_selected),
                ContextCompat.getColor(this, R.color.loader_selected));
        loader.setAnimDuration(500);
        loader.setFirstDelayDuration(100);
        loader.setSecondDelayDuration(200);
        loader.setInterpolator(new LinearInterpolator());

        handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent(StartScreenActivity.this,MainActivity.class);

                if(FireBase.isNetworkAvailable(StartScreenActivity.this)){
                    startActivity(intent);
                    finish();
                }


            }
        },10);


        lazyLoader.addView(loader);
        lazyLoader.setVisibility(View.INVISIBLE);

    }
}
