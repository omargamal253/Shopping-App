package com.example.skyapp.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.skyapp.DealsActivity;
import com.example.skyapp.ElectronicsActivity;
import com.example.skyapp.FashionActivity;
import com.example.skyapp.LaptopActivity;
import com.example.skyapp.MainActivity;
import com.example.skyapp.MobilesActivity;
import com.example.skyapp.ProductActivity;
import com.example.skyapp.R;
import com.example.skyapp.SuperMarketActivity;
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
    int BaseActivity;
    ActivityOptions options;
    public RecyclerAdapter(Context c, ArrayList<Product> products , int layout, int BaseActivity) {
        this.c = c;
        this.products = products;
        this.layout = layout;
        this.BaseActivity = BaseActivity;
        // 1 -> MainActivity / 2 -> DealActivity /  3 ->Supermarket  / 4 -> Laptop /  5 -> Mobiles / 6 -> Electronics / 7-> Fashion

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
            holder.price_before.setPaintFlags(holder.price_before.getPaintFlags()| Paint.STRIKE_THRU_TEXT_FLAG);
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
                    if(holder.AddToCard!=null)
                        holder.AddToCard.setText("Added");
                    holder.AddedToCard =true;
                }else{
                    if(holder.AddToCard!=null)
                        holder.AddToCard.setText("Add TO CARD");
                    holder.AddedToCard =false;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

if(holder.AddToCard!=null)
        holder.AddToCard.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {
                if (holder.AddedToCard == false) {

                    FireBase.AddToUser_Card(products.get(position));

                    Snackbar snackbar = Snackbar.make(v,  "New Product Added to your cart", Snackbar.LENGTH_SHORT);
                    snackbar.setBackgroundTint(R.color.colorPrimaryDark);


                    snackbar.show();
                    holder.AddToCard.setText("Added");
                    FireBase.GetNumOf_Products_InCard();
                    holder.AddedToCard =true;

                }
            }
        }
        );
        holder.HomeFav.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {

                if(holder.AddedToFav ==false){
                    holder.HomeFav.setImageResource(R.drawable.ic_fav);

                    Snackbar snackbar =Snackbar.make(v ,"New Product Saved to your Favorites ",Snackbar.LENGTH_SHORT);
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


                Pair[] pairs = new Pair[2];
                pairs[0]= new Pair<View , String>(holder.mImageView,"ImageTransition");
                pairs[1]= new Pair<View , String>(holder.mTitle,"TitleTransition");
               /* pairs[2]= new Pair<View , String>(holder.mTitle,"PriceTransition");*/

              if(BaseActivity==1)   options = ActivityOptions.makeSceneTransitionAnimation(  (MainActivity)c ,pairs);
              else if(BaseActivity==2)   options = ActivityOptions.makeSceneTransitionAnimation(  (DealsActivity)c ,pairs);
              else if(BaseActivity==3)   options = ActivityOptions.makeSceneTransitionAnimation(  (SuperMarketActivity)c ,pairs);
              else if(BaseActivity==4)   options = ActivityOptions.makeSceneTransitionAnimation(  (LaptopActivity)c ,pairs);
              else if(BaseActivity==5)   options = ActivityOptions.makeSceneTransitionAnimation(  (MobilesActivity)c ,pairs);
              else if(BaseActivity==6)   options = ActivityOptions.makeSceneTransitionAnimation(  (ElectronicsActivity)c ,pairs);
              else if(BaseActivity==7)   options = ActivityOptions.makeSceneTransitionAnimation(  (FashionActivity)c ,pairs);
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

    }

    @Override
    public int getItemCount() {
        return products.size();
    }

}
