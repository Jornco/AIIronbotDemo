package com.jornco.aiironbotdemo.ble;

import android.bluetooth.BluetoothGatt;

/**
 * Created by kkopite on 2017/12/24.
 */

public class A5BLESession {

    private BluetoothGatt mGatt;
    private A5BLEService mService;

    public A5BLESession(BluetoothGatt gatt, A5BLEService service) {
        mGatt = gatt;
        mService = service;
    }

    public void sendMsg(IronbotCode code, OnIronbotWriteCallback callback) {
        mService.sendMsg(code, callback);
    }
}
