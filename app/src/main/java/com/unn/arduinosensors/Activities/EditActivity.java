package com.unn.arduinosensors.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;

import com.unn.arduinosensors.Device;
import com.unn.arduinosensors.ClickListeners.EditClickListener;
import com.unn.arduinosensors.R;


public class EditActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Edit Device");
        setSupportActionBar(toolbar);

        Device device = (Device) getIntent().getSerializableExtra("Device");
        fillDeviceFields(device);

        (findViewById(R.id.saveDeviceButton)).setOnClickListener(new EditClickListener(device, EditActivity.this, this));
    }


    private void fillDeviceFields(Device device) {
        ((EditText) findViewById(R.id.editName)).setText(device.getName());
        ((EditText) findViewById(R.id.editIP)).setText(device.getIP());
        ((EditText) findViewById(R.id.editPort)).setText(device.getPort());
        ((EditText) findViewById(R.id.editUsername)).setText(device.getUserName());
        ((EditText) findViewById(R.id.editPassword)).setText(device.getPassword());
    }


}
