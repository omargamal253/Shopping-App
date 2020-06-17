package com.example.instagramapp.Adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.instagramapp.R;

interface ItemClickListener {
    void onItemClickListener(View v , int position);
}

public class MyHolder extends RecyclerView.ViewHolder implements  View.OnClickListener {
    ImageView mImageView;
    TextView mTitle , price_before , price_after  , discount;
    ItemClickListener itemClickListener;
    MyHolder(@NonNull View itemView) {
        super(itemView);
        this.mImageView = itemView.findViewById(R.id.imageIv);
        this.mTitle = itemView.findViewById(R.id.titleIv);
        this.discount = itemView.findViewById(R.id.discount);
        this.price_before = itemView.findViewById(R.id.PriceBefore);
        this.price_after= itemView.findViewById(R.id.PriceAfter);

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
