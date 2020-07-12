package com.example.skyapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.skyapp.Adapter.FireBase;
import com.example.skyapp.Adapter.MyCardHolder;
import com.example.skyapp.Adapter.RecyclerCardAdapter;
import com.example.skyapp.fragments.HomeFragment;
import com.example.skyapp.model.Product;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;

public class CardActivity extends AppCompatActivity {
    Toolbar toolbar;
    RecyclerView mreRecyclerView;
    RecyclerCardAdapter myAdapter;
    ArrayList<Product> products = new ArrayList<>() ;

    public static TextView TotalTextView;
    public static TextView   CardEmpty;

    Button CallBtn, CompleteOrderBtn;
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


        for (int childCount= mreRecyclerView.getChildCount() , i=0;  i < childCount ;  ++i){
            final MyCardHolder holder = (MyCardHolder) mreRecyclerView.getChildViewHolder(mreRecyclerView.getChildAt(i));
            Log.e("Holder "+i,holder.toString());
        }



        CallBtn = findViewById(R.id.CallToOrder);

        CallBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:" + "01148694280"));
                if (ActivityCompat.checkSelfPermission(CardActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    Toast.makeText(CardActivity.this,"Turn On Phone Permission to Call",Toast.LENGTH_SHORT).show();
                    return;
                }
                startActivity(intent);
            }
        });

        CompleteOrderBtn = findViewById(R.id.CompleteOrder);

        CompleteOrderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {

                Intent intent = new Intent(CardActivity.this ,OrderCheckout.class   );
                startActivity(intent);
             /*   if (myAdapter.products.size() > 0) {
                    DialogInterface.OnClickListener diOnClickListener = new DialogInterface.OnClickListener() {
                        @SuppressLint("ResourceAsColor")
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which) {
                                case DialogInterface.BUTTON_POSITIVE:

                                    CreateOrder(myAdapter.TotalPrice, myAdapter.products, myAdapter.productQtu);



                                    break;

                                case DialogInterface.BUTTON_NEGATIVE:
                                    break;

                            }
                        }
                    };

                    AlertDialog.Builder builder = new AlertDialog.Builder(CardActivity.this);
                    builder.setMessage(" Complete Your Order ").setPositiveButton("Yes", diOnClickListener)
                            .setNegativeButton("No", diOnClickListener);

                    builder.show();

                }else
                    Toast.makeText(CardActivity.this, "Your card is empty ", Toast.LENGTH_SHORT).show();



                */

            }
        });



        CardEmpty = findViewById(R.id.CardEmptyOrNot);
        if(myAdapter.products.size()==0) CardActivity.CardEmpty.setVisibility(View.VISIBLE);
        else CardActivity.CardEmpty.setVisibility(View.INVISIBLE);


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
                    if(myAdapter.products.size()==0) CardEmpty.setVisibility(View.VISIBLE);
                    else CardEmpty.setVisibility(View.INVISIBLE);


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



    public   void CreateOrder(double Total_Price , ArrayList<Product> products , ArrayList<Integer> productsQty){



        LocalDateTime myDateObj = LocalDateTime.now();
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

        String OrderDate = myDateObj.format(myFormatObj);
        // System.out.println("After formatting: " + OrderDate);


        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        HashMap<String,Object> productMap = new HashMap<>();

        if(products.size()==productsQty.size()){

            for (int i=0 ; i< products.size();i++){

                productMap.put(products.get(i).getTitle(), productsQty.get(i));
            }
        }
        final HashMap<String,Object> map = new HashMap<>();

        map.put("userId",user.getUid());
        map.put("totalPrice",Total_Price);
        map.put("numOfItem",products.size());
        map.put("ProductsInfo",productMap);
        map.put("orderDate",OrderDate);

        final ProgressDialog pd = new ProgressDialog(CardActivity.this);
        pd.show();
        pd.setContentView(R.layout.progress_dialog);
        pd.getWindow().setBackgroundDrawableResource(R.color.transparent);



        FirebaseDatabase.getInstance().getReference().child("Orders").child(user.getUid()+String.valueOf((int)Total_Price)+String.valueOf(products.size()))
                .updateChildren(map).addOnCompleteListener(new OnCompleteListener<Void>() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){

                    for (int i = 0; i < myAdapter.products.size(); i++) {
                        FireBase.RemoveProductFromCard(myAdapter.products.get(i));
                    }

                    myAdapter.products.clear();
                    myAdapter.notifyDataSetChanged();
                    myAdapter.TotalPrice = 0;


                    FireBase.NumOf_Products_InCard = 0;
                    FireBase.GetNumOf_Products_InCard();

                    FirebaseDatabase.getInstance().getReference().child("MyOrders").child(user.getUid()).push()
                            .updateChildren(map);

                    pd.dismiss();
                    Toast.makeText(CardActivity.this, "The Order was made Successfully ", Toast.LENGTH_SHORT).show();
                    finish();


                }else{
                    Toast.makeText(CardActivity.this ,"Check your network connection ", Toast.LENGTH_LONG).show();
                }
            }
        });

    }


}
