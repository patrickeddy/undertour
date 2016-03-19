package com.patrickeddy.undertour;

import android.app.Activity;
import android.os.Bundle;

import com.patrickeddy.undertour.adapters.LocationAdapter;
import com.patrickeddy.undertour.model.Location;

import java.util.List;

/**
 * Created by patrickeddy on 3/19/16.
 */
public class TourActivity extends Activity {

    private List<Location> myLocations;

    private LocationAdapter myLocationAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tour_activity);

        
    }
}
