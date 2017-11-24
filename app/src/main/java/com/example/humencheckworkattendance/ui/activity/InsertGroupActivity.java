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
import com.example.humencheckworkattendance.contact.InsertGroupContact;
import com.example.humencheckworkattendance.presenter.InsertGroupPresenter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

public class InsertGroupActivity extends BaseActivity<InsertGroupPresenter> implements InsertGroupContact.InsertGroupView {
    @BindView(R.id.editText_Insert_Group_GroupName)
    EditText editTextInsertGroupGroupName;
    @BindView(R.id.spinner_Insert_Group)
    TextView spinnerInsertGroup;
    @BindView(R.id.imageView_InsertHumenAddGroup_Dialog)
    ImageView imageViewInsertHumenAddGroupDialog;
    @BindView(R.id.imageView_InsertHumenAddGroup)
    ImageView imageViewInsertHumenAddGroup;
    @BindView(R.id.imageView_Insert_Group_Submit)
    ImageView imageViewInsertGroupSubmit;
    @BindView(R.id.imageView_Back)
    ImageView imageView_Back;
    ArrayList<GrouperBean.ListBean> grouperBeanArrayList = new ArrayList<GrouperBean.ListBean>();
    Intent intent;
    LoginBean loginBean;
    ListView user_multiChoiceListView;
    String mUsers="";
    @Override
    protected InsertGroupPresenter loadPresenter() {
        return new InsertGroupPresenter();
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        intent = getIntent();
        loginBean = (LoginBean)intent.getSerializableExtra("userMessage");
        showProgressDialogWithText("正在加载，请稍候...");
        mPresenter.getGrouper(loginBean.getProjId());
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initView() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_insert_group;
    }

    @Override
    protected void otherViewClick(View view) {

    }
    @OnClick({R.id.imageView_InsertHumenAddGroup_Dialog,R.id.imageView_InsertHumenAddGroup,R.id.imageView_Insert_Group_Submit,R.id.imageView_Back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imageView_InsertHumenAddGroup_Dialog:
                final String[] user_items = new String[grouperBeanArrayList.size()];
                final boolean[] user_checkedItems = new boolean[grouperBeanArrayList.size()];
                for (int i = 0; i < grouperBeanArrayList.size(); i++) {
                    user_items[i] = grouperBeanArrayList.get(i).getName();
                    user_checkedItems[i] = false;
                }
                AlertDialog.Builder builder = new AlertDialog.Builder(InsertGroupActivity.this);
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
                                }
                                msg += grouperBeanArrayList.get(i).getName();
                                mUsers += grouperBeanArrayList.get(i).getName();
                            }
                        }
                        spinnerInsertGroup.setText(msg);
                    }
                });
                AlertDialog dialog = builder.create();
                user_multiChoiceListView = dialog.getListView();
                dialog.show();
                break;
            case R.id.imageView_Insert_Group_Submit:
                if(TextUtils.isEmpty(editTextInsertGroupGroupName.getText().toString())){
                    toast("未填写班组名称，请先填写班组名称！");
                    return;
                }
                if(TextUtils.isEmpty(spinnerInsertGroup.getText().toString())){
                    toast("未选择班组长，请先选择班组长！");
                    return;
                }
                showProgressDialogWithText("正在添加中，请稍后...");
                mPresenter.saveGroup(editTextInsertGroupGroupName.getText().toString(),spinnerInsertGroup.getText().toString(),loginBean.getProjId());
                break;
            case R.id.imageView_InsertHumenAddGroup:
                intent.setClass(InsertGroupActivity.this,InsertGrouperActivity.class);
                startActivityForResult(intent,0X111);
                break;
            case R.id.imageView_Back:
                finish();
                break;
            default:
                break;
        }
    }

    @Override
    public void getGrouperSuccess(GrouperBean GrouperBeanList) {
        dismissProgressDialog();
        grouperBeanArrayList = GrouperBeanList.getList();
    }

    @Override
    public void getGrouperFail(String failMsg) {
        dismissProgressDialog();
        toast(failMsg);
    }

    @Override
    public void saveGroupSuccess(String str) {
        dismissProgressDialog();
        this.setResult(3, intent);
        toast("班组添加成功！");
        this.finish();
    }

    @Override
    public void saveGroupFail(String failMsg) {
        dismissProgressDialog();
        toast(failMsg);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (resultCode) {
            case 1:
                showProgressDialogWithText("正在加载，请稍后...");
                mPresenter.getGrouper(loginBean.getProjId());
                break;
            default:
                break;
        }
    }
}
