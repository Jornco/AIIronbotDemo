package com.jornco.aiironbotdemo.activity.a14;

import android.os.Binder;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;

import com.jornco.aiironbotdemo.activity.a13.A13BLEService;
import com.jornco.aiironbotdemo.ble.device.IronbotInfo;

/**
 * Created by kkopite on 2017/12/26.
 */

public class A14SrvBinder extends Binder {

    private A13BLEService mService;
    private IBinder mBinderCallback;

    public A14SrvBinder(A13BLEService service, IBinder binderCallback) {
        mService = service;
        mBinderCallback = binderCallback;
    }

    @Override
    protected boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
        IronbotInfo info = mService.getInfo();
        reply.writeString(info.toXml());
        return true;
    }
}
