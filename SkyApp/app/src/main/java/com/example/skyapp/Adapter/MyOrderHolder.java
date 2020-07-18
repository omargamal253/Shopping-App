package com.example.skyapp.Adapter;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.skyapp.R;

import org.w3c.dom.Text;

public class MyOrderHolder  extends RecyclerView.ViewHolder implements  View.OnClickListener   {


    TextView Number_Of_Item;
    TextView products_Title_Qty ;
    TextView  User_Description;
    TextView totalPrice;
    TextView OrderDate;
    Button AcceptOrderBtn;

    ItemClickListener itemClickListener;
    ImageView deleteMyOrder;


    public MyOrderHolder(@NonNull View itemView) {
        super(itemView);

        this.Number_Of_Item = itemView.findViewById(R.id.numOfItems);
        this.products_Title_Qty = itemView.findViewById(R.id.products_Title_Qty);
        this.User_Description = itemView.findViewById(R.id.User_Info);
        this.totalPrice= itemView.findViewById(R.id.TotalPrice);
        this.AcceptOrderBtn = itemView.findViewById(R.id.AcceptOrder);
        this.OrderDate = itemView.findViewById(R.id.OrderDate);
        this.deleteMyOrder=itemView.findViewById(R.id.deleteMyOrder);

        itemView.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
//        this.itemClickListener.onItemClickListener(v ,getLayoutPosition());

    }

    public  void setItemClickListener(ItemClickListener ic){
        this.itemClickListener = ic ;
    }

}
