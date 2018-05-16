package com.example.anjaniprasad.sharer3;

import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Arrays;

public class SeeActivity extends AppCompatActivity {
ArrayList<Details1> mydet;
    float[] dist={};

TextView tv1,tv2,tv3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.see);
        tv1=findViewById(R.id.tv1);
        dist = new float[3];
        tv2=findViewById(R.id.tv2);
        tv3=findViewById(R.id.textView);
        mydet = new ArrayList<>();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference().child("");

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
    }

//    public static Double distanceBetween(LatLng point1, LatLng point2) {
//        if (point1 == null || point2 == null) {
//            return null;
//        }
//
//        return SphericalUtil.computeDistanceBetween(point1, point2);
//    }

   public void click(View view)
   {
//       Log.i("phone1",mydet.get(0).phone);
//       Log.i("lat1",Double.toString(mydet.get(0).lat));
//       Log.i("lon1",Double.toString(mydet.get(0).lon));
//
//       Log.i("phone2",mydet.get(1).phone);
//       Log.i("lat2",Double.toString(mydet.get(1).lat));
//       Log.i("lon2",Double.toString(mydet.get(1).lon));
//       System.out.println("phone 1"+mydet.get(0).phone);
//       System.out.println("phone 1"+mydet.get(1).phone);
//       System.out.println("lat1"+mydet.get(0).lat);
//       System.out.println("lon1"+mydet.get(0).lon);
//       System.out.println("lat 2"+mydet.get(1).lat);
//       System.out.println("lon 2"+mydet.get(1).lon);
       for(Details1 det:mydet)
       {
//           System.out.println();
              Log.e("name",det.getName());
           Log.e("lat",Double.toString(det.getLatitude()));
              Log.e("lon",Double.toString(det.getLongititude()));
           Log.e("pass",det.getPass());
           Log.e("phone",det.getPhone());
       }
      distance();
   }

   void distance()
   {

       Location.distanceBetween(12.9728367, 79.1571961, 12.9728367, 79.1571961, dist);
       Log.e("distance", Arrays.toString(dist));

   }
}
