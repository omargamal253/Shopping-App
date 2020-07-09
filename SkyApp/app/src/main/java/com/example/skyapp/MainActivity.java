package com.example.skyapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;

import android.app.ActivityOptions;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.util.Pair;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.agrawalsuneet.dotsloader.loaders.CircularDotsLoader;
import com.example.skyapp.Adapter.FireBase;
import com.example.skyapp.Adapter.Utils;
import com.example.skyapp.fragments.AccountFragment;
import com.example.skyapp.fragments.FavoriteFragment;
import com.example.skyapp.fragments.HomeFragment;
import com.example.skyapp.fragments.SearchFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent;
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEventListener;

public class MainActivity extends AppCompatActivity implements  NavigationView.OnNavigationItemSelectedListener {

DrawerLayout drawerLayout ;
   // TextView tvInfo ;
    Toolbar toolbar;
   public static TextView CardCount;

    TextView Username , Email;
  public static   FragmentTransaction fragmentTransaction ;

  public  static  BottomNavigationView bottomNavigationView;
    CircularDotsLoader circularDotsLoader;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
     //  FirebaseAuth.getInstance().signOut();


        //   Utils.getDatabase();
        ProgressDialog pd = new ProgressDialog(MainActivity.this);

        pd.show();
        pd.setContentView(R.layout.progress_dialog);
        pd.getWindow().setBackgroundDrawableResource(R.color.transparent);

        if(FireBase.isNetworkAvailable(MainActivity.this))
            pd.dismiss();



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
View view = navigationView.getHeaderView(0);

        SetDrawerHeader setDrawerHeader = new SetDrawerHeader(view);

/*
      Username = findViewById(R.id.user_username);
        Email = findViewById(R.id.user_email);

        Username.setText("CurrentUser.getUsername()");
        Email.setText("CurrentUser.getEmail()");

*/
        set_AdminBlock_Visible(false);
               FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

               if(user.getEmail()!=null) {
                   if (user.getEmail().equals("omar2018170252@cis.asu.edu.eg"))
                       set_AdminBlock_Visible(true);

               }
              // sendEmailVerification();


               circularDotsLoader = findViewById(R.id.CircularDotsLoader);

                        circularDotsLoader.startAnimation();

                                Handler handler = new Handler();
               handler.postDelayed(new Runnable() {
            @Override
           public void run() {

                                        circularDotsLoader.setVisibility(View.INVISIBLE);
                            }
       },2000);


        KeyboardVisibilityEvent.setEventListener(
                this,
                new KeyboardVisibilityEventListener() {
                    @Override
                    public void onVisibilityChanged(boolean isOpen) {
                        if(isOpen){
                            Log.d("KeyboardVisibilityEvent", "onVisibilityChanged: Keyboard is open");
                            bottomNavigationView.setVisibility(View.INVISIBLE);
                        }else{
                            Log.d("KeyboardVisibilityEvent", "onVisibilityChanged: Keyboard is closed");
                            bottomNavigationView.setVisibility(View.VISIBLE);
                        }
                    }
                });

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
        if(menuItem.getTitle().equals("About Us")){
                        Intent intent = new Intent(MainActivity.this, AboutUsActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    }



        if(menuItem.getTitle().equals("Add Product")){
            Intent intent = new Intent(MainActivity.this , AddItemActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
        if(menuItem.getTitle().equals("Users Orders")){
            Intent intent = new Intent(MainActivity.this , OrdersActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
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

         bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if(id == R.id.nav_home){
                    HomeFragment homeFragment = new HomeFragment();

                 //   homeFragment.SetBase_Activity((MainActivity.this).getParent());

                    fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.fragment_container, homeFragment);
                    fragmentTransaction.commit();


                }
                else if(id == R.id.nav_heart){
                    FavoriteFragment favoriteFragment = new FavoriteFragment();
                   // FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.fragment_container, favoriteFragment);
                    fragmentTransaction.commit();

                }
                else if(id == R.id.nav_profile){
                    AccountFragment profileFragment = new AccountFragment();
                  //  FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.fragment_container, profileFragment);
                    fragmentTransaction.commit();

                }
                else if(id == R.id.nav_search){
                    SearchFragment searchFragment = new SearchFragment();
                   // FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction = getSupportFragmentManager().beginTransaction();
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

    public void sendEmailVerification(){
               FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
              user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
          @Override
          public void onComplete(@NonNull Task<Void> task) {
                               if (task.isSuccessful()){
                                      Toast.makeText(MainActivity.this, "Check Your Email To Verify It " +
                                              "", Toast.LENGTH_SHORT).show();

                                           }

                                   }
       });


    }

    private void set_AdminBlock_Visible(boolean x)
   {
              NavigationView  navigationView = (NavigationView) findViewById(R.id.drawer);
               Menu nav_Menu = navigationView.getMenu();


               nav_Menu.findItem(R.id.Admin_Block).setVisible(x);


           }


}
