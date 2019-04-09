package com.zxdz.car.main.view.setting;

import android.os.Bundle;
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
    @BindView(R.id.infor_ip)
    TextView inforIp;

    @Override
    public void init() {
        ButterKnife.bind(this);
        basetoobar(informationToolbar, "设备信息");
        if (App.ZDJID!=null){
            inforZdjid.setText(App.ZDJID);
        }
        if (App.phoneIP!=null){
            inforIp.setText(App.phoneIP);
        }

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_information;
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }


}
