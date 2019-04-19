package com.zxdz.car.main.presenter;

import android.content.Context;

import com.blankj.utilcode.util.LogUtils;
import com.kk.securityhttp.domain.ResultInfo;
import com.zxdz.car.base.presenter.BasePresenter;
import com.zxdz.car.main.contract.SettingInfoContract;
import com.zxdz.car.main.model.domain.PoliceInfoAll;
import com.zxdz.car.main.model.domain.SettingInfo;
import com.zxdz.car.main.model.domain.TerminalInfo;
import com.zxdz.car.main.model.engin.SettingInfoEngin;

import java.util.List;

import rx.Subscriber;
import rx.Subscription;


public class SettingInfoPresenter extends BasePresenter<SettingInfoEngin, SettingInfoContract.View> implements SettingInfoContract.Presenter {

    public SettingInfoPresenter(Context context, SettingInfoContract.View view) {
        super(context, view);
        mEngin = new SettingInfoEngin(context);
    }

    @Override
    public void initEquipment(String imel) {
        Subscription subscribe = mEngin.initEquInfo(imel).subscribe(new Subscriber<ResultInfo<TerminalInfo>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                mView.showNoNet();
            }

            @Override
            public void onNext(final ResultInfo<TerminalInfo> resultInfo) {
                if (resultInfo != null&&resultInfo.message.equals("成功")) {
                    LogUtils.a(resultInfo.message);
                    LogUtils.a(resultInfo.data.toString());
                    mView.loadInitInfo(resultInfo.data);
                }else{
                    LogUtils.a("返回空");
                    mView.showNoNet();
                }
            }
        });
        mSubscriptions.add(subscribe);
    }

    @Override
    public void getSettingInfo(String zdjId) {
        Subscription subscribe = mEngin.loadSettingInfo(zdjId).subscribe(new Subscriber<ResultInfo<SettingInfo>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                mView.showNoNet();
            }

            @Override
            public void onNext(final ResultInfo<SettingInfo> resultInfo) {
                if (resultInfo != null&&resultInfo.message.equals("成功")) {
                    mView.loadSettingInfo(resultInfo.data);
                }else{
                    mView.showNoNet();
                }
            }
        });
        mSubscriptions.add(subscribe);
    }

    @Override
    public void loadData(boolean forceUpdate, boolean showLoadingUI) {
        if (!forceUpdate) return;
    }
}
