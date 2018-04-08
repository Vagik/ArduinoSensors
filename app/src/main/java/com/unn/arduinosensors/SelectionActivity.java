package com.unn.arduinosensors;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SelectionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selection);

        Device[] devices = new Device[3];
        devices[0] = new Device("Device_1", "168.192.1.1", "Port_1", "Username_1", "Password_1");
        devices[1] = new Device("Device_2", "168.192.1.2", "Port_2", "Username_2", "Password_2");
        devices[2] = new Device("Device_3", "168.192.1.3", "Port_3", "Username_3", "Password_3");

        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.mainLayout);

        for (Device device : devices) {
            LinearLayout linearLayout1 = new LinearLayout(this);
            linearLayout1.setOrientation(LinearLayout.HORIZONTAL);
            linearLayout1.setLayoutParams(new ActionBar.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT));

            TextView textView = new TextView(this);
            textView.append(device.Name);
            textView.setTextSize(24);
            textView.setTextColor(Color.rgb(0, 0, 0));
            linearLayout1.addView(textView);

            Button button = new Button(this);
            button.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_edit_button, 0, 0, 0);
            button.setBackgroundColor(getResources().getColor(R.color.colorBackground));
            final Device intentDevice = device;
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(SelectionActivity.this, EditActivity.class);
                    intent.putExtra(Device.class.getSimpleName(), intentDevice);
                    startActivity(intent);
                }
            });
            linearLayout1.addView(button);

            linearLayout.addView(linearLayout1);
        }

    }
}
