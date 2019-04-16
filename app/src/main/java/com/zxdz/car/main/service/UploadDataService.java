package com.zxdz.car.main.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.zxdz.car.base.helper.CarTravelHelper;
import com.zxdz.car.main.model.domain.CarTravelRecord;
import com.zxdz.car.main.utils.UploadInfoUtil;

import java.util.List;

/**
 * Created by iflying on 2017/11/25.
 */

public class UploadDataService extends Service {

    private UploadInfoUtil uploadInfoUtil;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        LogUtils.e("service onCreate--->");
        uploadInfoUtil = new UploadInfoUtil(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        try {
            LogUtils.e("service onStartCommand--->");
            otherInfo();//上传当前流水的附信息
            uploadInfo();//上传主信息
        } catch (NullPointerException e) {

        }
        return super.onStartCommand(intent, flags, startId);
    }

    /**
     * 上传主数据
     */
    public void uploadInfo() {
        LogUtils.a("上传数据—准备上传主信息");
        List<CarTravelRecord> carTravelRecordListFromDB = CarTravelHelper.getCarTravelRecordListFromDB();
        LogUtils.a("未上传的流水个数" + carTravelRecordListFromDB.size());
        if (carTravelRecordListFromDB != null && carTravelRecordListFromDB.size() > 0) {
            for (CarTravelRecord carTravelRecord : carTravelRecordListFromDB) {
                if (carTravelRecord != null) {
                    /*//初始状态时，设置值LS_ID为0
                    if (carTravelRecord.getZT() == 0 || carTravelRecord.getZT() == 10) {
                        carTravelRecord.setLS_ID(0);
                        CarTravelHelper.saveCarTravelRecordToDB(carTravelRecord);
                    }*/
                    uploadInfoUtil.uploadCarRecord(carTravelRecord);
                   // LogUtils.a("上传主信息",carTravelRecord.toString());
                } else {
                    LogUtils.a("carTravelRecord=null");
                    ToastUtils.showLong("未检测到流水信息，请完善操作");
                }
            }
        }else {
            LogUtils.a("carTravelRecordListFromDB=null");
        }
    }

    /**
     * 上传
     * 报警数据
     * 取消报警数据
     * 照片数据
     */
    public void otherInfo() {
        long id = 0L;
        int lsid = 0;
        if (CarTravelHelper.carTravelRecord != null) {
            id = CarTravelHelper.carTravelRecord.getId();
            lsid = CarTravelHelper.carTravelRecord.getLS_ID();
        }
        LogUtils.a("上传数据—准备上传附信息");
        uploadInfoUtil.uploadWarnInfo(id, lsid);
        uploadInfoUtil.uploadUnWarnInfo();
        uploadInfoUtil.uploadPicture(id, lsid);


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
