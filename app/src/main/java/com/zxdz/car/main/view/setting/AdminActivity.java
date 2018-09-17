package com.zxdz.car.main.view.setting;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.zxdz.car.R;
import com.zxdz.car.base.helper.AdminCardHelper;
import com.zxdz.car.base.view.BaseActivity;
import com.zxdz.car.main.adapter.AdminRVAdapter;
import com.zxdz.car.main.bean.AdminCard;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.widget.Toast.makeText;

public class AdminActivity extends BaseActivity {
    @BindView(R.id.admin_toolbar)
    Toolbar adminToolbar;
    @BindView(R.id.admin_rv)
    RecyclerView adminRv;
    @BindView(R.id.admin_terminal_number)
    TextView adminTerminalNumber;
    private AdminRVAdapter adminRVAdapter;
    private List<AdminCard> mList;

    @Override
    public int getLayoutId() {
        return R.layout.activity_admin;
    }

    @Override
    public void init() {
        ButterKnife.bind(this);
        basetoobar(adminToolbar, R.string.admin_num);
        mList = new ArrayList<AdminCard>();
        initdata();
    }

    private void initdata() {
        //数据库获取终端机序号；管理人员
        List<com.zxdz.car.main.model.domain.AdminCard> carTravelRecordListFromDB = AdminCardHelper.getCarTravelRecordListFromDB();
        if (carTravelRecordListFromDB != null&&carTravelRecordListFromDB.size()>0) {
            for (com.zxdz.car.main.model.domain.AdminCard adminCard : carTravelRecordListFromDB) {
                AdminCard adminCard1 = new AdminCard(adminCard.getAdmin_name(), adminCard.getAdmin_num());
                mList.add(adminCard1);
            }
        }
        adminRVAdapter = new AdminRVAdapter();
        adminRVAdapter.setmList(mList);
        adminRv.setLayoutManager(new LinearLayoutManager(this));
        adminRv.setAdapter(adminRVAdapter);
        adminRVAdapter.setItemLongClickListener(new AdminRVAdapter.OnItemLongClickListener() {
            @Override
            public void itemLongClick(int position) {
                //长按弹窗popwindow
                showPop(position);
            }
        });
    }

    private void showPop(final int position) {
        final PopupWindow mPopupWindow = new PopupWindow(this);
        View mView = LayoutInflater.from(this).inflate(R.layout.pop_admin, null);
        TextView deletOne = (TextView) mView.findViewById(R.id.delete_one);
        TextView deleteAll = (TextView) mView.findViewById(R.id.delete_all);
        LinearLayout popAdmin = (LinearLayout) mView.findViewById(R.id.pop_admin);
        //设置布局
        mPopupWindow.setContentView(mView);
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setTouchable(true);
        mPopupWindow.setWidth(WindowManager.LayoutParams.MATCH_PARENT);
        mPopupWindow.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        mPopupWindow.showAtLocation(popAdmin, Gravity.BOTTOM, 0, 0);
        //给popwindow条目设置点击监听
        deletOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mList.remove(position);
                adminRVAdapter.notifyDataSetChanged();
                mPopupWindow.dismiss();

            }
        });
        deleteAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mList.clear();
                adminRVAdapter.notifyDataSetChanged();
                mPopupWindow.dismiss();

            }
        });
    }

    @OnClick(R.id.toobar_add)
    public void onViewClicked() {
        makeText(this, "请刷卡", Toast.LENGTH_SHORT).show();
        //点击弹窗弹窗
        showdialog();
    }

    private void showdialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final AlertDialog dialog = builder.create();
        View view = View.inflate(this, R.layout.admin_dialog, null);
        dialog.setView(view, 0, 0, 0, 0);
        final EditText admin_name = (EditText) view.findViewById(R.id.et_admin_name);
        final EditText admin_num = (EditText) view.findViewById(R.id.et_admin_num);
        Button btnOK = (Button) view.findViewById(R.id.admin_btn_ok);
        Button btnCancel = (Button) view.findViewById(R.id.admin_btn_cancel);
        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = admin_name.getText().toString().trim();
                String num = admin_num.getText().toString().trim();
                if (name.equals("") || num.equals("")) {
                    Toast.makeText(AdminActivity.this, "卡号和姓名不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                AdminCardHelper.addCardInfo(new com.zxdz.car.main.model.domain.AdminCard(num,name,"11111"));
                AdminCard adminCard = new AdminCard(name, num);
                mList.add(adminCard);
                adminRVAdapter.setmList(mList);
                adminRVAdapter.notifyDataSetChanged();
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


}
