package com.patrickeddy.undertour;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import com.patrickeddy.undertour.adapters.LocationAdapter;
import com.patrickeddy.undertour.model.TourLocation;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by patrickeddy on 3/19/16.
 */
public class TourActivity extends Activity {

    private ListView myLocationListView;

    private List<TourLocation> myTourLocations;

    private LocationAdapter myLocationAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tour_activity);

        myTourLocations = new ArrayList<>();
        //TODO: Fetch the tour locations from the tour.
        myTourLocations.add(new TourLocation());
        myTourLocations.add(new TourLocation());
        myTourLocations.add(new TourLocation());
        myLocationAdapter = new LocationAdapter(this, myTourLocations);

        myLocationListView = (ListView) findViewById(R.id.tour_location_list);
        myLocationListView.setAdapter(myLocationAdapter);
    }
}
