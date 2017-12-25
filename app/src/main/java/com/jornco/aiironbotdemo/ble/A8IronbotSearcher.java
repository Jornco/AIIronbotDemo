package com.jornco.aiironbotdemo.ble;

import android.bluetooth.BluetoothDevice;

import com.jornco.aiironbotdemo.ble.scan.IronbotFilter;
import com.jornco.aiironbotdemo.ble.scan.IronbotSearcherCallback;

/**
 * Created by kkopite on 2017/12/25.
 */

public class A8IronbotSearcher {

    private A8BLEScan mBLEScan = new A8BLEScan();
    private IronbotFilter mFilter = new IronbotFilter() {
        @Override
        public boolean filter(BluetoothDevice info) {
            String name = info.getName();
            return name != null && (name.equals("RS-BLE") || name.equals("PS-BLE") || name.startsWith("Tav") || name.startsWith("CC"));
        }
    };

    public void searchIronbot(IronbotSearcherCallback callback) {
        mBLEScan.searchIronbot(callback, mFilter);
    }

    public void stopScan(){
        mBLEScan.stopScan();
    }

    public boolean isEnable(){
        return mBLEScan.isEnable();
    }

    public void enable() {
        mBLEScan.enable();
    }

}
