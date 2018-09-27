package com.zxdz.car.main.view.lock;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
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
import com.blankj.utilcode.util.ToastUtils;
import com.jakewharton.rxbinding.view.RxView;
import com.zxdz.car.App;
import com.zxdz.car.R;
import com.zxdz.car.base.helper.CarTravelHelper;
import com.zxdz.car.base.helper.UnWarnInfoHelper;
import com.zxdz.car.base.helper.WarnInfoHelper;
import com.zxdz.car.base.utils.AudioPlayUtils;
import com.zxdz.car.base.view.BaseActivity;
import com.zxdz.car.main.contract.PersionInfoContract;
import com.zxdz.car.main.model.domain.PoliceInfoAll;
import com.zxdz.car.main.model.domain.UnWarnInfo;
import com.zxdz.car.main.model.domain.WarnInfo;
import com.zxdz.car.main.presenter.PersionInfoPresenter;
import com.zxdz.car.main.service.UploadDataService;
import com.zxdz.car.main.utils.BlueToothHelper;
import com.zxdz.car.main.utils.BlueToothUtils;
import com.zxdz.car.main.view.CarTrailActivity;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;
import rx.functions.Action1;

@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
public class OpenCardActivity extends BaseActivity<PersionInfoPresenter> implements PersionInfoContract.View {

    @BindView(R.id.toolbar)
    Toolbar mToolBar;
    @BindView(R.id.tv_car_name)
    TextView tvCarName;
    @BindView(R.id.tv_car_number)
    TextView tvCarNumber;
    @BindView(R.id.tv_auth_state)
    TextView tvAuthState;
    @BindView(R.id.layout_card_info)
    LinearLayout layoutCardInfo;
    @BindView(R.id.layout_card_read_wait)
    LinearLayout layoutCardReadWait;
    @BindView(R.id.btn_next_success)
    Button btnNextSuccess;
    @BindView(R.id.layout_next)
    LinearLayout layoutNext;
    @BindView(R.id.open_working)
    TextView openWorking;
    @BindView(R.id.police_change)
    TextView policeChange;
    private String cardNum = "无";
    private SweetAlertDialog initDialog;
    private boolean flag = true;
    private boolean tag = false;//非法开锁标志
    private AudioPlayUtils audioPlayUtils;
    private Intent intentService;


    private String mcarNumber;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            String obj = (String) msg.obj;
            LogUtils.a("mHandler" + obj);
            if (obj.equals("开锁成功")) {
                if (msg.what == 2) {
                    mHandler.removeCallbacks(runnable3);
                    App.GravityListener_type = 1;//开启手持机移动报警
                    Intent intent2 = new Intent(OpenCardActivity.this, CarTrailActivity.class);
                    intent2.putExtra("car_trail", 2);
                    startActivity(intent2);
                    //根据读取的卡号查询人员信息
                    savedate(2);
                    Log.e("requestinfo", "请求干警信息");
                    mPresenter.getPersionInfo(cardNum);
                    if (!TextUtils.equals(cardNum, null)) {
                        mcarNumber = cardNum;
                        // mHandler.postDelayed(mRunnable, 500);
                    }
                    return;
                }
                if (flag) {
                    flag = false;
                    callPolice(1);
                    audioPlayUtils = new AudioPlayUtils(OpenCardActivity.this, R.raw.baojing);
                    audioPlayUtils.play(true);
                    tag = true;
                    openWorking.setText("非法开锁，报警中");
                }
            } else if (obj.equals("上锁成功")) {
                if (audioPlayUtils != null) {
                    openWorking.setText("工作中,工作完成后请刷卡");
                    if (tag) {
                        callPolice(2);
                        //audioPlayUtils.stop();
                    }
                    flag = true;
                }
            } else if (obj.equals("失败成功")) {
                openlock(2);
            }
        }
    };

    private int step;
    private boolean statecallpolice = false;

    @Override
    public int getLayoutId() {
        return R.layout.activity_open_card;
    }

    @Override
    public void init() {
        setSupportActionBar(mToolBar);
        mToolBar.setNavigationIcon(R.mipmap.back_icon);
        step = getIntent().getIntExtra("blue_step", 0);
        if (step == 0) {
            audioPlayUtils = new AudioPlayUtils(this, R.raw.zpscwc_qdcmjcxazsb);
            audioPlayUtils.play();
            BlueToothHelper.getBlueHelp().setReceiverMode(new BlueToothUtils.receiveCardIDListener() {
                @Override
                public void receiveCardID(String str) {
                    if (statecallpolice) {//刷完卡解除报警
                        audioPlayUtils.stop();
                        statecallpolice = false;
                        callPolice(2);
//                        if (dialog != null) {
//                            dialog.dismiss();
//                        }


                    }
                    showCardNumber(str);
                }
            });
            //开启强拆报警监测
            BlueToothHelper.getBlueHelp().openCallPolices(new BlueToothUtils.openCallPoliceListener() {
                @Override
                public void openCallPolice() {
                    statecallpolice = true;
                    //policeingunclick();
                    audioPlayUtils = new AudioPlayUtils(OpenCardActivity.this, R.raw.ydbj);
                    audioPlayUtils.play(true);
                    callPolice(1);
                }
            });
        }
        mPresenter = new PersionInfoPresenter(this, this);
        intentService = new Intent(this, UploadDataService.class);
        //上传主信息记录
        App.UPLOAD_STEP = 5;
        startService(intentService);
        RxView.clicks(btnNextSuccess).throttleFirst(200, TimeUnit.MILLISECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                ToastUtils.showLong("刷卡成功，开始开锁");
                Intent intent = new Intent(OpenCardActivity.this, BlueToothActivity.class);
                intent.putExtra("car_trail", cardNum);
                OpenCardActivity.this.setResult(RESULT_OK, intent);
                OpenCardActivity.this.finish();
            }
        });
        policeChange.setVisibility(View.GONE);
        //模拟测试步骤，后期删除
        // layoutNext.setVisibility(View.VISIBLE);
    }

    public void showCardNumber(String carNumber) {
        carNumber = carNumber.replaceAll(" ", "");
        if (judgment()) {
            return;
        }
//        if (audioPlayUtils != null) {
//            audioPlayUtils.stop();
//
        tvCarNumber.setText(carNumber);
        /*String getname = getname(carNumber);
        tvCarName.setText(getname);*/
//        layoutCardReadWait.setVisibility(View.GONE);
//        layoutCardInfo.setVisibility(View.VISIBLE);
//        layoutNext.setVisibility(View.VISIBLE);
//        tvAuthState.setText(getResources().getText(R.string.card_auth_success_text));
        cardNum = carNumber;
        openlock(2);
        /*Intent intent = new Intent(OpenCardActivity.this, BlueToothActivity.class);
        intent.putExtra("car_trail", cardNum);
        intent.putExtra("blue_step",2);
        startActivity(intent);
        finish();*/
    }

    private void openlock(final int i) {
        checkOpenLock("开锁中");
        //mHandler.postDelayed(runnable3, 10000);
        BlueToothHelper.getBlueHelp().openLock(new BlueToothUtils.OpenLockListenter() {
            @Override
            public void openable(String str) {
                // TODO: 2018/5/9 语音提示可以开锁
                //mHandler.removeCallbacks(runnable3);
                Message message = new Message();
                message.obj = str;
                message.what = i;
                mHandler.sendMessage(message);
                initDialog.dismissWithAnimation();
            }
        });
    }

    Runnable runnable3 = new Runnable() {
        @Override
        public void run() {
            LogUtils.a("定时重新开锁");
            openlock(2);
        }
    };


    @OnClick({R.id.police_change, R.id.btn_next_success})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.police_change:
                break;
            case R.id.btn_next_success:
                openlock(2);
                break;
        }
    }

    public void checkOpenLock(String msg) {
        try {
            if (initDialog == null) {
                initDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
                initDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                initDialog.setTitleText(msg);
                initDialog.setCancelable(false);
                initDialog.show();
                initDialog.setCanceledOnTouchOutside(true);
            } else {
                if (!initDialog.isShowing()) {
                    initDialog.setTitleText(msg);
                    initDialog.show();
                } else {
                    initDialog.dismissWithAnimation();
                    initDialog.setTitleText(msg);
                    initDialog.show();
                }
            }
        } catch (Exception e) {
            LogUtils.a("benbenbenebn");
        }
    }

    private void savedate(int i) {
        cardNum = cardNum.replaceAll(" ", "");
        if (i == 2) {//开锁
            CarTravelHelper.carTravelRecord.setDLGJ_KSSJ(new Date());
            CarTravelHelper.carTravelRecord.setDLGJ_KSKH(cardNum);
            CarTravelHelper.carTravelRecord.setZT(50);
            CarTravelHelper.saveCarTravelRecordToDB(CarTravelHelper.carTravelRecord);
            App.SWIPE_STEP = 6;
            //上传主信息记录
            App.UPLOAD_STEP = 5;
        }
    }

    private boolean judgment() {
        // TODO: 2017\12\22 0022  //是否已经安装设备，没有则语音提示请安装
        return false;
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
            warnInfo.setWarnContent("强拆锁");
            warnInfo.setWarnDate(new Date());
            warnInfo.setWarnType(0);
            warnInfo.setDataState(0);
            warnInfo.setWarnPoliceNum(policeNum);
            warnInfo.setWarnFlag(0);
            WarnInfoHelper.saveWarnInfoToDB(warnInfo);
            startService(intentService);
        } else if (i == 2) {//取消报警
            LogUtils.a("开始储存取消报警数据");
            UnWarnInfo warnInfoListByID = UnWarnInfoHelper.getWarnInfoListByID(CarTravelHelper.carTravelRecord.getId(), false);
            warnInfoListByID.setFlag(true);
            UnWarnInfoHelper.saveWarnInfoToDB(warnInfoListByID);
            startService(intentService);
        }
    }


    @Override
    public void hideStateView() {

    }

    @Override
    public void showNoNet() {

    }

    @Override
    public void showPoliceInfoAll(PoliceInfoAll persionInfo) {
        //mHandler.removeCallbacks(mRunnable);
        if (persionInfo != null) {
            if (CarTravelHelper.carTravelRecord != null) {
                CarTravelHelper.carTravelRecord.setDLGJ_KSXM(persionInfo.getDLGJ_XM());
                CarTravelHelper.carTravelRecord.setDLGJ_KSJH(persionInfo.getDLGJ_JH());
                CarTravelHelper.carTravelRecord.setDLGJ_KSBM(persionInfo.getDLGJ_BMMC());
                CarTravelHelper.saveCarTravelRecordToDB(CarTravelHelper.carTravelRecord);
            }
        }
        startService(intentService);
        finish();
    }
}