package com.example.anjaniprasad.sharer3;

import java.io.Serializable;

/**
 * Created by ANJANIPRASAD on 3/21/2018.
 */
@SuppressWarnings("serial")
public class Details1 implements Serializable{
    public String pass, name, phone;
    public double latitude, longitude;

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongititude() {
        return longitude;
    }

    public void setLongititude(double longititude) {
        this.longitude = longititude;
    }
}
