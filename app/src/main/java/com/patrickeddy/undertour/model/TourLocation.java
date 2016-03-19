package com.patrickeddy.undertour.model;

import com.parse.ParseClassName;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;

/**
 * Created by patrickeddy on 3/18/16.
 */
@ParseClassName("TourLocation")
public class TourLocation extends ParseObject {
    public String getName() {
        return getString("name");
    }

    public ParseGeoPoint getCoordinate() {
        return getParseGeoPoint("coordinate");
    }

    public void setName(final String theName) {
        put("name", theName);
    }

    public void setCoordinate(final ParseGeoPoint theCoordinate) {
        put("coordinate", theCoordinate);
    }
}
