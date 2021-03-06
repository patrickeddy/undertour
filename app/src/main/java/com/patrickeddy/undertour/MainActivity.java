package com.patrickeddy.undertour;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;
import com.patrickeddy.undertour.adapters.TourAdapter;
import com.patrickeddy.undertour.model.Tour;
import com.patrickeddy.undertour.model.TourLocation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private static final String APP_KEY = "DBbWOh4TzocBiVGaQ2wcdrdjDCqzsN1pAXHOZxO2";
    private static final String CLIENT_KEY = "01MuLrsIRrBJH6e3PuiMDE62eOP6wg1T87kJkDKe";

    private ListView myTourListView;
    private ProgressBar myProgressBar;

    private List<Tour> myTours;
    private TourAdapter myTourAdapter;

    @Override
    protected void onStart() {
        super.onStart();
        if (myTours != null) {
            fetchTours();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        parseInit();

        // Fetch all of the tours.
        //TODO: Actually fetch the tours from the server.
        myTours = new ArrayList<>();

        // Set the adapter.
        myTourAdapter = new TourAdapter(this, myTours);
        myTourListView = (ListView) findViewById(R.id.main_tour_list);
        myProgressBar = (ProgressBar) findViewById(R.id.main_list_progress_bar);
        myTourListView.setAdapter(myTourAdapter);

        myTourListView.setOnItemClickListener(this);
    }

    private void fetchTours() {
        myTours.clear();
        myProgressBar.setVisibility(View.VISIBLE);
        // Fetch the tours
        ParseQuery<Tour> tourQuery = ParseQuery.getQuery("Tour");
        tourQuery.findInBackground(new FindCallback<Tour>() {
            @Override
            public void done(List<Tour> tours, ParseException e) {
                if (e != null) {
                    Log.e("TOUR-FETCH", e.getMessage());
                }
                myTours.addAll(tours);
                myTourAdapter.notifyDataSetChanged();
                myProgressBar.setVisibility(View.GONE);
            }
        });
    }

    private void parseInit() {
        // Register this app as a Parse app.
        Parse.initialize(getApplicationContext(), APP_KEY, CLIENT_KEY);
        // Register the Parse related classes.
        ParseObject.registerSubclass(TourLocation.class);
        ParseObject.registerSubclass(com.patrickeddy.undertour.model.Tour.class);

        //FIXME: Remove this when actually using app
        //Supply the DB with static data.
//        fakeDatafy();
    }

    private void fakeDatafy() {
        Tour testTour = new Tour();
        testTour.setTitle("New Comer To Seattle");
        final Tour theFinalTour = testTour;
        testTour.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e != null) {
                    Log.e("SAMPLE-DATA", e.getMessage());
                } else {
                    try {
                        TourLocation l0 = new TourLocation();
                        TourLocation l1 = new TourLocation();
                        TourLocation l2 = new TourLocation();
                        TourLocation l3 = new TourLocation();
                        l0.setName("Kubota Garden");
                        l0.setCoordinate(new ParseGeoPoint(47.513084, -122.266741));
                        l0.save();
                        l1.setName("Westlake Park");
                        l1.setCoordinate(new ParseGeoPoint(47.611091, -122.337059));
                        l1.save();
                        l2.setName("Victor Steinbrueck Park");
                        l2.setCoordinate(new ParseGeoPoint(47.609977, -122.343691));
                        l2.save();
                        l3.setName("Kerry Park");
                        l3.setCoordinate(new ParseGeoPoint(47.629489, -122.359730));
                        l3.save();
                        addTourRelations(theFinalTour, Arrays.asList(l0, l1, l2, l3));
                    } catch (ParseException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });

        Tour testTour2 = new Tour();
        testTour2.setTitle("Seattle Water Tour");
        final Tour theFinalTour2 = testTour2;
        testTour.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e != null) {
                    Log.e("SAMPLE-DATA", e.getMessage());
                } else {
                    try {
                        TourLocation l0 = new TourLocation();
                        TourLocation l1 = new TourLocation();
                        TourLocation l2 = new TourLocation();
                        TourLocation l3 = new TourLocation();
                        l0.setName("Alki beach park");
                        l0.setCoordinate(new ParseGeoPoint(47.57908, 122.41143));
                        l0.save();
                        l1.setName("Gas Works Park");
                        l1.setCoordinate(new ParseGeoPoint(47.64563, -122.33435));
                        l1.save();
                        l2.setName("Green lake park");
                        l2.setCoordinate(new ParseGeoPoint(47.68157, -122.33435));
                        l2.save();
                        l3.setName("Discovery park");
                        l3.setCoordinate(new ParseGeoPoint(47.65919, -122.42510));
                        l3.save();
                        addTourRelations(theFinalTour2, Arrays.asList(l0, l1, l2, l3));
                    } catch (ParseException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });

        Tour testTour3 = new Tour();
        testTour3.setTitle("Seattle Kids Parks Tour");
        final Tour theFinalTour3 = testTour3;
        testTour.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e != null) {
                    Log.e("SAMPLE-DATA", e.getMessage());
                } else {
                    try {
                        TourLocation l0 = new TourLocation();
                        TourLocation l1 = new TourLocation();
                        TourLocation l2 = new TourLocation();
                        TourLocation l3 = new TourLocation();
                        l0.setName("Ballard commons park");
                        l0.setCoordinate(new ParseGeoPoint(47.67064, -122.3855));
                        l0.save();
                        l1.setName("Beacon Hill Playground");
                        l1.setCoordinate(new ParseGeoPoint(47.58626, -122.31721));
                        l1.save();
                        l2.setName("Bitter Lake Playfield");
                        l2.setCoordinate(new ParseGeoPoint(47.72426, -122.34879));
                        l2.save();
                        l3.setName("Lake Ridge park");
                        l3.setCoordinate(new ParseGeoPoint(47.49544, -122.34681));
                        l3.save();
                        addTourRelations(theFinalTour3, Arrays.asList(l0, l1, l2, l3));
                    } catch (ParseException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });
    }

    private void addTourRelations(final Tour theTour, final List<TourLocation> theLocations) throws ParseException {
        for (final TourLocation l : theLocations) {
            theTour.addLocation(l);
        }
        theTour.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e != null) {
                    Log.e("SAMPLE-DATA", e.getMessage());
                } else {
                    Log.d("SAMPLE-DATA", "Sample data uploaded.");
                }
            }
        });

    }

    public void createTour(final View theView) {
        createTour();
    }

    private void createTour() {
        Intent newTourIntent = new Intent(this, EditTourActivity.class);
        startActivity(newTourIntent);
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent newTourIntent = new Intent(this, TourActivity.class);
        newTourIntent.putExtra("tourObjectId", myTours.get(position).getObjectId());
        startActivity(newTourIntent);
    }
}
