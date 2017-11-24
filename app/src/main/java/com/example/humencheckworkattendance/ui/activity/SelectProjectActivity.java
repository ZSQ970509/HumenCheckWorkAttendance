package com.example.humencheckworkattendance.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.humencheckworkattendance.R;
import com.example.humencheckworkattendance.adapter.SelectProjectAdapter;
import com.example.humencheckworkattendance.base.BaseActivity;
import com.example.humencheckworkattendance.bean.LoginBean;
import com.example.humencheckworkattendance.bean.ProjectBean;
import com.example.humencheckworkattendance.contact.SelectProjectContact;
import com.example.humencheckworkattendance.presenter.SelectProjectPresenter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

public class SelectProjectActivity extends BaseActivity<SelectProjectPresenter> implements SelectProjectContact.SelectProjectView {
    @BindView(R.id.imageView_Back)
    ImageView imageViewBack;
    @BindView(R.id.select_project_editText)
    EditText selectProjectEditText;
    @BindView(R.id.select_project_ImageView)
    ImageView selectProjectImageView;
    @BindView(R.id.select_project_ListView)
    ListView selectProjectListView;
    Intent intent;
    LoginBean loginBean;
    ArrayList<ProjectBean.ListBean> projectList = new ArrayList<ProjectBean.ListBean>();
    @Override
    public void SeachProjectSuccess(ProjectBean projectBean) {
        dismissProgressDialog();
        if(projectList.size() == 0){
            projectList.clear();
        }
        projectList = projectBean.getList();
        selectProjectListView.setAdapter(new SelectProjectAdapter(this,projectList));
    }

    @Override
    public void SeachProjectFail(String s) {
        dismissProgressDialog();
        toast("查询失败，网络异常请稍候！");
    }

    @Override
    protected SelectProjectPresenter loadPresenter() {
        return new SelectProjectPresenter();
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        intent = getIntent();
        loginBean = (LoginBean)intent.getSerializableExtra("userMessage");
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initView() {
        selectProjectListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                intent.setClass(SelectProjectActivity.this,InsertHumenActivity.class);
                intent.putExtra("isUpdate",false);
                intent.putExtra("selectProjectId",projectList.get(position).getId()+"");
                startActivityForResult(intent,0X111);
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_select_project;
    }

    @Override
    protected void otherViewClick(View view) {

    }
    @OnClick({R.id.select_project_ImageView,R.id.imageView_Back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.select_project_ImageView:
                showProgressDialogWithText("正在查询项目，请稍候！");
                mPresenter.seachProject(loginBean.getMemberId()+"",loginBean.getUserTypeId(),selectProjectEditText.getText().toString());
                break;
            case R.id.imageView_Back:
                finish();
                break;
            default:
                break;
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
       // Log.e("requestCode",resultCode+"11111");
        switch (resultCode) {
            case 1:
                this.finish();
                break;
            default:
                break;
        }
    }
}
