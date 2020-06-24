package com.example.skyapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.skyapp.Adapter.FireBase;
import com.example.skyapp.Adapter.Utils;
import com.example.skyapp.fragments.AccountFragment;
import com.example.skyapp.fragments.FavoriteFragment;
import com.example.skyapp.fragments.HomeFragment;
import com.example.skyapp.fragments.SearchFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements  NavigationView.OnNavigationItemSelectedListener {

DrawerLayout drawerLayout ;
   // TextView tvInfo ;
    Toolbar toolbar;
   public static TextView CardCount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // FirebaseAuth.getInstance().signOut();


           Utils.getDatabase();

        setContentView(R.layout.activity_main2);


        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

       // tvInfo =(TextView) findViewById(R.id.tv_info);
        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView =(NavigationView) findViewById(R.id.drawer);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this
                , drawerLayout,toolbar,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();

        ManageButtonNav();
        FireBase fireBase= new FireBase();

        CardCount = findViewById(R.id.Card_Count);

        FireBase.GetNumOf_Products_InCard();
/*
       if(FireBase.NumOf_Products_InCard>0)
        {
            CardCount.setVisibility(View.VISIBLE);
            CardCount.setText(String.valueOf(FireBase.NumOf_Products_InCard));
        }
        else CardCount.setVisibility(View.INVISIBLE);
       // CardCount.setText(String.valueOf(FireBase.NumOf_Products_InCard));*/

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        String itemName = (String) menuItem.getTitle();
       // tvInfo.setText(itemName);


        if(menuItem.getTitle().equals("Deals")){
            Intent intent = new Intent(MainActivity.this , DealsActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
        if(menuItem.getTitle().equals("Supermarket")){
            Intent intent = new Intent(MainActivity.this , SuperMarketActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
        if(menuItem.getTitle().equals("Laptop & Tablets")){
            Intent intent = new Intent(MainActivity.this , LaptopActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
        if(menuItem.getTitle().equals("Mobiles")){
            Intent intent = new Intent(MainActivity.this , MobilesActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
        if(menuItem.getTitle().equals("Electronics")){
            Intent intent = new Intent(MainActivity.this , ElectronicsActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
        if(menuItem.getTitle().equals("Fashion")){
            Intent intent = new Intent(MainActivity.this , FashionActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
        if(menuItem.getTitle().equals("Card")){
            Intent intent = new Intent(MainActivity.this, CardActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }

        if(menuItem.getTitle().equals("My Wishlist")){
            FavoriteFragment favoriteFragment = new FavoriteFragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, favoriteFragment);
            fragmentTransaction.commit();

            BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
            bottomNavigationView.setSelectedItemId(R.id.nav_heart);

        }



        if(menuItem.getTitle().equals("Add Product")){
            Intent intent = new Intent(MainActivity.this , AddItemActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
        CloseDrawer();

        return true;
    }

    private void CloseDrawer() {
        drawerLayout.closeDrawer(GravityCompat.START);
    }
    private void OpenDrawer(){
        drawerLayout.openDrawer(GravityCompat.START);

    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            CloseDrawer();
        }
        super.onBackPressed();
    }

    public void ManageButtonNav(){

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if(id == R.id.nav_home){
                    HomeFragment homeFragment = new HomeFragment();
                    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.fragment_container, homeFragment);
                    fragmentTransaction.commit();

                }
                else if(id == R.id.nav_heart){
                    FavoriteFragment favoriteFragment = new FavoriteFragment();
                    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.fragment_container, favoriteFragment);
                    fragmentTransaction.commit();

                }
                else if(id == R.id.nav_profile){
                    AccountFragment profileFragment = new AccountFragment();
                    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.fragment_container, profileFragment);
                    fragmentTransaction.commit();

                }
                else if(id == R.id.nav_search){
                    SearchFragment searchFragment = new SearchFragment();
                    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.fragment_container, searchFragment);
                    fragmentTransaction.commit();

                }
                return true ;
            }
        });
        // so default fragment is home
        bottomNavigationView.setSelectedItemId(R.id.nav_home);
    }

    public void MoveToCardActivity(View view) {
        Intent intent = new Intent(this, CardActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);

    }
}
