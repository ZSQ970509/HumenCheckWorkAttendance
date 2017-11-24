package com.example.humencheckworkattendance.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.humencheckworkattendance.R;
import com.example.humencheckworkattendance.adapter.HistroyAdapter;
import com.example.humencheckworkattendance.base.BaseActivity;
import com.example.humencheckworkattendance.bean.HistroyBean;
import com.example.humencheckworkattendance.bean.LoginBean;
import com.example.humencheckworkattendance.contact.SignInDetailsContact;
import com.example.humencheckworkattendance.presenter.SignInDetailsPresenter;
import com.example.humencheckworkattendance.widget.PullToRefreshView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import butterknife.BindView;
import butterknife.OnClick;

public class SignInDetailsActivity extends BaseActivity<SignInDetailsPresenter> implements SignInDetailsContact.SignInDetailsView,PullToRefreshView.OnFooterRefreshListener {
    @BindView(R.id.listView_Histroy)
    ListView listView;
    @BindView(R.id.mPullToRefreshView)
    PullToRefreshView mPullToRefreshView;
    @BindView(R.id.imageView_Date_Lift)
    ImageView imageViewDateLift;
    @BindView(R.id.imageView_Date_Right)
    ImageView imageViewDateRight;
    @BindView(R.id.textview_Date)
    TextView textViewDate;
    @BindView(R.id.imageView_Back)
    ImageView imageViewBack;
    LoginBean loginBean;
    Intent intent;
    int year ;
    int month ;
    int page = 1;
    int sumPage ;
    ArrayList<HistroyBean.MothListBean> dataList = new ArrayList<HistroyBean.MothListBean>();
    HistroyAdapter adapter = new HistroyAdapter(SignInDetailsActivity.this,dataList);
    @Override
    protected SignInDetailsPresenter loadPresenter() {
        return new SignInDetailsPresenter();
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        listView.setAdapter(adapter);
        showProgressDialogWithText("查询数据中，请稍候...");
        mPresenter.getHistroy(year+"-"+month,page+"",loginBean.getMemberId()+"");
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initView() {
        mPullToRefreshView.setOnFooterRefreshListener(this);
        Date curDate =  new Date(System.currentTimeMillis());
        SimpleDateFormat  formatter = new SimpleDateFormat("yyyy");
        year = Integer.parseInt(formatter.format(curDate));
        formatter = new SimpleDateFormat("MM");
        month   =  Integer.parseInt(formatter.format(curDate));
        textViewDate.setText(year+"年"+month+"月");
        intent = getIntent();
        loginBean = (LoginBean)intent.getSerializableExtra("userMessage");
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_sign_in_details;
    }

    @Override
    protected void otherViewClick(View view) {

    }
    @OnClick({R.id.imageView_Date_Lift,R.id.imageView_Date_Right,R.id.textview_Date,R.id.imageView_Back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imageView_Date_Lift:
                if(month == 1){
                    month = 12;
                    year = year-1;
                    textViewDate.setText(year+"年"+month+"月");

                }else {
                    month = month-1;
                    textViewDate.setText(year+"年"+month+"月");
                }
                dataList.clear();
                page = 1;
                showProgressDialogWithText("查询数据中，请稍候...");
                mPresenter.getHistroy(year+"-"+month,page+"",loginBean.getMemberId()+"");
                break;
            case R.id.imageView_Date_Right:
                if(month == 12){
                    month = 1;
                    year = year+1;
                    textViewDate.setText(year+"年"+month+"月");
                }else {
                    month = month+1;
                    textViewDate.setText(year+"年"+month+"月");
                }
                dataList.clear();
                page = 1;
                showProgressDialogWithText("查询数据中，请稍候...");
                mPresenter.getHistroy(year+"-"+month,page+"",loginBean.getMemberId()+"");
                break;
            case R.id.imageView_Back:
                finish();
                break;
        }
    }

    @Override
    public void onFooterRefresh(PullToRefreshView view) {
        mPullToRefreshView.postDelayed(new Runnable() {

            public void run() {
                if(page > sumPage){
                    toast("已经是最后一页了");
                    mPullToRefreshView.onFooterRefreshComplete();
                }else{
                    mPresenter.getHistroy(year+"-"+month,page+"",loginBean.getMemberId()+"");
                }

            }
        }, 1000);

    }


    @Override
    public void getHistroySuccess(HistroyBean histroyBean) {
        dismissProgressDialog();
        if(histroyBean.getMothList().size() == 0){
            toast("暂无数据！");
        }else{
            toast("数据加载成功！");
        }

        dataList.addAll(histroyBean.getMothList());
        adapter.notifyDataSetChanged();
        sumPage = (histroyBean.getTotal()  +  10  - 1) / 10;
        if(page<=sumPage){
            page++;
        }
        mPullToRefreshView.onFooterRefreshComplete();
    }

    @Override
    public void getHistroyFail(String failMsg) {
        toast(failMsg);
    }
}
