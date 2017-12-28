package com.jornco.aiironbotdemo.activity.a19;

import android.bluetooth.BluetoothGatt;

import com.jornco.aiironbotdemo.ble.common.IronbotCode;
import com.jornco.aiironbotdemo.ble.connect.OnIronbotWriteCallback;

/**
 * Created by kkopite on 2017/12/28.
 */

public class A19BLESession {
    private BluetoothGatt mGatt;
    private A19BLEService mService = null;

    public A19BLESession(BluetoothGatt gatt, A19BLEService srv) {
        mGatt = gatt;
        mService = srv;
    }

    public String getServiceInfoXml() {
        return mService.getInfo().toXml();
    }

    public void sendMsg(IronbotCode code, OnIronbotWriteCallback callback) {
        mService.sendMsg(code, callback);
    }
}
