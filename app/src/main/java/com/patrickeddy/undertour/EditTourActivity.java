package com.patrickeddy.undertour;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;


import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.parse.ParseException;
import com.parse.ParseGeoPoint;
import com.parse.ParseRelation;
import com.parse.SaveCallback;
import com.patrickeddy.undertour.adapters.EditTourAdapter;
import com.patrickeddy.undertour.model.Tour;
import com.patrickeddy.undertour.model.TourLocation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ryan on 3/19/16.
 */
public class EditTourActivity extends Activity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener{

    private EditText tourNameEditText;

    /**
     * New tour object.
     */
    private Tour myTour;

    /**
     * The view.
     */
    private ListView myLocationListView;

    /**
     * Edit tour adapter.
     */
    private EditTourAdapter myEditTourAdapter;

    /**
     *
     */
    private List<TourLocation> myLocations;

    /**
     * The current location being stored.
     */
    private Location myCurrentLocation;

    /**
     * The google api client for retrieving location.
     */
    private GoogleApiClient mGoogleApiClient;

    /**
     * Starts the Api Client
     */
    protected void onStart() {
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }
        mGoogleApiClient.connect();
        super.onStart();
    }

    protected void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_tour);

        // Create the new tour.
        myTour = new Tour();
        myLocations = new ArrayList<>();

        myLocationListView = (ListView) findViewById(R.id.edit_tour_list);
        tourNameEditText = (EditText) findViewById(R.id.edit_tour_name);

        myEditTourAdapter = new EditTourAdapter(this, myLocations);
        myLocationListView.setAdapter(myEditTourAdapter);
    }



    private ParseGeoPoint getCurrentLocation() {
        // Get the current location
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return null;
        }
        myCurrentLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

        return new ParseGeoPoint(myCurrentLocation.getLatitude(), myCurrentLocation.getLongitude());
    }

    public void addNewLocation(final View v){
        addNewLocation();
    }

    public void createTour(final View v){
        createTour();
    }

    private void addNewLocation() {
        ParseGeoPoint currentLocation = getCurrentLocation();
        String currentLocationName = "";

        TourLocation newLocation = new TourLocation();

        Geocoder geoCoder = new Geocoder(this);
        try {
            List<Address> matches = geoCoder.getFromLocation(currentLocation.getLatitude(), currentLocation.getLongitude(), 1);
            Address bestMatch = (matches.isEmpty() ? null: matches.get(0));
            currentLocationName = bestMatch.getFeatureName();
        } catch (IOException e) {
            e.printStackTrace();
        }

        newLocation.setName(currentLocationName);
        newLocation.setCoordinate(currentLocation);

        myLocations.add(newLocation);
        myEditTourAdapter.notifyDataSetChanged();
    }

    private void createTour() {
        Tour newTour = new Tour();
        // Add all of the locations to the tour.
        ParseRelation<TourLocation> tourRelation = newTour.getLocationRelation();
        for (final TourLocation l: myLocations) {
            try {
                l.save();
                tourRelation.add(l);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        // Set the title of the tour based on the input text.
        newTour.setTitle(tourNameEditText.getText().toString());
        // Save the tour.
        newTour.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e != null) {
                    Log.e("NEW-TOUR-SAVE", e.getMessage());
                }
                myEditTourAdapter.notifyDataSetChanged();
                // Close the activity.
                finish();
            }
        });
    }


    @Override
    public void onConnected(Bundle bundle) {
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Toast.makeText(this, "Couldn't find the current location.", Toast.LENGTH_SHORT).show();
    }
}
