package com.zxdz.car.main.utils;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.widget.Toast;

/**
 * Created by Administrator on 2018\1\2 0002.
 */

@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
public class NewBlueLockUtils {

    private static NewBlueLockUtils newBlueLockUtils;
    private Context mContext;
    private BluetoothAdapter mBluetoothAdapter;
    private BluetoothDevice selectDevice;
    private BluetoothGatt bluetoothGatt;

    private NewBlueLockUtils(Context context) {
        mContext = context;
        openBluetooth();
    }
    public static NewBlueLockUtils getBlueHelp(Context context) {
        if (newBlueLockUtils == null) {
            newBlueLockUtils = new NewBlueLockUtils(context);
        }
        return newBlueLockUtils;
    }

    //扫码连接设备

    public void ConnectedDevices(String address) {
        selectDevice = mBluetoothAdapter.getRemoteDevice(address);//开始连接
        bluetoothGatt = selectDevice.connectGatt(mContext, false, mGattCallback);
    }
    private final BluetoothGattCallback mGattCallback = new BluetoothGattCallback() {




    };


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



}
