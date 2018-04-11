package com.unn.arduinosensors;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ConnectionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connection);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Add Device");
        setSupportActionBar(toolbar);

        LinearLayout mainLayout = ((LinearLayout) findViewById(R.id.connectionLayout));

        String textViews[] = new String[5];
        fillTextViews(textViews);

        RelativeLayout.LayoutParams textViewParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(textViewParams);
        textViewParams.setMargins(10, 0, 0, 0);

        for (int i = 0; i < 5; i++) {
            LinearLayout linearLayout = new LinearLayout(this);
            linearLayout.setOrientation(LinearLayout.VERTICAL);
            linearLayout.setLayoutParams(layoutParams);

            TextView textView = new TextView(this);
            textView.setText(textViews[i]);
            textView.setLayoutParams(textViewParams);
            linearLayout.addView(textView);

            EditText editText = new EditText(this);
            editText.setLines(1);
            linearLayout.addView(editText);

            mainLayout.addView(linearLayout);
        }

        Button button = new Button(this);
        button.setText(R.string.addDevice);
        button.setBackgroundColor(getResources().getColor(R.color.colorButton));
        button.setLayoutParams(layoutParams);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        mainLayout.addView(button);
    }


    private void fillTextViews(String[] textViews) {
        textViews[0] = "Device Name:";
        textViews[1] = "IP:";
        textViews[2] = "Port:";
        textViews[3] = "Username:";
        textViews[4] = "Password:";
    }
}
