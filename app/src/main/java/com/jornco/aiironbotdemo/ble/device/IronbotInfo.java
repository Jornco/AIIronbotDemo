package com.jornco.aiironbotdemo.ble.device;

/**
 * Created by kkopite on 2017/12/22.
 */

public class IronbotInfo {

    private final String name;
    private final String address;
    private BLEState state;

    public IronbotInfo(String xml) {
        if (xml == null) {
            throw new IllegalArgumentException("xml can not be null");
        }
        String[] strings = xml.split(",");
        if (strings.length != 2) {
            throw new IllegalArgumentException("xml is invalid");
        }
        name = strings[0];
        address = strings[1];
    }

    public IronbotInfo(String name, String address) {
        this(name, address, BLEState.DISCONNECT);
    }

    public IronbotInfo(String name, String address, BLEState state) {
        this.name = name;
        this.address = address;
        this.state = state;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public BLEState getState() {
        return state;
    }

    public void setState(BLEState state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "IronbotInfo{" +
                "name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", state=" + state +
                '}';
    }

    public String toXml() {
        return name + "," + address;
    }
}
