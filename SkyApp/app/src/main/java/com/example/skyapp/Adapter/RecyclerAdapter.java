package com.example.skyapp.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.skyapp.ProductActivity;
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
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RecyclerAdapter  extends RecyclerView.Adapter<MyHolder> {
    Context c ;
    public ArrayList<Product> products;
    int layout;
    public RecyclerAdapter(Context c, ArrayList<Product> products , int layout) {
        this.c = c;
        this.products = products;
        this.layout = layout;
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
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item, null) ;

        if(layout == 1)
        return new MyHolder(view) ;
        else if (layout == 2)
        {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.deal_item, null) ;
            return new MyHolder(view) ;
        }else if (layout == 3)
        {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.mobile_item, null) ;
            return new MyHolder(view) ;
        }
        else if (layout == 4)
        {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fashion_item, null) ;
            return new MyHolder(view) ;
        }
        else return  null;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyHolder holder, final int position) {
        holder.mTitle.setText(products.get(position).getTitle());

        if(products.get(position).getDiscount()>0)
        {
            holder.discount.setText(String.valueOf(products.get(position).getDiscount() + "% OFF"));
          //  holder.price_before.setText(String.valueOf(products.get(position).getPrice()));
            double price_before = products.get(position).getPrice();
            holder.price_before.setText( String.format("%,.2f",price_before) + " EGP");
            double discount =   products.get(position).getDiscount() ;
            double price_after =  price_before - (price_before * (  discount/100));

            holder.price_after.setText(String.format("%,.2f",price_after) + " EGP");

        }
        else {
            holder.discount.setVisibility(View.INVISIBLE);
            holder.price_before.setVisibility(View.INVISIBLE);
            holder.price_after.setText(  String.valueOf(products.get(position).getPrice()));

        }
         try {
             Picasso.get().load(products.get(position).getImage_url()).into(holder.mImageView);
         }catch (Exception e){

         }

         // to check if product in Fav or not
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("UserFav").child(user.getUid());
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.child(products.get(position).getTitle()+products.get(position).getBrand()+products.get(position).getColor()).exists()){
                    holder.HomeFav.setImageResource(R.drawable.ic_fav);
                    holder.AddedToFav =true;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        // to check if product in Card or not
         ref = FirebaseDatabase.getInstance().getReference().child("UserCards").child(user.getUid());
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.child(products.get(position).getTitle()+products.get(position).getBrand()+products.get(position).getColor()).exists()){
                    holder.AddToCard.setText("Added");
                    holder.AddedToCard =true;
                }else{
                    holder.AddToCard.setText("Add TO CARD");
                    holder.AddedToCard =false;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        holder.AddToCard.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {
              FireBase.AddToUser_Card(products.get(position));

                Snackbar snackbar =Snackbar.make(v ,products.get(position).getTitle()+" Added to your card",Snackbar.LENGTH_SHORT);
                snackbar.setBackgroundTint(R.color.colorPrimaryDark);


                snackbar.show();
                  holder.AddToCard.setText("Added");
                FireBase.GetNumOf_Products_InCard();

            }
        }
        );
        holder.HomeFav.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {

                if(holder.AddedToFav ==false){
                    holder.HomeFav.setImageResource(R.drawable.ic_fav);

                    Snackbar snackbar =Snackbar.make(v ,products.get(position).getTitle()+" Saved to your Favorites ",Snackbar.LENGTH_SHORT);
                    snackbar.setBackgroundTint(R.color.colorPrimaryDark);
                    snackbar.show();
                    FireBase.AddToUser_Fav(products.get(position));
                    holder.AddedToFav =true;

                }
            }
        });

        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClickListener(View v, int position) {

                Intent intent = new Intent( c, ProductActivity.class);
                intent.putExtra("ProductObject", products.get(position));
                intent.putExtra("AddedToCardOrNot", holder.AddedToCard);
                intent.putExtra("AddedToFavOrNot",holder.AddedToFav);
                c.startActivity(intent);

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

    }

    @Override
    public int getItemCount() {
        return products.size();
    }
}
