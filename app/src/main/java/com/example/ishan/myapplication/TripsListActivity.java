package com.example.ishan.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import java.util.ArrayList;

public class TripsListActivity extends AppCompatActivity {
    DBHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trips_list);

        Bundle appleData = getIntent().getExtras();
        if (appleData== null){
            return;
        }
        db = new DBHelper(this, null, null, 1);
        SearchPlaces tripQuery = (SearchPlaces) appleData.getSerializable("tripQuery");
        Intent lesson42Service = new Intent(this, MyService.class);
        startService(lesson42Service);

        ArrayList<Trips> trips=    db.getTrips(tripQuery);


        ListAdapter customListAdapter = new CustomAdapter(this,trips);// Pass the food arrary to the constructor.
        ListView customListView = (ListView) findViewById(R.id.tripListView);
        customListView.setAdapter(customListAdapter);

        customListView.setOnItemClickListener(
                new AdapterView.OnItemClickListener(){
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        Trips trip = (Trips) parent.getItemAtPosition(position);

                        Intent I = new Intent(TripsListActivity.this, TripInfoActivity.class);

                        I.putExtra("tripInfo", trip.get_id());
                        startActivity(I);

                        Toast.makeText(TripsListActivity.this, trip.get_name(), Toast.LENGTH_LONG).show();
                    }
                }
        );



    }
}
