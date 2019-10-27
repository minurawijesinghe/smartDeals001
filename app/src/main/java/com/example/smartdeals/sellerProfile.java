package com.example.smartdeals;

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
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class sellerProfile extends AppCompatActivity {





        ListView listView;
        Button editItem;

        //setting the values for items inthe list
        String mTitle[] = {"Coca cola", "Red Bull", "suasages", "onnion 1kg", "Hair brush"};
        String mDescription[] = {"Rs 350.00", "Rs 400.00", "Rs 280.00", "Rs 50.00", "Rs 600.00"};
        int images[] = { R.drawable.cocacola, R.drawable.redbull, R.drawable.sausage, R.drawable.onion,R.drawable.hairbrush};
        // so our images and other things are set in array

        // now paste some images in drawable

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_seller_profile);

            listView = findViewById(R.id.listViewSellerPro);
            // now create an adapter class
            editItem = (Button)findViewById(R.id.addItem);

            MyAdapter adapter = new MyAdapter(this, mTitle, mDescription, images);

            listView.setAdapter(adapter);
            // there is my mistake...
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
                    if (position ==  0) {
                        openProductDiscription();

                    }
                    if (position ==  1) {

                        openProductDiscription();

                    }
                    if (position ==  2) {
                        openProductDiscription();

                    }
                    if (position ==  3) {
                        openProductDiscription();


                    }
                    if (position ==  4) {
                        openProductDiscription();


                    }
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
                View row = layoutInflater.inflate(R.layout.row_sellerprofile, parent, false);
                ImageView images = row.findViewById(R.id.image1);
                TextView myTitle = row.findViewById(R.id.textView4);
                TextView myDescription = row.findViewById(R.id.textView5);

                // now set our resources on views
                images.setImageResource(rImgs[position]);
                myTitle.setText(rTitle[position]);
                myDescription.setText(rDescription[position]);




                return row;
            }
        }

        public void openProductDiscription(){
            Intent intent = new Intent(this, productDiscription.class);
            startActivity(intent);
        }
    }

