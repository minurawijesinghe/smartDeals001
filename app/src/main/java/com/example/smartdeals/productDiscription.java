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

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class productDiscription extends AppCompatActivity {

    int quantityInt =0;
    DatabaseReference mdatabase8;
    List<String> productSummary = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_discription);





        Button locate = (Button)findViewById(R.id.buttonLocate);
        Button reserve = (Button)findViewById(R.id.buttonResevre);
        TextView seller = (TextView) findViewById(R.id.seller);
        final TextView quantity = (TextView)findViewById(R.id.quantity_text_view);
        final Button increment = (Button) findViewById(R.id.buttonIncrement);
        final Button decrement = (Button)findViewById(R.id.ButtonDecrement);
        Intent intent = getIntent();
        ImageView imageView =(ImageView)findViewById(R.id.imageproduct);
        TextView discript = (TextView)findViewById(R.id.offerDiscription);
        TextView titl = (TextView)findViewById(R.id.productdescription);
        TextView price = (TextView)findViewById(R.id.price);
        productSummary = intent.getStringArrayListExtra("productSummary");
        discript.setText(productSummary.get(1));
        titl.setText(productSummary.get(0));
        price.setText(productSummary.get(3));
        seller.setText(productSummary.get(4));
        
        ;


        mdatabase8 = FirebaseDatabase.getInstance().getReference();


        Picasso.get().load(productSummary.get(2)).into(imageView);


        quantity.setHint("0");


        increment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quantityInt = increment(quantityInt);
                quantity.setText(Integer.toString(quantityInt));
            }
        });
        decrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quantityInt = decrement(quantityInt);

                quantity.setText(Integer.toString(quantityInt));
            }
        });




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

    public   int increment(int quantityInt){
             if (quantityInt<5){
        quantityInt++;           }

          return quantityInt;

    }
    public  int decrement(int quantityInt){

        if (quantityInt>0){
            quantityInt--;

        }
        return quantityInt;
    }


}
