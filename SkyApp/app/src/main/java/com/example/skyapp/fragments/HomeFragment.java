package com.example.skyapp.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.FileUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.example.skyapp.Adapter.RecyclerAdapter;
import com.example.skyapp.R;
import com.example.skyapp.model.Product;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class HomeFragment extends Fragment {
    RecyclerView DealsRecyclerView;
   public static RecyclerAdapter DealsAdapter;
    RecyclerView BestSealsRecyclerView;
    public static RecyclerAdapter BestSealsAdapter;

    RecyclerView LaptopRecyclerView;
    public static RecyclerAdapter LaptopAdapter;

    RecyclerView MobilesRecyclerView;
    public static RecyclerAdapter MobilesAdapter;


    ViewFlipper viewFlipper;
         ArrayList<Product> products = new ArrayList<>() ;
    ArrayList<Product> products2 = new ArrayList<>() ;
    ArrayList<Product> products3 = new ArrayList<>() ;
    ArrayList<Product> products4 = new ArrayList<>() ;

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
        DealsRecyclerView =view.findViewById(R.id.DealsRecyclerView);

        BestSealsRecyclerView =view.findViewById(R.id.BestRecyclerView);
        LaptopRecyclerView = view.findViewById(R.id.LaptopRecyclerView);
        MobilesRecyclerView = view.findViewById(R.id.MobilesRecyclerView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(
                c ,LinearLayoutManager.HORIZONTAL,false
        );
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(
                c ,LinearLayoutManager.HORIZONTAL,false
        );
        LinearLayoutManager layoutManager3 = new LinearLayoutManager(
                c ,LinearLayoutManager.HORIZONTAL,false
        );
        LinearLayoutManager layoutManager4 = new LinearLayoutManager(
                c ,LinearLayoutManager.HORIZONTAL,false
        );
        /*LinearLayoutManager layoutManager = new GridLayoutManager(
                c ,2
        );*/

        DealsRecyclerView.setLayoutManager(layoutManager);
        BestSealsRecyclerView.setLayoutManager(layoutManager2);
        LaptopRecyclerView.setLayoutManager(layoutManager3);
        MobilesRecyclerView.setLayoutManager(layoutManager4);


        DealsRecyclerView.setItemAnimator(new DefaultItemAnimator());
        BestSealsRecyclerView.setItemAnimator(new DefaultItemAnimator());
        LaptopRecyclerView.setItemAnimator(new DefaultItemAnimator());
        MobilesRecyclerView.setItemAnimator(new DefaultItemAnimator());

        DealsAdapter = new RecyclerAdapter( c, products , 1);
        BestSealsAdapter = new RecyclerAdapter( c, products2, 1);
        LaptopAdapter =new RecyclerAdapter( c, products3, 1);
        MobilesAdapter = new RecyclerAdapter( c, products4, 1);

        DealsRecyclerView.setAdapter(DealsAdapter);
        BestSealsRecyclerView.setAdapter(BestSealsAdapter);
        LaptopRecyclerView.setAdapter(LaptopAdapter);
        MobilesRecyclerView.setAdapter(MobilesAdapter);


        getMyList("Deals", DealsAdapter);
        getMyList("Best Sales", BestSealsAdapter);
        getMyList("Laptop and Tablets",LaptopAdapter);
        getMyList("Mobiles",MobilesAdapter);




        return view;

    }




    private void getMyList(String category , RecyclerAdapter Adapter) {

      final  RecyclerAdapter MyAdapt = Adapter;
        products.clear();
        products2.clear();
        products3.clear();
        products4.clear();

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

    }

}
