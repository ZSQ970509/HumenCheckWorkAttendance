package com.example.humencheckworkattendance.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.humencheckworkattendance.R;
import com.example.humencheckworkattendance.adapter.PopListViewAdapter;
import com.example.humencheckworkattendance.adapter.PopListViewSecondAdapter;
import com.example.humencheckworkattendance.base.BaseActivity;
import com.example.humencheckworkattendance.bean.EmtpBean;
import com.example.humencheckworkattendance.bean.EmtpRolesBean;
import com.example.humencheckworkattendance.bean.LoginBean;
import com.example.humencheckworkattendance.bean.TeamListBean;
import com.example.humencheckworkattendance.bean.UserBean;
import com.example.humencheckworkattendance.contact.InsertHumenContact;
import com.example.humencheckworkattendance.presenter.InserHumenManagerPresenter;
import com.example.humencheckworkattendance.utils.RegexUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

public class InsertHumenManagerActivity extends BaseActivity<InserHumenManagerPresenter> implements InsertHumenContact.InsertHumenView {
    @BindView(R.id.radioGroup_Insert_Sex)
    RadioGroup radioGroupInsertSex;
    @BindView( R.id.radioButton_Men)
    RadioButton radioButtonMen;
    @BindView( R.id.radioButton_Women)
    RadioButton radioButtonWomen;
    @BindView(R.id.editText_Insert_UserName)
    EditText editTextInsertUserName;
    @BindView(R.id.editText_Insert_IdNum)
    EditText editTextInsertIdNum;
    @BindView(R.id.editText_Insert_ValidateCode)
    EditText editTextInsertValidateCode;
    @BindView(R.id.editText_Insert_Phone)
    EditText editTextInsertPhone;
    @BindView(R.id.imageView_Insert_Submit)
    ImageView imageViewInsertSubmit;
    @BindView(R.id.imageView_Insert_Save)
    ImageView imageViewInsertSave;
    @BindView(R.id.spinner_InsertHumen)
    TextView spinnerInsertHumen;
    @BindView(R.id.spinner_InsertHumen_EmtpRoles)
    TextView spinnerInsertHumenEmtpRoles;
    @BindView(R.id.spinner_StreamTitle)
    TextView spinnerStreamTitle;
    @BindView(R.id.imageView_Back)
    ImageView imageViewBack;
    @BindView(R.id.linearLayout_Emtp)
    LinearLayout linearLayoutEmtp;
    @BindView(R.id.linearLayout_EmtpRoles)
    LinearLayout linearLayoutEmtpRoles;
    @BindView(R.id. linearLayout_LinearLayout_EmtpRoles)
    LinearLayout linearLayoutLinearLayoutEmtpRoles;
    @BindView(R.id.linearLayout_StreamTitle)
    LinearLayout linearLayoutStreamTitle;
    @BindView(R.id.linearLayout_EmtpRoles_Line)
    LinearLayout linearLayoutEmtpRolesLine;

    Intent intent;
    LoginBean loginBean;
    String sex="1";
    PopupWindow mPopWindow;
    ArrayList<EmtpBean.ListBean>  EmtpBeanList = new ArrayList<EmtpBean.ListBean> ();
    ArrayList<EmtpBean.ListBean>  DictByTypeList = new ArrayList<EmtpBean.ListBean> ();
    ArrayList<EmtpRolesBean.ListBean>  EmtpRolesBeanList = new ArrayList<EmtpRolesBean.ListBean> ();
    String EmtpId="";
    String EmtpRolesId="";
    String streamTitle="";
    UserBean userBean ;
    boolean isExistHumen = false;
    String statusType;
    String IdNum;
    String Moblie;
    String selectProject = "";
    String idNumImgSrc = "";
    String headImgSrc = "";
    String idNumBackImgSrc="";
    @Override
    protected InserHumenManagerPresenter loadPresenter() {
        return new InserHumenManagerPresenter();
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        loginBean = (LoginBean)intent.getSerializableExtra("userMessage");
        showProgressDialogWithText("正在加载，请稍候...");
        selectProject = intent.getStringExtra("selectProjectId");
        Log.e("selectProject",selectProject+"");

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initView() {
        intent = getIntent();
        mPresenter.getEmtp();
        Log.e("isUpdate",!(intent.getBooleanExtra(("isUpdate"),false))+"");
        if(!(intent.getBooleanExtra(("isUpdate"),false))) {
            Log.e("isUpdate",11111+"");
            editTextInsertIdNum.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (hasFocus) {
                        Log.e("isUpdate",11111+"");
                        // 此处为得到焦点时的处理内容
                    } else {
                        Log.e("isUpdate",22222+"");
                        showProgressDialogWithText("正在加载，请稍候...");
                        //IdNum = editTextInsertIdNum.getText().toString();
                        mPresenter.getStreamMemberByIdCard(editTextInsertIdNum.getText().toString());
                        // 此处为失去焦点时的处理内容
                    }
                }
            });
        }else{
            showProgressDialogWithText("正在加载，请稍候...");
            mPresenter.getStreamMemberByIdCard(intent.getStringExtra("updateHumenId"));
        }
        radioGroupInsertSex.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup arg0, int arg1) {

                if (arg1 == R.id.radioButton_Men) {
                    sex = "1";
                } else {
                    sex = "2";
                }
            }
        });
    }

    @Override
    protected void changeScreen() {
        if (null != mPopWindow && mPopWindow.isShowing()) {
            mPopWindow.dismiss();
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_insert_humen_manager;
    }

    @Override
    protected void otherViewClick(View view) {

    }
    @OnClick({R.id.imageView_Insert_Save,R.id.linearLayout_StreamTitle,R.id.imageView_Insert_Submit,R.id.imageView_Back,R.id.linearLayout_Emtp,R.id.linearLayout_LinearLayout_EmtpRoles})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.linearLayout_StreamTitle:
                editTextInsertIdNum.setFocusable(false);
                //editTextInsertUserName.setFocusableInTouchMode(true);
                //editTextInsertUserName.clearFocus();
                editTextInsertIdNum.setFocusable(true);
                editTextInsertIdNum.setFocusableInTouchMode(true);
                showThirdPopupWindow();
                break;
            case R.id.linearLayout_LinearLayout_EmtpRoles:
                editTextInsertIdNum.setFocusable(false);
                //editTextInsertUserName.setFocusableInTouchMode(true);
               // editTextInsertUserName.clearFocus();
                editTextInsertIdNum.setFocusable(true);
                editTextInsertIdNum.setFocusableInTouchMode(true);
                showSecondPopupWindow();
                break;
            case R.id.linearLayout_Emtp:
                editTextInsertIdNum.setFocusable(false);
                //editTextInsertUserName.setFocusableInTouchMode(true);
                //editTextInsertUserName.clearFocus();
                editTextInsertIdNum.setFocusable(true);
                editTextInsertIdNum.setFocusableInTouchMode(true);
                showPopupWindow();
                break;
            case R.id.imageView_Insert_Save:
                if(TextUtils.isEmpty(editTextInsertUserName.getText().toString())){
                    toast("未填写姓名，请先填写姓名！");
                    return;
                }
                if(TextUtils.isEmpty(EmtpId)){
                    toast("未选择部门类型，请先选择部门类型！");
                    return;
                }
                if(TextUtils.isEmpty(EmtpRolesId)){
                    toast("未选择工作岗位，请先选择工作岗位！");
                    return;
                }
                if(TextUtils.isEmpty(streamTitle)){
                    toast("未选择职称，请先选择职称！");
                    return;
                }
                if(TextUtils.isEmpty(editTextInsertValidateCode.getText().toString())){
                    toast("未填写注册证、岗位证名称及编号，请先填写相关信息！");
                    return;
                }
                if(TextUtils.isEmpty(editTextInsertIdNum.getText().toString())){
                    toast("未填写身份证号，请先填写身份证号！");
                    return;
                }

                if(TextUtils.isEmpty(editTextInsertPhone.getText().toString())){
                    toast("未填写手机号，请先填写手机号！");
                    return;
                }
                if(!RegexUtils.checkIdCard(editTextInsertIdNum.getText().toString())){
                    toast("请输入正确的身份证号！");
                    return;
                }
                if(!RegexUtils.checkMobile(editTextInsertPhone.getText().toString())){
                    toast("请输入正确的手机号！");
                    return;
                }
                statusType = "0";
                showProgressDialogWithText("正在保存中，请稍候...");
                if(userBean != null && IdNum.equals(editTextInsertIdNum.getText().toString())&& Moblie.equals(editTextInsertPhone.getText().toString())){
                    if(userBean!=null){
                        if(selectProject== null){
                            mPresenter.insertHumen(userBean.getStreamId()+"",editTextInsertIdNum.getText().toString(),editTextInsertUserName.getText().toString(),editTextInsertPhone.getText().toString(), headImgSrc, idNumImgSrc, idNumBackImgSrc,EmtpId,EmtpRolesId,sex,loginBean.getProjId(),editTextInsertValidateCode.getText().toString(),streamTitle,statusType,loginBean.getUserTypeId(),loginBean.getMemberId()+"",loginBean.getUserName());
                        }else{
                            mPresenter.insertHumen(userBean.getStreamId()+"",editTextInsertIdNum.getText().toString(),editTextInsertUserName.getText().toString(),editTextInsertPhone.getText().toString(), headImgSrc, idNumImgSrc, idNumBackImgSrc,EmtpId,EmtpRolesId,sex,selectProject,editTextInsertValidateCode.getText().toString(),streamTitle,statusType,loginBean.getUserTypeId(),loginBean.getMemberId()+"",loginBean.getUserName());
                        }
                    }else{
                        if(selectProject== null) {
                            mPresenter.insertHumen("0", editTextInsertIdNum.getText().toString(), editTextInsertUserName.getText().toString(), editTextInsertPhone.getText().toString(), headImgSrc, idNumImgSrc, idNumBackImgSrc, EmtpId, EmtpRolesId, sex, loginBean.getProjId(), editTextInsertValidateCode.getText().toString(), streamTitle, statusType,loginBean.getUserTypeId(),loginBean.getMemberId()+"",loginBean.getUserName());
                        }else {
                            mPresenter.insertHumen("0", editTextInsertIdNum.getText().toString(), editTextInsertUserName.getText().toString(), editTextInsertPhone.getText().toString(), headImgSrc, idNumImgSrc, idNumBackImgSrc, EmtpId, EmtpRolesId, sex, selectProject, editTextInsertValidateCode.getText().toString(), streamTitle, statusType,loginBean.getUserTypeId(),loginBean.getMemberId()+"",loginBean.getUserName());
                        }
                    }
                }else if(userBean != null && IdNum.equals(editTextInsertIdNum.getText().toString()) ){
                    mPresenter.checkHumenIsExist(editTextInsertPhone.getText().toString(),"");
                }else if(userBean != null && Moblie.equals(editTextInsertPhone.getText().toString())){
                    mPresenter.checkHumenIsExist("",editTextInsertIdNum.getText().toString());
                }  else{
                    mPresenter.checkHumenIsExist(editTextInsertPhone.getText().toString(),editTextInsertIdNum.getText().toString());
                }
                break;
            case R.id.imageView_Insert_Submit:
                if(TextUtils.isEmpty(editTextInsertUserName.getText().toString())){
                    toast("未填写姓名，请先填写姓名！");
                    return;
                }
                if(TextUtils.isEmpty(EmtpId)){
                    toast("未选择部门类型，请先选择部门类型！");
                    return;
                }
                if(TextUtils.isEmpty(EmtpRolesId)){
                    toast("未选择工作岗位，请先选择工作岗位！");
                    return;
                }
                if(TextUtils.isEmpty(streamTitle)){
                    toast("未选择职称，请先选择职称！");
                    return;
                }
                if(TextUtils.isEmpty(editTextInsertValidateCode.getText().toString())){
                    toast("未填写注册证、岗位证名称及编号，请先填写相关信息！");
                    return;
                }
                if(TextUtils.isEmpty(editTextInsertIdNum.getText().toString())){
                    toast("未填写身份证号，请先填写身份证号！");
                    return;
                }

                if(TextUtils.isEmpty(editTextInsertPhone.getText().toString())){
                    toast("未填写手机号，请先填写手机号！");
                    return;
                }
                if(!RegexUtils.checkIdCard(editTextInsertIdNum.getText().toString())){
                    toast("请输入正确的身份证号！");
                    return;
                }
                if(!RegexUtils.checkMobile(editTextInsertPhone.getText().toString())){
                    toast("请输入正确的手机号！");
                    return;
                }
                statusType = "1";
                showProgressDialogWithText("正在保存提交中，请稍候...");
                if(userBean != null && IdNum.equals(editTextInsertIdNum.getText().toString())&& Moblie.equals(editTextInsertPhone.getText().toString())){
                    if(userBean!=null){
                        if(selectProject== null){
                            mPresenter.insertHumen(userBean.getStreamId()+"",editTextInsertIdNum.getText().toString(),editTextInsertUserName.getText().toString(),editTextInsertPhone.getText().toString(), headImgSrc, idNumImgSrc, idNumBackImgSrc,EmtpId,EmtpRolesId,sex,loginBean.getProjId(),editTextInsertValidateCode.getText().toString(),streamTitle,statusType,loginBean.getUserTypeId(),loginBean.getMemberId()+"",loginBean.getUserName());
                        }else{
                            mPresenter.insertHumen(userBean.getStreamId()+"",editTextInsertIdNum.getText().toString(),editTextInsertUserName.getText().toString(),editTextInsertPhone.getText().toString(), headImgSrc, idNumImgSrc, idNumBackImgSrc,EmtpId,EmtpRolesId,sex,selectProject,editTextInsertValidateCode.getText().toString(),streamTitle,statusType,loginBean.getUserTypeId(),loginBean.getMemberId()+"",loginBean.getUserName());
                        }
                    }else{
                        if(selectProject== null) {
                            mPresenter.insertHumen("0", editTextInsertIdNum.getText().toString(), editTextInsertUserName.getText().toString(), editTextInsertPhone.getText().toString(), headImgSrc, idNumImgSrc, idNumBackImgSrc,EmtpId, EmtpRolesId, sex, loginBean.getProjId(), editTextInsertValidateCode.getText().toString(), streamTitle, statusType,loginBean.getUserTypeId(),loginBean.getMemberId()+"",loginBean.getUserName());
                        }else {
                            mPresenter.insertHumen("0", editTextInsertIdNum.getText().toString(), editTextInsertUserName.getText().toString(), editTextInsertPhone.getText().toString(), headImgSrc, idNumImgSrc, idNumBackImgSrc,EmtpId, EmtpRolesId, sex, selectProject, editTextInsertValidateCode.getText().toString(), streamTitle, statusType,loginBean.getUserTypeId(),loginBean.getMemberId()+"",loginBean.getUserName());
                        }
                    }
                }else if(userBean != null && IdNum.equals(editTextInsertIdNum.getText().toString())){
                    mPresenter.checkHumenIsExist(editTextInsertPhone.getText().toString(),"");
                }else if(userBean != null && Moblie.equals(editTextInsertPhone.getText().toString())){
                    mPresenter.checkHumenIsExist("",editTextInsertIdNum.getText().toString());
                }  else{
                    mPresenter.checkHumenIsExist(editTextInsertPhone.getText().toString(),editTextInsertIdNum.getText().toString());
                }

                break;
            case R.id.imageView_Back:
                finish();
                break;
            default:
                break;
        }
    }
    private void showThirdPopupWindow() {
//        backgroundAlpha(0.5f);
        View contentView;
        if (mScreenOrientation) {
            contentView = LayoutInflater.from(InsertHumenManagerActivity.this).inflate(R.layout.view_popupwindow_listview_land, null);
        } else {
            contentView = LayoutInflater.from(InsertHumenManagerActivity.this).inflate(R.layout.view_popupwindow_listview, null);
        }
        mPopWindow = new PopupWindow(contentView);
        mPopWindow.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        mPopWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        mPopWindow.setBackgroundDrawable(new BitmapDrawable());
        mPopWindow.setOutsideTouchable(true);
        mPopWindow.setFocusable(true);
//        mPopWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
//            @Override
//            public void onDismiss() {
//                backgroundAlpha(1);
//            }
//        });
        LinearLayout pop_LinearLayout = (LinearLayout) contentView.findViewById(R.id.pop_LinearLayout);
        pop_LinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPopWindow.dismiss();
            }
        });
        ListView ListViewPopupWindow = (ListView) contentView.findViewById(R.id.pop_ListView);
        ListViewPopupWindow.setAdapter(new PopListViewAdapter(this,DictByTypeList));
        ListViewPopupWindow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mPopWindow.dismiss();
                spinnerStreamTitle.setText(DictByTypeList.get(position).getName());
                streamTitle = DictByTypeList.get(position).getName();

            }
        });
        if (Build.VERSION.SDK_INT < 24) {
            mPopWindow.showAsDropDown(linearLayoutStreamTitle);
        } else {
            int[] location = new int[2];
            linearLayoutStreamTitle.getLocationOnScreen(location);
            int x = location[0];
            int y = location[1];
            if (Build.VERSION.SDK_INT == 25) {
                WindowManager wm = (WindowManager) mPopWindow.getContentView().getContext().getSystemService(Context.WINDOW_SERVICE);
                int screenHeight = wm.getDefaultDisplay().getHeight();
                mPopWindow.setHeight(screenHeight - location[1] - linearLayoutStreamTitle.getHeight());
            }
            mPopWindow.showAtLocation(linearLayoutStreamTitle, Gravity.NO_GRAVITY, x, linearLayoutStreamTitle.getHeight() + y);
        }

    }
    private void showSecondPopupWindow() {
//        backgroundAlpha(0.5f);
        View contentView ;
        if (mScreenOrientation) {
            contentView = LayoutInflater.from(InsertHumenManagerActivity.this).inflate(R.layout.view_popupwindow_listview_land, null);
        } else {
            contentView = LayoutInflater.from(InsertHumenManagerActivity.this).inflate(R.layout.view_popupwindow_listview, null);
        }
        mPopWindow = new PopupWindow(contentView);
        mPopWindow.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        mPopWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        mPopWindow.setOutsideTouchable(true);
        mPopWindow.setFocusable(true);
        mPopWindow.setBackgroundDrawable(new BitmapDrawable());
//        mPopWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
//            @Override
//            public void onDismiss() {
//                backgroundAlpha(1);
//            }
//        });
        ListView ListViewPopupWindow = (ListView) contentView.findViewById(R.id.pop_ListView);
        LinearLayout pop_LinearLayout = (LinearLayout) contentView.findViewById(R.id.pop_LinearLayout);
        pop_LinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPopWindow.dismiss();
            }
        });
        ListViewPopupWindow.setAdapter(new PopListViewSecondAdapter(this,EmtpRolesBeanList));
        ListViewPopupWindow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mPopWindow.dismiss();
                EmtpRolesId = EmtpRolesBeanList.get(position).getId()+"";
                spinnerInsertHumenEmtpRoles.setText(EmtpRolesBeanList.get(position).getName());
            }
        });
        if (Build.VERSION.SDK_INT < 24) {
            mPopWindow.showAsDropDown(linearLayoutLinearLayoutEmtpRoles);
        } else {
            int[] location = new int[2];
            linearLayoutLinearLayoutEmtpRoles.getLocationOnScreen(location);
            int x = location[0];
            int y = location[1];
            if (Build.VERSION.SDK_INT == 25) {
                WindowManager wm = (WindowManager) mPopWindow.getContentView().getContext().getSystemService(Context.WINDOW_SERVICE);
                int screenHeight = wm.getDefaultDisplay().getHeight();
                mPopWindow.setHeight(screenHeight - location[1] - linearLayoutLinearLayoutEmtpRoles.getHeight());
            }
            mPopWindow.showAtLocation(linearLayoutLinearLayoutEmtpRoles, Gravity.NO_GRAVITY, x, y + linearLayoutLinearLayoutEmtpRoles.getHeight());
        }

    }
    private void showPopupWindow() {
//        backgroundAlpha(0.5f);
        View contentView;
        if (mScreenOrientation) {
            contentView = LayoutInflater.from(InsertHumenManagerActivity.this).inflate(R.layout.view_popupwindow_listview_land, null);
        } else {
            contentView = LayoutInflater.from(InsertHumenManagerActivity.this).inflate(R.layout.view_popupwindow_listview, null);
        }
        mPopWindow = new PopupWindow(contentView);
        mPopWindow.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        mPopWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        mPopWindow.setOutsideTouchable(true);
        mPopWindow.setFocusable(true);
        mPopWindow.setBackgroundDrawable(new BitmapDrawable());
//        mPopWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
//            @Override
//            public void onDismiss() {
//                backgroundAlpha(1);
//            }
//        });
        LinearLayout pop_LinearLayout = (LinearLayout) contentView.findViewById(R.id.pop_LinearLayout);
        pop_LinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPopWindow.dismiss();
            }
        });
        ListView ListViewPopupWindow = (ListView) contentView.findViewById(R.id.pop_ListView);
        ListViewPopupWindow.setAdapter(new PopListViewAdapter(this,EmtpBeanList));
        ListViewPopupWindow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mPopWindow.dismiss();
                spinnerInsertHumen.setText(EmtpBeanList.get(position).getName());
                EmtpId = EmtpBeanList.get(position).getId()+"";
                EmtpRolesBeanList.clear();
                EmtpRolesId ="";
                spinnerInsertHumenEmtpRoles.setText("");
                showProgressDialogWithText("正在加载，请稍候...");
                //mPresenter.getTeam(loginBean.getProjId());
                mPresenter.getEmtpRoles(EmtpId);

            }
        });
       if (Build.VERSION.SDK_INT < 24) {
            mPopWindow.showAsDropDown(linearLayoutEmtp);
        } else {
           int[] location = new int[2];
           linearLayoutEmtp.getLocationOnScreen(location);
           int x = location[0];
           int y = location[1];
           if (Build.VERSION.SDK_INT == 25) {
               WindowManager wm = (WindowManager) mPopWindow.getContentView().getContext().getSystemService(Context.WINDOW_SERVICE);
               int screenHeight = wm.getDefaultDisplay().getHeight();
               mPopWindow.setHeight(screenHeight - location[1] - linearLayoutEmtp.getHeight());
           }
            mPopWindow.showAtLocation(linearLayoutEmtp, Gravity.NO_GRAVITY, x, linearLayoutEmtp.getHeight() + y);
        }

    }
//    public void backgroundAlpha(float bgAlpha) {
//        WindowManager.LayoutParams lp = getWindow().getAttributes();
//        lp.alpha = bgAlpha; //0.0-1.0
//        getWindow().setAttributes(lp);
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
//    }
    @Override
    public void getTeamSuccess(TeamListBean teamBeanList) {

    }

    @Override
    public void getTeamFail(String failMsg) {

    }

    @Override
    public void getInsertHumenSuccess(String s) {
        dismissProgressDialog();
        toast("人员添加成功！");

        if(intent.getBooleanExtra(("isUpdate"),false)) {
            intent.putExtra("statusType",statusType);
            intent.putExtra("idNum",editTextInsertIdNum.getText().toString());
            intent.putExtra("userName",editTextInsertUserName.getText().toString());
        }
        this.setResult(1, intent);
        this.finish();
    }

    @Override
    public void getInsertHumenSuccessFail(String failMsg) {
        dismissProgressDialog();
        toast(failMsg);
    }

    @Override
    public void getcheckHumenIsExistSuccess(String s) {
        //添加背面
        if(userBean!=null){
            if(selectProject== null){
                mPresenter.insertHumen(userBean.getStreamId()+"",editTextInsertIdNum.getText().toString(),editTextInsertUserName.getText().toString(),editTextInsertPhone.getText().toString(), headImgSrc, idNumImgSrc, idNumBackImgSrc,EmtpId,EmtpRolesId,sex,loginBean.getProjId(),editTextInsertValidateCode.getText().toString(),streamTitle,statusType,loginBean.getUserTypeId(),loginBean.getMemberId()+"",loginBean.getUserName());
            }else{
                mPresenter.insertHumen(userBean.getStreamId()+"",editTextInsertIdNum.getText().toString(),editTextInsertUserName.getText().toString(),editTextInsertPhone.getText().toString(), headImgSrc, idNumImgSrc, idNumBackImgSrc,EmtpId,EmtpRolesId,sex,selectProject,editTextInsertValidateCode.getText().toString(),streamTitle,statusType,loginBean.getUserTypeId(),loginBean.getMemberId()+"",loginBean.getUserName());
            }
        }else{
            if(selectProject== null) {
                mPresenter.insertHumen("0", editTextInsertIdNum.getText().toString(), editTextInsertUserName.getText().toString(), editTextInsertPhone.getText().toString(), headImgSrc, idNumImgSrc, idNumBackImgSrc, EmtpId, EmtpRolesId, sex, loginBean.getProjId(), editTextInsertValidateCode.getText().toString(), streamTitle, statusType,loginBean.getUserTypeId(),loginBean.getMemberId()+"",loginBean.getUserName());
            }else {
                mPresenter.insertHumen("0", editTextInsertIdNum.getText().toString(), editTextInsertUserName.getText().toString(), editTextInsertPhone.getText().toString(), headImgSrc, idNumImgSrc, idNumBackImgSrc,EmtpId, EmtpRolesId, sex, selectProject, editTextInsertValidateCode.getText().toString(), streamTitle, statusType,loginBean.getUserTypeId(),loginBean.getMemberId()+"",loginBean.getUserName());
            }
        }


    }

    @Override
    public void getcheckHumenIsExistFail(String failMsg) {
        dismissProgressDialog();
        toast(failMsg);
    }

    @Override
    public void getEmtpSuccess(EmtpBean emtpBean) {

        EmtpBeanList = emtpBean.getList();
        mPresenter.getDictByType();
    }

    @Override
    public void getEmtpFail(String failMsg) {
        dismissProgressDialog();
        toast("网络异常，请重试！");
        finish();
    }

    @Override
    public void getDictByTypeSuccess(EmtpBean emtpBean) {
        dismissProgressDialog();
        DictByTypeList = emtpBean.getList();
    }

    @Override
    public void getDictByTypeFail(String failMsg) {
        dismissProgressDialog();
        toast("网络异常，请重试！");
        finish();
    }

    @Override
    public void getEmtpRolesSuccess(EmtpRolesBean emtpRolesBean) {
        dismissProgressDialog();
        linearLayoutEmtpRoles.setVisibility(View.VISIBLE);
        linearLayoutEmtpRolesLine.setVisibility(View.VISIBLE);
        EmtpRolesBeanList = emtpRolesBean.getList();
        //判断
        if(isExistHumen){
            for (int i= 0;i<EmtpRolesBeanList.size();i++){
                if(EmtpRolesBeanList.get(i).getId() == userBean.getEmtpRolesId()){
                    EmtpRolesId = EmtpRolesBeanList.get(i).getId()+"";
                    spinnerInsertHumenEmtpRoles.setText(EmtpRolesBeanList.get(i).getName());
                }
            }
            streamTitle = userBean.getStreamTitle();
            spinnerStreamTitle.setText(userBean.getStreamTitle());
            editTextInsertValidateCode.setText(userBean.getValidateCode());
            editTextInsertPhone.setText(userBean.getMobile());
            //Moblie = userBean.getMobile();
            if(userBean.getGender() == 1){
                radioButtonMen.setChecked(true);
                sex = "1";
            }else{
                radioButtonWomen.setChecked(true);
                sex = "2";
            }
            isExistHumen = false;
        }

    }

    @Override
    public void getEmtpRolesFail(String failMsg) {
        dismissProgressDialog();
        toast("网络异常，请重试！");
        finish();
    }

    @Override
    public void getStreamMemberByIdCardSuccess(UserBean userBean) {
       this.userBean = userBean;
        IdNum = userBean.getIDCard();
        Moblie = userBean.getMobile();
      //  Log.e("sss_",this.userBean.getImageUrl());
        //mPresenter.loadHead(UrlHelper.BASE_URL+this.userBean.getImageUrl());
        editTextInsertIdNum.setText(userBean.getIDCard());
        editTextInsertUserName.setText(userBean.getStreamName());
        //EmtpId = EmtpBeanList.get(userBean.getEmtpId()).getId()+"";
        Log.e("EmtpBeanList",userBean.getEmtpId()+"");
        Log.e("EmtpBeanList",EmtpBeanList.size()+"");
        for (int i= 0;i<EmtpBeanList.size();i++){
            if(EmtpBeanList.get(i).getId() == userBean.getEmtpId()){
                EmtpId = EmtpBeanList.get(i).getId()+"";
                Log.e("EmtpBeanList",EmtpBeanList.get(i).getName()+"");
                spinnerInsertHumen.setText(EmtpBeanList.get(i).getName());
            }
        }
        Log.e("EmtpBeanList",EmtpId);
        mPresenter.getEmtpRoles(EmtpId);
        isExistHumen = true;
        idNumImgSrc = userBean.getImageUrl();
        headImgSrc = userBean.getHeadImage();
        idNumBackImgSrc= userBean.getIDCardBackImagePath();
    }

    @Override
    public void getStreamMemberByIdCardFail(String failMsg) {
        idNumImgSrc = "";
        headImgSrc = "";
        idNumBackImgSrc= "";
        this.userBean = null;
        dismissProgressDialog();
    }

    @Override
    public void getHeadSuccess(byte[] bytes) {

    }

    @Override
    public void getHeadFail(String failMsg) {

    }


}
