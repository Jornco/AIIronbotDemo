package com.jornco.aiironbotdemo.activity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.jornco.aiironbotdemo.R;
import com.jornco.aiironbotdemo.ble.device.IronbotInfo;
import com.jornco.aiironbotdemo.ble.scan.IronbotSearcherCallback;
import com.jornco.aiironbotdemo.service.A8MyBinder;
import com.jornco.aiironbotdemo.service.A8MyService;

public class A8SearchActivity extends AppCompatActivity implements View.OnClickListener, IronbotSearcherCallback {

    private static final String TAG = "A8SearchActivity";
    private Button mBtnScan;
    private Button mBtnStop;
    private TextView mTvDevice;

    private IronbotInfo mDeviceInfo;

    private A8MyBinder mBinder;

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mBinder = (A8MyBinder) service;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a8_search);
        initView();
        bindService(new Intent(this, A8MyService.class), mConnection, BIND_AUTO_CREATE);
    }

    private void initView() {
        mBtnScan = (Button) findViewById(R.id.btn_scan);
        mBtnStop = (Button) findViewById(R.id.btn_stop);
        mTvDevice = (TextView) findViewById(R.id.tv_device);

        mBtnScan.setOnClickListener(this);
        mBtnStop.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_scan:
                if (mBinder == null) {
                    return;
                }
                if (mBinder.isEnable()) {
                    mBinder.searchIronbot(this);
                } else {
                    mBinder.enable();
                }
                break;
            case R.id.btn_stop:
                if (mBinder == null) {
                    return;
                }
                mBinder.stopScan();
                break;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mBinder != null) {
            mBinder.stopScan();
        }
        unbindService(mConnection);
    }

    @Override
    public void onIronbotFound(final IronbotInfo info) {
        Log.e(TAG, "onIronbotFound: " + info.toString());
        mDeviceInfo = info;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mTvDevice.setText(info.toString());
            }
        });
    }

    @Override
    public void onIronbotFound(String xml) {

    }
}
