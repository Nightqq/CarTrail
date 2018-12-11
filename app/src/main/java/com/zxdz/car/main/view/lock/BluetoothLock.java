package com.zxdz.car.main.view.lock;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothProfile;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.widget.Toast;

import com.blankj.utilcode.util.LogUtils;
import com.zxdz.car.base.utils.SwitchUtils;
import com.zxdz.car.main.model.domain.LockInfo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Lenovo on 2017/11/10.
 */
@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
public class BluetoothLock {
    private BluetoothDevice device;
    private Context mContext;
    private BluetoothAdapter mBluetoothAdapter;
    private BluetoothGatt bluetoothGatt;
    private BluetoothGattCharacteristic writer_characteristic;
    private BluetoothGattCharacteristic read_characteristic;
    private boolean statue;
    public static byte key = -1;
    private static BluetoothLock bluetoothLock;
    private BluetoothDevice selectDevice;
    private IntentFilter mFilter;
    private List<byte[]> arrayLists;
    private String My_Address = "10:00:00:00:20:52";
    private static boolean flag = false;//蓝牙是否连接标志

    private BluetoothLock(Context context) {
        mContext = context;
        key = -1;
        openBluetooth();
        regist();
        scanning();
    }

    public static BluetoothLock getBlueHelp(Context context) {
        if (bluetoothLock == null) {
            bluetoothLock = new BluetoothLock(context);
        }
        return bluetoothLock;
    }

    public void scanagain() {
        if (mBluetoothAdapter.isEnabled()) { //通过适配器对象调用isEnabled()方法，判断蓝牙是否打开了
            if (mBluetoothAdapter.isDiscovering()) {
                mBluetoothAdapter.cancelDiscovery();
            }
            mBluetoothAdapter.startDiscovery();
        } else {
            openBluetooth();
        }
    }

    public boolean isCreated() {
        if (bluetoothGatt != null) {
            return true;
        }
        return false;
    }

    //扫描设备返回蓝牙搜索到的设备，
    public BluetoothDevice getScannedBluetooth(GetblueToothListenter getblueToothListenter) {
        getblue = getblueToothListenter;
        return device;
    }

    //连接设备
    public void ConnectedDevices(String address, ConnectedDevicesListenter devicesListenter) {
        try {
            lianjie = false;
            connectedDevicesListenter = devicesListenter;
            selectDevice = mBluetoothAdapter.getRemoteDevice(My_Address);
            LogUtils.a("开始连接");
            bluetoothGatt = selectDevice.connectGatt(mContext, false, mGattCallback);//开始连接
        } catch (IllegalArgumentException e) {
            return;
        }

    }

    //查询锁是否关闭
    public void enquiriesState(EnquiriesStateListenter menquiriesState) {
        enquiriesState = menquiriesState;
        if (key != -1 && mBluetoothAdapter != null && bluetoothGatt != null) {
            byte[] bytes = SwitchUtils.enquiriesLock(key);
            writer_characteristic.setValue(bytes);
            statue = bluetoothGatt.writeCharacteristic(writer_characteristic);
            return;
        } else {
            enquiriesState.enquiriesState("请连接设备");
        }
    }

    //开锁
    public void openLock(final OpenLockListenter mopenLock) {
        openLock = mopenLock;
        /*if (!flag) {
            ConnectedDevices(My_Address, new ConnectedDevicesListenter() {
                @Override
                public void connectenDevice(String str) {
                    if (str.equals("连接成功")) {
                        openLock(openLock);
                    }
                    return;
                }
            });
            return;
        }*/
        LogUtils.a("准备开锁");
        if (key == -1) {
            LogUtils.a("key == -1");
        }
        if (key != -1 && mBluetoothAdapter != null && bluetoothGatt != null) {
            LogUtils.a("写开锁命令");
            byte[] bytes = SwitchUtils.openLock(key);
            writer_characteristic.setValue(bytes);
            statue = bluetoothGatt.writeCharacteristic(writer_characteristic);
            return;
        } else {
            LogUtils.a("异常");
            SharedPreferences sp = mContext.getSharedPreferences("activity", Context.MODE_WORLD_READABLE);
            String My_address = sp.getString("My_address", "");
            ConnectedDevices(My_address, new ConnectedDevicesListenter() {
                @Override
                public void connectenDevice(String str) {
                    openLock(mopenLock);
                }
            });
        }
    }

    //close
    public void closeAll() {
        key = -1;
        if (bluetoothGatt != null) {
            LogUtils.a("开始断开");
            bluetoothGatt.disconnect();
            bluetoothGatt.close();
        }
        if (mBluetoothAdapter.isEnabled()) {
            mBluetoothAdapter.disable();//关闭蓝牙
        }
        if (bluetoothLock != null) {
            bluetoothLock = null;
        }
        if (mBluetoothAdapter != null) {
            mBluetoothAdapter = null;
        }
        if (read_characteristic != null) {
            read_characteristic = null;
        }
        if (read_characteristic != null) {
            read_characteristic = null;
        }
        if (selectDevice != null) {
            selectDevice = null;
        }
        if (arrayLists != null) {
            arrayLists = null;
        }
        if (arrayLists != null) {
            arrayLists = null;
        }
    }

    private final BluetoothGattCallback mGattCallback = new BluetoothGattCallback() {
        @Override//当连接上设备或者失去连接时会回调该函数
        public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
            if (newState == BluetoothProfile.STATE_CONNECTED) { //连接成功

                bluetoothGatt.discoverServices(); //连接成功后就去找出该设备中的服务 private BluetoothGatt mBluetoothGatt;
                LogUtils.a("连接成功");
            } else if (newState == BluetoothProfile.STATE_DISCONNECTED) {  //连接失败
                LogUtils.a("连接失败");//重新连接
            }
        }

        @Override //当设备是否找到服务时，会回调该函数
        public void onServicesDiscovered(BluetoothGatt gatt, int status) {
            LogUtils.a("找到服务");
            if (status == BluetoothGatt.GATT_SUCCESS) {
                LogUtils.a("GATT_SUCCESS");
                List<BluetoothGattService> gattServices = getSupportedGattServices();
                for (BluetoothGattService gattService : gattServices) {// 遍历出gattServices里面的所有服务
                    List<BluetoothGattCharacteristic> gattCharacteristics = gattService.getCharacteristics();
                    for (final BluetoothGattCharacteristic gattCharacteristic : gattCharacteristics) {// 遍历每条服务里的所有Characteristic
                        if (gattCharacteristic.getUuid().toString().equalsIgnoreCase(LockInfo.Read_UUID.toString())) {
                            read_characteristic = gattCharacteristic;
                            LogUtils.a("read_characteristic");
                        }
                        if (gattCharacteristic.getUuid().toString().equalsIgnoreCase(LockInfo.Write_UUID.toString())) {
                            writer_characteristic = gattCharacteristic;
                        }
                    }
                }
                LogUtils.a("" + key);
                if (key == -1) {
                    LogUtils.a("key == -1");
                    byte[] bytes = SwitchUtils.xor34(LockInfo.bytes_key);

                    writer_characteristic.setValue(bytes);
                    statue = bluetoothGatt.writeCharacteristic(writer_characteristic);
                    LogUtils.a("" + statue);
                    if (read_characteristic != null) {
                        LogUtils.a("设置notify");
                        setCharacteristicNotification(read_characteristic, true);
                    }
                }
            } else {
                LogUtils.a("服务读失败");
            }
        }

        //要设置一下可以接收通知Notifaction
        public void setCharacteristicNotification(BluetoothGattCharacteristic characteristic, boolean enabled) {
            LogUtils.a("setCharacteristicNotification");
            if (mBluetoothAdapter == null || bluetoothGatt == null) {
                LogUtils.a("mBluetoothAdapter == null || bluetoothGatt == null");
                return;
            }
            bluetoothGatt.setCharacteristicNotification(characteristic, enabled);
            BluetoothGattDescriptor descriptor = characteristic.getDescriptor(LockInfo.Descirptor_UUID);
            if (descriptor != null) {
                LogUtils.a("descriptor !=  null");
                descriptor.setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
                bluetoothGatt.writeDescriptor(descriptor);
            } else {
                LogUtils.a("descriptor == null");
            }
        }

        @Override//设备发出通知时会调用到该接口
        public void onCharacteristicChanged(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic) {
            LogUtils.a("onCharacteristicChanged");
            if (mBluetoothAdapter == null || bluetoothGatt == null) {
                LogUtils.a("mBluetoothAdapter == null || bluetoothGatt == null");
                return;
            }
            LogUtils.a("收到通知");
            byte[] value = characteristic.getValue();
            if (value != null) {
                byte[] getbyte = SwitchUtils.getbyte(value);
                parse(getbyte);
                //解析命令
                if (key == -1) {
                    key = getbyte[2];
                    LogUtils.a("连接成功",key);
                    connectedDevicesListenter.connectenDevice("连接成功");
                    return;
                }
            }
        }
    };

    private List<BluetoothGattService> getSupportedGattServices() {
        if (bluetoothGatt == null)
            return null;
        return bluetoothGatt.getServices();   //此处返回获取到的服务列表
    }

    //解析返回的命令
    private void parse(final byte[] bytes1) {
        boolean ishave = byteCollection(bytes1);
        if (ishave) {
            return;
        }
        switch (bytes1[3]) {
            case 0x21:
                if (bytes1[5] == 0x00) {
                    openLock.openLock("开锁成功");
                }
                if (bytes1[5] == 0x01) {
                    openLock.openLock("开锁失败");
                }
                break;
            case 0x22:
                if (bytes1[5] == 0x00) {
                    openLock.openLock("上锁成功");
                }
                if (bytes1[5] == 0x01) {
                    openLock.openLock("上锁失败");
                }
                break;
            case 0x31:
                final String s = Integer.toString(bytes1[6] & 0xFF);
                LogUtils.a("电量：" + s);
                if (bytes1[5] == 0x00) {
                    enquiriesState.enquiriesState("锁状态：开");
                }
                if (bytes1[5] == 0x01) {
                    enquiriesState.enquiriesState("锁状态：关");
                }
                break;
        }
    }

    private boolean byteCollection(byte[] mBytes) {
        if (arrayLists == null) {
            arrayLists = new ArrayList<>();
            arrayLists.add(mBytes);
            return false;
        }
        for (byte[] arrayList : arrayLists) {
            if (Arrays.equals(arrayList, mBytes)) {
                return true;
            }
        }

        return false;
    }


    private void openBluetooth() {
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (!mBluetoothAdapter.isEnabled()) {
            boolean enable = mBluetoothAdapter.enable(); //返回值表示 是否成功打开了蓝牙设备
            if (enable) {
                Toast.makeText(mContext, "打开蓝牙功能成功！", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(mContext, "打开蓝牙功能失败，请到'系统设置'中手动开启蓝牙功能！", Toast.LENGTH_SHORT).show();
                return;
            }
        }
    }

    private void regist() {
        IntentFilter mFilter = new IntentFilter();
        mFilter.addAction(BluetoothDevice.ACTION_FOUND);//蓝牙查询,可以在reciever中接受查询到的蓝牙设备
        mFilter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED); // 注册搜索完时的receiver
        mFilter.addAction(BluetoothDevice.ACTION_BOND_STATE_CHANGED);//蓝牙连接状态发生改变时,接收状态
        mContext.registerReceiver(mReceiver, mFilter);
    }

    public void unregist() {
        if (mReceiver != null) {
            mContext.unregisterReceiver(mReceiver);
        }
    }
    private boolean lianjie = true;
    public BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(BluetoothDevice.ACTION_FOUND)) {
                device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);//通过此方法获取搜索到的蓝牙设备
                if (device == null) {
                    return;
                }
                String name = device.getName();
                if (name == null) {
                    return;
                }
                LogUtils.i("bluetoothname:",name+":"+device.getAddress());
                if (!name.equals("OmniLock")) {
                    return;
                }
                if (lianjie) {
                    Toast.makeText(context, "搜索到蓝牙设备", Toast.LENGTH_SHORT).show();
  //            new AudioPlayUtils(context, R.raw.qdlgjxzlys).play();
                }
                //getblue.getblueTooth(device);
            } else if (action.equals(BluetoothAdapter.ACTION_DISCOVERY_FINISHED)) {
                Toast.makeText(context, "蓝牙扫描结束", Toast.LENGTH_SHORT).show();
            }
            if (BluetoothAdapter.ACTION_STATE_CHANGED.equals(action)) {
                if (intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, -1)
                        == BluetoothAdapter.STATE_OFF) {
                }
            }
        }
    };

    private void scanning() {
        if (mBluetoothAdapter.isEnabled()) { //通过适配器对象调用isEnabled()方法，判断蓝牙是否打开了
            if (mBluetoothAdapter.isDiscovering()) {
                mBluetoothAdapter.cancelDiscovery();
            }
            //Toast.makeText(mContext, "开始扫描", Toast.LENGTH_SHORT).show();
            mBluetoothAdapter.startDiscovery();
        } else {
            openBluetooth();
        }
    }

    private GetblueToothListenter getblue;

    public interface GetblueToothListenter {
        void getblueTooth(BluetoothDevice device);
    }

    private ConnectedDevicesListenter connectedDevicesListenter;

    public interface ConnectedDevicesListenter {
        void connectenDevice(String str);
    }

    private OpenLockListenter openLock;

    public interface OpenLockListenter {
        void openLock(String str);
    }

    private EnquiriesStateListenter enquiriesState;

    public interface EnquiriesStateListenter {
        void enquiriesState(String str);
    }
}
