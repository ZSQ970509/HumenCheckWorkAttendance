package com.example.humencheckworkattendance.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.humencheckworkattendance.R;
import com.example.humencheckworkattendance.base.BaseActivity;
import com.example.humencheckworkattendance.bean.LoginBean;
import com.example.humencheckworkattendance.contact.HomePageManageContact;
import com.example.humencheckworkattendance.presenter.HomePageManagePresenter;

import butterknife.BindView;
import butterknife.OnClick;

public class HomePageManageActivity extends BaseActivity<HomePageManagePresenter> implements HomePageManageContact.HomePageManageView{
    @BindView(R.id.textView_UserName_Manage)
    TextView textViewUserNameManage;
    @BindView(R.id.home_page_manager_inserthumen)
    ImageView  homepagemanagerinserthumen;
    @BindView(R.id.home_page_manager_examine)
    ImageView  homepagemanagerexamine;
    @BindView(R.id.home_page_examine_short)
    ImageView  homepageexamineshort;
    @BindView(R.id.home_page_play_card_today)
    ImageView  homepageplaycardtoday;
    @BindView(R.id.home_page_month_social_security)
    ImageView  mHomepageSocialSecurity;
    LoginBean loginBean;
    Intent intent;
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
        loginBean = (LoginBean)intent.getSerializableExtra("userMessage");
        textViewUserNameManage.setText(loginBean.getUserName());
        if(loginBean.getUserTypeId().equals("100")){
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
    protected void otherViewClick(View view) {

    }
    @OnClick({R.id.home_page_manager_inserthumen,R.id.home_page_examine_short,R.id.home_page_play_card_today,R.id.home_page_month_social_security})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.home_page_manager_inserthumen:
                if(loginBean.getUserTypeId().equals("100")) {

                    intent.setClass(HomePageManageActivity.this, SelectProjectActivity.class);
                    //intent.setClass(HomePageManageActivity.this,SubmitHumenMessageActivity.class);
                }else{
                    Log.e("11111","11111111");
                    intent.putExtra("isUpdate",false);
                    intent.setClass(HomePageManageActivity.this, InsertHumenManagerActivity.class);
                }
                startActivity(intent);
                break;
            case R.id.home_page_examine_short:
                //intent.setClass(HomePageManageActivity.this,SelectProjectActivity.class);
                intent.setClass(HomePageManageActivity.this,SubmitHumenMessageActivity.class);
                startActivity(intent);
                break;
            case R.id.home_page_play_card_today:
                intent.setClass(HomePageManageActivity.this,PlayCardTodayActivity.class);
                startActivity(intent);
                break;
            case R.id.home_page_month_social_security:
                intent.setClass(HomePageManageActivity.this,MonthSocialSecurityActivity.class);
                startActivity(intent);
                break;
        }
    }
}
