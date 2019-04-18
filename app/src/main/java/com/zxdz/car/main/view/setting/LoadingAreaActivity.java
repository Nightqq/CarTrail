package com.zxdz.car.main.view.setting;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.alibaba.fastjson.TypeReference;
import com.blankj.utilcode.util.LogUtils;
import com.kk.securityhttp.domain.ResultInfo;
import com.kk.securityhttp.engin.HttpCoreEngin;
import com.zxdz.car.R;
import com.zxdz.car.base.helper.UnloadAreaHelper;
import com.zxdz.car.base.view.BaseActivity;
import com.zxdz.car.main.adapter.RecyclerviewAdapter;
import com.zxdz.car.main.model.domain.AreaInfo;
import com.zxdz.car.main.model.domain.URLConfig;
import com.zxdz.car.main.model.domain.UnloadAreaAllInfo;
import com.zxdz.car.main.model.domain.UnloadAreaInfo;
import com.zxdz.car.main.view.MainActivity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import butterknife.BindView;
import rx.Observable;
import rx.Subscriber;
import rx.internal.operators.BlockingOperatorToIterator;

public class LoadingAreaActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    private RecyclerView recyclerview;
    private RecyclerviewAdapter mAdapter;

    @Override
    public void init() {
        basetoobar(toolbar, "装卸区设置");

        recyclerview = (RecyclerView) findViewById(R.id.recyclerview);
        recyclerview.setLayoutManager(new GridLayoutManager(this,2));
        recyclerview.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        List<UnloadAreaInfo> warnInfoListFromDB = UnloadAreaHelper.getWarnInfoListFromDB();
        mAdapter = new RecyclerviewAdapter(this, warnInfoListFromDB, new RecyclerviewAdapter.checkedChangedListener() {
            @Override
            public void changedlistener(String s) {
                mAdapter.notifyDataSetChanged();
            }
        });
        recyclerview.setAdapter(mAdapter);
        Button confirm = (Button) findViewById(R.id.confirm);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               //获取装卸区列表
                loadAreaInfo().subscribe(new Subscriber<ResultInfo<UnloadAreaAllInfo>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(final ResultInfo<UnloadAreaAllInfo> resultInfo) {
                        LogUtils.a("返回装卸区列表装卸区列表",resultInfo.toString());
                        if (resultInfo!=null&&resultInfo.data.getDlgj()!=null&& resultInfo.data.getDlgj().size()>0){
                            LogUtils.a("返回装卸区列表",resultInfo.data.toString());
                            List<UnloadAreaInfo> dlgj = resultInfo.data.getDlgj();
                            UnloadAreaHelper.deleteAllWarnInfoList();
                            UnloadAreaHelper.saveWarnInfoListToDB(dlgj);
                            LogUtils.a("返回装卸区列表",dlgj.toString());
                            mAdapter.addUnload(dlgj);
                        }else {
                            LogUtils.a("返回装卸区列表","返回数据异常");
                            Toast.makeText(LoadingAreaActivity.this, "返回数据异常", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_loading_area;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mAdapter = null;
    }

    public Observable<ResultInfo<UnloadAreaAllInfo>> loadAreaInfo() {
        return HttpCoreEngin.get(this).rxpost(URLConfig.getinstance().getunload_area_URL(), new TypeReference<ResultInfo<UnloadAreaAllInfo>>() {
                }.getType(),null,
                false, false,
                false);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }
}
