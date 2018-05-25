package com.simonyan.arduinosensors.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.simonyan.arduinosensors.Device;
import com.simonyan.arduinosensors.DevicesAdapter;
import com.simonyan.arduinosensors.R;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class SelectionActivity extends BaseActivity {

    @Override
    protected String getActivityTitle() {
        return getString(R.string.SelectDevice);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(SelectionActivity.this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selection);

        ArrayList<Device> devices = getAllDevices();

        fillRecyclerView(devices);
    }

    void fillRecyclerView(ArrayList<Device> devices) {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.devicesRecycleView);
        DevicesAdapter devicesAdapter = new DevicesAdapter(devices, SelectionActivity.this);
        recyclerView.setAdapter(devicesAdapter);
    }

    ArrayList<Device> getAllDevices() {
        ArrayList<Device> devices = new ArrayList<>();
        SharedPreferences preferences = getSharedPreferences("ArduinoSensors", Context.MODE_PRIVATE);
        Set<String> setDevices = preferences.getStringSet("Devices", new HashSet<String>());

        for (String dev : setDevices) {
            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.create();
            Device gsonDevice = gson.fromJson(dev, Device.class);
            devices.add(gsonDevice);
        }
        return devices;
    }

}
