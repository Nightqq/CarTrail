package com.zxdz.car.main.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.TextView;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.Poi;
import com.blankj.utilcode.util.TimeUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.hwangjr.rxbus.RxBus;
import com.hwangjr.rxbus.annotation.Subscribe;
import com.hwangjr.rxbus.annotation.Tag;
import com.hwangjr.rxbus.thread.EventThread;
import com.jakewharton.rxbinding.view.RxView;
import com.zxdz.car.App;
import com.zxdz.car.R;
import com.zxdz.car.base.helper.CarTravelHelper;
import com.zxdz.car.base.helper.TrailPointHelper;
import com.zxdz.car.base.utils.AudioPlayUtils;
import com.zxdz.car.base.utils.LocationService;
import com.zxdz.car.base.view.BaseActivity;
import com.zxdz.car.main.model.domain.Constant;
import com.zxdz.car.main.model.domain.TrailPointInfo;
import com.zxdz.car.main.service.UploadDataService;
import com.zxdz.car.main.utils.BlueToothHelper;
import com.zxdz.car.main.utils.BlueToothUtils;
import com.zxdz.car.main.view.lock.BlueToothActivity;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import rx.functions.Action1;

/**
 * 车辆轨迹记录
 */
@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
public class CarTrailActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolBar;

    @BindView(R.id.btn_arrive)
    Button mArriveButton;

    @BindView(R.id.tv_address)
    TextView mAddressTextView;

    private int carTrail = 1;//车辆轨迹记录类型(工作进入，工作出门)
    private LocationService locationService;
    private AudioPlayUtils audioPlayUtils;
    private String resultadress;
    private boolean isretention;
    private Intent intentService;

    @Override
    public int getLayoutId() {
        return R.layout.activity_trail;
    }

    @Override
    public void init() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        App.GravityListener_type = 1;//开启手持机移动报警
        intentService = intentService = new Intent(this, UploadDataService.class);
        SharedPreferences sp = getApplicationContext().getSharedPreferences("spUtils", MODE_PRIVATE);
        if (bundle != null) {
            carTrail = bundle.getInt("car_trail");
            if (carTrail == 1) {
                mToolBar.setTitle("车辆进入轨迹");
                boolean isclickone = sp.getBoolean("isclickone", true);
                if (isclickone) {//是否点击确认到达
                    mArriveButton.setText("到达装卸区");
                } else {
                    mArriveButton.setText("到达装卸区请刷卡确认");
                    mArriveButton.setClickable(false);
                    swipCard();
                }

            } else {
                mToolBar.setTitle("车辆出门轨迹");
                isretention = sp.getBoolean("isretention", false);
                if (isretention) {//有滞留区
                    mArriveButton.setText("到达滞留区");

                } else {//没有滞留区
                    mArriveButton.setText("出门");
                }
            }
        }

        setSupportActionBar(mToolBar);
        RxView.clicks(mArriveButton).throttleFirst(200, TimeUnit.MILLISECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                if (carTrail == 1) {
                    closeLock();//第一次锁车
                } else {
                    outDoor();//滞留区锁车
                }
            }
        });
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (carTrail == 1) {
                    audioPlayUtils = new AudioPlayUtils(CarTrailActivity.this, R.raw.kzqazwc_qwydkzq_kyxs);//控制器安装完成，请勿移动控制器，可以行使
                    audioPlayUtils.play();
                } else {
                    audioPlayUtils = new AudioPlayUtils(CarTrailActivity.this, R.raw.syk_kyslzyq);
                    audioPlayUtils.play();
                }
            }
        }, 600);
    }

    private void swipCard() {
        BlueToothHelper.getBlueHelp().setReceiverMode(new BlueToothUtils.receiveCardIDListener() {
            @Override
            public void receiveCardID(String str) {
                String carNumber = str.replaceAll(" ", "");
                saveAdminCard(carNumber);  //存储卡号到本地数据库0
                Intent intent = new Intent(CarTrailActivity.this, BlueToothActivity.class);
                intent.putExtra("blue_step", 1);
                startActivity(intent);
                startService(intentService);
                finish();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }


    public void closeLock() {
        if (App.baojing_type == 1) {
            return;
        }
        Intent intent = new Intent(CarTrailActivity.this, InstallConfirmActivity.class);
        intent.putExtra("confirm_step", 2);
        startActivity(intent);
        finish();
    }

    public void outDoor() {
        if (App.baojing_type == 1) {
            return;
        }
        if (isretention) {
            Intent intent = new Intent(CarTrailActivity.this, BlueToothActivity.class);
            intent.putExtra("blue_step", 2);
            startActivity(intent);
            finish();
        } else {
            Intent intent = new Intent(CarTrailActivity.this, InstallConfirmActivity.class);
            intent.putExtra("confirm_step", 3);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        // -----------location config ------------
        locationService = ((App) getApplication()).locationService;
        //获取locationservice实例，建议应用中只初始化1个location实例，然后使用，可以参考其他示例的activity，都是通过此种方式获取locationservice实例的
        locationService.registerListener(mListener);
        //注册监听

        locationService.setLocationOption(locationService.getDefaultLocationClientOption());


        locationService.start();//定位SDK
    }

    /***
     * Stop location service
     */
    @Override
    protected void onStop() {
        super.onStop();
        locationService.unregisterListener(mListener); //注销掉监听
        locationService.stop(); //停止定位服务
        mListener = null;
    }

    /*****
     *
     * 定位结果回调，重写onReceiveLocation方法，可以直接拷贝如下代码到自己工程中修改
     *
     */
    private BDAbstractLocationListener mListener = new BDAbstractLocationListener() {


        @Override
        public void onReceiveLocation(BDLocation location) {
            // TODO Auto-generated method stub
            if (null != location && location.getLocType() != BDLocation.TypeServerError) {
                StringBuffer sb = new StringBuffer(256);
                sb.append("time : ");
                /**
                 * 时间也可以使用systemClock.elapsedRealtime()方法 获取的是自从开机以来，每次回调的时间；
                 * location.getTime() 是指服务端出本次结果的时间，如果位置不发生变化，则时间不变
                 */
                sb.append(location.getTime());
                sb.append("\nlocType : ");// 定位类型
                sb.append(location.getLocType());
                sb.append("\nlocType description : ");// *****对应的定位类型说明*****
                sb.append(location.getLocTypeDescription());
                sb.append("\nlatitude : ");// 纬度
                sb.append(location.getLatitude());
                sb.append("\nlontitude : ");// 经度
                sb.append(location.getLongitude());
                sb.append("\nradius : ");// 半径
                sb.append(location.getRadius());
                sb.append("\nCountryCode : ");// 国家码
                sb.append(location.getCountryCode());
                sb.append("\nCountry : ");// 国家名称
                sb.append(location.getCountry());
                sb.append("\ncitycode : ");// 城市编码
                sb.append(location.getCityCode());
                sb.append("\ncity : ");// 城市
                sb.append(location.getCity());
                sb.append("\nDistrict : ");// 区
                sb.append(location.getDistrict());
                sb.append("\nStreet : ");// 街道
                sb.append(location.getStreet());
                sb.append("\naddr : ");// 地址信息
                sb.append(location.getAddrStr());
                sb.append("\nUserIndoorState: ");// *****返回用户室内外判断结果*****
                sb.append(location.getUserIndoorState());
                sb.append("\nDirection(not all devices have value): ");
                sb.append(location.getDirection());// 方向
                sb.append("\nlocationdescribe: ");
                sb.append(location.getLocationDescribe());// 位置语义化信息
                sb.append("\nPoi: ");// POI信息
                if (location.getPoiList() != null && !location.getPoiList().isEmpty()) {
                    for (int i = 0; i < location.getPoiList().size(); i++) {
                        Poi poi = location.getPoiList().get(i);
                        sb.append(poi.getName() + ";");
                    }
                }
                if (location.getLocType() == BDLocation.TypeGpsLocation) {// GPS定位结果
                    sb.append("\nspeed : ");
                    sb.append(location.getSpeed());// 速度 单位：km/h
                    sb.append("\nsatellite : ");
                    sb.append(location.getSatelliteNumber());// 卫星数目
                    sb.append("\nheight : ");
                    sb.append(location.getAltitude());// 海拔高度 单位：米
                    sb.append("\ngps status : ");
                    sb.append(location.getGpsAccuracyStatus());// *****gps质量判断*****
                    sb.append("\ndescribe : ");
                    sb.append("gps定位成功");
                }/* else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {// 网络定位结果
                    // 运营商信息
                    if (location.hasAltitude()) {// *****如果有海拔高度*****
                        sb.append("\nheight : ");
                        sb.append(location.getAltitude());// 单位：米
                    }
                    sb.append("\noperationers : ");// 运营商信息
                    sb.append(location.getOperators());
                    sb.append("\ndescribe : ");
                    sb.append("网络定位成功");
                } else if (location.getLocType() == BDLocation.TypeOffLineLocation) {// 离线定位结果
                    sb.append("\ndescribe : ");
                    sb.append("离线定位成功，离线定位结果也是有效的");
                } else if (location.getLocType() == BDLocation.TypeServerError) {
                    sb.append("\ndescribe : ");
                    sb.append("服务端网络定位失败，可以反馈IMEI号和大体定位时间到loc-bugs@baidu.com，会有人追查原因");
                } else if (location.getLocType() == BDLocation.TypeNetWorkException) {
                    sb.append("\ndescribe : ");
                    sb.append("网络不同导致定位失败，请检查网络是否通畅");
                } else if (location.getLocType() == BDLocation.TypeCriteriaException) {
                    sb.append("\ndescribe : ");
                    sb.append("无法获取有效定位依据导致定位失败，一般是由于手机的原因，处于飞行模式下一般会造成这种结果，可以试着重启手机");
                }*/
                resultadress = "经度" + location.getLongitude() +
                        "纬度" + location.getLatitude() +
                        "速度" + location.getSpeed();
                TrailPointInfo trailPointInfo = new TrailPointInfo();
                trailPointInfo.setGJZBWD(location.getLatitude());
                trailPointInfo.setGJZBJD(location.getLongitude());
                trailPointInfo.setGJSJ(new Date());
                trailPointInfo.setResult("");
                RxBus.get().post(Constant.UPDATE_LOCATION, trailPointInfo);
            }
        }

    };


    @Subscribe(
            thread = EventThread.MAIN_THREAD,
            tags = {
                    @Tag(Constant.UPDATE_LOCATION)
            }
    )
    /**
     * 更新定位信息
     */
    public void updateLocation(TrailPointInfo trailPointInfo) {
        if (mAddressTextView != null) {
            ToastUtils.showLong("轨迹记录中：" + TimeUtils.getNowString());
            if (trailPointInfo != null) {
                mAddressTextView.setText(resultadress);
                TrailPointHelper.saveTrailPointInfoToDB(trailPointInfo);
            }
        }
    }

    /**
     * 读卡成功保存【卡号】
     *
     * @param cardNumber
     */
    public void saveAdminCard(String cardNumber) {
        CarTravelHelper.carTravelRecord.setDLGJ_SCKH(cardNumber);
        CarTravelHelper.carTravelRecord.setDLGJ_SCSJ(new Date());
        CarTravelHelper.carTravelRecord.setZT(40);
        CarTravelHelper.saveCarTravelRecordToDB(CarTravelHelper.carTravelRecord);
    }
}
