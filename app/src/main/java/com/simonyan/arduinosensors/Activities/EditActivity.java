package com.simonyan.arduinosensors.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.simonyan.arduinosensors.ClickListeners.EditClickListener;
import com.simonyan.arduinosensors.Device;
import com.simonyan.arduinosensors.R;


public class EditActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Edit Device");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        Device device = (Device) getIntent().getSerializableExtra("Device");
        fillDeviceFields(device);
        Button button = (Button) findViewById(R.id.saveDeviceButton);
        button.setOnClickListener(new EditClickListener(device, EditActivity.this, this));
    }

    private void fillDeviceFields(Device device) {
        ((EditText) findViewById(R.id.editName)).setText(device.getName());
        ((EditText) findViewById(R.id.editIP)).setText(device.getIP());
        ((EditText) findViewById(R.id.editPort)).setText(device.getPort());
        ((EditText) findViewById(R.id.editUsername)).setText(device.getUserName());
        ((EditText) findViewById(R.id.editPassword)).setText(device.getPassword());
    }


}
