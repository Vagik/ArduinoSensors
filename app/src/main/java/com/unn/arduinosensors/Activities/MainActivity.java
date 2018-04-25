package com.unn.arduinosensors.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.unn.arduinosensors.R;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Arduino Sensors");
        setSupportActionBar(toolbar);

        setButtonIntent((Button) (findViewById(R.id.connectionButton)), MainActivity.this, ConnectionActivity.class);
        setButtonIntent((Button) (findViewById(R.id.selectionButton)), MainActivity.this, SelectionActivity.class);

    }

    private void setButtonIntent(Button button, final Context from, final Class to) {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent conAct = new Intent(from, to);
                startActivity(conAct);
            }
        });
    }
}
