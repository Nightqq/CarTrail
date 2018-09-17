package com.zxdz.car.base.helper;

import com.zxdz.car.base.dao.AdminCardDao;
import com.zxdz.car.main.model.domain.AdminCard;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lenovo on 2017/10/31.
 */

public class AdminCardHelper {

    private static AdminCardDao adminCardDao = CardDBUtil.getDaoSession().getAdminCardDao();

    public static void addCardInfo(AdminCard adminCard) {
        adminCardDao.insert(adminCard);
    }
    public static List<AdminCard> getCarTravelRecordListFromDB() {
        return (ArrayList<AdminCard>) adminCardDao.queryBuilder().list();
    }

    public static void deleteCarTravelRecordInDB(AdminCard CarTravelRecord) {
        adminCardDao.delete(CarTravelRecord);
    }

    public static void deleteAllCarTravelRecordList() {
        adminCardDao.deleteAll();
    }
}
