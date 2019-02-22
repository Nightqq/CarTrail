package com.zxdz.car.main.utils;

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
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.view.WindowManager;
import android.widget.Toast;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.Utils;
import com.zxdz.car.App;
import com.zxdz.car.R;
import com.zxdz.car.base.utils.AudioPlayUtils;
import com.zxdz.car.base.utils.SwitchUtils;
import com.zxdz.car.base.view.BaseActivity;
import com.zxdz.car.main.model.domain.Constant;
import com.zxdz.car.main.model.domain.LockInfo;
import com.zxdz.car.main.view.lock.OpenLockActivity;

import java.util.Arrays;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by Administrator on 2018/5/4.
 */
@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
public class BlueToothUtils {
    private BluetoothDevice device;
    private Context mContext;
    private BluetoothAdapter mBluetoothAdapter;
    private BluetoothGatt bluetoothGatt;
    private BluetoothGattCharacteristic writer_characteristic;
    private BluetoothGattCharacteristic read_characteristic;
    private boolean statue;
    public static byte key = -1;
    private BluetoothDevice selectDevice;
    private byte[] flagbyte = new byte[1];
    private String mAddress;
    private final SharedPreferences sp;
    private boolean flag12 = true;
    private int flag = 1;
    private boolean isClosed = false;
    private boolean stateCallPolice = false;
    private SweetAlertDialog initDialog;
    private boolean isLoop = true;//是否在循环连接中

    BlueToothUtils() {
        mContext = Utils.getContext();
        key = -1;
        openBluetooth();
        //regist();
        sp = mContext.getSharedPreferences("spUtils", Context.MODE_PRIVATE);
    }

    public void getDevice(GetblueToothListenter gbl) {
        getblue = gbl;
    }

    public boolean isCreateds() {
        if (bluetoothGatt != null) {
            return true;
        }
        return false;
    }

    //设置为可以接收刷卡信息模式
    public void setReceiverMode1(receiveCardIDListener r) {
        receivecardid = r;
        flagbyte[0] = 0x43;
    }

    public void setflagbyte() {
        flagbyte[0] = 0x46;
    }

    ;

    //报警模式不接受锁消息
    public void giveupCardID(CloseCallPolice closeCall, boolean state) {
        this.closeCall = closeCall;
        stateCallPolice = state;
        if (isClosed) {
            closeLock.closedLock("上锁成功");
            isClosed = false;
        }
    }

    //连接设备
    public void ConnectedDevice(String address, ConnectedDevicesListenter devicesListenter, boolean isScaning) {
        isLoop = true;
        if (isScaning) {
            mAddress = address;
            SharedPreferences.Editor edit = sp.edit();
            edit.putString("macaddress", address);
            edit.commit();
        } else {
            mAddress = sp.getString("macaddress", "111");
        }
        connectedDevicesListenter = devicesListenter;
        handler.removeCallbacks(runnable1);
        handler.postDelayed(runnable1, 6000);
        try {
            if (!mBluetoothAdapter.isEnabled()) {
                mBluetoothAdapter.enable();
            } else {
                selectDevice = mBluetoothAdapter.getRemoteDevice(mAddress);
                LogUtils.a("开始连接");
                if (bluetoothGatt != null) {
                    bluetoothGatt.close();
                }
                bluetoothGatt = selectDevice.connectGatt(mContext, false, mGattCallback);//开始连接
            }
        } catch (IllegalArgumentException e) {
            return;
        }
    }

    //查询锁是否关闭
    public void enquiriesStates(EnquiriesStateListenter menquiriesState) {
        enquiriesState = menquiriesState;
        if (mBluetoothAdapter != null && bluetoothGatt != null) {
            byte[] bytes = SwitchUtils.enquiriesLock();
            flagbyte[0] = 0x52;
            writer_characteristic.setValue(bytes);
            statue = bluetoothGatt.writeCharacteristic(writer_characteristic);
            return;
        } else {
            enquiriesState.enquiriesState("请连接控制器");
        }
    }

    //开锁
    public void openLocks(final OpenLockListenter mopenLock) {
        openLock = mopenLock;
        if (mBluetoothAdapter != null && bluetoothGatt != null) {
            byte[] bytes = SwitchUtils.openLock();
            flagbyte[0] = 0x55;
            writer_characteristic.setValue(bytes);
            statue = bluetoothGatt.writeCharacteristic(writer_characteristic);
            return;
        } else {
            checkOpenLock("蓝牙连接中断或未打开，自动重连中...");
            ConnectedDevice(mAddress, new ConnectedDevicesListenter() {
                @Override
                public void connectenDevice(int i) {
                    openLocks(mopenLock);
                }
            }, false);
        }
    }

    //关闭锁
    public void closeLocks(CloseLockListener close) {
        closeLock = close;
        if (mBluetoothAdapter != null && bluetoothGatt != null) {
            byte[] bytes = SwitchUtils.closeLock();
            flagbyte[0] = 0x4C;
            writer_characteristic.setValue(bytes);
            statue = bluetoothGatt.writeCharacteristic(writer_characteristic);
//            if (statue) {
//                closeLock.closeable("关锁命令发送成功");
//            }
        } else {
            closeLock.closeable("请连接控制器");
        }
    }

    //设置参数
    public void setParameters(int i, SetParametersListener Parameters) {
        if (flag12) {
            flag12 = false;
            setParameters = Parameters;
            if (mBluetoothAdapter != null && bluetoothGatt != null) {
                byte[] bytes = SwitchUtils.setParameters(i);
                flagbyte[0] = 0x50;
                writer_characteristic.setValue(bytes);
                statue = bluetoothGatt.writeCharacteristic(writer_characteristic);
                if (statue) {
                    setParameters.setParameter("参数设置完成");
                }
            } else {
                setParameters.setParameter("请连接控制器");
                flag12 = true;
            }
        } else {
            return;
        }
    }

    public void setparams() {
        int type = sp.getInt(Constant.READ_CARD_TYPE, 0);
        setParameters(type, new SetParametersListener() {
            @Override
            public void setParameter(String str) {
                if (str.equals("设置参数成功")) {
                }
            }
        });
    }

    private Runnable runnable1 = new Runnable() {
        @Override
        public void run() {
            ConnectedDevice(mAddress, connectedDevicesListenter, false);
        }
    };
    private Runnable runnable2 = new Runnable() {
        @Override
        public void run() {
            checkOpenLock("智能锁连接断开，重新连接中。。。");
            ConnectedDevice(mAddress, new ConnectedDevicesListenter() {
                @Override
                public void connectenDevice(int i) {

                }
            }, false);
        }
    };

    private final BluetoothGattCallback mGattCallback = new BluetoothGattCallback() {
        @Override//当连接上设备或者失去连接时会回调该函数
        public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
            if (newState == BluetoothProfile.STATE_CONNECTED) { //连接成功
                bluetoothGatt.discoverServices(); //连接成功后就去找出该设备中的服务 private BluetoothGatt mBluetoothGatt;
                if (mBluetoothAdapter.isDiscovering()) {
                    mBluetoothAdapter.cancelDiscovery();
                }
                handler.removeCallbacks(runnable1);
                handler1.removeCallbacks(runnable2);
                isLoop = false;//连接成功，断开就会重连
                LogUtils.a("连接成功");
                closeCallPloiceAudio();
                if (null != initDialog && initDialog.isShowing()) {
                    initDialog.dismiss();
                }
            } else if (newState == BluetoothProfile.STATE_DISCONNECTED) {  //断开连接
                gatt.close();
                bluetoothGatt = null;
                OpenLockActivity.iscon = false;
                LogUtils.a("连接断开" + isLoop);
                //ToastUtils.showShort("蓝牙断开连接");
                if (handler1 != null && !isLoop) {
                    AudioPlayUtils.getAudio(mContext, R.raw.lyljdkbj).play(true);
                    BaseActivity.intent.callPolice(1, "蓝牙异常断开");
                    //LogUtils.a("自动重连中。。。");
                    handler1.postDelayed(runnable2, 3000);
                }
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
                //民警拿到锁后自动把锁设置为打开状态
                byte[] bytes = SwitchUtils.openLock();
                writer_characteristic.setValue(bytes);
                bluetoothGatt.writeCharacteristic(writer_characteristic);

                connectedDevicesListenter.connectenDevice(1);
                if (read_characteristic != null) {
                    LogUtils.a("设置notify");
                    setCharacteristicNotification(read_characteristic, true);
                }
            } else {
                LogUtils.a("服务读失败");
            }
        }

        //要设置一下可以接收通知Notifaction
        public void setCharacteristicNotification(BluetoothGattCharacteristic characteristic, boolean enabled) {
            if (mBluetoothAdapter == null || bluetoothGatt == null) {
                return;
            }
            bluetoothGatt.setCharacteristicNotification(characteristic, enabled);
            BluetoothGattDescriptor descriptor = characteristic.getDescriptor(LockInfo.Descirptor_UUID);
            if (descriptor != null) {
                descriptor.setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
                bluetoothGatt.writeDescriptor(descriptor);
            } else {
                LogUtils.a("descriptor == null");
            }
        }

        @Override//设备发出通知时会调用到该接口
        public void onCharacteristicChanged(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic) {
            if (mBluetoothAdapter == null || bluetoothGatt == null) {
                LogUtils.a("BlueToothUtils", "设备连接断开");
                return;
            }
            byte[] value = characteristic.getValue();
            if (stateCallPolice) {
                LogUtils.a("tagggg", value[1] + "");
                if (value[1] == 0x46) {
                    isClosed = true;
                } else if (value[1] == 0x43) {
                    closeCall.closeCallPolice();
                }
                return;
            }
            if (value != null) {
                //LogUtils.a("收到通知", flagbyte[0] + " q " + value[1]);
                parse(value);
            }
        }
    };

    private List<BluetoothGattService> getSupportedGattServices() {
        if (bluetoothGatt == null)
            return null;
        return bluetoothGatt.getServices();   //此处返回获取到的服务列表
    }

    //解析返回的命令
    private synchronized void parse(final byte[] bytes1) {
        switch (flagbyte[0]) {
            case 0x4C://准备上锁
                if (bytes1[1] == 0x00) {
                    closeLock.closeable("可以上锁");
                    flagbyte[0] = 0x46;
                } else if (bytes1[1] == 0x03) {
                    closeLock.closeable("执行命令超时");
                } else if (bytes1[1] == 0x02) {
                    closeLock.closeable("电量低");
                }
                break;
            case 0x55://准备开锁
                if (bytes1[1] == 0x00) {
                    openLock.openable("开锁成功");
                } else if (bytes1[1] == 0x03) {
                    openLock.openable("执行命令超时");
                } else if (bytes1[1] == 0x02) {
                    openLock.openable("电量低");
                }
                break;
            case 0x52://查询锁状态返回
                byte[] s1 = SwitchUtils.getBooleanArray(bytes1[3]);
                LogUtils.a("车锁状态", Arrays.toString(s1)+"yyy"+bytes1[3]);
                if (s1[5] == 1) {//车锁按钮弹不起来时，则会判断失误
                    enquiriesState.enquiriesState("锁状态：开");
                } else if (s1[5] == 0) {
                    enquiriesState.enquiriesState("锁状态：关");
                }
                float power = ((float)(bytes1[5]*256+bytes1[4]))/100;
                double pencent = (power - 1)*100/0.61;
                //LogUtils.a("车锁电压22", "bytes1[4]"+bytes1[4]+"bytes1[5]"+bytes1[5]+"power"+power);
                enquiriesState.enquiriesPower(pencent+"%");
                break;
            case 0x50://设置参数反馈
                if (bytes1[1] == 0x00) {
                    setParameters.setParameter("设置参数成功");
                } else if (bytes1[1] == 0x03) {
                    setParameters.setParameter("执行命令超时");
                    flag12 = true;
                } else if (bytes1[1] == 0x02) {
                    setParameters.setParameter("电量低");
                }
                break;
            case 0x43://刷卡返回
                if (bytes1[1] == 0x43) {
                    byte[] bytess = new byte[3];
                    if (App.readCardType == 1){
                        byte[] bytes = {(byte) (bytes1[3] ^ 0xFF), (byte) (bytes1[4] ^ 0xFF), (byte) (bytes1[5] ^ 0xFF)};
                        bytess = bytes;
                    }else {
                        byte[] bytes = {(byte) (bytes1[6] ^ 0xFF), (byte) (bytes1[5] ^ 0xFF), (byte) (bytes1[4] ^ 0xFF)};
                        bytess = bytes;
                    }
                    final String s = SwitchUtils.byte2HexStr(bytess);
                    LogUtils.a(s);
                    handler1.post(new Runnable() {
                        @Override
                        public void run() {
                            receivecardid.receiveCardID(s);
                        }
                    });
                    break;
                } else {
                    if (bytes1[1] == 0x45) {//强拆报警
                        openCallPolice.openCallPolice();
                    }
                }
                break;
            case 0x46://上锁成功
                //LogUtils.a("tagggg", bytes1[1] + "");
                if (bytes1[1] == 0x46) {
                    closeLock.closedLock("上锁成功");
                }
                break;
        }
    }


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
    public void rmoveConnections(){
        if (null != handler ){
            if (null != runnable1){
                handler.removeCallbacks(runnable1);
            }
            if (null != runnable2){
                handler.removeCallbacks(runnable2);
            }
        }
    }

    public void unregist() {
        if (mReceiver != null && flag == 2 && mContext != null) {
            mContext.unregisterReceiver(mReceiver);
            flag = 1;
        }
    }

    public BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(BluetoothDevice.ACTION_FOUND)) {
                device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);//通过此方法获取搜索到的蓝牙设备
                if (device == null) {
                    LogUtils.a("BlueToothUtils", "没有扫描到设备");
                    return;
                }
                String name = device.getName();
                if (name != null && name.length() > 3) {
                    String substring = name.substring(0, 4);
                    if (TextUtils.equals(substring, "NJZX")) {
                        getblue.getblueTooth(device);
                        //handler.removeCallbacks(runnable);
                    }
                }
            } else if (action.equals(BluetoothAdapter.ACTION_DISCOVERY_FINISHED)) {
                Toast.makeText(context, "蓝牙扫描结束", Toast.LENGTH_SHORT).show();
            }
        }
    };


    //蓝牙没成功打开时调用
    private Handler handler = new Handler() {
    };
    private Handler handler1 = new Handler(Looper.getMainLooper()) {
    };


    //close
    public void closeAlls() {
        if (bluetoothGatt != null) {
            isLoop = true;
            closeCallPloiceAudio();
            LogUtils.a("开始断开");
            bluetoothGatt.disconnect();
            mBluetoothAdapter.disable();
        }
    }

    private void closeCallPloiceAudio() {
        AudioPlayUtils.getAudio(Utils.getContext(), 0).stop();
        BaseActivity.intent.callPolice(2, null);
    }

    private CloseCallPolice closeCall;

    public interface CloseCallPolice {
        void closeCallPolice();
    }

    private GetblueToothListenter getblue;

    public interface GetblueToothListenter {
        void getblueTooth(BluetoothDevice device);
    }

    private ConnectedDevicesListenter connectedDevicesListenter;

    public interface ConnectedDevicesListenter {
        void connectenDevice(int i);//0失败，1成功
    }

    private OpenLockListenter openLock;

    public interface OpenLockListenter {
        void openable(String str);
    }

    private EnquiriesStateListenter enquiriesState;

    public interface EnquiriesStateListenter {
        void enquiriesState(String str);
        void enquiriesPower(String str);
    }

    private CloseLockListener closeLock;

    public interface CloseLockListener {
        void closeable(String str);

        void closedLock(String str);
    }

    private SetParametersListener setParameters;

    public interface SetParametersListener {
        void setParameter(String str);
    }

    private receiveCardIDListener receivecardid;

    public interface receiveCardIDListener {
        void receiveCardID(String str);
    }

    public void openCallPolice(openCallPoliceListener openCallPolice) {
        this.openCallPolice = openCallPolice;
    }

    //开启强拆报警
    private openCallPoliceListener openCallPolice;

    public interface openCallPoliceListener {
        void openCallPolice();
    }

    public void checkOpenLock(String msg) {
        if (null == initDialog) {
            initDialog = new SweetAlertDialog(mContext, SweetAlertDialog.PROGRESS_TYPE);
            initDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
            initDialog.setTitleText(msg);
            initDialog.setCancelable(false);
            initDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
            initDialog.setCanceledOnTouchOutside(true);
        }
        if (!initDialog.isShowing()) {
            initDialog.show();
        }
    }

    private void regist() {
        if (flag == 1) {
            IntentFilter mFilter = new IntentFilter();
            mFilter.addAction(BluetoothDevice.ACTION_FOUND);//蓝牙查询,可以在reciever中接受查询到的蓝牙设备
            mFilter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED); // 注册搜索完时的receiver
            mFilter.addAction(BluetoothDevice.ACTION_BOND_STATE_CHANGED);//蓝牙连接状态发生改变时,接收状态
            mContext.registerReceiver(mReceiver, mFilter);
            flag = 2;
        }
    }
    //    private Runnable runnable = new Runnable() {
//        @Override
//        public void run() {
//            scanning();
//        }
//    };

    //    public void scanning() {
//        handler.removeCallbacks(runnable);
//        handler.postDelayed(runnable, 5000);
//        if (mBluetoothAdapter.isEnabled()) { //通过适配器对象调用isEnabled()方法，判断蓝牙是否打开了
//            if (mBluetoothAdapter.isDiscovering()) {
//                mBluetoothAdapter.cancelDiscovery();
//            }
//            Toast.makeText(mContext, "开始扫描", Toast.LENGTH_SHORT).show();
//            mBluetoothAdapter.startDiscovery();
//        } else {
//            openBluetooth();
//        }
//    }
}
