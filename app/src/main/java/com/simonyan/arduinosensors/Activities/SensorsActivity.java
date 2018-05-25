package com.simonyan.arduinosensors.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.simonyan.arduinosensors.Device;
import com.simonyan.arduinosensors.Mqtt.MqttMessageService;
import com.simonyan.arduinosensors.Mqtt.PahoMqttClient;
import com.simonyan.arduinosensors.Mqtt.StaticData;
import com.simonyan.arduinosensors.R;


public class SensorsActivity extends BaseActivity {

    @Override
    protected String getActivityTitle() {
        return getString(R.string.SensorsActivity);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensors);


        // TODO: 25.04.2018 Connection to Arduino
        Device device = (Device) getIntent().getSerializableExtra("Device");
        StaticData.MQTT_BROKER_URL = "tcp://iot.eclipse.org:" + device.getPort();
        StaticData.USERNAME = device.getUserName();
        StaticData.PASSWORD = device.getPassword();
        StaticData.CLIENT_ID = device.getPassword();

        StaticData.pahoMqttClient = new PahoMqttClient();
        StaticData.client = StaticData.pahoMqttClient.getMqttClient(getApplicationContext(), StaticData.MQTT_BROKER_URL, StaticData.CLIENT_ID);

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
