package com.jornco.aiironbotdemo.activity.a19;

import android.os.IBinder;
import android.os.RemoteException;

import com.jornco.aiironbotdemo.IApplication;
import com.jornco.aiironbotdemo.ble.IronbotService;

/**
 * Created by kkopite on 2017/12/28.
 */

public class A19SrvBinder extends IronbotService.Stub {
    private A19BLEService mService;
    private IBinder mBinderCallback;

    public A19SrvBinder(A19BLEService service) {
        mService = service;
    }

    @Override
    public String getInfoXml() throws RemoteException {
        return mService.getInfo().toXml();
    }

    @Override
    public IBinder getSessionWithIB(IBinder callback) throws RemoteException {
        mBinderCallback = callback;
        A19BLESession session = mService.getSession(IApplication.getInstance());
        return new A19SessionBinder(session);
    }

    @Override
    public IBinder getSession() throws RemoteException {
        // 返回一个连接 session可能为null
        A19BLESession session = mService.getSession(IApplication.getInstance());
        return new A19SessionBinder(session);
    }
}
