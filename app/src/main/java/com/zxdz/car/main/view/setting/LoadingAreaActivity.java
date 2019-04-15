package com.zxdz.car.main.view.setting;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.zxdz.car.R;
import com.zxdz.car.base.view.BaseActivity;
import com.zxdz.car.main.adapter.RecyclerviewAdapter;
import com.zxdz.car.main.view.MainActivity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class LoadingAreaActivity extends BaseActivity {

    private RecyclerView recyclerview;
    private RecyclerviewAdapter mAdapter;

    @Override
    public void init() {
        recyclerview = (RecyclerView) findViewById(R.id.recyclerview);
        recyclerview.setLayoutManager(new GridLayoutManager(this,2));
        recyclerview.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));

        List<String> strings = new ArrayList<>();
//        strings.add("装卸区一");
//        strings.add("装卸区二");
//        strings.add("装卸区三");
//        strings.add("装卸区四");
//        strings.add("装卸区五");
//        strings.add("装卸区六");
//        strings.add("装卸区七");
//        strings.add("装卸区八");
//        strings.add("装卸区九");
        for (int j = 0; j <30 ; j++) {
            strings.add("装卸区"+j);
        }
        strings.add("装卸区999999");
        mAdapter = new RecyclerviewAdapter(this, strings, new RecyclerviewAdapter.checkedChangedListener() {
            @Override
            public void changedlistener() {
                mAdapter.notifyDataSetChanged();
            }
        });
        recyclerview.setAdapter(mAdapter);
        Button confirm = (Button) findViewById(R.id.confirm);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoadingAreaActivity.this, MainActivity.class));
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
}
