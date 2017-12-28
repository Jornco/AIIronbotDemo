// IronbotSession.aidl
package com.jornco.aiironbotdemo.ble;

// Declare any non-default types here with import statements

interface IronbotSession {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    boolean isConnected();
    String getServiceInfoXml();
    void sendMsg(String codeXml, IBinder callback);
}
