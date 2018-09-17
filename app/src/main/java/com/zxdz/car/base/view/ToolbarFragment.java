package com.zxdz.car.base.view;

import android.support.v7.app.AppCompatActivity;


import com.zxdz.car.R;
import com.zxdz.car.base.presenter.BasePresenter;

import butterknife.BindView;

public abstract class ToolbarFragment<P extends BasePresenter> extends BaseFragment<P> {

    @BindView(R.id.toolbar)
    protected BaseToolBar mToolbar;

    @Override
    public void init() {
        if (mToolbar == null) {
            throw new NullPointerException("error, please set com.yc.english.main.view.MainToolBar id -> toolbar.");
        }

        if (isInstallToolbar()) {
            mToolbar.init((AppCompatActivity) getActivity());
            getActivity().invalidateOptionsMenu();
        }
    }


    public BaseToolBar getToolbar() {
        return mToolbar;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    public abstract boolean isInstallToolbar();
}
