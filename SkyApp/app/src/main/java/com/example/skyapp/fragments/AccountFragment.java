package com.example.skyapp.fragments;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.skyapp.Adapter.FireBase;
import com.example.skyapp.AddItemActivity;
import com.example.skyapp.MainActivity;
import com.example.skyapp.R;
import com.example.skyapp.StartActivity;
import com.example.skyapp.model.Product;
import com.example.skyapp.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class AccountFragment extends Fragment {

     FragmentActivity c;
    CircleImageView userImage;
    TextView Username  , Email, Email2 , Phone;
    HashMap<String,Object> map;
    User CurrentUser ;
    ImageView SelectIcon;
    RelativeLayout LogOut, WishList;
    Uri imageUri ;
    String imageName , DownloadUrl ;
    private static final int IMAGE_REQUEST =  2 ;

    public AccountFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_account, container, false);
        c =getActivity();

        userImage = view.findViewById(R.id.user_profile_photo);
        Username = view.findViewById(R.id.user_username);
        Email = view.findViewById(R.id.user_email);
        Email2 = view.findViewById(R.id.UserEmail);
        Phone = view.findViewById(R.id.UserPhone);
        SelectIcon = view.findViewById(R.id.SelectPhotoIcon);
        LogOut = view.findViewById(R.id.logRelative);
        WishList = view.findViewById(R.id.Wish_Relative);


        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference().child("Users").child(user.getUid());



        CurrentUser = new User();

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {



                map =(HashMap <String,Object>) dataSnapshot.getValue();
                CurrentUser  = dataSnapshot.getValue(User.class);
                Email.setText(CurrentUser.getUser_email());
                Email2.setText(CurrentUser.getUser_email());
                Username.setText(CurrentUser.getUser_username());
                Phone.setText(CurrentUser.getUser_phone());



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

      userImage.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              SelectPhoto();
          }
      });
        SelectIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectPhoto();
            }
        });
        LogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(c , StartActivity.class);
                startActivity(intent);
                c.finish();
            }
        });
        WishList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FavoriteFragment favoriteFragment = new FavoriteFragment();
                 MainActivity.fragmentTransaction = c.getSupportFragmentManager().beginTransaction();
                MainActivity.fragmentTransaction.replace(R.id.fragment_container, favoriteFragment);
                MainActivity.fragmentTransaction.commit();

             //   BottomNavigationView bottomNavigationView = view.findViewById(R.id.bottom_navigation);
                MainActivity.bottomNavigationView.setSelectedItemId(R.id.nav_heart);

            }
        });



        return view;
    }

    public void SelectPhoto() {

        OpedImage();
        //UploadData();
    }
    public void OpedImage(){
        Intent intent = new Intent();
        intent.setType("image/");
        intent.setAction((Intent.ACTION_GET_CONTENT));
        startActivityForResult(intent, IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == IMAGE_REQUEST && resultCode == RESULT_OK){
            imageUri = data.getData();
          //  userImage.setImageURI(imageUri);
              UploadData();

        }
    }

    public String getFileExtension(Uri uri){
        ContentResolver contentResolver =  getActivity().getContentResolver();


                MimeTypeMap minMimeTypeMap = MimeTypeMap.getSingleton();
        return minMimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));

    }


    private void UploadData() {
       /* final ProgressDialog pd = new ProgressDialog(c);
        pd.setMessage("Uploading");

        pd.show();*/
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if(imageUri!= null){
            imageName = System.currentTimeMillis()+"."+getFileExtension(imageUri);
            final StorageReference fileRef = FirebaseStorage.getInstance().getReference().child("uploads").child(
                    user.getUid());

            fileRef.putFile(imageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                    fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            DownloadUrl = uri.toString() ;
                           // Log.d("DownLoadUi ", DownloadUrl);

                            // Picasso.get().load(DownloadUrl).into(imageAdded);
                            LoadProfilePhoto();

                          //  pd.dismiss();


                        }
                    });
                }
            });
        }

    }


    public void LoadProfilePhoto(){

     //   product.setImage_url(DownloadUrl);

      //  FireBase.AddProductTo_FireBase(product, Category);
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        HashMap<String , Object> map = new HashMap<>();
        map.put("user_email", CurrentUser.getUser_email());
        map.put("user_username" , CurrentUser.getUser_username());
        map.put("user_phone" , CurrentUser.getUser_phone());
        map.put("user_id" , user.getUid());
        map.put("user_url" , DownloadUrl);
         DatabaseReference mRootRef;
        mRootRef = FirebaseDatabase.getInstance().getReference();

        mRootRef.child("Users").child(user.getUid()).updateChildren(map).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(c, "Profile Photo Updated Successfully",Toast.LENGTH_SHORT).show();
                    userImage.setImageURI(imageUri);

                }else{
                    Toast.makeText(c, "Failed To Updated Profile Photo ",Toast.LENGTH_SHORT).show();

                }

            }
        });

     /*   DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Users").child(user.getUid()).updateChildren(map);

        ref.addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(c, "Profile Photo Updated Successfully",Toast.LENGTH_SHORT).show();
                    userImage.setImageURI(imageUri);

                }else{
                    Toast.makeText(c, "Failed To Updated Profile Photo ",Toast.LENGTH_SHORT).show();

                }
            }
        });*/


    }




}
