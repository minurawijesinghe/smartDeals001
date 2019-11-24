package com.example.smartdeals;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class homePage extends AppCompatActivity {

    private FirebaseAuth title,description,image;
    DatabaseReference mDatabase3;
    DatabaseReference mDatabase4;
    DatabaseReference mDatabase5;
    DatabaseReference mDatabase6;
    String name;
    String titles;
    String urls;
    String descriptions;



    private LocationManager locationManager;
    private LocationListener locationListener;
    public static String tvLongi;
    public static String tvLati;
    boolean stateCheck = false;


    ListView listView;
    List<String> ltitle = new ArrayList<>();
    List<String> ldescription = new ArrayList<>();
    List<String> limages = new ArrayList<>();
    List<String> lnames = new ArrayList<>();
    List<String> results = new ArrayList<>();
    List<String> Distances = new ArrayList<>();


    List<String> SellerIDs = new ArrayList<>();
    List<String> prices  =new ArrayList<>();
    List<String> discriptions  =new ArrayList<>();
    List<String> Titiles  =new ArrayList<>();
    List<String> uris = new ArrayList<>();
    List<Double> latitudes =  new ArrayList<>();
    List<Double> longitudes = new ArrayList<>();



    Button go;
    EditText searchView;
    int count =0;

    String mTitle[] = {"Coca cola", "Auto mobile parts", "Digital marcketing", "Electronis", "Facebook","Potatoes"};
    String mDescription[] = {"Stand a chance to win 2 air tickets to melbourn", "10% discount on any automobile part", "Looking for a chance to promote your business on discount", "discounts on electronics up to 25%", "facebook marckeitng for free for two first two members apply","20% discount for potatoes "};
    int images[] = { R.drawable.cocacola, R.drawable.engine, R.drawable.marcketing, R.drawable.innovation,R.drawable.facebook,R.drawable.potato };
   
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_home_page);
        mDatabase3 = FirebaseDatabase.getInstance().getReference();
        mDatabase4 = FirebaseDatabase.getInstance().getReference();
        mDatabase5 = FirebaseDatabase.getInstance().getReference();
        mDatabase6 = FirebaseDatabase.getInstance().getReference();








        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Downloading content ..!");
        progressDialog.show();
        mDatabase3.child("Items").addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    name = ds.getKey();
                    count++;
                    lnames.add(name);


                }
                progressDialog.dismiss();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        go = findViewById(R.id.go);
        searchView = findViewById(R.id.searchbar);




        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String SearchItem = searchView.getText().toString();
               results =  check(lnames,SearchItem);


               mDatabase5.addListenerForSingleValueEvent(new ValueEventListener() {
                   @Override
                   public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                       for (int i =0; i<results.size();i++){
                           String SellerID = dataSnapshot.child("Items").child(results.get(i)).child("SellerID").getValue().toString();
                           SellerIDs.add(SellerID);
                           String price = dataSnapshot.child("Items").child(results.get(i)).child("Price").getValue().toString();
                            prices.add(price);
                           String discript = dataSnapshot.child("Items").child(results.get(i)).child("Discription").getValue().toString();
                           discriptions.add(discript);
                           String title = dataSnapshot.child("Items").child(results.get(i)).child("Title").getValue().toString();
                            Titiles.add(title);
                           String uri = dataSnapshot.child("Items").child(results.get(i)).child("Uri").getValue().toString();
                              uris.add(uri);



                       }


                   }

                   @Override
                   public void onCancelled(@NonNull DatabaseError databaseError) {

                   }
               });


               mDatabase6.addListenerForSingleValueEvent(new ValueEventListener() {
                   @Override
                   public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                       for (int i=0; i<results.size();i++){

                           String lat = dataSnapshot.child(SellerIDs.get(i)).child("Location").child("Latitude").getValue().toString();
                           String lng = dataSnapshot.child(SellerIDs.get(i)).child("Location").child("Longitude").getValue().toString();

                            latitudes.add(Double.parseDouble(lat)) ;
                            longitudes.add(Double.parseDouble(lng)) ;

                       }
                       Distances = distanceCalculate(latitudes,longitudes,tvLati,tvLongi);
                       String  dn = "kjbv";
                   }

                   @Override
                   public void onCancelled(@NonNull DatabaseError databaseError) {

                   }
               });











            }
        });




            mDatabase4.addListenerForSingleValueEvent(new ValueEventListener() {

                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot){
                    for (int i=0;i<count;i++) {

                        final String currentName = lnames.get(i);
                        titles = dataSnapshot.child("Items").child(currentName).child("Title").getValue().toString();
                        ltitle.add(titles);
                        urls = dataSnapshot.child("Items").child(currentName).child("Uri").getValue().toString();
                        limages.add(urls);
                        descriptions = dataSnapshot.child("Items").child(currentName).child("Discription").getValue().toString();
                        ldescription.add(descriptions);

                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });


        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        final ProgressDialog progressDialog4 = new ProgressDialog(this);
        progressDialog4.setTitle("Downloading content ..!");
        progressDialog4.show();

        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                tvLati =("  " +location.getLatitude()  ) ;
                tvLongi  =("  " +location.getLongitude()  ) ;
                progressDialog4.dismiss();
                
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

    private void configureButton() {
        locationManager.requestLocationUpdates("gps", 50, 0, locationListener);







        listView = findViewById(R.id.listView);
        // now create an adapter class

        MyAdapter adapter = new MyAdapter(this, mTitle, mDescription, images);
        listView.setAdapter(adapter);




        // now set item click on list view
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position ==  0) {
                    Toast.makeText(homePage.this, "Coca cola Description", Toast.LENGTH_SHORT).show();
                    openProductDiscriptio();
                }
                if (position ==  1) {
                    Toast.makeText(homePage.this, "Automobile Description", Toast.LENGTH_SHORT).show();
                    openProductDiscriptio();

                }
                if (position ==  2) {
                    Toast.makeText(homePage.this, "Digital marcketing Description", Toast.LENGTH_SHORT).show();
                    openProductDiscriptio();

                }
                if (position ==  3) {
                    Toast.makeText(homePage.this, "Electronics Description", Toast.LENGTH_SHORT).show();
                    openProductDiscriptio();

                }
                if (position ==  4) {
                    Toast.makeText(homePage.this, "Facebook marcketing Description", Toast.LENGTH_SHORT).show();
                    openProductDiscriptio();

                }
                if (position ==  5) {
                    Toast.makeText(homePage.this, "Facebook marcketing Description", Toast.LENGTH_SHORT).show();
                    openProductDiscriptio();

                }
                if (position ==  6) {
                    Toast.makeText(homePage.this, "Facebook marcketing Description", Toast.LENGTH_SHORT).show();
                    openProductDiscriptio();

                }
            }
        });
        // so item click is done now check list view
    }

    class MyAdapter extends ArrayAdapter<String> {

        Context context;
        String rTitle[];
        String rDescription[];
        int rImgs[];

        MyAdapter (Context c, String title[], String description[], int imgs[]) {
            super(c, R.layout.row_homepage, R.id.textView1, title);
            this.context = c;
            this.rTitle = title;
            this.rDescription = description;
            this.rImgs = imgs;

        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater)getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = layoutInflater.inflate(R.layout.row_homepage, parent, false);
            ImageView images = row.findViewById(R.id.image);
            TextView myTitle = row.findViewById(R.id.textView1);
            TextView myDescription = row.findViewById(R.id.textView2);

            // now set our resources on views
            images.setImageResource(rImgs[position]);
            myTitle.setText(rTitle[position]);
            myDescription.setText(rDescription[position]);




            return row;
        }
    }

    public void openProductDiscriptio(){
        Intent intent = new Intent(this,productDiscription.class);
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
    public List distanceCalculate(List<Double>latitudes,List<Double>longitudes,String currentLati,String CurrentLongi){

         List<Double> Distances = new ArrayList<>();

        Location locationC = new Location("");
        locationC.setLatitude(Double.parseDouble(currentLati));
        locationC.setLongitude(Double.parseDouble(CurrentLongi));



        for (int i=0;i<latitudes.size();i++){

           Location Shop = new Location("");
           Shop.setLatitude(latitudes.get(i));
           Shop.setLongitude(longitudes.get(i));

           double distanceInMeters = locationC.distanceTo(Shop);
           Distances.add(distanceInMeters);



        }

       return Distances;

    }

}
