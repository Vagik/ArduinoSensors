package com.simonyan.arduinosensors.Mqtt;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.view.Gravity;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.simonyan.arduinosensors.Activities.MainActivity;
import com.simonyan.arduinosensors.Device;
import com.simonyan.arduinosensors.R;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class MqttMessageService extends Service {
    final int humidityCoef = 1;
    final int tempCoef = 2;
    Device device;

    @Override
    public void onCreate() {
        super.onCreate();

        initializeMQTT();

        MqttData.client.setCallback(new MqttCallbackExtended() {
            @Override
            public void connectComplete(boolean b, String s) {
                Toast toast = Toast.makeText(getApplicationContext(),
                        "Connection Complete!",
                        Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER_HORIZONTAL, 0, 0);
                toast.setGravity(Gravity.BOTTOM, 0, 15);
                toast.show();
            }

            @Override
            public void connectionLost(Throwable throwable) {
                Toast toast = Toast.makeText(getApplicationContext(),
                        "Connection Lost!",
                        Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER_HORIZONTAL, 0, 0);
                toast.setGravity(Gravity.BOTTOM, 0, 15);
                toast.show();
            }

            @Override
            public void messageArrived(String topic, MqttMessage mqttMessage) {
                String message = new String(mqttMessage.getPayload());
                switch (topic) {
                    case MqttData.SUBSCRIBE_TOPIC_HUM:
                        MqttData.humValue = getValue(message, humidityCoef);
                        break;
                    case MqttData.SUBSCRIBE_TOPIC_TEMP:
                        MqttData.tempValue = getValue(message, tempCoef);
                        break;
                    case MqttData.SUBSCRIBE_TOPIC_WATERING:
                        if (message.equals("S")) {
                            setMessageNotification("Watering started!");
                        } else if (message.equals("F")) {
                            setMessageNotification("Watering finished!");
                        }
                        break;
                }
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
            }
        });
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    private void setMessageNotification(String msg) {
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle(device.getName())
                        .setContentText(msg);
        Intent resultIntent = new Intent(this, MainActivity.class);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(MainActivity.class);
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(resultPendingIntent);
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(100, mBuilder.build());
    }

    private void initializeMQTT() {
        SharedPreferences preferences = getSharedPreferences("SelectedDevice", Context.MODE_PRIVATE);
        String dev = preferences.getString("Device", null);

        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();
        device = gson.fromJson(dev, Device.class);

        MqttData.pahoMqttClient = new PahoMqttClient(device);
        MqttData.client = MqttData.pahoMqttClient.getMqttClient(getApplicationContext(),
                MqttData.MQTT_BROKER_URL + device.getPort(), device.getClientID());
    }

    int getValue(String message, int coef){
        int value = Integer.valueOf(message);
        if(value < 0) {
            return 0;
        } else if (value > 100 / coef){
            return 100 / coef;
        } else{
            return value;
        }
    }
}