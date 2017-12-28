package com.jornco.aiironbotdemo.activity.a18;

import android.os.IBinder;
import android.os.RemoteException;

import com.jornco.aiironbotdemo.ble.IronbotSession;

/**
 * Created by kkopite on 2017/12/28.
 */

public class A18SessionBinder extends IronbotSession.Stub {

    private A18BLESession mSession;
    private IBinder mBinderCallback;

    public A18SessionBinder(A18BLESession session) {
        mSession = session;
    }

    @Override
    public boolean isConnected() throws RemoteException {
        return false;
    }

    @Override
    public String getServiceInfoXml() throws RemoteException {
        return mSession.getServiceInfoXml();
    }

    @Override
    public void sendMsg(String codeXml, IBinder callback) throws RemoteException {
        mBinderCallback = callback;
    }
}
