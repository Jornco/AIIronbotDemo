// IDance.aidl
package com.jornco.aiironbotdemo.ble;

// Declare any non-default types here with import statements

interface IDance {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    // 掃描5秒種
    void startScan(IBinder ib_callback);
    int getServicesCount();
    // 針對選定的Service建立Connection
    boolean bindService(int serviceIndex);
    // 開始跳舞
    void startDance();
    // 結束跳舞
    void stopDance();
}
