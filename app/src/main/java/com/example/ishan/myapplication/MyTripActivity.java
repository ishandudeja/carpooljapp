package com.example.ishan.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MyTripActivity extends AppCompatActivity {
    DBHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_trip);
        db = new DBHelper(this, null, null, 1);
        SharedPreferences loginData = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        int user_id = loginData.getInt("user_id", 0);
        if (user_id > 0) {
            ArrayList<Trips> trips = db.myTripsById(user_id);


            ListAdapter customListAdapter = new CustomAdapter(this, trips);// Pass the food arrary to the constructor.
            ListView customListView = (ListView) findViewById(R.id.mytripListView);
            customListView.setAdapter(customListAdapter);

            customListView.setOnItemClickListener(
                    new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                            Trips trip = (Trips) parent.getItemAtPosition(position);

                            Intent I = new Intent(MyTripActivity.this, TripInfoActivity.class);

                            I.putExtra("tripInfo", trip.get_id());
                            startActivity(I);

                            Toast.makeText(MyTripActivity.this, trip.get_name(), Toast.LENGTH_LONG).show();
                        }
                    }
            );
        }
    }

    public void createTip(View view){
        Intent intent=new Intent(this,CreateTripActivity.class);
        startActivity(intent);


    }
}
