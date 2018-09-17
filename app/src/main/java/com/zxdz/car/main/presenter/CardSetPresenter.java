package com.zxdz.car.main.presenter;

import android.content.Context;

import com.kk.securityhttp.domain.ResultInfo;
import com.zxdz.car.base.helper.ResultInfoHelper;
import com.zxdz.car.base.presenter.BasePresenter;
import com.zxdz.car.main.contract.CardSetContract;
import com.zxdz.car.main.model.engin.CardSetEngin;

import rx.Subscriber;
import rx.Subscription;


public class CardSetPresenter extends BasePresenter<CardSetEngin, CardSetContract.View> implements CardSetContract.Presenter {

    public CardSetPresenter(Context context, CardSetContract.View view) {
        super(context, view);
        mEngin = new CardSetEngin(context);
    }

    @Override
    public void updateCardSet(int type, int upValue, int areaId) {
        mView.showLoadingDialog("正在修改");
        Subscription subscribe = mEngin.updateCardSet(type, upValue, areaId).subscribe(new Subscriber<ResultInfo>() {
            @Override
            public void onCompleted() {
                mView.dismissLoadingDialog();
            }

            @Override
            public void onError(Throwable e) {
                mView.dismissLoadingDialog();
                mView.showNoNet();
            }

            @Override
            public void onNext(final ResultInfo resultInfo) {
                ResultInfoHelper.handleResultInfo(resultInfo, new ResultInfoHelper.Callback() {
                    @Override
                    public void resultInfoEmpty(String message) {
                        mView.showNoNet();
                    }

                    @Override
                    public void resultInfoNotOk(String message) {
                        mView.showNoNet();
                    }

                    @Override
                    public void resultInfoOk() {
                        if (resultInfo != null) {
                            mView.showUpdateCardState(resultInfo);
                        } else {
                            mView.showNoNet();
                        }
                    }
                });
            }
        });
        mSubscriptions.add(subscribe);
    }

    @Override
    public void loadData(boolean forceUpdate, boolean showLoadingUI) {
        if (!forceUpdate) return;
    }
}
