package com.simonyan.arduinosensors.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.simonyan.arduinosensors.Animation.ProgressBarAnimation;
import com.simonyan.arduinosensors.Animation.TextViewAnimation;
import com.simonyan.arduinosensors.ClickListeners.RefreshClickListener;
import com.simonyan.arduinosensors.Device;
import com.simonyan.arduinosensors.Mqtt.MqttData;
import com.simonyan.arduinosensors.Mqtt.MqttMessageService;
import com.simonyan.arduinosensors.Mqtt.PahoMqttClient;
import com.simonyan.arduinosensors.R;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.MqttException;

import java.io.UnsupportedEncodingException;

public class HumidityActivity extends BaseActivity {
    final int humidityCoef = 1;
    final int qos = 1;
    Intent intent;

    @Override
    protected String getActivityTitle() {
        return getString(R.string.HumidityActivity);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_humidity);

        intent = new Intent(this, MqttMessageService.class);
        startService(intent);

        TextView humText = (TextView) findViewById(R.id.humTextView);
        ProgressBar progressBarHum = (ProgressBar) findViewById(R.id.humProgressBar);
        humText.setOnClickListener(new RefreshClickListener(humText, progressBarHum, humidityCoef, 0));
        initTextView(humText, MqttData.humValue);
        initProgressBar(progressBarHum, humidityCoef, MqttData.humValue);

        Button waterApply = (Button) findViewById(R.id.waterApply);
        waterApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg = "";
                Switch waterSwitch = (Switch) findViewById(R.id.waterSwitch);
                msg = checkSwitch(waterSwitch, msg);

                Switch autoWaterSwitch = (Switch) findViewById(R.id.autoWaterSwitch);
                msg = checkSwitch(autoWaterSwitch, msg);

                publish(msg);
                saveSwitchSettings(msg);
            }
        });

        Switch waterPlants = (Switch) findViewById(R.id.waterSwitch);
        setWaterPlantsListener(waterPlants);

        Switch autoWaterPlants = (Switch) findViewById(R.id.autoWaterSwitch);
        setAutoWaterListener(autoWaterPlants);

        loadSwitchSettings(waterPlants, autoWaterPlants);
    }


    @Override
    protected void onStart() {
        super.onStart();
        subscribe(MqttData.SUBSCRIBE_TOPIC_HUM);
        subscribe(MqttData.SUBSCRIBE_TOPIC_WATERING);
    }

    @Override
    protected void onDestroy() {
        stopService(intent);
        super.onDestroy();
    }

    private void publish(String message) {
        try {
            MqttData.pahoMqttClient.publishMessage(MqttData.client, message, qos, MqttData.PUBLISH_TOPIC_HUM);
        } catch (MqttException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    private void subscribe(final String topic) {
        try {
            MqttData.pahoMqttClient.subscribe(MqttData.client, topic, qos);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    private void setWaterPlantsListener(Switch waterPlants) {
        waterPlants.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    ((Switch) findViewById(R.id.autoWaterSwitch)).setChecked(false);
                }
            }
        });
    }

    private void setAutoWaterListener(Switch autoWaterPlants) {
        autoWaterPlants.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    ((Switch) findViewById(R.id.waterSwitch)).setChecked(false);
                }
            }
        });
    }

    private void initTextView(TextView textView, int value) {
        TextViewAnimation animation = new TextViewAnimation(textView, value, "%");
        animation.setDuration(500);
        textView.startAnimation(animation);
    }

    private void initProgressBar(ProgressBar progressBar, int coef, int value) {
        ProgressBarAnimation animation = new ProgressBarAnimation(progressBar, coef * value);
        animation.setDuration(500);
        progressBar.startAnimation(animation);
    }

    public String checkSwitch(Switch sw, String message) {
        if (sw.isChecked()) {
            message += "1";
        } else {
            message += "0";
        }
        return message;
    }

    private void saveSwitchSettings(String message) {
        SharedPreferences preferences = getSharedPreferences("HumiditySettings", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.putString("Settings", message);
        editor.apply();
    }

    private void loadSwitchSettings(Switch waterPlants, Switch autoWaterPlants) {
        SharedPreferences preferences = getSharedPreferences("HumiditySettings", Context.MODE_PRIVATE);
        String message = preferences.getString("Settings", "00");
        switch (message) {
            case "00":
                waterPlants.setChecked(false);
                autoWaterPlants.setChecked(false);
                break;
            case "10":
                waterPlants.setChecked(true);
                break;
            case "01":
                autoWaterPlants.setChecked(true);
                break;
        }
    }

}
