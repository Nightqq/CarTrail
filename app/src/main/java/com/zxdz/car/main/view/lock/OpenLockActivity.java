package com.zxdz.car.main.view.lock;

import android.bluetooth.BluetoothDevice;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.zxdz.car.R;
import com.zxdz.car.base.view.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;

@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
public class OpenLockActivity extends BaseActivity {


    @BindView(R.id.open_toobar_scan)
    TextView openToobarScan;
    @BindView(R.id.open_lock_toolbar)
    Toolbar openLockToolbar;
    @BindView(R.id.open_title)
    TextView openTitle;
    @BindView(R.id.open_con1)
    Button openCon1;
    @BindView(R.id.open_con2)
    Button openCon2;
    @BindView(R.id.open_open)
    Button openOpen;
    @BindView(R.id.open_enquir)
    Button openEnquir;
    private SweetAlertDialog initDialog;
    public  static boolean iscon = false;//是否连接标准
    private String mAddress="空";

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            String obj = (String) msg.obj;
            openTitle.setText(obj);
        }
    };

    @Override
    public void init() {
        ButterKnife.bind(this);
        basetoobar(openLockToolbar, "蓝牙锁");
       /* BluetoothLock.getBlueHelp(this).getScannedBluetooth(new BluetoothLock.GetblueToothListenter() {
            @Override
            public void getblueTooth(BluetoothDevice device) {
                Toast.makeText(OpenLockActivity.this, "扫描到设备", Toast.LENGTH_SHORT).show();
            }
        });*/
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_open_lock2;
    }

    @Override
    protected void onStart() {
        super.onStart();
        boolean created = BluetoothLock.getBlueHelp(this).isCreated();
        if (created) {
            openTitle.setText("已经连接过设备");
            iscon = true;
        }
    }


    @OnClick({R.id.open_toobar_scan, R.id.open_con1, R.id.open_con2, R.id.open_open, R.id.open_enquir})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.open_toobar_scan:
                if (mAddress.equals("空")){
                    Toast.makeText(this, "未连接过设备", Toast.LENGTH_SHORT).show();
                    return;
                }
                checkOpenLock("连接中");
                sendCommand(mAddress);
                break;
            case R.id.open_con1:
                checkOpenLock("连接中");
                sendCommand("10:00:00:00:20:52");
                break;
            case R.id.open_con2:
                checkOpenLock("连接中");
                sendCommand("10:00:00:00:1F:A0");
                break;
            case R.id.open_open:
                if (!iscon) {
                    Toast.makeText(this, "请先连接设备", Toast.LENGTH_SHORT).show();
                    return;
                }
                checkOpenLock("开锁中");
                BluetoothLock.getBlueHelp(this).openLock(new BluetoothLock.OpenLockListenter() {
                    @Override
                    public void openLock(final String str) {
                        sendmsg(str);
                    }
                });
                break;
            case R.id.open_enquir:
                if (!iscon) {
                    Toast.makeText(this, "请先连接设备", Toast.LENGTH_SHORT).show();
                    return;
                }
                checkOpenLock("查询中");
                BluetoothLock.getBlueHelp(this).enquiriesState(new BluetoothLock.EnquiriesStateListenter() {
                    @Override
                    public void enquiriesState(String str) {
                        sendmsg(str);
                    }
                });
                break;
        }
    }

    private void sendmsg(String str) {
        Message message = new Message();
        message.obj = str;
        mHandler.sendMessage(message);
        initDialog.dismissWithAnimation();
    }

    private void sendCommand(String address) {
        BluetoothLock.key = -1;
        mAddress=address;
        BluetoothLock.getBlueHelp(this).ConnectedDevices(address, new BluetoothLock.ConnectedDevicesListenter() {
            @Override
            public void connectenDevice(final String str) {
                if (str.equals("连接成功")){
                    iscon=true;
                }
                sendmsg(str);
            }
        });
    }

    public void checkOpenLock(String msg) {
        initDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        initDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        initDialog.setTitleText(msg);
        initDialog.setCancelable(false);
        initDialog.show();
        initDialog.setCanceledOnTouchOutside(true);
    }
}
