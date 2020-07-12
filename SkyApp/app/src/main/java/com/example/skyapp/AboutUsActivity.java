package com.example.skyapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;

public class AboutUsActivity extends AppCompatActivity {
    Toolbar toolbar;

    @Override
   protected void onCreate(Bundle savedInstanceState) {
              super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_about_us);


                toolbar = findViewById(R.id.AboutUs_Toolbar);
        setSupportActionBar(toolbar);

                toolbar.setTitle("Contact");
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
