package com.example.skyapp.Adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.skyapp.DealsActivity;
import com.example.skyapp.ElectronicsActivity;
import com.example.skyapp.FashionActivity;
import com.example.skyapp.LaptopActivity;
import com.example.skyapp.MainActivity;
import com.example.skyapp.MobilesActivity;
import com.example.skyapp.ProductActivity;
import com.example.skyapp.RegisterActivity;
import com.example.skyapp.SuperMarketActivity;
import com.example.skyapp.model.Product;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
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
            NumOf_Products_InCard = (int) dataSnapshot.getChildrenCount();
            if (FireBase.NumOf_Products_InCard > 0) {
                MainActivity.CardCount.setVisibility(View.VISIBLE);
                MainActivity.CardCount.setText(String.valueOf(FireBase.NumOf_Products_InCard));
                if (ProductActivity.CardCount_ != null) {
                    ProductActivity.CardCount_.setVisibility(View.VISIBLE);
                    ProductActivity.CardCount_.setText(String.valueOf(FireBase.NumOf_Products_InCard));
                }
                if (DealsActivity.DealsCardCount_ != null) {
                    DealsActivity.DealsCardCount_.setVisibility(View.VISIBLE);
                    DealsActivity.DealsCardCount_.setText(String.valueOf(FireBase.NumOf_Products_InCard));
                }
                if (SuperMarketActivity.SupermarketCardCount_ != null) {
                    SuperMarketActivity.SupermarketCardCount_.setVisibility(View.VISIBLE);
                    SuperMarketActivity.SupermarketCardCount_.setText(String.valueOf(FireBase.NumOf_Products_InCard));
                }
                if (LaptopActivity.LaptopCardCount_ != null) {
                    LaptopActivity.LaptopCardCount_.setVisibility(View.VISIBLE);
                    LaptopActivity.LaptopCardCount_.setText(String.valueOf(FireBase.NumOf_Products_InCard));
                }
                if (MobilesActivity.MobilesCardCount_ != null) {
                    MobilesActivity.MobilesCardCount_.setVisibility(View.VISIBLE);
                    MobilesActivity.MobilesCardCount_.setText(String.valueOf(FireBase.NumOf_Products_InCard));
                }
                if (ElectronicsActivity.ElectronicsCardCount_ != null) {
                    ElectronicsActivity.ElectronicsCardCount_.setVisibility(View.VISIBLE);
                    ElectronicsActivity.ElectronicsCardCount_.setText(String.valueOf(FireBase.NumOf_Products_InCard));
                }
                if (FashionActivity.FashionCardCount_ != null) {
                    FashionActivity.FashionCardCount_.setVisibility(View.VISIBLE);
                    FashionActivity.FashionCardCount_.setText(String.valueOf(FireBase.NumOf_Products_InCard));
                }
            } else {
                MainActivity.CardCount.setVisibility(View.INVISIBLE);
                if (ProductActivity.CardCount_ != null) {
                    ProductActivity.CardCount_.setVisibility(View.INVISIBLE);
                }

                if (DealsActivity.DealsCardCount_ != null) {
                    DealsActivity.DealsCardCount_.setVisibility(View.INVISIBLE);
                }
                if (SuperMarketActivity.SupermarketCardCount_ != null) {
                    SuperMarketActivity.SupermarketCardCount_.setVisibility(View.INVISIBLE);
                }
                if (LaptopActivity.LaptopCardCount_ != null) {
                    LaptopActivity.LaptopCardCount_.setVisibility(View.INVISIBLE);
                }
                if (MobilesActivity.MobilesCardCount_ != null) {
                    MobilesActivity.MobilesCardCount_.setVisibility(View.INVISIBLE);
                }
                if (ElectronicsActivity.ElectronicsCardCount_ != null) {
                    ElectronicsActivity.ElectronicsCardCount_.setVisibility(View.INVISIBLE);
                }
                if (FashionActivity.FashionCardCount_ != null) {
                    FashionActivity.FashionCardCount_.setVisibility(View.INVISIBLE);
                }

            }

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


   /* public static  void CreateOrder(double Total_Price , ArrayList<Product> products , ArrayList<Integer> productsQty, Context c){



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
        HashMap<String,Object> map = new HashMap<>();

         map.put("userId",user.getUid());
         map.put("totalPrice",Total_Price);
         map.put("numOfItem",products.size());
         map.put("ProductsInfo",productMap);
         map.put("orderDate",OrderDate);

        final ProgressDialog pd = new ProgressDialog(c);
        pd.setMessage("Please Wail!");
        pd.show();


        FirebaseDatabase.getInstance().getReference().child("Orders").child(user.getUid()+String.valueOf((int)Total_Price)+String.valueOf(products.size()))
                .updateChildren(map).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    pd.dismiss();

                }
            }
        });

    }*/

   public static  boolean isNetworkAvailable(Context c){
       ConnectivityManager connectivityManager
               =(ConnectivityManager) c.getSystemService(Context.CONNECTIVITY_SERVICE);
       NetworkInfo aNetworkInfo =connectivityManager.getActiveNetworkInfo();
       return aNetworkInfo!=null && aNetworkInfo.isConnected();

   }

}
