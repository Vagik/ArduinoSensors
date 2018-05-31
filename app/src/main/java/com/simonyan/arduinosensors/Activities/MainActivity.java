package com.simonyan.arduinosensors.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.simonyan.arduinosensors.ClickListeners.JumpClickListener;
import com.simonyan.arduinosensors.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        (findViewById(R.id.connectionButton)).setOnClickListener(new JumpClickListener(MainActivity.this, ConnectionActivity.class));
        (findViewById(R.id.selectionButton)).setOnClickListener(new JumpClickListener(MainActivity.this, SelectionActivity.class));

    }

}
