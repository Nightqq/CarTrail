package com.zxdz.car.base.helper;

import com.blankj.utilcode.util.LogUtils;
import com.zxdz.car.base.dao.CardInfoDao;
import com.zxdz.car.main.model.domain.CardInfo;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by super on 2017/10/19.
 */

public class CardHelper {

    private static CardInfoDao cardInfoDao = CardDBUtil.getDaoSession().getCardInfoDao();

    public static Observable<ArrayList<CardInfo>> carInfoList() {
        return Observable.just("").subscribeOn(Schedulers.io()).map(new Func1<String, ArrayList<CardInfo>>() {

            @Override
            public ArrayList<CardInfo> call(String s) {
                return (ArrayList<CardInfo>) cardInfoDao.queryBuilder().list();
            }
        });
    }

    public static Observable<ArrayList<CardInfo>> addCardInfo(final CardInfo aCardInfo) {
        return Observable.just("").subscribeOn(Schedulers.io()).map(new Func1<String, ArrayList<CardInfo>>() {
            @Override
            public ArrayList<CardInfo> call(String s) {
                cardInfoDao.insert(aCardInfo);

                return (ArrayList<CardInfo>) cardInfoDao.queryBuilder().list();
            }
        });
    }

    public static Observable<ArrayList<CardInfo>> deleteCardInfo(final CardInfo dCardInfo) {
        return Observable.just("").subscribeOn(Schedulers.io()).map(new Func1<String, ArrayList<CardInfo>>() {
            @Override
            public ArrayList<CardInfo> call(String s) {
                cardInfoDao.delete(dCardInfo);
                return (ArrayList<CardInfo>) cardInfoDao.queryBuilder().list();
            }
        });
    }

    public static List<CardInfo> getCardInfoListFromDB() {
        return (ArrayList<CardInfo>) cardInfoDao.queryBuilder().list();
    }

    //判断管理员卡号是否在数据库中存在
    public static boolean isAvailableInDB(String cardnumber){
        QueryBuilder queryBuilder = cardInfoDao.queryBuilder();
        queryBuilder.where(CardInfoDao.Properties.AdminCardNumber.eq(cardnumber));
        //List<CardInfo> list = cardInfoDao.queryBuilder().where(CardInfoDao.Properties.AdminCardNumber.eq(cardnumber)).list();
        if (queryBuilder.list().size()>0 && queryBuilder.list() != null){
            return true;
        }
        return false;
    }

   /* public static CardInfo getcardInfoBynum(String num){
        if (isAvailableInDB(num)){


            return ;
        }else return null;
    }*/

    public static void saveCardInfoToDB(CardInfo cardInfo) {
        if (cardInfo != null) {
            cardInfoDao.insertOrReplace(cardInfo);
        }
    }
    public static void saveCardInfoListToDB(List<CardInfo> list) {
        if (list != null) {
            for (CardInfo info : list) {
                info.setCardType(0);
                cardInfoDao.insertOrReplace(info);
                LogUtils.a("管理员卡号：",info.getAdminCardNumber());
            }
        }
        LogUtils.a("保存管理员卡号结束");
    }
    public static void deleteCardInfoInDB(CardInfo cardInfo) {
        cardInfoDao.delete(cardInfo);
    }
    public static void deleteAllCardInfoList() {
        cardInfoDao.deleteAll();
    }
    /**
     * 根据人员类型及刷卡卡号，判断卡号是否符合要求
     *
     * @param cardNumber
     * @param cardType
     * @return
     */
    public static CardInfo validateCardNumber(String cardNumber, int cardType) {
        QueryBuilder queryBuilder = cardInfoDao.queryBuilder();
        queryBuilder.where(CardInfoDao.Properties.AdminCardNumber.eq(cardNumber));
        queryBuilder.where(CardInfoDao.Properties.CardType.eq(cardType));
        if (queryBuilder.list() != null && queryBuilder.list().size() > 0) {
            return (CardInfo) queryBuilder.list().get(0);
        }
        return null;
    }

}
