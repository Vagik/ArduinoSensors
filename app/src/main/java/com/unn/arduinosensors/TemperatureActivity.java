package com.unn.arduinosensors;

import android.print.PrintAttributes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

public class TemperatureActivity extends AppCompatActivity {
    private int tempCoef = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temperature);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Temperature");
        setSupportActionBar(toolbar);

        int temperature = rand(0, 50);
        TextView tempView = ((TextView) findViewById(R.id.tempTextView));
        tempView.setText(Integer.toString(temperature));

        ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);
        ProgressBarAnimation animation = new ProgressBarAnimation(progressBar, 0, tempCoef*temperature);
        animation.setDuration(500);
        progressBar.startAnimation(animation);

        GraphView graph = (GraphView) findViewById(R.id.graph);
        DataPoint[] dataPoints = new DataPoint[30];
        for (int i = 0; i < 30; i++) {
            dataPoints[i] = new DataPoint(i, Math.random() * 5);
        }
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(dataPoints);
        graph.addSeries(series);
    }

    private int rand(int min, int max) {
        max -= min;
        return (int) (Math.random() * ++max) + min;
    }
}
