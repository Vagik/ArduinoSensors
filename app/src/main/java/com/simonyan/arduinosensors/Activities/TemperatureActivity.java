package com.simonyan.arduinosensors.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;

import com.simonyan.arduinosensors.ClickListeners.RefreshClickListener;
import com.simonyan.arduinosensors.Mqtt.MqttData;
import com.simonyan.arduinosensors.Mqtt.MqttMessageService;
import com.simonyan.arduinosensors.ProgressBarAnimation;
import com.simonyan.arduinosensors.R;

import org.eclipse.paho.client.mqttv3.MqttException;

public class TemperatureActivity extends BaseActivity {
    final int temperatureCoef = 2;
    Intent intent;

    @Override
    protected String getActivityTitle() {
        return getString(R.string.TemperatureSensor);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temperature);

        intent = new Intent(this, MqttMessageService.class);
        startService(intent);

        TextView tempText = (TextView) findViewById(R.id.tempTextView);
        ProgressBar progressBarTemp = (ProgressBar) findViewById(R.id.tempProgressBar);
        tempText.setOnClickListener(new RefreshClickListener(tempText, progressBarTemp, temperatureCoef, 1));
        tempText.setText(MqttData.tempValue + "°C");
        initProgressBar(progressBarTemp, temperatureCoef, MqttData.tempValue);


        Button waterApply = (Button) findViewById(R.id.tempApply);
        waterApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg = "";
                Switch tempSwitch = (Switch) findViewById(R.id.tempSwitch);
                msg = checkSwitch(tempSwitch, msg);

                Switch autoTempSwitch = (Switch) findViewById(R.id.autoTempSwitch);
                msg = checkSwitch(autoTempSwitch, msg);

                final AlertDialog aboutDialog = new AlertDialog.Builder(
                        TemperatureActivity.this).setMessage("COMING SOON!")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        }).create();

                aboutDialog.show();

            }
        });


        Switch openDoor = (Switch) findViewById(R.id.tempSwitch);
        setOpenDoorListener(openDoor);

        Switch autoOpenDoor = (Switch) findViewById(R.id.autoTempSwitch);
        setAutoDoorListener(autoOpenDoor);

    }

    @Override
    protected void onStart() {
        super.onStart();
        subscribe(MqttData.SUBSCRIBE_TOPIC_TEMP);
    }

    @Override
    protected void onDestroy() {
        stopService(intent);
        super.onDestroy();
    }

    private void subscribe(final String topic) {
        try {
            MqttData.pahoMqttClient.subscribe(MqttData.client, topic, 1);
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
        ProgressBarAnimation animation = new ProgressBarAnimation(progressBar, coef * value);
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
