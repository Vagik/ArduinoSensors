package com.unn.arduinosensors;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;


public class ConnectionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connection);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Add Device");
        setSupportActionBar(toolbar);


        (findViewById(R.id.addDeviceButton)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            // TODO: 12.04.2018  Add device in SQLite
                Intent intent = new Intent(ConnectionActivity.this, SelectionActivity.class);
                startActivity(intent);
            }
        });

    }

}
