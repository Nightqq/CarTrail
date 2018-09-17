package com.zxdz.car.base.view;

import android.content.Context;
import android.widget.ProgressBar;
import android.widget.TextView;


import com.zxdz.car.R;

import butterknife.BindView;



public class LoadingDialog extends BaseDialog {

    @BindView(R.id.tv_loading_message)
    TextView mLoadingMessageTextView;

    @BindView(R.id.loading)
    ProgressBar mLoadingProgressBar;

    public LoadingDialog(Context context) {
        super(context);
    }


    public void setMessage(String title){
        mLoadingMessageTextView.setText(title);
    }

    @Override
    public void init() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.base_dialog_progress_layout;
    }
}
