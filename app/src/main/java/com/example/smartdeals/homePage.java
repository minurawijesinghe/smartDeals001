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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class homePage extends AppCompatActivity {

    ListView listView;
    String mTitle[] = {"Coca cola", "Auto mobile parts", "Digital marcketing", "Electronis", "Facebook","Potatoes"};
    String mDescription[] = {"Stand a chance to win 2 air tickets to melbourn", "10% discount on any automobile part", "Looking for a chance to promote your business on discount", "discounts on electronics up to 25%", "facebook marckeitng for free for two first two members apply","20% discount for potatoes "};
    int images[] = { R.drawable.cocacola, R.drawable.engine, R.drawable.marcketing, R.drawable.innovation,R.drawable.facebook,R.drawable.potato };
    // so our images and other things are set in array

    // now paste some images in drawable

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        listView = findViewById(R.id.listView);
        // now create an adapter class

        MyAdapter adapter = new MyAdapter(this, mTitle, mDescription, images);
        listView.setAdapter(adapter);
        // there is my mistake...
        // now again check this..

        // now set item click on list view
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position ==  0) {
                    Toast.makeText(homePage.this, "Coca coala Description", Toast.LENGTH_SHORT).show();
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
    
}
