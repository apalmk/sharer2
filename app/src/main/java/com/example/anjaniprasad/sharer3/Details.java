package com.example.anjaniprasad.sharer3;

/**
 * Created by ANJANIPRASAD on 2/23/2018.
 */

public class Details {
    public String fbid;
    public String doc_id;

    public String getDoc_id() {
        return doc_id;
    }

    public void setDoc_id(String doc_id) {
        this.doc_id = doc_id;
    }

    public double lat,lon;

    public String getFbid() {
        return fbid;
    }


    public double getLat() {
        return lat;
    }

    public double getLon() {
        return lon;
    }

    public void setFbid(String fbid) {
        this.fbid = fbid;
    }


    public void setLat(double lat) {
        this.lat = lat;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }
}
