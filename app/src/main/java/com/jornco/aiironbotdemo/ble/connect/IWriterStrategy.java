package com.jornco.aiironbotdemo.ble.connect;

import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;

/**
 * Created by kkopite on 2017/11/29.
 */

public interface IWriterStrategy {
    void write(String data, IronbotWriterCallback callback);
    void writeSuccess();
    void writeFailure();
    void start(BluetoothGatt gatt, BluetoothGattCharacteristic writerBGC);
    void stop();
}
