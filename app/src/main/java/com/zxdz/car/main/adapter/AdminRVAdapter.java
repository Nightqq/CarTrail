package com.zxdz.car.main.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zxdz.car.R;
import com.zxdz.car.main.bean.AdminCard;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Lenovo on 2017/10/19.
 */

public class AdminRVAdapter extends RecyclerView.Adapter<AdminRVAdapter.MyViewHolder> {
    private List<AdminCard> mList;
    private LayoutInflater mInflater;

    public void setmList(List<AdminCard> mList) {
        this.mList = mList;
    }
    public List<AdminCard> getmList() {
        return mList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_rcv_item, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(mView);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        AdminCard adminCard = mList.get(position);
        holder.adminRcName.setText(adminCard.admin_name);
        holder.adminRcNum.setText(adminCard.admin_num);
        holder.view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (mItemLongClickListener!=null){
                    mItemLongClickListener.itemLongClick(position);
                }
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.admin_rc_name)
        TextView adminRcName;
        @BindView(R.id.admin_rc_num)
        TextView adminRcNum;
        View view;
        MyViewHolder(View view) {
            super(view);
            this.view=view;
            ButterKnife.bind(this, view);
        }
    }

    private OnItemLongClickListener mItemLongClickListener;
    public void setItemLongClickListener(OnItemLongClickListener itemLongClickListener) {
        mItemLongClickListener = itemLongClickListener;
    }
    public  interface OnItemLongClickListener{
        void itemLongClick(int position);
    }
}
