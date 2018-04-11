package com.unn.arduinosensors;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Arduino Sensors");
        setSupportActionBar(toolbar);

        (findViewById(R.id.connectionButton)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent conAct = new Intent(MainActivity.this, ConnectionActivity.class);
                startActivity(conAct);
            }
        });

        (findViewById(R.id.selectionButton)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent selAct = new Intent(MainActivity.this, SelectionActivity.class);
                startActivity(selAct);
            }
        });


    }
}
