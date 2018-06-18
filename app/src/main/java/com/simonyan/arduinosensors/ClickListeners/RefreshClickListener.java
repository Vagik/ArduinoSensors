package com.simonyan.arduinosensors.ClickListeners;

import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.simonyan.arduinosensors.Animation.ProgressBarAnimation;
import com.simonyan.arduinosensors.Animation.TextViewAnimation;
import com.simonyan.arduinosensors.Mqtt.MqttData;


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
        initTextView(textView, value, unit);
        initProgressBar(progressBar, coef, value);
    }


    private void initProgressBar(ProgressBar progressBar, int coef, int value) {
        ProgressBarAnimation animation = new ProgressBarAnimation(progressBar, coef * value);
        animation.setDuration(500);
        progressBar.startAnimation(animation);
    }

    private void initTextView(TextView textView, int value, String unit) {
        TextViewAnimation animation = new TextViewAnimation(textView, value, unit);
        animation.setDuration(500);
        textView.startAnimation(animation);
    }
}
