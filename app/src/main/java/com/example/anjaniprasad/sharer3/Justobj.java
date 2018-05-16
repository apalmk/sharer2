package com.example.anjaniprasad.sharer3;

import java.io.Serializable;

/**
 * Created by ANJANIPRASAD on 3/23/2018.
 */

@SuppressWarnings("serial")
public class Justobj implements Serializable
{
public String name,phone;

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
}
