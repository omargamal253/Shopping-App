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
import android.util.Log;
import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.agrawalsuneet.dotsloader.loaders.CircularDotsLoader;
import com.example.skyapp.Adapter.FireBase;
import com.example.skyapp.Adapter.RecyclerAdapter;
import com.example.skyapp.model.Product;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SuperMarketActivity extends AppCompatActivity {
    Toolbar toolbar;
    public static TextView SupermarketCardCount_;
    RecyclerView RecyclerView;
    RecyclerAdapter Adapter;
    ArrayList<Product> products = new ArrayList<>() ;
    CircularDotsLoader circularDotsLoader;
    SearchView searchView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_super_market);
        toolbar = findViewById(R.id.ProductToolbar);
        // add this in this activity tag  android:parentActivityName=".MainActivity"
        setSupportActionBar(toolbar);

        toolbar.setTitle("Supermarket");
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

        SupermarketCardCount_ = findViewById(R.id.Count);

        FireBase.GetNumOf_Products_InCard();

        RecyclerView =findViewById(R.id.SuperRecyclerView);
        LinearLayoutManager layoutManager = new GridLayoutManager(
                this ,2
        );
        RecyclerView.setLayoutManager(layoutManager);
        RecyclerView.setItemAnimator(new DefaultItemAnimator());
        Adapter = new RecyclerAdapter( this, products , 2,3);
        RecyclerView.setAdapter(Adapter);
        getMyList("Supermarket", Adapter);

        circularDotsLoader = findViewById(R.id.CircularDotsLoader);

                        circularDotsLoader.startAnimation();


                               Handler handler = new Handler();
              handler.postDelayed(new Runnable() {
            @Override
           public void run() {

                                        circularDotsLoader.setVisibility(View.INVISIBLE);
                            }
        },1000);



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
                          getMyList("Supermarket", Adapter);

                      }else{
                          if(newText.length()%3  ==0) {

                              searchProducts(newText, "Supermarket", Adapter);
                          }
                      }

                      return true;
                  }
              });

    }


    public static void searchProducts(String s ,String category , RecyclerAdapter Adapter) {

        final  RecyclerAdapter MyAdapt = Adapter;

        Query query = FirebaseDatabase.getInstance().getReference().child("products").child(category)
                .orderByKey().startAt(s).endAt(s + "\uf8ff");


        // Read from the database
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            //query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                MyAdapt.products.clear();
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
                //Toast.makeText(.this, "fail to load list",Toast.LENGTH_SHORT).show();

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
                Toast.makeText(SuperMarketActivity.this, "fail to load list",Toast.LENGTH_SHORT).show();

            }
        });

        products.clear();

    }


    public void MoveToCardActivity(View view) {
        Intent intent = new Intent(this, CardActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);

    }
}
