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
import android.widget.Spinner;
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
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class homePage extends AppCompatActivity {

     FirebaseAuth mAuth2 ;
    DatabaseReference mDatabase3;
    DatabaseReference mDatabase4;
    String name;
    String titles;
    String urls;
    String descriptions;
    String price;


    ListView listView;
    List<String> ltitle = new ArrayList<>();
    List<String> ldescription = new ArrayList<>();
    List<String> limages = new ArrayList<>();
     List<String> lnames = new ArrayList<>();
    List<String> lprices = new ArrayList<>();
    List<String>discounts = new ArrayList<>();

    List<String>likes = new ArrayList<>();
    List<String>dislikes = new ArrayList<>();


    List<String>sortedDiscount =  new ArrayList<>();






    List<String> sortedPrices = new ArrayList<>();
    List<String> sortedDiscriptios = new ArrayList<>();
    List<String> sortedUris = new ArrayList<>();
    List<String> sortedTitle = new ArrayList<>();
    List<String> sortedAuths = new ArrayList<>();
    List<Double> sortedDistance = new ArrayList<>();
    List<String>sortedDistancesStrings = new ArrayList<String>();

    List<String>productSummary =  new ArrayList<String>();
    int spinnerPossition;


    List<String> titleList = new ArrayList<String>();
    List<String> discriptionList = new ArrayList<String>();
    List<String> imageList = new ArrayList<String>();
    List<String>pricelist = new ArrayList<String>();
    List<String>shops = new ArrayList<>();


    List<List<String>> distanceVlues= new ArrayList<>();
    List<List<String>> priceVlues= new ArrayList<>();
    List<List<String>> likeVlues= new ArrayList<>();

    List<String> cockies = new ArrayList<>();






    DatabaseReference mDatabase5;
    DatabaseReference mDatabase6;

    Spinner spinner;




    private LocationManager locationManager;
    private LocationListener locationListener;
    public static String tvLongi;
    public static String tvLati;
    String shop;


   
    List<String> results = new ArrayList<>();
    List<Double> Distances = new ArrayList<>();


    List<String> SellerIDs = new ArrayList<>();
    List<String> pricesSList =new ArrayList<>();
    List<String> discriptions  =new ArrayList<>();
    List<String> Titiles  =new ArrayList<>();
    List<String>shopNames = new ArrayList<>();
    List<String> uris = new ArrayList<>();
    List<Double> latitudes =  new ArrayList<>();
    List<Double> longitudes = new ArrayList<>();
    List<String>shopList = new ArrayList<>();

    String  currentUserID  ;
    int count =0;

    @Override
    protected void onRestart() {
        results.clear();
        super.onRestart();
        Titiles.clear();
        discriptions.clear();
        pricelist.clear();
        uris.clear();
        productSummary.clear();
        spinnerPossition=0;
        sortedPrices.clear();
        sortedDiscriptios.clear();
        sortedUris.clear();
        sortedDistance.clear();
        sortedAuths.clear();
        lnames.clear();
        count=0;



    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_home_page);


        results.clear();

        mDatabase3 = FirebaseDatabase.getInstance().getReference();
        mDatabase4 = FirebaseDatabase.getInstance().getReference();
        mDatabase5 = FirebaseDatabase.getInstance().getReference();
        mDatabase6 = FirebaseDatabase.getInstance().getReference();
        mAuth2  = FirebaseAuth.getInstance();


        
        currentUserID = mAuth2.getUid();
        spinner = (Spinner)findViewById(R.id.spinner);

        Button go = (Button)findViewById(R.id.go);
        final EditText searchView = (EditText)findViewById(R.id.searchbar);



        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Downloading content ..!");
        progressDialog.show();
        //this database reference for get the names of the items in the list in database
        mDatabase3.child("Items").addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    //get the namse to list
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


        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //getting the input in the search box
                String SearchItem = searchView.getText().toString();
                //checck the database with the key word
                results =  check(lnames,SearchItem);




                //methana spinner eke possition eka enwa no special nm 0, time nam 1,distance nam 2.
               spinnerPossition= spinner.getLastVisiblePosition();

               int k =0;



                //this database listener for get other details of the searched products

                mDatabase5.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        for (int i =0; i<results.size();i++){
                            String SellerID = dataSnapshot.child("Items").child(results.get(i)).child("SellerID").getValue().toString();
                            SellerIDs.add(SellerID);
                            String price = dataSnapshot.child("Items").child(results.get(i)).child("Price").getValue().toString();
                            pricelist.add(price);
                            String discript = dataSnapshot.child("Items").child(results.get(i)).child("Discription").getValue().toString();
                            discriptions.add(discript);
                            String title = dataSnapshot.child("Items").child(results.get(i)).child("Title").getValue().toString();
                            Titiles.add(title);
                            String uri = dataSnapshot.child("Items").child(results.get(i)).child("Uri").getValue().toString();
                            uris.add(uri);
                            String discount=dataSnapshot.child("Items").child(results.get(i)).child("Discount").getValue().toString();
                            discounts.add(discount);
                            String like=dataSnapshot.child("Items").child(results.get(i)).child("Like").getValue().toString();
                            likes.add(like);
                            String dislike=dataSnapshot.child("Items").child(results.get(i)).child("Dislike").getValue().toString();
                            dislikes.add(dislike);
                            String namez=dataSnapshot.child("Items").child(results.get(i)).child("name").getValue().toString();
                            shopNames.add(namez);


                        }


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                //this data base reference is for   get the location corintes od the shop for the distance calculation
                mDatabase6.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (int i=0; i<results.size();i++){

                            String lat = dataSnapshot.child(SellerIDs.get(i)).child("Location").child("Latitude").getValue().toString();
                            String lng = dataSnapshot.child(SellerIDs.get(i)).child("Location").child("Longitude").getValue().toString();

                            latitudes.add(Double.parseDouble(lat));
                            longitudes.add(Double.parseDouble(lng));

                        }
                        Distances = distanceCalculate(latitudes,longitudes,tvLati,tvLongi);


                        sortingDistance(Distances,results) ;
                        sortingPrice(results,pricelist,discounts);
                        sortingLike(results,likes,dislikes);

                        //me click kiyana function ekata userta ona type eke parameter eka yawanna
                        click(results,distanceVlues,priceVlues,likeVlues,Distances,pricelist,Titiles,uris,discriptions,discounts,shopNames);
                        String aaa="dd";
                        
                        nextActivity();


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });





            }
        });





        mDatabase4.addListenerForSingleValueEvent(new ValueEventListener() {
            List<Integer> indexes = new ArrayList<>();
              // getting the database list of all items
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot){
                for (int i=0;i<count;i++) {

                    final String currentName = lnames.get(i);
                    titles = dataSnapshot.child("Items").child(currentName).child("Title").getValue().toString();
                    ltitle.add(titles);
                    urls = dataSnapshot.child("Items").child(currentName).child("Uri").getValue().toString();
                    limages.add(urls);
                    price = dataSnapshot.child("Items").child(currentName).child("Price").getValue().toString();
                    lprices.add(price);


                    descriptions = dataSnapshot.child("Items").child(currentName).child("Discription").getValue().toString();
                    ldescription.add(descriptions);
                    shop = dataSnapshot.child("Items").child(currentName).child("name").getValue().toString();
                    shopNames.add(shop);


                         indexes.add(i);


                }
                Collections.shuffle(indexes);
                for (int i=0;i<count;i++){
                    titleList.add(ltitle.get(indexes.get(i)));
                    imageList.add(limages.get(indexes.get(i)));
                    discriptionList.add(ldescription.get(indexes.get(i)));
                    pricesSList.add(lprices.get(indexes.get(i)));
                    shopList.add(shopNames.get(i));


                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

           //this cosde is for get the users current location
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
        final ProgressDialog progressDialog7 = new ProgressDialog(this);
        progressDialog7.setTitle("downloading content");
        progressDialog7.show();
        MyAdapter adapter = new MyAdapter(this, titleList, discriptionList,imageList,pricesSList);
        listView.setAdapter(adapter);

         progressDialog7.dismiss();


        // now set item click on list view
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(homePage.this,Integer.toString(position),Toast.LENGTH_SHORT).show();

               openProductDiscription(position);
            }
        });
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
            View row = layoutInflater.inflate(R.layout.row_homepage, parent, false);

            ImageView images =row.findViewById(R.id.image);
            TextView myTitle = row.findViewById(R.id.textView1);
            TextView myDescription = row.findViewById(R.id.textView2);
            TextView myPrice = row.findViewById(R.id.textView3);


            Picasso.get().load(rImgs.get(position)).into(images);

            myTitle.setText(rTitle.get(position));
            myDescription.setText(rDescription.get(position));
            myPrice.setText("Rs."+rPrices.get(position)+".00");




            return row;
        }

    }
   

    public void openProductDiscription(int index){

        productSummary.add(titleList.get(index));
        productSummary.add(discriptionList.get(index));
        productSummary.add(imageList.get(index));
        productSummary.add(pricesSList.get(index));
        productSummary.add(shopList.get(index));


        Intent intent = new Intent(this,productDiscription.class);
        intent.putExtra("productSummary",(Serializable) productSummary);
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
    public void nextActivity(){
        Intent intent  = new Intent(this,searchedList.class);
        intent.putExtra("titleList", (Serializable) sortedTitle);
        intent.putExtra("imageList", (Serializable) sortedUris);
        intent.putExtra("discriptionList", (Serializable)  sortedDiscriptios);
        intent.putExtra("priceList",(Serializable) sortedPrices);
        intent.putExtra("shopnames",(Serializable)shopNames);
        intent.putExtra("results",(Serializable)results);

        

        startActivity(intent);



    }





    public void sortingPrice(List<String> results,List<String> pricelist,List<String> discounts )
    {
        List<String> key2=new ArrayList<>();
        List<String> price2=new ArrayList<>();
        List<String> discout2=new ArrayList<>();
        for(int i=0;i<results.size();i++)
        {
            key2.add(results.get(i));
        }

        for(int i=0;i<pricelist.size();i++)
        {
            price2.add(pricelist.get(i));
        }

        for(int i=0;i<discounts.size();i++)
        {
            discout2.add(discounts.get(i));
        }


        String tempSt2="";
        Double temp2;
        String tempn2="";

        List<Double> doublePrice=new ArrayList<>();
        List<Double> doublediscount=new ArrayList<>();
        List<List<String>> priceResult= new ArrayList<>();

        for(int i=0;i<key2.size();i++)
        {
            List<String> temp = new ArrayList<>();
            for(int j=0;j<2;j++)
            {
                if(j==0)
                    temp.add(key2.get(i));
                else
                    temp.add("0");
            }
            priceResult.add(temp);
        }
        String asa="hdfj";

        Double p;

        for(int i=0;i<price2.size();i++)
        {
            p=Double.valueOf(price2.get(i));
            doublePrice.add(p);
        }

        for(int i=0;i<discout2.size();i++)
        {
            doublediscount.add(Double.valueOf(discout2.get(i)));
        }


        Double sub;
        for(int i=0;i<price2.size();i++)
        {
            sub=doublePrice.get(i)-doublediscount.get(i);
            doublePrice.set(i,sub);
        }

        String bb="aa";
        for(int i=0;i<doublePrice.size();i++)
        {
            for(int j=i+1;j<doublePrice.size();j++)
            {
                if (doublePrice.get(i) > doublePrice.get(j)) {
                    tempSt2=key2.get(i);
                    temp2=doublePrice.get(i);

                    key2.set(i,key2.get(j));
                    doublePrice.set(i,doublePrice.get(j));

                    key2.set(j,tempSt2);
                    doublePrice.set(j,temp2) ;

                }
            }

        }

        String keyChecker="0";
        int count=1;

        for(int i=0;i<priceResult.size();i++)
        {
            List<String> eleArray=new ArrayList<>();
            keyChecker= priceResult.get(i).get(0);
            eleArray.add(priceResult.get(i).get(0));
            eleArray.add(priceResult.get(i).get(1));
            for(int j=0;j<key2.size();j++)
            {

                if(key2.get(j)==keyChecker)
                {
                    eleArray.set(1,String.valueOf(count));
                }
                else
                    count++;
            }
            priceResult.set(i,eleArray);
            count=1;
        }

        String aa="ss";
        priceVlues=priceResult;


        // List<Double> ds = new ArrayList<Double>();
// fill ds with Doubles
        List<String> strings = new ArrayList<String>();
        for (Double d : sortedDistance) {
            // Apply formatting to the string if necessary
            sortedDistancesStrings.add(d.toString());
        }

    }
////////////////////////////////////////////////////////////////////





    public void sortingLike(List<String> results,List<String> likes,List<String> dislikes)
    {
        List<String> key3=new ArrayList<>();
        List<String> like=new ArrayList<>();
        List<String> dislike=new ArrayList<>();
        for(int i=0;i<results.size();i++)
        {
            key3.add(results.get(i));
        }

        for(int i=0;i<likes.size();i++)
        {
            like.add(likes.get(i));
        }

        for(int i=0;i<dislikes.size();i++)
        {
            dislike.add(dislikes.get(i));
        }

        String tempSt3="";
        Integer temp3;

        List<Integer> intLike=new ArrayList<>();
        List<Integer> intDislike=new ArrayList<>();
        List<Integer> totLike=new ArrayList<>();
        int t;

        List<List<String>> likeResult= new ArrayList<>();

        for(int i=0;i<key3.size();i++)
        {
            List<String> temp = new ArrayList<>();
            for(int j=0;j<2;j++)
            {
                if(j==0)
                    temp.add(key3.get(i));
                else
                    temp.add("0");
            }
            likeResult.add(temp);
        }

        for(int i=0;i<like.size();i++)
        {
            intLike.add(Integer.parseInt(like.get(i)));
        }

        for(int i=0;i<dislike.size();i++)
        {
            intDislike.add(Integer.parseInt(dislike.get(i)));
        }

        for(int i=0;i<like.size();i++)
        {
            t=intLike.get(i)-3*intDislike.get(i);
            totLike.add(t);
        }

        for(int i=0;i<totLike.size();i++)
        {
            for(int j=i+1;j<totLike.size();j++)
            {
                if (totLike.get(i) > totLike.get(j)) {
                    tempSt3=key3.get(i);
                    temp3=totLike.get(i);

                    key3.set(i,key3.get(j));
                    totLike.set(i,totLike.get(j));

                    key3.set(j,tempSt3);
                    totLike.set(j,temp3) ;

                }
            }

        }


        String keyChecker="0";
        int count=1;

        for(int i=0;i<likeResult.size();i++)
        {
            List<String> eleArray=new ArrayList<>();
            keyChecker= likeResult.get(i).get(0);
            eleArray.add(likeResult.get(i).get(0));
            eleArray.add(likeResult.get(i).get(1));
            for(int j=0;j<key3.size();j++)
            {

                if(key3.get(j)==keyChecker)
                {
                    eleArray.set(1,String.valueOf(count));
                }
                else
                    count++;
            }
            likeResult.set(i,eleArray);
            count=1;
        }

        String tr="bv";
        likeVlues=likeResult;
    }

    public void  click(List<String> results,List<List<String>> distanceVlues,List<List<String>>  priceVlues,List<List<String>> likeVlues,List<Double> Distances,List<String>pricelist,List<String> Titiles,List<String>uris,List<String>discriptions,List<String> discounts,List<String> shopNames)
    {

        List<String> keys=new ArrayList<>();
        List<List<String>> distanceV= new ArrayList<>();
        List<List<String>> priceV= new ArrayList<>();
        List<List<String>>  likeV= new ArrayList<>();
        List<Double> dist=new ArrayList<>();
        List<String> pprice=new ArrayList<>();
        List<String> discount=new ArrayList<>();
        List<String> titel=new ArrayList<>();
        List<String>  uri=new ArrayList<>();
        List<String>  dis=new ArrayList<>();
        List<String>  spName=new ArrayList<>();

        for(int i=0;i<results.size();i++)
        {
            keys.add(results.get(i));
        }

        for(int i=0;i<results.size();i++)
        {
            List<String> temp = new ArrayList<>();
            for(int j=0;j<2;j++)
            {
                if(j==0)
                    temp.add(distanceVlues.get(i).get(0));
                else
                    temp.add(distanceVlues.get(i).get(1));
            }
            distanceV.add(temp);
        }

        for(int i=0;i<results.size();i++)
        {
            List<String> temp = new ArrayList<>();
            for(int j=0;j<2;j++)
            {
                if(j==0)
                    temp.add(priceVlues.get(i).get(0));
                else
                    temp.add(priceVlues.get(i).get(1));
            }
            priceV.add(temp);
        }

        for(int i=0;i<results.size();i++)
        {
            List<String> temp = new ArrayList<>();
            for(int j=0;j<2;j++)
            {
                if(j==0)
                    temp.add(likeVlues.get(i).get(0));
                else
                    temp.add(likeVlues.get(i).get(1));
            }
            likeV.add(temp);
        }

        for(int i=0;i<Distances.size();i++)
        {
            dist.add(Distances.get(i));
        }

        for(int i=0;i<pricelist.size();i++)
        {
            pprice.add(pricelist.get(i));
        }

        for(int i=0;i<discounts.size();i++)
        {
            discount.add(discounts.get(i));
        }

        for(int i=0;i<Titiles.size();i++)
        {
            titel.add(Titiles.get(i));
        }

        for(int i=0;i<uris.size();i++)
        {
            uri.add(uris.get(i));
        }

        for(int i=0;i<discriptions.size();i++)
        {
            dis.add(discriptions.get(i));
        }

        for(int i=0;i<shopNames.size();i++)
        {
            spName.add(shopNames.get(i));
        }

        Double tempDist;
        String tempSt="";
        String tempp="";
        String temti="";
        String tempU="";
        String temd="";
        String tempn="";
        String tempdiscount="";
        String tempspName="";

        List<List<String>> itemKeys= new ArrayList<>();

        for(int i=0;i<keys.size();i++)
        {
            List<String> temp = new ArrayList<>();
            for(int j=0;j<2;j++)
            {
                if(j==0)
                    temp.add(keys.get(i));
                else
                    temp.add("0");
            }
            itemKeys.add(temp);
        }
        String ass="HJ";
        for(int k=0;k<keys.size();k++)
        {

            List<String> eleArray=new ArrayList<>();
            eleArray.add(itemKeys.get(k).get(0));
            eleArray.add(itemKeys.get(k).get(1));
            int count=0;

            if (spinnerPossition==0){
                //no special

                count=((Integer.parseInt(distanceV.get(k).get(1)))*2)+((Integer.parseInt(priceV.get(k).get(1)))*2)+((Integer.parseInt(likeV.get(k).get(1)))*2);
            }  else
            if (spinnerPossition==1)    {
                //time efficiency
                 count=((Integer.parseInt(distanceV.get(k).get(1)))*7)+((Integer.parseInt(priceV.get(k).get(1)))*1)+((Integer.parseInt(likeV.get(k).get(1)))*2);

            }    else if (spinnerPossition==2){
                //price efficiency
                 count=((Integer.parseInt(distanceV.get(k).get(1)))*2)+((Integer.parseInt(priceV.get(k).get(1)))*7)+((Integer.parseInt(likeV.get(k).get(1)))*1);


            }else{
                //no special

                count=((Integer.parseInt(distanceV.get(k).get(1)))*2)+((Integer.parseInt(priceV.get(k).get(1)))*2)+((Integer.parseInt(likeV.get(k).get(1)))*2);
            }








            eleArray.set(1,String.valueOf(count));
            itemKeys.set(k,eleArray);

        }

        String hd="hd";
        List<String> displayKey=new ArrayList<>();
        List<String> Priority=new ArrayList<>();
        List<Integer> intPriority=new ArrayList<>();

        for(int i=0;i<keys.size();i++)
        {
            displayKey.add(itemKeys.get(i).get(0));
        }

        for(int i=0;i<keys.size();i++)
        {
            Priority.add(itemKeys.get(i).get(1));
        }

        for(int i=0;i<keys.size();i++)
        {
            intPriority.add(Integer.parseInt(Priority.get(i)));
        }

        int temp=0;
        String tempK="";
        for(int k=0;k<intPriority.size();k++)
        {
            for(int l=k+1;l<intPriority.size();l++)
            {
                if(intPriority.get(k)>intPriority.get(l)){
                    temp=intPriority.get(k);
                    tempK=displayKey.get(k);
                    tempDist = dist.get(k);
                    tempp=pprice.get(k);
                    temti=titel.get(k);
                    tempU=uri.get(k);
                    temd=dis.get(k);
                    tempdiscount=discount.get(k);
                    tempspName=spName.get(k);
                    // tempn=names1.get(i);

                    intPriority.set(k,intPriority.get(l));
                    displayKey.set(k,displayKey.get(l));
                    dist.set(k,dist.get(l));
                    pprice.set(k,pprice.get(l));
                    titel.set(k,titel.get(l));
                    uri.set(k,uri.get(l));
                    dis.set(k,dis.get(l)) ;
                    discount.set(k,discount.get(l)) ;
                    spName.set(k,spName.get(l));


                    intPriority.set(l,temp);
                    displayKey.set(l,tempK);
                    pprice.set(l,tempp) ;
                    titel.set(l,temti) ;
                    uri.set(l,tempU);
                    dis.set(l,temd)  ;
                    dist.set(l,tempDist);
                    discount.set(l,tempdiscount);
                    spName.set(l,tempspName);


                }
            }
        }

        sortedAuths = displayKey;
        sortedDiscriptios = dis;
        sortedDistance = dist;
        sortedPrices = pprice;
        sortedTitle = titel;
        sortedUris=  uri;
        sortedDiscount =  discount;
        String hdf="bnd";

    }
    public void sortingDistance(List<Double> Distances , List<String> results)
    {
        List<Double> dist=new ArrayList<>();
        List<String> key=new ArrayList<>();
        for(int i=0;i<Distances.size();i++)
        {
            dist.add(Distances.get(i));
        }

        for(int i=0;i<results.size();i++)
        {
            key.add(results.get(i));
        }

        Double tempDist;
        String tempSt="";

        List<List<String>> distanceResult= new ArrayList<>();

        for(int i=0;i<key.size();i++)
        {
            List<String> tp = new ArrayList<>();
            for(int j=0;j<2;j++)
            {
                if(j==0)
                    tp.add(String.valueOf(key.get(i)));
                else
                    tp.add("0");
            }
            distanceResult.add(tp);
        }

        for(int i=0;i<dist.size();i++)
        {
            for(int j=i+1;j<dist.size();j++)
            {
                if (dist.get(i) > dist.get(j)) {
                    tempDist = dist.get(i);
                    tempSt=key.get(i);

                    dist.set(i,dist.get(j));
                    key.set(i,key.get(j));

                    dist.set(j,tempDist);
                    key.set(j,tempSt);

                }
            }
        }

        String keyChecker="0";
        int count=1;

        for(int i=0;i<distanceResult.size();i++)
        {
            List<String> eleArray=new ArrayList<>();
            keyChecker= distanceResult.get(i).get(0);
            eleArray.add(distanceResult.get(i).get(0));
            eleArray.add(distanceResult.get(i).get(1));
            for(int j=0;j<key.size();j++)
            {

                if(key.get(j)==keyChecker)
                {
                    eleArray.set(1,String.valueOf(count));
                }
                else
                    count++;
            }
            distanceResult.set(i,eleArray);
            count=1;
        }

        distanceVlues=distanceResult;



        List<String> strings = new ArrayList<String>();
        for (Double d : sortedDistance) {
            // Apply formatting to the string if necessary
            sortedDistancesStrings.add(d.toString());
        }

    }



}
