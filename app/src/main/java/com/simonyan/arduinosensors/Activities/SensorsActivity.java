package com.simonyan.arduinosensors.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.simonyan.arduinosensors.Device;
import com.simonyan.arduinosensors.Mqtt.MqttData;
import com.simonyan.arduinosensors.Mqtt.PahoMqttClient;
import com.simonyan.arduinosensors.R;

import org.eclipse.paho.android.service.MqttAndroidClient;

import java.util.HashSet;
import java.util.Set;


public class SensorsActivity extends BaseActivity {

    @Override
    protected String getActivityTitle() {
        return getString(R.string.SensorsActivity);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensors);

        fillMqttData();

        (findViewById(R.id.humidityTextView)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SensorsActivity.this, HumidityActivity.class);
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

    private void fillMqttData() {
        Device device = (Device) getIntent().getSerializableExtra("Device");
        SharedPreferences preferences = getSharedPreferences("SelectedDevice", Context.MODE_PRIVATE);

        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();
        String selectedDevice = gson.toJson(device);

        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.putString("Device", selectedDevice);
        editor.apply();

        MqttData.pahoMqttClient = new PahoMqttClient(device);
        MqttData.client = MqttData.pahoMqttClient.getMqttClient(getApplicationContext(),
                MqttData.MQTT_BROKER_URL + device.getPort(), device.getClientID());

    }
}

