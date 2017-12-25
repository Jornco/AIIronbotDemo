package com.jornco.aiironbotdemo.activity.a7;

import android.bluetooth.BluetoothDevice;

import com.jornco.aiironbotdemo.activity.a5.A5BLEService;
import com.jornco.aiironbotdemo.ble.device.IronbotInfo;
import com.jornco.aiironbotdemo.ble.scan.IronbotFilter;
import com.jornco.aiironbotdemo.ble.scan.IronbotSearcherCallback;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by kkopite on 2017/12/24.
 */

public class A7IronbotSearcher {

    public static Map<String, A5BLEService> mServices = new HashMap<>();

    private A7BLEScan mBLEScan = new A7BLEScan();

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

    public static A5BLEService findService(IronbotInfo info) {
        String address = info.getAddress();
// 從mServiceList找出一個service
        return mServices.get(address);
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
