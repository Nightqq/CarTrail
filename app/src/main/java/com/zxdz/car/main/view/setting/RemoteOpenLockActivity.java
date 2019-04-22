package com.zxdz.car.main.view.setting;

import android.content.SharedPreferences;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;

import com.blankj.utilcode.util.ToastUtils;
import com.suke.widget.SwitchButton;
import com.zxdz.car.R;
import com.zxdz.car.base.view.BaseActivity;

import java.util.ArrayList;
import java.util.List;

public class RemoteOpenLockActivity extends BaseActivity implements SwitchButton.OnCheckedChangeListener, View.OnTouchListener {

    private SwitchButton set_1;
    private SwitchButton set_2;
    private SwitchButton set_3;
    private List<SwitchButton> switchButtons;
    private SharedPreferences sp;
    private boolean isIntercept = true;
    private boolean ispressed = false;

    @Override
    public void init() {
        set_1 = (SwitchButton) findViewById(R.id.start_set_1);
        set_2 = (SwitchButton) findViewById(R.id.start_set_2);
        set_3 = (SwitchButton) findViewById(R.id.start_set_3);
        Toolbar toolbar = (Toolbar) findViewById(R.id.open_lock_toolbar);
        basetoobar(toolbar, "开锁设置");

        set_1.setOnCheckedChangeListener(this);
        set_2.setOnCheckedChangeListener(this);
        set_3.setOnCheckedChangeListener(this);

        set_1.setOnTouchListener(this);
        set_2.setOnTouchListener(this);
        set_3.setOnTouchListener(this);

        switchButtons = new ArrayList<>();
        switchButtons.add(set_1);
        switchButtons.add(set_2);
        switchButtons.add(set_3);
        sp = getSharedPreferences("OpenLockStyle", MODE_PRIVATE);
        int open_style = sp.getInt("open_style", 2);
        if (open_style == 1) {
            isIntercept = false;
            set_1.setChecked(true);
        } else if (open_style == 2) {
            set_2.setChecked(true);
        } else {
            isIntercept = false;
            set_3.setChecked(true);
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_remote_open_lock;
    }

    @Override
    public void onCheckedChanged(SwitchButton view, boolean isChecked) {
        if (!isChecked) {//除了勾选的动作，其他的取消勾选动作交给系统处理
            if (view.getId() != R.id.start_set_2) {//第二个按钮关掉后不拦截
                if (ispressed) {//如果手动关第一和第二个按钮则打开中间的按钮
                    set_2.setChecked(true);
                    ispressed = false;
                }
            }
            return;
        }
        switch (view.getId()) {
            case R.id.start_set_1:
                isIntercept = false;
                setSwitchButton(set_1, 1);
                break;
            case R.id.start_set_2:
                if (isChecked) {//关闭不用走，打开走
                    isIntercept = true;
                    setSwitchButton(set_2, 2);
                }
                break;
            case R.id.start_set_3:
                isIntercept = false;
                setSwitchButton(set_3, 3);
                break;
        }
    }

    private void setSwitchButton(SwitchButton s, int i) {
        if (sp != null) {
            sp.edit().putInt("open_style", i).commit();
        }
        for (SwitchButton sbt : switchButtons
                ) {
            if (!sbt.equals(s)) {
                sbt.setChecked(false);
            }
        }
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if (view.getId() == R.id.start_set_2 && isIntercept == true) {
            ToastUtils.showShort("至少要选择一种刷卡方式");
            return true;
        }
        if (view.getId() == R.id.start_set_1) {
            if (set_1.isChecked() && !set_3.isChecked()) {
                ispressed = true;
            }
        } else if (view.getId() == R.id.start_set_3) {
            if (!set_1.isChecked() && set_3.isChecked()) {
                ispressed = true;
            }
        }
        return false;
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }
}
