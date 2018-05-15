package com.simonyan.arduinosensors.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;

import com.simonyan.arduinosensors.Mqtt.Constants;
import com.simonyan.arduinosensors.Mqtt.MqttMessageService;
import com.simonyan.arduinosensors.Mqtt.PahoMqttClient;
import com.simonyan.arduinosensors.ProgressBarAnimation;
import com.simonyan.arduinosensors.R;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.w3c.dom.Text;

import java.io.UnsupportedEncodingException;

public class HumidityActivity extends AppCompatActivity {
    static int humidityCoef = 2;
    public static int humidity = 0;
    public static TextView tempText;
    public static ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_humidity);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Humidity Sensor");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        tempText = (TextView) findViewById(R.id.tempTextView);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);



        Button waterApply = (Button)findViewById(R.id.waterApply);

        waterApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg = "";
                Switch waterSwitch = (Switch)findViewById(R.id.waterSwitch);
                if(waterSwitch.isChecked()){
                    msg += "1";
                } else {
                    msg += "0";
                }

                Switch autoWaterSwitch = (Switch)findViewById(R.id.autoWaterSwitch);
                if(autoWaterSwitch.isChecked()){
                    msg += "1";
                } else {
                    msg += "0";
                }

                if (!msg.isEmpty()) {
                    try {
                        Constants.pahoMqttClient.publishMessage(Constants.client, msg, 1, Constants.PUBLISH_TOPIC);
                    } catch (MqttException e) {
                        e.printStackTrace();
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }
            }
        });


        Intent intent = new Intent(HumidityActivity.this, MqttMessageService.class);
        startService(intent);


        try {
            Constants.pahoMqttClient.subscribe(Constants.client, Constants.SUBSCRIBE_TOPIC, 1);
        } catch (MqttException e) {
            e.printStackTrace();
        }
/*
      //  int humidity = rand(10, 50);
        initProgressBar(progressBar, humidity);
        tempText.setText(Integer.toString(humidity) + "%");

*/
        Switch waterPlants = (Switch) findViewById(R.id.waterSwitch);
        setWaterPlantsListener(waterPlants);

        Switch autoWaterPlants = (Switch) findViewById(R.id.autoWaterSwitch);
        setAutoWaterListener(autoWaterPlants);

    }

    public static void initProgressBar(ProgressBar progressBar, int temperature) {
        ProgressBarAnimation animation = new ProgressBarAnimation(progressBar, 0, humidityCoef * temperature);
        animation.setDuration(500);
        progressBar.startAnimation(animation);
    }

    private int rand(int min, int max) {
        max -= min;
        return (int) (Math.random() * ++max) + min;
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
}
