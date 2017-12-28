package com.jornco.aiironbotdemo.activity.a22;

import android.bluetooth.BluetoothGatt;

import com.jornco.aiironbotdemo.ble.common.IronbotCode;
import com.jornco.aiironbotdemo.ble.connect.OnIronbotWriteCallback;

import java.util.List;

/**
 * Created by kkopite on 2017/12/28.
 */

public class A22BLESession {

    private BluetoothGatt mGatt;
    private A22BLEService mService = null;

    public A22BLESession(BluetoothGatt gatt, A22BLEService srv) {
        mGatt = gatt;
        mService = srv;
    }
    public List<A22BLEMotor> getMotorList(){
        return mService.getMotorList();
    }

    public String getServiceInfoXml() {
        return mService.getInfo().toXml();
    }
    public void sendMsg(IronbotCode code, OnIronbotWriteCallback callback) {
        mService.sendMsg(code, callback);
    }
}
