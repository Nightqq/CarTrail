package com.zxdz.car.base.helper;

import android.app.Activity;
import android.content.Context;

import com.blankj.utilcode.util.SnackbarUtils;
import com.blankj.utilcode.util.ToastUtils;


public class TipsHelper {
    enum TipsType {
        TOAST,
        SNAKE
    }

    public static void tips(Context context, String msg, TipsType tipsType) {
        if (tipsType == TipsType.SNAKE) {
            if (context instanceof Activity) {
                SnackbarUtils.with(((Activity) context).findViewById(android.R.id.content)).setMessage(msg).show();
            }
            return;
        }

        if (tipsType == TipsType.TOAST) {
            ToastUtils.showShort(msg);
        }
    }

    public static void tips(Context context, String msg) {
        tips(context, msg, TipsType.SNAKE);
    }
}
