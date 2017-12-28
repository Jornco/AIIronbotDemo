package com.jornco.aiironbotdemo.activity.a22;

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
import com.jornco.aiironbotdemo.ble.IMotor;
import com.jornco.aiironbotdemo.ble.device.IronbotInfo;

public class A22Activity extends AppCompatActivity implements View.OnClickListener {

    private IronbotInfo mDeviceInfo;

    private Button mBtnScan;
    private Button mBtnFind;
    private TextView mTvDevice;

    private IBinder mCallbackBinder;
    private IMotor mMotorProxy;

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mMotorProxy = IMotor.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };
    private Button mBtnMotorInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a22);
        initView();
        bindService(new Intent(this, A20DanceService.class), mConnection, BIND_AUTO_CREATE);
        mCallbackBinder = new ActBinder();
    }

    private void initView() {
        mBtnScan = (Button) findViewById(R.id.btn_scan);
        mBtnFind = (Button) findViewById(R.id.btn_find);
        mTvDevice = (TextView) findViewById(R.id.tv_device);

        mBtnScan.setOnClickListener(this);
        mBtnFind.setOnClickListener(this);
        mBtnMotorInfo = (Button) findViewById(R.id.btn_motor_info);
        mBtnMotorInfo.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_scan:
                if (mMotorProxy == null) {
                    return;
                }
                try {
                    mMotorProxy.startScan(mCallbackBinder);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.btn_find:
                try {
                    int count = mMotorProxy.getServicesCount();
                    if (count != 0) {
                        mMotorProxy.bindService(0);
                    }
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.btn_motor_info:
                try {
                    String xml = mMotorProxy.getMotorInfoXml(0);
                    mTvDevice.setText(xml);
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
            if (code == 0) {
                // callback from search ironbot
                String infoXml = data.readString();
                mDeviceInfo = new IronbotInfo(infoXml);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mTvDevice.setText(mDeviceInfo.toString());
                    }
                });
                return true;
            } else if (code == 1) {
                final String s = data.readString();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mTvDevice.setText(s);
                    }
                });
            }
            return false;
        }
    }
}
