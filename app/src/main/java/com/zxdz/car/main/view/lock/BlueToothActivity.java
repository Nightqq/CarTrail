package com.zxdz.car.main.view.lock;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.Utils;
import com.zxdz.car.R;
import com.zxdz.car.base.helper.CarTravelHelper;
import com.zxdz.car.base.utils.AudioPlayUtils;
import com.zxdz.car.base.view.BaseActivity;
import com.zxdz.car.main.service.UploadDataService;
import com.zxdz.car.main.utils.BlueToothHelper;
import com.zxdz.car.main.utils.BlueToothUtils;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;


public class BlueToothActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolBar;
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
    private SweetAlertDialog initDialog;
    private int step = 1;//判断是从哪个界面跳转到确认界面
    private boolean flag = true;
    public static boolean flag2 = false;
    private boolean flag3 = true;
    public static Context context;
    private Intent intentService;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            String obj = (String) msg.obj;
            openLockTvTitle.setText(obj);
            if (obj.equals("上锁成功")) {
                if (flag) {
                    flag = false;
                    //savedate(1);
                    if (step==1){
                        Intent intent = new Intent(BlueToothActivity.this, UnloadAreaActivity.class);
                        intent.putExtra("blue_step", step);
                        startActivity(intent);
                    }else {
                        Intent intent = new Intent(BlueToothActivity.this, CameraActivity.class);
                        intent.putExtra("blue_step", step);
                        startActivity(intent);
                    }

                    //后期添加：状态值43，锁车完成后更改
                    //CarTravelHelper.carTravelRecord.setZT(43);
                    //CarTravelHelper.saveCarTravelRecordToDB(CarTravelHelper.carTravelRecord);
                    BlueToothHelper.getBlueHelp().setListenerNull();//成功后置空回调
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

    @Override
    public void init() {
        ButterKnife.bind(this);
        mToolBar.setTitle("蓝牙锁");
        setSupportActionBar(mToolBar);
        intentService = new Intent(this, UploadDataService.class);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            step = bundle.getInt("blue_step");
        }
        if (step == 1) {
            AudioPlayUtils.getAudio(BlueToothActivity.this, R.raw.qzxqmjsc).play();//请装卸区民警锁车
        } else {
            AudioPlayUtils.getAudio(BlueToothActivity.this, R.raw.qzlqmjsc).play();//请滞留区民警锁车
        }
        closelock();
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


    @OnClick({R.id.open_lock_bluesaomiao, R.id.open_lock_enquir, R.id.open_lock_worked, R.id.open_lock_open, R.id.open_lock_conc1, R.id.open_lock_conc2})
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

                    @Override
                    public void enquiriesPower(long str) {
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
        checkOpenLock("锁车中");
        //mHandler.postDelayed(runnable2, 8000);//这个开锁是用来
        BlueToothHelper.getBlueHelp().closeLock(new BlueToothUtils.CloseLockListener() {
            @Override
            public void closeable(String str) {
                LogUtils.a(str);
                if (flag3) {
                    //mHandler.removeCallbacks(runnable2);
                    flag3 = false;
                    mHandler.postDelayed(new Runnable() {//如果车锁提前锁好，可以检测到并自动到拍照界面
                        @Override
                        public void run() {
                            BlueToothHelper.getBlueHelp().enquiriesState(new BlueToothUtils.EnquiriesStateListenter() {
                                @Override
                                public void enquiriesState(String str) {
                                    if (str.equals("锁状态：关")) {
                                        Message message = new Message();
                                        message.obj = "上锁成功";
                                        mHandler.sendMessage(message);
                                    } else {//如果还没锁，则等待接收锁车完成信号
                                        BlueToothHelper.getBlueHelp().setFlagbyte();
                                    }
                                }

                                @Override
                                public void enquiriesPower(long str) {

                                }
                            });
                        }
                    }, 500);
                    //mHandler.postDelayed(runnable3, 15000);
                }
            }

            @Override
            public void closedLock(String str) {
                LogUtils.a(str);
                Message message = new Message();
                message.obj = str;
                message.what = 1;
                mHandler.sendMessage(message);

            }
        });
    }

    private void openlock() {
        checkOpenLock("开锁中");
        mHandler.postDelayed(runnable2, 8000);
        BlueToothHelper.getBlueHelp().openLock(new BlueToothUtils.OpenLockListenter() {
            @Override
            public void openable(String str) {
                LogUtils.a(str);
                mHandler.removeCallbacks(runnable2);
                AudioPlayUtils.getAudio(BlueToothActivity.this, R.raw.qdcmjsc).play();
                Message message = new Message();
                message.obj = str;
                message.what = 1;
                Log.e("kscg....", "291------");
                mHandler.sendMessage(message);
                initDialog.dismissWithAnimation();
            }
        });
    }

    Runnable runnable2 = new Runnable() {
        @Override
        public void run() {
            LogUtils.a("定时重新开锁");
            openlock();
        }
    };
    Runnable runnable3 = new Runnable() {
        @Override
        public void run() {
            AudioPlayUtils.getAudio(BlueToothActivity.this, R.raw.qdcmjsc).play();
            mHandler.postDelayed(this,3000);
        }
    };

    public void checkOpenLock(String msg) {
        try {
            if (initDialog == null){
                initDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
                initDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                initDialog.setCancelable(false);
                initDialog.setCanceledOnTouchOutside(true);
            }
            if (!initDialog.isShowing()) {
                initDialog.setTitleText(msg);
                initDialog.show();
            }
        } catch (Exception e) {
            LogUtils.a("" + e.getMessage().toString());
        }
    }

    @Override
    protected void onDestroy() {
        if (initDialog != null && initDialog.isShowing()) {
            initDialog.dismiss();
        }
        super.onDestroy();
       // mHandler.removeCallbacks(runnable3);
        runnable2 = null;
        //runnable3 = null;
        mHandler = null;
        AudioPlayUtils.getAudio(Utils.getContext(), 0).stop();

    }
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }
}
