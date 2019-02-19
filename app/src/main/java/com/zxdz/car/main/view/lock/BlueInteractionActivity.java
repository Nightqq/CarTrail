package com.zxdz.car.main.view.lock;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.LogUtils;
import com.zxdz.car.R;
import com.zxdz.car.base.utils.AudioPlayUtils;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BlueInteractionActivity extends AppCompatActivity {


    @BindView(R.id.tv_car_number)
    TextView tvCarNumber;
    @BindView(R.id.tv_car_name)
    TextView tvCarName;
    @BindView(R.id.tv_alarm)
    TextView tvAlarm;
    @BindView(R.id.tv_dept)
    TextView tvDept;
    private BluetoothAdapter mBluetoothAdapter;
    private BluetoothDevice selectDevice;
    private UUID MY_UUID = UUID.fromString("6d10657e-5ab8-45d6-a2bf-333643232d1c");
    private BluetoothSocket clientSocket;
    private OutputStream os;
    private AcceptThread thread;
    private final String NAME = "Bluetooth_Socket";

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            super.handleMessage(msg);
            // 通过msg传递过来的信息，吐司一下收到的信息
            String obj = (String) msg.obj;
            if (obj.equals("000000")){
                AudioPlayUtils.getAudio(BlueInteractionActivity.this, R.raw.baojing).play(true);
                return;
            }else if (obj.equals("111111")){
                AudioPlayUtils.getAudio(BlueInteractionActivity.this,0).stop();
                return;
            }
            Toast.makeText(BlueInteractionActivity.this, obj, Toast.LENGTH_SHORT).show();
            tvCarNumber.setText(obj);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blue_interaction);
        ButterKnife.bind(this);
        open();
    }


    private void open() {
        if (mBluetoothAdapter == null) {
            LogUtils.a("打开蓝牙");
            mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
            mBluetoothAdapter.enable();
        }
        // 实例接收客户端传过来的数据线程
        LogUtils.a("开启线程");
        thread = new AcceptThread();
        // 线程开始
        thread.start();
    }

    // 服务端接收信息线程
    private class AcceptThread extends Thread {
        private BluetoothServerSocket serverSocket;// 服务端接口
        private BluetoothSocket socket;// 获取到客户端的接口
        private InputStream is;// 获取到输入流
        private OutputStream os;// 获取到输出流

        public AcceptThread() {
            try {
                // 通过UUID监听请求，然后获取到对应的服务端接口
                LogUtils.a("获取serverSocket");
                serverSocket = mBluetoothAdapter.listenUsingRfcommWithServiceRecord(NAME, MY_UUID);
            } catch (Exception e) {
                // TODO: handle exception
            }
        }

        public void run() {
            LogUtils.a("开始run");
            try {
                // 接收其客户端的接口
                socket = serverSocket.accept();
                // 获取到输入流
                is = socket.getInputStream();
                // 获取到输出流
                os = socket.getOutputStream();
                LogUtils.a("开始循环");
                // 无线循环来接收数据
                while (true) {
                    // 创建一个128字节的缓冲
                    byte[] buffer = new byte[128];
                    // 每次读取128字节，并保存其读取的角标
                    int count = is.read(buffer);
                    os.write("收到数据了".getBytes());
                    // 创建Message类，向handler发送数据
                    Message msg = new Message();
                    // 发送一个String的数据，让他向上转型为obj类型
                    msg.obj = new String(buffer, 0, count, "utf-8");
                    // 发送数据
                    LogUtils.a("发送收到的数据");
                    handler.sendMessage(msg);
                }
            } catch (Exception e) {
                // TODO: handle exception

                e.printStackTrace();
            }

        }
    }


    //设置蓝牙可被扫描
    private void ensureDiscoverable() {
        if (mBluetoothAdapter.getScanMode() != BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE) {
            Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
            discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 3000);
            startActivity(discoverableIntent);
        }
    }
}
