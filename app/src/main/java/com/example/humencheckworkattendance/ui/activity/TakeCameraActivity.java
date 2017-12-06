package com.example.humencheckworkattendance.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.humencheckworkattendance.R;
import com.example.humencheckworkattendance.base.BaseActivity;
import com.example.humencheckworkattendance.contact.TakeCameraContact;
import com.example.humencheckworkattendance.presenter.TakeCameraPresenter;
import com.example.humencheckworkattendance.widget.CamearSurfaceChangeView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 照相页面
 * Created by Administrator on 2017-11-28.
 */

public class TakeCameraActivity extends BaseActivity<TakeCameraPresenter> implements TakeCameraContact.TakeCameraView{

    @BindView(R.id.cameraSurfaceView)
    CamearSurfaceChangeView mySurfaceView;
    @BindView(R.id.imageView_TakePhote)
    ImageView mTakePhote;

    @Override
    protected TakeCameraPresenter loadPresenter() {
        return new TakeCameraPresenter();
    }

    @Override
    protected void initData(Bundle savedInstanceState) {}

    @Override
    protected void initListener() {}

    @Override
    protected void initView() {}

    @Override
    protected int getLayoutId() {
        return R.layout.activity_take_camera;
    }

    @Override
    protected void otherViewClick(View view) {}

    @Override
    protected void onResume() {
        mTakePhote.setClickable(true);
        super.onResume();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.e("requestCode",resultCode+"11111");
        switch (resultCode) {
            case 1:
                //照相完且上传照片后回调
                Intent mIntent = getIntent();
                //mIntent.putExtra("change01", "1000");
                // mIntent.putExtra("change02", "2000");
                // 设置结果，并进行传送
                this.setResult(1, data);
                String imgUrl = data.getStringExtra("url");
//                LogUtil.logE("照相页面，收到的图片Url"+imgUrl);
                this.finish();
                break;
            case 2:
                break;
            default:
                break;
        }
    }
    @OnClick({R.id.imageView_TakePhote,R.id.imageView_Back})
    public void onViewClicked(View view) {
        switch (view.getId()){
            case R.id.imageView_TakePhote:
                try {
                    mySurfaceView.setPhotoSize(1440,1080);
                    mySurfaceView.takePicture();
                    mTakePhote.setClickable(false);
//                    LogUtil.logE("点击了照相按钮");
                } catch (Exception e) {
                    toast("拍照失败");
                    mTakePhote.setClickable(true);
                }
                break;
            case R.id.imageView_Back:
                finish();
                break;
            default:
                break;
        }
    }
}
