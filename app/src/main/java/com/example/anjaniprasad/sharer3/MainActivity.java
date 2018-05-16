package com.example.anjaniprasad.sharer3;

import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    double lat;
    double lon;
    public String addre;
    //EditText edt;
    Firebase url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //edt= findViewById(R.id.edttext);
        Firebase.setAndroidContext(this);
        url = new Firebase("https://sharer3-b9ecc.firebaseio.com/");
    }

    public void loc(View view)
    {
        Intent myIntent = new Intent(MainActivity.this, Sendloc.class);
        startActivity(myIntent);
    }

    public void sees(View view)
    {
        Intent myIntent = new Intent(MainActivity.this, ViewContacts.class);
        startActivity(myIntent);
    }

    public void addr(View view)
    {
        TextView tv = (TextView) findViewById(R.id.txtv);
        addre= GetAddress(lat,lon);
        System.out.println(addre);
        tv.setText(addre);


    }

    public void nxtpage(View view)
    {
        Intent myIntent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(myIntent);
    }

    public void get(View view)
    {
        Intent myIntent1 = new Intent(MainActivity.this, SeeActivity.class);
        startActivity(myIntent1);
    }
    public String GetAddress(double lat, double lon)
    {    String address;
        Geocoder geocoder;
        List<Address> addresses;
        geocoder = new Geocoder(this, Locale.getDefault());
        try {
            addresses = geocoder.getFromLocation(lat, lon, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5

             address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
            String city = addresses.get(0).getLocality();
            String state = addresses.get(0).getAdminArea();
            String country = addresses.get(0).getCountryName();
            String postalCode = addresses.get(0).getPostalCode();
            String knownName = addresses.get(0).getFeatureName(); // Only if available else return NULL

        }
        catch(Exception e)
        {
            address="not found";
        }
        return address;
    }

    public void l(View view)
    {
        LocationManager locationManager =
                (LocationManager) MainActivity.this.getSystemService(Context.LOCATION_SERVICE);
        // Define a listener that responds to location updates
        LocationListener locationListener = new LocationListener() {
            public void onLocationChanged(Location location) {
                // Called when a new location is found by the network location provider.
               lat = location.getLatitude();
                 lon = location.getLongitude();
                TextView tv = (TextView) findViewById(R.id.txtv);
                tv.setText("Your Location is: \n latitude" + lat + " longitude \n:" + lon);
            }

            public void onStatusChanged(String provider, int status, Bundle extras) {}
            public void onProviderEnabled(String provider) {}
            public void onProviderDisabled(String provider) {}
        };

        try {
           Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
        } catch (SecurityException e) {
            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();// lets the user know there is a problem with the gps
        }
        // Register the listener with the Location Manager to receive location updates

    }



    public void dodo(View view) throws UnknownHostException {
        addre= GetAddress(lat,lon);
        String pass="mypass";
        String name="user-entered";
        Date d= new Date();
        //Sring u="user1";
        String date1 = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        Date currentTime = Calendar.getInstance().getTime();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("users").child("user2");
//        url=url+u;
        //DatabaseReference mRef = mDatabase.getReference("copyright");
//        Firebase fbid1=url.child("fbid1");
//        Firebase lat1=fbid1.child("lat");
//        Firebase lon1=fbid1.child("lon");
        myRef.child("phone").setValue("9790650451");
        myRef.child("latitude").setValue(lat);
        myRef.child("longitude").setValue(lon);
        myRef.child("pass").setValue(pass);
        myRef.child("name").setValue(name);
        Details det = new Details();
        det.lat =lat ;
        det.lon = lon ;
        det.phone = "9790650451";
        //det.userid = "AP";
        //det.myid="AP";
        SaveAsyncTask tsk = new SaveAsyncTask();
        tsk.execute(det);

    }


}
