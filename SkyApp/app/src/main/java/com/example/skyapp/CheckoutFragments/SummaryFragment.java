package com.example.skyapp.CheckoutFragments;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.skyapp.Adapter.FireBase;
import com.example.skyapp.Adapter.RecyclerCardAdapter;
import com.example.skyapp.CardActivity;
import com.example.skyapp.MainActivity;
import com.example.skyapp.MyOrdersActivity;
import com.example.skyapp.OrderCheckout;
import com.example.skyapp.R;
import com.example.skyapp.model.Product;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;

public class SummaryFragment extends Fragment {
    RecyclerView mreRecyclerView;
    RecyclerCardAdapter myAdapter;
    ArrayList<Product> MainProducts = new ArrayList<>() ;
    ProgressDialog pd, pd2;
    View DialogView;
    Button PLACE_A_ORDER;
    ImageView OkProgress;
    FragmentActivity c;
    TextView subTotal,Shipping,Total;

    public SummaryFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        final View view = inflater.inflate(R.layout.fragment_summary, container, false);
         c =getActivity();

        mreRecyclerView =view.findViewById(R.id.SummaryRecyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(
                c ,LinearLayoutManager.VERTICAL,false
        );
        mreRecyclerView.setLayoutManager(layoutManager);
        mreRecyclerView.setItemAnimator(new DefaultItemAnimator());
        myAdapter = new RecyclerCardAdapter( c, MainProducts , 2);

  /*      for(int i =0 ;i<myAdapter.productQtu.size() ;i++){
            myAdapter.productQtu.set(i, CardActivity.myAdapter.productQtu.get(i));

        }
            myAdapter.productQtu=  CardActivity.myAdapter.productQtu ;
*/
        mreRecyclerView.setAdapter(myAdapter);


        getMyList(myAdapter);
        pd= new ProgressDialog(c);
        pd2 = new ProgressDialog(c);


        PLACE_A_ORDER=view.findViewById(R.id.PLACE_A_ORDER);
        PLACE_A_ORDER.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (OrderCheckout.mPager.getCurrentItem() == 2) {

                    CreateOrder(CardActivity.myAdapter.TotalPrice, CardActivity.myAdapter.products, CardActivity.myAdapter.productQtu);
                }
            }
        });


        subTotal = view.findViewById(R.id.subTotal);
        subTotal.setText( String.format("%,.2f",CardActivity.myAdapter.TotalPrice) + " EGP");

        Shipping=view.findViewById(R.id.Shipping);
        double total = CardActivity.myAdapter.products.size() * 30f;
        Shipping.setText( String.format("%,.2f",total) + " EGP");

        Total=view.findViewById(R.id.Total);
        double tTotal = CardActivity.myAdapter.TotalPrice+total;
        Total.setText( String.format("%,.2f",     tTotal) + " EGP");




        pd = new ProgressDialog(c);
        LayoutInflater layoutInflater = (LayoutInflater)c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
         DialogView  =layoutInflater.inflate(R.layout.progress_successful,null);

        Button OkProgress=DialogView.findViewById(R.id.OkProgress);
        OkProgress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(c , MainActivity.class   );
                startActivity(intent);
                c.finish();

            }
        });
        TextView goToOrders = DialogView.findViewById(R.id.goToOrders);
        goToOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                c.finish();

                Intent intent = new Intent(c , MyOrdersActivity.class   );
                startActivity(intent);
            }
        });



        Log.d("-------------------TAG------------- :", String.valueOf(OrderCheckout.mPager.getCurrentItem()));

        return view ;
    }

    private void getMyList( RecyclerCardAdapter Adapter) {

        final  RecyclerCardAdapter MyAdapt = Adapter;
        MainProducts.clear();


        Product product = new Product();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference().child("UserCards").child(user.getUid());


        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot data :  dataSnapshot.getChildren()){
                    Product product  = data.getValue(Product.class);
                    if (product!=null){
                        //products.add(product);
                        MyAdapt.addNewData(product);

                    }
                    //  Toast.makeText(getContext(), product.getImage_url() ,Toast.LENGTH_SHORT).show();

                }


            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
             //   Toast.makeText(c, "fail to load Card List",Toast.LENGTH_SHORT).show();

            }
        });

        MainProducts.clear();


    }




    public   void CreateOrder(final double Total_Price , final ArrayList<Product> products , final ArrayList<Integer> productsQty){



        pd2.show();
        pd2.setContentView(R.layout.progress_dialog);
        pd2.getWindow().setBackgroundDrawableResource(R.color.transparent);
        pd.setCancelable(false);


        LocalDateTime myDateObj = LocalDateTime.now();
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

        String OrderDate = myDateObj.format(myFormatObj);
        // System.out.println("After formatting: " + OrderDate);


        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        HashMap<String,Object> productMap = new HashMap<>();

        if(products.size()==productsQty.size()){

            for (int i=0 ; i< products.size();i++){

                productMap.put(products.get(i).getTitle(), productsQty.get(i));
            }
        }
        final HashMap<String,Object> map = new HashMap<>();

        map.put("userId",user.getUid());
        map.put("totalPrice",Total_Price);
        map.put("numOfItem",products.size());
        map.put("ProductsInfo",productMap);
        map.put("orderDate",OrderDate);


        map.put("firstName",AddressFragment.FirstName);
        map.put("lastName",AddressFragment.LastName);
        map.put("city",AddressFragment.City);
        map.put("street",AddressFragment.Street);
        map.put("buildingNum",AddressFragment.BuildingNum);
        map.put("mobile",AddressFragment.Mobile);
        map.put("paymentState", PaymentFragment.PaymentState);





        FirebaseDatabase.getInstance().getReference().child("Orders")
                .child(user.getUid()+String.valueOf((int)Total_Price)+String.valueOf(products.size()))
                .updateChildren(map).addOnCompleteListener(new OnCompleteListener<Void>() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){

                    for (int i = 0; i < CardActivity.myAdapter.products.size(); i++) {
                        FireBase.RemoveProductFromCard(CardActivity.myAdapter.products.get(i));
                    }

                    CardActivity.myAdapter.products.clear();
                    CardActivity.myAdapter.notifyDataSetChanged();
                    CardActivity.myAdapter.TotalPrice = 0;


                    FireBase.NumOf_Products_InCard = 0;
                    FireBase.GetNumOf_Products_InCard();

                    FirebaseDatabase.getInstance().getReference().child("MyOrders").child(user.getUid())
                            .child(user.getUid()+String.valueOf((int)Total_Price)+String.valueOf(productsQty.size()))
                            .updateChildren(map)     .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                pd2.dismiss();
                                pd.show();
                                pd.setContentView(DialogView);
                                pd.getWindow().setBackgroundDrawableResource(R.color.transparent);
                                pd.setCancelable(false);

                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(c, e.getMessage(), Toast.LENGTH_SHORT).show();
                            pd2.dismiss();
                        }
                    });

                    pd.dismiss();
              //    Toast.makeText(c, "The Order was made Successfully ", Toast.LENGTH_SHORT).show();
                    //finish();


                }else{
                   Toast.makeText(c ,"Check your network connection ", Toast.LENGTH_LONG).show();
                }
            }
        });

    }



}