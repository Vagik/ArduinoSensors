package com.unn.arduinosensors;


public class Device {
    String IP;
    String Port;
    String UserName;
    String Password;

    Device (String IP, String Port, String UserName, String Password){
        this.IP = IP;
        this.Port = Port;
        this.UserName = UserName;
        this.Password = Password;
    }
}
