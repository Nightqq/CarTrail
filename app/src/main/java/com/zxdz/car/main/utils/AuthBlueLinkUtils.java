package com.zxdz.car.main.utils;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.widget.Toast;

import com.zxdz.car.main.view.lock.BluetoothLock;

/**
 * Created by Administrator on 2018\1\17 0017.
 */
@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
public class AuthBlueLinkUtils {
    private Context mContext;
    private static AuthBlueLinkUtils authBlueHelp;
    private BluetoothAdapter mBluetoothAdapter;
    private String My_Address = "10:00:00:00:20:52";
    private BluetoothDevice selectDevice;
    private BluetoothGatt bluetoothGatt;

    private AuthBlueLinkUtils(Context context) {
        mContext = context;
        openBluetooth();
    }

    public static AuthBlueLinkUtils getAuthBlueHelp(Context context) {
        if (authBlueHelp!=null){
            authBlueHelp = new AuthBlueLinkUtils(context);
        }
        return authBlueHelp;
    }

    /*//连接设备
    public void ConnectedDevices(String address, ConnectedDevicesListenter devicesListenter) {
        try {
            connectedDevicesListenter = devicesListenter;
            selectDevice = mBluetoothAdapter.getRemoteDevice(My_Address);
            bluetoothGatt = selectDevice.connectGatt(mContext, false, mGattCallback);//开始连接
        } catch (IllegalArgumentException e) {
            return;
        }
    }*/
    /*private final BluetoothGattCallback mGattCallback = new BluetoothGattCallback() {
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
                    if (statue) {
                        if (read_characteristic != null) {
                            LogUtils.a("设置notify");
                            setCharacteristicNotification(read_characteristic, true);
                        }
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
            if (mBluetoothAdapter == null || bluetoothGatt == null) {
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
                    LogUtils.a("连接成功");
                    connectedDevicesListenter.connectenDevice("连接成功");
                    return;
                }
            }
        }
    };
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
        }
    }*/


    private void openBluetooth() {
        if (mBluetoothAdapter == null) {
            mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        }
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


    private ConnectedDevicesListenter connectedDevicesListenter;

    public interface ConnectedDevicesListenter {
        void connectenDevice(String str);
    }

    private BluetoothLock.OpenLockListenter openLock;
    public interface OpenLockListenter {
        void openLock(String str);
    }


}
