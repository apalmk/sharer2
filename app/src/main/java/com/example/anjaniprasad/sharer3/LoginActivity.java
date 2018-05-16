package com.example.anjaniprasad.sharer3;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {
    ArrayList<Details1> mydet;
   EditText phn,pass;
   int t;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
       phn= (EditText)findViewById(R.id.phoneno);
       pass=(EditText)findViewById(R.id.input_password);
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


    public void signin(View view)
    {
        String no=phn.getText().toString();
        String passw=pass.getText().toString();
        for(Details1 det:mydet)
        {
           if((det.getPhone()).equalsIgnoreCase(no))
           {
               if(passw.equals(det.getPass()))
               {   t=1;
                   Intent myIntent1 = new Intent(LoginActivity.this, PageOne.class);
                   myIntent1.putExtra("object",det);
                   startActivity(myIntent1);
               }

               else
               {   t=1;
                   Toast.makeText(getApplicationContext(),"Your password is incorrect try again!",Toast.LENGTH_SHORT).show();

               }
           }
           else
           {

               continue;
           }
        }

        if(t!=1)
        {
            Toast.makeText(getApplicationContext(),"No such user exists!",Toast.LENGTH_SHORT).show();

        }

    }

    public void signup(View view)
    {
        Intent myIntent1 = new Intent(LoginActivity.this, SignUp.class);
        startActivity(myIntent1);
    }
}
