package com.zxdz.car.main.view;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.zxdz.car.App;
import com.zxdz.car.R;
import com.zxdz.car.base.helper.CarTravelHelper;
import com.zxdz.car.base.helper.CardHelper;
import com.zxdz.car.base.utils.AudioPlayUtils;
import com.zxdz.car.base.view.BaseActivity;
import com.zxdz.car.main.model.domain.CardInfo;
import com.zxdz.car.main.utils.BlueToothHelper;
import com.zxdz.car.main.utils.BlueToothUtils;

import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class InitReturnActivity extends BaseActivity {

    @BindView(R.id.init_return_electricity)
    TextView initReturnElectricity;
    @BindView(R.id.toolbar)
    Toolbar mToolBar;

    @Override
    public void init() {
        ButterKnife.bind(this);
        mToolBar.setTitle("确认行程结束");
        setSupportActionBar(mToolBar);
        App.GravityListener_type = 0;//关闭手持机移动报警
        if (CarTravelHelper.carTravelRecord != null) {
            //存储管理员初始化数据等待上传
            LogUtils.a("初始化数据等待上传");
            CarTravelHelper.carTravelRecord.setZT(80);
            CarTravelHelper.saveCarTravelRecordToDB(CarTravelHelper.carTravelRecord);
        }
    }

    private long str;
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (str>30){
                initReturnElectricity.setText("智能锁剩余电量："+str+"%");
            }else if (str >= 0 && str <= 30){
                initReturnElectricity.setText("智能锁电量不足："+str+"%");
                initReturnElectricity.setTextColor(Color.RED);
                AudioPlayUtils.getAudio(InitReturnActivity.this,R.raw.znsdlbz_qjscd).play();
            }else {
                initReturnElectricity.setText("智能锁电池损坏，请及时更换");
                initReturnElectricity.setTextColor(Color.RED);
                AudioPlayUtils.getAudio(InitReturnActivity.this,R.raw.znsdcsh_qjsgh).play();
            }
            BlueToothHelper.getBlueHelp().setReceiverMode(new BlueToothUtils.receiveCardIDListener() {
                @Override
                public void receiveCardID(String str) {
                    LogUtils.i("111管理员刷卡返回",str);
                    //调用刷卡刷管理员卡开锁
                    SharedPreferences spUtils = getSharedPreferences("spUtils", Context.MODE_PRIVATE);
                    boolean test = spUtils.getBoolean("test", false);
                    if (test||CardHelper.isAvailableInDB(str.replaceAll(" " ,""))){//如果数据库中存在
                        LogUtils.i("111数据库存在该管理员");
                        closePolice();
                        if ( CarTravelHelper.carTravelRecord!=null){
                            CarTravelHelper.carTravelRecord.setGLY_GHQRSJ(new Date());
                            CarTravelHelper.saveCarTravelRecordToDB(CarTravelHelper.carTravelRecord);
                        }
                        BlueToothHelper.getBlueHelp().closeAll();
                        Intent intent = new Intent(InitReturnActivity.this, MainActivity.class);
                        intent.putExtra("end",1);
                        startActivity(intent);
                        BlueToothHelper.getBlueHelp().setListenerNull();//成功后置空回调
                        finish();
                    }
                }
            });
            //ToastUtils.showShort(str);
        }
    };
    private void closePolice() {
        AudioPlayUtils.getAudio(this,0).stop();
    }
    @Override
    protected void onResume() {
        super.onResume();
        BlueToothHelper.getBlueHelp().enquiriesState(new BlueToothUtils.EnquiriesStateListenter() {
            @Override
            public void enquiriesState(String str) {
            }

            @Override
            public void enquiriesPower(final long str1) {
                str = str1;
                LogUtils.i("车锁电压11", str1);
                runOnUiThread(runnable);
            }
        });

        AudioPlayUtils.getAudio(this,R.raw.gcxcjs).play();//该次行程结束
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        runnable = null;
    }

    @Override
    protected void onStart() {
        super.onStart();
        //调用刷卡刷管理员卡开锁
        List<CardInfo> cardInfoListFromDB = CardHelper.getCardInfoListFromDB();
        if (cardInfoListFromDB!=null&&cardInfoListFromDB.size()>0){
            for (CardInfo admin:cardInfoListFromDB) {
                String adminCardNumber = admin.getAdminCardNumber();
                LogUtils.a("管理员卡号",adminCardNumber);
            }
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_init_return;
    }


    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }
}
