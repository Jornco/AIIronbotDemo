package com.jornco.aiironbotdemo.activity.a18;

import android.bluetooth.BluetoothGatt;

/**
 * Created by kkopite on 2017/12/28.
 */

public class A18BLESession {

    private BluetoothGatt mGatt;
    private A18BLEService mService = null;

    public A18BLESession(BluetoothGatt gatt, A18BLEService srv) {
        mGatt = gatt;
        mService = srv;
    }
    public String getServiceInfoXml() {
        return mService.getInfo().toXml();
    }

}
