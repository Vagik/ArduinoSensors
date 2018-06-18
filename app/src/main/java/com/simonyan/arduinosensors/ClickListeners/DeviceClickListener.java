package com.simonyan.arduinosensors.ClickListeners;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

import com.simonyan.arduinosensors.Device;

public class DeviceClickListener implements View.OnClickListener {
    private Device device;
    private Activity activity;
    private Class moveTo;

    public DeviceClickListener(Device device, Activity activity, Class moveTo) {
        this.device = device;
        this.activity = activity;
        this.moveTo = moveTo;
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(activity, moveTo);
        intent.putExtra("Device", device);
        activity.startActivity(intent);
    }
}

