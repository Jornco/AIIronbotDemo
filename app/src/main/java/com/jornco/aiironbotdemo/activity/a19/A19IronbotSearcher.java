package com.jornco.aiironbotdemo.activity.a19;

import android.bluetooth.BluetoothDevice;

import com.jornco.aiironbotdemo.ble.device.IronbotInfo;
import com.jornco.aiironbotdemo.ble.scan.IronbotFilter;
import com.jornco.aiironbotdemo.ble.scan.IronbotSearcherCallback;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by kkopite on 2017/12/28.
 */

public class A19IronbotSearcher {

    public static Map<String, A19BLEService> mServiceList = new HashMap<>();

    private A19BLEScan mBLEScan = new A19BLEScan();

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

    public A19BLEService findService(IronbotInfo info) {
        String address = info.getAddress();
        return mServiceList.get(address);
    }
}
