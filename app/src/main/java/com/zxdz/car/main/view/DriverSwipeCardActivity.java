package com.zxdz.car.main.view;

import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.Toolbar;
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
import com.zxdz.car.main.model.domain.DriverInfo;
import com.zxdz.car.main.model.domain.PersionInfo;
import com.zxdz.car.main.model.domain.PoliceInfoAll;
import com.zxdz.car.main.presenter.PersionInfoPresenter;
import com.zxdz.car.main.service.UploadDataService;
import com.zxdz.car.main.utils.BlueToothHelper;
import com.zxdz.car.main.utils.BlueToothUtils;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import rx.functions.Action1;

/**
 * Created by super on 2017/10/22.
 * 驾驶员刷卡界面
 */

public class DriverSwipeCardActivity extends BaseActivity<PersionInfoPresenter> implements PersionInfoContract.View {

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

    @BindView(R.id.tv_driver_name)
    TextView tvDriverName;

    @BindView(R.id.tv_car_number)
    TextView tvCarNumber;

    @BindView(R.id.tv_id_card)
    TextView tvIdCardName;

    @BindView(R.id.tv_dept_name)
    TextView tvDeptName;

    @BindView(R.id.tv_reason)
    TextView tvReasonName;


    private boolean flag = true;//跳转标准，避免多次多卡触发重复跳转

    private Intent intentService;

    @Override
    public int getLayoutId() {
        return R.layout.activity_driver_card;
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    public void init() {
        App.SWIPE_STEP = 3;
        AudioPlayUtils.getAudio(this, R.raw.qscjsysk).play();
        mToolBar.setTitle("驾驶员刷卡");
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
                //此处直接跳转到等待安装界面
                handler.removeCallbacks(runnable);
                Intent intent = new Intent(DriverSwipeCardActivity.this, InstallWaitActivity.class);
                startActivity(intent);
                finish();
            }
        });

        //模拟测试步骤，后期删除
        //TODO
        //mNextLayout.setVisibility(View.VISIBLE);
    }

  /*  @Override
    protected void onResume() {
        super.onResume();
        if (App.readCardType == 1) {
            new OpenT().start();
        } else {
            new InitT().start();
        }
    }

    @Subscribe(
            thread = EventThread.MAIN_THREAD,
            tags = {
                    @Tag(Constant.READ_CARD_NUMBER)
            }
    )*/
    /**
     * 显示读取到的卡号
     */
    public void showCardNumber(String carNumber) {
        if (App.SWIPE_STEP == 3 && flag) {
            flag = false;
            if (StringUtils.isEmpty(carNumber)) {
                ToastUtils.showLong("卡号读取错误，请重试");
                return;
            }
            //去除卡号中的空格
            carNumber = carNumber.replaceAll(" ", "");

            mCardNumberTextView.setText(carNumber);
            mReadWaitLayout.setVisibility(View.GONE);

            //存储卡号到本地数据库
            saveAdminCard(carNumber);
            mCardInfoLayout.setVisibility(View.VISIBLE);
            mNextLayout.setVisibility(View.VISIBLE);
            handler.postDelayed(runnable, 2000);
            //根据读取的卡号查询人员信息
            mPresenter.getPersionInfo(carNumber);
            //上传主信息记录
            App.UPLOAD_STEP = 2;

        }
    }

    /**
     * 读卡成功保存【驾驶员卡号】
     *
     * @param cardNumber
     */
    public void saveAdminCard(String cardNumber) {
        if (CarTravelHelper.carTravelRecord != null) {
            CarTravelHelper.carTravelRecord.setWLJSYKH(cardNumber);
            CarTravelHelper.carTravelRecord.setWLJSYSJ(new Date());
            CarTravelHelper.carTravelRecord.setZT(11);//暂时增加一个状态，驾驶员刷完卡
            CarTravelHelper.saveCarTravelRecordToDB(CarTravelHelper.carTravelRecord);
            LogUtils.e("读取到的驾驶员卡号--->" + CarTravelHelper.carTravelRecord.getWLJSYKH());
        }
    }

    @Override
    public void hideStateView() {

    }

    @Override
    public void showNoNet() {

    }

    @Override
    public void showPersionInfo(PersionInfo persionInfo) {

        if (persionInfo != null && persionInfo.getType() == 2) {
            tvCarNumber.setText(!StringUtils.isEmpty(persionInfo.getCph()) ? persionInfo.getCph() : "苏AGH642(读取不到车牌，显示默认)");
            tvDriverName.setText(persionInfo.getName());
            tvIdCardName.setText(persionInfo.getSfz());
            tvDeptName.setText(persionInfo.getDepName());
            tvReasonName.setText(persionInfo.getRemark());
        }

        startService(intentService);

        if (App.DRIVER_SWIPE == 0) {
           handler.postDelayed(runnable, 2000);
        }
    }

    @Override
    public void showPoliceInfoAll(PoliceInfoAll persionInfo) {

    }

    @Override
    public void showdriverInfo(DriverInfo driverInfo) {

    }


    Runnable runnable=new Runnable() {
        @Override
        public void run() {
            mNextButton.callOnClick();
        }
    };

}
