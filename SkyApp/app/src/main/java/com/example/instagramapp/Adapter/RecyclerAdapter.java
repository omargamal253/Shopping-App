package com.example.instagramapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.instagramapp.ProductActivity;
import com.example.instagramapp.R;
import com.example.instagramapp.model.Product;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class RecyclerAdapter  extends RecyclerView.Adapter<MyHolder> {
    Context c ;
    ArrayList<Product> products;

    public RecyclerAdapter(Context c, ArrayList<Product> products) {
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
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item, null) ;
        return new MyHolder(view) ;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyHolder holder, int position) {
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

       // holder.mImageView.setImageURI();


        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClickListener(View v, int position) {

                /*String gTitle = products.get(position).getTitle();
                String gDesc = products.get(position).getDescription();

                BitmapDrawable bitmapDrawable = (BitmapDrawable) holder.mImageView.getDrawable();
                Bitmap bitmap = bitmapDrawable.getBitmap();
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.WEBP,100,stream);
                byte[] bytes = stream.toByteArray();

                Intent intent = new Intent(c,ClickedActivity.class);
                intent.putExtra("iTitle" , gTitle);
                intent.putExtra("iDesc" ,gDesc);
                intent.putExtra("iImage" , bytes);*/
                Intent intent = new Intent( c, ProductActivity.class);
                intent.putExtra("ProductObject", products.get(position));
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
