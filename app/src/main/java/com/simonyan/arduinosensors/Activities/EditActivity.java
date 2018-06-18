package com.simonyan.arduinosensors.Activities;

import android.os.Bundle;
import android.widget.EditText;

import com.simonyan.arduinosensors.ClickListeners.EditClickListener;
import com.simonyan.arduinosensors.Device;
import com.simonyan.arduinosensors.R;


public class EditActivity extends BaseActivity {

    @Override
    protected String getActivityTitle() {
        return getString(R.string.EditDevice);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        Device device = (Device) getIntent().getSerializableExtra("Device");
        fillDeviceFields(device);
        (findViewById(R.id.saveDeviceButton)).setOnClickListener(new EditClickListener(device,  this));
    }

    private void fillDeviceFields(Device device) {
        ((EditText) findViewById(R.id.editName)).setText(device.getName());
        ((EditText) findViewById(R.id.editClientID)).setText(device.getClientID());
        ((EditText) findViewById(R.id.editPort)).setText(device.getPort());
        ((EditText) findViewById(R.id.editUsername)).setText(device.getUserName());
        ((EditText) findViewById(R.id.editPassword)).setText(device.getPassword());
    }


}
