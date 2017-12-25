package com.jornco.aiironbotdemo.activity.a8.service;

import android.os.Binder;

import com.jornco.aiironbotdemo.activity.a8.A8IronbotSearcher;
import com.jornco.aiironbotdemo.ble.scan.IronbotSearcherCallback;

/**
 * Created by kkopite on 2017/12/25.
 */

public class A8MyBinder extends Binder {

    private A8IronbotSearcher mSearcher = new A8IronbotSearcher();

    public void searchIronbot(IronbotSearcherCallback callback) {
        mSearcher.searchIronbot(callback);
    }

    public void stopScan(){
        mSearcher.stopScan();
    }

    public boolean isEnable(){
        return mSearcher.isEnable();
    }

    public void enable() {
        mSearcher.enable();
    }
}
