package com.jornco.aiironbotdemo.activity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.jornco.aiironbotdemo.R;
import com.jornco.aiironbotdemo.ble.A7ClientService;
import com.jornco.aiironbotdemo.ble.A7ClientSession;
import com.jornco.aiironbotdemo.ble.A7IronbotSearcher;
import com.jornco.aiironbotdemo.ble.common.BLEWriterError;
import com.jornco.aiironbotdemo.ble.connect.OnIronbotWriteCallback;
import com.jornco.aiironbotdemo.ble.device.IronbotInfo;
import com.jornco.aiironbotdemo.ble.scan.IronbotSearcherCallback;
import com.jornco.aiironbotdemo.util.RobotUtil;

import java.util.ArrayList;
import java.util.List;

public class A7Activity extends AppCompatActivity implements View.OnClickListener, IronbotSearcherCallback {

    private static final String TAG = "A7Activity";
    private Button mBtnScan;
    private Button mBtnStop;
    private Button mBtnConn;
    private TextView mTvDevice;

    private List<A7ClientService> mServiceList;
    private A7ClientService mBLEService;
    private A7ClientSession mBLESession;

    private MediaPlayer mPlayer;

    private A7IronbotSearcher mSearcher;
    private Button mBtnSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a7);
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

        mSearcher = new A7IronbotSearcher();
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
                mBLEService = mServiceList.get(0);
                mBLESession = mBLEService.getSession(this);

                // 子线程播放音乐
                new Thread() {
                    @Override
                    public void run() {
                        if (mPlayer != null) {
                            return;
                        }
                        mPlayer = MediaPlayer.create(A7Activity.this, R.raw.music);
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
                if (mBLESession == null) {
                    return;
                }
                mBLESession.sendMsg(RobotUtil.createRandomLED(), new OnIronbotWriteCallback() {
                    @Override
                    public void onWriterSuccess(String address) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(A7Activity.this, "发送成功", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    @Override
                    public void onWriterFailure(String address, BLEWriterError error) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(A7Activity.this, "发送失败", Toast.LENGTH_SHORT).show();
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
        for (A7ClientService service : mServiceList) {
            String address = service.getInfo().getAddress();
            if (address != null && address.equals(info.getAddress())) {
                return;
            }
        }
        mServiceList.add(new A7ClientService(info));
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
