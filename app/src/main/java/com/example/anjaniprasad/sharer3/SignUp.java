package com.example.anjaniprasad.sharer3;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class SignUp extends AppCompatActivity {
    ArrayList<Details1> mydet;
    double lon,lat;
EditText name1,phone,pass1,pass2;
int t=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        mydet = new ArrayList<>();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();
       name1= (EditText)findViewById(R.id.name);
       phone=(EditText)findViewById(R.id.phonenum);
       pass1= (EditText)findViewById(R.id.pass);
        pass2= (EditText)findViewById(R.id.repeatpass);
        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

//                Details pres = dataSnapshot.getValue(Details.class);
//                mydet.add(pres);
                for(DataSnapshot all:dataSnapshot.getChildren())
                {
                    mydet.add(all.getValue(Details1.class));
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        LocationManager locationManager =
                (LocationManager) SignUp.this.getSystemService(Context.LOCATION_SERVICE);
        // Define a listener that responds to location updates
        LocationListener locationListener = new LocationListener() {
            public void onLocationChanged(Location location) {
                // Called when a new location is found by the network location provider.
                lat = location.getLatitude();
                lon = location.getLongitude();

            }

            public void onStatusChanged(String provider, int status, Bundle extras) {}
            public void onProviderEnabled(String provider) {}
            public void onProviderDisabled(String provider) {}
        };

        try {
            Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
        } catch (SecurityException e) {
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();// lets the user know there is a problem with the gps
        }
        // Register the listener with the Location Manager to receive location updates
    }



    public void log(View view)
    {
        Intent myIntent1 = new Intent(SignUp.this, LoginActivity.class);
        startActivity(myIntent1);
    }





    public void dododo(View view)
    {
        String name= name1.getText().toString();
        String phonenumber= phone.getText().toString();
        String password= pass1.getText().toString();
        String repeatpassword= pass2.getText().toString();

        for(Details1 de:mydet)
        {
            if(de.getPhone().equalsIgnoreCase(phonenumber))
            {
                t=1;
                Toast.makeText(getApplicationContext(),"User already exists!",Toast.LENGTH_SHORT).show();
                break;
            }
        }

        if(t!=1)
        {
            if(password.equals(repeatpassword))
            {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("users").child(name);
//                l1();
                myRef.child("phone").setValue(phonenumber);
                myRef.child("latitude").setValue(lat);
                myRef.child("longitude").setValue(lon);
                myRef.child("pass").setValue(password);
                myRef.child("name").setValue(name);

                try {
                    Thread.sleep(3000);
                }
                catch (Exception e)
                {
                    Log.e("Exception",e.toString());
                }
                Intent myIntent1 = new Intent(SignUp.this, LoginActivity.class);
                startActivity(myIntent1);
            }

            else
            {
                Toast.makeText(getApplicationContext(),"Both the passwords are not matching!",Toast.LENGTH_SHORT).show();
            }
        }
    }
}
