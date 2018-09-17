package com.zxdz.car.main.adapter;

import android.content.Context;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zxdz.car.R;
import com.zxdz.car.main.model.domain.CardInfo;

import java.util.List;

public class AdminCardItemAdapter extends BaseQuickAdapter<CardInfo, BaseViewHolder> {

    private Context mContext;

    public AdminCardItemAdapter(Context context, List<CardInfo> datas) {
        super(R.layout.admin_card_item, datas);
        //this.mContext = context;
    }

    @Override
    protected void convert(final BaseViewHolder helper, final CardInfo item) {
        helper.setText(R.id.tv_admin_card_name, item.getAdminName()).setText(R.id.tv_admin_card_number, item.getAdminCardNumber());
        String cardRoleName = "";
        switch (item.getCardType()) {
            case 0:
                cardRoleName = "管理人员";
                break;
            case 1:
                cardRoleName = "驾驶员";
                break;
            case 2:
                cardRoleName = "干警";
                break;
            default:
                break;
        }

        helper.setText(R.id.tv_admin_card_type, cardRoleName);
    }
}