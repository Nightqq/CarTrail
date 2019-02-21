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
import android.widget.ImageView;
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
import com.zxdz.car.base.utils.SwipingCardUtils;
import com.zxdz.car.base.view.BaseActivity;
import com.zxdz.car.main.contract.PersionInfoContract;
import com.zxdz.car.main.model.domain.CarTravelRecord;
import com.zxdz.car.main.model.domain.PersionInfo;
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
    @BindView(R.id.install_wait)
    ImageView installWait;
    @BindView(R.id.refresh_card)
    ImageView refreshCard;
    private int step = 1;//判断是从哪个界面跳转到确认界面
    private String mCarNumber;
    private Intent intentService;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };
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
            AudioPlayUtils.getAudio(this, R.raw.qdcmjazkzq).play();
            mToolBar.setTitle("带车民警确认安装完成刷卡");
            notMove.setVisibility(View.VISIBLE);
        }
        if (step == 2) {
            App.SWIPE_STEP = 5;
            App.UPLOAD_STEP = 4;
            AudioPlayUtils.getAudio(this, R.raw.clddzxqqdcmjqrsk).play();
            mToolBar.setTitle("带车民警确认到达刷卡");
            installWait.setVisibility(View.GONE);
            refreshCard.setVisibility(View.VISIBLE);
        }
        if (step == 3) {
            App.SWIPE_STEP = 7;
            App.UPLOAD_STEP = 6;
            AudioPlayUtils.getAudio(this, R.raw.qdcmjqrjhkzqsc).play();//请带车民警确认交还控制器刷卡
            mToolBar.setTitle("带车民警确认交还控制器");
            installWait.setVisibility(View.GONE);
            refreshCard.setVisibility(View.VISIBLE);
        }
        setSupportActionBar(mToolBar);
       /* mToolBar.setNavigationIcon(R.mipmap.back_icon);
        mToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });*/
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
                    intent.putExtra("blue_step", 1);
                    startActivity(intent);
                    finish();
                }
                if (step == 3) {
                    LogUtils.a("归还成功，行程结束");
                    AudioPlayUtils.getAudio(InstallConfirmActivity.this, R.raw.gclcjs).play();
                       /* final Intent intent = getPackageManager().getLaunchIntentForPackage(getPackageName());
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);*/
                    Intent intent = new Intent(InstallConfirmActivity.this, InitReturnActivity.class);
                   // intent.putExtra("end", 1);
                    startActivity(intent);
                    startService(intentService);
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
            //mNextLayout.setVisibility(View.VISIBLE);
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
                AudioPlayUtils.getAudio(this, R.raw.gclcjs).play();
                Intent intent = new Intent(InstallConfirmActivity.this, InitReturnActivity.class);
                //intent.putExtra("end", 1);
                startActivity(intent);
                startService(intentService);
                finish();
            }
        }
        //根据读取的卡号查询人员信息
        Log.e("requestinfo", "开始获取民警数据");
        mPresenter.getPersionInfo(carNumber);
        if (!TextUtils.equals(carNumber, null)) {
            mcarNumber = carNumber;
        }
    }

    /**
     * 读卡成功保存【卡号】
     *
     * @param cardNumber
     */
    public void saveAdminCard(String cardNumber) {
        if (CarTravelHelper.carTravelRecord == null) {
            CarTravelHelper.carTravelRecord = new CarTravelRecord();
            CarTravelHelper.carTravelRecord.setLS_ID(0);
            CarTravelHelper.carTravelRecord.setId(System.currentTimeMillis());
            CarTravelHelper.carTravelRecord.setZDJ_ID(App.ZDJID);
        }
        if (CarTravelHelper.carTravelRecord != null) {
            if (step == 1) {
                SwipingCardUtils swipecardhelp = SwipingCardUtils.swipecardhelp(getApplicationContext());
                if (swipecardhelp.getArray(0, 0) == 0&&swipecardhelp.getArray(0, 1) == 0&&swipecardhelp.getArray(0, 2) == 0){
                    //掉过领用直接安装
                    CarTravelHelper.carTravelRecord.setDLGJ_LYKH(cardNumber);
                    CarTravelHelper.carTravelRecord.setDLGJ_LYSJ(new Date());
                }
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
                SwipingCardUtils swipecardhelp = SwipingCardUtils.swipecardhelp(getApplicationContext());
                if (swipecardhelp.getArray(0, 0) == 0&&swipecardhelp.getArray(0, 1) == 0&&swipecardhelp.getArray(0, 2) == 0){
                    //掉过领用直接安装
                    CarTravelHelper.carTravelRecord.setDLGJ_LYXM(persionInfo.getDLGJ_XM());
                    CarTravelHelper.carTravelRecord.setDLGJ_LYJH(persionInfo.getDLGJ_JH());
                    CarTravelHelper.carTravelRecord.setDLGJ_LYBM(persionInfo.getDLGJ_BMMC());
                }
                CarTravelHelper.carTravelRecord.setDLGJ_AZXM(persionInfo.getDLGJ_XM());
                CarTravelHelper.carTravelRecord.setDLGJ_AZJH(persionInfo.getDLGJ_JH());
                CarTravelHelper.carTravelRecord.setDLGJ_AZBM(persionInfo.getDLGJ_BMMC());
                CarTravelHelper.saveCarTravelRecordToDB(CarTravelHelper.carTravelRecord);
                //上传主信息记录
                startService(intentService);
            }
            if (step == 2) {
                CarTravelHelper.carTravelRecord.setDLGJ_SCXM(persionInfo.getDLGJ_XM());
                CarTravelHelper.carTravelRecord.setDLGJ_KSJH(persionInfo.getDLGJ_JH());
                CarTravelHelper.carTravelRecord.setDLGJ_KSBM(persionInfo.getDLGJ_BMMC());
                CarTravelHelper.saveCarTravelRecordToDB(CarTravelHelper.carTravelRecord);
                //上传主信息记录
                startService(intentService);
            }
            if (step == 3) {
                LogUtils.a("开始存储归还信息本地");
                CarTravelHelper.carTravelRecord.setDLGJ_JHXM(persionInfo.getDLGJ_XM());
                CarTravelHelper.carTravelRecord.setDLGJ_JHJH(persionInfo.getDLGJ_JH());
                CarTravelHelper.carTravelRecord.setDLGJ_JHBM(persionInfo.getDLGJ_BMMC());
                CarTravelHelper.saveCarTravelRecordToDB(CarTravelHelper.carTravelRecord);
            }
        }
    }

    @Override
    public void showPersionInfo(PersionInfo persionInfo) {
        if (persionInfo != null && persionInfo.getType() == 1) {//type = 0结果暂时添加
            tvCarNameTextView.setText(persionInfo.getName());
            tvAlarmTextView.setText(persionInfo.getAlarm());
            tvDeptTextView.setText(persionInfo.getDepName());
            if (CarTravelHelper.carTravelRecord != null) {
                CarTravelHelper.carTravelRecord.setDLGJ_LYXM(persionInfo.getName());
                CarTravelHelper.saveCarTravelRecordToDB(CarTravelHelper.carTravelRecord);
            }
            startService(intentService);
        } else if (persionInfo != null && persionInfo.getType() == 2) {
            tvCarNameTextView.setText(persionInfo.getName());
            tvDeptTextView.setText(persionInfo.getDepName());
            startService(intentService);
        } else  {
            startService(intentService);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
