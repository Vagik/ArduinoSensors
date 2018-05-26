package com.simonyan.arduinosensors.Activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;

import com.simonyan.arduinosensors.ClickListeners.RefreshClickListener;
import com.simonyan.arduinosensors.Mqtt.StaticData;
import com.simonyan.arduinosensors.ProgressBarAnimation;
import com.simonyan.arduinosensors.R;

import org.eclipse.paho.client.mqttv3.MqttException;

import java.io.UnsupportedEncodingException;

public class HumidityActivity extends BaseActivity {
    final int humidityCoef = 1;

    @Override
    protected String getActivityTitle() {
        return getString(R.string.HumidityActivity);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_humidity);

        TextView humText = (TextView) findViewById(R.id.humTextView);
        ProgressBar progressBarHum = (ProgressBar) findViewById(R.id.humProgressBar);
        humText.setOnClickListener(new RefreshClickListener(humText, progressBarHum, humidityCoef, 0));
        humText.setText(StaticData.humValue + "%");
        initProgressBar(progressBarHum, humidityCoef, StaticData.humValue);

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
            }
        });

        subscribe();


        Switch waterPlants = (Switch) findViewById(R.id.waterSwitch);
        setWaterPlantsListener(waterPlants);

        Switch autoWaterPlants = (Switch) findViewById(R.id.autoWaterSwitch);
        setAutoWaterListener(autoWaterPlants);
    }

    private void publish(String message) {
        try {
            StaticData.pahoMqttClient.publishMessage(StaticData.client, message, 1, StaticData.PUBLISH_TOPIC_HUM);
        } catch (MqttException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    private void subscribe() {
        try {
            StaticData.pahoMqttClient.subscribe(StaticData.client, StaticData.SUBSCRIBE_TOPIC_HUM, 1);
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

    public void initProgressBar(ProgressBar progressBar, int coef, int value) {
        ProgressBarAnimation animation = new ProgressBarAnimation(progressBar, 0, coef * value);
        animation.setDuration(500);
        progressBar.startAnimation(animation);
    }

    public String checkSwitch(Switch view, String message) {
        if (view.isChecked()) {
            message += "1";
        } else {
            message += "0";
        }
        return message;
    }
}
