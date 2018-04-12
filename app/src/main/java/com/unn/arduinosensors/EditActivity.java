package com.unn.arduinosensors;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


public class EditActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Edit Device");
        setSupportActionBar(toolbar);

        Bundle arguments = getIntent().getExtras();
        Device device = (Device) arguments.getSerializable(Device.class.getSimpleName());
        fillDeviceFields(device);


        (findViewById(R.id.saveDeviceButton)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: 12.04.2018 Save changes in SQLite
                Intent intent = new Intent(EditActivity.this, SelectionActivity.class);
                startActivity(intent);
            }
        });
    }

    private void fillDeviceFields(Device device) {
        ((EditText) findViewById(R.id.editName)).setText(device.Name);
        ((EditText) findViewById(R.id.editIP)).setText(device.IP);
        ((EditText) findViewById(R.id.editPort)).setText(device.Port);
        ((EditText) findViewById(R.id.editUsername)).setText(device.UserName);
        ((EditText) findViewById(R.id.editPassword)).setText(device.Password);
    }
}
