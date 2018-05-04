package com.simonyan.arduinosensors;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class MyToolbar extends AppCompatActivity{

    private Activity activity;
    private String title;

    public MyToolbar(){}

    public MyToolbar(Activity activity, String title){
        this.activity = activity;
        this.title = title;
    }

    public void init(){
        Toolbar toolbar = (Toolbar) activity.findViewById(R.id.toolbar);
        toolbar.setTitle(title);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}
