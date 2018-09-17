package com.zxdz.car.main.contract;

import com.kk.securityhttp.domain.ResultInfo;
import com.zxdz.car.base.presenter.IPresenter;
import com.zxdz.car.base.view.IDialog;
import com.zxdz.car.base.view.INoNet;
import com.zxdz.car.base.view.IView;
import com.zxdz.car.main.model.domain.CarTravelRecord;
import com.zxdz.car.main.model.domain.ChangePoliceInfo;
import com.zxdz.car.main.model.domain.TrailPointInfoWrapper;
import com.zxdz.car.main.model.domain.WarnInfo;

import java.util.List;

/**
 * Created by admin on 2017/11/12.
 */

public interface UploadInfoContract {

    interface View extends IView, INoNet, IDialog {
        /**
         * 更新车辆主记录信息上传结果
         *
         * @param resultInfo
         */
        void loadCarRecordUpload(ResultInfo resultInfo);

        /**
         * 更新车辆记录轨迹上传结果
         *
         * @param resultInfo
         */
        void loadTrailUpload(ResultInfo resultInfo);

        /**
         * 更新车辆记录报警上传结果
         *
         * @param resultInfo
         */
        void loadWarnUpload(ResultInfo resultInfo);

        /**
         * 更新车辆更换带领干警上传结果
         *
         * @param resultInfo
         */
        void loadChangeUpload(ResultInfo resultInfo);
    }

    interface Presenter extends IPresenter {
        /**
         * 上传主记录
         *
         * @param carTravelRecord
         */
        void uploadCarRecord(CarTravelRecord carTravelRecord);

        /**
         * 上传车辆轨迹点
         *
         * @param trailPointInfoWrapper
         */
        void uploadTrailPoint(TrailPointInfoWrapper trailPointInfoWrapper);

        /**
         * 上传报警信息
         *
         * @param warnInfos
         */
        void uploadWarnInfo(WarnInfo warnInfos);

        /**
         * 上传更换带领干警信息
         *
         * @param changePoliceInfos
         */
        void uploadChangePoliceInfo(List<ChangePoliceInfo> changePoliceInfos);

    }
}
