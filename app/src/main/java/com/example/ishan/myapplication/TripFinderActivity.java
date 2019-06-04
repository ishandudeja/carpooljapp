package com.example.ishan.myapplication;


import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.RectangularBounds;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;

import java.util.Arrays;

public class TripFinderActivity extends AppCompatActivity {
    private static final int AUTOCOMPLETE_REQUEST_CODE = 23487;
    private PlacesClient placesClient;

    SearchPlaces sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_finder);

        placesClient = Places.createClient(this);


        setupAutocompleteSupportFragment();
        setupAutocompletSupportFregmentDestination();
        setLoading(false);


        Intent lesson42Service = new Intent(this, MyService.class);
        startService(lesson42Service);
        sp = new SearchPlaces();
    }

    private void setupAutocompleteSupportFragment() {
        final AutocompleteSupportFragment autocompleteSupportFragment =
                (AutocompleteSupportFragment)
                        getSupportFragmentManager().findFragmentById(R.id.startPoint_trip_location1);
        autocompleteSupportFragment.setCountry("NZ");
        autocompleteSupportFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG, Place.Field.ADDRESS_COMPONENTS, Place.Field.ADDRESS));
        autocompleteSupportFragment.setOnPlaceSelectedListener(getStartPointListener());


    }

    private void setupAutocompletSupportFregmentDestination() {

        final AutocompleteSupportFragment autocompleteSupportFragment2 =
                (AutocompleteSupportFragment)
                        getSupportFragmentManager().findFragmentById(R.id.destinationPoint_trip_location1);
        autocompleteSupportFragment2.setCountry("NZ");
        autocompleteSupportFragment2.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG, Place.Field.ADDRESS_COMPONENTS, Place.Field.ADDRESS));
        autocompleteSupportFragment2.setOnPlaceSelectedListener(getDestinationPointListener());

    }

    @NonNull
    private PlaceSelectionListener getStartPointListener() {
        return new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                String locality = StringUtil.stringify(place);
                MyPlace startpoint = new MyPlace(place.getLatLng().toString(), place.getName(), place.getAddress(), locality);

                sp.set_startPlace(startpoint);

                Log.i("search", "Place: " + place.getName() + ", " + place.getId() + ", " + place.getAddress() + ", " + place.getAddressComponents());
            }

            @Override
            public void onError(Status status) {
                Log.i("search", status.getStatusMessage());
            }
        };
    }

    @NonNull
    private PlaceSelectionListener getDestinationPointListener() {
        return new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                String locality = StringUtil.stringify(place);

                MyPlace destinationplace = new MyPlace(place.getLatLng().toString(), place.getName(), place.getAddress(), locality);
                sp.set_destinationPlace(destinationplace);
//                responseView.setText(
//                        StringUtil.stringifyAutocompleteWidget(place, false));
                Log.i("search", "Place: " + place.getName() + ", " + place.getId() + ", " + place.getAddress() + ", " + place.getAddressComponents());
            }

            @Override
            public void onError(Status status) {
                Log.i("search", status.getStatusMessage());
            }
        };
    }

    @Nullable
    private RectangularBounds getBounds() {
        return RectangularBounds.newInstance(
                new LatLng(-33.880490, 151.184363),
                new LatLng(-33.858754, 151.229596));
    }

//    private void showErrorAlert(@StringRes int messageResId) {
//        new AlertDialog.Builder(this)
//                .setTitle(R.string.error_alert_title)
//                .setMessage(messageResId)
//                .show();
//    }

    private void setLoading(boolean loading) {
        findViewById(R.id.loading).setVisibility(loading ? View.VISIBLE : View.INVISIBLE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent intent) {
        if (requestCode == AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == AutocompleteActivity.RESULT_OK) {
                Place place = Autocomplete.getPlaceFromIntent(intent);
                //responseView.setText(
                //      StringUtil.stringifyAutocompleteWidget(place, true));
            } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
                Status status = Autocomplete.getStatusFromIntent(intent);

            } else if (resultCode == AutocompleteActivity.RESULT_CANCELED) {
                // The user canceled the operation.
            }
        }

        // Required because this class extends AppCompatActivity which extends FragmentActivity
        // which implements this method to pass onActivityResult calls to child fragments
        // (eg AutocompleteFragment).
        super.onActivityResult(requestCode, resultCode, intent);
    }


//    private AutocompleteActivityMode getMode() {
//        boolean isOverlayMode =
//                ((CheckBox) findViewById(R.id.autocomplete_activity_overlay_mode)).isChecked();
//        return isOverlayMode ? AutocompleteActivityMode.OVERLAY : AutocompleteActivityMode.FULLSCREEN;
//    }


    public void searchTrips(View view) {
        setLoading(true);
        Intent I = new Intent(this, TripsListActivity.class);

        I.putExtra("tripQuery", sp);
        startActivity(I);

        Intent intent = new Intent(this, TheIntentService.class);
        startService(intent);
        setLoading(false);
    }
}

