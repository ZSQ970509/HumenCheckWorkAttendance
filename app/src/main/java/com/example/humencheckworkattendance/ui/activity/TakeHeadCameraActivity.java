package com.example.humencheckworkattendance.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.humencheckworkattendance.R;
import com.example.humencheckworkattendance.base.BaseActivity;
import com.example.humencheckworkattendance.contact.TakeIdNumCameraContact;

import com.example.humencheckworkattendance.presenter.TakeHeadCameraPresenter;
import com.example.humencheckworkattendance.widget.CamearSurfaceChangeView;
import com.example.humencheckworkattendance.widget.CameraSurfaceChangNewView;

import butterknife.BindView;
import butterknife.OnClick;


public class TakeHeadCameraActivity extends BaseActivity<TakeHeadCameraPresenter> implements TakeIdNumCameraContact.TakeIdNumCameraView {
    @BindView(R.id.fl_photo)
    FrameLayout frameLayoutCameraIdNum;
    @BindView(R.id.cameraSurfaceView)
    CameraSurfaceChangNewView mySurfaceView;

    @BindView(R.id.imageView_TakeHeadPhoto_port)
    ImageView imageViewTakeHeadPhotoPort;
    @BindView(R.id.imageView_TakeHeadPhoto_land)
    ImageView imageViewTakeHeadPhotoLand;
    @BindView(R.id.imageView_port)
    ImageView imageViewPort;
    @BindView(R.id.imageView_land)
    ImageView imageViewLand;

    LinearLayout.LayoutParams flParams;
    int cameraPosition = 1;
    private int mScreenWidth;
    private int mScreenHeight;
    @Override
    protected TakeHeadCameraPresenter loadPresenter() {
        return new TakeHeadCameraPresenter();
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @Override
    protected void initListener() {

    }
    @Override
    protected void initView() {
        getScreenMetrix(this);

    }

    @Override
    protected void changeScreen() {
        if(mScreenOrientation){
//            flParams = (LinearLayout.LayoutParams) frameLayoutCameraIdNum.getLayoutParams();
//            flParams.height =mScreenHeight;
//            flParams.width = (int) (mScreenHeight * 1.33334F);
//            frameLayoutCameraIdNum.setLayoutParams(flParams);
            imageViewTakeHeadPhotoPort.setVisibility(View.GONE);
            imageViewTakeHeadPhotoLand.setVisibility(View.VISIBLE);
            imageViewPort.setVisibility(View.GONE);
            imageViewLand.setVisibility(View.VISIBLE);
        }else {
//            flParams = (LinearLayout.LayoutParams) frameLayoutCameraIdNum.getLayoutParams();
//            flParams.height = (int) (mScreenWidth * 1.33334F);
//            flParams.width = mScreenWidth;
//            frameLayoutCameraIdNum.setLayoutParams(flParams);
            imageViewTakeHeadPhotoPort.setVisibility(View.VISIBLE);
            imageViewTakeHeadPhotoLand.setVisibility(View.GONE);
            imageViewPort.setVisibility(View.VISIBLE);
            imageViewLand.setVisibility(View.GONE);
        }

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_take_head_camera_new;
    }
    @OnClick({R.id.imageView_TakeHeadPhoto_port,R.id.imageView_TakeHeadPhoto_land,R.id.imageView_Camera_Change,R.id.imageView_Back})
    public void onViewClicked(View view) {
        switch (view.getId()){
            case R.id.imageView_TakeHeadPhoto_port:
            case R.id.imageView_TakeHeadPhoto_land:
                try {

                    mySurfaceView.takePicture();
                } catch (Exception e) {
                    // TODO: handle exception
                    toast("拍照失败");
                }
                break;
            case R.id.imageView_Camera_Change:
                cameraPosition = mySurfaceView.changeCamera(cameraPosition,frameLayoutCameraIdNum,flParams);
                break;
            case R.id.imageView_Back:
                finish();
                break;
            default:
                break;
        }
    }
    @Override
    protected void otherViewClick(View view) {

    }
    /**
     * 获取当前手机屏幕的宽高
     * @param context
     */
    private void getScreenMetrix(Context context) {
        WindowManager WM = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        WM.getDefaultDisplay().getMetrics(outMetrics);
        mScreenWidth = outMetrics.widthPixels;
        mScreenHeight = outMetrics.heightPixels;
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.e("requestCode",resultCode+"11111");
        switch (resultCode) {
            case 1:
                Intent mIntent = getIntent();
                //mIntent.putExtra("change01", "1000");
               // mIntent.putExtra("change02", "2000");
                // 设置结果，并进行传送
                this.setResult(1, data);
                this.finish();
                break;
            case 2:
                break;
            default:
                break;
        }
    }
}
