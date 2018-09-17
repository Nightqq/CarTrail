package com.zxdz.car.main.view.setting;

import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.zxdz.car.R;
import com.zxdz.car.base.helper.CarTravelHelper;
import com.zxdz.car.base.view.BaseActivity;
import com.zxdz.car.main.model.domain.CarTravelRecord;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ChangeActivity extends BaseActivity {


    @BindView(R.id.change_toolbar)
    Toolbar changeToolbar;
    @BindView(R.id.change_police_num)
    TextView changePoliceNum;
    @BindView(R.id.change_stream_id)
    TextView changeStreamId;
    @BindView(R.id.change_admin_num)
    TextView changeAdminNum;
    @BindView(R.id.change_driver_num)
    TextView changeDriverNum;

    @Override
    public void init() {
        ButterKnife.bind(this);
        basetoobar(changeToolbar, R.string.change_police);
        initdata();
    }

    private void initdata() {
        List<CarTravelRecord> carTravelRecordListFromDB = CarTravelHelper.getCarTravelRecordListFromDB();
        if (carTravelRecordListFromDB != null && carTravelRecordListFromDB.size() > 0) {
            CarTravelRecord carTravelRecord = carTravelRecordListFromDB.get(carTravelRecordListFromDB.size() - 1);
            changeStreamId.setText(carTravelRecord.getLS_ID()+"");
            changeAdminNum.setText(carTravelRecord.getDLGJ_LYKH());
            //changePoliceNum.setText(carTravelRecord.getUsePoliceCardNumber());
            //changeDriverNum.setText(carTravelRecord.getDriverCardNumber());
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_change;
    }

    @OnClick(R.id.toobar_change)
    public void onViewClicked() {
        Toast.makeText(this, "请刷管理员卡", Toast.LENGTH_SHORT).show();
        showdialog();
    }

    private void showdialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final AlertDialog dialog = builder.create();
        View view = View.inflate(this, R.layout.change_dialog, null);
        dialog.setView(view, 0, 0, 0, 0);
        //找控件
        final EditText admincard = (EditText) view.findViewById(R.id.et_admincard);
        final EditText edtoldcard = (EditText) view.findViewById(R.id.et_oldcard);
        final EditText edtnewcard = (EditText) view.findViewById(R.id.et_newcard);
        Button btnOK = (Button) view.findViewById(R.id.btn_ok);
        Button btnCancel = (Button) view.findViewById(R.id.btn_cancel);
        //点击事件
        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String admin_card = admincard.getText().toString().trim();
                String old_card = edtoldcard.getText().toString().trim();
                String new_card = edtnewcard.getText().toString().trim();
                if (admin_card.equals("") || old_card.equals("") || new_card.equals("")) {
                    return;
                }
                changePoliceNum.setText(new_card);
                // TODO: 2017/10/16
                //跟换卡号上传数据，成功后更新界面
                Toast.makeText(ChangeActivity.this, "跟换成功失败反馈", Toast.LENGTH_SHORT).show();
                //跟换数据
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
