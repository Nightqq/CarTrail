package com.zxdz.car.main.view.lock;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.zxdz.car.R;
import com.zxdz.car.base.helper.CarTravelHelper;
import com.zxdz.car.base.helper.UnloadAreaHelper;
import com.zxdz.car.base.view.BaseActivity;
import com.zxdz.car.main.adapter.RecyclerviewAdapter;
import com.zxdz.car.main.model.domain.UnloadAreaInfo;

import java.util.List;

import butterknife.BindView;

public class UnloadAreaActivity extends BaseActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    private RecyclerView recyclerview;
    private RecyclerviewAdapter mAdapter;
    private int step;
    private String unLoadArea = "";
    private Intent intentService;
    private int tag = 0;

    @Override
    public void init() {
        toolbar.setTitle("装卸区选择");
        setSupportActionBar(toolbar);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            step = bundle.getInt("blue_step");
        }
        Button confirm = (Button) findViewById(R.id.confirm);
        intentService = new Intent(this, com.zxdz.car.main.service.UploadDataService.class);
        recyclerview = (RecyclerView) findViewById(R.id.recyclerview);
        recyclerview.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerview.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        List<UnloadAreaInfo> warnInfoListFromDB = UnloadAreaHelper.getWarnInfoListFromDB();
        if (warnInfoListFromDB == null) {
            tag=1;
            confirm.setText("获取装卸区列表");
        } else {
            tag=0;
            confirm.setText("确认");
        }
        mAdapter = new RecyclerviewAdapter(this, warnInfoListFromDB, new RecyclerviewAdapter.checkedChangedListener() {
            @Override
            public void changedlistener(String string) {
                unLoadArea = string;
            }
        });
        recyclerview.setAdapter(mAdapter);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ( tag==1){

                }else {
                    if (!unLoadArea.equals("")) {
                        UnloadAreaInfo unloadAreaInfo = UnloadAreaHelper.getWarnInfoListByID(unLoadArea);
                        if (unloadAreaInfo != null) {
                            if (CarTravelHelper.carTravelRecord != null) {
                                CarTravelHelper.carTravelRecord.setUnloadArea(unloadAreaInfo.getArea_id());
                                CarTravelHelper.saveCarTravelRecordToDB(CarTravelHelper.carTravelRecord);
                            }
                            startService(intentService);
                            Intent intent = new Intent(UnloadAreaActivity.this, CameraActivity.class);
                            intent.putExtra("blue_step", step);
                            startActivity(intent);
                            finish();
                        }
                    } else {
                        Toast.makeText(UnloadAreaActivity.this, "请选择装卸区", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mAdapter = null;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_loading_area;
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }
}
