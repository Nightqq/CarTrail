package com.zxdz.car.base.helper;

import com.zxdz.car.base.dao.TerminalInfoDao;
import com.zxdz.car.main.model.domain.TerminalInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lenovo on 2017/10/30.
 * 设备信息存储
 */

public class TerminalInfoHelper {

    private static TerminalInfoDao terminalInfoDao = CardDBUtil.getDaoSession().getTerminalInfoDao();

    public static List<TerminalInfo> getTerminalInfoListFromDB() {
        return terminalInfoDao.queryBuilder().list();
    }

    public static void saveTerminalInfoToDB(TerminalInfo TerminalInfo) {
        if (TerminalInfo != null) {
            terminalInfoDao.insertOrReplace(TerminalInfo);
        }
    }

    public static void saveTerminalInfoListToDB(List<TerminalInfo> list) {
        if (list != null) {
            for (TerminalInfo info : list) {
                terminalInfoDao.insertOrReplace(info);
            }
        }
    }

    public static void deleteTerminalInfoInDB(TerminalInfo TerminalInfo) {
        terminalInfoDao.delete(TerminalInfo);
    }

    public static void deleteAllTerminalInfoList() {
        terminalInfoDao.deleteAll();
    }
}
