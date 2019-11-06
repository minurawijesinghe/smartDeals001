package com.example.smartdeals;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class productDiscription extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_discription);
        Button locate = (Button)findViewById(R.id.buttonLocate);
        Button reserve = (Button)findViewById(R.id.buttonResevre);
        TextView seller = (TextView) findViewById(R.id.seller);



        seller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSellerProfile();
            }
        });

        reserve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openReserve();
                Toast.makeText(productDiscription.this, "your item is reserved", Toast.LENGTH_SHORT).show();
                openHomepage();


            }
        });


        ImageView imageproduct =(ImageView)findViewById(R.id.imageproduct);
        locate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMap();
            }
        });

    }
    public void openMap(){
        Uri gmmIntentUri = Uri.parse("google.navigation:q=6.824749,79.870251");
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        startActivity(mapIntent);

    }
    public void openReserve(){
        //Intent intent =  new Intent(this,customer_reserve.class);
       // startActivity(intent);
    }
    public void openSellerProfile(){
        Intent intent = new Intent(this,sellerProfile.class);
        startActivity(intent);
    }
    public void openHomepage(){
        Intent intent  = new Intent(this,homePage.class);
        startActivity(intent);
    }



}
