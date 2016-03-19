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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseRelation;
import com.parse.SaveCallback;
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

    private TextView myTourTitleTextView;
    private ListView myLocationListView;
    private ProgressBar myProgressBar;


    private List<TourLocation> myTourLocations;
    private LocationAdapter myLocationAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tour_activity);

        // Make the list
        myTourLocations = new ArrayList<>();

        myLocationAdapter = new LocationAdapter(this, myTourLocations);
        myLocationListView = (ListView) findViewById(R.id.tour_location_list);
        myTourTitleTextView = (TextView) findViewById(R.id.tour_title);
        myProgressBar = (ProgressBar) findViewById(R.id.tour_list_progress_bar);

        // Get the tour that we're showing.
        myProgressBar.setVisibility(View.VISIBLE);
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
                    // Set the title
                    myTourTitleTextView.setText(myTour.getTitle());

                    ParseRelation<TourLocation> myTourRelation = myTour.getLocationRelation();
                    myTourRelation.getQuery().findInBackground(new FindCallback<TourLocation>() {
                        @Override
                        public void done(List<TourLocation> locations, ParseException e) {
                            if (e != null) {
                                Log.e("LOCATIONS-FETCH", e.getMessage());
                            }
                            myTourLocations.addAll(locations);
                            myLocationAdapter.notifyDataSetChanged();
                            myProgressBar.setVisibility(View.GONE);
                        }
                    });
                }
            }
        });
        myLocationListView.setAdapter(myLocationAdapter);
        myLocationListView.setOnItemClickListener(this);
    }

    public void likeTour(final View v) {
        likeTour();
    }
    private void likeTour() {
        // Like this tour.
        myTour.like();
        // Save it again.
        myTour.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e != null) {
                    Log.e("LIKE-TOUR", e.getMessage());
                }
                toastLike();
            }
        });

    }

    private void toastLike() {
        Toast.makeText(this, "You liked " + myTour.getTitle(), Toast.LENGTH_SHORT).show();
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
