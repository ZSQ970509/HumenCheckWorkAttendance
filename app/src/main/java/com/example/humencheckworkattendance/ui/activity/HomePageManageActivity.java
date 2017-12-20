package com.example.humencheckworkattendance.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.humencheckworkattendance.R;
import com.example.humencheckworkattendance.base.BaseActivity;
import com.example.humencheckworkattendance.bean.LoginBean;
import com.example.humencheckworkattendance.contact.HomePageManageContact;
import com.example.humencheckworkattendance.presenter.HomePageManagePresenter;
import com.example.humencheckworkattendance.utils.VersionUtils;

import butterknife.BindView;
import butterknife.OnClick;

public class HomePageManageActivity extends BaseActivity<HomePageManagePresenter> implements HomePageManageContact.HomePageManageView {

    @BindView(R.id.home_page_manager_examine)
    ImageView  homepagemanagerexamine;

    TextView textViewUserNameManage;
    ImageView homepageexamineshort;
    ImageView homepageplaycardtoday;
    ImageView mHomepageSocialSecurity;
    ImageView imageViewHistroyManage;

    @BindView(R.id.imageView_Histroy_Manage_Port)
    ImageView imageViewHistroyManagePort;
    @BindView(R.id.textView_UserName_Manage)
    TextView textViewUserNameManagePort;
    @BindView(R.id.home_page_examine_short)
    ImageView homepageexamineshortPort;
    @BindView(R.id.home_page_play_card_today)
    ImageView homepageplaycardtodayPort;
    @BindView(R.id.home_page_month_social_security)
    ImageView mHomepageSocialSecurityPort;
    @BindView(R.id.textView)
    TextView mAppVersionPort;

    @BindView(R.id.imageView_Histroy_Manage_land)
    ImageView imageViewHistroyManageLand;
    @BindView(R.id.textView_UserName_Manage_land)
    TextView textViewUserNameManageLand;
    @BindView(R.id.home_page_examine_short_land)
    ImageView homepageexamineshortLand;
    @BindView(R.id.home_page_play_card_today_land)
    ImageView homepageplaycardtodayLand;
    @BindView(R.id.home_page_month_social_security_land)
    ImageView mHomepageSocialSecurityLand;
    @BindView(R.id.textView_land)
    TextView mAppVersionLand;

    @BindView(R.id.home_page_manage_land)
    LinearLayout mHomepageManageLand;
    @BindView(R.id.home_page_manage_port)
    LinearLayout mHomepageManagePort;

    LoginBean loginBean;
    Intent intent;
    private PopupWindow mPopWindow;

    @Override
    protected HomePageManagePresenter loadPresenter() {
        return new HomePageManagePresenter();
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initView() {
        intent = getIntent();
        loginBean = (LoginBean) intent.getSerializableExtra("userMessage");
        textViewUserNameManage.setText(loginBean.getUserName());
        if (loginBean.getUserTypeId().equals("100")) {
            // homepagemanagerexamine.setVisibility(View.GONE);
            homepageexamineshort.setVisibility(View.GONE);
            homepageplaycardtoday.setVisibility(View.GONE);
            mHomepageSocialSecurity.setVisibility(View.GONE);
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_home_page_manage;
    }

    @Override
    protected void changeScreen() {
        if(mScreenOrientation){
            mAppVersionLand.setText("版本：V"+VersionUtils.getVersionName()+"（Beta）");
        }else {
            mAppVersionPort.setText("版本：V"+VersionUtils.getVersionName()+"（Beta）");
        }
        imageViewHistroyManage = getShowWidgetsOnScreen(imageViewHistroyManageLand, imageViewHistroyManagePort);
        textViewUserNameManage = getShowWidgetsOnScreen(textViewUserNameManageLand, textViewUserNameManagePort);
        homepageexamineshort = getShowWidgetsOnScreen(homepageexamineshortLand, homepageexamineshortPort);
        homepageplaycardtoday = getShowWidgetsOnScreen(homepageplaycardtodayLand, homepageplaycardtodayPort);
        mHomepageSocialSecurity = getShowWidgetsOnScreen(mHomepageSocialSecurityLand, mHomepageSocialSecurityPort);
        setShowView(mHomepageManageLand, mHomepageManagePort);
    }

    @Override
    protected void otherViewClick(View view) {

    }

    @OnClick({R.id.home_page_manager_inserthumen, R.id.home_page_manager_inserthumen_land,
            R.id.home_page_examine_short, R.id.home_page_examine_short_land,
            R.id.home_page_play_card_today, R.id.home_page_play_card_today_land,
            R.id.home_page_month_social_security, R.id.home_page_month_social_security_land,
            R.id.imageView_Histroy_Manage_Port, R.id.imageView_Histroy_Manage_land})

    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.home_page_manager_inserthumen_land:
            case R.id.home_page_manager_inserthumen:
                if (loginBean.getUserTypeId().equals("100")) {

                    intent.setClass(HomePageManageActivity.this, SelectProjectActivity.class);
                    //intent.setClass(HomePageManageActivity.this,SubmitHumenMessageActivity.class);
                } else {
                    Log.e("11111", "11111111");
                    intent.putExtra("isUpdate", false);
                    intent.setClass(HomePageManageActivity.this, InsertHumenManagerActivity.class);
                }
                startActivity(intent);
                break;
            case R.id.home_page_examine_short_land:
            case R.id.home_page_examine_short:
                //intent.setClass(HomePageManageActivity.this,SelectProjectActivity.class);
                intent.setClass(HomePageManageActivity.this, SubmitHumenMessageActivity.class);
                startActivity(intent);
                break;
            case R.id.home_page_play_card_today_land:
            case R.id.home_page_play_card_today:
                intent.setClass(HomePageManageActivity.this, PlayCardTodayActivity.class);
                startActivity(intent);
                break;
            case R.id.home_page_month_social_security_land:
            case R.id.home_page_month_social_security:
                intent.setClass(HomePageManageActivity.this, MonthSocialSecurityActivity.class);
                startActivity(intent);
                break;
            case  R.id.imageView_Histroy_Manage_Port:
            case R.id.imageView_Histroy_Manage_land:
                showPopupWindow();
                break;
        }
    }
    private void showPopupWindow() {
        backgroundAlpha(0.5f);
        View contentView = LayoutInflater.from(HomePageManageActivity.this).inflate(R.layout.view_popupwindow_manage_more, null);
        mPopWindow = new PopupWindow(contentView);
        mPopWindow.setWidth(ViewGroup.LayoutParams.FILL_PARENT);
        mPopWindow.setHeight(ViewGroup.LayoutParams.FILL_PARENT);
        mPopWindow.setOutsideTouchable(true);
        mPopWindow.setFocusable(true);
        mPopWindow.setBackgroundDrawable(new BitmapDrawable());
        RelativeLayout RelativeLayoutPopupWindow = (RelativeLayout) contentView.findViewById(R.id.RelativeLayout_PopupWindow);
        RelativeLayout RelativeLayoutPopupWindowChangePassWord = (RelativeLayout) contentView.findViewById(R.id.RelativeLayout_PopupWindow_ChangePassWord);
        RelativeLayout RelativeLayoutPopupWindowMore = (RelativeLayout) contentView.findViewById(R.id.RelativeLayout_PopupWindow_More);
        RelativeLayoutPopupWindow.setOnClickListener(this);
        RelativeLayoutPopupWindowChangePassWord.setOnClickListener(this);
        RelativeLayoutPopupWindowMore.setOnClickListener(this);

        if (Build.VERSION.SDK_INT < 24) {
            mPopWindow.showAsDropDown(imageViewHistroyManage);
        } else {
            int[] location = new int[2];
            imageViewHistroyManage.getLocationOnScreen(location);
            int x = location[0];
            int y = location[1];
            if (Build.VERSION.SDK_INT == 25) {
                WindowManager wm = (WindowManager) mPopWindow.getContentView().getContext().getSystemService(Context.WINDOW_SERVICE);
                int screenHeight = wm.getDefaultDisplay().getHeight();
                mPopWindow.setHeight(screenHeight - location[1] - imageViewHistroyManage.getHeight());
            }
            mPopWindow.showAtLocation(imageViewHistroyManage, Gravity.NO_GRAVITY, 0, y + imageViewHistroyManage.getHeight());
        }

        mPopWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                backgroundAlpha(1f);
            }
        });

    }
    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        getWindow().setAttributes(lp);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.RelativeLayout_PopupWindow:
                mPopWindow.dismiss();
                backgroundAlpha(1f);
                break;
            case R.id.RelativeLayout_PopupWindow_ChangePassWord:
                mPopWindow.dismiss();
                backgroundAlpha(1f);
                intent.setClass(HomePageManageActivity.this, ChangePassWordActivity.class);
                startActivityForResult(intent, 0X111);
                break;
            case R.id.RelativeLayout_PopupWindow_More:
                mPopWindow.dismiss();
                backgroundAlpha(1f);
                intent.setClass(HomePageManageActivity.this, MoreActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}
