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

public class TemperatureActivity extends BaseActivity {
    final int temperatureCoef = 2;

    @Override
    protected String getActivityTitle() {
        return getString(R.string.TemperatureSensor);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temperature);

        TextView tempText = (TextView) findViewById(R.id.tempTextView);
        ProgressBar progressBarTemp = (ProgressBar) findViewById(R.id.tempProgressBar);

        tempText.setOnClickListener(new RefreshClickListener(tempText, progressBarTemp, temperatureCoef, 1));

        tempText.setText(StaticData.tempValue + "°C");
        initProgressBar(progressBarTemp, temperatureCoef, StaticData.tempValue);


        Button waterApply = (Button) findViewById(R.id.tempApply);
        waterApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg = "";
                Switch tempSwitch = (Switch) findViewById(R.id.tempSwitch);
                msg = checkSwitch(tempSwitch, msg);

                Switch autoTempSwitch = (Switch) findViewById(R.id.autoTempSwitch);
                msg = checkSwitch(autoTempSwitch, msg);

                // TODO: 23.05.2018 Message "COMING SOON"
            }
        });

        subscribe();


        Switch openDoor = (Switch) findViewById(R.id.tempSwitch);
        setOpenDoorListener(openDoor);

        Switch autoOpenDoor = (Switch) findViewById(R.id.autoTempSwitch);
        setAutoDoorListener(autoOpenDoor);

    }


    private void subscribe() {
        try {
            StaticData.pahoMqttClient.subscribe(StaticData.client, StaticData.SUBSCRIBE_TOPIC_TEMP, 1);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }


    private void setOpenDoorListener(Switch openDoor) {
        openDoor.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    ((Switch) findViewById(R.id.autoTempSwitch)).setChecked(false);
                }
            }
        });
    }

    private void setAutoDoorListener(Switch autoOpenDoor) {
        autoOpenDoor.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    ((Switch) findViewById(R.id.tempSwitch)).setChecked(false);
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
