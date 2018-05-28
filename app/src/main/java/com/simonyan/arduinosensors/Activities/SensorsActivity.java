package com.simonyan.arduinosensors.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.simonyan.arduinosensors.Device;
import com.simonyan.arduinosensors.Mqtt.MqttData;
import com.simonyan.arduinosensors.Mqtt.MqttMessageService;
import com.simonyan.arduinosensors.Mqtt.PahoMqttClient;
import com.simonyan.arduinosensors.R;

import org.eclipse.paho.client.mqttv3.MqttException;


public class SensorsActivity extends BaseActivity {

    @Override
    protected String getActivityTitle() {
        return getString(R.string.SensorsActivity);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensors);

        Device device = (Device) getIntent().getSerializableExtra("Device");
        MqttData.DEVICE_NAME = device.getName();
        MqttData.MQTT_BROKER_URL += device.getPort();
        MqttData.USERNAME = device.getUserName();
        MqttData.PASSWORD = device.getPassword();
        MqttData.CLIENT_ID = device.getPassword();
        MqttData.pahoMqttClient = new PahoMqttClient();
        MqttData.client = MqttData.pahoMqttClient.getMqttClient(getApplicationContext(), MqttData.MQTT_BROKER_URL, MqttData.CLIENT_ID);

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

        Intent intent = new Intent(this, MqttMessageService.class);
        startService(intent);
    }
}

