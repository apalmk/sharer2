package com.example.anjaniprasad.sharer3;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class ListSee extends AppCompatActivity {
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_see);
        db=openOrCreateDatabase("RememberDB", Context.MODE_PRIVATE, null);
        Cursor c=db.rawQuery("SELECT _id,name,phone FROM remember", null);
        ListView lvItems = (ListView) findViewById(R.id.mylist);
// Setup cursor adapter using cursor from last step
        CursorAdapterOwn todoAdapter = new CursorAdapterOwn(this, c);
// Attach cursor adapter to the ListView
        lvItems.setAdapter(todoAdapter);
        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                long entry=  parent.getAdapter().getItemId(position);
//                Justobj obj= (Justobj) parent.getAdapter().getItem(position);
//                Intent intent = new Intent(ListSee.this, Caller.class);
//                String numb = Long.toString(entry);
//                intent.putExtra("obj", numb);
//                startActivity(intent);
                int i= (int)entry;
                String ph;
                Cursor c1=db.rawQuery("SELECT phone FROM remember WHERE _id='"+i+"'", null);
                if(c1.moveToFirst()) {
                    ph = c1.getString(0);

//                    Toast.makeText(getApplicationContext(), ph, Toast.LENGTH_SHORT).show();



                            try {
                                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", ph, null));
                                startActivity(intent);
                            }
                            catch(SecurityException e)
                            {
                                e.printStackTrace();
                            }



                }

            }
        });

    }
}
