package com.example.smartdeals;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class register extends AppCompatActivity {
    EditText inREmail,inRpass;
    Button buttonRegisterBuyer,buttonRegisterSeler;
    public FirebaseAuth firebaseAuth;
    DatabaseReference mDatabase2;
    EditText userName;
    String currentSellers;
    double sLong,sLat ;
    private static final String TAG = "register";
    private LocationManager locationManager;
    private LocationListener locationListener;
    public static String tvLongi;
    public static String tvLati;
    Button button;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        button = (Button)findViewById(R.id.button);

        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                      tvLati =("  " +location.getLatitude()  ) ;
                      tvLongi  =("  " +location.getLongitude()  ) ;
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        }  ;
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.INTERNET
            },10);
            return;
        }
        else{
            configureButton();
        }


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 10:
                if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED)
                    configureButton();
                return;
        }
    }

    private void configureButton(){
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                locationManager.requestLocationUpdates("gps", 5000, 0, locationListener);
            }
        });

    






    mDatabase2 = FirebaseDatabase.getInstance().getReference();

        userName = (EditText) findViewById(R.id.userRName);
        firebaseAuth = FirebaseAuth.getInstance();
        inREmail = (EditText) findViewById(R.id.inREmail);
        inRpass = (EditText) findViewById(R.id.inRpass);
        buttonRegisterBuyer = (Button) findViewById(R.id.buttonRegisterBuyer);
        buttonRegisterSeler  =(Button)findViewById(R.id.buttonRegisterSeller) ;

        /////////////////////////////////////////





        

        //////////////////////////////////////




        buttonRegisterBuyer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserRegister(false);
            }


        });

        buttonRegisterSeler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserRegister(true);
            }
        });

        //occupied



        mDatabase2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                currentSellers  = dataSnapshot.child("SellersAuthKeys").getValue().toString();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }
    private void UserRegister(final boolean verify) {

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
                    if (verify==true){
                        String finalList = currentSellers + " ,"+user_id;

                        mDatabase2.child("SellersAuthKeys").setValue(finalList);

                        String Lat = Double.toString(sLat);
                        String Long = Double.toString(sLong);

                        Map datamap = new HashMap();
                        datamap.put("Latitude",tvLati);
                        datamap.put("Longitude",tvLongi);

                        mDatabase2.child(user_id).child("Location").setValue(datamap);



                    }


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

  

}


