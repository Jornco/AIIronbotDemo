package com.jornco.aiironbotdemo.activity.a18;

import android.os.IBinder;
import android.os.RemoteException;

import com.jornco.aiironbotdemo.IApplication;
import com.jornco.aiironbotdemo.ble.IronbotService;

/**
 * Created by kkopite on 2017/12/28.
 */

public class A18SrvBinder extends IronbotService.Stub {

    private A18BLEService mService;
    private IBinder mBinderCallback;

    public A18SrvBinder(A18BLEService service) {
        mService = service;
    }

    @Override
    public String getInfoXml() throws RemoteException {
        return mService.getInfo().toXml();
    }

    @Override
    public IBinder getSessionWithIB(IBinder callback) throws RemoteException {
        mBinderCallback = callback;
        A18BLESession session = mService.getSession(IApplication.getInstance());
        return new A18SessionBinder(session);
    }

    @Override
    public IBinder getSession() throws RemoteException {
        // 返回一个连接 session可能为null
        A18BLESession session = mService.getSession(IApplication.getInstance());
        return new A18SessionBinder(session);
    }
}
