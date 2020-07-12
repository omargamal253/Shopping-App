package com.example.skyapp.CheckoutFragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.skyapp.R;

public class AddressFragment extends Fragment {


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


        return inflater.inflate(R.layout.fragment_address, container, false);
    }
}