package com.zxdz.car.main.contract;

import com.zxdz.car.base.presenter.IPresenter;
import com.zxdz.car.base.view.INoNet;
import com.zxdz.car.base.view.IView;
import com.zxdz.car.main.model.domain.SettingInfo;
import com.zxdz.car.main.model.domain.TerminalInfo;


public interface SettingInfoContract {
    interface View extends IView, INoNet {
        void loadInitInfo(TerminalInfo terminalInfo);

        void loadSettingInfo(SettingInfo settingInfo);

        /*void loadCarInfoUpload(ResultInfo resultInfo);

        void loadTrailUpload(ResultInfo resultInfo);

        void loadWarnUpload(ResultInfo resultInfo);

        void loadChangeUpload(ResultInfo resultInfo);*/
    }

    interface Presenter extends IPresenter {
        //初始化设备信息
        void initEquipment(String imel);

        //根据设备串号获取服务器设置的信息
        void getSettingInfo(String zdjId);

    }
}
