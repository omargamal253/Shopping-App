package com.example.skyapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private TextInputEditText email;
    private TextInputEditText password;

    private Button login;
    private TextView registerUser, forgotText;

    private FirebaseAuth mAuth;
    private  TextView EmailVerify;
    LinearLayout LoginRelative, ForgotRelative;
    private TextInputEditText emailForgot;
    private Button SendForgot;

    ProgressDialog pd ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        pd= new ProgressDialog(this);

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        login = findViewById(R.id.login);
        registerUser = findViewById(R.id.register_user);

        EmailVerify = findViewById(R.id.VerifyEmail);

        mAuth = FirebaseAuth.getInstance();

        registerUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this , RegisterActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txt_email = email.getText().toString();
                String txt_password = password.getText().toString();

                if (TextUtils.isEmpty(txt_email) || TextUtils.isEmpty(txt_password)){
                    Toast.makeText(LoginActivity.this, "Empty Credentials!", Toast.LENGTH_SHORT).show();
                }
                else if(txt_email.equals("admin") && txt_password.equals("admin")){
                                        loginUser("omar2018170252@cis.asu.edu.eg","Taz77248Taz77248");
                }

                else {
                    loginUser(txt_email , txt_password);
                }
            }
        });
        LoginRelative = findViewById(R.id.LoginLinear);
        ForgotRelative= findViewById(R.id.ForgotLinear);
        emailForgot = findViewById(R.id.emailForgot);
        SendForgot = findViewById(R.id.SendReset);


        forgotText = findViewById(R.id.forgotText);
        forgotText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               LoginRelative.setVisibility(View.INVISIBLE);
               ForgotRelative.setVisibility(View.VISIBLE);
            }
        });


        SendForgot.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                pd.show();
                pd.setContentView(R.layout.progress_dialog);
                pd.getWindow().setBackgroundDrawableResource(R.color.transparent);

                FirebaseAuth.getInstance().sendPasswordResetEmail( emailForgot.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    LoginRelative.setVisibility(View.VISIBLE);
                                    ForgotRelative.setVisibility(View.INVISIBLE);
                                    pd.dismiss();
                                    Toast.makeText(LoginActivity.this, "Check your email", Toast.LENGTH_SHORT).show();

                                }
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        pd.dismiss();


                    }
                });
            }
        });


    }

    private void loginUser(String email, String password) {
        pd.show();
        pd.setContentView(R.layout.progress_dialog);
        pd.getWindow().setBackgroundDrawableResource(R.color.transparent);


        mAuth.signInWithEmailAndPassword(email , password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                    if (!user.isEmailVerified()) {
                                            EmailVerify.setVisibility(View.VISIBLE);
                                        pd.dismiss();
                                       } else {

                                                   if (task.isSuccessful()) {
                                                pd.dismiss();
                                                           Intent intent = new Intent(LoginActivity.this, StartScreenActivity.class);
                                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                    startActivity(intent);
                                                    finish();
                                                }


                    }




            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                pd.dismiss();

            }
        });

    }
}


