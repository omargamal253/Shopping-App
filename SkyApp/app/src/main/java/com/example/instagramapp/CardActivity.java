package com.example.instagramapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.instagramapp.Adapter.RecyclerAdapter;
import com.example.instagramapp.Adapter.RecyclerCardAdapter;
import com.example.instagramapp.model.Product;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CardActivity extends AppCompatActivity {
    Toolbar toolbar;
    RecyclerView mreRecyclerView;
    RecyclerCardAdapter myAdapter;
    ArrayList<Product> products = new ArrayList<>() ;

    public static TextView TotalTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card);
        toolbar = findViewById(R.id.CardToolbar);
        toolbar.setTitle("Card");
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mreRecyclerView =findViewById(R.id.CardsRecyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(
                this ,LinearLayoutManager.VERTICAL,false
        );
        mreRecyclerView.setLayoutManager(layoutManager);
        mreRecyclerView.setItemAnimator(new DefaultItemAnimator());
        myAdapter = new RecyclerCardAdapter( this, products);
        mreRecyclerView.setAdapter(myAdapter);
        TotalTextView = findViewById(R.id.Total);
        TotalTextView.setText(String.format("%,.2f",new Double(0))+" EGP");


        getMyList(myAdapter);
    }

    private void getMyList( RecyclerCardAdapter Adapter) {

        final  RecyclerCardAdapter MyAdapt = Adapter;
        products.clear();


        Product product = new Product();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference().child("UserCards").child(user.getUid());


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
                Toast.makeText(getBaseContext(), "fail to load Card List",Toast.LENGTH_SHORT).show();

            }
        });

        products.clear();


    }

}
