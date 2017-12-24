package com.jornco.aiironbotdemo.ble;

import android.bluetooth.BluetoothGatt;

/**
 * Created by kkopite on 2017/12/24.
 */

public class A4BLESession {

    private BluetoothGatt mGatt;
    private A4BLEService mService;

    public A4BLESession(BluetoothGatt gatt, A4BLEService service) {
        mGatt = gatt;
        mService = service;
    }

}
