package com.zxdz.car.main.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.zxdz.car.R;
import com.zxdz.car.base.helper.CarTravelHelper;
import com.zxdz.car.base.view.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class InitReturnActivity extends BaseActivity {

    @BindView(R.id.init_return_electricity)
    TextView initReturnElectricity;

    @Override
    public void init() {
        ButterKnife.bind(this);
        if (CarTravelHelper.carTravelRecord!=null){
            //存储管理员初始化数据等待上传
        }
    }

    @Override
    public int getLayoutId() {
        return 0;
    }
}
