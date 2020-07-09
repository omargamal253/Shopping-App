package com.example.skyapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;

import android.content.Intent;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

public class StartActivity extends AppCompatActivity {


    private ImageView iconImage;
    private LinearLayout linearLayout;
    private Button register;
    private Button login;
CallbackManager callbackManager;
FirebaseAuth firebaseAuth;
AccessTokenTracker accessTokenTracker;
LoginButton loginButton;
    ProgressDialog pd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        iconImage = findViewById(R.id.icon_image);
        linearLayout = findViewById(R.id.linear_layout);
        register = findViewById(R.id.register);
        login = findViewById(R.id.login);

pd = new ProgressDialog(this);
  loginButton = findViewById(R.id.login_button);
  loginButton.setPermissions("email", "public_profile");
  firebaseAuth = FirebaseAuth.getInstance();
  FacebookSdk.sdkInitialize(getApplicationContext());
  callbackManager = CallbackManager.Factory.create();
loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
    @Override
    public void onSuccess(LoginResult loginResult) {
        handleFacebookToken(loginResult.getAccessToken());
    }

    @Override
    public void onCancel() {
        Toast.makeText(StartActivity.this, "Canceled", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onError(FacebookException error) {
        Toast.makeText(StartActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();

    }
});

accessTokenTracker = new AccessTokenTracker() {
    @Override
    protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
        if(currentAccessToken==null){
            firebaseAuth.signOut();
        }
    }
};


        linearLayout.animate().alpha(0f).setDuration(10);
        TranslateAnimation animation = new TranslateAnimation(0 , 0 , 0 , -1500);
        animation.setDuration(1000);
        animation.setFillAfter(false);
        animation.setAnimationListener(new MyAnimationListener());

        iconImage.setAnimation(animation);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               startActivity(new Intent(StartActivity.this , RegisterActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP));
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               startActivity(new Intent(StartActivity.this , LoginActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP));
            }
        });




    }

    private void handleFacebookToken(AccessToken accessToken) {
        AuthCredential credential = FacebookAuthProvider.getCredential(accessToken.getToken());
        pd.show();
        pd.setContentView(R.layout.progress_dialog);
        pd.getWindow().setBackgroundDrawableResource(R.color.transparent);

        firebaseAuth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    FirebaseUser user = firebaseAuth.getCurrentUser();

                    UpdateUi(user);
                }else{
                    Toast.makeText(StartActivity.this, "Failed to sign in to Facebook  ", Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                }
            }
        });
    }

    private void UpdateUi(FirebaseUser user) {
      final  DatabaseReference mRootRef;
        mRootRef = FirebaseDatabase.getInstance().getReference();

        String username, phone, email,  password ,link;

      if(user!=null) {

          if(user.getPhotoUrl()!=null){
              link =user.getPhotoUrl().toString();
          }
           else link = "https://firebasestorage.googleapis.com/v0/b/skyapp-f0431.appspot.com/o/uploads%2Fprofil%20icon%20urliconicon?alt=media&token=4e16afc8-7c83-45e9-92a6-6685ff03b71b";

         username = user.getDisplayName();
         if(user.getPhoneNumber()!=null) phone =user.getPhoneNumber();
         else phone="--";

          if(user.getEmail()!=null) email =user.getEmail();
          else  email ="Facebook Account";
          password= user.getUid();

          HashMap<String, Object> map = new HashMap<>();
          map.put("user_email", email);
          map.put("user_username", username);
          map.put("user_phone", phone);
          map.put("user_id", user.getUid());
          map.put("user_url", link);


          mRootRef.child("Users").child(user.getUid()).updateChildren(map).addOnCompleteListener(new OnCompleteListener<Void>() {
              @Override
              public void onComplete(@NonNull Task<Void> task) {
                  if (task.isSuccessful()) {
                      pd.dismiss();
                    Intent intent = new Intent(StartActivity.this, StartScreenActivity.class);
                     // intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                     startActivity(intent);
                    //  finish();
                  }else {
                      Toast.makeText(StartActivity.this, "Failed to add Facebook user to firebase ", Toast.LENGTH_SHORT).show();
                      pd.dismiss();

                  }
              }
          });
          map.clear();

      }else {

          Toast.makeText(StartActivity.this, "null user", Toast.LENGTH_SHORT).show();

      }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    private class MyAnimationListener implements Animation.AnimationListener {

        @Override
        public void onAnimationStart(Animation animation) {

        }

        @Override
        public void onAnimationEnd(Animation animation) {

            iconImage.clearAnimation();
            iconImage.setVisibility(View.INVISIBLE);
            linearLayout.animate().alpha(1f).setDuration(1000);

        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    }

   @Override
    protected void onStart() {
        super.onStart();
       // if user already login
        if (FirebaseAuth.getInstance().getCurrentUser() != null){
            startActivity(new Intent(StartActivity.this , StartScreenActivity.class));
            finish();
        }
    }
}
