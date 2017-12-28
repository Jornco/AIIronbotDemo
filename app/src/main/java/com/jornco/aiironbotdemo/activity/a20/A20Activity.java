package com.jornco.aiironbotdemo.activity.a20;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.jornco.aiironbotdemo.R;
import com.jornco.aiironbotdemo.activity.a20.service.A20DanceService;
import com.jornco.aiironbotdemo.ble.IDance;
import com.jornco.aiironbotdemo.ble.device.IronbotInfo;

public class A20Activity extends AppCompatActivity implements View.OnClickListener {

    private IronbotInfo mDeviceInfo;

    private Button mBtnScan;
    private Button mBtnFind;
    private Button mBtnStartDance;
    private Button mBtnStopDance;
    private TextView mTvDevice;

    private IBinder mCallbackBinder;
    private IDance dancer;

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            dancer = IDance.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a20);
        initView();
        bindService(new Intent(this, A20DanceService.class), mConnection, BIND_AUTO_CREATE);
        mCallbackBinder = new ActBinder();
    }

    private void initView() {
        mBtnScan = (Button) findViewById(R.id.btn_scan);
        mBtnFind = (Button) findViewById(R.id.btn_find);
        mTvDevice = (TextView) findViewById(R.id.tv_device);
        mBtnStartDance = (Button) findViewById(R.id.btn_start_dance);
        mBtnStopDance = (Button) findViewById(R.id.btn_stop_dance);

        mBtnScan.setOnClickListener(this);
        mBtnFind.setOnClickListener(this);
        mBtnStartDance.setOnClickListener(this);
        mBtnStopDance.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_scan:
                if (dancer == null) {
                    return;
                }
                try {
                    dancer.startScan(mCallbackBinder);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.btn_find:
                try {
                    int count = dancer.getServicesCount();
                    if (count != 0) {
                        dancer.bindService(0);
                    }
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.btn_start_dance:
                try {
                    dancer.startDance();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.btn_stop_dance:
                try {
                    dancer.stopDance();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        unbindService(mConnection);
    }

    private class ActBinder extends Binder {

        @Override
        protected boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            return super.onTransact(code, data, reply, flags);
        }
    }
}
