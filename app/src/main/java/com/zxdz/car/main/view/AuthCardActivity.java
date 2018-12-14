package com.zxdz.car.main.view;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.jakewharton.rxbinding.view.RxView;
import com.zxdz.car.App;
import com.zxdz.car.R;
import com.zxdz.car.base.helper.CarTravelHelper;
import com.zxdz.car.base.helper.CardHelper;
import com.zxdz.car.base.utils.AudioPlayUtils;
import com.zxdz.car.base.utils.SwipingCardUtils;
import com.zxdz.car.base.view.BaseActivity;
import com.zxdz.car.main.contract.PersionInfoContract;
import com.zxdz.car.main.model.domain.CarTravelRecord;
import com.zxdz.car.main.model.domain.CardInfo;
import com.zxdz.car.main.model.domain.PersionInfo;
import com.zxdz.car.main.model.domain.PoliceInfoAll;
import com.zxdz.car.main.presenter.PersionInfoPresenter;
import com.zxdz.car.main.service.UploadDataService;
import com.zxdz.car.main.utils.BlueToothHelper;
import com.zxdz.car.main.utils.BlueToothUtils;
import com.zxdz.car.main.view.lock.OpenCardActivity;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.functions.Action1;

/**
 * Created by super on 2017/10/22.
 * 管理员刷卡认证页面
 */
@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
public class AuthCardActivity extends BaseActivity<PersionInfoPresenter> implements PersionInfoContract.View {

    @BindView(R.id.toolbar)
    Toolbar mToolBar;
    @BindView(R.id.btn_success)
    Button mSuccessButton;
    @BindView(R.id.tv_auth_state)
    TextView mCardStateTextView;
    @BindView(R.id.auth_layout_card_read_wait)
    LinearLayout mReadWaitLayout;
    @BindView(R.id.layout_card_info)
    LinearLayout mCardInfoLayout;
    @BindView(R.id.layout_next)
    LinearLayout mNextLayout;
    @BindView(R.id.tv_car_number)
    TextView mCardNumberTextView;
    AudioPlayUtils audioPlayUtils;
    public String flag = "0";//跳转标准，避免多次多卡触发重复跳转
    @BindView(R.id.tv_reason1)
    LinearLayout tvReason1;
    @BindView(R.id.tv_reason)
    TextView tvReason;
    @BindView(R.id.plate_number1)
    LinearLayout plateNumber1;
    @BindView(R.id.plate_number)
    TextView plateNumber;
    @BindView(R.id.tv_id_card1)
    LinearLayout tvIdCard1;
    @BindView(R.id.tv_id_card)
    TextView tvIdCard;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_auth_state1)
    LinearLayout tvAuthState1;
    @BindView(R.id.tv_dept)
    TextView tvDept;
    @BindView(R.id.tv_dept1)
    LinearLayout tvDept1;
    @BindView(R.id.tv_alarm)
    TextView tvAlarm;
    @BindView(R.id.tv_alarm1)
    LinearLayout tvAlarm1;
    private Intent intentService;
    SwipingCardUtils swipecardhelp;
    private int i = 0;

    @Override
    public int getLayoutId() {
        return R.layout.activity_auth_card;
    }

    @Override
    public void init() {
        App.IsNewLS=true;
        App.LSID = 0L;
        CarTravelHelper.carTravelRecord = null;
        swipecardhelp = SwipingCardUtils.swipecardhelp(getApplicationContext());
        if (swipecardhelp.getArray(0, 0) == 1) {
            App.SWIPE_STEP = 1;
            mToolBar.setTitle("刷管理员授权卡");
            audioPlayUtils = new AudioPlayUtils(getApplicationContext(), R.raw.glyqrsbsk);
            audioPlayUtils.play();
        } else {
            if (swipecardhelp.getArray(0, 1) == 1) {
                handler.postDelayed(runnable, 500);
            } else {
                if (swipecardhelp.getArray(0, 2) == 0) {
                    App.DRIVER_SWIPE = 1;
                }
                App.POLICE_SWIPE = 1;
                handler.postDelayed(runnable, 500);
            }
        }
        mPresenter = new PersionInfoPresenter(getApplicationContext(), this);
        intentService = new Intent(getApplicationContext(), UploadDataService.class);
        setSupportActionBar(mToolBar);
       /* mToolBar.setNavigationIcon(R.mipmap.back_icon);
        mToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });*/
        registerSwipeCard();
        //值班人员刷管理员卡成功，下一步
        RxView.clicks(mSuccessButton).throttleFirst(200, TimeUnit.MILLISECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                if (App.POLICE_SWIPE == 0) {
                    App.SWIPE_STEP = 2;
                    audioPlayUtils = new AudioPlayUtils(getApplicationContext(), R.raw.qdcmjqrlysk);
                    audioPlayUtils.play();
                    mToolBar.setTitle("带车民警刷卡");
                    return;
                } else {
                    if (App.DRIVER_SWIPE == 0) {
                        //swipeCard(3);
                        App.SWIPE_STEP = 3;
                        audioPlayUtils = new AudioPlayUtils(getApplicationContext(), R.raw.qscjsysk);
                        audioPlayUtils.play();
                        mToolBar.setTitle("驾驶员刷卡");
                        return;
                    } else {
                        Intent intent = null;
                        //判断是否刷确认安装步骤
                        if (SwipingCardUtils.swipecardhelp(AuthCardActivity.this).getArray(1, 1) == 1) {
                            intent = new Intent(AuthCardActivity.this, InstallConfirmActivity.class);
                            intent.putExtra("confirm_step", 1);
                        } else {
                            intent = new Intent(AuthCardActivity.this, CarTrailActivity.class);
                            App.GravityListener_type = 1;
                            App.SWIPE_STEP = 4;
                            App.UPLOAD_STEP = 3;
                            App.GravityListener_type = 1;
                            intent.putExtra("car_trail", 1);//进入时记录路线
                        }
                        startActivity(intent);
                        finish();
                    }
                }
                //1.测试[获取带领干警刷卡信息]
                //mPresenter.getPersionInfo("e4f33226");
            }
        });

        //mNextLayout.setVisibility(View.VISIBLE);
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                for (int i = 0; i <1000 ; i++) {
//                    PoliceinfoLocal policeinfoLocal = new PoliceinfoLocal(null,"AAFDB"+i,"张"+i,null,null);
//                    PoliceinfoLocalHelper.savePoliceinfoLocalToDB(policeinfoLocal);
//                    if (i == 999){
//                        LogUtils.e("ganjinxingxi","数据添加完成");
//                    }
//                }
//            }
//        }).start();
    }

    //注册刷卡监听
    public void registerSwipeCard() {
        BlueToothHelper.getBlueHelp().setReceiverMode(new BlueToothUtils.receiveCardIDListener() {
            @Override
            public void receiveCardID(final String str) {
                LogUtils.a("刷卡返回1：", i);
                showCardNumber(str);
            }
        });
    }


    /**
     * 显示读取到的卡号
     */
    public void showCardNumber(String carNumber) {
        if (StringUtils.isEmpty(carNumber)) {
            ToastUtils.showLong("卡号读取错误，请重试");
            return;
        }
        //去除卡号中的空格
        carNumber = carNumber.replaceAll(" ", "");
        LogUtils.a("卡号--->" + carNumber);
        //公共方法，后面很多步骤需要判断，都可用到
        //判断卡号是否存在
        if (App.SWIPE_STEP == 1) {
            CardInfo cardInfo = CardHelper.validateCardNumber(carNumber, 0);
            //卡号认证成功
            if (cardInfo != null) {
                mCardNumberTextView.setText(carNumber);
                String adminName = cardInfo.getAdminName();
                mReadWaitLayout.setVisibility(View.GONE);
                mCardInfoLayout.setVisibility(View.VISIBLE);
                tvName.setText(adminName);
                mNextLayout.setVisibility(View.VISIBLE);
                mCardStateTextView.setText(getResources().getText(R.string.card_auth_success_text));
                saveAdminCard(carNumber, 1);
                if (swipecardhelp.getArray(0, 1) == 1) {
                    mSuccessButton.callOnClick();
                } else {
                    if (swipecardhelp.getArray(0, 2) == 0) {
                        App.DRIVER_SWIPE = 1;
                    }
                    App.POLICE_SWIPE = 1;
                    handler.postDelayed(runnable, 1500);
                }
            } else {
                mCardStateTextView.setVisibility(View.GONE);
                audioPlayUtils = new AudioPlayUtils(getApplicationContext(), R.raw.bsgly_qglysk);
                audioPlayUtils.play();
            }
        } else if (App.SWIPE_STEP == 2) {
            tvAuthState1.setVisibility(View.GONE);
            tvAlarm1.setVisibility(View.VISIBLE);
            tvDept1.setVisibility(View.VISIBLE);

            mReadWaitLayout.setVisibility(View.GONE);
            mCardInfoLayout.setVisibility(View.VISIBLE);
            mNextLayout.setVisibility(View.VISIBLE);
            mCardNumberTextView.setText(carNumber);
            App.POLICE_SWIPE = 1;//干警刷完卡就不可以刷了
            saveAdminCard(carNumber, 2);//在管理员不刷时step要变动
//            LogUtils.e("ganjinxingxi","开始查询");
//            PoliceinfoLocal policeinfoLocal = PoliceinfoLocalHelper.getPoliceinfoLocalListByLSID(carNumber).get(0);
//            if (policeinfoLocal != null){
//                LogUtils.e("ganjinxingxi","姓名："+policeinfoLocal.getDLGJXM()+"卡号："+policeinfoLocal.getDLGJKH());
//            }
            if (swipecardhelp.getArray(0, 2) == 0) {
                App.DRIVER_SWIPE = 1;
            }
            handler.postDelayed(runnable, 1500);
            mPresenter.getPersionInfo(carNumber);
        } else if (App.SWIPE_STEP == 3) {
            tvAuthState1.setVisibility(View.GONE);
            tvAlarm1.setVisibility(View.GONE);
            tvDept1.setVisibility(View.GONE);
            tvReason1.setVisibility(View.VISIBLE);
            plateNumber1.setVisibility(View.VISIBLE);
            tvIdCard1.setVisibility(View.VISIBLE);

            mReadWaitLayout.setVisibility(View.GONE);
            mCardInfoLayout.setVisibility(View.VISIBLE);
            mNextLayout.setVisibility(View.VISIBLE);
            mCardNumberTextView.setText(carNumber);
            App.DRIVER_SWIPE = 1;
            saveAdminCard(carNumber, 3);
            mPresenter.getPersionInfo(carNumber);
            handler.postDelayed(runnable, 1500);
        }

        //判断是否是更换干警时的管理员确认刷卡
//                if (changPolice == 1) {//跟换干警刷卡
//                    LogUtils.a("即将更换干警");
//                    jumpNextAct();
//                } else {
//                    saveAdminCard(carNumber);
//                    mSuccessButton.callOnClick();
//                }
           /* if (App.POLICE_SWIPE == 0) {
                //播放音频提示文件
                //audioPlayUtils = new AudioPlayUtils(this, R.raw.qdlgjsk);
                //audioPlayUtils.play();
                swipeCard(2);
                return;
            } else {
                if (App.DRIVER_SWIPE == 0) {
                    audioPlayUtils = new AudioPlayUtils(this, R.raw.qjsysk);
                    audioPlayUtils.play();
                    swipeCard(3);
                    return;
                } else {
                    ToastUtils.showLong("认证成功，请输入车辆信息");
                    Intent intent = new Intent(AuthCardActivity.this, InputCarInfoActivity.class);
                    startActivity(intent);
                }
            }*/
//        }

    }


    //更换干警功能从管理员刷卡页面之间跳转
   /* private void jumpNextAct() {
        int aClass = getIntent().getIntExtra("class", 0);
        //  String policeNum = getIntent().getStringExtra("newPoliceNew");
        //   SharedPreferences sp = getSharedPreferences("activity", Context.MODE_WORLD_READABLE);
        //  SharedPreferences.Editor edit = sp.edit();
        // String oldPoliceNum = sp.getString("policeNum", "");
        //  edit.putString("policeNum", policeNum);
        //  edit.commit();
        // saveChangeNum(oldPoliceNum, policeNum);
        Intent intent = new Intent();
        switch (aClass) {
            case 2:
                intent.setClass(this, InstallConfirmActivity.class);
                intent.putExtra("confirm_step", 11);
                startActivity(intent);
                finish();
                break;
            case 3:
                intent.setClass(this, InstallConfirmActivity.class);
                intent.putExtra("confirm_step", 22);
                startActivity(intent);
                finish();
                break;
            case 5:
                intent.setClass(this, OpenCardActivity.class);
                //intent.putExtra("police_card", policeNum);
                intent.putExtra("blue_step", 1);
                startActivity(intent);
                this.finish();
                break;
            case 6:
                audioPlayUtils = new AudioPlayUtils(this, R.raw.gclcjs);
                audioPlayUtils.play();
                intent.setClass(this, MainActivity.class);
                intent.putExtra("end", 1);
                startActivity(intent);
                finish();
                break;
        }
        finish();
    }*/

    private void saveChangeNum(String oldPoliceNum, String newPoliceNum) {
        //TODO 保存跟换干警卡号记录

    }


    /**
     * 读卡成功保存管理员卡号
     *
     * @param cardNumber
     */
    public void saveAdminCard(String cardNumber, int step) {
        if (CarTravelHelper.carTravelRecord == null) {
            CarTravelHelper.carTravelRecord = new CarTravelRecord();
            CarTravelHelper.carTravelRecord.setLS_ID(0);
            CarTravelHelper.carTravelRecord.setId(System.currentTimeMillis());
            CarTravelHelper.carTravelRecord.setZDJ_ID(App.ZDJID);
        }
        if (step == 1) {
            CarTravelHelper.carTravelRecord.setGLRY(cardNumber);
            CarTravelHelper.carTravelRecord.setGLRYSJ(new Date());
            CarTravelHelper.carTravelRecord.setZT(0);//此时还未领用
        } else if (step == 2) {
            CarTravelHelper.carTravelRecord.setDLGJ_LYKH(cardNumber);
            CarTravelHelper.carTravelRecord.setDLGJ_LYSJ(new Date());
            CarTravelHelper.carTravelRecord.setZT(10);//领用
        } else if (step == 3) {
            CarTravelHelper.carTravelRecord.setWLJSYKH(cardNumber);
            CarTravelHelper.carTravelRecord.setWLJSYSJ(new Date());
            CarTravelHelper.carTravelRecord.setZT(11);//暂时增加一个状态，驾驶员刷完卡
        }
        CarTravelHelper.saveCarTravelRecordToDB(CarTravelHelper.carTravelRecord);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
        // BlueToothUtils.getBlueHelp().removeListener();
    }

    @Override
    public void hideStateView() {

    }

    @Override
    public void showNoNet() {

    }


    @Override
    public void showPersionInfo(PersionInfo persionInfo) {
        LogUtils.a("1111","获取到的人员信息--->" + JSON.toJSON(persionInfo));
        if (persionInfo != null && persionInfo.getType() == 1) {//type = 0结果暂时添加
            tvName.setText(persionInfo.getName());
            tvAlarm.setText(persionInfo.getAlarm());
            tvDept.setText(persionInfo.getDepName());
            if (CarTravelHelper.carTravelRecord != null) {
                CarTravelHelper.carTravelRecord.setDLGJ_LYXM(persionInfo.getName());
                CarTravelHelper.saveCarTravelRecordToDB(CarTravelHelper.carTravelRecord);
            }
            //上传主信息记录
            App.UPLOAD_STEP = 1;
            LogUtils.a("1111","开始保存数据");
            startService(intentService);
        } else if (persionInfo != null && persionInfo.getType() == 2) {
            plateNumber.setText(!StringUtils.isEmpty(persionInfo.getCph()) ? persionInfo.getCph() : "苏AGH642(读取不到车牌，显示默认)");
            tvName.setText(persionInfo.getName());
            tvIdCard.setText(persionInfo.getSfz());
            tvDept.setText(persionInfo.getDepName());
            tvReason.setText(persionInfo.getRemark());
            startService(intentService);
        } else if (persionInfo == null) {
            startService(intentService);
        }
    }

    @Override
    public void showPoliceInfoAll(PoliceInfoAll persionInfo) {
        if (persionInfo != null) {
            tvName.setText(persionInfo.getDLGJ_XM());
            tvAlarm.setText(persionInfo.getDLGJ_JH());
            tvDept.setText(persionInfo.getDLGJ_BMMC());
            if (CarTravelHelper.carTravelRecord != null) {
                CarTravelHelper.carTravelRecord.setDLGJ_LYXM(persionInfo.getDLGJ_XM());
                CarTravelHelper.saveCarTravelRecordToDB(CarTravelHelper.carTravelRecord);
            }
            //上传主信息记录
            App.UPLOAD_STEP = 1;
            startService(intentService);
        }
    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            mSuccessButton.callOnClick();
        }
    };
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
