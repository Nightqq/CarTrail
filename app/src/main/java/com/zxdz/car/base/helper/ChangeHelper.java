package com.zxdz.car.base.helper;

import com.zxdz.car.base.dao.ChangePoliceDao;
import com.zxdz.car.main.model.domain.ChangePolice;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lenovo on 2017/10/31.
 */

public class ChangeHelper {

    private static ChangePoliceDao changePoliceDao = CardDBUtil.getDaoSession().getChangePoliceDao();

    public static List<ChangePolice> getAreaInfoListFromDB() {
        return (ArrayList<ChangePolice>) changePoliceDao.queryBuilder().list();
    }

    public static void saveAreaInfoToDB(ChangePolice changePolice) {
        if (changePolice != null) {
            changePoliceDao.insertOrReplace(changePolice);
        }
    }
}