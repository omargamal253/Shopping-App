package com.example.skyapp.CheckoutFragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.skyapp.OrderCheckout;
import com.example.skyapp.R;


public class PaymentFragment extends Fragment {

    RelativeLayout CreditRelative, CashRelative;
    public static String PaymentState ;
    EditText Voucher;
    Button ApplyVoucher;
    public PaymentFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_payment, container, false);
        final FragmentActivity c =getActivity();



        InputMethodManager inputMethodManager=(InputMethodManager) c.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(),0);


        CreditRelative=view.findViewById(R.id.CreditRelative);
        CreditRelative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             PaymentState ="Credit Or Debit Cards";
            }
        });

        CashRelative=view.findViewById(R.id.CashRelative);
        CashRelative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (OrderCheckout.mPager.getCurrentItem() == 1) {

                    PaymentState = "Cash On Delivery (COD)";

                    //OrderCheckout.mPager.setPage(2,false);
                    OrderCheckout.mPager.setCurrentItem(2);
                    Voucher.setEnabled(false);

                    Voucher.setFocusable(false);
                    Voucher.setCursorVisible(false);
                    Voucher.setVisibility(View.INVISIBLE);

                }
            }
        });


        Voucher =view.findViewById(R.id.VoucherText);
        Voucher.setEnabled(true);
        ApplyVoucher=view.findViewById(R.id.ApplyVoucher);

        ApplyVoucher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Voucher.getText().toString().equals("admin") || Voucher.getText().toString().equals("omar gamal")){

                    PaymentState ="Voucher Code";
                 //   OrderCheckout.mPager.setPage(2,true);
                    OrderCheckout.mPager.setCurrentItem(2);
                    Voucher.setEnabled(false);
                    Voucher.setFocusable(false);
                    Voucher.setCursorVisible(false);
                    Voucher.setVisibility(View.INVISIBLE);
                }else Toast.makeText(c,"Voucher not found ",Toast.LENGTH_SHORT);
            }
        });



        Log.d("-------------------TAG------------- :", String.valueOf(OrderCheckout.mPager.getCurrentItem()));

        return view;
    }
}