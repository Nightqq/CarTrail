package com.zxdz.car.base.helper;

import com.zxdz.car.base.dao.TrailPointInfoDao;
import com.zxdz.car.main.model.domain.TrailPointInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * 轨迹坐标点辅助类
 */

public class TrailPointHelper {

    private static TrailPointInfoDao trailPointInfoDao = CardDBUtil.getDaoSession().getTrailPointInfoDao();

    public static List<TrailPointInfo> getTrailPointInfoListFromDB() {
        return (ArrayList<TrailPointInfo>) trailPointInfoDao.queryBuilder().list();
    }

    public static void saveTrailPointInfoToDB(TrailPointInfo TrailPointInfo) {
        if (TrailPointInfo != null) {
            if (getTrailPointInfoListFromDB()!=null&&getTrailPointInfoListFromDB().size()>2){
                com.zxdz.car.main.model.domain.TrailPointInfo trailPointInfo = getTrailPointInfoListFromDB().get(getTrailPointInfoListFromDB().size() - 1);
                if (trailPointInfo.getGJZBJD()!=TrailPointInfo.getGJZBJD()&&trailPointInfo.getGJZBWD()!=TrailPointInfo.getGJZBWD()){
                    trailPointInfoDao.insertOrReplace(TrailPointInfo);
                }
                return;
            }
            trailPointInfoDao.insertOrReplace(TrailPointInfo);
        }
    }

    public static void saveTrailPointInfoListToDB(List<TrailPointInfo> list) {
        if (list != null) {
            for (TrailPointInfo info : list) {
                trailPointInfoDao.insertOrReplace(info);
            }
        }
    }

    public static void deleteTrailPointInfoInDB(TrailPointInfo TrailPointInfo) {
        trailPointInfoDao.delete(TrailPointInfo);
    }

    public static void deleteTrailPointInfoInDBlist(List<TrailPointInfo> list) {
        for (TrailPointInfo trailPointInfo : list) {
            trailPointInfoDao.delete(trailPointInfo);
        }
    }

    public static void deleteAllTrailPointInfoList() {
        trailPointInfoDao.deleteAll();
    }
}
