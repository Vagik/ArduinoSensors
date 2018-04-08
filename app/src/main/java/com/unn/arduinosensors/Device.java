package com.unn.arduinosensors;


import java.io.Serializable;

public class Device implements Serializable {
    String Name;
    String IP;
    String Port;
    String UserName;
    String Password;

    Device(String Name, String IP, String Port, String UserName, String Password) {
        this.Name = Name;
        this.IP = IP;
        this.Port = Port;
        this.UserName = UserName;
        this.Password = Password;
    }
}
