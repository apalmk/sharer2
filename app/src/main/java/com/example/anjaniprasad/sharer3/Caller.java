package com.example.anjaniprasad.sharer3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Caller extends AppCompatActivity {
Justobj c1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_caller);
        Intent intenti = getIntent();
        String c1=intenti.getStringExtra("");
    }
}
