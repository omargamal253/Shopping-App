package com.example.instagramapp.Adapter;

import com.example.instagramapp.model.Product;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class FireBase {
   //static HashMap map ;

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
}
