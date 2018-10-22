package com.zxdz.car.base.view;

import android.app.ActivityManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.media.ToneGenerator;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.blankj.utilcode.util.EmptyUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.Utils;
import com.huayu.halio.Tag125K;
import com.hwangjr.rxbus.RxBus;
import com.kk.utils.UIUitls;
import com.zxdz.car.App;
import com.zxdz.car.R;
import com.zxdz.car.base.helper.CarTravelHelper;
import com.zxdz.car.base.helper.UnWarnInfoHelper;
import com.zxdz.car.base.helper.WarnInfoHelper;
import com.zxdz.car.base.presenter.BasePresenter;
import com.zxdz.car.base.utils.AudioPlayUtils;
import com.zxdz.car.main.model.domain.CardInfo;
import com.zxdz.car.main.model.domain.PersionInfo;
import com.zxdz.car.main.model.domain.UnWarnInfo;
import com.zxdz.car.main.model.domain.WarnInfo;
import com.zxdz.car.main.service.AppCloseLister;
import com.zxdz.car.main.service.GravitySListener;
import com.zxdz.car.main.service.UploadDataService;
import com.zxdz.car.main.utils.BlueToothHelper;
import com.zxdz.car.main.utils.BlueToothUtils;
import com.zxdz.car.main.utils.ToastUtil;

import java.util.Date;
import java.util.List;

import butterknife.ButterKnife;


public abstract class BaseActivity<P extends BasePresenter> extends AppCompatActivity implements IView, IDialog {

    private static final String TAG = "BaseActivity";

    protected P mPresenter;

    protected LoadingDialog mLoadingDialog;

    private ToneGenerator mToneGenerator;

    private Tag125K sTag125k = new Tag125K();

    private boolean mbQueryExitFlag = false;

    private SharedPreferences sp;

    private List<CardInfo> cardInfoListFromDB;

    private Thread queryUidThread;

    /* private ConnectT connectThread;

     private QueryICUidT queryICUidThread;

     private InitT initThread;*/
    private boolean flag = true;
    private static AudioPlayUtils audioPlayUtils;
    private GravityT gravityT;
    private Intent bjintent;
    private Dialog dialog;
    private ToastUtil toastUtil;
    public static final int FLAG_HOMEKEY_DISPATCHED = 0x80000000; //需要自己定义标志

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(FLAG_HOMEKEY_DISPATCHED, FLAG_HOMEKEY_DISPATCHED);//关键代码
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);//屏幕常亮
        if ((getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) != 0) {
            finish();
            return;
        }
        //registerReceiver(mHomeKeyEventReceiver, new IntentFilter(Intent.ACTION_CLOSE_SYSTEM_DIALOGS));
        ScreenUtils.setPortrait(this);
        RxBus.get().register(this);
        setContentView(getLayoutId());
        mLoadingDialog = new LoadingDialog(this);
        try {
            ButterKnife.bind(this);
        } catch (Exception e) {
            e.printStackTrace();
            LogUtils.i(this.getClass().getSimpleName() + " ButterKnife->初始化失败 原因:" + e);
        }
        // mToneGenerator = new ToneGenerator(AudioManager.STREAM_SYSTEM, 100);
        init();
      /*  //使用sp保存当前activity的名称
        String name = this.getClass().getName();
        sp = getSharedPreferences("activity", Context.MODE_WORLD_READABLE);
        SharedPreferences.Editor edit = sp.edit();
        edit.putString("name", name);
        edit.commit();*/
        GravityTOpen();//开启移动报警
        bjintent = new Intent(this, UploadDataService.class);
    }

    private static float x0;
    private static float y0;
    private static float z0;
    private long mTime;//准备报警时间
    private static float x1;
    private static float y1;
    private static float z1;

    public void GravityTOpen() {
        if (gravityT == null) {
            gravityT = new GravityT();
            gravityT.start();
        }
    }

    public class GravityT extends Thread {
        public void run() {
            startGravityListener();
        }
    }


    public synchronized void startGravityListener() {
        mySensorEventListener = new GravitySListener(new GravitySListener.CallBack() {
            @Override
            public void doSomeThing(float x, float y, float z) {
                x1 = x;
                y1 = y;
                z1 = z;
                if (App.GravityListener_type == 0) {//非报警状态
                    x0 = x;
                    y0 = y;
                    z0 = z;
                }
                if (App.GravityListener_type == 1) {//报警状态
                    if (Math.abs(z0 - z) > 1.5 || Math.abs(x0 - x) > 5 || Math.abs(y0 - y) > 3) {
                        if (System.currentTimeMillis() - mTime > 2000) {
                            // TODO: 2018\5\10 0010 报警
                            if (audioPlayUtils == null) {
                                policeingunclick();
                                audioPlayUtils = new AudioPlayUtils(BaseActivity.this, R.raw.ydbj);
                                callPolice(1);
                            }
                            if (!audioPlayUtils.isPLayComplete()) {
                                audioPlayUtils.play(true);
                            }
                        }
                    } else {
                        unPolice();
                    }
                }
            }
        });
    }

    private void unPolice() {
        if (audioPlayUtils != null) {
            if (dialog != null) {
                dialog.dismiss();
            }
            LogUtils.a("停止报警语音");
            callPolice(2);
            audioPlayUtils.stop();
            toastUtil.stop();
            BlueToothHelper.getBlueHelp().giveupCloseMessage(new BlueToothUtils.CloseCallPolice() {
                @Override
                public void closeCallPolice() {

                }
            }, false);
            audioPlayUtils = null;
        }
        mTime = System.currentTimeMillis();
    }

    private void callPolice(int i) {
        SharedPreferences sp = getSharedPreferences("activity", Context.MODE_WORLD_READABLE);
        String policeNum = sp.getString("policeNum", "111111");
        if (i == 1) {//报警
            LogUtils.a("开始储存报警数据");
            // TODO: 2018\1\17 0017 强拆锁报警
            WarnInfo warnInfo = new WarnInfo();
            warnInfo.setLsId(App.LSID.intValue());
            warnInfo.setId(CarTravelHelper.carTravelRecord.getId());
            warnInfo.setWarnContent("移动报警");
            warnInfo.setWarnDate(new Date());
            warnInfo.setWarnType(0);
            warnInfo.setDataState(0);
            warnInfo.setWarnPoliceNum(policeNum);
            warnInfo.setWarnFlag(0);
            WarnInfoHelper.saveWarnInfoToDB(warnInfo);
            startService(bjintent);
        } else if (i == 2) {//取消报警
            LogUtils.a("开始储存取消报警数据");
            UnWarnInfo warnInfoListByID = UnWarnInfoHelper.getWarnInfoListByID(CarTravelHelper.carTravelRecord.getId(), false);
            warnInfoListByID.setFlag(true);
            UnWarnInfoHelper.saveWarnInfoToDB(warnInfoListByID);
            startService(bjintent);
        }
    }

    //屏幕不可点击
    private void policeingunclick() {
        dialog = new android.app.AlertDialog.Builder(getApplicationContext())
                .create();
        dialog.setContentView(R.layout.dialog_police_unclick);
        Window window = dialog.getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.gravity = Gravity.BOTTOM;
        params.width = WindowManager.LayoutParams.WRAP_CONTENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.alpha = 0.7f;
        window.setAttributes(params);
        window.setType(WindowManager.LayoutParams.TYPE_SYSTEM_ERROR);
        dialog.setCancelable(false);
        dialog.show();
        //报警吐司
        toastUtil = new ToastUtil(this, R.layout.activity_toast, "设备移动报警中，请将设备放回原处(民警刷卡可重置安装位置)");
        toastUtil.show(100000000);
        //刷卡
        BlueToothHelper.getBlueHelp().giveupCloseMessage(new BlueToothUtils.CloseCallPolice() {
            @Override
            public void closeCallPolice() {
                LogUtils.a("返回取消报警");
                x0 = x1;
                y0 = y1;
                z0 = z1;
                unPolice();
            }
        }, true);
    }

    private SensorManager sensorManager;
    private GravitySListener mySensorEventListener;

    @Override
    protected void onResume() {
        sensorManager = (SensorManager) this.getSystemService(Context.SENSOR_SERVICE); // 实例化传感器管理者
        Sensor sensor_accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(mySensorEventListener, sensor_accelerometer, SensorManager.SENSOR_DELAY_UI);
        super.onResume();
        if (EmptyUtils.isNotEmpty(mPresenter)) {
            mPresenter.subscribe();
        }
    }

    @Override
    public void onBackPressed() {
    }

    private Handler handler = new Handler() {};

    //给拍照页面使用的标记，关闭后台自启动功能
    public static boolean start_flag=true;
    @Override
    protected void onStop() {
        super.onStop();
        //程序不可见判断程序是否处于前台
        SharedPreferences sp = getApplicationContext().getSharedPreferences("qq", Context.MODE_PRIVATE);
        boolean start_set_3 = sp.getBoolean("start_set_3", true);
        if (start_flag&&start_set_3&&!isAppOnForeground()) {
            handler.postDelayed(runnable, 500);
        }
    }
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            LogUtils.a("定时将后台应用跳转前台");
            //次方法启动activity，按下home键无法启动
            /*ActivityManager am = (ActivityManager) getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);
            am.moveTaskToFront(getTaskId(), ActivityManager.MOVE_TASK_WITH_HOME);*/
            //按下home后startActivity启动有5秒延迟！！！次方法不行
           /* Intent intent = new Intent(BaseActivity.this,BaseActivity.this.getClass());
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            BaseActivity.this.startActivity(intent);*/

            Intent intent = new Intent(BaseActivity.this, BaseActivity.this.getClass());
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
            PendingIntent pendingIntent =
                    PendingIntent.getActivity(BaseActivity.this, 0, intent, 0);
            try {
                pendingIntent.send();
            } catch (PendingIntent.CanceledException e) {
                e.printStackTrace();
            }
        }
    };


    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(mySensorEventListener);
        mbQueryExitFlag = true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = new Intent(Utils.getContext(), AppCloseLister.class);
        stopService(intent);
        handler.removeCallbacks(runnable);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (EmptyUtils.isNotEmpty(mPresenter)) {
            mPresenter.unsubscribe();
        }
        RxBus.get().unregister(this);
        flag = false;
    }

    //判断程序是否在前台运行
    public boolean isAppOnForeground() {
        ActivityManager activityManager = (ActivityManager) getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);
        String packageName = getApplicationContext().getPackageName();
        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager
                .getRunningAppProcesses();
        if (appProcesses == null)
            return false;
        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            // The name of the process that this object is associated with.
            if (appProcess.processName.equals(packageName)
                    && appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                return true;
            }
        }
        return false;
    }


    @Override
    public void showLoadingDialog(String msg) {
        mLoadingDialog.setMessage(msg);
        mLoadingDialog.show();

    }


    @Override
    public void dismissLoadingDialog() {
        UIUitls.post(new Runnable() {
            @Override
            public void run() {
                mLoadingDialog.dismiss();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void startact(Context old, Class newact) {
        startActivity(new Intent(old, newact));
    }

    //toolbar
    public void basetoobar(Toolbar toolbar, int title) {
        setSupportActionBar(toolbar);
        ActionBar actiontoolbar = getSupportActionBar();
        actiontoolbar.setDisplayHomeAsUpEnabled(true);
        actiontoolbar.setTitle(title);
    }

    public void basetoobar(Toolbar toolbar, String title) {
        setSupportActionBar(toolbar);
        ActionBar actiontoolbar = getSupportActionBar();
        actiontoolbar.setDisplayHomeAsUpEnabled(true);
        actiontoolbar.setTitle(title);
    }

    //Toolbar返回键设置
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.finish();
        }
        return true;
    }

    /*
        private void playTone() {
            if (mToneGenerator != null) {
                mToneGenerator.startTone(ToneGenerator.TONE_PROP_BEEP2, 100);
            }
        }*/


   /* public class QueryUidT extends Thread {
        public void run() {
            mbQueryExitFlag = false;

            try {
                while (!mbQueryExitFlag) {
                    byte[] bUid = null;
                    try {
                        bUid = sTag125k.readTag125KUid();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                        LogUtils.e(TAG, "readTag125KUid Exception:" + e.toString());
                    }
                    if (bUid != null) {
                        StringBuffer sb = new StringBuffer();
                        LogUtils.e(TAG, bUid.length + "");
                        for (int i = 0; i < bUid.length; i++) {
                            sb.append(" ");
                            sb.append(Integer.toHexString(bUid[i] & 0xFF));
                        }

                        RxBus.get().post(Constant.READ_CARD_NUMBER, sb.toString());

                        //play tone
                        playTone();
                        try {
                            sleep(200);//1000
                        } catch (java.lang.InterruptedException ie) {
                        }

                    } else {
                        LogUtils.e(TAG, "等待读卡中...");
                    }

                }
            } catch (Exception e) {
                LogUtils.e("QueryUidT error --->" + e.getMessage());
            }
        }
    }*/

   /* public class OpenT extends Thread {
        public void run() {
            sTag125k.closeTag125KPower();
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            sTag125k.openTag125KPower();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            try {
                sTag125k.openTag125KPort();
            } catch (FalconException fae) {
                LogUtils.e(TAG, "openTag125KPort Exception:" + fae.toString());
            }
            queryUidThread = new QueryUidT();
            queryUidThread.start();
        }
    }*/

   /* public class InitT extends Thread {
        public void run() {

//                com.halio.Rfid.closeCommPort();
//                com.halio.Rfid.powerOff();
//                try {
//                    LogUtils.e(TAG, "刷卡线程流程1");
//                    Thread.sleep(500);
//                } catch (InterruptedException e) {
//                    // TODO Auto-generated catch block
//                    e.printStackTrace();
//                }

                com.halio.Rfid.powerOn();
                com.halio.Rfid.openCommPort();

                try {
                    LogUtils.e(TAG, "刷卡线程流程2");
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                connectThread = new ConnectT();
                connectThread.start();
        }
    }*/

   /* private class ConnectT extends Thread {
        public void run() {
                byte[] bVersion = new byte[16];
                int iVersionLength = com.halio.Rfid.getHwVersion(bVersion);
                if (iVersionLength > 0) {
                    LogUtils.e(TAG, "刷卡线程流程3");
                    queryICUidThread = new QueryICUidT();
                    queryICUidThread.start();
                } else {
                    LogUtils.e(TAG, "刷卡线程流程4");
                    LogUtils.e("Connect Fail,getVersionFail ");
                    if (flag){
                        initThread = new InitT();
                        initThread.start();
                    }
                }


        }
    }
*/
   /* private boolean readData(byte[] UID) {
        if (!com.halio.Rfid.PcdConfigISOType((byte) 'A')) {
           // LogUtils.e("test", "PcdConfigISOType fail");
            return false;
        }

        long start1 = System.currentTimeMillis();
        byte[] tagType = new byte[2];
        if (!com.halio.Rfid.PcdRequest((byte) 0x52, tagType)) {
//			LogUtils.e("test", "PcdRequest fail");
            LogUtils.e("test", "PcdRequest fail:" + (System.currentTimeMillis() - start1));
            return false;
        }

        LogUtils.e("test", "PcdRequest succ:" + (System.currentTimeMillis() - start1));
        long start2 = System.currentTimeMillis();

        if (!com.halio.Rfid.PcdAnticoll(UID)) {
//			LogUtils.e("test", "PcdAnticoll fail");
            LogUtils.e("test", "PcdAnticoll fail:" + (System.currentTimeMillis() - start2));
            return false;
        }
        LogUtils.e("test", "PcdAnticoll succ:" + (System.currentTimeMillis() - start2));
        return true;
    }*/

  /*  private class QueryICUidT extends Thread {
        public void run() {
                LogUtils.e(TAG, "刷卡线程流程5");
                mbQueryExitFlag = false;
                try {
                    while (!mbQueryExitFlag) {
                        byte[] bUid = new byte[4];
                        if (readData(bUid)) {
                            StringBuffer sb = new StringBuffer();
                            for (int i = 0; i < bUid.length; i++) {
                                String string = Integer.toHexString(bUid[i] & 0xFF);
                                sb.append(" ");
                                sb.append(string.length() < 2 ? "0" + string : string);
                            }
                            //读取到卡号
                            RxBus.get().post(Constant.READ_CARD_NUMBER, sb.toString());
                            playTone();
                        } else {
                            // LogUtils.e("Read ic card waiting ");
                        }
                    }
                } catch (Exception e) {
                    LogUtils.e("Read ic card QueryICUidT error " + e.getMessage());
                }

        }

    }*/

    /* */

    /**
     * 关闭线程
     *//*
    public void closeThread(){
        if(queryUidThread != null){
            queryUidThread.stop();
    }

        if(connectThread != null){
             connectThread.stop();
        }

        if(queryICUidThread != null){
            queryICUidThread.stop();
        }

        if(initThread != null){
            initThread.stop();
        }
    }*/
    public void showPersionInfo(PersionInfo persionInfo) {

    }


}
