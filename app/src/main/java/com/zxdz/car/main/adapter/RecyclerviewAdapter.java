package com.zxdz.car.main.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;

import com.blankj.utilcode.util.LogUtils;
import com.zxdz.car.R;
import com.zxdz.car.main.model.domain.UnloadAreaInfo;

import java.util.List;

/**
 * Created by Administrator on 2019/4/15.
 */

public class RecyclerviewAdapter extends RecyclerView.Adapter<RecyclerviewAdapter.ViewHolder> {
    private Context context;
    private List<UnloadAreaInfo> UnloadAreaInfo;
    private int checkedposition = -1;

    public RecyclerviewAdapter(Context context, List<UnloadAreaInfo> data, checkedChangedListener listener) {
        this.context = context;
        this.UnloadAreaInfo = data;
        this.listener = listener;
    }

    public void addUnload(List<UnloadAreaInfo> mUnloadAreaInfo){
        UnloadAreaInfo=mUnloadAreaInfo;
        notifyDataSetChanged();
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.item_recyclervew, parent, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.name.setText(UnloadAreaInfo.get(position).getArea_name());
        //holder.name.setPadding();//设置间距
        if (checkedposition != position) {
            holder.name.setChecked(false);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.name.setChecked(true);
                checkedposition = position;
                listener.changedlistener(holder.name.getText()+"");
                notifyDataSetChanged();
            }
        });
    }

    //局部刷新
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position, List<Object> payloads) {
        if (!payloads.isEmpty()) {
            onBindViewHolder(holder, position);
        } else {
            holder.name.setText(UnloadAreaInfo.get(position).getArea_name());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    holder.name.setChecked(true);
                }
            });
            if (checkedposition == position) {
                holder.name.setChecked(true);
            }
        }
        super.onBindViewHolder(holder, position, payloads);
    }

    @Override
    public int getItemCount() {
        if (UnloadAreaInfo!=null){
            return UnloadAreaInfo.size();
        }else {
            return 0;
        }

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final CheckedTextView name;

        public ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.area_name);
        }
    }

    private checkedChangedListener listener = null;

    public interface checkedChangedListener {
        void changedlistener(String text);
    }
}
