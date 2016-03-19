package com.patrickeddy.undertour;

import android.content.Intent;
import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.Parse;
import com.parse.ParseObject;
import com.patrickeddy.undertour.adapters.TourAdapter;
import com.patrickeddy.undertour.model.Tour;

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
        Parse.initialize(this, APP_KEY, CLIENT_KEY);
        // Register the Parse related classes.
        ParseObject.registerSubclass(com.patrickeddy.undertour.model.Location.class);
        ParseObject.registerSubclass(com.patrickeddy.undertour.model.Tour.class);

        ParseObject testObject = ParseObject.create("Location");
        testObject.put("name", "Seattle");
        testObject.saveInBackground();
        Log.d("INIT", "Parse is initialized.");
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
