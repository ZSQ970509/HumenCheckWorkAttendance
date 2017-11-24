package com.example.humencheckworkattendance;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.humencheckworkattendance.utils.SystemCallUtil;
import com.google.gson.Gson;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import cn.cloudwalk.libproject.util.StringUtil;
import cn.fisc.entity.IVMBaseEntity;
import cn.fisc.libproject.facerecognition.FaceRecognition;
import cn.fisc.utils.ImageUtils;
import okhttp3.Call;

/**
 * 1:1人像图片比对
 * Created by Administrator on 2017/6/14.
 */

public class FaceMatchingDemoActivity extends Activity implements View.OnClickListener{
    public String publicFilePath;

    private Button btnIdentify; //人脸比对按钮

    private TextView tvInfo; //比对结果
    private ImageView ivAdd1; //比对图片1
    private ImageView ivAdd2; //比对图片2

    private static String imageOneBase64;
    private static String imageTwoBase64;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_face_matching_demo);
        //初始化数据
        initData();


    }
    private void initData(){
        btnIdentify = (Button) findViewById(R.id.btn_identify);
        tvInfo = (TextView) findViewById(R.id.tv_info);
        ivAdd1 = (ImageView) findViewById(R.id.iv_add1);
        ivAdd2 = (ImageView) findViewById(R.id.iv_add2);
        ivAdd1.setOnClickListener(this);
        ivAdd2.setOnClickListener(this);
        btnIdentify.setOnClickListener(this);

    }


    private static int type=-1;

    @Override
    public void onClick(View view) {
        int viewId = view.getId();
        //人脸比对
        if (viewId == R.id.btn_identify){
            if (StringUtil.isEmpty(imageOneBase64)|| StringUtil.isEmpty(imageTwoBase64)){
                Toast.makeText(getApplicationContext(),"请添加2张图片再进行比对",Toast.LENGTH_SHORT).show();
                return;
            }


            FaceRecognition.getInstance().startFaceMatching(Constants.RSA_KEY, Constants.PRIVATE_RSA, imageOneBase64, imageTwoBase64, new StringCallback() {
                @Override
                public void onError(Call call, Exception e, int i) {
                    Toast.makeText(FaceMatchingDemoActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onResponse(String s, int i) {
                    Gson gson=new Gson();
                    IVMBaseEntity entity = gson.fromJson(s, IVMBaseEntity.class);
                    if(entity!=null){
                        if(entity.getResultCd()==0){
                            try {
                                String str=gson.toJson(entity.getRes());
                                JSONObject json=new JSONObject(str);
                                double rate=json.getDouble("rate");
                                tvInfo.setText("比对相似度为"+save2Num((double)rate*100.0)+"%");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                        }else{
                            Toast.makeText(FaceMatchingDemoActivity.this,entity.getMsg(),Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(FaceMatchingDemoActivity.this,"比对失败",Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }else if (viewId == R.id.iv_add1){
            type=0;
            SystemCallUtil.showSelect(FaceMatchingDemoActivity.this, Constants.SDCARD_ANDROID_TEMP);

        }else if (viewId == R.id.iv_add2){
            type=1;
            SystemCallUtil.showSelect(FaceMatchingDemoActivity.this, Constants.SDCARD_ANDROID_TEMP);
        }
    }

    private String  imgPath(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyMMddHHmm");
        String path = new StringBuilder(Environment.getExternalStorageDirectory().getAbsolutePath())
                .append(File.separator).append("cloudwalk").append(File.separator).append(sdf.format(new Date()))
                .toString();
        return path;
    }
    public static String save2Num(double d){
        DecimalFormat df=new DecimalFormat("#0.00");
        String st=df.format(d);
        return st;
    }

    public static String getRealFilePath(final Context context, final Uri uri ) {
        if ( null == uri ) return null;
        final String scheme = uri.getScheme();
        String data = null;
        if ( scheme == null )
            data = uri.getPath();
        else if ( ContentResolver.SCHEME_FILE.equals( scheme ) ) {
            data = uri.getPath();
        } else if ( ContentResolver.SCHEME_CONTENT.equals( scheme ) ) {
            Cursor cursor = context.getContentResolver().query( uri, new String[] { MediaStore.Images.ImageColumns.DATA }, null, null, null );
            if ( null != cursor ) {
                if ( cursor.moveToFirst() ) {
                    int index = cursor.getColumnIndex( MediaStore.Images.ImageColumns.DATA );
                    if ( index > -1 ) {
                        data = cursor.getString( index );
                    }
                }
                cursor.close();
            }
        }
        return data;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == SystemCallUtil.REQUESTCODE_PHOTOALBUM && resultCode == RESULT_OK) {
            Uri uri = data.getData();
//            SystemCallUtil.ImageCut(this, uri, 314, 372);

            String filesPic=getRealFilePath(this,uri);
            Bitmap img = ImageUtils.decodeSampledBitmapFromResource(filesPic,0,0);

            if(type==0){
                ivAdd1.setImageBitmap(img);
                imageOneBase64 =  ImageUtils.getImageStr(filesPic);
            }else if(type==1){
                ivAdd2.setImageBitmap(img);
                imageTwoBase64 =  ImageUtils.getImageStr(filesPic);
            }
            tvInfo.setText("");


        } else if (requestCode == SystemCallUtil.REQUESTCODE_CAMERA && resultCode == RESULT_OK) {
            //对图片进行镜像处理
            Uri uri =SystemCallUtil.IMAGE_URI;
            String filesPic=getRealFilePath(this, uri);
            File pictureFile=new File(filesPic);
            ImageUtils.getImageMatrix(0,pictureFile);
            Bitmap img = ImageUtils.decodeSampledBitmapFromResource(filesPic,0,0);
            if(type==0){
                ivAdd1.setImageBitmap(img);
                imageOneBase64 =  ImageUtils.getImageStr(filesPic);
            }else if(type==1){
                ivAdd2.setImageBitmap(img);
                imageTwoBase64 =  ImageUtils.getImageStr(filesPic);
            }
            tvInfo.setText("");

//            SystemCallUtil.ImageCut(FaceMatchingDemoActivity.this, SystemCallUtil.IMAGE_URI,
//                    314, 372);


        } else if (requestCode == SystemCallUtil.REQUESTCODE_IMAGECUT && resultCode == RESULT_OK) {
            final Bitmap bitmap = data.getParcelableExtra("data");




        }
    }

}
