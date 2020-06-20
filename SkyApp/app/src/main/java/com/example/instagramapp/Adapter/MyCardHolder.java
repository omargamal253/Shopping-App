package com.example.instagramapp.Adapter;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.instagramapp.R;
import com.example.instagramapp.model.Product;

interface ItemClickListener {
    void onItemClickListener(View v , int position);
}

public class MyCardHolder extends RecyclerView.ViewHolder implements  View.OnClickListener  {
    ImageView mImageView;
     TextView mTitle , price_before , price_after  , discount;
    TextView RemoveFromCard ;
    ImageView FavProduct , AddQty, SubtractQty;
    boolean AddedToFav;
    EditText ProductQty ;
    int UserQTY;
    ItemClickListener itemClickListener;
    public Product product;

    public MyCardHolder(@NonNull View itemView) {
        super(itemView);

        this.mImageView = itemView.findViewById(R.id.imageIv2);
        this.mTitle = itemView.findViewById(R.id.titleIv2);
        this.discount = itemView.findViewById(R.id.discount2);
        this.price_before = itemView.findViewById(R.id.PriceBefore2);
        this.price_after= itemView.findViewById(R.id.PriceAfter2);

       RemoveFromCard = itemView.findViewById(R.id.remove_card_btn);
        FavProduct = itemView.findViewById(R.id.Add_toFav);
        AddQty = itemView.findViewById(R.id.add_Qty);
        SubtractQty = itemView.findViewById(R.id.subtract_Qty);

        ProductQty = itemView.findViewById(R.id.EditText_Qty);
         UserQTY=1;
        AddedToFav =false;


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
