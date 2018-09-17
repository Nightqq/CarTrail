package com.zxdz.car.main.view.lock;

import android.bluetooth.BluetoothDevice;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.zxdz.car.R;
import com.zxdz.car.base.view.BaseActivity;
import com.zxdz.car.main.adapter.BTConnectAdapter;
import com.zxdz.car.main.utils.BlueToothHelper;
import com.zxdz.car.main.utils.BlueToothUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
public class BlueToothConnectActivity extends BaseActivity {

    @BindView(R.id.bluetooth_connect_pgb)
    ProgressBar bluetoothConnectPgb;
    @BindView(R.id.bluetooth_connect_scan)
    TextView bluetoothConnectScan;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.bluetooth_rcv)
    RecyclerView bluetoothRcv;
    private BTConnectAdapter btConnectAdapter;

    @Override
    public void init() {
        ButterKnife.bind(this);
        btConnectAdapter = new BTConnectAdapter();
        bluetoothRcv.setLayoutManager(new LinearLayoutManager(this));
        bluetoothRcv.setAdapter(btConnectAdapter);
    }


    @Override
    protected void onStart() {
        BlueToothHelper.getBlueHelp().getDevices(new BlueToothUtils.GetblueToothListenter() {
            @Override
            public void getblueTooth(BluetoothDevice device) {
                btConnectAdapter.addmList(device);
            }
        });
        super.onStart();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_bluetooth_connect;

    }



    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    @OnClick(R.id.bluetooth_connect_scan)
    public void onViewClicked() {
            btConnectAdapter.delectList();
    }

}
