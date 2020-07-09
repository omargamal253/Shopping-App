package com.example.skyapp.Adapter;

import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.skyapp.CardActivity;
import com.example.skyapp.DealsActivity;
import com.example.skyapp.ElectronicsActivity;
import com.example.skyapp.FashionActivity;
import com.example.skyapp.LaptopActivity;
import com.example.skyapp.MainActivity;
import com.example.skyapp.MobilesActivity;
import com.example.skyapp.ProductActivity;
import com.example.skyapp.R;
import com.example.skyapp.SuperMarketActivity;
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

public class RecyclerCardAdapter extends RecyclerView.Adapter<MyCardHolder>{
    Context c ;
    public ArrayList<Product> products;
   public ArrayList<Integer>  productQtu=new ArrayList<>();

    public double TotalPrice;
    public RecyclerCardAdapter(Context c, ArrayList<Product> products) {
        this.c = c;
        this.products = products;
        TotalPrice=0;


    }
    public void addNewData(Product product){
        boolean add = true;
        for (int i=0 ;i<products.size();i++)
        {
            if(product.getDescription().equals(products.get(i).getDescription()))
            {   add=false;  break;  }
        }
        if(add == true)  {
            products.add(product);
            productQtu.add(1);
            double price_before = product.getPrice();
            double discount =   product.getDiscount() ;
            double price_after =  price_before - (price_before * (  discount/100));

            TotalPrice+=price_after;

        }
        CardActivity.TotalTextView.setText(String.format("%,.2f",TotalPrice)+" EGP");


        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public MyCardHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item, null) ;
        return new MyCardHolder(view) ;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyCardHolder holder, final int position) {

       holder.product = products.get(position);
        holder.mTitle.setText(products.get(position).getTitle());

        if(products.get(position).getDiscount()>0)
        {
            holder.discount.setText(String.valueOf(products.get(position).getDiscount() + "% OFF"));
            //  holder.price_before.setText(String.valueOf(products.get(position).getPrice()));
            double price_before = products.get(position).getPrice();
            holder.price_before.setText( String.format("%,.2f",price_before) + " EGP");
            double discount =   products.get(position).getDiscount() ;
            double price_after =  price_before - (price_before * (  discount/100));


       //     CardActivity.TotalTextView.setText(String.format("%,.2f",TotalPrice)+" EGP");
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

        holder.ProductQty.setText(String.valueOf(holder.UserQTY));


        holder.AddQty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.UserQTY++;
                productQtu.set(position,holder.UserQTY);
                holder.ProductQty.setText(String.valueOf(holder.UserQTY));
               //TotalPrice+=Double.valueOf(holder.price_after.getText().toString()) ;


                double price_before = products.get(position).getPrice();

                double discount =   products.get(position).getDiscount() ;
                double price_after =  price_before - (price_before * (  discount/100));

                TotalPrice+=price_after;
                CardActivity.TotalTextView.setText(String.format("%,.2f",TotalPrice)+" EGP");

            }
        });

        holder.SubtractQty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.UserQTY > 1) {
                    holder.UserQTY--;
                    productQtu.set(position,holder.UserQTY);

                    holder.ProductQty.setText(String.valueOf(holder.UserQTY));


                    double price_before = products.get(position).getPrice();

                    double discount =   products.get(position).getDiscount() ;
                    double price_after =  price_before - (price_before * (  discount/100));

                    TotalPrice-=price_after;
                    CardActivity.TotalTextView.setText(String.format("%,.2f",TotalPrice)+" EGP");

                }
            }
        });


        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("UserFav").child(user.getUid());
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.child(products.get(position).getTitle()+products.get(position).getBrand()+products.get(position).getColor()).exists()){
                    holder.FavProduct.setImageResource(R.drawable.ic_fav);
                    holder.AddedToFav =true;

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        holder.FavProduct.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {
                if(holder.AddedToFav ==false){
                    holder.FavProduct.setImageResource(R.drawable.ic_fav);

                    Snackbar snackbar =Snackbar.make(v ,products.get(position).getTitle()+" Saved to your Favorites ",Snackbar.LENGTH_SHORT);
                    snackbar.setBackgroundTint(R.color.colorPrimaryDark);
                    snackbar.show();
                     FireBase.AddToUser_Fav(products.get(position));
                     holder.AddedToFav =true;

                }
            }
        });
        holder.RemoveFromCard.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {
                Snackbar snackbar =Snackbar.make(v ,products.get(position).getTitle()+" Removed from Card ",Snackbar.LENGTH_SHORT);
                snackbar.setBackgroundTint(R.color.colorPrimaryDark);
                snackbar.show();

              double price_before = holder.product.getPrice();
                double discount =   holder.product.getDiscount() ;
                double price_after =  price_before - (price_before * (  discount/100));

                TotalPrice-=(price_after*holder.UserQTY);
                CardActivity.TotalTextView.setText(String.format("%,.2f",TotalPrice)+" EGP");


                FireBase.RemoveProductFromCard(products.get(position));
                products.remove(position);
                productQtu.remove(position);

                notifyDataSetChanged();
                FireBase.GetNumOf_Products_InCard();

                HomeFragment.DealsAdapter.notifyDataSetChanged();
                HomeFragment.BestSealsAdapter.notifyDataSetChanged();
                HomeFragment.LaptopAdapter.notifyDataSetChanged();
                HomeFragment.MobilesAdapter.notifyDataSetChanged();

             //   FavoriteFragment.FavAdapter.notifyDataSetChanged();

                if(products.size()==0) CardActivity.CardEmpty.setVisibility(View.VISIBLE);
                           else CardActivity.CardEmpty.setVisibility(View.INVISIBLE);

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

                ActivityOptions   options = ActivityOptions.makeSceneTransitionAnimation(  (CardActivity)c ,pairs);
                c.startActivity(intent , options.toBundle());
            }
        });






    }


    @Override
    public int getItemCount() {
        return products.size();
    }
}
