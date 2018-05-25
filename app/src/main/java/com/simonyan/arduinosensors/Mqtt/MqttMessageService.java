package com.simonyan.arduinosensors.Mqtt;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.ProgressBar;

import com.simonyan.arduinosensors.ProgressBarAnimation;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class MqttMessageService extends Service {

    public MqttMessageService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        PahoMqttClient pahoMqttClient = new PahoMqttClient();
        MqttAndroidClient mqttAndroidClient = pahoMqttClient.getMqttClient(getApplicationContext(), StaticData.MQTT_BROKER_URL, StaticData.CLIENT_ID);


        mqttAndroidClient.setCallback(new MqttCallbackExtended() {
            @Override
            public void connectComplete(boolean b, String s) {
            }

            @Override
            public void connectionLost(Throwable throwable) {
            }

            @Override
            public void messageArrived(String topic, MqttMessage mqttMessage) {
                String message = new String(mqttMessage.getPayload());

                if(topic.equals(StaticData.SUBSCRIBE_TOPIC_HUM)) {
                    StaticData.humValue = Integer.valueOf(message);
                }else if(topic.equals(StaticData.SUBSCRIBE_TOPIC_TEMP)) {
                    StaticData.tempValue = Integer.valueOf(message);
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
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    public void initProgressBar(ProgressBar progressBar, int coef, int value) {
        ProgressBarAnimation animation = new ProgressBarAnimation(progressBar, 0, coef * value);
        animation.setDuration(500);
        progressBar.startAnimation(animation);
    }

}
