package com.example.humencheckworkattendance.ui.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.humencheckworkattendance.R;
import com.example.humencheckworkattendance.adapter.SubmitHumenMessageAdapter;
import com.example.humencheckworkattendance.base.BaseActivity;
import com.example.humencheckworkattendance.bean.LoginBean;
import com.example.humencheckworkattendance.bean.SubmitHumenBean;
import com.example.humencheckworkattendance.contact.SubmitHumenMessageContact;
import com.example.humencheckworkattendance.presenter.SubmitHumenMessagePresenter;
import com.example.humencheckworkattendance.widget.PullToRefreshView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

public class SubmitHumenMessageActivity  extends BaseActivity<SubmitHumenMessagePresenter> implements SubmitHumenMessageContact.SubmitHumenMessageView,PullToRefreshView.OnFooterRefreshListener {
    @BindView(R.id.listView_Submit_Humen_Message)
    ListView listView;
    @BindView(R.id.mPullToRefreshView)
    PullToRefreshView mPullToRefreshView;
    ArrayList<SubmitHumenBean.MothListBean> dataList = new ArrayList<SubmitHumenBean.MothListBean>();
    SubmitHumenMessageAdapter adapter = new SubmitHumenMessageAdapter(this, dataList);
    int page = 1;
    int sumPage ;
    Intent intent;
    LoginBean loginBean;
    int dqPosition;
    @BindView(R.id.imageView_Back)
    ImageView imageViewBack;
    @Override
    protected SubmitHumenMessagePresenter loadPresenter() {
        return new SubmitHumenMessagePresenter();
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        listView.setAdapter(adapter);
        showProgressDialogWithText("查询数据中，请稍候...");
        mPresenter.getStreamMemberList(loginBean.getMemberId()+"",page+"",15+"");
        //ListView item的点击事件
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //Toast.makeText(SubmitHumenMessageActivity.this, "Click item" + i, Toast.LENGTH_SHORT).show();
            }
        });
        //ListView item 中的删除按钮的点击事件
        adapter.setOnItemDeleteClickListener(new SubmitHumenMessageAdapter.onItemChildListener() {
            @Override
            public void onDeleteClick(final int i) {
                AlertDialog.Builder builder = new AlertDialog.Builder(SubmitHumenMessageActivity.this);
                // 设置提示框的标题
                builder.setTitle("提示").
                        // 设置提示框的图标
                               // setIcon(R.drawable.icon).
                        // 设置要显示的信息
                                setMessage("是否确认删除？").
                        // 设置确定按钮
                                setPositiveButton("确定", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                showProgressDialogWithText("正在删除中，请稍候...");
                                dqPosition = i;
                                mPresenter.checkDeleteStreamMember(dataList.get(i).getStreamId()+"");
                            }
                        }).

                        // 设置取消按钮,null是什么都不做，并关闭对话框
                                setNegativeButton("取消", null);

                // 生产对话框
                AlertDialog alertDialog = builder.create();
                alertDialog.setCanceledOnTouchOutside(false);
                // 显示对话框
                alertDialog.show();
                alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.rgb(88,190,252));
            }

            @Override
            public void onSubmitClick(int i) {
                showProgressDialogWithText("提交审核中，请稍候...");
                dqPosition = i;
                mPresenter.subitStreamMember(dataList.get(i).getStreamId()+"");
                //Toast.makeText(SubmitHumenMessageActivity.this, "提交的item" + i, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onUpdateClick(int i) {
                dqPosition = i;
                intent.putExtra("isUpdate",true);
                intent.putExtra("updateHumenId",dataList.get(i).getIdCard()+"");
                intent.setClass(SubmitHumenMessageActivity.this,InsertHumenManagerActivity.class);
                startActivityForResult(intent,0X111);
               // Toast.makeText(SubmitHumenMessageActivity.this, "修改的item" + i, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onRollbackClick(int i) {
                showProgressDialogWithText("撤销审核中，请稍候...");
                dqPosition = i;
                mPresenter.rollBackMember(dataList.get(i).getStreamId()+"");
                //Toast.makeText(SubmitHumenMessageActivity.this, "撤销的item" + i, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onErrorClick(int i) {
                showProgressDialogWithText("查询中，请稍候...");
                dqPosition = i;
                mPresenter.getErrorStreamMember(dataList.get(i).getStreamId()+"");
            }
        });
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
        return R.layout.activity_submit_humen_message;
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
                    mPresenter.getStreamMemberList(loginBean.getMemberId()+"",page+"",10+"");
                }

            }
        }, 1000);
    }

    @Override
    public void getStreamMemberListSuccess(SubmitHumenBean submitHumenBean) {
        dismissProgressDialog();
        if(submitHumenBean.getMothList().size() == 0){
            toast("暂无数据！");
        }else{
            toast("数据加载成功！");
        }

        dataList.addAll(submitHumenBean.getMothList());
        adapter.notifyDataSetChanged();
        sumPage = (submitHumenBean.getTotal()  +  15  - 1) / 15;
        if(page<=sumPage){
            page++;
        }
        mPullToRefreshView.onFooterRefreshComplete();
    }

    @Override
    public void getStreamMemberListFail(String failMsg) {

        dismissProgressDialog();
        toast(failMsg);
    }

    @Override
    public void subitStreamMemberSuccess(String s) {
        dismissProgressDialog();
        dataList.get(dqPosition).setStatus(1);
        adapter.notifyDataSetChanged();
        toast("提交审核成功！");
    }

    @Override
    public void subitStreamMemberFail(String failMsg) {
        dismissProgressDialog();
        toast(failMsg);
    }

    @Override
    public void rollBackMemberSuccess(String s) {
        dismissProgressDialog();
        dataList.get(dqPosition).setStatus(0);
        adapter.notifyDataSetChanged();
        toast("撤销审核成功！");
    }

    @Override
    public void rollBackMemberFail(String failMsg) {
        dismissProgressDialog();
        toast(failMsg);
    }

    @Override
    public void checkDeleteStreamMemberSuccess(String s) {
        mPresenter.deleteStreamMember(dataList.get(dqPosition).getStreamId()+"");
    }

    @Override
    public void checkDeleteStreamMemberFail(String failMsg) {
        dismissProgressDialog();
        toast(failMsg);
    }

    @Override
    public void deleteStreamMemberSuccess(String s) {
        dismissProgressDialog();
        dataList.remove(dqPosition);
        adapter.notifyDataSetChanged();
        toast("删除成功！");
    }

    @Override
    public void deleteStreamMemberFail(String failMsg) {
        dismissProgressDialog();
        toast(failMsg);
    }

    @Override
    public void getErrorStreamMemberSuccess(String s) {
        dismissProgressDialog();
        AlertDialog.Builder builder = new AlertDialog.Builder(SubmitHumenMessageActivity.this);
        // 设置提示框的标题
        builder.setTitle("审核失败原因").
                // 设置提示框的图标
                // setIcon(R.drawable.icon).
                // 设置要显示的信息
                        setMessage(s).
                // 设置确定按钮
                        setPositiveButton("确定",null);

        // 生产对话框
        AlertDialog alertDialog = builder.create();
        alertDialog.setCanceledOnTouchOutside(false);
        // 显示对话框
        alertDialog.show();
        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.rgb(88,190,252));
    }

    @Override
    public void getErrorStreamMemberFail(String failMsg) {
        dismissProgressDialog();
        toast(failMsg);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
      //  Log.e("requestCode",resultCode+"11111");
        switch (resultCode) {
            case 1:
                dataList.get(dqPosition).setStatus(Integer.parseInt(data.getStringExtra("statusType")));
                dataList.get(dqPosition).setStreamName(data.getStringExtra("userName"));
                dataList.get(dqPosition).setIdCard(data.getStringExtra("idNum"));
                adapter.notifyDataSetChanged();
                break;
            default:
                break;
        }
    }
}
