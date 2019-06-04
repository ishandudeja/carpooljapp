package com.example.ishan.myapplication;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import android.widget.Toast;
import com.google.android.gms.common.api.Status;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.libraries.places.api.Places;

import com.google.android.libraries.places.api.model.Place;

import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

import java.util.Arrays;
import java.util.List;


import com.google.android.gms.tasks.Task;

import com.google.android.libraries.places.api.net.FindCurrentPlaceRequest;
import com.google.android.libraries.places.api.net.FindCurrentPlaceResponse;


import android.content.pm.PackageManager;

public class CreateTripActivity extends AppCompatActivity {
    private static final int AUTOCOMPLETE_REQUEST_CODE = 23487;
    private PlacesClient placesClient;
    SearchPlaces sp;
    DBHelper db;

    EditText txtName;
    EditText txtdescription;
    EditText txtDate;
    EditText txtTime;
    EditText txtsets;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_trip);

        txtName = (EditText) findViewById(R.id.txtName);
        txtdescription = (EditText) findViewById(R.id.txtdescription);
        txtDate = (EditText) findViewById(R.id.txtDate);
        txtTime = (EditText) findViewById(R.id.txtTime);
        txtsets = (EditText) findViewById(R.id.txtsets);

        placesClient = Places.createClient(this);
        setupAutocompleteStartPoint();
        setupAutocompletDestination();
        sp = new SearchPlaces();
        db = new DBHelper(this, null, null, 1);
        Intent lesson42Service = new Intent(this, MyService.class);
        startService(lesson42Service);
    }

    private void setupAutocompleteStartPoint() {
        final AutocompleteSupportFragment autocompleteSupportFragment =
                (AutocompleteSupportFragment)
                        getSupportFragmentManager().findFragmentById(R.id.start_trip_location);
        autocompleteSupportFragment.setCountry("NZ");
        autocompleteSupportFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG, Place.Field.ADDRESS_COMPONENTS, Place.Field.ADDRESS));
        autocompleteSupportFragment.setOnPlaceSelectedListener(getPlaceStartPointistener());

    }

    private PlaceSelectionListener getPlaceStartPointistener() {
        return new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                String locality = StringUtil.stringify(place);
                MyPlace startPoint = new MyPlace(place.getLatLng().toString(), place.getName(), place.getAddress(), locality);
                sp.set_startPlace(startPoint);
                String title = place.getName() + " to ";

                if (sp.get_destinationPlace() != null) {
                    title += sp.get_destinationPlace().get_placeName();
                }

                txtName.setText(title);

                Log.i("search", "Place: " + place.getName() + ",| " + place.getLatLng() + ", |" + place.getAddress() + ", |" + locality);
                Log.i("search", "Place: " + startPoint.get_placeName() + ",| " + startPoint.get_laglong() + ", |" + startPoint.get_address() + ", |" + startPoint.get_locality());

            }

            @Override
            public void onError(Status status) {

                Log.i("search", status.getStatusMessage());

            }
        };
    }

    private void setupAutocompletDestination() {
        final AutocompleteSupportFragment autocompleteSupportFragment =
                (AutocompleteSupportFragment)
                        getSupportFragmentManager().findFragmentById(R.id.destination_trip_location);
        autocompleteSupportFragment.setCountry("NZ");
        autocompleteSupportFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG, Place.Field.ADDRESS_COMPONENTS, Place.Field.ADDRESS));
        autocompleteSupportFragment.setOnPlaceSelectedListener(getPlaceDetinationPointlistener());
    }

    private PlaceSelectionListener getPlaceDetinationPointlistener() {
        return new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                String locality = StringUtil.stringify(place);
                MyPlace destination = new MyPlace(place.getLatLng().toString(), place.getName(), place.getAddress(), locality);
                sp.set_destinationPlace(destination);

                String title = "Your location to ";

                if (sp.get_startPlace() != null) {
                    title = sp.get_startPlace().get_placeName() + " to " + place.getName();

                } else {
                    title += place.getName();
                }
                txtName.setText(title);
                Log.i("search", "Place: " + place.getName() + ", " + place.getId() + ", " + place.getAddress() + ", " + place.getAddressComponents());
            }

            @Override
            public void onError(Status status) {

                Log.i("search", status.getStatusMessage());

            }
        };
    }

    public void saveTrip(View view) {

        SharedPreferences loginData = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        int user_id = loginData.getInt("user_id", 0);
        if (user_id > 0) {


            Trips trip = new Trips();
            trip.set_name(txtName.getText().toString());
            trip.set_vehicleDescription(txtdescription.getText().toString());
            trip.set_setAvailable(2);
            trip.set_startDate(txtDate.getText().toString());
            trip.set_startTime(txtTime.getText().toString());
            trip.set_duration("0");
            trip.set_distance("0");
            trip.set_departurePoint(sp.get_startPlace().get_placeName());
            trip.set_departureLatLong(sp.get_startPlace().get_laglong());
            trip.set_departureAddress(sp.get_startPlace().get_address());
            trip.set_departureLocality(sp.get_startPlace().get_locality());
            trip.set_arrivalPoint(sp.get_destinationPlace().get_placeName());
            trip.set_arrivalLatLong(sp.get_destinationPlace().get_laglong());
            trip.set_arrivalAddress(sp.get_destinationPlace().get_address());
            trip.set_arrivalLocality(sp.get_destinationPlace().get_locality());
            trip.set_instructions("Happy Trip");
            long id = db.createTrips(trip);
            MyTrip mytrip = new MyTrip(user_id, (int) id, true, true, 1);
            long trip_id = db.createMyTrip(mytrip);

            Intent I = new Intent(this, TripInfoActivity.class);

            I.putExtra("tripInfo", trip_id);
            startActivity(I);
//            Intent intent = new Intent(this, TheIntentService.class);
//            startService(intent);


        } else {
            AlertDialog.Builder a_builder = new AlertDialog.Builder(this);
            a_builder.setMessage("please login first").show();
        }
    }


    public void findCurrentPlace(View view) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_WIFI_STATE)
                != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(
                    this,
                    "Both ACCESS_WIFI_STATE & ACCESS_FINE_LOCATION permissions are required",
                    Toast.LENGTH_SHORT)
                    .show();
        }

        // Note that it is not possible to request a normal (non-dangerous) permission from
        // ActivityCompat.requestPermissions(), which is why the checkPermission() only checks if
        // ACCESS_FINE_LOCATION is granted. It is still possible to check whether a normal permission
        // is granted or not using ContextCompat.checkSelfPermission().
        if (checkPermission(ACCESS_FINE_LOCATION)) {
            findCurrentPlaceWithPermissions();
        }
    }

    private void findCurrentPlaceWithPermissions() {

        List<Place.Field> placeFields = Arrays.asList(Place.Field.NAME);
        FindCurrentPlaceRequest currentPlaceRequest =
                FindCurrentPlaceRequest.newInstance(placeFields);
        if (ContextCompat.checkSelfPermission(this, ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            Task<FindCurrentPlaceResponse> currentPlaceTask =
                    placesClient.findCurrentPlace(currentPlaceRequest);

            currentPlaceTask.addOnSuccessListener(new OnSuccessListener<FindCurrentPlaceResponse>() {
                @Override
                public void onSuccess(FindCurrentPlaceResponse response) {

                    MyPlace myPlace = new MyPlace();
                    myPlace.set_placeName(response.getPlaceLikelihoods().get(3).getPlace().getName());
                    myPlace.set_address(response.getPlaceLikelihoods().get(5).getPlace().getName());
                    myPlace.set_locality(response.getPlaceLikelihoods().get(3).getPlace().getName() + " " + response.getPlaceLikelihoods().get(6).getPlace().getName() + " " + response.getPlaceLikelihoods().get(9).getPlace().getName());
                    myPlace.set_laglong("000000");

                    sp.set_startPlace(myPlace);

                    Log.i("search", response.getPlaceLikelihoods().get(3).getPlace().getName());
                    Log.i("search", response.getPlaceLikelihoods().get(5).getPlace().getName());
                    Log.i("search", response.getPlaceLikelihoods().get(6).getPlace().getName());
                    Log.i("search", response.getPlaceLikelihoods().get(9).getPlace().getName());


                }
            });


            currentPlaceTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    // Task failed with an exception
                    // ...
                }
            });

            currentPlaceTask.addOnCompleteListener(new OnCompleteListener<FindCurrentPlaceResponse>() {
                @Override
                public void onComplete(@NonNull Task<FindCurrentPlaceResponse> task) {
                    if (task.isSuccessful()) {
                        // Task completed successfully
                        //  AuthResult result = task.getResult();
                    } else {
                        // Task failed with an exception
                        Exception exception = task.getException();
                    }
                }
            });
        }
    }

    private boolean checkPermission(String permission) {
        boolean hasPermission =
                ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED;
        if (!hasPermission) {
            ActivityCompat.requestPermissions(this, new String[]{permission}, 0);
        }
        return hasPermission;
    }

}
