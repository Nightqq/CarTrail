package com.zxdz.car.base.helper;

import com.blankj.utilcode.util.LogUtils;
import com.zxdz.car.App;
import com.zxdz.car.base.dao.CarTravelRecordDao;
import com.zxdz.car.main.model.domain.CarTravelRecord;
import org.greenrobot.greendao.query.QueryBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by super on 2017/10/19.
 */

public class CarTravelHelper {

    /**
     * 一次完整记录的车辆进入信息
     */
    public static CarTravelRecord carTravelRecord;

    private static CarTravelRecordDao carTravelRecordDao = CardDBUtil.getDaoSession().getCarTravelRecordDao();

    public static CarTravelRecord getCarTravelRecord(Long key) {
        return carTravelRecordDao.load(key);
    }

    public static List<CarTravelRecord> getCarTravelRecordListFromDB() {
        return (ArrayList<CarTravelRecord>) carTravelRecordDao.queryBuilder().list();
    }

    //到6.6为止异常退出再进入中调用
    public static CarTravelRecord getCarTravelRecord() {
        if (carTravelRecordDao.queryBuilder().list() != null && carTravelRecordDao.queryBuilder().list().size() > 0) {
            return carTravelRecordDao.queryBuilder().list().get(0);
        }
        return null;
    }

    public static CarTravelRecord getCarTravelRecordByLSID(Long lsId) {
        QueryBuilder queryBuilder = carTravelRecordDao.queryBuilder();
        queryBuilder.where(CarTravelRecordDao.Properties.LS_ID.eq(lsId));
        if (queryBuilder.list() != null && queryBuilder.list().size() > 0) {
            return (CarTravelRecord) queryBuilder.list().get(0);
        }
        return null;
    }

    public static void saveCarTravelRecordToDB(CarTravelRecord CarTravelRecord) {
        LogUtils.e("未上传个数",getCarTravelRecordListFromDB().size());
        if (CarTravelRecord != null) {
            //carTravelRecord=CarTravelRecord;
            carTravelRecordDao.insertOrReplace(CarTravelRecord);
            LogUtils.e("保存后未上传个数",getCarTravelRecordListFromDB().size());
        }
    }

    public static void saveCarTravelRecordListToDB(List<CarTravelRecord> list) {
        if (list != null) {
            for (CarTravelRecord info : list) {
                carTravelRecordDao.insertOrReplace(info);
            }
        }
    }

    public static void deleteCarTravelRecordInDB(CarTravelRecord CarTravelRecord) {
        carTravelRecordDao.delete(CarTravelRecord);
        LogUtils.e("删除后未上传个数",getCarTravelRecordListFromDB().size());
    }

    public static void deleteAllCarTravelRecordList() {
        carTravelRecordDao.deleteAll();
    }

}
