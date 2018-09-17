package com.zxdz.car.base.presenter;

import android.content.Context;
import android.support.annotation.NonNull;

import com.kk.securityhttp.domain.ResultInfo;
import com.zxdz.car.base.helper.ResultInfoHelper;
import com.zxdz.car.base.helper.TipsHelper;
import com.zxdz.car.base.view.IView;


import rx.subscriptions.CompositeSubscription;


public abstract class BasePresenter<M, V extends IView> implements IPresenter {
    @NonNull
    protected CompositeSubscription mSubscriptions;
    protected boolean mFirstLoad = true;

    protected M mEngin;
    protected V mView;
    protected Context mContext;

    public BasePresenter(V v) {
       this(null, v);
    }

    public BasePresenter(Context context, V v) {
        this.mContext = context;
        mSubscriptions = new CompositeSubscription();
        mView = v;
    }

    @Override
    public void subscribe() {
        loadData(false);
    }

    @Override
    public void unsubscribe() {
        mSubscriptions.clear();
    }


    public void loadData(boolean forceUpdate) {
        loadData(forceUpdate || mFirstLoad, true);
        mFirstLoad = false;
    }

    public abstract void loadData(final boolean forceUpdate, final boolean showLoadingUI);


    public <T> void handleResultInfo(final ResultInfo<T> resultInfo, final Runnable runnable) {
        ResultInfoHelper.handleResultInfo(resultInfo, new ResultInfoHelper.Callback() {
            @Override
            public void resultInfoEmpty(String message) {
                TipsHelper.tips(mContext, message);
            }

            @Override
            public void resultInfoNotOk(String message) {
                TipsHelper.tips(mContext, message);
            }

            @Override
            public void resultInfoOk() {
                if (runnable != null) {
                    runnable.run();
                }
            }
        });
    }

    public <T> void handleResultInfo(ResultInfo<T> resultInfo) {
        handleResultInfo(resultInfo, null);
    }
}

