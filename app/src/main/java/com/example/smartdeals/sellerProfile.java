package com.example.smartdeals;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class sellerProfile extends AppCompatActivity {



        FirebaseAuth mAuth;


         DatabaseReference mDatabase3;



        ListView listView;

                      int count =0;

    Button editItem;
        String name1;
        List<String> NamesOfGoods = new ArrayList<>();
        List<String> listOfGoodsInTheShop = new ArrayList<>();



        List<String> titles = new ArrayList<>();
        String title,discription,price,name,discount,image,sellerId,likeCount;

    List<String> discriptions = new ArrayList<>();
        List<String> prices = new ArrayList<>();
        List<String> names = new ArrayList<>();
        List<String> discounts = new ArrayList<>();
        List<String> imagesL = new ArrayList<>();
        List<String> sellerIds = new ArrayList<>();
        List<String>likeCounts = new ArrayList<>();





        Button listShow;
        String currentUserId;

        //setting the values for items inthe list
       

        // now paste some images in drawable

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_seller_profile);



            mAuth = FirebaseAuth.getInstance();
             currentUserId = mAuth.getCurrentUser().getUid();



            listShow = (Button)findViewById(R.id.listShow);
            mDatabase3 = FirebaseDatabase.getInstance().getReference();









            final ProgressDialog progressDialog1 = new ProgressDialog(this);
            progressDialog1.setTitle("Downloading content ..!");
            progressDialog1.show();

            mDatabase3.child("Items").addListenerForSingleValueEvent(new ValueEventListener() {

                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        //get the namse to list
                        name1 = ds.getKey();
                        count++;
                        NamesOfGoods.add(name1);


                    }

                    
                  listOfGoodsInTheShop=  check(NamesOfGoods,currentUserId);
                    check(NamesOfGoods,currentUserId);
                    progressDialog1.dismiss();

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });


            listView = findViewById(R.id.listViewSellerPro);

            final sellerProfile.MyAdapter adapter = new sellerProfile.MyAdapter(this, titles, discriptions, imagesL,prices);


            //////////////////////////////////////////////////
            //me kalla run wenda one

            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Downloading content ..!");
            progressDialog.show();


            mDatabase3.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for(int i=0;i<listOfGoodsInTheShop.size();i++){

                        title =  dataSnapshot.child("Items").child(listOfGoodsInTheShop.get(i)).child("Title").getValue().toString();
                        titles.add(title);

                        discription =  dataSnapshot.child("Items").child(listOfGoodsInTheShop.get(i)).child("Discription").getValue().toString();
                        discriptions.add(discription);


                        price =  dataSnapshot.child("Items").child(listOfGoodsInTheShop.get(i)).child("Price").getValue().toString();
                        prices.add(price);

                        sellerId =  dataSnapshot.child("Items").child(listOfGoodsInTheShop.get(i)).child("SellerID").getValue().toString();
                        sellerIds.add(sellerId);


                        image =  dataSnapshot.child("Items").child(listOfGoodsInTheShop.get(i)).child("Uri").getValue().toString();
                        imagesL.add(image);

                        name =  dataSnapshot.child("Items").child(listOfGoodsInTheShop.get(i)).child("name").getValue().toString();
                        names.add(name);

                        discount =  dataSnapshot.child("Items").child(listOfGoodsInTheShop.get(i)).child("Discount").getValue().toString();
                        discounts.add(discount);

                        likeCount =dataSnapshot.child("Items").child(listOfGoodsInTheShop.get(i)).child("Like").getValue().toString();
                        likeCounts.add(likeCount);

                        count++;

                    }
                     if (count+1==listOfGoodsInTheShop.size()) {
                         adapterInitializer(adapter);
                     }

                    progressDialog.dismiss();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
             //////////////////////////////////////

            /////////////////////////////////////
            //me kalla run krnna one


            ///////////////////////////////////////

            listShow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });













            // now create an adapter class
            editItem = (Button)findViewById(R.id.addItem);


            // now again check this..
            editItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openEditItem();
                }
            });

            //  set item click on list view
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                   
                }
            });
            // so item click is done now check list view
        }

    private void openEditItem() {

        Intent intent = new Intent(this, editItem.class);
        startActivity(intent);
    }

    class MyAdapter extends ArrayAdapter<String> {

        Context context;
        List<String> rTitle;
        List<String> rDescription;
        List<String> rImgs;
        List<String>rPrices;
        MyAdapter (Context c,List<String> ltitle, List<String> ldescription,List<String> limages,List<String>lprices) {
            super(c, R.layout.row_homepage, R.id.textView1, ltitle);
            this.context = c;
            this.rTitle = ltitle;
            this.rDescription = ldescription;
            this.rImgs = limages;
            this.rPrices=lprices;


        }


        @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                LayoutInflater layoutInflater = (LayoutInflater)getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View row = layoutInflater.inflate(R.layout.row_sellerprofile, parent, false);
                ImageView images = row.findViewById(R.id.image1);
                TextView myTitle = row.findViewById(R.id.textView4);
                TextView myDescription = row.findViewById(R.id.textView5);
                TextView myPrice = row.findViewById(R.id.textView3);


            Picasso.get().load(rImgs.get(position)).into(images);

            myTitle.setText(rTitle.get(position));
            myDescription.setText(rDescription.get(position));
            myPrice.setText("Rs."+rPrices.get(position)+".00");



                return row;
            }
        }

        public void openProductDiscription(){
            Intent intent = new Intent(this, productDiscription.class);
            startActivity(intent);
        }
    public List check(List<String >names ,String toSearch ){

        List<String> results = new ArrayList<>();
        for(int i =0;i<names.size();i++){
            String test = names.get(i);

            boolean seqFound = test.contains(toSearch);

            if (seqFound == true){

                results.add(names.get(i));

            }

        }

        return    results;


    }
    public  void adapterInitializer(ListAdapter adapter){
        listView.setAdapter(adapter);


    }
    }

