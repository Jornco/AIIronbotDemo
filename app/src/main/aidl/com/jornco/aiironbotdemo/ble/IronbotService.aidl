// IronbotService.aidl
package com.jornco.aiironbotdemo.ble;

// Declare any non-default types here with import statements

interface IronbotService {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    String getInfoXml();
    IBinder getSessionWithIB(IBinder callback);
    IBinder getSession();
}
