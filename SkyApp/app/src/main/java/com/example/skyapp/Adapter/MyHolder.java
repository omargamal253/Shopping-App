package com.example.skyapp.Adapter;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.skyapp.R;


public class MyHolder extends RecyclerView.ViewHolder implements  View.OnClickListener {
    ImageView mImageView;
    TextView mTitle , price_before , price_after  , discount;
    Button AddToCard ;
    ImageView HomeFav;
    boolean AddedToFav, AddedToCard;

    ItemClickListener itemClickListener;
    MyHolder(@NonNull View itemView) {
        super(itemView);
        this.mImageView = itemView.findViewById(R.id.imageIv3);
        this.mTitle = itemView.findViewById(R.id.titleIv3);
        this.discount = itemView.findViewById(R.id.discount2);
        this.price_before = itemView.findViewById(R.id.PriceBefore2);
        this.price_after= itemView.findViewById(R.id.PriceAfter3);
        this.AddToCard = itemView.findViewById(R.id.AddToCart_btn);
        this.HomeFav = itemView.findViewById(R.id.Home_Fav);
        AddedToFav =false;
        AddedToCard=false;
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        this.itemClickListener.onItemClickListener(v ,getLayoutPosition());
    }
    public  void setItemClickListener(ItemClickListener ic){
        this.itemClickListener = ic ;
    }
}
