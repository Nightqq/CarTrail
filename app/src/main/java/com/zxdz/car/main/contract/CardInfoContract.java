package com.zxdz.car.main.contract;

import com.zxdz.car.base.presenter.IPresenter;
import com.zxdz.car.base.view.IView;
import com.zxdz.car.main.model.domain.CardInfo;

import java.util.List;


public interface CardInfoContract {
    interface View extends IView {
        void showCardInfoListData(List<CardInfo> list);
    }

    interface Presenter extends IPresenter {
        void getCardInfoList(String imel);
    }
}
