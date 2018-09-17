package com.zxdz.car.main.presenter;

import android.content.Context;

import com.zxdz.car.base.presenter.BasePresenter;
import com.zxdz.car.main.contract.CardInfoContract;
import com.zxdz.car.main.model.domain.CardInfoWrapper;
import com.zxdz.car.main.model.engin.CardInfoEngin;

import rx.Subscriber;
import rx.Subscription;


public class CardInfoPresenter extends BasePresenter<CardInfoEngin, CardInfoContract.View> implements CardInfoContract.Presenter {

    public CardInfoPresenter(Context context, CardInfoContract.View view) {
        super(context, view);
        mEngin = new CardInfoEngin(context);
    }

    @Override
    public void getCardInfoList(String imel) {
        Subscription subscribe = mEngin.cardInfoList(imel).subscribe(new Subscriber<CardInfoWrapper>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
               //mView.showNoData();
            }

            @Override
            public void onNext(final CardInfoWrapper resultInfo) {
                if(resultInfo != null){
                    mView.showCardInfoListData(resultInfo.getData());
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
