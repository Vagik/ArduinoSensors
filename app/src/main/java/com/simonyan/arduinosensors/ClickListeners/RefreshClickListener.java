package com.simonyan.arduinosensors.ClickListeners;

import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.simonyan.arduinosensors.Mqtt.MqttData;
import com.simonyan.arduinosensors.ProgressBarAnimation;


public class RefreshClickListener implements View.OnClickListener {

    private TextView textView;
    private ProgressBar progressBar;
    private int coef;
    private int type;               // 0 - Humidity,  1 - Temperature

    public RefreshClickListener(TextView textView, ProgressBar progressBar, int coef, int type) {
        this.textView = textView;
        this.progressBar = progressBar;
        this.coef = coef;
        this.type = type;
    }


    @Override
    public void onClick(View v) {
        int value;
        String unit;
        if (type == 0) {
            value = MqttData.humValue;
            unit = "%";
        } else {
            value = MqttData.tempValue;
            unit = "°C";
        }
        textView.setText(value + unit);
        initProgressBar(progressBar, coef, value);
    }


    private void initProgressBar(ProgressBar progressBar, int coef, int value) {
        ProgressBarAnimation animation = new ProgressBarAnimation(progressBar, coef * value);
        animation.setDuration(500);
        progressBar.startAnimation(animation);
    }
}
