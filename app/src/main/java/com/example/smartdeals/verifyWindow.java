package com.example.smartdeals;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class verifyWindow extends AppCompatActivity {


    String requestList,shopNames,latList,lonList ;
    DatabaseReference databaseReference;


     Button verify,locate;
     TextView latitude,longitude,shopName,shopId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_window);


        databaseReference = FirebaseDatabase.getInstance().getReference();

        verify = (Button)findViewById(R.id.verify);
        locate = (Button)findViewById(R.id.locate);
        latitude = (TextView)findViewById(R.id.latitude);
        longitude = (TextView)findViewById(R.id.longitude);
        shopName= (TextView)findViewById(R.id.shopName);
        shopId = (TextView)findViewById(R.id.shopId);

        Intent intent = getIntent();

        requestList = intent.getStringExtra("requestList");
        shopNames = intent.getStringExtra("shopNames");
        latList = intent.getStringExtra("latList") ;
        lonList = intent.getStringExtra("lonList");



        shopName.setText(shopNames);
        latitude.setText(latList);
        longitude.setText(lonList);
        shopId.setText(requestList);





        locate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMap();
            }
        });
        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference.child(requestList).child("Location").child("verification").setValue("true");
                Toast.makeText(verifyWindow.this,"verfication done",Toast.LENGTH_SHORT).show();

            }
        });






    }



    public void openMap(){

        Uri gmmIntentUri = Uri.parse("google.navigation:q="+latList+","+lonList);
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        startActivity(mapIntent);

    }
}
