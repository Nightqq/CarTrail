package com.zxdz.car.main.view.lock;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;

import com.acker.simplezxing.activity.CaptureActivity;
import com.blankj.utilcode.util.LogUtils;
import com.zxdz.car.R;
import com.zxdz.car.base.view.BaseActivity;
import com.zxdz.car.main.utils.BlueToothHelper;
import com.zxdz.car.main.utils.BlueToothUtils;
import com.zxdz.car.main.view.AuthCardActivity;

public class BindLockActivity extends BaseActivity {


    @Override
    public void init() {
        Intent intent = new Intent(this, CaptureActivity.class);
        startActivityForResult(intent, CaptureActivity.REQ_CODE);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_bind_lock;
    }


    String mac="54:6C:0E:BD:7E:EE";
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case CaptureActivity.REQ_CODE:
                switch (resultCode) {
                    case RESULT_OK:
                        String stringExtra = data.getStringExtra(CaptureActivity.EXTRA_SCAN_RESULT);
                        LogUtils.a(stringExtra);
                        BlueToothHelper.getBlueHelp().unRegister();
                        BlueToothHelper.getBlueHelp().ConnectedDevices(mac, new BlueToothUtils.ConnectedDevicesListenter() {
                            @Override
                            public void connectenDevice(int i) {
                                if (i == 1) { //连接成功跳转管理员刷卡页面
                                    startActivity(new Intent(BindLockActivity.this, AuthCardActivity.class));
                                    finish();
                                }
                            }
                        },false);
                        break;
                    case RESULT_CANCELED:
                       /* String stringExtra2 = data.getStringExtra(CaptureActivity.EXTRA_SCAN_RESULT);
                        LogUtils.a(stringExtra2);*/
                        break;
                }
                break;
        }
    }
}
