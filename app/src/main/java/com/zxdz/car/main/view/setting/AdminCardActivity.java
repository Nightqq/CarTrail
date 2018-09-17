package com.zxdz.car.main.view.setting;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.blankj.utilcode.util.LogUtils;
import com.bumptech.glide.Glide;
import com.zxdz.car.R;
import com.zxdz.car.base.helper.CardHelper;
import com.zxdz.car.base.view.BaseActivity;
import com.zxdz.car.main.adapter.AdminCardItemAdapter;
import com.zxdz.car.main.contract.CardInfoContract;
import com.zxdz.car.main.model.domain.CardInfo;
import com.zxdz.car.main.presenter.CardInfoPresenter;

import java.util.List;

import butterknife.BindView;

/**
 * Created by super on 2017/10/31.
 * 卡号管理(0：管理人员  1：驾驶员 2：干警)
 */

public class AdminCardActivity extends BaseActivity<CardInfoPresenter> implements CardInfoContract.View {

    @BindView(R.id.toolbar)
    Toolbar mToolBar;

    @BindView(R.id.layout_list)
    LinearLayout mListLayout;

    @BindView(R.id.loading_layout)
    LinearLayout mLoadingLayout;

    @BindView(R.id.loading_iv)
    ImageView mLoadingImageView;

    @BindView(R.id.all_admin_list)
    RecyclerView mAllAdminRecyclerView;

    AdminCardItemAdapter mAdminCardItemAdapter;

    LinearLayoutManager mLinearLayoutManager;

    List<CardInfo> adminCardList;

    @Override
    public int getLayoutId() {
        return R.layout.activity_admin_card;
    }

    @Override
    public void init() {

        mToolBar.setTitle("管理员卡号");
        setSupportActionBar(mToolBar);
        mToolBar.setNavigationIcon(R.mipmap.back_icon);
        mToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Glide.with(this).load(R.drawable.loading).into(mLoadingImageView);

        //mPresenter = new CardInfoPresenter(this, this);

        //从初始化信息获取的管理员卡号
        adminCardList = CardHelper.getCardInfoListFromDB();
        for (CardInfo cardInfo : adminCardList) {
            LogUtils.e(cardInfo.getAdminName(),cardInfo.getAdminCardNumber());
        }
        mLinearLayoutManager = new LinearLayoutManager(this);
        mAllAdminRecyclerView.setLayoutManager(mLinearLayoutManager);

        mAdminCardItemAdapter = new AdminCardItemAdapter(this, adminCardList);
        mAllAdminRecyclerView.setAdapter(mAdminCardItemAdapter);

        //mPresenter.getCardInfoList(DeviceUtils.getAndroidID());
    }

    @Override
    public void showCardInfoListData(List<CardInfo> list) {
        if (list != null) {
            mLoadingLayout.setVisibility(View.GONE);
            mListLayout.setVisibility(View.VISIBLE);
        }
        mAdminCardItemAdapter.setNewData(list);
    }
}
