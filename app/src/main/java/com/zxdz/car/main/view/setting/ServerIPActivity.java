package com.zxdz.car.main.view.setting;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
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

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ServerIPActivity extends BaseActivity {


    @BindView(R.id.server_ip_toolbar)
    Toolbar serverIpToolbar;
    @BindView(R.id.wifi_name)
    TextView wifiname;
    @BindView(R.id.server_ip)
    TextView serverIp;
    @BindView(R.id.server_dk)
    TextView serverdk;
    @BindView(R.id.wifi_name_2)
    TextView wifiname2;
    @BindView(R.id.server_ip_2)
    TextView serverIp2;
    @BindView(R.id.server_dk_2)
    TextView serverdk2;
    @BindView(R.id.person_id)
    TextView personid;
    @BindView(R.id.person_factory)
    TextView personfactory;
    private long mLong = 1;

    @Override
    public void init() {
        ButterKnife.bind(this);
        basetoobar(serverIpToolbar, R.string.setting_server_IP);
        initdata();
    }

    private void initdata() {
        List<ServerIP> areaInfoListFromDB = ServerIPHelper.getAreaInfoListFromDB();
        if (areaInfoListFromDB != null && areaInfoListFromDB.size() > 0) {
            ServerIP server_IP = areaInfoListFromDB.get(0);
            if (server_IP.getWifi_name() != null) {
                wifiname.setText(server_IP.getWifi_name());
            }
            if (server_IP.getIp() != null) {
                serverIp.setText(server_IP.getIp());
            }
            if (server_IP.getDk() != null) {
                serverdk.setText(server_IP.getDk());
            }

            if (server_IP.getWifi_name_2() != null) {
                wifiname2.setText(server_IP.getWifi_name_2());
            }
            if (server_IP.getIp_2() != null) {
                serverIp2.setText(server_IP.getIp_2());
            }
            if (server_IP.getDk() != null) {
                serverdk2.setText(server_IP.getDk_2());
            }
            if (server_IP.getPersonID() != null) {
                personid.setText(server_IP.getPersonID());
            }
            if (server_IP.getPersonfactory() != null) {
                personfactory.setText(server_IP.getPersonfactory());
            }
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_server_ip;
    }


    @OnClick({R.id.wifi_name_change,R.id.server_ip_change,R.id.server_dk_change,R.id.wifi_name_change_2,R.id.server_ip_change_2,R.id.server_dk_change_2, R.id.person_id_change, R.id.person_factory_change})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.wifi_name_change:
                showwifinamedialog(1);
                break;
            case R.id.server_ip_change:
                showdialog(1);
                break;
            case R.id.server_dk_change:
                showdkdialog(1);
                break;
            case R.id.wifi_name_change_2:
                showwifinamedialog(2);
                break;
            case R.id.server_ip_change_2:
                showdialog(2);
                break;
            case R.id.server_dk_change_2:
                showdkdialog(2);
                break;
            case R.id.person_id_change:
                showIdDialog();
                break;
            case R.id.person_factory_change:
                showFactoryDialog();
                break;
        }
    }

    private void showwifinamedialog(final int i) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final AlertDialog dialog = builder.create();
        View view = View.inflate(this, R.layout.wifi_name_dialog, null);
        dialog.setView(view, 0, 0, 0, 0);
        final EditText viewById = (EditText) view.findViewById(R.id.wifi_dialog_edt);
        Button btnOK = (Button) view.findViewById(R.id.wifi_dialog_ok);
        Button btnCancel = (Button) view.findViewById(R.id.wifi_dialog_cancel);
        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = viewById.getText().toString().trim();
                if (id == null) {
                    Toast.makeText(ServerIPActivity.this, "wifi名称不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                // TODO: 2019\3\15 0015 id判断对应监狱，存储
                ServerIP serverIP;
                List<ServerIP> areaInfoListFromDB = ServerIPHelper.getAreaInfoListFromDB();
                if (areaInfoListFromDB != null && areaInfoListFromDB.size() > 0) {
                    serverIP = areaInfoListFromDB.get(0);
                } else {
                    serverIP = new ServerIP();
                }
                if (i==1){
                    serverIP.setWifi_name(id);
                    ServerIPHelper.saveAreaInfoToDB(serverIP);
                    wifiname.setText(id);
                }else if (i==2){
                    serverIP.setWifi_name_2(id);
                    ServerIPHelper.saveAreaInfoToDB(serverIP);
                    wifiname2.setText(id);
                }
                dialog.dismiss();
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void showdkdialog(final int i) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final AlertDialog dialog = builder.create();
        View view = View.inflate(this, R.layout.person_dk_dialog, null);
        dialog.setView(view, 0, 0, 0, 0);
        final EditText viewById = (EditText) view.findViewById(R.id.dk_dialog_edt);
        Button btnOK = (Button) view.findViewById(R.id.dk_dialog_ok);
        Button btnCancel = (Button) view.findViewById(R.id.dk_dialog_cancel);
        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = viewById.getText().toString().trim();
                if (id == null) {
                    Toast.makeText(ServerIPActivity.this, "端口不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                // TODO: 2019\3\15 0015 id判断对应监狱，存储
                ServerIP serverIP;
                List<ServerIP> areaInfoListFromDB = ServerIPHelper.getAreaInfoListFromDB();
                if (areaInfoListFromDB != null && areaInfoListFromDB.size() > 0) {
                    serverIP = areaInfoListFromDB.get(0);
                } else {
                    serverIP = new ServerIP();
                }

                if (i==1){
                    serverIP.setDk(id);
                    ServerIPHelper.saveAreaInfoToDB(serverIP);
                    serverdk.setText(id);
                }else if (i==2){
                    serverIP.setDk_2(id);
                    ServerIPHelper.saveAreaInfoToDB(serverIP);
                    serverdk2.setText(id);
                }

                dialog.dismiss();
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void showFactoryDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final AlertDialog dialog = builder.create();
        View view = View.inflate(this, R.layout.person_factory_dialog, null);
        dialog.setView(view, 0, 0, 0, 0);
        final EditText viewById = (EditText) view.findViewById(R.id.factory_dialog_edt);
        Button btnOK = (Button) view.findViewById(R.id.factory_dialog_ok);
        Button btnCancel = (Button) view.findViewById(R.id.factory_dialog_cancel);
        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = viewById.getText().toString().trim();
                if (id == null) {
                    Toast.makeText(ServerIPActivity.this, "监狱厂区不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                // TODO: 2019\3\15 0015 id判断对应监狱，存储
                ServerIP serverIP;
                List<ServerIP> areaInfoListFromDB = ServerIPHelper.getAreaInfoListFromDB();
                if (areaInfoListFromDB != null && areaInfoListFromDB.size() > 0) {
                    serverIP = areaInfoListFromDB.get(0);
                } else {
                    serverIP = new ServerIP();
                }
                serverIP.setPersonfactory(id);
                ServerIPHelper.saveAreaInfoToDB(serverIP);
                personfactory.setText(id);
                dialog.dismiss();
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void showIdDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final AlertDialog dialog = builder.create();
        View view = View.inflate(this, R.layout.person_id_dialog, null);
        dialog.setView(view, 0, 0, 0, 0);
        final EditText viewById = (EditText) view.findViewById(R.id.id_dialog_edt);
        Button btnOK = (Button) view.findViewById(R.id.id_dialog_ok);
        Button btnCancel = (Button) view.findViewById(R.id.id_dialog_cancel);
        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = viewById.getText().toString().trim();
                if (id == null) {
                    Toast.makeText(ServerIPActivity.this, "监狱id不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                // TODO: 2019\3\15 0015 id判断对应监狱，存储
                ServerIP serverIP;
                List<ServerIP> areaInfoListFromDB = ServerIPHelper.getAreaInfoListFromDB();
                if (areaInfoListFromDB != null && areaInfoListFromDB.size() > 0) {
                    serverIP = areaInfoListFromDB.get(0);
                } else {
                    serverIP = new ServerIP();
                }
                serverIP.setPersonID(id);
                ServerIPHelper.saveAreaInfoToDB(serverIP);
                personid.setText(id);
                dialog.dismiss();
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void showdialog(final int i) {
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
                    return;
                }
                ServerIP serverIP;
                List<ServerIP> areaInfoListFromDB = ServerIPHelper.getAreaInfoListFromDB();
                if (areaInfoListFromDB != null && areaInfoListFromDB.size() > 0) {
                    serverIP = areaInfoListFromDB.get(0);
                } else {
                    serverIP = new ServerIP();
                }
                if (i==1){
                    serverIP.setIp(ip);
                    ServerIPHelper.saveAreaInfoToDB(serverIP);
                    serverIp.setText(ip);
                }else if (i==2){
                    serverIP.setIp_2(ip);
                    ServerIPHelper.saveAreaInfoToDB(serverIP);
                    serverIp2.setText(ip);
                }
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
