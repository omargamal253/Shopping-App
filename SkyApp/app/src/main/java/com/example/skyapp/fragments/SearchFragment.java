package com.example.skyapp.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.skyapp.Adapter.RecyclerAdapter;
import com.example.skyapp.Adapter.Utils;
import com.example.skyapp.FashionActivity;
import com.example.skyapp.R;
import com.example.skyapp.model.Product;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.hendraanggrian.appcompat.widget.SocialAutoCompleteTextView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment {
    androidx.recyclerview.widget.RecyclerView RecyclerView , RecyclerView2 , RecyclerView3
            , RecyclerView4, RecyclerView5, RecyclerView6, RecyclerView7;
    RecyclerAdapter Adapter, Adapter2, Adapter3, Adapter4, Adapter5, Adapter6, Adapter7;

    ArrayList<Product> products = new ArrayList<>() ;
    ArrayList<Product> products2 = new ArrayList<>() ;
    ArrayList<Product> products3 = new ArrayList<>() ;
    ArrayList<Product> products4 = new ArrayList<>() ;
    ArrayList<Product> products5 = new ArrayList<>() ;
    ArrayList<Product> products6 = new ArrayList<>() ;
    ArrayList<Product> products7 = new ArrayList<>() ;

   // private SocialAutoCompleteTextView search_bar; //com.hendraanggrian.appcompat.widget.SocialAutoCompleteTextView
   private EditText search_bar;

    public SearchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_search, container, false);
        final FragmentActivity c =getActivity();

        RecyclerView =view.findViewById(R.id.recycler_view_products);
        RecyclerView2 =view.findViewById(R.id.recycler_view_products2);
        RecyclerView3 =view.findViewById(R.id.recycler_view_products3);
        RecyclerView4 =view.findViewById(R.id.recycler_view_products4);
        RecyclerView5 =view.findViewById(R.id.recycler_view_products5);
        RecyclerView6 =view.findViewById(R.id.recycler_view_products6);
        RecyclerView7 =view.findViewById(R.id.recycler_view_products7);


        LinearLayoutManager layoutManager = new GridLayoutManager(
                c ,1
        );
        LinearLayoutManager layoutManager2 = new GridLayoutManager(
                c ,1
        );
        LinearLayoutManager layoutManager3 = new GridLayoutManager(
                c ,1
        );
        LinearLayoutManager layoutManager4 = new GridLayoutManager(
                c ,1
        );
        LinearLayoutManager layoutManager5 = new GridLayoutManager(
                c ,1
        );
        LinearLayoutManager layoutManager6 = new GridLayoutManager(
                c ,1
        );
        LinearLayoutManager layoutManager7 = new GridLayoutManager(
                c ,1
        );
        RecyclerView.setLayoutManager(layoutManager);
        RecyclerView.setItemAnimator(new DefaultItemAnimator());
        Adapter = new RecyclerAdapter( c, products , 4);
        RecyclerView.setAdapter(Adapter);

        RecyclerView2.setLayoutManager(layoutManager2);
        RecyclerView2.setItemAnimator(new DefaultItemAnimator());
        Adapter2 = new RecyclerAdapter( c, products2 , 4);
        RecyclerView2.setAdapter(Adapter2);

        RecyclerView3.setLayoutManager(layoutManager3);
        RecyclerView3.setItemAnimator(new DefaultItemAnimator());
        Adapter3 = new RecyclerAdapter( c, products3 , 4);
        RecyclerView3.setAdapter(Adapter3);

        RecyclerView4.setLayoutManager(layoutManager4);
        RecyclerView4.setItemAnimator(new DefaultItemAnimator());
        Adapter4 = new RecyclerAdapter( c, products4 , 4);
        RecyclerView4.setAdapter(Adapter4);

        RecyclerView5.setLayoutManager(layoutManager5);
        RecyclerView5.setItemAnimator(new DefaultItemAnimator());
        Adapter5 = new RecyclerAdapter( c, products5 , 4);
        RecyclerView5.setAdapter(Adapter5);

        RecyclerView6.setLayoutManager(layoutManager6);
        RecyclerView6.setItemAnimator(new DefaultItemAnimator());
        Adapter6 = new RecyclerAdapter( c, products6 , 4);
        RecyclerView6.setAdapter(Adapter6);

        RecyclerView7.setLayoutManager(layoutManager7);
        RecyclerView7.setItemAnimator(new DefaultItemAnimator());
        Adapter7 = new RecyclerAdapter( c, products7 , 4);
        RecyclerView7.setAdapter(Adapter7);

        search_bar = view.findViewById(R.id.search_bar);
        search_bar.setFocusable(true);

        search_bar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                }
            @Override
            public void afterTextChanged(Editable s) {
                    if (s.toString().equals("")) {
                        Adapter.products.clear();
                        Adapter.notifyDataSetChanged();
                        Adapter2.products.clear();
                        Adapter2.notifyDataSetChanged();
                        Adapter3.products.clear();
                        Adapter3.notifyDataSetChanged();
                        Adapter4.products.clear();
                        Adapter4.notifyDataSetChanged();
                        Adapter5.products.clear();
                        Adapter5.notifyDataSetChanged();
                        Adapter6.products.clear();
                        Adapter6.notifyDataSetChanged();
                        Adapter7.products.clear();
                        Adapter7.notifyDataSetChanged();

                    } else {
                        try {
                            if(s.toString().length()%2!=0) {

                                searchProducts(s.toString(), "Deals", Adapter);
                                searchProducts(s.toString(), "Best Sales", Adapter2);
                                searchProducts(s.toString(), "Supermarket", Adapter3);
                                searchProducts(s.toString(), "Laptop and Tablets", Adapter4);
                                searchProducts(s.toString(), "Mobiles", Adapter5);
                                searchProducts(s.toString(), "Electronics", Adapter6);
                                searchProducts(s.toString(), "Fashion", Adapter7);
                            }

                        } catch (Exception e) {
                            Toast.makeText(c, e.getMessage(), Toast.LENGTH_SHORT).show();

                        }

                    }

            }
        });




        return view;
    }


    private void searchProducts(String s ,String category , RecyclerAdapter Adapter) {

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


        products.clear();

    }



}
