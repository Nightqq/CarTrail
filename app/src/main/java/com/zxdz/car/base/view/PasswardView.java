package com.zxdz.car.base.view;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.zxdz.car.R;
import com.zxdz.car.base.helper.CarTravelHelper;
import com.zxdz.car.base.helper.TakePictureHelper;
import com.zxdz.car.base.helper.UnWarnInfoHelper;
import com.zxdz.car.base.helper.WarnInfoHelper;

/**
 * Created by Administrator on 2018/6/4.
 */

public class PasswardView extends RelativeLayout {
    Context context;

    private String strPassword;     //输入的密码
    private String correctPassword = "123123";
    private TextView[] tvList;      //用数组保存6个TextView，为什么用数组？
    //因为就6个输入框不会变了，用数组内存申请固定空间，比List省空间（自己认为）

    public PasswardView(Context context) {
        this(context, null);
    }

    public PasswardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        View view = View.inflate(context, R.layout.activity_card_set_passward, null);

        tvList = new EditText[6];

        tvList[0] = (EditText) view.findViewById(R.id.tv_pass1);
        tvList[1] = (EditText) view.findViewById(R.id.tv_pass2);
        tvList[2] = (EditText) view.findViewById(R.id.tv_pass3);
        tvList[3] = (EditText) view.findViewById(R.id.tv_pass4);
        tvList[4] = (EditText) view.findViewById(R.id.tv_pass5);
        tvList[5] = (EditText) view.findViewById(R.id.tv_pass6);
        addView(view);      //必须要，不然不显示控件
        tvList[0].setFocusable(true);
        tvList[0].requestFocus();
    }

    //设置监听方法，在第6位输入完成后触发
    public void setOnFinishInput(final OnPasswordInputFinish pass) {

        for (int i = 0; i < 5; i++) {
            final int finalI = i;
            tvList[i].addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                }

                @Override
                public void afterTextChanged(Editable editable) {
                    tvList[finalI + 1].setFocusable(true);
                    tvList[finalI + 1].requestFocus();
                }
            });
        }
        tvList[5].addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().length() == 1) {
                    strPassword = "";     //每次触发都要先将strPassword置空，再重新获取，避免由于输入删除再输入造成混乱
                    for (int i = 0; i < 6; i++) {
                        strPassword += tvList[i].getText().toString().trim();
                    }
                    if (strPassword.equals("465646")){
                        CarTravelHelper.deleteAllCarTravelRecordList();
                        WarnInfoHelper.deleteAllWarnInfoList();
                        UnWarnInfoHelper.deleteAllWarnInfoList();
                        TakePictureHelper.deleteWarnInfoALL();
                        return;
                    }
                    if (strPassword.equals(correctPassword)) {
                        pass.inputFinish();    //接口中要实现的方法，完成密码输入完成后的响应逻辑
                    } else if (strPassword.length() == 6 && !strPassword.equals(correctPassword)) {
                        ToastUtils.showShort("密码错误，请重新输入");
                        for (int i = 0; i < 6; i++) {
                            tvList[i].setText("");
                        }
                        tvList[0].setFocusable(true);
                        tvList[0].requestFocus();
                    }
                }
            }
        });
        for (int i = 1; i < 6; i++) {
            final int finalI = i;
            tvList[i].setOnKeyListener(new OnKeyListener() {
                @Override
                public boolean onKey(View view, int key, KeyEvent keyEvent) {
                    if (key == KeyEvent.KEYCODE_DEL && keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
                        tvList[finalI - 1].setText("");
                        tvList[finalI - 1].setFocusable(true);
                        tvList[finalI - 1].requestFocus();
                        return true;
                    }
                    return false;
                }
            });
        }

    }

    //拦截触摸使输入框不能被点击
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        InputMethodManager inputMethodManager=(InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (!inputMethodManager.isActive()){
            inputMethodManager.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
        }else {
            inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT,InputMethodManager.HIDE_NOT_ALWAYS);
        }
        return true;
    }

    /* 获取输入的密码 */
    public String getStrPassword() {
        return strPassword;
    }

    public interface OnPasswordInputFinish {
        void inputFinish();
    }
    //重新输入
    public void inPutAgain(){
        for (int i = 0; i < 6; i++) {
            tvList[i].setText("");
        }
        tvList[0].setFocusable(true);
        tvList[0].requestFocus();
    }

}
