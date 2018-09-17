package com.zxdz.car.main.adapter;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.zxdz.car.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

/**
 * Created by Lenovo on 2017/10/26.
 */

public class ExpandAdapter extends BaseExpandableListAdapter {
    private String[] groups={"扫描的蓝牙设备"};
    private HashMap<String,List<BluetoothDevice>> devices=new HashMap<>();
    public BluetoothDevice getDevice(int groupPos,int childPos){
        return devices.get(groups[groupPos]).get(childPos);
    }

    @Override
    public int getGroupCount() {
        return groups.length;
    }
    public void addPairedDevices(BluetoothDevice device){
        List<BluetoothDevice> paireDevices = devices.get(groups[0]);
        if (paireDevices == null) {
            paireDevices=new ArrayList<>();
            devices.put(groups[0],paireDevices);
        }
        if (!paireDevices.contains(device)){
            paireDevices.add(device);
            notifyDataSetChanged();
        }
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        int count;
        try {
            count=devices.get(groups[groupPosition]).size();
        }catch (NullPointerException e){
            count=0;
        }
        return count;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return null;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return null;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if (convertView==null){
            Context context = parent.getContext();
            convertView= LayoutInflater.from(context).inflate(android.R.layout.test_list_item,parent,false);
            convertView.setPadding(100,0,0,0);
        }
        TextView textView= (TextView) convertView;
        textView.setTextSize(20);
        textView.setTextColor(Color.BLACK);
        textView.setText(groups[groupPosition]);
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        if (convertView==null){
            Context context = parent.getContext();
            convertView= LayoutInflater.from(context).inflate(android.R.layout.test_list_item,parent,false);
            convertView.setPadding(10,3,0,3);
        }
        TextView textView= (TextView) convertView;
        BluetoothDevice device = devices.get(groups[groupPosition]).get(childPosition);
        textView.setText(device.getName()+"\n"+device.getAddress());
        textView.setTextColor(Color.BLACK);
        textView.setTextSize(20);
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
