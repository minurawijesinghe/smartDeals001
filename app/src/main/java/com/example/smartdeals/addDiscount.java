package com.example.smartdeals;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class addDiscount extends AppCompatActivity {
    DatabaseReference mDatabase;
    private StorageReference mStorageRef;
    String currentDiscount;
    TextView currentDisc;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_discount);
        Intent intent = getIntent();

        final String productName = intent.getStringExtra("productName");


        final EditText discountPercentage =(EditText)findViewById(R.id.discountAmount);
        TextView previousValue = (TextView)findViewById(R.id.previousPrice);
        currentDisc = (TextView)findViewById(R.id.currentDiscount);
        TextView currentValue = (TextView)findViewById(R.id.currentPrice);
        Button launchDis =(Button)findViewById(R.id.lauchDis);
        Button back = (Button)findViewById(R.id.backButton);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mStorageRef = FirebaseStorage.getInstance().getReference();




        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                currentDiscount = dataSnapshot.child("Items").child(productName).child("Discount").getValue().toString();

                currentDisc.setText("Rs."+currentDiscount+".00");



            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




        launchDis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String percentage = discountPercentage.getText().toString();
                if (percentage.matches("")){
                    Toast.makeText(addDiscount.this, "percentage is null",Toast.LENGTH_SHORT).show();
                }   else {

                  mDatabase.child("Items").child(productName).child("Discount").setValue(percentage);
                  mDatabase.child("discount").child(productName).setValue(percentage);
                    Toast.makeText(addDiscount.this, "Discount is updated",Toast.LENGTH_SHORT).show();

                }
            }
        });
    }
}
