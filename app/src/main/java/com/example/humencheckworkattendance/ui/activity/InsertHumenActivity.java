package com.example.humencheckworkattendance.ui.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.humencheckworkattendance.R;
import com.example.humencheckworkattendance.adapter.PopListViewAdapter;
import com.example.humencheckworkattendance.adapter.PopListViewSecondAdapter;
import com.example.humencheckworkattendance.base.BaseActivity;
import com.example.humencheckworkattendance.bean.EmtpBean;
import com.example.humencheckworkattendance.bean.EmtpRolesBean;
import com.example.humencheckworkattendance.bean.IdCardBean;
import com.example.humencheckworkattendance.bean.LoginBean;
import com.example.humencheckworkattendance.bean.TeamListBean;
import com.example.humencheckworkattendance.bean.UserBean;
import com.example.humencheckworkattendance.contact.InsertHumenContact;
import com.example.humencheckworkattendance.presenter.InserHumenPresenter;
import com.example.humencheckworkattendance.url.UrlHelper;
import com.example.humencheckworkattendance.utils.RegexUtils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import butterknife.BindView;
import butterknife.OnClick;
import cn.cloudwalk.libproject.util.FileUtil;

public class InsertHumenActivity extends BaseActivity<InserHumenPresenter> implements InsertHumenContact.InsertHumenView {
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
    @BindView(R.id.imageView_UpLoadIdNum)
    ImageView imageViewUpLoadIdNum;
    @BindView(R.id.imageView_UpLoadIdNumBack)
    ImageView imageViewUpLoadIdNumBack;
    @BindView(R.id.imageView_UpLoadHead)
    ImageView imageViewUpLoadHead;
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
/*    @BindView(R.id.imageView_InsertHumenAddGroup)
    ImageView imageViewInsertHumenAddGroup;*/
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
    String idNumImgSrc = "";
    String headImgSrc = "";
    String idNumBackImgSrc="";
    String sex="1";
    //String branchId = "";
    ArrayList<TeamListBean.ListBean> teamData;
    PopupWindow mPopWindow;
    ArrayList<EmtpBean.ListBean>  EmtpBeanList = new ArrayList<EmtpBean.ListBean> ();
    ArrayList<EmtpBean.ListBean>  DictByTypeList = new ArrayList<EmtpBean.ListBean> ();
    ArrayList<EmtpRolesBean.ListBean>  EmtpRolesBeanList = new ArrayList<EmtpRolesBean.ListBean> ();
    String EmtpId="";
    String EmtpRolesId="";
    String streamTitle="";
    UserBean userBean ;
    int imageType = 1;
    File headPictureFile ;
    File IDPictureFile ;
    File IDBackPictureFile ;
    boolean isExistHumen = false;
    String statusType;
    String IdNum;
    String Moblie;
    String selectProject = "";
    @Override
    protected InserHumenPresenter loadPresenter() {
        return new InserHumenPresenter();
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        loginBean = (LoginBean)intent.getSerializableExtra("userMessage");
        showProgressDialogWithText("正在加载，请稍候...");
        selectProject = intent.getStringExtra("selectProjectId");
        Log.e("selectProject",selectProject+"");
        //mPresenter.getTeam(loginBean.getProjId());

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
    protected int getLayoutId() {
        return R.layout.activity_insert_humen;
    }

    @Override
    protected void otherViewClick(View view) {

    }
    @OnClick({R.id.imageView_Insert_Save,R.id.linearLayout_StreamTitle,R.id.imageView_UpLoadIdNumBack,R.id.imageView_UpLoadIdNum,R.id.imageView_UpLoadHead,R.id.imageView_Insert_Submit,R.id.imageView_Back,R.id.linearLayout_Emtp,R.id.linearLayout_LinearLayout_EmtpRoles})
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
            case R.id.imageView_UpLoadIdNum:
                intent.setClass(InsertHumenActivity.this,TakeIdNumCameraActivity.class);
                startActivityForResult(intent,0X111);
                break;
            case R.id.imageView_UpLoadHead:
                intent.setClass(InsertHumenActivity.this,TakeHeadCameraActivity.class);
                startActivityForResult(intent,0X111);
                break;
            case R.id.imageView_UpLoadIdNumBack:
                intent.setClass(InsertHumenActivity.this,TakeIdNumBackCameraActivity.class);
                startActivityForResult(intent,0X111);
                break;
            case R.id.imageView_Insert_Save:
                if(TextUtils.isEmpty(idNumImgSrc)){
                    toast("未上传身份证正面照，请先上传身份证正面照！");
                    return;
                }
                if(TextUtils.isEmpty(headImgSrc)){
                    toast("未上传头像，请先上传头像！");
                    return;
                }
                if(TextUtils.isEmpty(idNumBackImgSrc)){
                    toast("未上传身份证背面照，请先上传身份证背面照！");
                    return;
                }
                if(TextUtils.isEmpty(editTextInsertUserName.getText().toString())){
                    toast("未填写姓名，请先填写姓名！");
                    return;
                }
                /*if(TextUtils.isEmpty(branchId)){
                    toast("未选择班组，请先选择班组！");
                    return;
                }*/
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
                            mPresenter.insertHumen(userBean.getStreamId()+"",editTextInsertIdNum.getText().toString(),editTextInsertUserName.getText().toString(),editTextInsertPhone.getText().toString(),headImgSrc,idNumImgSrc,idNumBackImgSrc,EmtpId,EmtpRolesId,sex,loginBean.getProjId(),editTextInsertValidateCode.getText().toString(),streamTitle,statusType,loginBean.getUserTypeId());
                        }else{
                            mPresenter.insertHumen(userBean.getStreamId()+"",editTextInsertIdNum.getText().toString(),editTextInsertUserName.getText().toString(),editTextInsertPhone.getText().toString(),headImgSrc,idNumImgSrc,idNumBackImgSrc,EmtpId,EmtpRolesId,sex,selectProject,editTextInsertValidateCode.getText().toString(),streamTitle,statusType,loginBean.getUserTypeId());
                        }
                    }else{
                        if(selectProject== null) {
                            mPresenter.insertHumen("0", editTextInsertIdNum.getText().toString(), editTextInsertUserName.getText().toString(), editTextInsertPhone.getText().toString(), headImgSrc, idNumImgSrc, idNumBackImgSrc, EmtpId, EmtpRolesId, sex, loginBean.getProjId(), editTextInsertValidateCode.getText().toString(), streamTitle, statusType,loginBean.getUserTypeId());
                        }else {
                            mPresenter.insertHumen("0", editTextInsertIdNum.getText().toString(), editTextInsertUserName.getText().toString(), editTextInsertPhone.getText().toString(), headImgSrc, idNumImgSrc, idNumBackImgSrc, EmtpId, EmtpRolesId, sex, selectProject, editTextInsertValidateCode.getText().toString(), streamTitle, statusType,loginBean.getUserTypeId());
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
                if(TextUtils.isEmpty(idNumImgSrc)){
                    toast("未上传身份证正面照，请先上传身份证正面照！");
                    return;
                }
                if(TextUtils.isEmpty(headImgSrc)){
                    toast("未上传头像，请先上传头像！");
                    return;
                }
                if(TextUtils.isEmpty(idNumBackImgSrc)){
                    toast("未上传身份证背面照，请先上传身份证背面照！");
                    return;
                }
                if(TextUtils.isEmpty(editTextInsertUserName.getText().toString())){
                    toast("未填写姓名，请先填写姓名！");
                    return;
                }
                /*if(TextUtils.isEmpty(branchId)){
                    toast("未选择班组，请先选择班组！");
                    return;
                }*/
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
                            mPresenter.insertHumen(userBean.getStreamId()+"",editTextInsertIdNum.getText().toString(),editTextInsertUserName.getText().toString(),editTextInsertPhone.getText().toString(),headImgSrc,idNumImgSrc,idNumBackImgSrc,EmtpId,EmtpRolesId,sex,loginBean.getProjId(),editTextInsertValidateCode.getText().toString(),streamTitle,statusType,loginBean.getUserTypeId());
                        }else{
                            mPresenter.insertHumen(userBean.getStreamId()+"",editTextInsertIdNum.getText().toString(),editTextInsertUserName.getText().toString(),editTextInsertPhone.getText().toString(),headImgSrc,idNumImgSrc,idNumBackImgSrc,EmtpId,EmtpRolesId,sex,selectProject,editTextInsertValidateCode.getText().toString(),streamTitle,statusType,loginBean.getUserTypeId());
                        }
                    }else{
                        if(selectProject== null) {
                            mPresenter.insertHumen("0", editTextInsertIdNum.getText().toString(), editTextInsertUserName.getText().toString(), editTextInsertPhone.getText().toString(), headImgSrc, idNumImgSrc, idNumBackImgSrc, EmtpId, EmtpRolesId, sex, loginBean.getProjId(), editTextInsertValidateCode.getText().toString(), streamTitle, statusType,loginBean.getUserTypeId());
                        }else {
                            mPresenter.insertHumen("0", editTextInsertIdNum.getText().toString(), editTextInsertUserName.getText().toString(), editTextInsertPhone.getText().toString(), headImgSrc, idNumImgSrc, idNumBackImgSrc, EmtpId, EmtpRolesId, sex, selectProject, editTextInsertValidateCode.getText().toString(), streamTitle, statusType,loginBean.getUserTypeId());
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
        View contentView = LayoutInflater.from(InsertHumenActivity.this).inflate(R.layout.view_popupwindow_listview, null);
        mPopWindow = new PopupWindow(contentView);
        mPopWindow.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        mPopWindow.setHeight(ViewGroup.LayoutParams.FILL_PARENT);
        mPopWindow.setOutsideTouchable(true);
        mPopWindow.setFocusable(true);
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
            int[] a = new int[2];
            linearLayoutStreamTitle.getLocationInWindow(a);
          //  Log.e(" a[0]",a[0]+"");
            mPopWindow.showAtLocation(linearLayoutStreamTitle, Gravity.NO_GRAVITY, a[0], linearLayoutStreamTitle.getHeight() + a[1]);
            mPopWindow.update();
        }

    }
    private void showSecondPopupWindow() {
        View contentView = LayoutInflater.from(InsertHumenActivity.this).inflate(R.layout.view_popupwindow_listview, null);
        mPopWindow = new PopupWindow(contentView);
        mPopWindow.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        mPopWindow.setHeight(ViewGroup.LayoutParams.FILL_PARENT);
        mPopWindow.setOutsideTouchable(true);
        mPopWindow.setFocusable(true);
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
            mPopWindow.showAtLocation(linearLayoutLinearLayoutEmtpRoles, Gravity.NO_GRAVITY, x, y + linearLayoutLinearLayoutEmtpRoles.getHeight());
        }

    }
    private void showPopupWindow() {
        View contentView = LayoutInflater.from(InsertHumenActivity.this).inflate(R.layout.view_popupwindow_listview, null);
        mPopWindow = new PopupWindow(contentView);
        mPopWindow.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        mPopWindow.setHeight(ViewGroup.LayoutParams.FILL_PARENT);
        mPopWindow.setOutsideTouchable(true);
        mPopWindow.setFocusable(true);
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
            int[] a = new int[2];
            linearLayoutEmtp.getLocationInWindow(a);
          // Log.e(" a[0]",a[0]+"");
            mPopWindow.showAtLocation(linearLayoutEmtp, Gravity.NO_GRAVITY, a[0], linearLayoutEmtp.getHeight() + a[1]);
            mPopWindow.update();
        }

    }
    @Override
    public void getTeamSuccess(TeamListBean teamBeanList) {
     /*   dismissProgressDialog();
        teamData = teamBeanList.getList();

        ArrayList<String> strList = new ArrayList<String>();
        for (TeamListBean.ListBean dataList:teamData) {
            strList.add(dataList.getName());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, strList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerInsertHumen.setAdapter(adapter);
        spinnerInsertHumen.setOnItemSelectedListener(new SpinnerSelectedListener());*/
    }

    @Override
    public void getTeamFail(String failMsg) {
        dismissProgressDialog();
        toast("数据加载失败，请重试！");
        finish();
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
       // Log.e("selectProjectId",intent.getIntExtra("selectProjectId",0)+"");
       // Log.e("selectProjectId",loginBean.getProjId()+"");
        //添加背面
        if(userBean!=null){
            if(selectProject== null){
                mPresenter.insertHumen(userBean.getStreamId()+"",editTextInsertIdNum.getText().toString(),editTextInsertUserName.getText().toString(),editTextInsertPhone.getText().toString(),headImgSrc,idNumImgSrc,idNumBackImgSrc,EmtpId,EmtpRolesId,sex,loginBean.getProjId(),editTextInsertValidateCode.getText().toString(),streamTitle,statusType,loginBean.getUserTypeId());
            }else{
                mPresenter.insertHumen(userBean.getStreamId()+"",editTextInsertIdNum.getText().toString(),editTextInsertUserName.getText().toString(),editTextInsertPhone.getText().toString(),headImgSrc,idNumImgSrc,idNumBackImgSrc,EmtpId,EmtpRolesId,sex,selectProject,editTextInsertValidateCode.getText().toString(),streamTitle,statusType,loginBean.getUserTypeId());
            }
        }else{
            if(selectProject== null) {
                mPresenter.insertHumen("0", editTextInsertIdNum.getText().toString(), editTextInsertUserName.getText().toString(), editTextInsertPhone.getText().toString(), headImgSrc, idNumImgSrc, idNumBackImgSrc, EmtpId, EmtpRolesId, sex, loginBean.getProjId(), editTextInsertValidateCode.getText().toString(), streamTitle, statusType,loginBean.getUserTypeId());
            }else {
                mPresenter.insertHumen("0", editTextInsertIdNum.getText().toString(), editTextInsertUserName.getText().toString(), editTextInsertPhone.getText().toString(), headImgSrc, idNumImgSrc, idNumBackImgSrc, EmtpId, EmtpRolesId, sex, selectProject, editTextInsertValidateCode.getText().toString(), streamTitle, statusType,loginBean.getUserTypeId());
            }
        }
        /*if(userBean!=null){
            mPresenter.insertHumen(userBean.getStreamId()+"",editTextInsertIdNum.getText().toString(),editTextInsertUserName.getText().toString(),editTextInsertPhone.getText().toString(),headImgSrc,idNumImgSrc,idNumBackImgSrc,EmtpId,EmtpRolesId,sex,loginBean.getProjId()+"",editTextInsertValidateCode.getText().toString(),streamTitle,statusType);
        }else{
            mPresenter.insertHumen("0",editTextInsertIdNum.getText().toString(),editTextInsertUserName.getText().toString(),editTextInsertPhone.getText().toString(),headImgSrc,idNumImgSrc,idNumBackImgSrc,EmtpId,EmtpRolesId,sex,loginBean.getProjId()+"",editTextInsertValidateCode.getText().toString(),streamTitle,statusType);
        }*/

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
        mPresenter.loadHead(UrlHelper.BASE_URL+this.userBean.getImageUrl());
    }
    @Override
    public void getHeadSuccess(final byte[]bytes) {
        new Thread() {
            @Override
            public void run() {
                super.run();
                if(imageType == 1){
                    imageType++;

                    try {
                        IDPictureFile = null;
                        SimpleDateFormat sdf = new SimpleDateFormat("yyMMddHHmm");
                        String publicFilePath = new StringBuilder(Environment.getExternalStorageDirectory().getAbsolutePath())
                                .append(File.separator).append("ivm").append(File.separator).append(sdf.format(new Date()))
                                .toString();
                        FileUtil.mkDir(publicFilePath);
                        if (bytes != null && bytes.length > 0) {
                            FileUtil.writeByteArrayToFile(bytes, publicFilePath + "/IDImage.jpg");
                            IDPictureFile = new File(publicFilePath + "/IDImage.jpg");
                        }
                      //  Log.e("imgSrc",IDPictureFile.getAbsolutePath()+"");
                        mPresenter.loadHead(UrlHelper.BASE_URL+userBean.getHeadImage());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }else if(imageType == 2){
                    imageType++;

                    try {
                        headPictureFile = null;
                        SimpleDateFormat sdf = new SimpleDateFormat("yyMMddHHmm");
                        String publicFilePath = new StringBuilder(Environment.getExternalStorageDirectory().getAbsolutePath())
                                .append(File.separator).append("ivm").append(File.separator).append(sdf.format(new Date()))
                                .toString();
                        FileUtil.mkDir(publicFilePath);
                        if (bytes != null && bytes.length > 0) {
                            FileUtil.writeByteArrayToFile(bytes, publicFilePath + "/HeadImage.jpg");
                            headPictureFile = new File(publicFilePath + "/HeadImage.jpg");
                        }
                      //  Log.e("imgSrc",headPictureFile.getAbsolutePath()+"");
                        mPresenter.loadHead(UrlHelper.BASE_URL+userBean.getIDCardBackImagePath());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }else if(imageType == 3){
                    dismissProgressDialog();
                    imageType = 1;

                    try {
                        IDBackPictureFile = null;
                        SimpleDateFormat sdf = new SimpleDateFormat("yyMMddHHmm");
                        String publicFilePath = new StringBuilder(Environment.getExternalStorageDirectory().getAbsolutePath())
                                .append(File.separator).append("ivm").append(File.separator).append(sdf.format(new Date()))
                                .toString();
                        FileUtil.mkDir(publicFilePath);
                        if (bytes != null && bytes.length > 0) {
                            FileUtil.writeByteArrayToFile(bytes, publicFilePath + "/IdBackImage.jpg");
                            IDBackPictureFile = new File(publicFilePath + "/IdBackImage.jpg");
                        }
                      //  Log.e("imgSrc",IDBackPictureFile.getAbsolutePath()+"");
                       runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if(idNumImgSrc.equals("")){
                                    Glide.with(InsertHumenActivity.this).load(IDPictureFile.getAbsolutePath()+"")//拿到头像本地存放路径
                                            .error(R.drawable.signheader)//失败默认
                                            .placeholder(R.drawable.signheader)
                                            .diskCacheStrategy(DiskCacheStrategy.NONE)//不单独缓存
                                            .skipMemoryCache(true)
                                            .into(imageViewUpLoadIdNum);
                                    idNumImgSrc = userBean.getImageUrl();
                                }
                                if(headImgSrc.equals("")){
                                    Glide.with(InsertHumenActivity.this).load(headPictureFile.getAbsolutePath()+"")//拿到头像本地存放路径
                                            .error(R.drawable.signheader)//失败默认
                                            .placeholder(R.drawable.signheader)
                                            .diskCacheStrategy(DiskCacheStrategy.NONE)//不单独缓存
                                            .skipMemoryCache(true)
                                            .into(imageViewUpLoadHead);
                                    headImgSrc = userBean.getHeadImage();
                                }
                                if(idNumBackImgSrc.equals("")) {
                                    Glide.with(InsertHumenActivity.this).load(IDBackPictureFile.getAbsolutePath() + "")//拿到头像本地存放路径
                                            .error(R.drawable.signheader)//失败默认
                                            .placeholder(R.drawable.signheader)
                                            .diskCacheStrategy(DiskCacheStrategy.NONE)//不单独缓存
                                            .skipMemoryCache(true)
                                            .into(imageViewUpLoadIdNumBack);
                                    idNumBackImgSrc = userBean.getIDCardBackImagePath();
                                }
                                editTextInsertIdNum.setText(userBean.getIDCard());
                                editTextInsertUserName.setText(userBean.getStreamName());
                                //EmtpId = EmtpBeanList.get(userBean.getEmtpId()).getId()+"";
                                for (int i= 0;i<EmtpBeanList.size();i++){
                                    if(EmtpBeanList.get(i).getId() == userBean.getEmtpId()){
                                        EmtpId = EmtpBeanList.get(i).getId()+"";
                                        spinnerInsertHumen.setText(EmtpBeanList.get(i).getName());
                                    }
                                }
                                mPresenter.getEmtpRoles(EmtpId);
                                isExistHumen = true;

                            }
                        });

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }


            }

        }.start();



    }

    @Override
    public void getHeadFail(String failMsg) {
        dismissProgressDialog();
        toast(failMsg);
    }
    @Override
    public void getStreamMemberByIdCardFail(String failMsg) {
        dismissProgressDialog();
    }

    /*class SpinnerSelectedListener implements AdapterView.OnItemSelectedListener {

        public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
                                   long arg3) {
            Log.e("班组id",teamData.get(arg2).getId()+""+teamData.get(arg2).getName());
            branchId = teamData.get(arg2).getId()+"";
        }

        public void onNothingSelected(AdapterView<?> arg0) {
        }
    }*/
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
       // Log.e("resultCode",resultCode+"");
        switch (resultCode) {
            case 2:
                Glide.with(InsertHumenActivity.this).load(Environment.getExternalStorageDirectory() + "/surfingscene/shotDir/idnum.jpg")//拿到头像本地存放路径
                        .error(R.drawable.signheader)//失败默认
                        .placeholder(R.drawable.signheader)
                        .diskCacheStrategy(DiskCacheStrategy.NONE)//不单独缓存
                        .skipMemoryCache(true)
                        .into(imageViewUpLoadIdNum);
                IdCardBean idCardBean = (IdCardBean)data.getSerializableExtra("idCardBean");
                idNumImgSrc = idCardBean.getPicUrl();
                editTextInsertIdNum.setText(idCardBean.getIdnumber());
                editTextInsertUserName.setText(idCardBean.getName());
                if(idCardBean.getGender() != null) {
                    if (idCardBean.getGender().equals("男")) {
                        radioButtonMen.setChecked(true);
                        sex = "1";
                    } else {
                        radioButtonWomen.setChecked(true);
                        sex = "2";
                    }
                }
                showProgressDialogWithText("正在加载，请稍候...");
                //IdNum = editTextInsertIdNum.getText().toString();
                mPresenter.getStreamMemberByIdCard(editTextInsertIdNum.getText().toString());
                Log.e("idNumImgSrc",idNumImgSrc);
                break;
            case 1:
                Glide.with(InsertHumenActivity.this).load(Environment.getExternalStorageDirectory() + "/surfingscene/shotDir/uploadHead.jpg")//拿到头像本地存放路径
                        .error(R.drawable.signheader)//失败默认
                        .placeholder(R.drawable.signheader)
                        .diskCacheStrategy(DiskCacheStrategy.NONE)//不单独缓存
                        .skipMemoryCache(true)
                        .into(imageViewUpLoadHead);
                headImgSrc = data.getStringExtra("url");
              //  Log.e("headImgSrc",headImgSrc);
                break;
            case 3:
                showProgressDialogWithText("正在加载，请稍候...");
                mPresenter.getTeam(loginBean.getProjId());
                break;
            case 4:
                Glide.with(InsertHumenActivity.this).load(Environment.getExternalStorageDirectory() + "/surfingscene/shotDir/idnumback.jpg")//拿到头像本地存放路径
                        .error(R.drawable.signheader)//失败默认
                        .placeholder(R.drawable.signheader)
                        .diskCacheStrategy(DiskCacheStrategy.NONE)//不单独缓存
                        .skipMemoryCache(true)
                        .into(imageViewUpLoadIdNumBack);
                idNumBackImgSrc = data.getStringExtra("url");
              //  Log.e("headImgSrc",headImgSrc);
                break;
            default:
                break;
        }
    }
}
