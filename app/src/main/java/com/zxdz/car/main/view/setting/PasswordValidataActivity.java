package com.zxdz.car.main.view.setting;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.inputmethodservice.KeyboardView;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;

import com.blankj.utilcode.util.LogUtils;
import com.zxdz.car.R;
import com.zxdz.car.base.view.BaseActivity;
import com.zxdz.car.base.view.PasswardView;

/**
 * Created by Administrator on 2018/6/5.
 */

public class PasswordValidataActivity extends BaseActivity{

    private PasswardView passwardView;
    private InputMethodManager inputMethodManager;

    @Override
    public void init() {
        passwardValidata();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_password_validata;
    }

    //密码验证对话框
    private void passwardValidata(){
        final AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        View view = View.inflate(this,R.layout.activity_card_set_passwardvalidata, null);
        alertDialog.setView(view);
        passwardView = (PasswardView)view.findViewById(R.id.pwd_view);
        Button btnCancel = (Button) view.findViewById(R.id.server_btn_cancel);
        Button btnClipbord = (Button)view.findViewById(R.id.server_btn_clipboard);
        btnClipbord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                passwardView.inPutAgain();
            }
        });
        passwardView.setOnFinishInput(new PasswardView.OnPasswordInputFinish() {
            @Override
            public void inputFinish() {
                alertDialog.dismiss();
                Intent intent = new Intent(PasswordValidataActivity.this, SettingActivity.class);
                startActivity(intent);
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                finish();
            }
        });
        alertDialog.show();
        passwardView.postDelayed(runnable,100);

    }
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.toggleSoftInput(0, InputMethodManager.SHOW_FORCED);
        }
    };
}
