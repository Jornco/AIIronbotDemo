package com.jornco.aiironbotdemo.activity.a5;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothProfile;
import android.content.Context;

import com.jornco.aiironbotdemo.ble.common.BLELog;
import com.jornco.aiironbotdemo.ble.common.BLEWriterError;
import com.jornco.aiironbotdemo.ble.common.IronbotCode;
import com.jornco.aiironbotdemo.ble.connect.ConnectedWriterStrategy;
import com.jornco.aiironbotdemo.ble.connect.DisconnectedWriterStrategy;
import com.jornco.aiironbotdemo.ble.connect.IWriterStrategy;
import com.jornco.aiironbotdemo.ble.connect.IronbotWriterCallback;
import com.jornco.aiironbotdemo.ble.connect.MultiIronbotWriterCallback;
import com.jornco.aiironbotdemo.ble.connect.OnIronbotWriteCallback;
import com.jornco.aiironbotdemo.ble.device.BLEState;
import com.jornco.aiironbotdemo.ble.device.IronbotInfo;
import com.jornco.aiironbotdemo.ble.scan.IronbotRule;

import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by kkopite on 2017/12/24.
 */

public class A5BLEService extends BluetoothGattCallback implements MultiIronbotWriterCallback.OnSendListener {

    private IronbotInfo mInfo;
    private String mAddress;
    private BluetoothGatt mGatt;
    private BluetoothAdapter mAdapter;
    private ConcurrentLinkedQueue<WriteData> queueDatas = new ConcurrentLinkedQueue<>();

    // 连接时读写处理
    private IWriterStrategy mConnectedStrategy;
    // 断开时读写处理
    private IWriterStrategy mDisconnectedStrategy;
    // 当前的读写处理
    private IWriterStrategy mCurrentStrategy;

    private IronbotRule mRule = new IronbotRule() {

        final static String VERSION1_UUID_WRITER = "0000ffe9";
        final static String VERSION1_UUID_READ = "0000ffe1";

        final static String VERSION2_UUID_WRITER = "0000fff1";
        final static String VERSION2_UUID_READ = "0000fff4";

        @Override
        public boolean isRead(String uuid) {
            return uuid.startsWith(VERSION1_UUID_READ)
                    || uuid.startsWith(VERSION2_UUID_READ);
        }

        @Override
        public boolean isWrite(String uuid) {
            return uuid.startsWith(VERSION1_UUID_WRITER)
                    || uuid.startsWith(VERSION2_UUID_WRITER);
        }
    };
    private volatile boolean isWriting = false;

    public IronbotInfo getInfo() {
        return mInfo;
    }

    @Override
    public void onEndSend() {
        isWriting = false;
        next();
    }

    @Override
    public void onUnSendDevices(int total, int device) {

    }


    private static class WriteData {
        // 要发的数据
        private String data;
        // 要发的地址
        private String[] address;
        // 完成的回调
        private IronbotWriterCallback callback;
    }

    public A5BLEService(IronbotInfo info) {
        mInfo = info;
        mAddress = mInfo.getAddress();
        mAdapter = BluetoothAdapter.getDefaultAdapter();
        mConnectedStrategy = new ConnectedWriterStrategy(mAddress);
        mDisconnectedStrategy = new DisconnectedWriterStrategy(mAddress);
        mCurrentStrategy = mDisconnectedStrategy;
    }

    public A5BLESession getSession(Context context) {
        String address = mInfo.getAddress();
        mGatt = getConnection(context, address);
        if (mGatt == null) {
            return null;
        }
        return new A5BLESession(mGatt, this);
    }

    private BluetoothGatt getConnection(Context context, String address) {
        BluetoothDevice device = mAdapter.getRemoteDevice(address);
        if (device == null) {
            BLELog.log("Device not found Unable to connect");
            mGatt = null;
        } else {
            mGatt = device.connectGatt(context, false, this);
            if (!mGatt.connect()) {
                mGatt = null;
            }
        }
        return mGatt;
    }

    /**
     * 发送消息, 需要上层做同步
     * @param address   地址
     * @param cmd       指令
     * @param callback  回调
     */
    void sendMsg(String address, String cmd, IronbotWriterCallback callback) {
        this.writeData(cmd, callback);
    }

    public void sendMsg(IronbotCode code, OnIronbotWriteCallback callback) {
        this.sendMsg(new String[]{mAddress}, code, callback);
    }

//    /**
//     * 该指定设备发送指令
//     * @param address   地址
//     * @param code      指令
//     * @param callback  回调
//     */
//    public void sendMsg(String address, IronbotCode code, OnIronbotWriteCallback callback) {
//        sendMsg(new String[]{address}, code, callback);
//    }

    /**
     * 给指定的设备发送指令
     * @param address   地址
     * @param code      指令
     * @param callback  回调
     */
    public void sendMsg(String[] address, final IronbotCode code, final OnIronbotWriteCallback callback) {
        List<String> codes = code.getCodes();
        int size = address.length;
        if (size == 0) {
            if (callback != null) {
                callback.onWriterFailure("", new BLEWriterError("", "", "没有要发送的设备地址或者没有连接的设备"));
                callback.onAllDeviceFailure();
                callback.onWriterEnd();
            }
            return;
        }
        // 确保一条完整的指令
        synchronized (this) {
            for (int i = 0, length = codes.size(); i < length; i++) {
                WriteData data = new WriteData();
                data.address = address;
                data.data = codes.get(i);
                if (i == length - 1) {
                    // 分割的最后一条指令发完才算这条指令发送完毕, 才会去执行用户传进来的回调
                    data.callback = new MultiIronbotWriterCallback(callback, this, size);
                } else {
                    data.callback = new MultiIronbotWriterCallback(null, this, size);
                }
                queueDatas.add(data);
            }
        }
        next();
    }

    /**
     * 尝试从发送队列拉出一个指令来发送
     */
    private void next() {
        synchronized (this) {
            if (isWriting) {
                BLELog.log("当前有任务还在执行, 队列中还有" + queueDatas.size() + "个任务");
                return;
            }
            isWriting = true;
        }
        WriteData data = queueDatas.poll();
        if (data != null) {
            String[] address = data.address;
            for (String a : address) {
                sendMsg(a, data.data, data.callback);
            }
        } else {
            isWriting = false;
            if (!queueDatas.isEmpty()) {
                next();
            }
        }
    }

    @Override
    public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
        super.onConnectionStateChange(gatt, status, newState);
        if (newState == BluetoothProfile.STATE_CONNECTED) {
            BLELog.log(mAddress + "连接成功");
            boolean res = gatt.discoverServices();
            BLELog.log("发现服务" + res);
            changeState(BLEState.CONNECTED);
            return;
        } else {
            BLELog.log(mAddress + "连接断开");
            changeState(BLEState.DISCONNECT);
            mGatt.close();
            destroy();
        }
        switchWriterToDisconnect();
    }

    @Override
    public void onCharacteristicRead(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
        super.onCharacteristicRead(gatt, characteristic, status);
    }

    @Override
    public void onCharacteristicWrite(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
        super.onCharacteristicWrite(gatt, characteristic, status);
        if (status == BluetoothGatt.GATT_SUCCESS) {
            BLELog.log("写入成功");
            mCurrentStrategy.writeSuccess();
        } else {
            BLELog.log("错误代码: " + status);
            mCurrentStrategy.writeFailure();
        }
    }

    @Override
    public void onCharacteristicChanged(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic) {
        super.onCharacteristicChanged(gatt, characteristic);
//        byte[] bytes = mReadBGC.getValue();
//        String msg = new String(bytes);
//        mDeviceStateChangeListener.bleDeviceReceive(address, msg);
//        BLELog.log("收到设备传来的: " + msg);
    }

    @Override
    public void onServicesDiscovered(BluetoothGatt gatt, int status) {
        super.onServicesDiscovered(gatt, status);
        if (status == BluetoothGatt.GATT_SUCCESS) {
            List<BluetoothGattService> bgss = mGatt.getServices();
            for (BluetoothGattService bgs : bgss) {
                BLELog.log("find BluetoothGattService : " + bgs.getUuid().toString());
                List<BluetoothGattCharacteristic> bgcs = bgs.getCharacteristics();
                for (BluetoothGattCharacteristic bgc : bgcs) {
                    String uuid = bgc.getUuid().toString();
                    if (mRule.isRead(uuid)) {
                        BLELog.log("getRead BluetoothGattCharacteristic : " + uuid);
//                        mReadBGC = bgc;
                        boolean b = mGatt.setCharacteristicNotification(bgc, true);
                        BLELog.log("监听: " + b);
                    }
                    if (mRule.isWrite(uuid)) {
                        BLELog.log("getWrite BluetoothGattCharacteristic : " + uuid);
                        switchWriterToConnect(mGatt, bgc);
                    }
                }
            }
        }
    }
    /**
     * 切换至连接的读写状态
     * @param gatt gatt
     * @param bgc  可读的一个属性
     */
    private void switchWriterToConnect(BluetoothGatt gatt, BluetoothGattCharacteristic bgc){
        synchronized (this){
            mCurrentStrategy.stop();
            boolean b = gatt.setCharacteristicNotification(bgc, true);
            BLELog.log("监听: " + b);
            mCurrentStrategy = mConnectedStrategy;
            mCurrentStrategy.start(gatt, bgc);
        }
    }

    /**
     * 切换至断开的读写状态
     */
    private void switchWriterToDisconnect(){
        synchronized (this){
            mCurrentStrategy.stop();
            mCurrentStrategy = mDisconnectedStrategy;
            mCurrentStrategy.start(null, null);
        }
    }

    /**
     * 对设备进行读写
     * @param data      指令
     * @param callback  回调
     */
    void writeData(String data, IronbotWriterCallback callback) {
        mCurrentStrategy.write(data, callback);
    }

    /**
     * 断开连接
     */
    void disconnect() {
        if (mGatt == null) {
            return;
        }
        mGatt.disconnect();
    }

    /**
     * 销毁
     */
    private void destroy() {
//        mReadBGC = null;
        mGatt = null;
//        mDeviceStateChangeListener = null;
    }

    /**
     * 连接状态的改变
     * @param state state
     */
    private void changeState(BLEState state) {
//        mState = state;
//        mDeviceStateChangeListener.bleDeviceStateChange(address, state);
    }


}
