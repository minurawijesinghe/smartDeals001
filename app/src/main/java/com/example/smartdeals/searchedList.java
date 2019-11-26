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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class searchedList extends AppCompatActivity {


    ListView listView;
    List<String> titleList = new ArrayList<>();
    List<String> discriptionList = new ArrayList<>();
    List<String> imageList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searched_list);

        Intent intent = getIntent();
        titleList = intent.getStringArrayListExtra("titleList");
        imageList = intent.getStringArrayListExtra("imageList");

        discriptionList = intent.getStringArrayListExtra("titleList");



        listView = findViewById(R.id.listView);
        // now create an adapter class
        final ProgressDialog progressDialog7 = new ProgressDialog(this);
        progressDialog7.setTitle("downloading content");
        progressDialog7.show();
        searchedList.MyAdapter adapter = new searchedList.MyAdapter(this, titleList, discriptionList, imageList);
        listView.setAdapter(adapter);

        progressDialog7.dismiss();


        // now set item click on list view
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position ==  0) {
                    Toast.makeText(searchedList.this, "Coca cola Description", Toast.LENGTH_SHORT).show();
                }
                if (position ==  1) {
                    Toast.makeText(searchedList.this, "Automobile Description", Toast.LENGTH_SHORT).show();

                }
                if (position ==  2) {
                    Toast.makeText(searchedList.this, "Digital marcketing Description", Toast.LENGTH_SHORT).show();

                }
                if (position ==  3) {
                    Toast.makeText(searchedList.this, "Electronics Description", Toast.LENGTH_SHORT).show();

                }
                if (position ==  4) {
                    Toast.makeText(searchedList.this, "Facebook marcketing Description", Toast.LENGTH_SHORT).show();

                }
                if (position ==  5) {
                    Toast.makeText(searchedList.this, "Facebook marcketing Description", Toast.LENGTH_SHORT).show();

                }
                if (position ==  6) {
                    Toast.makeText(searchedList.this, "Facebook marcketing Description", Toast.LENGTH_SHORT).show();

                }
            }
        });
        // so item click is done now check list view
    }

    class MyAdapter extends ArrayAdapter<String> {

        Context context;
        List<String> rTitle;
        List<String> rDescription;
        List<String> rImgs;
        MyAdapter (Context c,List<String> ltitle, List<String> ldescription,List<String> limages) {
            super(c, R.layout.row_homepage, R.id.textView1, ltitle);
            this.context = c;
            this.rTitle = ltitle;
            this.rDescription = ldescription;
            this.rImgs = limages;


        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater)getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = layoutInflater.inflate(R.layout.row_homepage, parent, false);

            ImageView images =row.findViewById(R.id.image);
            TextView myTitle = row.findViewById(R.id.textView1);
            TextView myDescription = row.findViewById(R.id.textView2);


            Picasso.with(context).load(rImgs.get(position)).into(images);

            myTitle.setText(rTitle.get(position));
            myDescription.setText(rDescription.get(position));




            return row;
        }

    }
    
}
