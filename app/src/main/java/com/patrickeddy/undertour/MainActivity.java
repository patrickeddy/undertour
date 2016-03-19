package com.patrickeddy.undertour;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.SaveCallback;
import com.patrickeddy.undertour.adapters.TourAdapter;
import com.patrickeddy.undertour.model.Tour;
import com.patrickeddy.undertour.model.TourLocation;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{

    private static final String APP_KEY = "DBbWOh4TzocBiVGaQ2wcdrdjDCqzsN1pAXHOZxO2";
    private static final String CLIENT_KEY = "01MuLrsIRrBJH6e3PuiMDE62eOP6wg1T87kJkDKe";

    private ListView myTourListView;

    private List<Tour> myTours;
    private TourAdapter myTourAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        parseInit();

        // Fetch all of the tours.
        //TODO: Actually fetch the tours from the server.
        myTours = new ArrayList<>();
        myTours.add(new Tour());
        myTours.add(new Tour());
        myTours.add(new Tour());

        // Set the adapter.
        myTourAdapter = new TourAdapter(this, myTours);
        myTourListView = (ListView) findViewById(R.id.main_tour_list);
        myTourListView.setAdapter(myTourAdapter);

        myTourListView.setOnItemClickListener(this);
    }

    private void parseInit() {
        // Register this app as a Parse app.
        Parse.initialize(getApplicationContext(), APP_KEY, CLIENT_KEY);
        // Register the Parse related classes.
        ParseObject.registerSubclass(TourLocation.class);
        ParseObject.registerSubclass(com.patrickeddy.undertour.model.Tour.class);
    }

    public void addTour(final View theView) {
        addTour();
    }

    private void addTour() {
        //TODO: Launch new tour activity.
        myTours.add(new Tour());
        myTourAdapter.notifyDataSetChanged();
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent newTourIntent = new Intent(this, TourActivity.class);
        startActivity(newTourIntent);
        Toast.makeText(this, "Clicked tour!", Toast.LENGTH_SHORT).show();
    }
}
