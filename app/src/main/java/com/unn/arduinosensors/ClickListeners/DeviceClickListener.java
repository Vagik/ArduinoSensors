package com.unn.arduinosensors.ClickListeners;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.unn.arduinosensors.Device;

public class DeviceClickListener implements View.OnClickListener {
    private Device device;
    private Context context;
    private Class moveTo;

    public DeviceClickListener(Device device, Context context, Class moveTo) {
        this.device = device;
        this.context = context;
        this.moveTo = moveTo;
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(context, moveTo);
        intent.putExtra("Device", device);
        context.startActivity(intent);
    }
}

