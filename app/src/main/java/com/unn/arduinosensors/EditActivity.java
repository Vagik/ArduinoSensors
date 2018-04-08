package com.unn.arduinosensors;

import android.support.constraint.ConstraintLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.Serializable;

public class EditActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        Bundle arguments = getIntent().getExtras();
        Device device = (Device) arguments.getSerializable(Device.class.getSimpleName());

        LinearLayout mainLayout = new LinearLayout(this);
        mainLayout.setOrientation(LinearLayout.VERTICAL);
        mainLayout.setLayoutParams(new ActionBar.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT));
        ((ConstraintLayout) findViewById(R.id.main)).addView(mainLayout);

        String textViews[] = new String[5];
        fillTextViews(textViews);

        String deviceFields[] = new String[5];
        fillDeviceFields(deviceFields, device);

        for (int i = 0; i < 5; i++) {
            LinearLayout linearLayout = new LinearLayout(this);
            linearLayout.setOrientation(LinearLayout.VERTICAL);
            linearLayout.setLayoutParams(new ActionBar.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT));

            TextView textView = new TextView(this);
            textView.setText(textViews[i]);
            linearLayout.addView(textView);

            EditText editText = new EditText(this);
            editText.setText(deviceFields[i]);
            linearLayout.addView(editText);

            mainLayout.addView(linearLayout);
        }
    }


    private void fillTextViews(String[] textViews) {
        textViews[0] = "Device Name:";
        textViews[1] = "IP:";
        textViews[2] = "Port:";
        textViews[3] = "Username:";
        textViews[4] = "Password:";
    }

    private void fillDeviceFields(String[] deviceFields, Device device) {
        deviceFields[0] = device.Name;
        deviceFields[1] = device.IP;
        deviceFields[2] = device.Port;
        deviceFields[3] = device.UserName;
        deviceFields[4] = device.Password;
    }
}
