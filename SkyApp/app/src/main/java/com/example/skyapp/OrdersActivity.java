package com.example.skyapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.skyapp.Adapter.RecyclerCardAdapter;
import com.example.skyapp.Adapter.RecyclerOrderAdapter;
import com.example.skyapp.model.Order;
import com.example.skyapp.model.Product;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class OrdersActivity extends AppCompatActivity {
    Toolbar toolbar;

    TextView textView ;


    RecyclerView mreRecyclerView;
    RecyclerOrderAdapter myAdapter;
    ArrayList<Order>  ordersList = new ArrayList<>();

    ArrayList<Order> Orders = new ArrayList<>() ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);
        textView = findViewById(R.id.TestClass);

        toolbar = findViewById(R.id.OrderToolbar);
        // add this in this activity tag  android:parentActivityName=".MainActivity"
        setSupportActionBar(toolbar);


        toolbar.setTitle("Users Orders");
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



        mreRecyclerView =findViewById(R.id.OrderRecyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(
                this ,LinearLayoutManager.VERTICAL,false
        );
        mreRecyclerView.setLayoutManager(layoutManager);
        mreRecyclerView.setItemAnimator(new DefaultItemAnimator());
        myAdapter = new RecyclerOrderAdapter( this ,1);
        mreRecyclerView.setAdapter(myAdapter);


        getMyList(myAdapter);

        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference().child("Orders");


        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot data :  dataSnapshot.getChildren()){

                }


            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value

            }
        });




        }

    private void getMyList( RecyclerOrderAdapter Adapter) {

        final  RecyclerOrderAdapter MyAdapt = Adapter;


        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference().child("Orders");


        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot data :  dataSnapshot.getChildren()){
                    Order order  = data.getValue(Order.class);
                    HashMap H = (HashMap) data.child("ProductsInfo").getValue();
                    Log.d("HashMap : ", String.valueOf(H));


                    MyAdapt.addNewData(order,H);
                    /*for(Object name : H.keySet()){
                        String key =  name.toString();
                        String  value =(String) H.get(name).toString();
                        textView.append(key +" : "+value+"\n");
                    }*/
                    //  textView.setText(order.getUserId());
                    //  Toast.makeText(getContext(), product.getImage_url() ,Toast.LENGTH_SHORT).show();


                }


            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value

            }
        });

    }


}

