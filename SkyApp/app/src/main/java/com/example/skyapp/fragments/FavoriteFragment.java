package com.example.skyapp.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.skyapp.Adapter.FireBase;
import com.example.skyapp.Adapter.RecyclerFavAdapter;
import com.example.skyapp.R;
import com.example.skyapp.model.Product;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteFragment extends Fragment {
    RecyclerView FavRecyclerView;
    public static RecyclerFavAdapter FavAdapter;
    ArrayList<Product> products = new ArrayList<>() ;
    public static  RelativeLayout relativeLayout ;

    public FavoriteFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_favorite, container, false);
        final FragmentActivity c =getActivity();

        FavRecyclerView =view.findViewById(R.id.FavRecyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(
                c ,LinearLayoutManager.VERTICAL,false
        );
        FavRecyclerView.setLayoutManager(layoutManager);
        FavRecyclerView.setItemAnimator(new DefaultItemAnimator());

        FavAdapter = new RecyclerFavAdapter( c, products);
        FavRecyclerView.setAdapter(FavAdapter);

        getMyList(FavAdapter);



        ItemTouchHelper.SimpleCallback simpleCallback =new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                Toast.makeText(view.getContext(),"On Swipe",Toast.LENGTH_SHORT).show();
                return false;
            }

            @SuppressLint("ResourceAsColor")
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                    int pos = viewHolder.getAdapterPosition();
                Snackbar snackbar =Snackbar.make(view ,FavAdapter.products.get(pos).getTitle()+" Removed from Favorite ",Snackbar.LENGTH_SHORT);
                snackbar.setBackgroundTint(R.color.colorPrimaryDark);
                snackbar.show();


                FireBase.RemoveProductFromFav(FavAdapter.products.get(pos));
                FavAdapter.products.remove(pos);
                FavAdapter.notifyDataSetChanged();

                FireBase.GetNumOf_Products_InCard();

                HomeFragment.DealsAdapter.notifyDataSetChanged();
                HomeFragment.BestSealsAdapter.notifyDataSetChanged();
                FavAdapter.notifyDataSetChanged();

            }
        };
        ItemTouchHelper itemTouchHelper =new ItemTouchHelper(simpleCallback) ;
        itemTouchHelper.attachToRecyclerView(FavRecyclerView);

TextView t = view.findViewById(R.id.noSaved);


        relativeLayout = view.findViewById(R.id.RelativeNoItem);

        // Inflate the layout for this fragment

        return view;
    }

    private void getMyList( RecyclerFavAdapter Adapter) {

        final  RecyclerFavAdapter MyAdapt = Adapter;
        products.clear();

        Product product = new Product();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference().child("UserFav").child(user.getUid());


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
