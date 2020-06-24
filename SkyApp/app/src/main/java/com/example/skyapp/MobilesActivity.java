package com.example.skyapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.skyapp.Adapter.FireBase;
import com.example.skyapp.Adapter.RecyclerAdapter;
import com.example.skyapp.model.Product;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MobilesActivity extends AppCompatActivity {
    Toolbar toolbar;
    public static TextView MobilesCardCount_;
    androidx.recyclerview.widget.RecyclerView RecyclerView;
    RecyclerAdapter Adapter;
    ArrayList<Product> products = new ArrayList<>() ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mobiles);

        toolbar = findViewById(R.id.ProductToolbar);
        // add this in this activity tag  android:parentActivityName=".MainActivity"
        setSupportActionBar(toolbar);

        toolbar.setTitle("Mobiles");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        MobilesCardCount_ = findViewById(R.id.Count);

        FireBase.GetNumOf_Products_InCard();
        RecyclerView =findViewById(R.id.MobilesRecyclerView);
        LinearLayoutManager layoutManager = new GridLayoutManager(
                this ,2
        );
        RecyclerView.setLayoutManager(layoutManager);
        RecyclerView.setItemAnimator(new DefaultItemAnimator());
        Adapter = new RecyclerAdapter( this, products , 2);
        RecyclerView.setAdapter(Adapter);
        getMyList("Mobiles", Adapter);

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
                Toast.makeText(MobilesActivity.this, "fail to load list",Toast.LENGTH_SHORT).show();

            }
        });

        products.clear();

    }


    public void MoveToCardActivity(View view) {
        Intent intent = new Intent(this, CardActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);

    }
}
