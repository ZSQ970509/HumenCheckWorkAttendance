package com.example.humencheckworkattendance.ui.activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.provider.Settings;
import android.support.v4.content.FileProvider;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.humencheckworkattendance.R;
import com.example.humencheckworkattendance.base.BaseActivity;
import com.example.humencheckworkattendance.bean.LoginBean;
import com.example.humencheckworkattendance.bean.UpdateBean;
import com.example.humencheckworkattendance.contact.LoginContact;
import com.example.humencheckworkattendance.presenter.LoginPresenter;
import com.example.humencheckworkattendance.url.UrlHelper;
import com.example.humencheckworkattendance.utils.PermissionManager;
import com.example.humencheckworkattendance.utils.PreferencesUtils;
import com.example.humencheckworkattendance.utils.ToastUtil;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import cn.cloudwalk.libproject.util.FileUtil;


public class LoginActivity extends BaseActivity<LoginPresenter> implements LoginContact.LoginView{
    EditText editTextUserName;
    EditText editTextPassWord;
    CheckBox checkBoxSavePassWord;

    @BindView(R.id.editText_UserName)
    EditText editTextUserNamePort;
    @BindView(R.id.editText_PassWord)
    EditText editTextPassWordPort;
    @BindView(R.id.checkBox_SavePassWord)
    CheckBox checkBoxSavePassWordPort;

    @BindView(R.id.editText_UserName_land)
    EditText editTextUserNameLand;
    @BindView(R.id.editText_PassWord_land)
    EditText editTextPassWordLand;
    @BindView(R.id.checkBox_SavePassWord_land)
    CheckBox checkBoxSavePassWordLand;


    @BindView(R.id.login_port)
    LinearLayout mLoginPort;
    @BindView(R.id.login_land)
    LinearLayout mLoginLand;

    LoginBean loginBean;
    File pictureFile;
    Intent updateIntent;
    UpdateBean updateBean;
    AlertDialog.Builder alertDialog;
    private final int SDK_PERMISSION_REQUEST = 127;
    private String permissionInfo;
    AlertDialog dialog = null;
    Handler myHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {

                case 0X111:
                    Intent intent2;
                    if(loginBean.getUserType().equals("manager")){
                        intent2 = new Intent(LoginActivity.this, HomePageManageActivity.class);
                    }else{
                        intent2 = new Intent(LoginActivity.this, HomePageActivity.class);
                    }
                    intent2.putExtra("userMessage",loginBean);
                    intent2.putExtra("updateBean",updateBean);
                    intent2.putExtra("imgSrc",pictureFile);
                    intent2.putExtra("isExist",true);
                    intent2.putExtra("isUpdate",updateIntent.getBooleanExtra("isUpdate",false));
                    Log.e("imgSrc",pictureFile.getAbsolutePath()+"");
                    startActivity(intent2);
                    finish();
                    break;
                case 0X112:
                    Intent intent1;
                    if(loginBean.getUserType().equals("manager")){
                        intent1 = new Intent(LoginActivity.this, HomePageManageActivity.class);
                    }else{
                        intent1 = new Intent(LoginActivity.this, HomePageActivity.class);
                    }
                    intent1.putExtra("userMessage",loginBean);
                    intent1.putExtra("updateBean",updateBean);
                    intent1.putExtra("isExist",false);
                    intent1.putExtra("imgSrc",pictureFile);
                    intent1.putExtra("isUpdate",updateIntent.getBooleanExtra("isUpdate",false));

                    startActivity(intent1);
                    break;

            }
            super.handleMessage(msg);
        }
    };
    @Override
    protected LoginPresenter loadPresenter() {
        return new LoginPresenter();
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        updateIntent = getIntent();
        updateBean = (UpdateBean)updateIntent.getSerializableExtra("updateBean");
        if(updateIntent.getBooleanExtra("isUpdate",false)){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            // 设置提示框的标题

            BigDecimal bg = new BigDecimal(Double.parseDouble(updateBean.getTarget_size())/1024/1024);
            double f1 = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
            String appSize = f1+"";
            builder.setTitle("发现新版本").
                    // 设置提示框的图标
                            setIcon(R.drawable.icon).
                    // 设置要显示的信息
                            setMessage("最新版本：V"+updateBean.getNew_user_version()+"\n"+"新版本大小："+appSize+"M\n\n"+"更新内容：\n"+updateBean.getUpdate_log()).
                    // 设置确定按钮
                            setPositiveButton("确定", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            loadNewVersionProgress();//下载最新的版本程序
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
    }

    @Override
    protected void changeScreen() {
        editTextUserName = getShowWidgetsOnScreen(editTextUserNameLand,editTextUserNamePort);
        editTextPassWord = getShowWidgetsOnScreen(editTextPassWordLand,editTextPassWordPort);
        checkBoxSavePassWord = getShowWidgetsOnScreen(checkBoxSavePassWordLand,checkBoxSavePassWordPort);
        setShowView(mLoginLand,mLoginPort);
    }

    private void loadNewVersionProgress() {
        final   String uri=updateBean.getApk_file_url();
       // final   String uri="http://120.35.11.49:26969/UpdatePackage/201709251618239026.apk";
        final ProgressDialog pd;    //进度条对话框
        pd = new  ProgressDialog(this);
        pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        pd.setProgressDrawable(getResources().getDrawable(R.drawable.progessbar));
        pd.setMessage("正在下载更新");
        pd.setCanceledOnTouchOutside(false);
        pd.setProgressNumberFormat("");
        pd.show();

        //启动子线程下载任务
        new Thread(){
            @Override
            public void run() {
                try {
                    Looper.prepare();
                    File file = getFileFromServer(uri, pd);
                    sleep(3000);
                    installApk(file);
                    pd.dismiss(); //结束掉进度条对话框
                } catch (Exception e) {
                    //下载apk失败
                    Toast.makeText(getApplicationContext(), "下载新版本失败", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }}.start();
    }
    public static File getFileFromServer(String uri, ProgressDialog pd) throws Exception{
        //如果相等的话表示当前的sdcard挂载在手机上并且是可用的
        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            URL url = new URL(uri);
            final HttpURLConnection conn =  (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5000);
            //获取到文件的大小
            pd.setMax(conn.getContentLength());
            pd.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    conn.disconnect();
                }
            });
            InputStream is = conn.getInputStream();
            File file = new File(Environment.getExternalStorageDirectory(), "updata.apk");
            FileOutputStream fos = new FileOutputStream(file);
            BufferedInputStream bis = new BufferedInputStream(is);
            byte[] buffer = new byte[1024];
            int len ;
            int total=0;
            while((len =bis.read(buffer))!=-1){
                fos.write(buffer, 0, len);
                total+= len;
                //获取当前下载量
                pd.setProgress(total);
                //pd.setProgressNumberFormat(String.format("%.2fM/%.2fM", total/1024/1024, conn.getContentLength()/1024/1024));
            }
            fos.close();
            bis.close();
            is.close();
            return file;
        }
        else{
            return null;
        }
    }
    protected void installApk(File file) {
        Intent i = new Intent(Intent.ACTION_VIEW);
        if (Build.VERSION.SDK_INT >= 24) { //适配安卓7.0
            i.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            Uri apkFileUri = FileProvider.getUriForFile(mContext.getApplicationContext(),
                    mContext.getPackageName(), file);
            i.setDataAndType(apkFileUri, "application/vnd.android.package-archive");
        } else {
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            i.setDataAndType(Uri.parse("file://" + file.toString()),
                    "application/vnd.android.package-archive");// File.toString()会返回路径信息
        }
        /*Intent intent = new Intent();
        //执行动作
        intent.setAction(Intent.ACTION_VIEW);
        //执行的数据类型
        intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
        */
        startActivity(i);
    }
    @Override
    protected void initListener() {

    }

    @Override
    protected void initView() {

        String userName = PreferencesUtils.getString(this,"UserName","");
        if(!userName.equals("")){
            checkBoxSavePassWord.setChecked(true);
            editTextUserName.setText(userName);
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void otherViewClick(View view) {

    }
    @OnClick({R.id.imageView_Login,R.id.imageView_Login_land})
    public void onViewClicked(View view) {
        switch (view.getId()){
            case R.id.imageView_Login_land:
            case R.id.imageView_Login:
                if(editTextUserName.getText().toString().isEmpty()){
                    ToastUtil.setToast("用户名不能为空");
                    return;
                }
                if (editTextPassWord.getText().toString().isEmpty()){
                    ToastUtil.setToast("密码不能为空");
                    return;
                }
                showProgressDialogWithText("登录中，请稍候...");
                TelephonyManager tm = (TelephonyManager) this.getSystemService(TELEPHONY_SERVICE);
                mPresenter.login(editTextUserName.getText().toString(),editTextPassWord.getText().toString(),tm.getDeviceId());
                break;
        }
    }


    @Override
    public void toHomePageActivity(LoginBean loginBean) {
            dismissProgressDialog();
            if(checkBoxSavePassWord.isChecked()){
                PreferencesUtils.putString(this,"UserName",editTextUserName.getText().toString());
            }else{
                PreferencesUtils.putString(this,"UserName","");
            }
            this.loginBean = loginBean;
            if(loginBean.getImgUrl()!=null){
                mPresenter.loadHead(UrlHelper.BASE_URL+loginBean.getImgUrl());

            }else{
                Message message = new Message();
                message.what = 0X112;
                myHandler.handleMessage(message);
            }



    }

    @Override
    public void loginFail(String failMsg) {
        dismissProgressDialog();
        toast(failMsg);
    }

    @Override
    public void getHeadSuccess(final byte[]bytes) {

            new Thread() {
                @Override
                public void run() {
                    super.run();
                    try {
                        pictureFile = null;
                        String publicFilePath = new StringBuilder(Environment.getExternalStorageDirectory().getAbsolutePath())
                                .append(File.separator).append("ivm").append(File.separator).toString();
                        FileUtil.mkDir(publicFilePath);
                        if (bytes != null && bytes.length > 0) {
                            FileUtil.writeByteArrayToFile(bytes, publicFilePath + "/head.jpg");
                            pictureFile = new File(publicFilePath + "/head.jpg");
                        }
                       // Log.e("imgSrc",pictureFile.getAbsolutePath()+"");
                        Message message = new Message();
                        message.what = 0X111;
                        myHandler.handleMessage(message);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }

            }.start();



    }

    @Override
    public void getHeadFail(String failMsg) {
        dismissProgressDialog();
        toast(failMsg);
    }
    @TargetApi(23)
    private void getPersimmions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ArrayList<String> permissions = new ArrayList<String>();
            /***
             * 定位权限为必须权限，用户如果禁止，则每次进入都会申请
             */
            // 定位精确位置
            if(checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
                permissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
            }
            if(checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
                permissions.add(Manifest.permission.ACCESS_COARSE_LOCATION);
            }
            // 读取电话状态权限
            if (checkSelfPermission(Manifest.permission.RECORD_AUDIO)!= PackageManager.PERMISSION_GRANTED) {
                permissions.add(Manifest.permission.RECORD_AUDIO);
            }
			/*
			 * 读写权限和电话状态权限非必要权限(建议授予)只会申请一次，用户同意或者禁止，只会弹一次
			 */
            // 读写权限
            if (addPermission(permissions, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                permissionInfo += "Manifest.permission.WRITE_EXTERNAL_STORAGE Deny \n";
            }
            // 读取电话状态权限
            if(checkSelfPermission(Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED){
                permissions.add(Manifest.permission.READ_PHONE_STATE);
            }
            if(checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
                permissions.add(Manifest.permission.CAMERA);
            }
            if (permissions.size() > 0) {
                requestPermissions(permissions.toArray(new String[permissions.size()]), SDK_PERMISSION_REQUEST);
            }
        }
    }

    @TargetApi(23)
    private boolean addPermission(ArrayList<String> permissionsList, String permission) {
        if (checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) { // 如果应用没有获得对应权限,则添加到列表中,准备批量申请
            if (shouldShowRequestPermissionRationale(permission)){
                return true;
            }else{
                permissionsList.add(permission);
                return false;
            }

        }else{
            return true;
        }
    }

    @TargetApi(23)
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        // TODO Auto-generated method stub
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

    }

    @Override
    protected void onStart() {
        super.onStart();
        getPersimmions();
        openGPSSettings();
       // RequestTakePicAndCamera();
    }
    private void openGPSSettings() {
        if(dialog != null){
            dialog.dismiss();
        }
        if (checkGPSIsOpen()) {

        } else {
            //没有打开则弹出对话框
            dialog  = new AlertDialog.Builder(this)
                    .setTitle("提示")
                    .setMessage("当前应用需要打开定位功能。\n\n请点击“设置”-“定位服务”-打开定位功能。")
                    // 拒绝, 退出应用
                    .setNegativeButton("取消",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    openGPSSettings();
                                }
                            })

                    .setPositiveButton("设置",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    //跳转GPS设置界面
                                    if (checkGPSIsOpen()) {
                                        dialog.dismiss();
                                    }else{
                                        dialog.dismiss();
                                        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                        startActivityForResult(intent, 10);
                                    }



                                }
                            })

                    .setCancelable(false)
                    .show();

        }
    }

    private boolean checkGPSIsOpen() {
        boolean isOpen;
        LocationManager locationManager = (LocationManager) this
                .getSystemService(Context.LOCATION_SERVICE);
        isOpen = locationManager.isProviderEnabled(android.location.LocationManager.GPS_PROVIDER);
        return isOpen;
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10) {
            //做需要做的事情，比如再次检测是否打开GPS了 或者定位
            if(dialog != null){
                dialog.dismiss();
            }
            openGPSSettings();
        }
    }
    //请求存储数据、录音、摄像权限
    public void RequestTakePicAndCamera() {
        //所要申请的权限
        final String[] params = {Manifest.permission.RECORD_AUDIO,Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO, Manifest.permission.CAMERA};
        final int[] requestCodes = {PermissionManager.PHONE_STATE_CODE,PermissionManager.LOCATION_CODE,PermissionManager.WRITE_EXTERNAL_STORAGE_CODE, PermissionManager.RECORD_AUDIO_CODE, PermissionManager.CAMERA_CODE};
        final boolean[] status = {false,false,false,false,false};


        new PermissionManager(LoginActivity.this).requestPermissions(params, requestCodes, new PermissionManager.PermissionRequestListener() {
            @Override
            public void permissionGranted(int code) {

                switch (code){
                    case PermissionManager.PHONE_STATE_CODE:
                        status[0]=true;
                        break;

                    case PermissionManager.LOCATION_CODE:
                        status[1]=true;
                        break;
                    case PermissionManager.WRITE_EXTERNAL_STORAGE_CODE:
                        status[2]=true;
                        break;

                    case PermissionManager.RECORD_AUDIO_CODE:
                        status[3]=true;
                        break;
                    case PermissionManager.CAMERA_CODE:
                        status[4]=true;
                        break;
                }

                if (status[0]&&status[1]&&status[2]&&status[3]&&status[4]){
                    //有权限这里继续执行
                   // startActivityForResult(new Intent(OrderReservationActivity.this, CameraActivity.class), 100);
                }

            }

            @Override
            public void permissionDenied(int code) {
                switch (code) {
                    case PermissionManager.PHONE_STATE_CODE:
                        break;

                    case PermissionManager.LOCATION_CODE:
                        break;
                    case PermissionManager.WRITE_EXTERNAL_STORAGE_CODE:

                       // ToastUtil.setToast(ProApplication.getmContext().getString(R.string.permision_denied_storage));
                        break;
                    case PermissionManager.RECORD_AUDIO_CODE:

                      //  ToastUtil.setToast(ProApplication.getmContext().getString(R.string.permision_denied_microphone));
                        break;
                    case PermissionManager.CAMERA_CODE:
                     //   ToastUtil.setToast(ProApplication.getmContext().getString(R.string.permision_denied_camera));
                        break;

                }

            }

            @Override
            public void permissionRationale(int code) {

            }
        }, new PermissionManager.PermissionPageListener() {
            @Override
            public void pageIntent(int code, final Intent intent) {


                String message="";

                switch (code){
                    case PermissionManager.PHONE_STATE_CODE:
                        break;

                    case PermissionManager.LOCATION_CODE:
                        break;
                    case PermissionManager.WRITE_EXTERNAL_STORAGE_CODE:
                    //    message = ProApplication.getmContext().getString(R.string.permision_storage);
                        break;

                    case PermissionManager.RECORD_AUDIO_CODE:
                     //   message = ProApplication.getmContext().getString(R.string.permision_microphone);
                        break;
                    case PermissionManager.CAMERA_CODE:
                     //   message = ProApplication.getmContext().getString(R.string.permision_camera);

                        break;
                }


                new AlertDialog.Builder(LoginActivity.this)
                        .setMessage(message)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                startActivity(intent);
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).setCancelable(false)
                        .show();

            }
        });


    }


}
