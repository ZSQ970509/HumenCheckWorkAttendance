package com.example.humencheckworkattendance.ui.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;

import com.example.humencheckworkattendance.R;
import com.example.humencheckworkattendance.base.BaseActivity;
import com.example.humencheckworkattendance.bean.UpdateBean;
import com.example.humencheckworkattendance.contact.WelcomeContact;
import com.example.humencheckworkattendance.presenter.WelcomePresenter;
import com.vector.update_app.utils.AppUpdateUtils;

import butterknife.BindView;

public class WelcomeActivity extends BaseActivity<WelcomePresenter> implements WelcomeContact.WelcomeView {
    @BindView(R.id.imageView_Welcome_Background)
    ImageView imageViewWelcomeBackgroundPort;
    @BindView(R.id.imageView_Welcome_Background_land)
    ImageView imageViewWelcomeBackgroundLand;

    Intent intent = new Intent();

    @Override
    protected WelcomePresenter loadPresenter() {
        return new WelcomePresenter();
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @Override
    protected void initListener() {

    }
//
    @Override
    protected void initView() {
        mPresenter.getVersion(AppUpdateUtils.getVersionCode(this) + "");
//        mPresenter.getIsExistModel(android.os.Build.MODEL + "");
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_welcome;
    }

    @Override
    protected void changeScreen() {
        if (mScreenOrientation) {
            imageViewWelcomeBackgroundLand.setVisibility(View.VISIBLE);
            imageViewWelcomeBackgroundPort.setVisibility(View.GONE);
        } else {
            imageViewWelcomeBackgroundLand.setVisibility(View.GONE);
            imageViewWelcomeBackgroundPort.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void otherViewClick(View view) {

    }

    @Override
    public void getVersionSuccess(final UpdateBean updateBean) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                intent.setClass(WelcomeActivity.this, LoginActivity.class);
                intent.putExtra("isUpdate", true);
                intent.putExtra("updateBean", updateBean);
                startActivity(intent);
                finish();
            }
        },2000);
//        try {
//            intent.setClass(this, LoginActivity.class);
//            intent.putExtra("isUpdate", true);
//            intent.putExtra("updateBean", updateBean);
//            Thread.sleep(2000);
//            startActivity(intent);
//            finish();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

    }

    @Override
    public void getVersionFail(String failMsg) {
        try {
            intent.setClass(this, LoginActivity.class);
           intent.putExtra("isUpdate", false);
            Thread.sleep(2000);
           startActivity(intent);
           finish();
       } catch (InterruptedException e) {
            e.printStackTrace();
       }

    }

    @Override
    public void getModelSuccess(String msg) {
//        imageViewWelcomeBackgroundPort.setImageResource(R.drawable.welcomebackground);
        mPresenter.getVersion(AppUpdateUtils.getVersionCode(this) + "");
    }

    @Override
    public void getModelFail(String failMsg) {
        mPresenter.getVersion(AppUpdateUtils.getVersionCode(this) + "");
       /* AlertDialog.Builder builder = new AlertDialog.Builder(WelcomeActivity.this);
        // 设置提示框的标题
        builder.setTitle("警告：").
                setIcon(R.drawable.icon).
                setMessage(failMsg).
                setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.setCanceledOnTouchOutside(false);
        // 显示对话框
        alertDialog.show();
        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.rgb(88, 190, 252));
        alertDialog.setCancelable(false);*/
    }
}
