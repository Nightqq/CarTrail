package com.zxdz.car.base.view;

import android.content.Context;
import android.util.AttributeSet;

import com.zxdz.car.R;


public class MainToolBar extends BaseToolBar {
    public MainToolBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public int getLayoutId() {
        return R.layout.base_view_toolbar;
    }
}
