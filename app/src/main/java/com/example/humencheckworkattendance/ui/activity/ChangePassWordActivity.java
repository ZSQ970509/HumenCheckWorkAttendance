package com.example.humencheckworkattendance.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.humencheckworkattendance.R;
import com.example.humencheckworkattendance.base.BaseActivity;
import com.example.humencheckworkattendance.bean.LoginBean;
import com.example.humencheckworkattendance.contact.ChangePassWordContact;
import com.example.humencheckworkattendance.presenter.ChangePassWordPresenter;

import butterknife.BindView;
import butterknife.OnClick;

public class ChangePassWordActivity extends BaseActivity<ChangePassWordPresenter> implements ChangePassWordContact.ChangePassWordView{

    @BindView(R.id.editText_Change_OldPassWord)
    EditText editTextChangeOldPassWord;
    @BindView(R.id.editText_Change_NewPassWord)
    EditText editTextChangeNewPassWord;
    @BindView(R.id.editText_Change_NewPassWord_Again)
    EditText editTextChangeNewPassWordAgain;
    @BindView(R.id.imageView_Change_Submit)
    ImageView imageViewChangeSubmit;
    @BindView(R.id.imageView_Back)
    ImageView imageViewBack;
    Intent intent;
    LoginBean loginBean;
    @Override
    protected ChangePassWordPresenter loadPresenter() {
        return new ChangePassWordPresenter();
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

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_change_pass_word;
    }
    @OnClick({R.id.imageView_Change_Submit,R.id.imageView_Back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imageView_Change_Submit:
                if(TextUtils.isEmpty(editTextChangeOldPassWord.getText().toString())){
                    toast("未填写旧密码，请先填写旧密码！");
                    return;
                }
                if(TextUtils.isEmpty(editTextChangeNewPassWord.getText().toString())){
                    toast("未填写新密码，请先填写新密码！");
                    return;
                }
                if(TextUtils.isEmpty(editTextChangeNewPassWordAgain.getText().toString())){
                    toast("未填写确认密码，请先填写确认密码！");
                    return;
                }
                if(!editTextChangeNewPassWord.getText().toString().equals(editTextChangeNewPassWordAgain.getText().toString())){
                    toast("新密码两次输入的不同，请检查重试！");
                    return;
                }
                if(editTextChangeOldPassWord.getText().toString().equals(editTextChangeNewPassWord.getText().toString())){
                    toast("新密码与旧密码相同，请检查重试！");
                    return;
                }
                showProgressDialogWithText("正在修改中，请稍候...");
                mPresenter.ChangePassWord(editTextChangeOldPassWord.getText().toString(),editTextChangeNewPassWord.getText().toString(),loginBean.getUserAccount());
                break;
            case R.id.imageView_Back:
                finish();
                break;

            default:
                break;
        }
    }
    @Override
    protected void otherViewClick(View view) {

    }

    @Override
    public void ChangePassWordSuccess(String s) {
        dismissProgressDialog();
        toast("修改密码成功");
        setResult(1, intent);
        finish();

    }

    @Override
    public void ChangePassWordFail(String failMsg) {
        dismissProgressDialog();
        toast(failMsg);
    }


}
