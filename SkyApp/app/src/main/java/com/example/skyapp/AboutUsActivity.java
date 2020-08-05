package com.example.skyapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

public class AboutUsActivity extends AppCompatActivity {
    Toolbar toolbar;

    @Override
   protected void onCreate(Bundle savedInstanceState) {
              super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_about_us);


                toolbar = findViewById(R.id.AboutUs_Toolbar);
        setSupportActionBar(toolbar);

                toolbar.setTitle("Contact");
        toolbar.setTitleTextColor(Color.parseColor("#FFF8F3"));

        setSupportActionBar(toolbar);
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                                finish();
                            }
        });




    }

}
