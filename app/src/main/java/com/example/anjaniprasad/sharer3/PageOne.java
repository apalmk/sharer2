package com.example.anjaniprasad.sharer3;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import io.nlopez.smartlocation.OnLocationUpdatedListener;
import io.nlopez.smartlocation.SmartLocation;
import io.nlopez.smartlocation.location.config.LocationAccuracy;
import io.nlopez.smartlocation.location.config.LocationParams;

public class PageOne extends AppCompatActivity {
    Context context = this;
    float[] dist={};
    Details1 det1;
    SQLiteDatabase db;
    ArrayList<Details1> mydet;
    PopupWindow mpopup;
    double lat,lon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_one);
        db=openOrCreateDatabase("RememberDB", Context.MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS remember(_id integer primary key autoincrement,name VARCHAR,address VARCHAR,date VARCHAR,phone VARCHAR,time VARCHAR);");
        dist = new float[3];
        mydet = new ArrayList<>();
        Intent i = getIntent();
        det1 = (Details1) i.getSerializableExtra("object");
        Handler handler = new Handler();
        startLocationListener();
    }

    public void startownLocationListener()
    {
        LocationManager locationManager =
                (LocationManager) PageOne.this.getSystemService(Context.LOCATION_SERVICE);
        // Define a listener that responds to location updates
        LocationListener locationListener = new LocationListener() {
            public void onLocationChanged(Location location) {
                // Called when a new location is found by the network location provider.
                lat = location.getLatitude();
                lon = location.getLongitude();
            }

            public void onStatusChanged(String provider, int status, Bundle extras) {
            }

            public void onProviderEnabled(String provider) {
            }

            public void onProviderDisabled(String provider) {
            }
        };
        try {
            Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
        } catch (SecurityException e) {
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();// lets the user know there is a problem with the gps
        }
    }

    public void upda(View view)
    {
//        startownLocationListener();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("users").child(det1.name);
        DatabaseReference myRef1 = database.getReference("");

        myRef.child("phone").setValue(det1.getPhone());
        myRef.child("latitude").setValue(56.1304);
        myRef.child("longitude").setValue(106.3468);
        myRef.child("pass").setValue(det1.getPass());
        myRef.child("name").setValue(det1.getName());
//        myRef1.push().setValue("Updated");
        Toast.makeText(getApplicationContext(),"Distance updated",Toast.LENGTH_SHORT).show();

    }
    private void startLocationListener() {

        long mLocTrackingInterval = 1000 *5;
        float trackingDistance = 15;
        LocationAccuracy trackingAccuracy = LocationAccuracy.HIGH;

        LocationParams.Builder builder = new LocationParams.Builder()
                .setAccuracy(trackingAccuracy)
                .setDistance(trackingDistance)
                .setInterval(mLocTrackingInterval);

        SmartLocation.with(this)
                .location()
                .config(LocationParams.BEST_EFFORT)
                .continuous()
                .config(builder.build())
                .start(new OnLocationUpdatedListener() {
                    @Override
                    public void onLocationUpdated(Location location) {
                        //Onlocation update code
                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        DatabaseReference myRef = database.getReference("users").child(det1.name);
//
                        myRef.child("phone").setValue(det1.getPhone());
                        myRef.child("latitude").setValue(location.getLatitude());
                        myRef.child("longitude").setValue(location.getLongitude());
                        myRef.child("pass").setValue(det1.getPass());
                        myRef.child("name").setValue(det1.getName());
                        Toast.makeText(getApplicationContext(),"Distance updated",Toast.LENGTH_SHORT).show();
                    }
                });
    }


   public void seedb()
   {
       FirebaseDatabase database = FirebaseDatabase.getInstance();
       DatabaseReference myRef = database.getReference();

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

               for(DataSnapshot all:dataSnapshot.getChildren())
               {
                   mydet.add(all.getValue(Details1.class));
               }
           }

           @Override
           public void onChildRemoved(DataSnapshot dataSnapshot) {

               for(DataSnapshot all:dataSnapshot.getChildren())
               {
                   mydet.add(all.getValue(Details1.class));
               }

           }

           @Override
           public void onChildMoved(DataSnapshot dataSnapshot, String s) {

               for(DataSnapshot all:dataSnapshot.getChildren())
               {
                   mydet.add(all.getValue(Details1.class));
               }

           }

           @Override
           public void onCancelled(DatabaseError databaseError) {

           }
       });
   }
    public void check(View view)
    {
         seedb();
        for(Details1 det:mydet)
        {
          if(det.getPhone().equalsIgnoreCase(det1.getPhone()))
          {

          }
          else
          {
              float dis=distance(det.getLatitude(),det.getLongititude(),det1.getLatitude(),det1.getLongititude());
//              Log.e("distance between",Float.toString(dis));
//              Log.e("the name",det.getName());
              if(dis<=500.00)
              {
                  checkremember(det,det1);
                  break;
              }

          }
        }

    }

    public void checkremember(Details1 det,Details1 det1)
    {      String name11,address11,date11,phone11,time11;
        Cursor c=db.rawQuery("SELECT name FROM remember WHERE phone='"+det.getPhone()+"'", null);
        if(c.getCount()==0)
        {
            askpopup(det);

        }

        else {
            Cursor c1=db.rawQuery("SELECT name,address,date,phone,time FROM remember WHERE phone='"+det.getPhone()+"'", null);

            if(c1.moveToFirst())
            {
                name11=c1.getString(0);
                address11=c1.getString(1);
                date11=c1.getString(2);
                phone11=c1.getString(3);
                time11=c1.getString(4);
                remindpopup(name11,address11,date11,phone11,time11);
            }

        }

    }

    public void askpopup(final Details1 det)
    {


        View popUpView = getLayoutInflater().inflate(R.layout.ask,
                null); // inflating popup layout
        mpopup = new PopupWindow(popUpView, ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT, true);
//        mpopup.setHeight(1);
//        mpopup.setWidth(1);// Creation of popup
        mpopup.setAnimationStyle(android.R.style.Animation_Dialog);
        mpopup.showAtLocation(popUpView, Gravity.CENTER, 0, 0); // Displaying popup
        TextView some = (TextView) popUpView.findViewById(R.id.txtv2);
        String s1="Do you want to remember "+det.getName();
        some.setText(s1);
        Button btnCancel = (Button) popUpView.findViewById(R.id.canc1);
        Button addDB = (Button) popUpView.findViewById(R.id.rem1);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mpopup.dismiss();
            }
        });

        addDB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mpopup.dismiss();
                addintoDB(det);
            }
        });

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

    public void addintoDB(Details1 det)
    {
        String addre= GetAddress(det.getLatitude(),det.getLongititude());
        String date1 = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        Date currentTime = Calendar.getInstance().getTime();
        String timen= DateFormat.getTimeInstance().format(new Date());
        db.execSQL("INSERT INTO remember(name,address,date,phone,time) VALUES('"+det.getName()+"','"+
                addre+"','"+date1+"','"+det.getPhone()+"','"+timen+"');");
        Toast.makeText(getApplicationContext(),"Added to contacts",Toast.LENGTH_SHORT).show();


    }

    public void remindpopup(String n,String a, String d,String p,String t)
    {
        View popUpView = getLayoutInflater().inflate(R.layout.remind,
                null); // inflating popup layout
        mpopup = new PopupWindow(popUpView, ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT, true);
//        mpopup.setHeight(1);
//        mpopup.setWidth(1);// Creation of popup
        mpopup.setAnimationStyle(android.R.style.Animation_Dialog);
        mpopup.showAtLocation(popUpView, Gravity.CENTER, 0, 0); // Displaying popup
        TextView some = (TextView) popUpView.findViewById(R.id.txtv1);
        String s1="You met "+n+" at "+a+" on "+d+" at "+t;
        some.setText(s1);
        Button btnCancel = (Button) popUpView.findViewById(R.id.canc);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mpopup.dismiss();
            }
        });
    }

    public float distance(double x1, double y1, double x2, double y2)
    {
        Location.distanceBetween(x1, y1, x2, y2, dist);
        return dist[0];
    }

    public void oldlist(View view)
    {
        Intent myIntent1 = new Intent(PageOne.this, ListSee.class);
        startActivity(myIntent1);
    }
    public void logout(View view)
    {
        SmartLocation.with(context).location().stop();
        Intent myIntent1 = new Intent(PageOne.this, LoginActivity.class);
        startActivity(myIntent1);
    }
}
