package com.example.skyapp.CheckoutFragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.skyapp.CardActivity;
import com.example.skyapp.OrderCheckout;
import com.example.skyapp.R;

public class AddressFragment extends Fragment {

  Button PROCEED_TO_PAYMENT;
  static public EditText FirstNameET, LastNameET, CityET, StreetET, BuildingNumEt, MobileET;
  public  static  String FirstName,LastName,City, Street,BuildingNum, Mobile;
  TextView subTotal,Shipping,Total;

    public AddressFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
      /*
      // to send data to fragments
        Bundle bundle = new Bundle();
        //bundle.putSerializable("ObjectName" , Object);
        Fragment fragment = new Fragment();
        fragment.setArguments(bundle);

        */
    // To receive data
   //   getArguments().getSerializable("ObjectName");

        final View view = inflater.inflate(R.layout.fragment_address, container, false);
        final FragmentActivity c =getActivity();
        FirstNameET = view.findViewById(R.id.FirstName);
        LastNameET =view.findViewById(R.id.LastName);
        CityET = view.findViewById(R.id.city);
        StreetET =view.findViewById(R.id.street);
        BuildingNumEt =view.findViewById(R.id.building);
        MobileET =view.findViewById(R.id.Phone);

        PROCEED_TO_PAYMENT = view.findViewById(R.id.PROCEED_TO_PAYMENT);

        PROCEED_TO_PAYMENT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (TextUtils.isEmpty(FirstNameET.getText())){
                    Toast.makeText(c,"First Name Empty", Toast.LENGTH_SHORT).show();
                }
                else if (TextUtils.isEmpty(LastNameET.getText())){
                    Toast.makeText(c,"Last Name Empty", Toast.LENGTH_SHORT).show();
                }
                else if (TextUtils.isEmpty(CityET.getText())){
                    Toast.makeText(c,"City Empty", Toast.LENGTH_SHORT).show();
                }
                else if (TextUtils.isEmpty(StreetET.getText())){
                    Toast.makeText(c,"Street Empty", Toast.LENGTH_SHORT).show();
                }
                else if (TextUtils.isEmpty(BuildingNumEt.getText())){
                    Toast.makeText(c,"Building Number Empty", Toast.LENGTH_SHORT).show();
                }
                else if (TextUtils.isEmpty(MobileET.getText())   ){
                    Toast.makeText(c,"Mobile Empty", Toast.LENGTH_SHORT).show();
                }
                else if ( MobileET.getText().length() <= 9){
                    Toast.makeText(c,"Mobile phone not correct", Toast.LENGTH_SHORT).show();
                }

                else{

                    if(OrderCheckout.mPager.getCurrentItem()==0) {


                        FirstName = FirstNameET.getText().toString();
                        LastName = LastNameET.getText().toString();
                        City = CityET.getText().toString();
                        Street = StreetET.getText().toString();
                        BuildingNum = BuildingNumEt.getText().toString();
                        Mobile = MobileET.getText().toString();


                        FirstNameET.setEnabled(false);
                        LastNameET.setEnabled(false);
                        CityET.setEnabled(false);
                        StreetET.setEnabled(false);
                        BuildingNumEt.setEnabled(false);
                        MobileET.setEnabled(false);


                        // OrderCheckout.mPager.setPage(1,true);
                        OrderCheckout.mPager.setCurrentItem(1);
                    }
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


        Log.d("-------------------TAG------------- :", String.valueOf(OrderCheckout.mPager.getCurrentItem()));

        return view;
    }
}