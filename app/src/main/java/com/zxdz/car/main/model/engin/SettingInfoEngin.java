package com.zxdz.car.main.model.engin;

import android.content.Context;

import com.alibaba.fastjson.TypeReference;
import com.kk.securityhttp.domain.ResultInfo;
import com.kk.securityhttp.engin.HttpCoreEngin;
import com.zxdz.car.base.model.BaseEngin;
import com.zxdz.car.main.model.domain.SettingInfo;
import com.zxdz.car.main.model.domain.TerminalInfo;
import com.zxdz.car.main.model.domain.URLConfig;

import java.util.HashMap;
import java.util.Map;

import rx.Observable;


public class SettingInfoEngin extends BaseEngin {

    private Context mContext;

    public SettingInfoEngin(Context context) {
        super(context);
        mContext = context;
    }

    /**
     * 初始化设备信息
     *
     * @param imel
     * @return
     */
    public Observable<ResultInfo<TerminalInfo>> initEquInfo(String imel) {
        Map<String, String> params = new HashMap<>();
        params.put("ZDJMC", imel);
        return HttpCoreEngin.get(mContext).rxpost(URLConfig.getinstance().getINIT_EQU_URL(), new TypeReference<ResultInfo<TerminalInfo>>() {
                }.getType(), params,
                false, false,
                false);
    }

    /**
     * 根据设备号获取服务器设置的信息
     *
     * @param zdjId
     * @return
     */
    public Observable<ResultInfo<SettingInfo>> loadSettingInfo(String zdjId) {
        Map<String, String> params = new HashMap<>();
        //TODO,值暂时写固定，后面修改
        params.put("ZDJID", zdjId);
        return HttpCoreEngin.get(mContext).rxpost(URLConfig.getinstance().getEQU_INFO_URL(), new TypeReference<ResultInfo<SettingInfo>>() {
                }.getType(), params,
                false, false,
                false);
    }
}
