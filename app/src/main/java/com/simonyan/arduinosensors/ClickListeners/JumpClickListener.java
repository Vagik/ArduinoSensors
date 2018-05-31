package com.simonyan.arduinosensors.ClickListeners;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

public class JumpClickListener implements View.OnClickListener {
    private Activity activity;
    private Class jumpClass;

    public JumpClickListener(Activity activity, Class jumpClass){
        this.activity = activity;
        this.jumpClass = jumpClass;
    }

    @Override
    public void onClick(View v){
        Intent intent = new Intent(activity, jumpClass);
        activity.startActivity(intent);
    }
}
