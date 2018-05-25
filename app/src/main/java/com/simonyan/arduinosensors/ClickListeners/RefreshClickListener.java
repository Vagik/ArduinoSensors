package com.simonyan.arduinosensors.ClickListeners;

import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.simonyan.arduinosensors.Mqtt.StaticData;
import com.simonyan.arduinosensors.ProgressBarAnimation;


public class RefreshClickListener implements View.OnClickListener {

    private TextView textView;
    private ProgressBar progressBar;
    private int coef;
    private int type;

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
        if(type == 0){
            value = StaticData.humValue;
            unit = "%";
        } else {
            value = StaticData.tempValue;
            unit = "°C";
        }
        textView.setText(value + unit);
        initProgressBar(progressBar, coef, value);
    }


    public void initProgressBar(ProgressBar progressBar, int coef, int value) {
        ProgressBarAnimation animation = new ProgressBarAnimation(progressBar, 0, coef * value);
        animation.setDuration(500);
        progressBar.startAnimation(animation);
    }
}
