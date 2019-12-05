package com.example.smartdeals;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class addDiscount extends AppCompatActivity {
    DatabaseReference mDatabase;
    private StorageReference mStorageRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_discount);


        final EditText discountPercentage =(EditText)findViewById(R.id.discountAmount);
        TextView previousValue = (TextView)findViewById(R.id.previousPrice);
        TextView currentValue = (TextView)findViewById(R.id.currentPrice);
        Button launchDis =(Button)findViewById(R.id.lauchDis);
        Button back = (Button)findViewById(R.id.backButton);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mStorageRef = FirebaseStorage.getInstance().getReference();



        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String percentage = discountPercentage.getText().toString();
                if (percentage==null){
                    Toast.makeText(addDiscount.this, "percentage is null",Toast.LENGTH_SHORT).show();
                }   else {

                  mDatabase.child("Item").child(homePage.lnames.toString());
                }
            }
        });



    }
}
