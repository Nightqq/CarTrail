package com.zxdz.car.main.view.setting;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.zxdz.car.R;
import com.zxdz.car.base.view.BaseActivity;
import com.zxdz.car.main.adapter.AreaLocusRVAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AreaLocusActivity extends BaseActivity {

    @BindView(R.id.area_locus_toolbar)
    Toolbar areaLocusToolbar;
    @BindView(R.id.area_locus_rcv)
    RecyclerView areaLocusRcv;

    @Override
    public void init() {
        ButterKnife.bind(this);
        basetoobar(areaLocusToolbar,"区域轨迹");
        initdata();
    }

    private void initdata() {
        List mList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            mList.add("行驶路线"+(i+1));
        }
        AreaLocusRVAdapter areaLocusRVAdapter = new AreaLocusRVAdapter();
        areaLocusRVAdapter.setmList(mList);
        areaLocusRcv.setLayoutManager(new LinearLayoutManager(this));
        areaLocusRcv.setAdapter(areaLocusRVAdapter);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_area_locus;
    }


}
