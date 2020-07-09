package com.example.skyapp.Adapter;

import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.skyapp.MainActivity;
import com.example.skyapp.ProductActivity;
import com.example.skyapp.R;
import com.example.skyapp.fragments.FavoriteFragment;
import com.example.skyapp.fragments.HomeFragment;
import com.example.skyapp.model.Product;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class RecyclerFavAdapter  extends RecyclerView.Adapter<MyFavHolder> {
    Context c ;
    public ArrayList<Product> products;

    public RecyclerFavAdapter(Context c, ArrayList<Product> products) {
        this.c = c;
        this.products = products;

    }

    public void addNewData(Product product){
        boolean add = true;
        for (int i=0 ;i<products.size();i++)
        {
            if(product.getDescription().equals(products.get(i).getDescription()))
            {   add=false;  break;  }
        }
        if(add == true)  products.add(product);

        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public MyFavHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fav_item, null) ;

        return new MyFavHolder(view) ;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyFavHolder holder, final int position) {
        holder.mTitle.setText(products.get(position).getTitle());

        double price_before = products.get(position).getPrice();
        double discount =   products.get(position).getDiscount() ;
        double price_after =  price_before - (price_before * (  discount/100));

        holder.price_after.setText(String.format("%,.2f",price_after) + " EGP");

        try {
            Picasso.get().load(products.get(position).getImage_url()).into(holder.mImageView);
        }catch (Exception e){

        }

        // to check if product in Fav or not
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("UserFav").child(user.getUid());



        // to check if product in Card or not
        ref = FirebaseDatabase.getInstance().getReference().child("UserCards").child(user.getUid());
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.child(products.get(position).getTitle()+products.get(position).getBrand()+products.get(position).getColor()).exists()){
                    holder.AddToCardImg.setImageResource(R.drawable.ic_added_tocard);
                    holder.AddedToCard =true;
                }else{
                    holder.AddToCardImg.setImageResource(R.drawable.ic_add_shopping_card);

                    holder.AddedToCard =false;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        holder.AddToCardImg.setOnClickListener(new View.OnClickListener() {
                                                @SuppressLint("ResourceAsColor")
                                                @Override
                                                public void onClick(View v) {
                                                    FireBase.AddToUser_Card(products.get(position));

                                                    Snackbar snackbar =Snackbar.make(v ,products.get(position).getTitle()+" Added to your card",Snackbar.LENGTH_SHORT);
                                                    snackbar.setBackgroundTint(R.color.colorPrimaryDark);

                                                    snackbar.show();
                                                    holder.AddToCardImg.setImageResource(R.drawable.ic_added_tocard);
                                                    FireBase.GetNumOf_Products_InCard();

                                                }
                                            }
        );
        holder.RemoveFromFav.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {
                Snackbar snackbar =Snackbar.make(v ,products.get(position).getTitle()+" Removed from Favorite ",Snackbar.LENGTH_SHORT);
                snackbar.setBackgroundTint(R.color.colorPrimaryDark);
                snackbar.show();


                FireBase.RemoveProductFromFav(products.get(position));
                products.remove(position);
                notifyDataSetChanged();
                FireBase.GetNumOf_Products_InCard();

                HomeFragment.DealsAdapter.notifyDataSetChanged();
                HomeFragment.BestSealsAdapter.notifyDataSetChanged();
                HomeFragment.LaptopAdapter.notifyDataSetChanged();
                HomeFragment.MobilesAdapter.notifyDataSetChanged();
                FavoriteFragment.FavAdapter.notifyDataSetChanged();

                if(products.size()==0) FavoriteFragment.RelativeLayoutNoItem.setVisibility(View.VISIBLE);
                else FavoriteFragment.RelativeLayoutNoItem.setVisibility(View.INVISIBLE);


            }
        });


        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClickListener(View v, int position) {


                Intent intent = new Intent( c, ProductActivity.class);
                intent.putExtra("ProductObject", products.get(position));


                Pair[] pairs = new Pair[2];
                pairs[0]= new Pair<View , String>(holder.mImageView,"ImageTransition");
                pairs[1]= new Pair<View , String>(holder.mTitle,"TitleTransition");
                /* pairs[2]= new Pair<View , String>(holder.mTitle,"PriceTransition");*/

                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(  (MainActivity)c ,pairs);

                c.startActivity(intent , options.toBundle());

            }
        });




    /* holder.setItemClickListener(new ItemClickListener() {
         @Override
         public void onItemClickListener(View v, int position) {
             if( models.get(position).getTitle().equals("New Feeds")){
                 // move to another activity
             }
         }
     });*/
        if(products.size()>0){
            FavoriteFragment.RelativeLayoutNoItem.setVisibility(View.INVISIBLE);
        }


    }

    @Override
    public int getItemCount() {

        if(products.size()>0){
            FavoriteFragment.RelativeLayoutNoItem.setVisibility(View.INVISIBLE);
        }

        return products.size();
    }
}
