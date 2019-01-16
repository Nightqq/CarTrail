package com.zxdz.car.main.view.setting;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.zxdz.car.R;
import com.zxdz.car.base.helper.ServerIPHelper;
import com.zxdz.car.base.view.BaseActivity;
import com.zxdz.car.base.view.IPEditText;
import com.zxdz.car.main.model.domain.ServerIP;
import com.zxdz.car.main.model.domain.URLConfig;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ServerIPActivity extends BaseActivity {


    @BindView(R.id.server_ip_toolbar)
    Toolbar serverIpToolbar;
    @BindView(R.id.server_ip)
    TextView serverIp;
    private long mLong = 1;
    @Override
    public void init() {
        ButterKnife.bind(this);
        basetoobar(serverIpToolbar, R.string.setting_server_IP);
        initdata();
    }

    private void initdata() {
        List<ServerIP> areaInfoListFromDB = ServerIPHelper.getAreaInfoListFromDB();
        if (areaInfoListFromDB!=null&&areaInfoListFromDB.size()>0){
            ServerIP server_IP = areaInfoListFromDB.get(0);
            serverIp.setText(server_IP.getIp());
            return;
        }
        Toast.makeText(this, "没有数据", Toast.LENGTH_SHORT).show();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_server_ip;
    }


    @OnClick(R.id.server_ip_change)
    public void onViewClicked() {
        showdialog();
    }

    private void showdialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final AlertDialog dialog = builder.create();
        View view = View.inflate(this, R.layout.server_dialog, null);
        dialog.setView(view, 0, 0, 0, 0);
        final IPEditText server_ip = (IPEditText) view.findViewById(R.id.server_edt);
        Button btnOK = (Button) view.findViewById(R.id.server_btn_ok);
        Button btnCancel = (Button) view.findViewById(R.id.server_btn_cancel);
        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ip = server_ip.getText().toString().trim();
                if (ip == null) {
                    Toast.makeText(ServerIPActivity.this, "ip地址不能为空", Toast.LENGTH_SHORT).show();
                }
                ServerIP serverIP = new ServerIP(mLong, ip);
                ServerIPHelper.saveAreaInfoToDB(serverIP);
                initdata();
                dialog.dismiss();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();// 隐藏dialog
            }
        });
        dialog.show();
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }
}
