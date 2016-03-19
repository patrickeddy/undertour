package com.patrickeddy.undertour;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.FocusFinder;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseRelation;
import com.patrickeddy.undertour.adapters.LocationAdapter;
import com.patrickeddy.undertour.model.Tour;
import com.patrickeddy.undertour.model.TourLocation;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by patrickeddy on 3/19/16.
 */
public class TourActivity extends Activity implements AdapterView.OnItemClickListener {

    private Tour myTour;

    private ListView myLocationListView;


    private List<TourLocation> myTourLocations;
    private LocationAdapter myLocationAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tour_activity);

        // Make the list
        myTourLocations = new ArrayList<>();

        // Get the tour that we're showing.
        String tourObjectId = getIntent().getStringExtra("tourObjectId");
        ParseQuery<Tour> tourQuery = ParseQuery.getQuery("Tour");
        tourQuery.whereEqualTo("objectId", tourObjectId);
        tourQuery.getFirstInBackground(new GetCallback<Tour>() {
            @Override
            public void done(Tour theTour, ParseException e) {
                if (e != null) {
                    Log.e("TOUR-FETCH", e.getMessage());
                }
                myTour = theTour;
                if (myTour != null) {
                    ParseRelation<TourLocation> myTourRelation = myTour.getLocationRelation();
                    myTourRelation.getQuery().findInBackground(new FindCallback<TourLocation>() {
                        @Override
                        public void done(List<TourLocation> locations, ParseException e) {
                            if (e != null) {
                                Log.e("LOCATIONS-FETCH", e.getMessage());
                            }
                            myTourLocations.addAll(locations);
                            myLocationAdapter.notifyDataSetChanged();
                        }
                    });
                }
            }
        });


        myLocationAdapter = new LocationAdapter(this, myTourLocations);
        myLocationListView = (ListView) findViewById(R.id.tour_location_list);
        myLocationListView.setAdapter(myLocationAdapter);
        myLocationListView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        // Kick the user off to Google maps to the location.
        TourLocation location = myTourLocations.get(position);
        if (location.getCoordinate() != null) {
            String baseQuery = "geo:";
            String lat = String.valueOf(location.getCoordinate().getLatitude());
            String lo = String.valueOf(location.getCoordinate().getLongitude());
            String name = location.getName();
            Uri mapsUri = Uri.parse(baseQuery + lat + ", " + lo + "?q=" + name);
            Intent mapsIntent = new Intent(Intent.ACTION_VIEW, mapsUri);
            if (mapsIntent.resolveActivity(getPackageManager()) != null) {
                startActivity(mapsIntent);
            }
        }
    }
}
