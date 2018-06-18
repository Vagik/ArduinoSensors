package com.simonyan.arduinosensors.Animation;

import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.TextView;

public class TextViewAnimation extends Animation {
    private TextView textView;
    private float value;
    private String unit;

    public TextViewAnimation(TextView textView, float value, String unit) {
        super();
        this.textView = textView;
        this.value = value;
        this.unit = unit;
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        super.applyTransformation(interpolatedTime, t);
        float value = this.value * interpolatedTime;
        textView.setText((int) value + unit);
    }
}