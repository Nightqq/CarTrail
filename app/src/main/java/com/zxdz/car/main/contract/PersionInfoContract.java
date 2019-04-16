package com.zxdz.car.main.contract;

import com.zxdz.car.base.presenter.IPresenter;
import com.zxdz.car.base.view.INoNet;
import com.zxdz.car.base.view.IView;
import com.zxdz.car.main.model.domain.DriverInfo;
import com.zxdz.car.main.model.domain.PersionInfo;
import com.zxdz.car.main.model.domain.PoliceInfoAll;

/**
 * Created by iflying on 2017/11/20.
 */

public interface PersionInfoContract {

    interface View extends IView, INoNet{
        void showPersionInfo(PersionInfo persionInfo);
        void showPoliceInfoAll(PoliceInfoAll persionInfo);
        void showdriverInfo(DriverInfo driverInfo);
    }

    interface Presenter extends IPresenter {
        /**
         * 根据卡号获取人员信息
         *
         * @param cardNumber
         */
        void getPersionInfo(String cardNumber);

        void getDriverInfo(String cardNumber);
    }
}
