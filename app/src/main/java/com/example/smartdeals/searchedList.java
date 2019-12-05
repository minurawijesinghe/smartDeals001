package com.example.smartdeals;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class searchedList extends AppCompatActivity {


    ListView listView;
    List<String> titleList = new ArrayList<>();
    List<String> discriptionList = new ArrayList<>();
    List<String> imageList = new ArrayList<>();
    List<String> priceList = new ArrayList<>();


    List<String> productSummary = new ArrayList<>();


    @Override
    protected void onRestart() {
        super.onRestart();
        productSummary.clear();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searched_list);

        Intent intent = getIntent();
        titleList = intent.getStringArrayListExtra("titleList");
        imageList = intent.getStringArrayListExtra("imageList");

        discriptionList = intent.getStringArrayListExtra("titleList");
        priceList = intent.getStringArrayListExtra("priceList");




        listView = findViewById(R.id.listView);
        searchedList.MyAdapter adapter = new searchedList.MyAdapter(this, titleList, discriptionList, imageList,priceList);
        listView.setAdapter(adapter);



        // now set item click on list view
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                openProductDiscription(position);
            }
        });
        // so item click is done now check list view
    }

    private void openProductDiscription() {

        Intent intent = new Intent(this,productDiscription.class);
        startActivity(intent);
    }

    class MyAdapter extends ArrayAdapter<String> {

        Context context;
        List<String> rTitle;
        List<String> rDescription;
        List<String> rImgs;
        List<String> rPrices;
        MyAdapter (Context c,List<String> ltitle, List<String> ldescription,List<String> limages,List<String> lprices) {
            super(c, R.layout.row_homepage, R.id.textView1, ltitle);
            this.context = c;
            this.rTitle = ltitle;
            this.rDescription = ldescription;
            this.rImgs = limages;
            this.rPrices = lprices;



        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater)getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = layoutInflater.inflate(R.layout.row_homepage, parent, false);

            ImageView images =row.findViewById(R.id.image);
            TextView myTitle = row.findViewById(R.id.textView1);
            TextView myDescription = row.findViewById(R.id.textView2);
            TextView myPrices = row.findViewById(R.id.textView3);



            Picasso.get().load(rImgs.get(position)).into(images);

            myTitle.setText(rTitle.get(position));
            myDescription.setText(rDescription.get(position));
            myPrices.setText("Rs."+rPrices.get(position)+".00");




            return row;
        }

    }
    public void openProductDiscription(int index){

        productSummary.add(titleList.get(index));
        productSummary.add(discriptionList.get(index));
        productSummary.add(imageList.get(index));
        productSummary.add(priceList.get(index));

        Intent intent = new Intent(this,productDiscription.class);
        intent.putExtra("productSummary",(Serializable) productSummary);
        startActivity(intent);


    }
    
}
