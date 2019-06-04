package com.example.ishan.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class TripInfoActivity extends AppCompatActivity {
    DBHelper db;
    TextView tripName;
    TextView textStartPlace;
    TextView textDestinationPlace;
    TextView textTripTiming;
    TextView textAvailableSets;
    EditText editAvailableSets;
    Trips trip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_info);
        db = new DBHelper(this, null, null, 1);
        Bundle appleData = getIntent().getExtras();
        if (appleData == null) {
            return;
        }
        int trip_id = (int) appleData.getSerializable("tripInfo");
        trip = db.getTripById(trip_id);
        Log.i("search", "trip id:" + trip_id);
        Log.i("search", trip.get_name());

        tripName = findViewById(R.id.textNameTrip);
        textStartPlace = findViewById(R.id.textStartPlace);
        textDestinationPlace = findViewById(R.id.textDestinationPlace);
        textTripTiming = findViewById(R.id.textTripTiming);
        textAvailableSets = (TextView) findViewById(R.id.textAvailableSets);
        editAvailableSets = findViewById(R.id.editAvailableSets);

        tripName.setText(trip.get_name());
        textStartPlace.setText(trip.get_departureLocality());
        textDestinationPlace.setText(trip.get_arrivalLocality());
        textTripTiming.setText(trip.get_startDate() + " - " + trip.get_startTime());
        // textAvailableSets.setText(trip.get_setAvailable());


    }

    public void bookSet(View view) {
        SharedPreferences loginData = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        int user_id = loginData.getInt("user_id", 0);
        if (user_id > 0) {
            MyTrip mytrip = new MyTrip(user_id, (int) trip.get_id(), false, true, 1);
            db.createMyTrip(mytrip);

            Intent I = new Intent(this, MyTripActivity.class);

            startActivity(I);
        }else
        {
            AlertDialog.Builder a_builder = new AlertDialog.Builder(this);
            a_builder.setMessage("please login first").show();


        }
    }
}
