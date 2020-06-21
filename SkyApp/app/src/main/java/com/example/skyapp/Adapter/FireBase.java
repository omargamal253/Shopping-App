package com.example.skyapp.Adapter;

import android.view.View;

import androidx.annotation.NonNull;

import com.example.skyapp.MainActivity;
import com.example.skyapp.model.Product;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class FireBase {
   //static HashMap map ;
    public  static int NumOf_Products_InCard=0;
    public FireBase() {
    }
    public static void AddProductTo_FireBase(Product product , String Category){

          //String title ,description , color , brand , image_url;
         //double price  , discount ;
        HashMap map  = new HashMap<>();

        map.put("title" , product.getTitle());
        map.put("description" , product.getDescription());
        map.put("color" , product.getColor());
        map.put("brand" , product.getBrand());
        map.put("image_url" , product.getImage_url());
        map.put("price" , product.getPrice());
        map.put("discount" , product.getDiscount());



        // key product.getTitle()+product.getBrand()+product.getColor()
        FirebaseDatabase.getInstance().getReference().child("products").child(Category).child(product.getTitle()+product.getBrand()+product.getColor())
                .updateChildren(map);
         map.clear();
    }
    public static void AddToUser_Card(Product product){
        HashMap map  = new HashMap<>();

        map.put("title" , product.getTitle());
        map.put("description" , product.getDescription());
        map.put("color" , product.getColor());
        map.put("brand" , product.getBrand());
        map.put("image_url" , product.getImage_url());
        map.put("price" , product.getPrice());
        map.put("discount" , product.getDiscount());

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseDatabase.getInstance().getReference().child("UserCards").child(user.getUid()).child(product.getTitle()+product.getBrand()+product.getColor())
                .updateChildren(map);
        map.clear();
       // NumOf_Products_InCard++;

    }
    public  static void AddToUser_Fav(Product product){
        HashMap map  = new HashMap<>();

        map.put("title" , product.getTitle());
        map.put("description" , product.getDescription());
        map .put("color" , product.getColor());
        map.put("brand" , product.getBrand());
        map.put("image_url" , product.getImage_url());
        map.put("price" , product.getPrice());
        map.put("discount" , product.getDiscount());

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseDatabase.getInstance().getReference().child("UserFav").child(user.getUid()).child(product.getTitle()+product.getBrand()+product.getColor())
                .updateChildren(map);
        map.clear();


    }


    public static void GetNumOf_Products_InCard(){
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("UserCards").child(user.getUid());
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                NumOf_Products_InCard= (int)dataSnapshot.getChildrenCount();
                if(FireBase.NumOf_Products_InCard>0)
                {
                    MainActivity.CardCount.setVisibility(View.VISIBLE);
                    MainActivity.CardCount.setText(String.valueOf(FireBase.NumOf_Products_InCard));
                }
                else MainActivity.CardCount.setVisibility(View.INVISIBLE);
               // MainActivity.CardCount.setText(String.valueOf(FireBase.NumOf_Products_InCard));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public static void RemoveProductFromCard(Product product){
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("UserCards")
                .child(user.getUid()).child(product.getTitle()+product.getBrand()+product.getColor());

        ref.removeValue();

    }
    public static void RemoveProductFromFav(Product product){
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("UserFav")
                .child(user.getUid()).child(product.getTitle()+product.getBrand()+product.getColor());

        ref.removeValue();

    }

}
