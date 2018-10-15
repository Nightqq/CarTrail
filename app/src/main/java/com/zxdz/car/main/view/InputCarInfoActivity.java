package com.zxdz.car.main.view;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.jakewharton.rxbinding.view.RxView;
import com.zxdz.car.R;
import com.zxdz.car.base.helper.CarTravelHelper;
import com.zxdz.car.base.view.BaseActivity;
import java.util.concurrent.TimeUnit;
import butterknife.BindView;
import rx.functions.Action1;

/**
 * Created by super on 2017/10/22.
 * 车辆信息录入
 */

public class InputCarInfoActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolBar;

    @BindView(R.id.spinner_car_way)
    Spinner mCarWaySpinner;

    @BindView(R.id.btn_next_step)
    Button mNextStepButton;

    @BindView(R.id.ev_car_number)
    EditText mCarNumberEditText;

    @Override
    public int getLayoutId() {
        return R.layout.activity_input_carinfo;
    }

    @Override
    public void init() {
        mToolBar.setTitle("车辆信息录入");
        setSupportActionBar(mToolBar);
        /*mToolBar.setNavigationIcon(R.mipmap.back_icon);
        mToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });*/
        mCarNumberEditText.setText("苏A12345");
        if (CarTravelHelper.carTravelRecord == null) {
            CarTravelHelper.carTravelRecord.setSJJHBZ(10);
            CarTravelHelper.saveCarTravelRecordToDB(CarTravelHelper.carTravelRecord);
        }

        RxView.clicks(mNextStepButton).throttleFirst(200, TimeUnit.MILLISECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                if (StringUtils.isEmpty(mCarNumberEditText.getText())) {
                    ToastUtils.showLong("请输入车牌号");
                    return;
                }
                if (!isCarNumberValidate(mCarNumberEditText.getText().toString().trim())) {
                    ToastUtils.showLong("车牌号格式有误，请重新输入");
                    return;
                }
                Intent intent = new Intent(InputCarInfoActivity.this, InstallWaitActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    public static boolean isCarNumberValidate(String carNumber) {
        /*
         车牌号格式：汉字 + A-Z + 5位A-Z或0-9
        （只包括了普通车牌号，教练车和部分部队车等车牌号不包括在内）
         */
        String carNumRegex = "[\u4e00-\u9fa5]{1}[A-Z]{1}[A-Z_0-9]{5}";
        return carNumber.matches(carNumRegex);
    }
}
