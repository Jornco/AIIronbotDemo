// ISearchIronbot.aidl
package com.jornco.aiironbotdemo.ble;

// Declare any non-default types here with import statements

interface ISearchIronbot {

    boolean isEnable();
    void searchIronbot(IBinder callback);
    void enable();
    void stopScan();
    IBinder findService(String infoXml);
}
