package com.zxdz.car.main.view;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.jakewharton.rxbinding.view.RxView;
import com.zxdz.car.App;
import com.zxdz.car.R;
import com.zxdz.car.base.helper.CarTravelHelper;
import com.zxdz.car.base.utils.AudioPlayUtils;
import com.zxdz.car.base.view.BaseActivity;
import com.zxdz.car.main.contract.PersionInfoContract;
import com.zxdz.car.main.model.domain.PoliceInfoAll;
import com.zxdz.car.main.presenter.PersionInfoPresenter;
import com.zxdz.car.main.service.UploadDataService;
import com.zxdz.car.main.utils.BlueToothHelper;
import com.zxdz.car.main.utils.BlueToothUtils;
import com.zxdz.car.main.view.lock.BlueToothActivity;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.functions.Action1;

/**
 * Created by super on 2017/10/22.
 * 确认刷卡界面
 */

public class InstallConfirmActivity extends BaseActivity<PersionInfoPresenter> implements PersionInfoContract.View {

    @BindView(R.id.toolbar)
    Toolbar mToolBar;

    @BindView(R.id.btn_next_success)
    Button mNextButton;

    @BindView(R.id.layout_card_read_wait)
    LinearLayout mReadWaitLayout;

    @BindView(R.id.layout_card_info)
    LinearLayout mCardInfoLayout;

    @BindView(R.id.layout_next)
    LinearLayout mNextLayout;

    @BindView(R.id.tv_card_number)
    TextView mCardNumberTextView;

    @BindView(R.id.tv_car_name)
    TextView tvCarNameTextView;

    @BindView(R.id.tv_alarm)
    TextView tvAlarmTextView;

    @BindView(R.id.tv_dept)
    TextView tvDeptTextView;

    public String flag = "000000";
    @BindView(R.id.not_move)
    TextView notMove;
    private int step = 1;//判断是从哪个界面跳转到确认界面
    private String mCarNumber;
    private Intent intentService;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };
    private AudioPlayUtils audioPlayUtils;
    private String mcarNumber;

    @Override
    public int getLayoutId() {
        return R.layout.activity_install_confirm;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    public void init() {
        flag = "000000";
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            step = bundle.getInt("confirm_step");
        }
        if (step == 1) {
            App.SWIPE_STEP = 4;
            App.UPLOAD_STEP = 3;
            LogUtils.a(App.UPLOAD_STEP);
            audioPlayUtils = new AudioPlayUtils(this, R.raw.sbazwbhqdcmjsk);
            audioPlayUtils.play();
            mToolBar.setTitle("带车民警确认安装完成");
            notMove.setVisibility(View.VISIBLE);
        }
        if (step == 2) {
            App.SWIPE_STEP = 5;
            App.UPLOAD_STEP = 4;
            audioPlayUtils = new AudioPlayUtils(this, R.raw.clddzxqqdcmjqrsk);
            audioPlayUtils.play();
            mToolBar.setTitle("带车民警确认到达刷卡");
        }
        if (step == 3) {
            App.SWIPE_STEP = 7;
            App.UPLOAD_STEP = 6;
            audioPlayUtils = new AudioPlayUtils(this, R.raw.qdcmjqrsbjhsk);
            audioPlayUtils.play();
            mToolBar.setTitle("带车民警确认交还设备");
        }
        setSupportActionBar(mToolBar);
        mToolBar.setNavigationIcon(R.mipmap.back_icon);
        mToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mPresenter = new PersionInfoPresenter(this, this);
        intentService = new Intent(this, UploadDataService.class);
        BlueToothHelper.getBlueHelp().setReceiverMode(new BlueToothUtils.receiveCardIDListener() {
            @Override
            public void receiveCardID(String str) {
                showCardNumber(str);
            }
        });
        //驾驶员刷卡
        RxView.clicks(mNextButton).throttleFirst(200, TimeUnit.MILLISECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                if (step == 1) {
                    ToastUtils.showLong("认证成功,开始记录轨迹");
                    App.GravityListener_type = 1;
                    Intent intent = new Intent(InstallConfirmActivity.this, CarTrailActivity.class);
                    intent.putExtra("car_trail", 1);//进入时记录路线
                    startActivity(intent);
                    finish();
                }
                if (step == 2) {
                    //ToastUtils.showLong("认证成功,请关闭蓝牙锁");
                    Intent intent = new Intent(InstallConfirmActivity.this, BlueToothActivity.class);
                    intent.putExtra("police_card", mCarNumber);
                    intent.putExtra("blue_step", 1);
                    startActivity(intent);
                    finish();
                }
                if (step == 3) {
                    LogUtils.a("归还成功，行程结束");
                    audioPlayUtils = new AudioPlayUtils(InstallConfirmActivity.this, R.raw.gclcjs);
                    audioPlayUtils.play();
                       /* final Intent intent = getPackageManager().getLaunchIntentForPackage(getPackageName());
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);*/
                    Intent intent = new Intent(InstallConfirmActivity.this, MainActivity.class);
                    intent.putExtra("end", 1);
                    startActivity(intent);
                    finish();
                }
            }
        });

        //模拟测试步骤，后期删除
        //TODO
        //mNextLayout.setVisibility(View.VISIBLE);
    }

    /**
     * 显示读取到的卡号
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    public void showCardNumber(String carNumber) {
        LogUtils.a("读卡");
        if (flag.equals(carNumber)) {//两次刷卡相同抛弃
            LogUtils.a("抛弃");
            return;
        }
        flag = carNumber;
        if (App.SWIPE_STEP == 4 || App.SWIPE_STEP == 5 || App.SWIPE_STEP == 7) {
            if (StringUtils.isEmpty(carNumber)) {
                ToastUtils.showLong("卡号读取错误，请重试");
                return;
            }
            //去除卡号中的空格
            carNumber = carNumber.replaceAll(" ", "");
            mCarNumber = carNumber;
            mCardNumberTextView.setText(carNumber);
            mReadWaitLayout.setVisibility(View.GONE);
            mCardInfoLayout.setVisibility(View.VISIBLE);
            mNextLayout.setVisibility(View.VISIBLE);
            if (step == 1) {
                saveAdminCard(carNumber);  //存储卡号到本地数据库0
                //ToastUtils.showLong("认证成功,开始记录轨迹");
                App.GravityListener_type = 1;//开启手持机移动报警
                LogUtils.a(App.GravityListener_type);
                Intent intent = new Intent(InstallConfirmActivity.this, CarTrailActivity.class);
                intent.putExtra("car_trail", 1);//进入时记录路线
                startActivity(intent);
                finish();
            }
            LogUtils.a("step == 1结束");
            if (step == 2) {
                saveAdminCard(carNumber);  //存储卡号到本地数据库0
                //ToastUtils.showLong("认证成功,请关闭蓝牙锁");
                //在关闭锁界面，要处理上传主流水记录的操作
                Intent intent = new Intent(InstallConfirmActivity.this, BlueToothActivity.class);
                intent.putExtra("police_card", carNumber);
                intent.putExtra("blue_step", 1);
                startActivity(intent);
                finish();
            }
            if (step == 3) {
                saveAdminCard(carNumber);  //存储卡号到本地数据库0
                LogUtils.a("归还成功，行程结束");/*
                    final Intent intent = getPackageManager().getLaunchIntentForPackage(getPackageName());
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);*/
                audioPlayUtils = new AudioPlayUtils(this, R.raw.gclcjs);
                audioPlayUtils.play();
                BlueToothHelper.getBlueHelp().closeAll();
                Intent intent = new Intent(InstallConfirmActivity.this, MainActivity.class);
                intent.putExtra("end", 1);
                startActivity(intent);
                finish();
            }
        }
        //根据读取的卡号查询人员信息
        //  handler.postDelayed(runnable, 8000);
        Log.e("requestinfo", "开始获取民警数据");
        mPresenter.getPersionInfo(carNumber);
        if (!TextUtils.equals(carNumber, null)) {
            mcarNumber = carNumber;
            //handler.postDelayed(mRunnable,500);
        }
    }
   /* Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            mPresenter.getPersionInfo(mcarNumber);
            handler.postDelayed(mRunnable,500);
        }
    };*/


    /**
     * 读卡成功保存【卡号】
     *
     * @param cardNumber
     */
    public void saveAdminCard(String cardNumber) {
        if (CarTravelHelper.carTravelRecord != null) {
            if (step == 1) {
                CarTravelHelper.carTravelRecord.setDLGJ_AZKH(cardNumber);
                CarTravelHelper.carTravelRecord.setDLGJ_AZSJ(new Date());
                CarTravelHelper.carTravelRecord.setZT(20);
            }
            if (step == 2) {
                CarTravelHelper.carTravelRecord.setDLGJ_SCKH(cardNumber);
                CarTravelHelper.carTravelRecord.setDLGJ_SCSJ(new Date());
                CarTravelHelper.carTravelRecord.setZT(40);
            }
            if (step == 3) {
                CarTravelHelper.carTravelRecord.setDLGJ_JHKH(cardNumber);
                CarTravelHelper.carTravelRecord.setDLGJ_JHSJ(new Date());
                CarTravelHelper.carTravelRecord.setZT(70);
            }
            CarTravelHelper.saveCarTravelRecordToDB(CarTravelHelper.carTravelRecord);
        }
    }

    /*Runnable runnable=new Runnable() {
        @Override
        public void run() {
            LogUtils.a("定时上传数据");
            startService(intentService);
        }
    };*/
    @Override
    public void hideStateView() {
    }

    @Override
    public void showNoNet() {
    }

    @Override
    public void showPoliceInfoAll(PoliceInfoAll persionInfo) {
        if (persionInfo != null) {
            tvCarNameTextView.setText(persionInfo.getDLGJ_XM());
            tvAlarmTextView.setText(persionInfo.getDLGJ_JH());
            tvDeptTextView.setText(persionInfo.getDLGJ_BMMC());
            if (step == 1) {
                CarTravelHelper.carTravelRecord.setDLGJ_AZXM(persionInfo.getDLGJ_XM());
                CarTravelHelper.carTravelRecord.setDLGJ_AZJH(persionInfo.getDLGJ_JH());
                CarTravelHelper.carTravelRecord.setDLGJ_AZBM(persionInfo.getDLGJ_BMMC());
            }
            if (step == 2) {
                CarTravelHelper.carTravelRecord.setDLGJ_SCXM(persionInfo.getDLGJ_XM());
                CarTravelHelper.carTravelRecord.setDLGJ_KSJH(persionInfo.getDLGJ_JH());
                CarTravelHelper.carTravelRecord.setDLGJ_KSBM(persionInfo.getDLGJ_BMMC());
            }
            if (step == 3) {
                LogUtils.a("开始存储归还信息本地");
                CarTravelHelper.carTravelRecord.setDLGJ_JHXM(persionInfo.getDLGJ_XM());
                CarTravelHelper.carTravelRecord.setDLGJ_JHJH(persionInfo.getDLGJ_JH());
                CarTravelHelper.carTravelRecord.setDLGJ_JHBM(persionInfo.getDLGJ_BMMC());
            }
            CarTravelHelper.saveCarTravelRecordToDB(CarTravelHelper.carTravelRecord);
        }
        //上传主信息记录
        startService(intentService);
        if (App.DRIVER_SWIPE == 0) {
            /*if (flag) {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mNextButton.callOnClick();
                        flag = false;
                    }
                }, 2000);
            }*/
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
