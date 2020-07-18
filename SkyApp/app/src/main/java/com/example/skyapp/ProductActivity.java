package com.example.skyapp;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import com.example.skyapp.Adapter.FireBase;
import com.example.skyapp.fragments.FavoriteFragment;
import com.example.skyapp.fragments.HomeFragment;
import com.example.skyapp.model.Product;
import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;

import java.io.Serializable;

public class ProductActivity extends AppCompatActivity implements Serializable {
    Toolbar toolbar;
    public static TextView CardCount_;


    TextView ProductTitle, ProductBrand, ProductColor, ProductPriceBefore, ProductPriceAfter, ProductDiscount, ProductDescription;
    boolean AddedToCard, AddedToFav;
    ImageView ProductImage, ProductFav;
    Button AddToCardBtn, CallBtn;
ProgressDialog pd ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);


        // ActionBar actionBar =  getSupportActionBar();
        toolbar = findViewById(R.id.ProductToolbar);
        // add this in this activity tag  android:parentActivityName=".MainActivity"
        setSupportActionBar(toolbar);


        final Product product = (Product) getIntent().getSerializableExtra("ProductObject");

        AddedToCard = getIntent().getBooleanExtra("AddedToCardOrNot", false);
        AddedToFav = getIntent().getBooleanExtra("AddedToFavOrNot", false);

        toolbar.setTitle(product.getTitle());
        toolbar.setTitleTextColor(Color.parseColor("#FFF8F3"));

        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        CardCount_ = findViewById(R.id.Count);
        // go to firebase class and and to this fun
        FireBase.GetNumOf_Products_InCard();

        ProductImage = findViewById(R.id.ProductImage);
        ProductTitle = findViewById(R.id.ProductTitle);
        ProductBrand = findViewById(R.id.ProductBrand);
        ProductColor = findViewById(R.id.ProductColor);
        ProductPriceBefore = findViewById(R.id.ProductPriceBefore);
        ProductPriceAfter = findViewById(R.id.ProductPriceAfter);
        ProductDiscount = findViewById(R.id.ProductPriceDiscount);
        ProductDescription = findViewById(R.id.ProductDescription);
        ProductFav = findViewById(R.id.AddProduct_toFav);
        AddToCardBtn = findViewById(R.id.AddProductToCard);
        CallBtn = findViewById(R.id.CallButton);

        LayoutInflater layoutInflater = (LayoutInflater)this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View DialogView  =layoutInflater.inflate(R.layout.progress_addcard,null);
        pd = new ProgressDialog(this);


        ProductTitle.setText(product.getTitle());
        ProductBrand.setText(product.getBrand());
        ProductColor.setText(product.getColor());

        double price_before = product.getPrice();
        double discount = product.getDiscount();
        double price_after = price_before - (price_before * (discount / 100));

        ProductPriceBefore.setText(String.format("%,.2f", price_before) + " EGP");
        ProductPriceBefore.setPaintFlags(ProductPriceBefore.getPaintFlags()| Paint.STRIKE_THRU_TEXT_FLAG);
        ProductPriceAfter.setText(String.format("%,.2f", price_after) + " EGP");
        ProductDiscount.setText("-" + String.valueOf(product.getDiscount()) + "%");
        ProductDescription.setText(product.getDescription());

        try {
            Picasso.get().load(product.getImage_url()).into(ProductImage);
        }catch (Exception e){

        }




        if (AddedToFav == true) {
            ProductFav.setImageResource(R.drawable.ic_fav);
        } else ProductFav.setImageResource(R.drawable.ic_fav_border);
        ProductFav.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {
                if (AddedToFav == false) {
                    FireBase.AddToUser_Fav(product);
                    ProductFav.setImageResource(R.drawable.ic_fav);
                    Snackbar snackbar = Snackbar.make(v, product.getTitle() + " Saved to your Favorites ", Snackbar.LENGTH_SHORT);
                    //    snackbar.setActionTextColor(R.color.MainColor);
                    snackbar.setBackgroundTint(R.color.colorPrimaryDark);
                    snackbar.show();
                    AddedToFav = true;

                }
            }
        });

        if (AddedToCard == true) {
            AddToCardBtn.setText("Added to your Card");
        }
        AddToCardBtn.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {
                if (AddedToCard == false) {

                    FireBase.AddToUser_Card(product);
                    AddToCardBtn.setText("Added to your Card");
                    AddedToCard = true;
                  /*  Snackbar snackbar = Snackbar.make(v, product.getTitle() + " Saved to your Card ", Snackbar.LENGTH_SHORT);
                    //    snackbar.setActionTextColor(R.color.MainColor);
                    snackbar.setBackgroundTint(R.color.colorPrimaryDark);
                    snackbar.show();*/

                    HomeFragment.DealsAdapter.notifyDataSetChanged();
                    HomeFragment.BestSealsAdapter.notifyDataSetChanged();
                    if(FavoriteFragment.FavAdapter!=null)
                    FavoriteFragment.FavAdapter.notifyDataSetChanged();
                    FireBase.GetNumOf_Products_InCard();

                    pd.show();
                    pd.setContentView(DialogView);
               //    pd.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
                    pd.getWindow(). setBackgroundDrawableResource(R.color.transparent);


                    pd.setCancelable(false);



                }
            }
        });

        CallBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:" + "01148694280"));
                if (ActivityCompat.checkSelfPermission(ProductActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    Toast.makeText(ProductActivity.this,"Check Call Permission",Toast.LENGTH_SHORT).show();
                    return;
                }
                startActivity(intent);
      }
  });





        Button AddToCard=DialogView.findViewById(R.id.GO_CART);
        AddToCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProductActivity.this , CardActivity.class );
                startActivity(intent);
                fileList();
            }
        });

        Button BackBtn=DialogView.findViewById(R.id.BackBtn);
        BackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pd.dismiss();
            }
        });



    }

    public void MoveToCardActivity(View view) {
        Intent intent = new Intent(this, CardActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);

    }
}
