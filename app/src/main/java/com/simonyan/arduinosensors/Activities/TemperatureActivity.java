package com.simonyan.arduinosensors.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.simonyan.arduinosensors.ProgressBarAnimation;
import com.simonyan.arduinosensors.R;

public class TemperatureActivity extends AppCompatActivity {
    private static final int tempCoef = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temperature);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Temperature Sensor");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        int temperature = rand(20, 50);

        initProgressBar((ProgressBar) findViewById(R.id.progressBar), temperature);

        ((TextView) findViewById(R.id.tempTextView)).setText(Integer.toString(temperature) + "°C");

        GraphView graph = (GraphView) findViewById(R.id.tempGraph);
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

    private void initProgressBar(ProgressBar progressBar, int temperature) {
        ProgressBarAnimation animation = new ProgressBarAnimation(progressBar, 0, tempCoef * temperature);
        animation.setDuration(500);
        progressBar.startAnimation(animation);
    }
}
