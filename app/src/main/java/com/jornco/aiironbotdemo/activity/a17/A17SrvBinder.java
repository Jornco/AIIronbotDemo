package com.jornco.aiironbotdemo.activity.a17;

import android.os.IBinder;
import android.os.RemoteException;

import com.jornco.aiironbotdemo.activity.a13.A13BLEService;
import com.jornco.aiironbotdemo.ble.IronbotService;

/**
 * Created by kkopite on 2017/12/26.
 */

public class A17SrvBinder extends IronbotService.Stub{

    private A13BLEService mService;

    public A17SrvBinder(A13BLEService service) {
        mService = service;
    }

    @Override
    public String getInfoXml() throws RemoteException {
        return mService.getInfo().toXml();
    }

    @Override
    public IBinder getSessionWithIB(IBinder callback) throws RemoteException {
        return null;
    }

    @Override
    public IBinder getSession() throws RemoteException {
        return null;
    }
}
