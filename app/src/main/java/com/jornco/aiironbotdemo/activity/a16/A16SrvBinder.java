package com.jornco.aiironbotdemo.activity.a16;

import android.os.Binder;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;

import com.jornco.aiironbotdemo.activity.a13.A13BLEService;
import com.jornco.aiironbotdemo.ble.device.IronbotInfo;

/**
 * Created by kkopite on 2017/12/26.
 */

public class A16SrvBinder extends Binder {

    private A13BLEService mService;
    private IBinder mBinderCallback;

    public A16SrvBinder(A13BLEService service, IBinder binderCallback) {
        mService = service;
        mBinderCallback = binderCallback;
    }

    @Override
    protected boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
        if (code == 0) {
            // getInfo()
            IronbotInfo info = mService.getInfo();
            Parcel data2 = Parcel.obtain();
            Parcel reply2 = Parcel.obtain();
            data2.writeString(info.toXml());
            /// 回调到activity的 acBinder
            mBinderCallback.transact(3, data2, reply2, 0);
            return true;
        }
        return super.onTransact(code, data, reply, flags);
    }
}
