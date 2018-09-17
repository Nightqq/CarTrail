package com.zxdz.car.main.model.engin;

import android.content.Context;

import com.alibaba.fastjson.TypeReference;
import com.kk.securityhttp.domain.ResultInfo;
import com.kk.securityhttp.engin.HttpCoreEngin;
import com.zxdz.car.base.model.BaseEngin;
import com.zxdz.car.main.model.domain.CardInfoWrapper;
import com.zxdz.car.main.model.domain.SettingInfo;
import com.zxdz.car.main.model.domain.URLConfig;

import java.util.HashMap;
import java.util.Map;

import rx.Observable;


public class CardInfoEngin extends BaseEngin {

    private Context mContext;

    public CardInfoEngin(Context context) {
        super(context);
        mContext = context;
    }

    public Observable<CardInfoWrapper> cardInfoList(String imel) {
        Map<String, String> params = new HashMap<>();
        //params.put("imel", imel);
        return HttpCoreEngin.get(mContext).rxpost(URLConfig.getinstance().getCARD_INFO_URL(), new TypeReference<CardInfoWrapper>() {
                }.getType(), null,
                false, true,
                false);
    }

}
