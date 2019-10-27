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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class register extends AppCompatActivity {
    EditText inREmail,inRpass;
    Button buttonRegister;
    public FirebaseAuth firebaseAuth;
    EditText userName;
    String occupied;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference();



        userName = (EditText) findViewById(R.id.userRName);
        firebaseAuth = FirebaseAuth.getInstance();
        inREmail = (EditText) findViewById(R.id.inREmail);
        inRpass = (EditText) findViewById(R.id.inRpass);
        buttonRegister = (Button) findViewById(R.id.buttonRegisterSeller);



        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserRegister();
            }


        });

        //occupied



    }
    private void UserRegister() {

        final String email,password,name;

        name = userName.getText().toString();
        email = inREmail.getText().toString();
        password = inRpass.getText().toString();

        if (TextUtils.isEmpty(email)||TextUtils.isEmpty(password)){
            Toast.makeText(register.this,"Empty fields are there",Toast.LENGTH_SHORT).show();
            return;

        }


        firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful())
                {
                    Toast.makeText(register.this,"Registration Successful",Toast.LENGTH_SHORT).show();

                    String user_id = firebaseAuth.getCurrentUser().getUid();
                    DatabaseReference current_user_db = FirebaseDatabase.getInstance().getReference().child("users").child(user_id);
                    Map newpost = new HashMap();
                    newpost.put("name",name);
                    current_user_db.setValue(newpost);


                    LoginPage();

                }else{
                    Toast.makeText(register.this,"Registration Error"+ task.getException().getMessage(),Toast.LENGTH_SHORT).show();

                }

            }
        });

    }
    public void LoginPage(View view) {
        Intent intent  = new Intent(this,MainActivity.class);
        startActivity(intent);
    }
    public void LoginPage() {
        Intent intent  = new Intent(this,MainActivity.class);
        startActivity(intent);
    }
   /* private void SendReferenceURL(String key) {
        String FIREBASE_URL ="https://android-studio-64e2c.firebaseio.com/";
        Toast.makeText(ActivityRegister.this, "Opening board: "+key, Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this, Activity5.class);
        intent.putExtra("FIREBASE_URL", FIREBASE_URL);
        intent.putExtra("BOARD_ID", key);

    }*/
}


