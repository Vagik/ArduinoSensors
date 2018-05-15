package com.simonyan.arduinosensors.ClickListeners;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.simonyan.arduinosensors.Device;

import java.util.HashSet;
import java.util.Set;

public class DeleteClickListener implements View.OnClickListener {
    private Device device;
    private Activity activity;

    public DeleteClickListener(Device device, Activity activity) {
        this.device = device;
        this.activity = activity;
    }


    @Override
    public void onClick(View v) {
        SharedPreferences preferences = activity.getSharedPreferences("Storage", Context.MODE_PRIVATE);
        Set<String> setDevices = preferences.getStringSet("Devices", new HashSet<String>());

        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();
        String delDevice = gson.toJson(device);

        for (String dev : setDevices) {
            if (dev.equals(delDevice)) {
                setDevices.remove(dev);
                break;
            }
        }

        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.putStringSet("Devices", setDevices);
        editor.apply();

        Intent intent = new Intent(activity, activity.getClass());
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        activity.finish();
        activity.startActivity(intent);
    }
}
