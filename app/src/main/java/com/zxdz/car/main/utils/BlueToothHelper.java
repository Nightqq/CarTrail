package com.zxdz.car.main.utils;

import android.os.Build;
import android.support.annotation.RequiresApi;

/**
 * Created by Administrator on 2018/5/4.
 * 蓝牙功能类
 */
@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
public class BlueToothHelper extends BlueToothUtils {


    private static BlueToothHelper bluetoothLock;

    private BlueToothHelper() {
        super();
    }


    public static BlueToothHelper getBlueHelp() {
        if (bluetoothLock == null) {
            bluetoothLock = new BlueToothHelper();
        }
        return bluetoothLock;
    }

    //扫描蓝牙
//    public void scannings() {
//        scanning();
//    }

    //通过扫描获取到的蓝牙设备
    public void getDevices(GetblueToothListenter getblue) {
        getDevice(getblue);
    }

    //是否连接状态
    public boolean isCreated() {
        boolean created = isCreateds();
        return created;
    }

    //准备接受卡号消息
    public void setReceiverMode(receiveCardIDListener receivecardid) {
        setReceiverMode1(receivecardid);
    }

    //报警状态取消接受上锁消息,恢复接受上锁消息
    public void giveupCloseMessage(CloseCallPolice closeCall, boolean b) {
        giveupCardID(closeCall, b);
    }

    //连接设备
    public void ConnectedDevices(String address, ConnectedDevicesListenter devicesListenter, boolean isScanning) {
        ConnectedDevice(address, devicesListenter, isScanning);
    }

    //查询锁状态
    public void enquiriesState(EnquiriesStateListenter menquiriesState) {
        enquiriesStates(menquiriesState);
    }

    //准备开锁
    public void openLock(OpenLockListenter mopenLock) {
        openLocks(mopenLock);
    }

    //准备关锁
    public void closeLock(CloseLockListener close) {
        closeLocks(close);
    }

    //设置参数
//    public void setParameter(SetParametersListener Parameters){
//        setParameters(Parameters);
//    }


    //关闭蓝牙
    public void closeAll() {
        closeAlls();
    }

    //注销广播
    public void unRegister() {
        unregist();
    }

    //开启强拆报警
    public void openCallPolices(openCallPoliceListener listener) {
        openCallPolice(listener);
    }
}
