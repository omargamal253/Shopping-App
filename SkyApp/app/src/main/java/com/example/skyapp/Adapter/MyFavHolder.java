package com.example.skyapp.Adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.skyapp.R;



public class MyFavHolder extends RecyclerView.ViewHolder implements  View.OnClickListener {
    ImageView mImageView;
    TextView mTitle ,  price_after  ;
    boolean AddedToCard;

    ImageView AddToCardImg, RemoveFromFav;

    ItemClickListener itemClickListener;
    MyFavHolder(@NonNull View itemView) {
        super(itemView);
        this.mImageView = itemView.findViewById(R.id.imageIv3);
        this.mTitle = itemView.findViewById(R.id.titleIv3);
        this.price_after= itemView.findViewById(R.id.PriceAfter3);

        AddToCardImg =  itemView.findViewById(R.id.AddFromFavToCard);
        RemoveFromFav = itemView.findViewById(R.id.RemoveFromFav);

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
