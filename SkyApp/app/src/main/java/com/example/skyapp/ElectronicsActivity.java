package com.example.skyapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.agrawalsuneet.dotsloader.loaders.ZeeLoader;
import com.example.skyapp.Adapter.FireBase;
import com.example.skyapp.Adapter.RecyclerAdapter;
import com.example.skyapp.model.Product;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ElectronicsActivity extends AppCompatActivity {
    Toolbar toolbar;
    public static TextView ElectronicsCardCount_;
    androidx.recyclerview.widget.RecyclerView RecyclerView;
    RecyclerAdapter Adapter;
    ArrayList<Product> products = new ArrayList<>() ;
    ZeeLoader zeeLoader;
    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_electronics);
        toolbar = findViewById(R.id.ProductToolbar);
        // add this in this activity tag  android:parentActivityName=".MainActivity"
        setSupportActionBar(toolbar);

        toolbar.setTitle("Electronics");
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

        ElectronicsCardCount_ = findViewById(R.id.Count);

        FireBase.GetNumOf_Products_InCard();

        RecyclerView =findViewById(R.id.ElectronicsRecyclerView);
        LinearLayoutManager layoutManager = new GridLayoutManager(
                this ,1
        );
        RecyclerView.setLayoutManager(layoutManager);
        RecyclerView.setItemAnimator(new DefaultItemAnimator());
        Adapter = new RecyclerAdapter( this, products , 4,6);
        RecyclerView.setAdapter(Adapter);
        getMyList("Electronics", Adapter);


        zeeLoader = findViewById(R.id.ZeeLoader);

                       Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
           @Override
         public void run() {

                                        zeeLoader.setVisibility(View.INVISIBLE);
                            }
        },2000);


        searchView = findViewById(R.id.SearchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //     Log.d("------------------------ ",query);

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                //   Log.d("------------------------ ",newText);

                if(newText.equals("")){
                    Adapter.products.clear();
                    Adapter.notifyDataSetChanged();
                    getMyList("Electronics", Adapter);

                }else{
                    if(newText.length()%3  ==0) {

                        SuperMarketActivity.searchProducts(newText, "Electronics", Adapter);
                    }
                }

                return true;
            }
        });



    }
    private void getMyList(String category , RecyclerAdapter Adapter) {

        final  RecyclerAdapter MyAdapt = Adapter;
        products.clear();

        Product product = new Product();

        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference().child("products").child(category);


        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot data :  dataSnapshot.getChildren()){
                    Product product  = data.getValue(Product.class);
                    if (product!=null){
                        //products.add(product);
                        MyAdapt.addNewData(product);

                    }
                    //  Toast.makeText(getContext(), product.getImage_url() ,Toast.LENGTH_SHORT).show();


                }


            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Toast.makeText(ElectronicsActivity.this, "fail to load list",Toast.LENGTH_SHORT).show();

            }
        });

        products.clear();

    }



    public void MoveToCardActivity(View view) {
        Intent intent = new Intent(this, CardActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);

    }
}
