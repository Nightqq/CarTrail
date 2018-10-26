package com.zxdz.car.main.view.lock;

import android.bluetooth.BluetoothDevice;
import android.content.Context;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;

import android.support.annotation.RequiresApi;
import android.support.v7.widget.Toolbar;

import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.TextView;
import com.blankj.utilcode.util.LogUtils;
import com.zxdz.car.R;

import com.zxdz.car.base.utils.AudioPlayUtils;
import com.zxdz.car.base.view.BaseActivity;
import com.zxdz.car.main.adapter.ExpandAdapter;
import com.zxdz.car.main.utils.BlueToothHelper;
import com.zxdz.car.main.utils.BlueToothUtils;

import com.zxdz.car.main.view.PoliceCardActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;

@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
public class AuthBlueLinkActivity extends BaseActivity {

    @BindView(R.id.Auth_blue_toolbar)
    Toolbar AuthBlueToolbar;
    @BindView(R.id.Auth_blue_explv)
    ExpandableListView AuthBlueExplv;
    @BindView(R.id.Auth_blue_tv_title)
    TextView AuthBlueTvTitle;
    @BindView(R.id.Auth_blue_bluesaomiao)
    Button AuthBlueBluesaomiao;
    @BindView(R.id.Auth_blue_enquir)
    Button AuthBlueEnquir;
    private ExpandAdapter expandAdapter;
    private SweetAlertDialog initDialog;
    private String My_address = "";
    private boolean flag3 = true;
    private Handler mHandler = new Handler() {};
    private AudioPlayUtils audioPlayUtils;
    private String My_Address="54:6C:0E:BD:7E:EE";

    @Override
    public void init() {
        ButterKnife.bind(this);
        basetoobar(AuthBlueToolbar, "蓝牙锁连接");
        //initdata();
        audioPlayUtils = new AudioPlayUtils(this, R.raw.sbzzsmz_qdd);
        audioPlayUtils.play();
        //AuthBlueBluesaomiao.callOnClick();
        checkOpenLock("连接中");
        sendCommand(My_Address);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_auth_blue_link;
    }

    @OnClick({R.id.Auth_blue_skip, R.id.Auth_blue_bluesaomiao, R.id.Auth_blue_enquir})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.Auth_blue_skip:
                startact(AuthBlueLinkActivity.this, PoliceCardActivity.class);
                finish();
                break;
            case R.id.Auth_blue_bluesaomiao://开
                openlock();
                break;
            case R.id.Auth_blue_enquir://关
                closelock();
                break;
        }
    }

    private void initdata() {
        expandAdapter = new ExpandAdapter();
        AuthBlueExplv.setAdapter(expandAdapter);
        AuthBlueExplv.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                checkOpenLock("连接中");
                BluetoothDevice device = expandAdapter.getDevice(groupPosition, childPosition);
                My_address = device.getAddress();
                SharedPreferences sp = getSharedPreferences("activity", Context.MODE_WORLD_READABLE);
                SharedPreferences.Editor edit = sp.edit();
                edit.putString("My_address", My_address);
                edit.commit();
                sendCommand(My_address);
                return true;
            }
        });
    }
    private Handler handler = new Handler();
    private void sendCommand(final String address) {
        BlueToothHelper.getBlueHelp().closeAll();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                BlueToothHelper.getBlueHelp().ConnectedDevices(address, new BlueToothUtils.ConnectedDevicesListenter() {
                    @Override
                    public void connectenDevice(int i) {
                        if (i == 1) { //连接成功跳转管理员刷卡页面
                            if (initDialog!=null&&initDialog.isShowing()){
                                initDialog.dismiss();
                            }
                        }
                    }
                },true);
            }
        },2000);
    }

    private void closelock() {
        BlueToothHelper.getBlueHelp().closeLock(new BlueToothUtils.CloseLockListener() {
            @Override
            public void closeable(String str) {
                LogUtils.a(str);
                if (flag3) {
                    checkOpenLock("锁车中");
                    flag3 = false;
                }
            }
            @Override
            public void closedLock(String str) {
                if (initDialog != null){
                    initDialog.dismissWithAnimation();
                }
            }
        });
    }


    private void openlock() {
        checkOpenLock("开锁中");
        BlueToothHelper.getBlueHelp().openLock(new BlueToothUtils.OpenLockListenter() {
            @Override
            public void openable(String str) {
                LogUtils.a(str);
                initDialog.dismissWithAnimation();
            }
        });
    }

    public void checkOpenLock(String msg) {
        initDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        initDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        initDialog.setTitleText(msg);
        initDialog.setCancelable(false);
        if (!initDialog.isShowing()){
            initDialog.show();
        }
        initDialog.setCanceledOnTouchOutside(true);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        BluetoothLock.getBlueHelp(this).unregist();
    }


}
