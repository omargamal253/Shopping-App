package com.example.skyapp.Adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.skyapp.CardActivity;
import com.example.skyapp.R;
import com.example.skyapp.model.Order;
import com.example.skyapp.model.Product;
import com.example.skyapp.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
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
import java.util.HashMap;

public class RecyclerOrderAdapter extends RecyclerView.Adapter<MyOrderHolder> {

    Context c ;
    ArrayList<Order> orders = new ArrayList<>();
ArrayList<HashMap> ProductsInfo = new ArrayList<>();

int layout ;
    public RecyclerOrderAdapter(Context c, int layout) {

        this.c = c;
        this.layout = layout;
    }


    public void addNewData(Order order , HashMap hashMap){
        boolean add = true;
        for (int i=0 ;i<orders.size();i++)
        {
            if(order.getTotalPrice()== orders.get(i).getTotalPrice() && order.getNumOfItem()== orders.get(i).getNumOfItem())
            {   add=false;  break;  }
        }
        if(add == true)  {
        orders.add(order) ;
        ProductsInfo.add(hashMap);
        }

        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public MyOrderHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_item, null) ;

        if(layout==1){
            return new MyOrderHolder(view) ;
        }
        else if(layout==2){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_order_item, null) ;
            return new MyOrderHolder(view) ;

        }
        else return  null ;

    }


    @Override
    public void onBindViewHolder(@NonNull final MyOrderHolder holder, final int position) {


        holder.Number_Of_Item.setText(String.valueOf(orders.get(position).getNumOfItem()));
        holder.totalPrice.setText(String.format("%,.2f", orders.get(position).getTotalPrice()) + " EGP");
        holder.OrderDate.setText(orders.get(position).getOrderDate());

        String productInfo = "";
        // HashMap H = (HashMap) data.child("ProductsInfo").getValue();

        int count = 0;
        if (ProductsInfo.get(position) != null) {
            for (Object name : ProductsInfo.get(position).keySet()) {
                String key = name.toString();
                String value = (String) ProductsInfo.get(position).get(name).toString();
                productInfo += key + "  QTY: " + value;

                productInfo += "\n";

                if (count != ProductsInfo.size()) {
                    count++;

                }

            }
        }

        holder.products_Title_Qty.setText(productInfo);


        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference().child("Users").child(orders.get(position).getUserId());


        //    CurrentUser = new User();
        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                User CurrentUser = dataSnapshot.getValue(User.class);

                String UserInfo = "";

                UserInfo += "UserName : " + CurrentUser.getUser_username() + "\n";
                UserInfo += "Email : " + CurrentUser.getUser_email() + "\n";

                UserInfo+= "Address : "+ orders.get(position).getCity() +" "+ orders.get(position).getStreet()+" "+orders.get(position).getBuildingNum()+"\n";

                UserInfo += "Phone : " + CurrentUser.getUser_phone();


                if(UserInfo!=null&& holder.User_Description!=null) holder.User_Description.setText(UserInfo);


            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                //   Toast.makeText(view.getContext() , "fail to load list",Toast.LENGTH_SHORT).show();

            }
        });


        final ProgressDialog pd = new ProgressDialog(c);



        if (holder.AcceptOrderBtn != null){
            holder.AcceptOrderBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {


                    DialogInterface.OnClickListener diOnClickListener = new DialogInterface.OnClickListener() {
                        @SuppressLint("ResourceAsColor")
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which) {
                                case DialogInterface.BUTTON_POSITIVE:
                                    pd.show();
                                    pd.setContentView(R.layout.progress_dialog);
                                    pd.getWindow().setBackgroundDrawableResource(R.color.transparent);
                                    pd.setCancelable(false);

                                    final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                                    FirebaseDatabase.getInstance().getReference().child("Orders")
                                            .child(user.getUid() + (int) orders.get(position).getTotalPrice() + orders.get(position).getNumOfItem())
                                            .removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                orders.remove(position);
                                                ProductsInfo.remove(position);
                                                notifyDataSetChanged();
                                                pd.dismiss();
                                                Snackbar snackbar = Snackbar.make(v, "Order Successful Accepted", Snackbar.LENGTH_SHORT);
                                                snackbar.setBackgroundTint(R.color.colorPrimaryDark);
                                                snackbar.show();


                                            } else {
                                                Toast.makeText(c, "Check your network connection ", Toast.LENGTH_LONG).show();
                                            }
                                        }

                                    });


                                    break;

                                case DialogInterface.BUTTON_NEGATIVE:
                                    break;

                            }
                        }
                    };

                    AlertDialog.Builder builder = new AlertDialog.Builder(c);
                    builder.setMessage(" Are you Sure accepted Order ").setPositiveButton("Yes", diOnClickListener)
                            .setNegativeButton("No", diOnClickListener);

                    builder.show();


                }
            });

    }


        holder.deleteMyOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {


                if(layout==1){
                    pd.show();
                    pd.setContentView(R.layout.progress_dialog);
                    pd.getWindow().setBackgroundDrawableResource(R.color.transparent);
                    pd.setCancelable(false);

                    final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                    FirebaseDatabase.getInstance().getReference().child("Orders")
                            .child(user.getUid() + (int) orders.get(position).getTotalPrice() + orders.get(position).getNumOfItem())
                            .removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @SuppressLint("ResourceAsColor")
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                orders.remove(position);
                                ProductsInfo.remove(position);
                                notifyDataSetChanged();
                                pd.dismiss();
                                Snackbar snackbar = Snackbar.make(v, "Order Successful Accepted", Snackbar.LENGTH_SHORT);
                                snackbar.setBackgroundTint(R.color.colorPrimaryDark);
                                snackbar.show();


                            } else {
                                Toast.makeText(c, "Check your network connection ", Toast.LENGTH_LONG).show();
                            }
                        }

                    });



                }

                if(layout==2){
                    pd.show();
                    pd.setContentView(R.layout.progress_dialog);
                    pd.getWindow().setBackgroundDrawableResource(R.color.transparent);
                    pd.setCancelable(false);

                    final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

      /*              FirebaseDatabase.getInstance().getReference().child("MyOrders")
                            .child(user.getUid() ).child(user.getUid() + (int) orders.get(position).getTotalPrice() + orders.get(position).getNumOfItem())

                            .removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @SuppressLint("ResourceAsColor")
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                orders.remove(position);
                                ProductsInfo.remove(position);
                                notifyDataSetChanged();
                                pd.dismiss();
                                Snackbar snackbar = Snackbar.make(v, "Order removed", Snackbar.LENGTH_SHORT);
                                snackbar.setBackgroundTint(R.color.colorPrimaryDark);
                                snackbar.show();


                            } else {
                                Toast.makeText(c, "Check your network connection ", Toast.LENGTH_LONG).show();
                            }
                        }

                    });
*/

                    FirebaseDatabase.getInstance().getReference().child("MyOrders")
                            .child(user.getUid() ).child(user.getUid() + (int) orders.get(position).getTotalPrice() + orders.get(position).getNumOfItem())

                            .removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @SuppressLint("ResourceAsColor")
                        @Override
                        public void onSuccess(Void aVoid) {
                            orders.remove(position);
                            ProductsInfo.remove(position);
                            notifyDataSetChanged();
                            pd.dismiss();
                            Snackbar snackbar = Snackbar.make(v, "Order removed", Snackbar.LENGTH_SHORT);
                            snackbar.setBackgroundTint(R.color.colorPrimaryDark);
                            snackbar.show();

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(c, e.getMessage(), Toast.LENGTH_LONG).show();

                        }
                    });


                }



            }
        });





    }

    @Override
    public int getItemCount() {
        return orders.size();
    }
}
