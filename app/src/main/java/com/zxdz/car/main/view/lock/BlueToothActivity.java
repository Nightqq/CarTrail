package com.zxdz.car.main.view.lock;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.zxdz.car.App;
import com.zxdz.car.R;
import com.zxdz.car.base.helper.CarTravelHelper;
import com.zxdz.car.base.utils.AudioPlayUtils;
import com.zxdz.car.base.view.BaseActivity;
import com.zxdz.car.main.adapter.ExpandAdapter;
import com.zxdz.car.main.service.UploadDataService;
import com.zxdz.car.main.utils.BlueToothHelper;
import com.zxdz.car.main.utils.BlueToothUtils;
import com.zxdz.car.main.view.CarTrailActivity;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;

@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
public class BlueToothActivity extends BaseActivity {


    @BindView(R.id.open_lock_toolbar)
    Toolbar openLockToolbar;
    @BindView(R.id.open_lock_tv_title)
    TextView openLockTvTitle;
    @BindView(R.id.open_lock_bluesaomiao)
    Button openLockBluesaomiao;
    @BindView(R.id.open_lock_enquir)
    Button openLockEnquir;
    @BindView(R.id.open_lock_open)
    Button openLockOpen;
    @BindView(R.id.open_lock_worked)
    Button openLockWorked;
    @BindView(R.id.open_lock_conc1)
    Button openLockConc1;
    @BindView(R.id.open_lock_conc2)
    Button openLockConc2;
    private ExpandAdapter expandAdapter;
    private String My_address = "";
    private SweetAlertDialog initDialog;
    private int step = 1;//判断是从哪个界面跳转到确认界面
    private boolean flag = true;
    public static boolean  flag2 = false;
    private boolean flag3 = true;
    public static Context context;
    private Intent intentService;
    private AudioPlayUtils audioPlayUtils;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            String obj = (String) msg.obj;
            openLockTvTitle.setText(obj);
            if (obj.equals("上锁成功")) {
                if (flag) {
                    flag = false;
                    //savedate(1);
                    startact(BlueToothActivity.this,CameraActivity.class);
                    //LogUtils.a(App.GravityListener_type);
                    //startActivityForResult(new Intent(BlueToothActivity.this, OpenCardActivity.class), 1);
                    finish();
                }
            } else if (obj.equals("开锁成功")) {
                if (msg.what == 1) {
                    Toast.makeText(BlueToothActivity.this, "请锁车", Toast.LENGTH_SHORT).show();
                    return;
                }/*else if (msg.what == 2) {
                    if (flag2) {
                        flag2 = false;
                        Intent intent2 = new Intent(BlueToothActivity.this, CarTrailActivity.class);
                        intent2.putExtra("car_trail", 2);
                        startActivity(intent2);
                        savedate(2);
                        finish();
                    }
                }*/
            }
        }
    };
    private String num;
    private String police_num;

    @Override
    public void init() {
        ButterKnife.bind(this);
        basetoobar(openLockToolbar, "蓝牙锁");
        intentService = new Intent(this, UploadDataService.class);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            step = bundle.getInt("blue_step");
        }
        if (step == 1) {
            police_num = intent.getStringExtra("police_card");
            closelock();
        }else if (step == 2){
            police_num = intent.getStringExtra("police_card");
            openlock();
        }
     /*   if (step == 2) {
            num = intent.getStringExtra("car_trail");
            num = num.replaceAll(" ", "");
            openlock(2);
        }*/
    }

    @Override
    protected void onStart() {
        super.onStart();
       // boolean created = BlueToothHelper.getBlueHelp().isCreated();
       /* if (created) {
            openLockTvTitle.setText("已经连接过设备");
            openLockBluesaomiao.setVisibility(View.GONE);
            openLockConc1.setVisibility(View.GONE);
            openLockConc2.setVisibility(View.GONE);
            openLockEnquir.setVisibility(View.VISIBLE);
            openLockOpen.setVisibility(View.VISIBLE);
        }*/
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_open_lock;
    }


    @OnClick({ R.id.open_lock_bluesaomiao, R.id.open_lock_enquir, R.id.open_lock_worked, R.id.open_lock_open, R.id.open_lock_conc1, R.id.open_lock_conc2})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.open_lock_bluesaomiao:
                break;
            case R.id.open_lock_enquir:
                //checkOpenLock("查询中");
                BlueToothHelper.getBlueHelp().enquiriesState(new BlueToothUtils.EnquiriesStateListenter() {
                    @Override
                         public void enquiriesState(String str) {
                        Message message = new Message();
                        message.obj = str;
                        mHandler.sendMessage(message);
                        //initDialog.dismissWithAnimation();
                    }
                });
                break;
            case R.id.open_lock_worked:
                openLockTvTitle.setText("工作完成，刷卡开锁");
                openLockWorked.setVisibility(View.GONE);
                openLockOpen.setVisibility(View.VISIBLE);
                break;
            case R.id.open_lock_open:
                openlock(1);
                //startActivityForResult(new Intent(BlueToothActivity.this, OpenCardActivity.class), 1);
                break;
            case R.id.open_lock_conc1:
                break;
            case R.id.open_lock_conc2:
                break;
        }
    }
//手动再次发送开锁命令
    private void openlock(final int i) {
        checkOpenLock("开锁中");
//        BluetoothLock.getBlueHelp(BlueToothActivity.this).openLock(new BluetoothLock.OpenLockListenter() {
//            @Override
//            public void openLock(final String str) {;
//                Message message = new Message();
//                message.obj = str;
//                message.what = i;
//                mHandler.sendMessage(message);
//                initDialog.dismissWithAnimation();
//            }
//        });
        BlueToothHelper.getBlueHelp().openLock(new BlueToothUtils.OpenLockListenter() {
            @Override
            public void openable(String str) {
                // TODO: 2018/5/8 添加可以开锁的语音提示
            }
        });
    }
    private void closelock() {
        mHandler.postDelayed(runnable2, 8000);
        BlueToothHelper.getBlueHelp().closeLock(new BlueToothUtils.CloseLockListener() {
            @Override
            public void closeable(String str) {
                LogUtils.a(str);
                if (flag3) {
                    checkOpenLock("锁车中");
                    mHandler.removeCallbacks(runnable2);
                    flag3 = false;
                    audioPlayUtils = new AudioPlayUtils(BlueToothActivity.this, R.raw.qdcmjsc);
                    audioPlayUtils.play();
                }
            }

            @Override
            public void closedLock(String str) {
                LogUtils.a(str);
                Message message = new Message();
                 message.obj = str;
                message.what = 1;
                Log.e("kscg....","291------");
                mHandler.sendMessage(message);
                if (initDialog != null){
                    initDialog.dismissWithAnimation();
                }
            }
        });
    }
    private void openlock(){ 
        checkOpenLock("开锁中");
        mHandler.postDelayed(runnable2, 8000);
        BlueToothHelper.getBlueHelp().openLock(new BlueToothUtils.OpenLockListenter() {
            @Override
            public void openable(String str) {
                LogUtils.a(str);
                mHandler.removeCallbacks(runnable2);
                audioPlayUtils = new AudioPlayUtils(BlueToothActivity.this, R.raw.qdcmjsc);
                audioPlayUtils.play();
                Message message = new Message();
                message.obj = str;
                message.what = 1;
                Log.e("kscg....","291------");
                mHandler.sendMessage(message);
                initDialog.dismissWithAnimation();
            }
        });
    }

    Runnable runnable2=new Runnable() {
        @Override
        public void run() {
            LogUtils.a("定时重新开锁");
            openlock();
        }
    };
    Runnable runnable3=new Runnable() {
        @Override
        public void run() {
            LogUtils.a("定时重新开锁");
            //openlock(2);
        }
    };

    public void checkOpenLock(String msg) {
        try {
            if (!initDialog.isShowing()){
            initDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
            initDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
            initDialog.setTitleText(msg);
            initDialog.setCancelable(false);
            initDialog.setCanceledOnTouchOutside(true);
            initDialog.show();
            }
        }catch (Exception e){

        }
    }

   /* private void savedate(int i) {
        if (i == 1) {//锁车
            *//*if (CarTravelHelper.carTravelRecord != null) {
                CarTravelHelper.carTravelRecord.setDLGJ_SCSJ(new Date());
                CarTravelHelper.carTravelRecord.setDLGJ_SCKH(police_num);
                LogUtils.a(police_num);
                CarTravelHelper.carTravelRecord.setZT(40);
                CarTravelHelper.saveCarTravelRecordToDB(CarTravelHelper.carTravelRecord);
                App.SWIPE_STEP = 5;
                //上传主信息记录
                App.UPLOAD_STEP = 4;
                startService(intentService);
            }*//*
        } else if (i == 2) {//开锁
            if (CarTravelHelper.carTravelRecord != null) {
                CarTravelHelper.carTravelRecord.setDLGJ_KSSJ(new Date());
                CarTravelHelper.carTravelRecord.setDLGJ_KSKH(num);
                CarTravelHelper.carTravelRecord.setZT(50);
                CarTravelHelper.saveCarTravelRecordToDB(CarTravelHelper.carTravelRecord);
                App.SWIPE_STEP = 6;
                //上传主信息记录
                App.UPLOAD_STEP = 5;
                startService(intentService);
                finish();
            }
        }
    }*/
}
