package com.unn.arduinosensors;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class SensorsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensors);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Sensors");
        setSupportActionBar(toolbar);

        (findViewById(R.id.motionTextView)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SensorsActivity.this, MotionActivity.class);
                startActivity(intent);
            }
        });

        (findViewById(R.id.temperatureTextView)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SensorsActivity.this, TemperatureActivity.class);
                startActivity(intent);
            }
        });
    }
}
