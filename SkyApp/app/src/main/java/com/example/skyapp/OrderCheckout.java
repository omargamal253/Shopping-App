package com.example.skyapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Display;
import android.view.DragEvent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.skyapp.CheckoutFragments.AddressFragment;
import com.example.skyapp.CheckoutFragments.PaymentFragment;
import com.example.skyapp.CheckoutFragments.SummaryFragment;
import com.ydn.viewpagerwithicons.StateViewPager;

public class OrderCheckout extends AppCompatActivity {
    Toolbar toolbar;
    private static final int NUM_PAGES = 4;
    public static StateViewPager mPager;
    private PagerAdapter mPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_checkout);

        toolbar = findViewById(R.id.Checkout_Toolbar);
        setSupportActionBar(toolbar);

        toolbar.setTitle("Checkout");
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

        mPager = findViewById(R.id.pager);

        Display display = ((android.view.WindowManager) getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
       // mPager.getLayoutParams().height = (int) (display.getHeight() * 0.8);

        mPagerAdapter = new PagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);
        setConfiguration();




    }


    void setConfiguration() {
        mPager.setOrientation(LinearLayout.HORIZONTAL);
        mPager.setGravity(Gravity.TOP);
        setConfiguration4();

        mPager.setCurrentItem(0);
        mPager.setAdapter(mPagerAdapter);
        mPagerAdapter.notifyDataSetChanged();
        mPager.requestLayout();
    }


    void setConfiguration4() {


            mPager.setIntermediateIconSize(30, 4);

            mPager.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    return true;
                }
            });

//58B70A

        mPager.setNumberOfIcons(3)
                //.setOrientation(LinearLayout.HORIZONTAL)

                .setMargins(45, 25, 35, 55)
                //.setGravity(Gravity.TOP)

                .setIconSize(48, 48)
                .setSelectedIconSize(60, 60)

                .setOnIconClickListener(new StateViewPager.OnIconClickListener() {
                    @Override
                    public void onIconClick(int iconNum) {
                 //       mPager.setPage(iconNum, true);
                    }
                })
                .setMarginBetweenIcons(15)
                //.setIntermediateIconSize(30, 4)
                .setShowCheckmarks(true, true, false)
                .setCheckmarkColors(Color.WHITE, Color.WHITE, 0)
                .setShowNumbers(false, false, true)
                .setNumberColors(0, 0, Color.WHITE)
                //.setIconDrawables(getDrawable(R.mipmap.ic_launcher), null, null)
                .setTitles(new String[]{
                        "Address",
                        "Payment",
                        "Summary"})
                .setIconColors(Color.parseColor("#E600FF7F"), Color.parseColor("#E600FF7F"), Color.parseColor("#2DAEFF"))
                .setRectangularIcons(false, false, false)
                .setBorderSizes(0, 0, 0)
                .setTextColors(Color.GRAY, Color.GRAY, Color.GRAY)
                .setTextStyles(0, Typeface.BOLD, 0)
                .setTextGravities(Gravity.BOTTOM, Gravity.BOTTOM, Gravity.BOTTOM)
                .setTextSizes(12, 12, 12)
                .setTextMargins(10, 10, 10)
                .setIntermediateIconColors(Color.GRAY, Color.GRAY)
                .setIntermediateIconStyles("dotted", "dotted");
    }



    private class PagerAdapter extends FragmentStatePagerAdapter {
        public PagerAdapter(FragmentManager fm) {

            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new AddressFragment();
                case 1:
                    return new PaymentFragment();
                case 2:
                    return new SummaryFragment();

            }
            return new AddressFragment();
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }
}