package com.example.humencheckworkattendance.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.humencheckworkattendance.R;
import com.example.humencheckworkattendance.adapter.PlayCardTodayAdapter;
import com.example.humencheckworkattendance.base.BaseActivity;
import com.example.humencheckworkattendance.bean.LoginBean;
import com.example.humencheckworkattendance.bean.PlayCardTodayBean;
import com.example.humencheckworkattendance.contact.PlayCardTodayContact;
import com.example.humencheckworkattendance.presenter.PlayCardTodayPresenter;
import com.example.humencheckworkattendance.widget.PullToRefreshView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

public class PlayCardTodayActivity extends BaseActivity<PlayCardTodayPresenter> implements PlayCardTodayContact.PlayCardTodayView,PullToRefreshView.OnFooterRefreshListener {
    @BindView(R.id.listView_Play_Card_Today)
    ListView listView;
    @BindView(R.id.mPullToRefreshView)
    PullToRefreshView mPullToRefreshView;
    @BindView(R.id.imageView_Back)
    ImageView imageViewBack;
    Intent intent;
    LoginBean loginBean;
    int page = 1;
    int sumPage ;
    int dqPosition;

    ArrayList<PlayCardTodayBean.MothListBean> dataList = new ArrayList<PlayCardTodayBean.MothListBean>();
    PlayCardTodayAdapter adapter = new PlayCardTodayAdapter(this, dataList);
    @Override
    protected PlayCardTodayPresenter loadPresenter() {
        return new PlayCardTodayPresenter();
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        listView.setAdapter(adapter);
        showProgressDialogWithText("查询数据中，请稍候...");
        mPresenter.getDayAttendanceList(loginBean.getProjId()+"",page+"");
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initView() {
        mPullToRefreshView.setOnFooterRefreshListener(this);
        intent = getIntent();
        loginBean = (LoginBean)intent.getSerializableExtra("userMessage");
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_play_card_today;
    }

    @Override
    protected void otherViewClick(View view) {

    }
    @OnClick({R.id.imageView_Back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
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
                    mPresenter.getDayAttendanceList(loginBean.getProjId()+"",page+"");
                }

            }
        }, 1000);
    }

    @Override
    public void getDayAttendanceListSuccess(PlayCardTodayBean playCardTodayBean) {
        dismissProgressDialog();
        if(playCardTodayBean.getMothList().size() == 0){
            toast("暂无数据！");
        }else{
            toast("数据加载成功！");
        }

        dataList.addAll(playCardTodayBean.getMothList());
        adapter.notifyDataSetChanged();
        sumPage = (playCardTodayBean.getTotal()  +  15  - 1) / 15;
        if(page<=sumPage){
            page++;
        }
        mPullToRefreshView.onFooterRefreshComplete();
    }

    @Override
    public void getDayAttendanceListFail(String failMsg) {

        dismissProgressDialog();
        toast(failMsg);
    }
}
