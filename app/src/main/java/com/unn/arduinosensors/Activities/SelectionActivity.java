package com.unn.arduinosensors.Activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import com.google.gson.Gson;

import com.google.gson.GsonBuilder;
import com.unn.arduinosensors.Device;
import com.unn.arduinosensors.DevicesAdapter;
import com.unn.arduinosensors.R;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class SelectionActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selection);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Select Device");
        setSupportActionBar(toolbar);

        getIntent().removeExtra("Device");


        SharedPreferences preferences = getSharedPreferences("Storage",Context.MODE_PRIVATE);
        Set<String> setDevices = preferences.getStringSet("Devices", new HashSet<String>());
        ArrayList<Device> devices = new ArrayList<>();

        for(String dev : setDevices){
            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.create();
            Device gsonDevice = gson.fromJson(dev, Device.class);
            devices.add(gsonDevice);
        }

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.devicesRecycleView);
        DevicesAdapter devicesAdapter = new DevicesAdapter(devices, SelectionActivity.this);
        recyclerView.setAdapter(devicesAdapter);

    }
}
