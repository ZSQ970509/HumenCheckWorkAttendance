package com.example.humencheckworkattendance.ui.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.humencheckworkattendance.R;
import com.example.humencheckworkattendance.base.BaseActivity;
import com.example.humencheckworkattendance.bean.GrouperBean;
import com.example.humencheckworkattendance.bean.LoginBean;
import com.example.humencheckworkattendance.contact.InsertGrouperContact;
import com.example.humencheckworkattendance.presenter.InsertGrouperPresenter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

public class InsertGrouperActivity extends BaseActivity<InsertGrouperPresenter> implements InsertGrouperContact.InsertGrouperView {
    @BindView(R.id.editText_Insert_Grouper_IdNum)
    EditText editTextInsertGrouperIdNum;
    @BindView(R.id.editText_Insert_Grouper_UserName)
    EditText editTextInsertGrouperUserName;
    @BindView(R.id.editText_Insert_Grouper_Phone)
    EditText editText_InsertGrouperPhone;
    @BindView(R.id.spinner_Insert_Grouper)
    TextView spinnerInsertGrouper;
    @BindView(R.id.imageView_InsertHumenAddGrouper_Dialog)
    ImageView imageViewInsertHumenAddGrouperDialog;
    @BindView(R.id.imageView_Insert_Grouper_Submit)
    ImageView imageViewInsertGrouperSubmit;
    @BindView(R.id.imageView_Back)
    ImageView imageViewBack;
    Intent intent;
    LoginBean loginBean;
    ArrayList<GrouperBean.ListBean> grouperBeanArrayList = new ArrayList<GrouperBean.ListBean>();
    String mUsers="";
    String mIds = "";
    ListView user_multiChoiceListView;
    @Override
    protected InsertGrouperPresenter loadPresenter() {
        return new InsertGrouperPresenter();
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        intent = getIntent();
        loginBean = (LoginBean)intent.getSerializableExtra("userMessage");
        showProgressDialogWithText("正在加载，请稍后...");
        mPresenter.getGroup(loginBean.getProjId());
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initView() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_insert_grouper;
    }

    @Override
    protected void otherViewClick(View view) {

    }
    @OnClick({R.id.imageView_InsertHumenAddGrouper_Dialog,R.id.imageView_Insert_Grouper_Submit,R.id.imageView_Back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imageView_InsertHumenAddGrouper_Dialog:
                final String[] user_items = new String[grouperBeanArrayList.size()];
                final boolean[] user_checkedItems = new boolean[grouperBeanArrayList.size()];
                for (int i = 0; i < grouperBeanArrayList.size(); i++) {
                    user_items[i] = grouperBeanArrayList.get(i).getName();
                    user_checkedItems[i] = false;
                }
                AlertDialog.Builder builder = new AlertDialog.Builder(InsertGrouperActivity.this);
                builder.setMultiChoiceItems(user_items, user_checkedItems, null);
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        String msg = "";
                        mUsers = "";
                        for (int i = 0; i < user_items.length; i++) {
                            if (user_multiChoiceListView.getCheckedItemPositions().get(i)) {
                                if (!TextUtils.isEmpty(msg)) {
                                    msg += ",";
                                    mUsers += ",";
                                    mIds += ",";
                                }
                                msg += grouperBeanArrayList.get(i).getName();
                                mUsers += grouperBeanArrayList.get(i).getName();
                                mIds += grouperBeanArrayList.get(i).getId();
                            }
                        }
                        spinnerInsertGrouper.setText(msg);
                    }
                });
                AlertDialog dialog = builder.create();
                user_multiChoiceListView = dialog.getListView();
                dialog.show();
                break;
            case R.id.imageView_Insert_Grouper_Submit:
                if(TextUtils.isEmpty(editTextInsertGrouperIdNum.getText().toString())){
                    toast("未填写身份证，请先填写身份证！");
                    return;
                }
                if(TextUtils.isEmpty(editTextInsertGrouperUserName.getText().toString())){
                    toast("未填写班组长姓名，请先填写班组长姓名！");
                    return;
                }
                if(TextUtils.isEmpty(editText_InsertGrouperPhone.getText().toString())){
                    toast("未填写手机号，请先填写手机号！");
                    return;
                }
                showProgressDialogWithText("正在添加中，请稍后...");
               // Log.e("log",mIds);
                mPresenter.saveGrouper(loginBean.getProjId(),loginBean.getUserName(),mIds,spinnerInsertGrouper.getText().toString(),editTextInsertGrouperUserName.getText().toString(),editText_InsertGrouperPhone.getText().toString(),editTextInsertGrouperIdNum.getText().toString());
                break;
            case R.id.imageView_Back:
                finish();
                break;
            default:
                break;
        }
    }
    @Override
    public void getGroupSuccess(GrouperBean GrouperBeanList) {
        dismissProgressDialog();
        grouperBeanArrayList = GrouperBeanList.getList();
    }

    @Override
    public void getGroupFail(String failMsg) {
        dismissProgressDialog();
        toast(failMsg);
    }

    @Override
    public void saveGrouperSuccess(String str) {
        dismissProgressDialog();
        this.setResult(1, intent);
        toast("人员添加成功！");
        this.finish();
    }

    @Override
    public void saveGrouperFail(String failMsg) {
        dismissProgressDialog();
        toast(failMsg);
    }
}
