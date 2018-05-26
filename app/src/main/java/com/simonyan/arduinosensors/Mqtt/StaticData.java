package com.simonyan.arduinosensors.Mqtt;

import android.widget.Switch;

import org.eclipse.paho.android.service.MqttAndroidClient;

public class StaticData {

    public static String MQTT_BROKER_URL;
    public static final String PUBLISH_TOPIC_HUM= "android/hum";
    public static final String SUBSCRIBE_TOPIC_HUM= "arduino/hum";
    public static final String SUBSCRIBE_TOPIC_TEMP= "arduino/temp";
    public static String CLIENT_ID;
    public static String USERNAME;
    public static String PASSWORD;


    public static int humValue = 0;
    public static int tempValue = 0;

    public static MqttAndroidClient client;
    public static PahoMqttClient pahoMqttClient;

}
