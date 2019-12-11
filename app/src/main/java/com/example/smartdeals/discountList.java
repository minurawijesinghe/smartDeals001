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

public class discountList extends AppCompatActivity {


    List<String> discountNameList = new ArrayList<>();
    List<String> discountList = new ArrayList<>();
    List<String> discountImagelist = new ArrayList<>();
    List<String>discountPriceList =  new ArrayList<>();
    List<String>discounttitleList =  new ArrayList<>();
    List<String>discountSellersList =  new ArrayList<>();
    List<String>discountDiscriptionList =  new ArrayList<>();
    List<String> productSummary = new ArrayList<>();



    ListView listView;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discount_list);



        Intent intent = getIntent();
        discountList =  intent.getStringArrayListExtra("discountList");
        discountImagelist=intent.getStringArrayListExtra("discountImageList");
        discounttitleList = intent.getStringArrayListExtra("discountTitleList");
        discountPriceList = intent.getStringArrayListExtra("discountPriceList") ;
        discountDiscriptionList = intent.getStringArrayListExtra("discountDiscriptionList") ;
        discountSellersList = intent.getStringArrayListExtra("discountSellerList") ;
        discountNameList = intent.getStringArrayListExtra("discountNameList");


        listView = findViewById(R.id.listView);
        discountList.MyAdapter adapter = new discountList.MyAdapter(this, discounttitleList, discountDiscriptionList, discountImagelist,discountPriceList,discountList);
        listView.setAdapter(adapter);



        // now set item click on list view
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                    openProductDiscription(position);

            }
        });

    }





    class MyAdapter extends ArrayAdapter<String> {

        Context context;
        List<String> rTitle;
        List<String> rDescription;
        List<String> rImgs;
        List<String> rPrices;
        List<String> rDiscount;
        MyAdapter (Context c,List<String> ltitle, List<String> ldescription,List<String> limages,List<String> lprices,List<String>lDiscount) {
            super(c, R.layout.row_discount_list, R.id.textView1, ltitle);
            this.context = c;
            this.rTitle = ltitle;
            this.rDescription = ldescription;
            this.rImgs = limages;
            this.rPrices = lprices;
            this.rDiscount=lDiscount;



        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater)getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = layoutInflater.inflate(R.layout.row_discount_list, parent, false);

            ImageView images =row.findViewById(R.id.image);
            TextView myTitle = row.findViewById(R.id.textView1);
            TextView myDescription = row.findViewById(R.id.textView2);
            TextView myPrices = row.findViewById(R.id.textView3);
            TextView myDiscount = row.findViewById(R.id.textView4);



            Picasso.get().load(rImgs.get(position)).into(images);

            myTitle.setText(rTitle.get(position));
            myDescription.setText(rDescription.get(position));
            myPrices.setText("Rs."+rPrices.get(position)+".00");
            myDiscount.setText(rDiscount.get(position)+" % OFF");




            return row;
        }

    }
    public void openProductDiscription(int index){

        productSummary.add(discounttitleList.get(index));
        productSummary.add(discountDiscriptionList.get(index));
        productSummary.add(discountImagelist.get(index));
        productSummary.add(discountPriceList.get(index));
        productSummary.add(discountSellersList.get(index));
        productSummary.add(discountNameList.get(index));


        Intent intent = new Intent(this,productDiscription.class);
        intent.putExtra("productSummary",(Serializable) productSummary);

        startActivity(intent);


    }
}
