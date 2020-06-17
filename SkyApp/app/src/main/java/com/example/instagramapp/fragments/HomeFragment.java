package com.example.instagramapp.fragments;

import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.example.instagramapp.Adapter.RecyclerAdapter;
import com.example.instagramapp.AddItemActivity;
import com.example.instagramapp.R;
import com.example.instagramapp.model.Product;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class HomeFragment extends Fragment {
    RecyclerView mreRecyclerView;
    RecyclerAdapter myAdapter;
    RecyclerView mreRecyclerView2;
    RecyclerAdapter myAdapter2;

    ViewFlipper viewFlipper;
         ArrayList<Product> products = new ArrayList<>() ;
    ArrayList<Product> products2 = new ArrayList<>() ;
    public HomeFragment() {
        // Required empty public constructor

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
final View view = inflater.inflate(R.layout.fragment_home, container, false);
final FragmentActivity c =getActivity();


        viewFlipper = view.findViewById(R.id.flipperId);
        viewFlipper.startFlipping();
        mreRecyclerView =view.findViewById(R.id.DealsRecyclerView);

        mreRecyclerView2 =view.findViewById(R.id.BestRecyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(
                c ,LinearLayoutManager.HORIZONTAL,false
        );
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(
                c ,LinearLayoutManager.HORIZONTAL,false
        );
        /*LinearLayoutManager layoutManager = new GridLayoutManager(
                c ,2
        );*/

        mreRecyclerView.setLayoutManager(layoutManager);
        mreRecyclerView2.setLayoutManager(layoutManager2);


        mreRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mreRecyclerView2.setItemAnimator(new DefaultItemAnimator());

        myAdapter = new RecyclerAdapter( c, products);
        myAdapter2 = new RecyclerAdapter( c, products2);

        mreRecyclerView.setAdapter(myAdapter);
        mreRecyclerView2.setAdapter(myAdapter2);


        getMyList("Deals",myAdapter);
        getMyList("Best Sales",myAdapter2);



        return view;

    }




    private void getMyList(String category , RecyclerAdapter Adapter) {

      final  RecyclerAdapter MyAdapt = Adapter;
        products.clear();
        products2.clear();

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
                Toast.makeText(getContext(), "fail to load list",Toast.LENGTH_SHORT).show();

            }
        });

       products.clear();

/*


        product.setTitle("Michael Kors Brinkley Men's Gold Dial Stainless Steel Band Watch - MK6187");
        product.setDescription("Description:\n" +
                "Product Features:\n" +
                "\n" +
                "Brand: Michael Kors\n" +
                "Watch Shape: Round\n" );

        //product.setImg("https://firebasestorage.googleapis.com/v0/b/skyapp-f0431.appspot.com/o/uploads%2FDell%20G3dellblack?alt=media&token=62d79d77-069f-43c9-b5f0-ce3d98ce7581");
        //Picasso.get().load(products.get(position).getImage_url()).into(holder.mImageView);

        product.setPrice(15700);
product.setDiscount(16);

        products.add(product);
        for(int i=1 ;i< 4;i++){
             product = new Product();
            product.setTitle("Michael Kors Brinkley Men's Gold Dial Stainless Steel Band Watch - MK6187");
            product.setDescription("Description:\n" +
                    "Product Features:\n" +
                    "\n" +
                    "Brand: Michael Kors\n" +
                    "Watch Shape: Round\n" );
            //product.setImg(R.drawable.watch1);
            product.setPrice(1000);
            product.setDiscount(16);

            products.add(product);


        }*/
        //return products;

    }

}
