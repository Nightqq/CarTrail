package com.zxdz.car.base.utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018\5\21 0021.
 */

public class SwipingCardUtils {

    /*领用，安装，锁车，开锁，
    *交还管理员，民警，驾驶员
    * 0不刷，1刷卡
    * */
    private static SwipingCardUtils swipingCardUtils;
    private static Context mContext;
    private static SharedPreferences sp;

    private SwipingCardUtils() {
        initArray();
    }
    public static SwipingCardUtils swipecardhelp(Context context){
        mContext=context;
        sp = mContext.getSharedPreferences("SwipingCardStyp", mContext.MODE_PRIVATE);
        if (swipingCardUtils==null){
            swipingCardUtils = new SwipingCardUtils();
        }
        return swipingCardUtils;
    }
    private void initArray(){
        if (sp.getBoolean("swipe_card",true)){
            SharedPreferences.Editor edit = sp.edit();
            int[][] swipe_card_styp={{0,1,0},{0,1,0},{0,1,0},{0,1,0},{0,1,0}};
            for (int i = 0; i <5; i++) {
                for (int j = 0; j < 3; j++) {
                    edit.putInt("swipe_card"+i+""+j,swipe_card_styp[i][j]);
                }
            }
            edit.putBoolean("swipe_card",false);
            edit.commit();
        }

    }
    public int getArray(int i, int j){
        int anInt = sp.getInt("swipe_card" + i + "" + j, 0);
        return anInt;
    }
    public void setArray(int i, int j){
        SharedPreferences.Editor edit = sp.edit();
        int anInt = sp.getInt("swipe_card" + i + "" + j, 0);
        int ii=anInt==0?1:0;
        edit.putInt("swipe_card"+i+""+j,ii);
        edit.commit();
    }
    public int[][] getAllArray(){
        int[][] ints = new int[5][3];
        for (int i = 0; i <5; i++) {
            for (int j = 0; j < 3; j++) {
                ints[i][j]=sp.getInt("swipe_card"+i+""+j,0);
            }
        }
        return ints;
    }
}
