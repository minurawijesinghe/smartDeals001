package com.example.smartdeals;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class adminVerrification extends AppCompatActivity {
    ListView listView;
    List<String>requestList = new ArrayList<>();
    List<String>latList = new ArrayList<>();
    List<String>lonList = new ArrayList<>();
    List<String>shopNames = new ArrayList<>();

    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_verrification);
        Intent intent = getIntent();
        requestList = intent.getStringArrayListExtra("requestList");
        latList = intent.getStringArrayListExtra("latList");
        lonList = intent.getStringArrayListExtra("lonList");
        shopNames = intent.getStringArrayListExtra("shopNames");

        databaseReference = FirebaseDatabase.getInstance().getReference();

        
        listView = findViewById(R.id.listView);
        adminVerrification.MyAdapter adapter = new adminVerrification.MyAdapter(this, shopNames, requestList, latList,lonList);
        listView.setAdapter(adapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                    openVerifyWindow(position);

            }
        });
        



    }




    class MyAdapter extends ArrayAdapter<String> {

        Context context;
        List<String> rTitle;
        List<String> rDescription;
        List<String> rImgs;
        List<String> rPrices;
        MyAdapter (Context c,List<String> ltitle, List<String> ldescription,List<String> limages,List<String> lprices) {
            super(c, R.layout.row_admin_verification, R.id.textView1, ltitle);
            this.context = c;
            this.rTitle = ltitle;
            this.rDescription = ldescription;



        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater)getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = layoutInflater.inflate(R.layout.row_admin_verification, parent, false);

            TextView myTitle = row.findViewById(R.id.textView1);
            TextView myDescription = row.findViewById(R.id.textView2);





            myTitle.setText(rTitle.get(position)+"- shop Name");
            myDescription.setText(rDescription.get(position)+"- shopID");



            return row;
        }

    }

    public void openVerifyWindow(int index){

        Intent intent = new Intent(this,verifyWindow.class);
        intent.putExtra("requestList",requestList.get(index));
        intent.putExtra("shopNames",shopNames.get(index));
        intent.putExtra("latList",latList.get(index));
        intent.putExtra("lonList",lonList.get(index));
        startActivity(intent);


    }


}
