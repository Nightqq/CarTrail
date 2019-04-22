package com.zxdz.car.main.view.lock;

import android.annotation.TargetApi;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

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
import java.io.File;
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
    private Intent intentService;
    private int step;


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    public void init() {
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        toolbar.setTitle("拍照上传");
        App.GravityListener_type = 0;//关闭手持机移动报警
        BlueToothActivity.flag2 = true;
        start_flag=false;
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            step = bundle.getInt("blue_step");
        }
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                AudioPlayUtils.getAudio(CameraActivity.this, R.raw.qdcmjpz).play();//请带车民警拍照
            }
        }, 600);
        alarm(1);
        intentService = new Intent(this, UploadDataService.class);

        imgname=new Date()+"";
        Intent mIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        ContentValues contentValues = new ContentValues(2);
        contentValues.put(MediaStore.Images.Media.DATA, getPhotopath());
        //如果想拍完存在系统相机的默认目录,改为
        //contentValues.put(MediaStore.Images.Media.DISPLAY_NAME, "111111.jpg");
        contentValues.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
        Uri mPhotoUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
        mIntent.putExtra(MediaStore.EXTRA_OUTPUT, mPhotoUri);
        startActivityForResult(mIntent, 1);

       /* Intent mIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
        File out = new File(getPhotopath());
        Uri uri = Uri.fromFile(out);
        intent.putExtra(MediaStore.EXTRA_OUTPUT,uri);
        startActivityForResult(mIntent, 1);*/
    }


    static String imgname="";
    private String getPhotopath() {
        // 照片全路径
        String fileName = "";
        // 文件夹路径
        String pathUrl = Environment.getExternalStorageDirectory() + "/mymy/";
        String imageName =imgname;
        File file = new File(pathUrl);
        file.mkdirs();// 创建文件夹
        fileName = pathUrl + imageName;
        return fileName;
    }
    private Bitmap getBitmapFromUrl(String url, double width, double height) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true; // 设置了此属性一定要记得将值设置为false
        Bitmap bitmap = BitmapFactory.decodeFile(url);
        // 防止OOM发生
        options.inJustDecodeBounds = false;
        int mWidth = bitmap.getWidth();
        int mHeight = bitmap.getHeight();
        Matrix matrix = new Matrix();
        float scaleWidth = 1;
        float scaleHeight = 1;
        // 按照固定宽高进行缩放
        // 这里希望知道照片是横屏拍摄还是竖屏拍摄
        // 因为两种方式宽高不同，缩放效果就会不同
        // 这里用了比较笨的方式
        if (mWidth <= mHeight) {
            scaleWidth = (float) (width / mWidth);
            scaleHeight = (float) (height / mHeight);
        } else {
            scaleWidth = (float) (height / mWidth);
            scaleHeight = (float) (width / mHeight);
        }
        // 按照固定大小对图片进行缩放
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap newBitmap = Bitmap.createBitmap(bitmap, 0, 0, mWidth, mHeight,
                matrix, true);
        // 用完了记得回收
        bitmap.recycle();
        return newBitmap;
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
            /*Bundle bundle = data.getExtras();
            mBitmap = (Bitmap) bundle.get("data");*/
            mBitmap = getBitmapFromUrl(getPhotopath(), 350, 350);
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
                Intent intent = new Intent(this, OpenCardActivity.class);
                intent.putExtra("blue_step", step);
                startActivity(intent);
                finish();
                break;
            case R.id.camera_again:
                start_flag=false;
                imgname=new Date()+"";
                Intent mIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                ContentValues contentValues = new ContentValues(2);
                contentValues.put(MediaStore.Images.Media.DATA, getPhotopath());
                //如果想拍完存在系统相机的默认目录,改为
                //contentValues.put(MediaStore.Images.Media.DISPLAY_NAME, "111111.jpg");
                contentValues.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
                Uri mPhotoUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
                mIntent.putExtra(MediaStore.EXTRA_OUTPUT, mPhotoUri);
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
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mBitmap.recycle();
        System.gc();
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
        //后期添加状态值45，照片上传后更改
        CarTravelHelper.carTravelRecord.setZT(45);
        CarTravelHelper.saveCarTravelRecordToDB(CarTravelHelper.carTravelRecord);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

}
