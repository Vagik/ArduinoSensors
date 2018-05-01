package com.simonyan.arduinosensors.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.simonyan.arduinosensors.MyToolbar;
import com.simonyan.arduinosensors.R;

public class MotionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_motion);

        Toolbar toolbar =  (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Motion Sensor");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        GraphView graph = (GraphView) findViewById(R.id.motionGraph);
        DataPoint[] dataPoints = new DataPoint[15];
        for (int i = 0; i < 15; i++) {
            dataPoints[i] = new DataPoint(i, rand(0, 5));
        }
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(dataPoints);
        graph.addSeries(series);
    }

    private int rand(int min, int max) {
        max -= min;
        return (int) (Math.random() * ++max) + min;
    }
}
