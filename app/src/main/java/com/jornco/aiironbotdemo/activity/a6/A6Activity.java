package com.jornco.aiironbotdemo.activity.a6;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.jornco.aiironbotdemo.R;
import com.jornco.aiironbotdemo.activity.a1.A1IronbotSearcher;
import com.jornco.aiironbotdemo.activity.a5.A5BLEService;
import com.jornco.aiironbotdemo.activity.a5.A5BLESession;
import com.jornco.aiironbotdemo.ble.common.BLEWriterError;
import com.jornco.aiironbotdemo.ble.connect.OnIronbotWriteCallback;
import com.jornco.aiironbotdemo.ble.device.IronbotInfo;
import com.jornco.aiironbotdemo.ble.scan.IronbotSearcherCallback;
import com.jornco.aiironbotdemo.util.IronbotCodeUtil;

import java.util.ArrayList;
import java.util.List;

public class A6Activity extends AppCompatActivity implements View.OnClickListener, IronbotSearcherCallback {

    private static final String TAG = "A6Activity";

    private Button mBtnScan;
    private Button mBtnStop;
    private Button mBtnConn;
    private TextView mTvDevice;

    private A1IronbotSearcher mSearcher;
    private List<A5BLEService> mServiceList;

    private A5BLEService mService;
    private A5BLESession mSession;

    private MediaPlayer mPlayer;
    private Button mBtnSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a6);
        initView();
    }

    private void initView() {
        mBtnScan = (Button) findViewById(R.id.btn_scan);
        mBtnStop = (Button) findViewById(R.id.btn_stop);
        mBtnConn = (Button) findViewById(R.id.btn_conn);
        mTvDevice = (TextView) findViewById(R.id.tv_device);

        mBtnScan.setOnClickListener(this);
        mBtnStop.setOnClickListener(this);
        mBtnConn.setOnClickListener(this);

        mSearcher = new A1IronbotSearcher();
        mServiceList = new ArrayList<>();

        mBtnSend = (Button) findViewById(R.id.btn_send);
        mBtnSend.setOnClickListener(this);
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
                if (mServiceList.isEmpty()) {
                    Toast.makeText(this, "当前没有扫描到设备", Toast.LENGTH_SHORT).show();
                    return;
                }
                mService = mServiceList.get(0);
                mSession = mService.getSession(this);

                // 子线程播放音乐
                new Thread() {
                    @Override
                    public void run() {
                        if (mPlayer != null) {
                            return;
                        }
                        mPlayer = MediaPlayer.create(A6Activity.this, R.raw.music);
                        try {
                            mPlayer.start();
                        } catch (IllegalStateException e) {
                            e.printStackTrace();
                            Log.e(TAG, "error" + e.getMessage());
                        }
                    }
                }.start();

                break;
            case R.id.btn_send:
                if (mSession == null) {
                    return;
                }
                mSession.sendMsg(IronbotCodeUtil.createRandomLED(), new OnIronbotWriteCallback() {
                    @Override
                    public void onWriterSuccess(String address) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(A6Activity.this, "发送成功", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    @Override
                    public void onWriterFailure(String address, BLEWriterError error) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(A6Activity.this, "发送失败", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    @Override
                    public void onAllDeviceFailure() {

                    }

                    @Override
                    public void onWriterEnd() {

                    }
                });
                break;
        }
    }

    @Override
    public void onIronbotFound(IronbotInfo info) {
        Log.e(TAG, "onIronbotFound: " + info.toString());
        for (A5BLEService service : mServiceList) {
            String address = service.getInfo().getAddress();
            if (address != null && address.equals(info.getAddress())) {
                return;
            }
        }
        mServiceList.add(new A5BLEService(info));
    }

    @Override
    public void onIronbotFound(String xml) {

    }

    @Override
    protected void onStop() {
        super.onStop();
        mSearcher.stopScan();
        if (mPlayer != null) {
            mPlayer.stop();
            mPlayer.release();
        }
    }
}
