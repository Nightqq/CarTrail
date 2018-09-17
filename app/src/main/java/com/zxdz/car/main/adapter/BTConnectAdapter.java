package com.zxdz.car.main.adapter;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.zxdz.car.R;
import com.zxdz.car.main.utils.BlueToothHelper;
import com.zxdz.car.main.utils.BlueToothUtils;
import com.zxdz.car.main.view.AuthCardActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018\5\7 0007.
 */

public class BTConnectAdapter extends RecyclerView.Adapter<BTConnectAdapter.MyViewHolder> {

    private List<BluetoothDevice> mList = new ArrayList<BluetoothDevice>();
    private LayoutInflater mInflater;
    private Context context;

    public void addmList(BluetoothDevice device) {
        if (!mList.contains(device)){
            LogUtils.a(device.getName(), device.getAddress());
            mList.add(device);
            LogUtils.a(mList.size() + "");
            notifyDataSetChanged();
        }
    }

    public void delectList() {
        mList.clear();
        notifyDataSetChanged();
    }

    public List<BluetoothDevice> getmList() {
        return mList;
    }

    @Override
    public BTConnectAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View mView = LayoutInflater.from(context).inflate(R.layout.btconnect_rcv, null, false);
        MyViewHolder myViewHolder = new MyViewHolder(mView);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(BTConnectAdapter.MyViewHolder holder, final int position) {
        LogUtils.a("添加内容");
        holder.rcvName.setText(mList.get(position).getName());
        holder.rcvMac.setText(mList.get(position).getAddress());
        holder.view.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
            @Override
            public void onClick(View v) {
                BlueToothHelper.getBlueHelp().unRegister();
                BlueToothHelper.getBlueHelp().ConnectedDevices(mList.get(position).getAddress(), new BlueToothUtils.ConnectedDevicesListenter() {
                    @Override
                    public void connectenDevice(int i) {
                        if (i == 1) { //连接成功跳转管理员刷卡页面
                            context.startActivity(new Intent(context, AuthCardActivity.class));
                        }
                    }
                },false);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.rcv_name)
        TextView rcvName;
        @BindView(R.id.rcv_mac)
        TextView rcvMac;
        View view;

        MyViewHolder(View view) {
            super(view);
            this.view = view;
            ButterKnife.bind(this, view);
        }
    }
}
