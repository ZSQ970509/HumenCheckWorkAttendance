package com.example.humencheckworkattendance.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.example.humencheckworkattendance.R;
import com.example.humencheckworkattendance.adapter.MonthSocialSecurityAdapter;
import com.example.humencheckworkattendance.base.BaseActivity;
import com.example.humencheckworkattendance.base.BaseHttpResult;
import com.example.humencheckworkattendance.bean.LoginBean;
import com.example.humencheckworkattendance.bean.MonthSocialSecurityBean;
import com.example.humencheckworkattendance.contact.MonthSocialSecurityContact;
import com.example.humencheckworkattendance.presenter.MonthSocialSecurityPresenter;
import com.example.humencheckworkattendance.widget.PullToRefreshView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import cn.cloudwalk.libproject.util.LogUtil;

/**
 * 本月社保上传页面
 * Created by Administrator on 2017-11-28.
 */

public class MonthSocialSecurityActivity extends BaseActivity<MonthSocialSecurityPresenter> implements MonthSocialSecurityContact.MonthSocialSecurityView, PullToRefreshView.OnFooterRefreshListener {

    @BindView(R.id.listView_month_social_security)
    ListView mList;
    @BindView(R.id.mPullToRefreshView)
    PullToRefreshView mPullToRefreshView;
    Intent mIntent;

    ArrayList<MonthSocialSecurityBean.MothListBean> mDataList = new ArrayList<>();
    MonthSocialSecurityAdapter mAdapter = new MonthSocialSecurityAdapter(this, mDataList);
    int mPage = 1;
    int mSumPage;
    LoginBean mLoginBean;

    int mPosition = 0;

    @Override
    protected MonthSocialSecurityPresenter loadPresenter() {
        return new MonthSocialSecurityPresenter();
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        mList.setAdapter(mAdapter);
        mAdapter.setOnItemDeleteClickListener(new MonthSocialSecurityAdapter.onItemChildListener() {
            @Override
            public void onUpdateClick(int position) {
                Intent intent = new Intent(MonthSocialSecurityActivity.this, TakeCameraActivity.class);
                mPosition = position;
                startActivityForResult(intent,1);
            }
        });
        showProgressDialogWithText("查询数据中，请稍候...");
        mPresenter.getMonthSocialSecurityList(mLoginBean.getProjId() + "", mPage + "");
        LogUtil.logE("登录的id： " + mLoginBean.getProjId());
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initView() {
        mPullToRefreshView.setOnFooterRefreshListener(this);
        mIntent = getIntent();
        mLoginBean = (LoginBean) mIntent.getSerializableExtra("userMessage");
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_month_social_security;
    }

    @Override
    protected void otherViewClick(View view) {

    }

    @Override
    public void getMonthSocialSecurityListSuccess(MonthSocialSecurityBean monthSocialSecurityBean) {
        dismissProgressDialog();
        if (monthSocialSecurityBean.getMothList().size() == 0) {
            toast("暂无数据！");
        } else {
            toast("数据加载成功！");
        }

        mDataList.addAll(monthSocialSecurityBean.getMothList());
        mAdapter.notifyDataSetChanged();
        mSumPage = (monthSocialSecurityBean.getTotal() + 15 - 1) / 15;
        if (mPage <= mSumPage) {
            mPage++;
        }
        mPullToRefreshView.onFooterRefreshComplete();
    }

    @Override
    public void getMonthSocialSecurityListFail(String failMsg) {
        dismissProgressDialog();
        toast(failMsg);
    }

    @Override
    public void addSocialSecuritySuccess(String empty) {
        dismissProgressDialog();
        mDataList.get(mPosition).setStatus(1);//状态修改成已上传：0未上传  1已上传
        mAdapter.notifyDataSetChanged();
        toast("上传成功");
    }

    @Override
    public void addSocialSecurityFail(String failMsg) {
        dismissProgressDialog();
        toast(failMsg);
    }

    @Override
    public void onFooterRefresh(PullToRefreshView view) {
        mPullToRefreshView.postDelayed(new Runnable() {

            public void run() {
                if (mPage > mSumPage) {
                    toast("已经是最后一页了");
                    mPullToRefreshView.onFooterRefreshComplete();
                } else {
                    mPresenter.getMonthSocialSecurityList(mLoginBean.getProjId() + "", mPage + "");
                }

            }
        }, 1000);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch (resultCode) {
            case 1:
                //照相完且上传照片后回调
                String imgUrl = data.getStringExtra("url");
//                LogUtil.logE("本月社保上传页面，上传的图片Url" + imgUrl);
                showProgressDialogWithText("上传数据中，请稍候...");
                mPresenter.addSocialSecurity(mLoginBean.getProjId(),mDataList.get(mPosition).getStreamId(),imgUrl);
                break;
            case 2:
                break;
            default:
                break;
        }
    }

    @OnClick({R.id.imageView_Back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imageView_Back:
                finish();
                break;

        }
    }
}
