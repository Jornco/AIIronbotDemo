package com.jornco.aiironbotdemo.activity.a13;

import android.bluetooth.BluetoothDevice;

import com.jornco.aiironbotdemo.ble.scan.IronbotFilter;
import com.jornco.aiironbotdemo.ble.scan.IronbotSearcherCallback;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by kkopite on 2017/12/26.
 */

public class A13IronbotSearcher {

    public static Map<String, A13BLEService> mServiceList = new HashMap<>();

    private A13BLEScan mBLEScan = new A13BLEScan();

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
