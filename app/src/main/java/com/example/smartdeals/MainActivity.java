package com.example.smartdeals;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    EditText inLpass,inLemail;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();



        inLemail = (EditText)findViewById(R.id.inLEmail);
        inLpass = (EditText)findViewById(R.id.inLpass);
        Button loginButton = (Button)findViewById(R.id.buttonLogin);
        Button registerButton = (Button)findViewById(R.id.buttonLRegister) ;
        Button asAdmin  = (Button)findViewById(R.id.buttonAdminLogin);

        asAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSellerProfile();

            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openRegisterActivity();
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userLogin();
            }
        });


    }
    

    public void openActivityHomePage(){
        Intent intent = new Intent(this,homePage.class);
        startActivity(intent);
    }
    public void openRegisterActivity(){
        Intent intent =  new Intent(this, register.class) ;
        startActivity(intent);

    }
    public void openSellerProfile(){
        Intent intent = new Intent(this, sellerProfile.class);
        startActivity(intent);
    }
    public void userLogin()
    {

        String email,password;

        email = inLemail.getText().toString();
        password = inLpass.getText().toString();

        if (TextUtils.isEmpty(email)||TextUtils.isEmpty(password)) {
            Toast.makeText(MainActivity.this, "Empty fields are there", Toast.LENGTH_SHORT).show();
            return;
        }


        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(MainActivity.this,"Login successful..",Toast.LENGTH_SHORT).show();
                            FirebaseUser user = mAuth.getCurrentUser();
                            Continue();
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(MainActivity.this, "Authentication failed."+task.getException().getMessage()+"Try again",
                                    Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });
    }

    public void Continue(){
        Intent intent = new Intent(this,homePage.class);
        startActivity(intent);
    }





}
