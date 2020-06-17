package com.example.instagramapp;

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

import com.example.instagramapp.fragments.AccountFragment;
import com.example.instagramapp.fragments.FavoriteFragment;
import com.example.instagramapp.fragments.HomeFragment;
import com.example.instagramapp.fragments.SearchFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements  NavigationView.OnNavigationItemSelectedListener {

DrawerLayout drawerLayout ;
    TextView tvInfo ;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // FirebaseAuth.getInstance().signOut();

        setContentView(R.layout.activity_main2);
        FloatingActionButton floatingActionButton =( FloatingActionButton) findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar snackbar =Snackbar.make(v ,"Write email",Snackbar.LENGTH_INDEFINITE);
                snackbar.setAction("Yes", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Snackbar.make(v,"Done",Snackbar.LENGTH_SHORT).show();
                    }
                });
                snackbar.show();;

            }
        });


        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitleMargin(0,0,250,0);
        setSupportActionBar(toolbar);

        tvInfo =(TextView) findViewById(R.id.tv_info);
        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView =(NavigationView) findViewById(R.id.drawer);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this
                , drawerLayout,toolbar,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();

        ManageButtonNav();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        String itemName = (String) menuItem.getTitle();
        tvInfo.setText(itemName);
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
}
