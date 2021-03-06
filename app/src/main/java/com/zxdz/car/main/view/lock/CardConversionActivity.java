package com.zxdz.car.main.view.lock;

import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zxdz.car.R;
import com.zxdz.car.base.utils.SwitchUtils;
import com.zxdz.car.base.view.BaseActivity;

import butterknife.BindView;

public class CardConversionActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolBar;
    @BindView(R.id.tv_car_name)
    TextView tvCarName;
    @BindView(R.id.tv_car_number)
    TextView tvCarNumber;
    @BindView(R.id.tv_auth_state)
    TextView tvAuthState;
    @BindView(R.id.layout_card_info)
    LinearLayout layoutCardInfo;
    @BindView(R.id.layout_card_read_wait)
    LinearLayout layoutCardReadWait;
    @BindView(R.id.btn_next_success)
    Button btnNextSuccess;
    @BindView(R.id.layout_next)
    LinearLayout layoutNext;

    @Override
    public int getLayoutId() {
        return R.layout.activity_card_conversion;
    }

    @Override
    public void init() {
        setSupportActionBar(mToolBar);
      /*  mToolBar.setNavigationIcon(R.mipmap.back_icon);
        mToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });*/
      /*  RxView.clicks(btnNextSuccess).throttleFirst(200, TimeUnit.MILLISECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {

            }
        });*/
        //模拟测试步骤，后期删除
        layoutNext.setVisibility(View.VISIBLE);
    }

   /* @Override
    protected void onResume() {
        super.onResume();
        if (App.readCardType == 1) {
            new OpenT().start();
        } else {
            new InitT().start();
        }
    }

    @Subscribe(
            thread = EventThread.MAIN_THREAD,
            tags = {
                    @Tag(Constant.READ_CARD_NUMBER)
            }
    )*/
    public void showCardNumber(String carNumber) {
        carNumber = SwitchUtils.ICUnconver(carNumber);
        tvCarNumber.setText(carNumber);
        /*String getname = getname(carNumber);
        tvCarName.setText(getname);*/
        layoutCardReadWait.setVisibility(View.GONE);
        layoutCardInfo.setVisibility(View.VISIBLE);
        layoutNext.setVisibility(View.VISIBLE);
        tvAuthState.setText(getResources().getText(R.string.card_auth_success_text));
    }


}
