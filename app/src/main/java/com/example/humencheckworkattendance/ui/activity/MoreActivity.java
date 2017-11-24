package com.example.humencheckworkattendance.ui.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Looper;
import android.support.v4.content.FileProvider;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.humencheckworkattendance.R;
import com.example.humencheckworkattendance.base.BaseActivity;
import com.example.humencheckworkattendance.bean.LoginBean;
import com.example.humencheckworkattendance.bean.UpdateBean;
import com.example.humencheckworkattendance.contact.MoreContact;
import com.example.humencheckworkattendance.presenter.MorePresenter;
import com.vector.update_app.utils.AppUpdateUtils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import butterknife.BindView;
import butterknife.OnClick;

public class MoreActivity extends BaseActivity<MorePresenter> implements MoreContact.MoreView{
    Intent intent;
    LoginBean loginBean;
    UpdateBean updateBean;
    @BindView(R.id.textview_NowVersion)
    TextView textviewNowVersion;
    @BindView(R.id.textview_NewVersion)
    TextView textviewNewVersion;
    @BindView(R.id.imageView_Update_Btn)
    ImageView imageViewUpdateBtn;
    @BindView(R.id.imageView_Back)
    ImageView imageViewBack;
    @Override
    protected MorePresenter loadPresenter() {
        return new MorePresenter();
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initView() {
        intent = getIntent();
        updateBean = (UpdateBean)intent.getSerializableExtra("updateBean");
        textviewNowVersion.setText("当前版本："+AppUpdateUtils.getVersionName(this)+"");
        if(updateBean ==null){
            textviewNewVersion.setText("最新版本："+AppUpdateUtils.getVersionName(this)+"");
        }else{
            textviewNewVersion.setText("最新版本："+updateBean.getNew_user_version());
        }

        if(intent.getBooleanExtra("isUpdate",false)){
            imageViewUpdateBtn.setImageResource(R.drawable.updateneedbtn);
        }else{
            imageViewUpdateBtn.setImageResource(R.drawable.updatedontneedbtn);
        }
    }
    @OnClick({R.id.imageView_Update_Btn,R.id.imageView_Back})
    public void onViewClicked(View view) {
        switch (view.getId()){
            case R.id.imageView_Update_Btn:
                if(intent.getBooleanExtra("isUpdate",false)){
                    loadNewVersionProgress();
                }else{
                }
                break;
            case R.id.imageView_Back:
                finish();
                break;
            default:
                break;
        }
    }
    @Override
    protected int getLayoutId() {
        return R.layout.activity_more;
    }

    @Override
    protected void otherViewClick(View view) {

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
            HttpURLConnection conn =  (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5000);
            //获取到文件的大小
            pd.setMax(conn.getContentLength());
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
}
