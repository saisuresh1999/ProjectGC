package com.android.projectgc;

import com.google.firebase.firestore.GeoPoint;

public class discover {
    String area;
    Double currentDepth;
    Double initialDepth;
    GeoPoint location;
    Double perc;

    public discover() {
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public Double getCurrentDepth() {
        return currentDepth;
    }

    public void setCurrentDepth(Double currentDepth) {
        this.currentDepth = currentDepth;
    }

    public Double getInitialDepth() {
        return initialDepth;
    }

    public void setInitialDepth(Double initialDepth) {
        this.initialDepth = initialDepth;
    }

    public GeoPoint getLocation() {
        return location;
    }

    public void setLocation(GeoPoint location) {
        this.location = location;
    }

    public Double getPerc() {
        return perc;
    }

    public void setPerc(Double perc) {
        this.perc = perc;
    }

    public discover(String area, Double currentDepth, Double initialDepth, GeoPoint location, Double perc) {
        this.area = area;
        this.currentDepth = currentDepth;
        this.initialDepth = initialDepth;
        this.location = location;
        this.perc = perc;
    }
}
