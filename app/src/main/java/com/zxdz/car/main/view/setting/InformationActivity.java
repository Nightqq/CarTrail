package com.zxdz.car.main.view.setting;

import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.TextView;

import com.zxdz.car.App;
import com.zxdz.car.R;
import com.zxdz.car.base.view.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class InformationActivity extends BaseActivity {

    @BindView(R.id.information_toolbar)
    Toolbar informationToolbar;
    @BindView(R.id.infor_zdjid)
    TextView inforZdjid;

    @Override
    public void init() {
        ButterKnife.bind(this);
        basetoobar(informationToolbar, "设备信息");
        inforZdjid.setText(App.ZDJID);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_information;
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }
}
