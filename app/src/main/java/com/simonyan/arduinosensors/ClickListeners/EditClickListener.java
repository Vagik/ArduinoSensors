package com.simonyan.arduinosensors.ClickListeners;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.EditText;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.simonyan.arduinosensors.Activities.SelectionActivity;
import com.simonyan.arduinosensors.Device;
import com.simonyan.arduinosensors.R;

import java.util.HashSet;
import java.util.Set;

public class EditClickListener implements View.OnClickListener {
    private Device device;
    private Context context;
    private Activity activity;

    public EditClickListener(Device device, Context context, Activity activity) {
        this.device = device;
        this.context = context;
        this.activity = activity;
    }

    @Override
    public void onClick(View v) {
        Device newDevice = readChanges();

        SharedPreferences preferences = context.getSharedPreferences("Storage", Context.MODE_PRIVATE);
        Set<String> setDevices = preferences.getStringSet("Devices", new HashSet<String>());

        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();
        String gsonOldDevice = gson.toJson(device);

        for (String dev : setDevices) {
            if (dev.equals(gsonOldDevice)) {
                setDevices.remove(dev);
                setDevices.add(gson.toJson(newDevice));
                break;
            }
        }

        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.putStringSet("Devices", setDevices);
        editor.apply();

        Intent intent = new Intent(context, SelectionActivity.class);
        context.startActivity(intent);

    }

    public Device readChanges() {
        String Name = ((EditText) activity.findViewById(R.id.editName)).getText().toString();
        String IP = ((EditText) activity.findViewById(R.id.editIP)).getText().toString();
        String Port = ((EditText) activity.findViewById(R.id.editPort)).getText().toString();
        String UserName = ((EditText) activity.findViewById(R.id.editUsername)).getText().toString();
        String Password = ((EditText) activity.findViewById(R.id.editPassword)).getText().toString();
        return new Device(Name, IP, Port, UserName, Password);
    }

}