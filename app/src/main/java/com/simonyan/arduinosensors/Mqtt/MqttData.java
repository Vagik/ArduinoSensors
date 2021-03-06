package com.simonyan.arduinosensors.Mqtt;

import org.eclipse.paho.android.service.MqttAndroidClient;

public class MqttData {

    public static final String PUBLISH_TOPIC_HUM = "android/hum";
    public static final String SUBSCRIBE_TOPIC_HUM = "arduino/hum";
    public static final String SUBSCRIBE_TOPIC_TEMP = "arduino/temp";
    public static final String SUBSCRIBE_TOPIC_WATERING = "arduino/watering";
    public static final String MQTT_BROKER_URL = "tcp://iot.eclipse.org:";

    public static int humValue = 0;
    public static int tempValue = 0;

    public static MqttAndroidClient client;
    public static PahoMqttClient pahoMqttClient;

}
