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


/*
        ProgressDialog pd = new ProgressDialog(this);
        LayoutInflater inflater = (LayoutInflater)this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View DialogView  =inflater.inflate(R.layout.progress_successful,null);
        pd.setContentView(DialogView);
        pd.getWindow().setBackgroundDrawableResource(R.color.transparent);
        ImageView OkProgress=DialogView.findViewById(R.id.OkProgress);
        OkProgress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AboutUsActivity.this , MainActivity.class   );
                startActivity(intent);
                finish();

            }
        });



        pd.show();*/


    }

}
