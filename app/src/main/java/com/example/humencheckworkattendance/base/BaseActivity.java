package com.example.humencheckworkattendance.base;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.humencheckworkattendance.ProApplication;
import com.example.humencheckworkattendance.R;
import com.example.humencheckworkattendance.bean.User;
import com.example.humencheckworkattendance.mvp.IView;
import com.example.humencheckworkattendance.utils.ActivityUtils;
import com.example.humencheckworkattendance.utils.LogUtils;
import com.example.humencheckworkattendance.utils.PermissionListener;
import com.example.humencheckworkattendance.utils.ProgressDialogUtils;
import com.example.humencheckworkattendance.widget.NetworkStateView;
import com.gyf.barlibrary.ImmersionBar;
import com.joker.api.Permissions4M;

import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * Created by GaoSheng on 2016/9/13.
 */

public abstract class BaseActivity<P extends BasePresenter> extends AppCompatActivity implements
        IView, View.OnClickListener, NetworkStateView.OnRefreshListener {
    protected View view;

    protected P mPresenter;
    public Context mContext;
    private Unbinder unbinder;

    private ProgressDialogUtils progressDialog;

    private NetworkStateView networkStateView;

    private static PermissionListener mPermissionListener;
    private static final int CODE_REQUEST_PERMISSION = 1;
    /**
     * 屏幕的方向：横屏(true)   竖屏(false)
     */
    protected boolean mScreenOrientation = false;
    protected int getRequestedOrientation = 90;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //取消标题
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(getLayoutId());
        unbinder = ButterKnife.bind(this);
        initScreenOrientation();

        ImmersionBar.with(this)
                .statusBarColor(R.color.colorPrimary)
                .fitsSystemWindows(true)
                .init();   //所有子类都将继承这些相同的属性

        mPresenter = loadPresenter();
        initCommonData();
        ActivityUtils.addActivity(this);
        mContext = this;
        initDialog();

        initView();
        initListener();
        initData(savedInstanceState);
    }


    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        View view = getLayoutInflater().inflate(R.layout.activity_base, null);
        //设置填充activity_base布局
        super.setContentView(view);

        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT) {
            view.setFitsSystemWindows(true);
        }

        //加载子类Activity的布局
        initDefaultView(layoutResID);
    }

    /**
     * 初始化默认布局的View
     *
     * @param layoutResId 子View的布局id
     */
    private void initDefaultView(int layoutResId) {
        networkStateView = (NetworkStateView) findViewById(R.id.nsv_state_view);
        FrameLayout container = (FrameLayout) findViewById(R.id.fl_activity_child_container);
        View childView = LayoutInflater.from(this).inflate(layoutResId, null);
        container.addView(childView, 0);
    }


    protected abstract P loadPresenter();

    private void initCommonData() {

        if (mPresenter != null)
            mPresenter.attachView(this);
    }

    protected abstract void initData(Bundle savedInstanceState);

    protected abstract void initListener();

    protected abstract void initView();

    protected abstract int getLayoutId();

    protected abstract void otherViewClick(View view);

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[]
            grantResults) {
        Permissions4M.onRequestPermissionsResult(BaseActivity.this, requestCode, grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    /**
     * 点击的事件的统一的处理
     *
     * @param view
     */
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            default:
                otherViewClick(view);
                break;
        }

    }


    /**
     * @param str 显示一个内容为str的toast
     */
    public void toast(String str) {
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
    }

    /**
     * @param contentId 显示一个内容为contentId指定的toast
     */
    public void toast(int contentId) {
        Toast.makeText(this, contentId, Toast.LENGTH_SHORT).show();
    }


    /**
     * @param str 日志的处理
     */
    public void LogE(String str) {
        LogUtils.e(getClass(), str);
    }


    /**
     * 设置返回toolbar
     *
     * @param toolbarCommon
     * @param title
     * @param titleString
     */
    public void setCommonBackToolBar(Toolbar toolbarCommon, TextView title, String titleString) {
        toolbarCommon.setTitle("");
        setSupportActionBar(toolbarCommon);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        title.setText(titleString);

    }

    public void setCommonBackToolBarRun(Toolbar toolbarCommon, TextView title) {
        toolbarCommon.setTitle("");
        setSupportActionBar(toolbarCommon);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }


    private void initDialog() {
        progressDialog = new ProgressDialogUtils(this, R.style.Progress);
    }

    /**
     * 显示加载中的布局
     */
    public void showLoadingView() {
        networkStateView.showLoading();
    }

    /**
     * 显示加载完成后的布局(即子类Activity的布局)
     */
    public void showContentView() {
        networkStateView.showSuccess();
    }

    /**
     * 显示没有网络的布局
     */
    public void showNoNetworkView() {
        networkStateView.showNoNetwork();
        networkStateView.setOnRefreshListener(this);
    }

    /**
     * 显示没有数据的布局
     */
    public void showEmptyView() {
        networkStateView.showEmpty();
        networkStateView.setOnRefreshListener(this);
    }

    /**
     * 显示数据错误，网络错误等布局
     */
    public void showErrorView() {
        networkStateView.showError();
        networkStateView.setOnRefreshListener(this);
    }


    @Override
    public void onNetworkSRefresh() {
        onNetworkViewRefresh();
    }


    /**
     * 重新请求网络
     */
    public void onNetworkViewRefresh() {

    }

    /**
     * 显示加载的ProgressDialog
     */
    public void showProgressDialog() {
        progressDialog.showProgressDialog();
    }

    /**
     * 显示有加载文字ProgressDialog，文字显示在ProgressDialog的下面
     *
     * @param text 需要显示的文字
     */
    public void showProgressDialogWithText(String text) {
        progressDialog.showProgressDialogWithText(text);
    }

    /**
     * 显示加载成功的ProgressDialog，文字显示在ProgressDialog的下面
     *
     * @param message 加载成功需要显示的文字
     * @param time    需要显示的时间长度(以毫秒为单位)
     */
    public void showProgressSuccess(String message, long time) {
        progressDialog.showProgressSuccess(message, time);
    }

    /**
     * 显示加载成功的ProgressDialog，文字显示在ProgressDialog的下面
     * ProgressDialog默认消失时间为1秒(1000毫秒)
     *
     * @param message 加载成功需要显示的文字
     */
    public void showProgressSuccess(String message) {
        progressDialog.showProgressSuccess(message);
    }

    /**
     * 显示加载失败的ProgressDialog，文字显示在ProgressDialog的下面
     *
     * @param message 加载失败需要显示的文字
     * @param time    需要显示的时间长度(以毫秒为单位)
     */
    public void showProgressFail(String message, long time) {
        progressDialog.showProgressFail(message, time);
    }

    /**
     * 显示加载失败的ProgressDialog，文字显示在ProgressDialog的下面
     * ProgressDialog默认消失时间为1秒(1000毫秒)
     *
     * @param message 加载成功需要显示的文字
     */
    public void showProgressFail(String message) {
        progressDialog.showProgressFail(message);
    }

    /**
     * 隐藏加载的ProgressDialog
     */
    public void dismissProgressDialog() {
        progressDialog.dismissProgressDialog();
    }


    public void startActivity(Intent intent, boolean isNeedLogin) {
        if (isNeedLogin) {
            User user = ProApplication.getInstance().getUser();
            if (user != null) {
                super.startActivity(intent);
            } else {

//                ProApplication.getInstance().putIntent(intent);
//                Intent loginIntent = new Intent(this
//                        , LoginActivity.class);
//                super.startActivity(loginIntent);

            }

        } else {
            super.startActivity(intent);
        }

    }


    public void startActivityForResult(Intent intent, boolean isNeedLogin, int requestCode) {


        if (isNeedLogin) {

            User user = ProApplication.getInstance().getUser();
            if (user != null) {
                super.startActivityForResult(intent, requestCode);
            } else {

//                ProApplication.getInstance().putIntent(intent);
//                Intent loginIntent = new Intent(mContext, LoginActivity.class);
//                super.startActivityForResult(loginIntent,requestCode);

            }

        } else {
            super.startActivityForResult(intent, requestCode);
        }

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.detachView();
        }
        unbinder.unbind();
        ImmersionBar.with(this).destroy();  //不调用该方法，如果界面bar发生改变，在不关闭app的情况下，退出此界面再进入将记忆最后一次bar改变的状态
        ActivityUtils.removeActivity(this);

    }


    @TargetApi(19)
    private void setTranslucentStatus(Activity activity, boolean on) {
        Window win = activity.getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

    /**
     * 初始化横屏/竖屏的标识
     */
    private void initScreenOrientation() {
        Configuration mConfiguration = this.getResources().getConfiguration(); //获取设置的配置信息
        int ori = mConfiguration.orientation; //获取屏幕方向
        if (ori == mConfiguration.ORIENTATION_LANDSCAPE) {
            mScreenOrientation = true;//横屏
            Log.e("1111", getRequestedOrientation+"");


        } else if (ori == mConfiguration.ORIENTATION_PORTRAIT) {
            mScreenOrientation = false;//竖屏
            Log.e("1111", getRequestedOrientation+"");


        }

        changeScreen();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Log.e("1111", "onConfigurationChanged横屏");
            mScreenOrientation = true;
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            Log.e("1111", "onConfigurationChanged竖屏");
            mScreenOrientation = false;
        }else if (newConfig.orientation == Configuration.ORIENTATION_UNDEFINED) {
            Log.e("1111", "onConfigurationChanged竖屏");
            mScreenOrientation = false;
        }

        changeScreen();
    }

    /**
     * 转屏和onCreate时 设置显示 横屏/竖屏的布局
     */
    protected void changeScreen() {
    }

    /**
     * 获取当前应显示的控件
     * @param landEdt   横屏的EditText
     * @param portEdt   竖屏的EditText
     * @return
     */
    protected EditText getShowWidgetsOnScreen(EditText landEdt, EditText portEdt) {
        if (mScreenOrientation) {//横屏
            landEdt.setText(portEdt.getText() + "");
            return landEdt;
        } else {//竖屏
            portEdt.setText(landEdt.getText() + "");
            return portEdt;
        }

    }

    protected CheckBox getShowWidgetsOnScreen(CheckBox landCb, CheckBox portCb) {
        if (mScreenOrientation) {//横屏
            landCb.setChecked(portCb.isChecked());
            return landCb;
        } else {//竖屏
            portCb.setChecked(landCb.isChecked());
            return portCb;
        }
    }
    protected RelativeLayout getShowWidgetsOnScreen(RelativeLayout landRl, RelativeLayout portRl) {
        if (mScreenOrientation) {//横屏
            landRl.setVisibility(portRl.getVisibility());
            return landRl;
        } else {//竖屏
            portRl.setVisibility(landRl.getVisibility());
            return portRl;
        }
    }
    protected LinearLayout getShowWidgetsOnScreen(LinearLayout landLl, LinearLayout portLl) {
        if (mScreenOrientation) {//横屏
            landLl.setVisibility(portLl.getVisibility());
            return landLl;
        } else {//竖屏
            portLl.setVisibility(landLl.getVisibility());
            return portLl;
        }
    }
    protected TextView getShowWidgetsOnScreen(TextView landTv, TextView portTv) {
        if (mScreenOrientation) {//横屏
            landTv.setText(portTv.getText());
            landTv.setVisibility(portTv.getVisibility());
            return landTv;
        } else {//竖屏
            portTv.setText(landTv.getText());
            portTv.setVisibility(landTv.getVisibility());
            return portTv;
        }
    }
    protected ImageView getShowWidgetsOnScreen(ImageView landTvImg, ImageView portImg) {
        if (mScreenOrientation) {//横屏
            landTvImg.setImageDrawable(portImg.getDrawable());//前景(对应src属性)
            landTvImg.setBackgroundDrawable(portImg.getBackground());//背景(对应background属性)
            landTvImg.setVisibility(portImg.getVisibility());
            return landTvImg;
        } else {//竖屏
            portImg.setImageDrawable(landTvImg.getDrawable());
            portImg.setBackgroundDrawable(landTvImg.getBackground());
            portImg.setVisibility(landTvImg.getVisibility());
            return portImg;
        }
    }

    /**
     * 设置 当前应显示的视图
     * @param landLL    横屏视图
     * @param portLL    竖屏视图
     */
    protected void setShowView(LinearLayout landLL, LinearLayout portLL) {
        if (mScreenOrientation) {//横屏
            landLL.setVisibility(View.VISIBLE);
            portLL.setVisibility(View.GONE);
        } else {//竖屏
            landLL.setVisibility(View.GONE);
            portLL.setVisibility(View.VISIBLE);
        }
    }
}

