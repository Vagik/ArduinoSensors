package com.simonyan.arduinosensors.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.simonyan.arduinosensors.Device;
import com.simonyan.arduinosensors.Mqtt.Constants;
import com.simonyan.arduinosensors.Mqtt.PahoMqttClient;
import com.simonyan.arduinosensors.R;

import org.eclipse.paho.client.mqttv3.MqttException;


public class SensorsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensors);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Sensors");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        // TODO: 25.04.2018 Connection to Arduino
        Device device = (Device) getIntent().getSerializableExtra("Device");
        Constants.MQTT_BROKER_URL = "tcp://iot.eclipse.org:" + device.getPort();
        Constants.USERNAME = device.getUserName();
        Constants.PASSWORD = device.getPassword();


        Constants.pahoMqttClient = new PahoMqttClient();
        Constants.client = Constants.pahoMqttClient.getMqttClient(getApplicationContext(), Constants.MQTT_BROKER_URL, Constants.CLIENT_ID);





        (findViewById(R.id.motionTextView)).setOnClickListener(new View.OnClickListener() {
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

}
