package com.jornco.aiironbotdemo.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.jornco.aiironbotdemo.R;
import com.jornco.aiironbotdemo.ble.A1IronbotSearcher;
import com.jornco.aiironbotdemo.ble.A2IronbotConnect;
import com.jornco.aiironbotdemo.ble.device.IronbotInfo;
import com.jornco.aiironbotdemo.ble.scan.IronbotSearcherCallback;

public class A2SearchActivity extends AppCompatActivity implements View.OnClickListener, IronbotSearcherCallback {

    private static final String TAG = "A2SearchActivity";
    private Button mBtnScan;
    private Button mBtnStop;
    private Button mBtnConn;
    private IronbotInfo mDeviceInfo;

    private A1IronbotSearcher mSearcher;
    private A2IronbotConnect mIronbotConnect;
    private TextView mTvDevice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a2_search);
        initView();

        mSearcher = new A1IronbotSearcher();
        mIronbotConnect = new A2IronbotConnect();
    }

    private void initView() {
        mBtnScan = (Button) findViewById(R.id.btn_scan);
        mBtnStop = (Button) findViewById(R.id.btn_stop);
        mBtnConn = (Button) findViewById(R.id.btn_conn);

        mBtnScan.setOnClickListener(this);
        mBtnStop.setOnClickListener(this);
        mBtnConn.setOnClickListener(this);
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
            case R.id.btn_conn:
                if (mDeviceInfo != null) {
                    mIronbotConnect.connect(this, mDeviceInfo);
                }
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
        mSearcher.stopScan();
    }
}
