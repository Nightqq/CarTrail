package com.zxdz.car.main.view;

import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.zxdz.car.App;
import com.zxdz.car.R;
import com.zxdz.car.base.helper.CarTravelHelper;
import com.zxdz.car.base.view.BaseActivity;
import com.zxdz.car.main.utils.BlueToothHelper;
import com.zxdz.car.main.utils.BlueToothUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class InitReturnActivity extends BaseActivity {

    @BindView(R.id.init_return_electricity)
    TextView initReturnElectricity;
    @BindView(R.id.toolbar)
    Toolbar mToolBar;

    @Override
    public void init() {
        ButterKnife.bind(this);

        mToolBar.setTitle("确认行程结束");
        setSupportActionBar(mToolBar);
        App.GravityListener_type = 0;//关闭手持机移动报警
        if (CarTravelHelper.carTravelRecord != null) {
            //存储管理员初始化数据等待上传
            LogUtils.a("初始化数据等待上传");
            CarTravelHelper.carTravelRecord.setZT(80);
            CarTravelHelper.saveCarTravelRecordToDB(CarTravelHelper.carTravelRecord);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        BlueToothHelper.getBlueHelp().enquiriesState(new BlueToothUtils.EnquiriesStateListenter() {
            @Override
            public void enquiriesState(String str) {

            }

            @Override
            public void enquiriesPower(final String str) {
                LogUtils.i("车锁电压11", str);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        initReturnElectricity.setText(str);
                        //ToastUtils.showShort(str);
                    }
                });

            }
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_init_return;
    }
}
