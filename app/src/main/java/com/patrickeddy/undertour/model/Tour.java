package com.patrickeddy.undertour.model;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseRelation;

/**
 * Created by patrickeddy on 3/18/16.
 */
@ParseClassName("Tour")
public class Tour extends ParseObject {

    public String getTitle() {
        return getString("title");
    }

    public ParseRelation getLocationRelation() {
        return getRelation("locations");
    }

    public int getLikes() {
        return getInt("likes");
    }

    public void setTitle(final String theTitle) {
        put("title", theTitle);
    }

    public void addLocation(final TourLocation theLocation) {
        getLocationRelation().add(theLocation);
    }

    public void removeLocation(final TourLocation theLocation) {
        getLocationRelation().remove(theLocation);
    }

    public void like() {
        put("likes", (getLikes() + 1));
    }
}
