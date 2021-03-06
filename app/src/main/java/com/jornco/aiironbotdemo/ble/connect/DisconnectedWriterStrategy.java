package com.jornco.aiironbotdemo.ble.connect;

import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;

import com.jornco.aiironbotdemo.ble.common.BLEWriterError;


/**
 * Created by kkopite on 2017/11/29.
 */

public class DisconnectedWriterStrategy implements IWriterStrategy {

    private String address;

    public DisconnectedWriterStrategy(String address) {
        this.address = address;
    }

    @Override
    public void write(String data, IronbotWriterCallback callback) {
        callback.writerFailure(address, data, new BLEWriterError(address, data, "当前设备已断开"));
    }

    @Override
    public void writeSuccess() {

    }

    @Override
    public void writeFailure() {

    }

    @Override
    public void start(BluetoothGatt gatt, BluetoothGattCharacteristic writerBGC) {

    }

    @Override
    public void stop() {

    }
}
