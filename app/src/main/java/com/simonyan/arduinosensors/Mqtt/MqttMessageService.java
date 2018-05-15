package com.simonyan.arduinosensors.Mqtt;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.simonyan.arduinosensors.Activities.HumidityActivity;
import com.simonyan.arduinosensors.Activities.MainActivity;
import com.simonyan.arduinosensors.R;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class MqttMessageService extends Service {

    private static final String TAG = "MqttMessageService";
    private PahoMqttClient pahoMqttClient;
    private MqttAndroidClient mqttAndroidClient;

    public MqttMessageService() {
        Log.i("MQTT", "SERVICE");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        pahoMqttClient = new PahoMqttClient();
        mqttAndroidClient = pahoMqttClient.getMqttClient(getApplicationContext(), Constants.MQTT_BROKER_URL, Constants.CLIENT_ID);

        mqttAndroidClient.setCallback(new MqttCallbackExtended() {
            @Override
            public void connectComplete(boolean b, String s) {
                Log.i("MQTT", "connectComplete");
            }

            @Override
            public void connectionLost(Throwable throwable) {
                Log.i("MQTT", "connectionLost");
            }

            @Override
            public void messageArrived(String topic, MqttMessage mqttMessage) throws Exception {
                String message = new String(mqttMessage.getPayload());
                String mes ="" + message.charAt(1) + message.charAt(2);
                int humidity = Integer.valueOf(mes);
                HumidityActivity.tempText.setText(humidity + "%");
                HumidityActivity.initProgressBar(HumidityActivity.progressBar, humidity);
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
                Log.i("MQTT", "deliveryComplete");
            }
        });
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "onStartCommand");
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy");
    }

    private void setMessageNotification(String topic, String msg) {
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.ic_edit_button)
                        .setContentTitle(topic)
                        .setContentText(msg);
        Intent resultIntent = new Intent(this, MainActivity.class);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(MainActivity.class);
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(resultPendingIntent);
        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(100, mBuilder.build());
    }
}
