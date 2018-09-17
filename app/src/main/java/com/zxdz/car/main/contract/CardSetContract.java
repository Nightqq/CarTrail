package com.zxdz.car.main.contract;

import com.kk.securityhttp.domain.ResultInfo;
import com.zxdz.car.base.presenter.IPresenter;
import com.zxdz.car.base.view.IDialog;
import com.zxdz.car.base.view.INoNet;
import com.zxdz.car.base.view.IView;


public interface CardSetContract {
    interface View extends IView,INoNet,IDialog{
        void showUpdateCardState(ResultInfo resultInfo);
    }

    interface Presenter extends IPresenter {
        void updateCardSet(int type, int upValue, int areaId);
    }
}
