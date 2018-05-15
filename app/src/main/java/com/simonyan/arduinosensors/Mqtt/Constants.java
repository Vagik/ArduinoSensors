package com.simonyan.arduinosensors.Mqtt;

import org.eclipse.paho.android.service.MqttAndroidClient;

public class Constants {

    public static String MQTT_BROKER_URL;
    public static final String PUBLISH_TOPIC= "android/topic";
    public static final String SUBSCRIBE_TOPIC= "arduino/topic";
    public static String CLIENT_ID = "android";
    public static String USERNAME;
    public static String PASSWORD;

    public static MqttAndroidClient client;
    public static PahoMqttClient pahoMqttClient;
}
