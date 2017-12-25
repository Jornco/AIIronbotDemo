package com.jornco.aiironbotdemo.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.jornco.aiironbotdemo.R;
import com.jornco.aiironbotdemo.ble.A1IronbotSearcher;
import com.jornco.aiironbotdemo.ble.device.IronbotInfo;
import com.jornco.aiironbotdemo.ble.scan.IronbotSearcherCallback;

public class A1SearchActivity extends AppCompatActivity implements View.OnClickListener, IronbotSearcherCallback {

    private static final String TAG = "A1SearchActivity";
    private Button mBtnScan;
    private Button mBtnStop;
    private A1IronbotSearcher mSearcher;
    private IronbotInfo mDeviceInfo;
    private TextView mTvDevice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a1_search);
        initView();

        mSearcher = new A1IronbotSearcher();
    }

    private void initView() {
        mBtnScan = (Button) findViewById(R.id.btn_scan);
        mBtnStop = (Button) findViewById(R.id.btn_stop);

        mBtnScan.setOnClickListener(this);
        mBtnStop.setOnClickListener(this);
        mTvDevice = (TextView) findViewById(R.id.tv_device);
        mTvDevice.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_scan:
                if (mSearcher.isEnable()) {
                    mSearcher.searchIronbot(this);
                } else {
                    mSearcher.enable();
                }
                break;
            case R.id.btn_stop:
                mSearcher.stopScan();
                break;
        }
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

    @Override
    protected void onStop() {
        super.onStop();
        if (mSearcher != null) {
            mSearcher.stopScan();
        }
    }
}
