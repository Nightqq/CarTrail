package com.zxdz.car.main.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zxdz.car.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Lenovo on 2017/11/2.
 */

public class AreaLocusRVAdapter extends RecyclerView.Adapter<AreaLocusRVAdapter.MyViewHolder> {
    private List<String> mList;
    private LayoutInflater mInflater;

    public void setmList(List<String> mList) {
        this.mList = mList;
    }

    public List<String> getmList() {
        return mList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.arealocus_rcv_item, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(mView);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        String string = mList.get(position);
        holder.arealocueRcvTv.setText(string);
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.arealocue_rcv_tv)
        TextView arealocueRcvTv;
        View view;
        MyViewHolder(View view) {
            super(view);
            this.view=view;
            ButterKnife.bind(this, view);
        }
    }

}
