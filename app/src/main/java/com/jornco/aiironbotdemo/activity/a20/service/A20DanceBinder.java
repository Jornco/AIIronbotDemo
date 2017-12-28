package com.jornco.aiironbotdemo.activity.a20.service;

import android.os.IBinder;
import android.os.RemoteException;

import com.jornco.aiironbotdemo.IApplication;
import com.jornco.aiironbotdemo.activity.a19.A19BLEService;
import com.jornco.aiironbotdemo.activity.a19.A19BLESession;
import com.jornco.aiironbotdemo.activity.a19.A19IronbotSearcher;
import com.jornco.aiironbotdemo.ble.IDance;
import com.jornco.aiironbotdemo.ble.device.IronbotInfo;
import com.jornco.aiironbotdemo.ble.scan.IronbotSearcherCallback;
import com.jornco.aiironbotdemo.util.ActionSendHelper;
import com.jornco.aiironbotdemo.util.IronbotCodeUtil;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by kkopite on 2017/12/28.
 */

public class A20DanceBinder extends IDance.Stub implements IronbotSearcherCallback {

    private A19IronbotSearcher mSearcher = new A19IronbotSearcher();
    private IBinder mBinderCallback;
    private String mStatus;
    private A19BLEService mService;
    private A19BLESession mSession;
    private ActionSendHelper mHelper;

    private Timer mTimer = new Timer();
    private TimerTask mTask;
    @Override
    public void onIronbotFound(IronbotInfo info) {

    }

    @Override
    public void onIronbotFound(String xml) {

    }

    @Override
    public void startScan(IBinder ib_callback) throws RemoteException {
        if (mTask != null) {
            mTask.cancel();
        }
        mTask = new TimerTask() {
            @Override
            public void run() {
                mSearcher.stopScan();
            }
        };
        mBinderCallback = ib_callback;
        mSearcher.searchIronbot(this);

        // 5秒后停止扫描
        mTimer.schedule(mTask, 5000);
    }

    @Override
    public int getServicesCount() throws RemoteException {
        return A19IronbotSearcher.mServiceList.size();
    }

    @Override
    public boolean bindService(int serviceIndex) throws RemoteException {
        // map 不是 list
        // 要嘛传入address
        mService = A19IronbotSearcher.mServiceList.values().iterator().next();
        mSession = mService.getSession(IApplication.getInstance());
        return true;
    }

    @Override
    public void startDance() throws RemoteException {
        if (mHelper != null) {
            mHelper.stop();
        }
        mHelper = new ActionSendHelper(mSession, IronbotCodeUtil.createRandomAction());
        mHelper.start();
    }

    @Override
    public void stopDance() throws RemoteException {
        if (mHelper != null) {
            mHelper.stop();
        }
    }
}
