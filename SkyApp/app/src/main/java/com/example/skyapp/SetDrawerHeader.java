package com.example.skyapp;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.skyapp.model.Product;
import com.example.skyapp.model.User;
import com.example.skyapp.model.User;
import com.example.skyapp.model.User;
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

import de.hdodenhof.circleimageview.CircleImageView;

public class SetDrawerHeader {

    CircleImageView userImage;
    TextView Username , Email;

    View view ;
    HashMap <String,Object> map;
    User CurrentUser ;
    ArrayList<User> users = new ArrayList<>();
    public SetDrawerHeader(final View view) {
      /*  LayoutInflater inflater =(LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        view =inflater.inflate(R.layout.drawer_header,null);
*/
        userImage = view.findViewById(R.id.user_profile_image);
        Username = view.findViewById(R.id.user_username);
        Email = view.findViewById(R.id.user_email);

         final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference().child("Users").child(user.getUid());



  CurrentUser = new User();
        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {



                map =(HashMap <String,Object>) dataSnapshot.getValue();
                CurrentUser  = dataSnapshot.getValue(User.class);
               Email.setText(CurrentUser.getUser_email());
               Username.setText(CurrentUser.getUser_username());

 try {
            Picasso.get().load(CurrentUser.getUser_url()).into(userImage);
        }catch (Exception e){

        }



            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
            //   Toast.makeText(view.getContext() , "fail to load list",Toast.LENGTH_SHORT).show();

            }
        });
       // Username.setText(String.valueOf(users.size()));


        //Username.setText((String) map.get("username"));
      //  Email.setText((String) map.get("email"));
   // Username.setText(CurrentUser.getUser_username());

       // if(map!=null&&map.containsKey("user_email")) Email.setText((String)map.get("user_email"));

    //Email.setText(CurrentUser.getUser_email());
     //   Log.e("Users",map.toString());;



       /* try {
            Picasso.get().load(user.getPhotoUrl()).into(userImage);
        }catch (Exception e){

        }*/


    }
}
