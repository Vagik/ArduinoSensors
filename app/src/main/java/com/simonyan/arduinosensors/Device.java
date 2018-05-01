package com.simonyan.arduinosensors;


import java.io.Serializable;

public class Device implements Serializable {
    private String Name;
    private String IP;
    private String Port;
    private String UserName;
    private String Password;

    public Device(String Name, String IP, String Port, String UserName, String Password) {
        this.Name = Name;
        this.IP = IP;
        this.Port = Port;
        this.UserName = UserName;
        this.Password = Password;
    }

    public String getName() {
        return Name;
    }

    public String getIP() {
        return IP;
    }

    public String getPort() {
        return Port;
    }

    public String getUserName() {
        return UserName;
    }

    public String getPassword() {
        return Password;
    }


}
