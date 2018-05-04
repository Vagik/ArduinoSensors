package com.simonyan.arduinosensors;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.ProgressBar;

public class ProgressBarAnimation extends Animation {
    private ProgressBar progressBar;
    private float from;
    private float to;

    public ProgressBarAnimation(ProgressBar progressBar, float from, float to) {
        super();
        this.progressBar = progressBar;
        this.from = from;
        this.to = to;
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        super.applyTransformation(interpolatedTime, t);
        float value = from + (to - from) * interpolatedTime;
        progressBar.setProgress((int) value);
        progressBar.getProgressDrawable().setColorFilter(Color.rgb(2 * (int) value, 100 - (int) value, 255 - 2 * (int) value), PorterDuff.Mode.SRC_IN);
    }
}