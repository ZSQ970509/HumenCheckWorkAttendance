package com.example.humencheckworkattendance.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.humencheckworkattendance.R;
import com.example.humencheckworkattendance.base.BaseActivity;
import com.example.humencheckworkattendance.contact.TakeIdNumCameraContact;
import com.example.humencheckworkattendance.presenter.TakeIdNumCameraPresenter;
import com.example.humencheckworkattendance.widget.CameraSurfaceView;

import butterknife.BindView;
import butterknife.OnClick;


public class TakeIdNumCameraActivity extends BaseActivity<TakeIdNumCameraPresenter> implements TakeIdNumCameraContact.TakeIdNumCameraView {
//    @BindView(R.id.frameLayout_Camera_IdNum)
//    FrameLayout frameLayoutCameraIdNum;

    //    @BindView(R.id.imageView_TakePhote)
//    ImageView imageViewTakePhoto;
//    @BindView(R.id.imageView_Back)
//    ImageView  imageViewBack;

    @BindView(R.id.imageView_CameraBold)
    ImageView imageViewCameraBold;

    @BindView(R.id.imageView_TakePhote_land)
    ImageView imageViewTakePhotoLand;
    @BindView(R.id.imageView_TakePhote_port)
    ImageView imageViewTakePhotoPort;

    @BindView(R.id.cameraSurfaceView)
    CameraSurfaceView mySurfaceView;
    private int mScreenWidth;
    private int mScreenHeight;

    @Override
    protected TakeIdNumCameraPresenter loadPresenter() {
        return new TakeIdNumCameraPresenter();
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @Override
    protected void changeScreen() {
        if(mScreenOrientation){
            imageViewTakePhotoLand.setVisibility(View.VISIBLE);
            imageViewTakePhotoPort.setVisibility(View.GONE);
        }else {
            imageViewTakePhotoLand.setVisibility(View.GONE);
            imageViewTakePhotoPort.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initView() {
        getScreenMetrix(this);
        /*LinearLayout.LayoutParams flParams = (LinearLayout.LayoutParams) frameLayoutCameraIdNum.getLayoutParams();
        flParams.height = (int) (mScreenWidth * 1.33334F);
        flParams.width = mScreenWidth;
        frameLayoutCameraIdNum.setLayoutParams(flParams);*/
        mySurfaceView.setImg(imageViewCameraBold);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_take_id_num_camera;
    }

    @OnClick({R.id.imageView_TakePhote_land, R.id.imageView_TakePhote_port, R.id.imageView_Back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imageView_TakePhote_land:
            case R.id.imageView_TakePhote_port:
                try {
                    mySurfaceView.takePicture();
                } catch (Exception e) {
                    toast("拍照失败");
                }
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
     *
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
        Log.e("requestCode", resultCode + "11111");
        switch (resultCode) {
            case 1:
                Intent mIntent = getIntent();
                //mIntent.putExtra("change01", "1000");
                // mIntent.putExtra("change02", "2000");
                // 设置结果，并进行传送
                this.setResult(2, data);
                this.finish();
                break;
            default:
                break;
        }
    }
}
