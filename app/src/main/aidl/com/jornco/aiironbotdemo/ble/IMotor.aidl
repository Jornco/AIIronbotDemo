// IMotor.aidl
package com.jornco.aiironbotdemo.ble;

// Declare any non-default types here with import statements

interface IMotor {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    // 掃描5秒種
    void startScan(IBinder ib_callback);
    int getServicesCount();
    // 針對選定的Service建立Connection
    boolean bindService(int serviceIndex);
    // 獲取一個特定Motor的資料
    String getMotorInfoXml(int motorIndex );
}
