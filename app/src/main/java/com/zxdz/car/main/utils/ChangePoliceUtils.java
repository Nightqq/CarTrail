package com.zxdz.car.main.utils;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.widget.PopupWindow;

import com.blankj.utilcode.util.LogUtils;
import com.zxdz.car.R;
import com.zxdz.car.base.utils.AudioPlayUtils;
import com.zxdz.car.main.view.AuthCardActivity;
import com.zxdz.car.main.view.InstallConfirmActivity;
import com.zxdz.car.main.view.lock.OpenCardActivity;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by Administrator on 2018\1\11 0011.
 */

public class ChangePoliceUtils {
    private Context mContext;
    private int mClass;
    private String newPoliceNew;
    private static ChangePoliceUtils changePoliceUtils;
    private AudioPlayUtils audioPlayUtils;
    // public static Handler mHandler;

    private ChangePoliceUtils(Context mContext, int mClass) {
        this.mContext = mContext;
        this.mClass = mClass;
    }

    public static ChangePoliceUtils getChangePolice(Context mContext, int mClass) {
        LogUtils.a("changePoliceUtils准备new");
        changePoliceUtils = new ChangePoliceUtils(mContext, mClass);
        return changePoliceUtils;
    }

    public void stop() {
        changePoliceUtils = null;
    }


    //0表示前后一致，1表示不一致但客户点击取消，2表示不一致客户点击确认
    public int isSame(String carNumber,IsSameListenter isSame) {
        isSameListenter=isSame;
        newPoliceNew = carNumber;
        SharedPreferences sp = mContext.getSharedPreferences("activity", Context.MODE_WORLD_READABLE);
        String oldNum = sp.getString("policeNum", "111111");
        if (carNumber.equals(oldNum)) {
            LogUtils.a(" return 0;");
            return 0;
        } else {
            LogUtils.a("准备 return i;;");
            int i = showDialog();
            return i;
        }
    }

    private int showDialog() {
        LogUtils.a("开始showdialog");
        final int[] i = {1};
        audioPlayUtils = new AudioPlayUtils(mContext, R.raw.qhkhbyz_sfghmj);
        audioPlayUtils.play();
        SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(mContext, SweetAlertDialog.CUSTOM_IMAGE_TYPE)
                .setTitleText("是否更换民警")
                .setContentText("如选是进入管理员刷卡确认页面，如选择否则此次刷卡无效")
                .setConfirmText("是")
                .setCancelText("否")
                .setCustomImage(R.mipmap.card_icon)
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.dismissWithAnimation();
                        i[0] = 2;
                        LogUtils.a("点击确认");
                        Intent intent = new Intent(mContext, AuthCardActivity.class);
                        intent.putExtra("changPolice", 1);
                        intent.putExtra("class", mClass);
                        intent.putExtra("newPoliceNew", newPoliceNew);
                        mContext.startActivity(intent);
                        isSameListenter.isSame(2);
                       // Message m = mHandler.obtainMessage();
                       // mHandler.sendMessage(m);
                    }
                })
                .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.dismissWithAnimation();
                        isSameListenter.isSame(1);
                        LogUtils.a("点击取消");
                    }
                });
        LogUtils.a("sweetAlertDialog.show();开始");
        sweetAlertDialog.show();
        LogUtils.a("sweetAlertDialog.show();结束");
        sweetAlertDialog.setCanceledOnTouchOutside(false);
        LogUtils.a("返回值类型", i[0]);
        /*try {
            Looper.loop();
        } catch (Exception e) {
        }*/
        return i[0];
    }

    private IsSameListenter isSameListenter;
    public interface IsSameListenter {
        void isSame(int i);
    }

}
