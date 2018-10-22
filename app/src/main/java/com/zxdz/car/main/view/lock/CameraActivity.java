package com.zxdz.car.main.view.lock;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.blankj.utilcode.util.LogUtils;
import com.zxdz.car.App;
import com.zxdz.car.R;
import com.zxdz.car.base.helper.CarTravelHelper;
import com.zxdz.car.base.helper.TakePictureHelper;
import com.zxdz.car.base.utils.AudioPlayUtils;
import com.zxdz.car.base.utils.SwitchUtils;
import com.zxdz.car.base.view.BaseActivity;
import com.zxdz.car.main.model.domain.PictureInfo;
import com.zxdz.car.main.service.UploadDataService;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CameraActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.camera_img)
    ImageView cameraImg;
    private Bitmap mBitmap;
    private boolean flag = false;
    private boolean flag1 = true;
    private AudioPlayUtils audioPlayUtils;
    private Intent intentService;


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    public void init() {
        ButterKnife.bind(this);
        basetoobar(toolbar, "拍照上传");
        App.GravityListener_type = 0;//关闭手持机移动报警
        BlueToothActivity.flag2 = true;
        start_flag=false;
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {

                audioPlayUtils = new AudioPlayUtils(CameraActivity.this, R.raw.scwc_qdcmjqxsbpz);
                audioPlayUtils.play();
            }
        }, 600);
        alarm(1);
        intentService = new Intent(this, UploadDataService.class);
        Intent mIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(mIntent, 1);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_camera;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            flag = true;
            start_flag=true;
            Bundle bundle = data.getExtras();
            mBitmap = (Bitmap) bundle.get("data");
            cameraImg.setImageBitmap(mBitmap);
        }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    @OnClick({R.id.camera_ok, R.id.camera_again})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.camera_ok:
                if (!flag) {
                    Toast.makeText(this, "请拍完照片后再点击确认", Toast.LENGTH_SHORT).show();
                    return;
                }
                alarm(2);
                App.UPLOAD_STEP = 5;
                startact(this, OpenCardActivity.class);
                finish();
                break;
            case R.id.camera_again:
                start_flag=false;
                Intent mIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(mIntent, 1);
                break;
        }
    }

    private void alarm(int i) {
        switch (i) {
            case 1://关闭报警
                // TODO: 2017\12\22 拍照保存上传
                break;
            case 2:
                //保存图片，将图片转换base64字符串上传服务器
                String bitmapByte = SwitchUtils.getBitmapByte(mBitmap);
                MediaStore.Images.Media.insertImage(getContentResolver(), mBitmap, "title", "description");
                save(bitmapByte);
                // 上传数据
                saveImageInSDCard(bitmapByte);
                // 开启报警
                break;
        }
    }

    private void save(String inputText) {
        FileOutputStream out = null;
        BufferedWriter writer = null;
        try {
            out = openFileOutput("image", Context.MODE_PRIVATE);
            writer = new BufferedWriter(new OutputStreamWriter(out));
            writer.write(inputText);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 保存图片信息到本地
     *
     * @param imageinfos
     */
    public void saveImageInSDCard(String imageinfos) {
        PictureInfo pictureInfo = new PictureInfo();
        pictureInfo.setLsId(App.LSID.intValue());
        if (CarTravelHelper.carTravelRecord.getId() != null) {
            pictureInfo.setId(CarTravelHelper.carTravelRecord.getId());
        }
        pictureInfo.setGjpzSJ(new Date());
        pictureInfo.setPicture(imageinfos);
        TakePictureHelper.saveWarnInfoToDB(pictureInfo);
        startService(intentService);

    }
}
