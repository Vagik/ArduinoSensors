package com.simonyan.arduinosensors.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;

import com.simonyan.arduinosensors.ProgressBarAnimation;
import com.simonyan.arduinosensors.R;

public class TemperatureActivity extends AppCompatActivity {
    private static final int tempCoef = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temperature);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Temperature Sensor");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        int temperature = rand(10, 50);
        initProgressBar((ProgressBar) findViewById(R.id.progressBar), temperature);
        ((TextView) findViewById(R.id.tempTextView)).setText(Integer.toString(temperature) + "°C");


        Switch openDoor = (Switch) findViewById(R.id.openSwitch);
        setOpenDoorListener(openDoor);

        Switch autoOpenDoor = (Switch) findViewById(R.id.autoOpenSwitch);
        setAutoDoorListener(autoOpenDoor);

    }

    private int rand(int min, int max) {
        max -= min;
        return (int) (Math.random() * ++max) + min;
    }

    private void initProgressBar(ProgressBar progressBar, int temperature) {
        ProgressBarAnimation animation = new ProgressBarAnimation(progressBar, 0, tempCoef * temperature);
        animation.setDuration(500);
        progressBar.startAnimation(animation);
    }

    private void setOpenDoorListener(Switch openDoor) {
        openDoor.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    ((Switch) findViewById(R.id.autoOpenSwitch)).setChecked(false);
                    // TODO: 08.05.2018 Tell arduino to open the door
                } else {
                    // TODO: 08.05.2018 Tell arduino to close the door 
                }
            }
        });
    }

    private void setAutoDoorListener(Switch autoOpenDoor) {
        autoOpenDoor.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    ((Switch) findViewById(R.id.openSwitch)).setChecked(false);
                    // TODO: 08.05.2018 Tell arduino to open the door automatically
                } else {
                    // TODO: 08.05.2018 Turn off
                }
            }
        });
    }

}
/*
сервопривод
servo.h

на какие порты (на аналогвые и цифровые)
pwm
мотором зажать трубку

*/
