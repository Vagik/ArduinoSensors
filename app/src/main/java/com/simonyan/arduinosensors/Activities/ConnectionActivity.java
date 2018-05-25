package com.simonyan.arduinosensors.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.simonyan.arduinosensors.Device;
import com.simonyan.arduinosensors.R;

import java.util.HashSet;
import java.util.Set;


public class ConnectionActivity extends BaseActivity {

    @Override
    protected String getActivityTitle() {
        return getString(R.string.NewConnection);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connection);


        (findViewById(R.id.addDeviceButton)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Device device = initDevice();

                SharedPreferences preferences = getSharedPreferences("ArduinoSensors", Context.MODE_PRIVATE);
                Set<String> setDevices = preferences.getStringSet("Devices", new HashSet<String>());

                GsonBuilder gsonBuilder = new GsonBuilder();
                Gson gson = gsonBuilder.create();
                setDevices.add(gson.toJson(device));

                SharedPreferences.Editor editor = preferences.edit();
                editor.clear();
                editor.putStringSet("Devices", setDevices);
                editor.apply();

                Intent intent = new Intent(ConnectionActivity.this, SelectionActivity.class);
                startActivity(intent);
            }
        });

    }

    private Device initDevice(){
        String name = ((EditText) findViewById(R.id.connectName)).getText().toString();
        String IP = ((EditText) findViewById(R.id.connectIP)).getText().toString();
        String Port = ((EditText) findViewById(R.id.connectPort)).getText().toString();
        String Username = ((EditText) findViewById(R.id.connectUsername)).getText().toString();
        String Password = ((EditText) findViewById(R.id.connectPassword)).getText().toString();
        return new Device(name, IP, Port, Username, Password);
    }

}
