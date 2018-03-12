package com.example.humencheckworkattendance.ui.activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.humencheckworkattendance.Constants;
import com.example.humencheckworkattendance.ProApplication;
import com.example.humencheckworkattendance.R;
import com.example.humencheckworkattendance.base.BaseActivity;
import com.example.humencheckworkattendance.bean.CheckIsFrozenBean;
import com.example.humencheckworkattendance.bean.LoginBean;
import com.example.humencheckworkattendance.bean.UploadBean;
import com.example.humencheckworkattendance.contact.HomePageContact;
import com.example.humencheckworkattendance.presenter.HomePagePresenter;
import com.example.humencheckworkattendance.server.LocationService;
import com.example.humencheckworkattendance.utils.VersionUtils;
import com.example.humencheckworkattendance.widget.CircleImageTransform;
import com.google.gson.Gson;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import cn.cloudwalk.FaceInterface;
import cn.cloudwalk.libproject.LiveStartActivity;
import cn.cloudwalk.libproject.callback.ResultCallBack;
import cn.cloudwalk.libproject.util.FileUtil;
import cn.cloudwalk.libproject.util.StringUtil;
import cn.fisc.entity.IVMBaseEntity;
import cn.fisc.libproject.facerecognition.FaceRecognition;
import cn.fisc.utils.ImageUtils;
import okhttp3.Call;


public class HomePageActivity extends BaseActivity<HomePagePresenter> implements HomePageContact.HomePageView {
    RelativeLayout relativeLayoutInsertHumen;
    RelativeLayout relativeLayoutViewHolder;
    TextView textViewUserName;
    ImageView imageViewHistroy;
    ImageView homePageCamera;
    ImageView homePageHead;
    ImageView homePageSign;
    TextView homePageAddress;
    LinearLayout linearLayoutMap;

    @BindView(R.id.relativeLayout_Insert_Humen)
    RelativeLayout relativeLayoutInsertHumenPort;
    @BindView(R.id.relativeLayout_ViewHolder)
    RelativeLayout relativeLayoutViewHolderPort;
    @BindView(R.id.textView_UserName)
    TextView textViewUserNamePort;
    @BindView(R.id.imageView_Histroy)
    ImageView imageViewHistroyPort;
    @BindView(R.id.home_page_camera)
    ImageView homePageCameraPort;
    @BindView(R.id.home_page_head)
    ImageView homePageHeadPort;
    @BindView(R.id.home_page_sign)
    ImageView homePageSignPort;
    @BindView(R.id.home_page_addresss)
    TextView homePageAddressPort;
    @BindView(R.id.linearLayout_Map)
    LinearLayout linearLayoutMapPort;
    @BindView(R.id.textView)
    TextView mAppVersionPort;

    @BindView(R.id.relativeLayout_Insert_Humen_land)
    RelativeLayout relativeLayoutInsertHumenLand;
    @BindView(R.id.relativeLayout_ViewHolder_land)
    RelativeLayout relativeLayoutViewHolderLand;
    @BindView(R.id.textView_UserName_land)
    TextView textViewUserNameLand;
    @BindView(R.id.imageView_Histroy_land)
    ImageView imageViewHistroyLand;
    @BindView(R.id.home_page_camera_land)
    ImageView homePageCameraLand;
    @BindView(R.id.home_page_head_land)
    ImageView homePageHeadLand;
    @BindView(R.id.home_page_sign_land)
    ImageView homePageSignLand;
    @BindView(R.id.home_page_addresss_land)
    TextView homePageAddressLand;
    @BindView(R.id.linearLayout_Map_land)
    LinearLayout linearLayoutMapLand;
    @BindView(R.id.textView_land)
    TextView mAppVersionLand;

    @BindView(R.id.home_page_land)
    LinearLayout mHomePageLand;
    @BindView(R.id.home_page_port)
    LinearLayout mHomePagePort;

    public LoginBean loginBean;
    Intent intent;
    String type = "1";
    public LocationClient mLocationClient = null;
    public BDLocationListener myListener = new MyLocationListener();
    private LocationService locationService;
    private final int SDK_PERMISSION_REQUEST = 127;
    private String permissionInfo;
    public String publicFilePath;
    public static int liveCount = 5;
    public static int liveLevel = FaceInterface.LevelType.LEVEL_EASY;//检测难度  简单，经典，困难

    private String fileSrc1; //图片目录1
    private String imageOneBase64 = "";
    private String imageTwoBase64 = "";
    Double latitude;
    Double Longitude;
    private PopupWindow mPopWindow;

    @Override
    protected HomePagePresenter loadPresenter() {
        return new HomePagePresenter();
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        mkImgDir();
        mLocationClient = new LocationClient(getApplicationContext());
        mLocationClient.registerLocationListener(myListener);
        initLocation();
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initView() {
        intent = getIntent();
        loginBean = (LoginBean) intent.getSerializableExtra("userMessage");
        // Log.e("loginbean",loginBean.getProjId());
        // Log.e("loginbean",loginBean.getUserType());
        if (loginBean.getUserType().equals("worker")) {
            relativeLayoutInsertHumen.setVisibility(View.GONE);
            relativeLayoutViewHolder.setVisibility(View.VISIBLE);
        } else {
            relativeLayoutInsertHumen.setVisibility(View.VISIBLE);
            relativeLayoutViewHolder.setVisibility(View.GONE);
        }
        textViewUserName.setText(loginBean.getUserName());
        File imgSrcFile = (File) intent.getSerializableExtra("imgSrc");
        // Log.e("url",UrlHelper.BASE_URL+loginBean.getImgUrl());
        if (imgSrcFile != null && intent.getBooleanExtra("isExist", false)) {
            Glide.with(HomePageActivity.this).load(imgSrcFile)//拿到头像本地存放路径
                    .error(R.drawable.signheader)//失败默认
                    .placeholder(R.drawable.signheader)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)//不单独缓存
                    .bitmapTransform(new CircleImageTransform(HomePageActivity.this))//圆形头像显示
                    .skipMemoryCache(true)
                    .into(homePageHead);
            imageTwoBase64 = ImageUtils.getImageStr(imgSrcFile.getAbsolutePath());
        }

    }

    @Override
    protected void changeScreen() {
        boolean isShowPopWindow = false;
        if (mPopWindow != null && mPopWindow.isShowing()) {
            mPopWindow.dismiss();
            isShowPopWindow = true;
        }
        if(mScreenOrientation){
            mAppVersionLand.setText(VersionUtils.getVersionName());
        }else {
            mAppVersionPort.setText(VersionUtils.getVersionName());
        }
        relativeLayoutInsertHumen = getShowWidgetsOnScreen(relativeLayoutInsertHumenLand, relativeLayoutInsertHumenPort);
        relativeLayoutViewHolder = getShowWidgetsOnScreen(relativeLayoutViewHolderLand, relativeLayoutViewHolderPort);
        textViewUserName = getShowWidgetsOnScreen(textViewUserNameLand, textViewUserNamePort);
        imageViewHistroy = getShowWidgetsOnScreen(imageViewHistroyLand, imageViewHistroyPort);
        homePageCamera = getShowWidgetsOnScreen(homePageCameraLand, homePageCameraPort);
        homePageHead = getShowWidgetsOnScreen(homePageHeadLand, homePageHeadPort);
        homePageSign = getShowWidgetsOnScreen(homePageSignLand, homePageSignPort);
        homePageAddress = getShowWidgetsOnScreen(homePageAddressLand, homePageAddressPort);
        linearLayoutMap = getShowWidgetsOnScreen(linearLayoutMapLand, linearLayoutMapPort);

        setShowView(mHomePageLand, mHomePagePort);
        if(isShowPopWindow)
            showPopupWindow();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_home_page;
    }

    @Override
    protected void otherViewClick(View view) {

    }

    @OnClick({R.id.home_page_camera,R.id.home_page_camera_land, R.id.home_page_sign,R.id.home_page_sign_land,
            R.id.linearLayout_Map,R.id.linearLayout_Map_land, R.id.imageView_Histroy,R.id.imageView_Histroy_land,
            R.id.relativeLayout_Insert_Humen,R.id.relativeLayout_Insert_Humen_land})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.home_page_camera_land:
            case R.id.home_page_camera:
                if (StringUtil.isEmpty(imageTwoBase64)) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(HomePageActivity.this);
                    // 设置提示框的标题
                    builder.setTitle("提示：").
                            // 设置提示框的图标
                            // setIcon(R.drawable.icon).
                            // 设置要显示的信息
                                    setMessage("请联系管理人员添加底图").
                            // 设置确定按钮
                                    setPositiveButton("确定", null);

                    // 生产对话框
                    AlertDialog alertDialog = builder.create();
                    alertDialog.setCanceledOnTouchOutside(false);
                    // 显示对话框
                    alertDialog.show();
                    alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.rgb(88, 190, 252));
                    //Toast.makeText(getApplicationContext(), "请联系管理人员添加底图", Toast.LENGTH_SHORT).show();
                    return;
                }
                showProgressDialogWithText("获取权限中，请稍后...");
                mPresenter.checkIsFrozen(loginBean.getMemberId() + "", loginBean.getProjId());

                break;
            case R.id.home_page_sign_land:
            case R.id.home_page_sign:
                if (StringUtil.isEmpty(imageTwoBase64)) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(HomePageActivity.this);
                    // 设置提示框的标题
                    builder.setTitle("提示：").
                            // 设置提示框的图标
                            // setIcon(R.drawable.icon).
                            // 设置要显示的信息
                                    setMessage("请联系管理人员添加底图").
                            // 设置确定按钮
                                    setPositiveButton("确定", null);

                    // 生产对话框
                    AlertDialog alertDialog = builder.create();
                    alertDialog.setCanceledOnTouchOutside(false);
                    // 显示对话框
                    alertDialog.show();
                    alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.rgb(88, 190, 252));
                    // Toast.makeText(getApplicationContext(),"请联系管理人员添加底图",Toast.LENGTH_SHORT).show();
                    return;
                } else if (StringUtil.isEmpty(imageOneBase64)) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(HomePageActivity.this);
                    // 设置提示框的标题
                    builder.setTitle("提示：").
                            // 设置提示框的图标
                            // setIcon(R.drawable.icon).
                            // 设置要显示的信息
                                    setMessage("请先进行人脸检测再进行报到" +
                                    "" +
                                    "" +
                                    "" +
                                    "").
                            // 设置确定按钮
                                    setPositiveButton("确定", null);

                    // 生产对话框
                    AlertDialog alertDialog = builder.create();
                    alertDialog.setCanceledOnTouchOutside(false);
                    // 显示对话框
                    alertDialog.show();
                    alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.rgb(88, 190, 252));
                    //Toast.makeText(getApplicationContext(),"请先进行检测再进行比对",Toast.LENGTH_SHORT).show();
                    return;
                }
                showProgressDialogWithText("获取权限中，请稍候...");
                FaceRecognition.getInstance().startFaceMatching(Constants.RSA_KEY, Constants.PRIVATE_RSA, imageOneBase64, imageTwoBase64, new StringCallback() {


                    /*@Override
                    public void onError(Call call, Response response, Exception e, int id) {
                        Log.e("111","111");
                        dismissProgressDialog();
                        Toast.makeText(HomePageActivity.this,"网络连接异常，请稍后！",Toast.LENGTH_SHORT).show();
                    }*/


                    @Override
                    public void onError(Call call, Exception e, int i) {
                        // Log.e("111","111");
                        dismissProgressDialog();
                        AlertDialog.Builder builder = new AlertDialog.Builder(HomePageActivity.this);
                        // 设置提示框的标题
                        builder.setTitle("提示：").
                                // 设置提示框的图标
                                // setIcon(R.drawable.icon).
                                // 设置要显示的信息
                                        setMessage("网络连接异常，请稍候！").
                                // 设置确定按钮
                                        setPositiveButton("确定", null);

                        // 生产对话框
                        AlertDialog alertDialog = builder.create();
                        alertDialog.setCanceledOnTouchOutside(false);
                        // 显示对话框
                        alertDialog.show();
                        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.rgb(88, 190, 252));
                        //Toast.makeText(HomePageActivity.this,"网络连接异常，请稍后！",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(String s, int i) {
                        Gson gson = new Gson();
                        IVMBaseEntity entity = gson.fromJson(s, IVMBaseEntity.class);
                        if (entity != null) {
                            if (entity.getResultCd() == 0) {
                                try {
                                    String str = gson.toJson(entity.getRes());
                                    JSONObject json = new JSONObject(str);
                                    double rate = json.getDouble("rate");
                                    //Toast.makeText(getApplicationContext(),"比对相似度为"+save2Num((double)rate*100.0)+"%",Toast.LENGTH_SHORT).show();
                                    // Log.e("log",rate+"");
                                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                    Date curDate = new Date(System.currentTimeMillis());
                                    String time = formatter.format(curDate);
                                    if (rate >= 0.6) {
                                        type = "1";

                                        //mPresenter.playCard(time,loginBean.getMemberId()+"",homePageAddress.getText().toString(),type,loginBean.getProjId(),loginBean.getEmtpId(),loginBean.getEmtpRolesId(),Longitude+"",latitude+"");

                                    } else {
                                        type = "0";
                                        //mPresenter.playCard(time,loginBean.getMemberId()+"",homePageAddress.getText().toString(),type,loginBean.getProjId(),loginBean.getEmtpId(),loginBean.getEmtpRolesId(),Longitude+"",latitude+"");

                                    }
                                    JSONObject param = new JSONObject();
                                    try {
                                        param.put("pic", imageOneBase64);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    mPresenter.uploadImage64(param.toString());
                                    //tvInfo.setText("比对相似度为"+save2Num((double)rate*100.0)+"%");
                                } catch (JSONException e) {

                                    e.printStackTrace();
                                }


                            } else {
                                dismissProgressDialog();
                                AlertDialog.Builder builder = new AlertDialog.Builder(HomePageActivity.this);
                                // 设置提示框的标题
                                builder.setTitle("提示：").
                                        // 设置提示框的图标
                                        // setIcon(R.drawable.icon).
                                        // 设置要显示的信息
                                                setMessage("底图采集不符合标准，请联系管理员！").
                                        // 设置确定按钮
                                                setPositiveButton("确定", null);

                                // 生产对话框
                                AlertDialog alertDialog = builder.create();
                                alertDialog.setCanceledOnTouchOutside(false);
                                // 显示对话框
                                alertDialog.show();
                                alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.rgb(88, 190, 252));
                                //Toast.makeText(HomePageActivity.this,"底图采集不符合标准，请联系管理员！",Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            dismissProgressDialog();
                            AlertDialog.Builder builder = new AlertDialog.Builder(HomePageActivity.this);
                            // 设置提示框的标题
                            builder.setTitle("提示：").
                                    // 设置提示框的图标
                                    // setIcon(R.drawable.icon).
                                    // 设置要显示的信息
                                            setMessage("网络连接异常，请稍候！").
                                    // 设置确定按钮
                                            setPositiveButton("确定", null);

                            // 生产对话框
                            AlertDialog alertDialog = builder.create();
                            alertDialog.setCanceledOnTouchOutside(false);
                            // 显示对话框
                            alertDialog.show();
                            alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.rgb(88, 190, 252));
                            // Toast.makeText(HomePageActivity.this,"网络连接异常，请稍后！",Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                break;
            case R.id.linearLayout_Map:
            case R.id.linearLayout_Map_land:
                Intent intentToMap = new Intent(HomePageActivity.this, MapActivity.class);
                intentToMap.putExtra("latitude", latitude);
                intentToMap.putExtra("Longitude", Longitude);
                startActivity(intentToMap);
                break;
            case R.id.imageView_Histroy_land:
            case R.id.imageView_Histroy:
                showPopupWindow();
                break;
            case R.id.relativeLayout_Insert_Humen:
            case R.id.relativeLayout_Insert_Humen_land:
                intent.setClass(HomePageActivity.this, InsertHumenActivity.class);
                startActivity(intent);
                break;

            default:
                break;
        }
    }

    private ArrayList<Integer> getLiveList() {
        ArrayList<Integer> liveList = new ArrayList<Integer>();
        liveList.add(FaceInterface.LivessType.LIVESS_MOUTH);
        liveList.add(FaceInterface.LivessType.LIVESS_HEAD_UP);
        liveList.add(FaceInterface.LivessType.LIVESS_HEAD_DOWN);
        liveList.add(FaceInterface.LivessType.LIVESS_HEAD_LEFT);
        liveList.add(FaceInterface.LivessType.LIVESS_HEAD_RIGHT);
        liveList.add(FaceInterface.LivessType.LIVESS_EYE);
        return liveList;
    }

    public static String save2Num(double d) {
        DecimalFormat df = new DecimalFormat("#0.00");
        String st = df.format(d);
        return st;
    }

    ResultCallBack resultCallBack = new ResultCallBack() {
        @Override
        public void result(boolean isLivePass, boolean isVerfyPass, String faceSessionId, double face_score, int resultType, byte[] bestFace, HashMap liveDatas, int cameraId) {


            //count++;
            // 存储最佳人脸图片
            File pictureFile = null;
            if (bestFace != null && bestFace.length > 0) {
                FileUtil.writeByteArrayToFile(bestFace, publicFilePath + "/" + "bestface.jpg");
                try {
                    pictureFile = new File(publicFilePath + "/" + "bestface.jpg");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            // 存储活体证据图片
            if (liveDatas != null && liveDatas.size() > 0) {
                Iterator iter = liveDatas.entrySet().iterator();
                while (iter.hasNext()) {
                    Map.Entry<Integer, byte[]> entry = (Map.Entry<Integer, byte[]>) iter.next();
                    int key = entry.getKey();
                    byte[] val = entry.getValue();
                    String saveName = null;
                    switch (key) {
                        case FaceInterface.CW_LivenessCode.CW_FACE_LIVENESS_HEADLEFT:
                            saveName = "/" + "headleft.jpg";
                            break;
                        case FaceInterface.CW_LivenessCode.CW_FACE_LIVENESS_HEADRIGHT://
                            saveName = "/" + "headright.jpg";
                            break;

                        case FaceInterface.CW_LivenessCode.CW_FACE_LIVENESS_HEADPITCH://
                            saveName = "/" + "headup.jpg";
                            break;
                        case FaceInterface.CW_LivenessCode.CW_FACE_LIVENESS_HEADDOWN://
                            saveName = "/" + "headdown.jpg";
                            break;
                        case FaceInterface.CW_LivenessCode.CW_FACE_LIVENESS_OPENMOUTH://
                            saveName = "/" + "mouth.jpg";
                            break;
                        case FaceInterface.CW_LivenessCode.CW_FACE_LIVENESS_BLINK://
                            saveName = "/" + "eye.jpg";
                            break;
                    }
                    FileUtil.writeByteArrayToFile(val, publicFilePath + saveName);
                }
            }
            if (null == pictureFile) {
                return;
            }
            //对图片进行镜像处理
            ImageUtils.getImageMatrix(cameraId, pictureFile);
            // 图片文件转base 64
            imageOneBase64 = ImageUtils.getImageStr(pictureFile.getAbsolutePath());
            fileSrc1 = pictureFile.getAbsolutePath();
            //base 64转为图片
//            ImageUtils.generateImage(strImg, pictureFile.getAbsolutePath());
            Glide.with(HomePageActivity.this).load(pictureFile)//拿到头像本地存放路径
                    .error(R.drawable.ic_default)//失败默认
                    .placeholder(R.drawable.ic_default)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)//不单独缓存
                    .bitmapTransform(new CircleImageTransform(HomePageActivity.this))//圆形头像显示
                    .skipMemoryCache(true)
                    .into(homePageCamera);
            showProgressDialogWithText("正在报到中，请稍候...");
            FaceRecognition.getInstance().startFaceMatching(Constants.RSA_KEY, Constants.PRIVATE_RSA, imageOneBase64, imageTwoBase64, new StringCallback() {


                    /*@Override
                    public void onError(Call call, Response response, Exception e, int id) {
                        Log.e("111","111");
                        dismissProgressDialog();
                        Toast.makeText(HomePageActivity.this,"网络连接异常，请稍后！",Toast.LENGTH_SHORT).show();
                    }*/


                @Override
                public void onError(Call call, Exception e, int i) {
                    Log.e("111", "111");
                    dismissProgressDialog();
                    AlertDialog.Builder builder = new AlertDialog.Builder(HomePageActivity.this);
                    // 设置提示框的标题
                    builder.setTitle("提示：").
                            // 设置提示框的图标
                            // setIcon(R.drawable.icon).
                            // 设置要显示的信息
                                    setMessage("网络连接异常，请稍候！").
                            // 设置确定按钮
                                    setPositiveButton("确定", null);

                    // 生产对话框
                    AlertDialog alertDialog = builder.create();
                    alertDialog.setCanceledOnTouchOutside(false);
                    // 显示对话框
                    alertDialog.show();
                    alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.rgb(88, 190, 252));
                    // Toast.makeText(HomePageActivity.this,"网络连接异常，请稍后！",Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onResponse(String s, int i) {
                    Gson gson = new Gson();
                    IVMBaseEntity entity = gson.fromJson(s, IVMBaseEntity.class);
                    if (entity != null) {
                        if (entity.getResultCd() == 0) {
                            try {
                                String str = gson.toJson(entity.getRes());
                                JSONObject json = new JSONObject(str);
                                double rate = json.getDouble("rate");
                                //Toast.makeText(getApplicationContext(),"比对相似度为"+save2Num((double)rate*100.0)+"%",Toast.LENGTH_SHORT).show();
                                // Log.e("log",rate+"");

                                if (rate >= 0.6) {
                                    type = "1";


                                } else {
                                    type = "0";
                                    // mPresenter.playCard(time,loginBean.getMemberId()+"",homePageAddress.getText().toString(),type,loginBean.getProjId(),loginBean.getEmtpId(),loginBean.getEmtpRolesId(),Longitude+"",latitude+"");

                                }
                                JSONObject param = new JSONObject();
                                try {
                                    param.put("pic", imageOneBase64);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                mPresenter.uploadImage64(param.toString());
                                //tvInfo.setText("比对相似度为"+save2Num((double)rate*100.0)+"%");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                        } else {
                            //Log.e("no face",entity.getMsg());
                            dismissProgressDialog();
                            AlertDialog.Builder builder = new AlertDialog.Builder(HomePageActivity.this);
                            // 设置提示框的标题
                            builder.setTitle("提示：").
                                    // 设置提示框的图标
                                    // setIcon(R.drawable.icon).
                                    // 设置要显示的信息
                                            setMessage("底图采集不符合标准，请联系管理员！").
                                    // 设置确定按钮
                                            setPositiveButton("确定", null);

                            // 生产对话框
                            AlertDialog alertDialog = builder.create();
                            alertDialog.setCanceledOnTouchOutside(false);
                            // 显示对话框
                            alertDialog.show();
                            alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.rgb(88, 190, 252));
                            // Toast.makeText(HomePageActivity.this,"底图采集不符合标准，请联系管理员！",Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        dismissProgressDialog();
                        AlertDialog.Builder builder = new AlertDialog.Builder(HomePageActivity.this);
                        // 设置提示框的标题
                        builder.setTitle("提示：").
                                // 设置提示框的图标
                                // setIcon(R.drawable.icon).
                                // 设置要显示的信息
                                        setMessage("网络连接异常，请稍候！").
                                // 设置确定按钮
                                        setPositiveButton("确定", null);

                        // 生产对话框
                        AlertDialog alertDialog = builder.create();
                        alertDialog.setCanceledOnTouchOutside(false);
                        // 显示对话框
                        alertDialog.show();
                        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.rgb(88, 190, 252));
                        // Toast.makeText(HomePageActivity.this,"网络连接异常，请稍后！",Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    };

    private void mkImgDir() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyMMddHHmm");
        publicFilePath = new StringBuilder(Environment.getExternalStorageDirectory().getAbsolutePath())
                .append(File.separator).append("ivm").append(File.separator).toString();
        FileUtil.mkDir(publicFilePath);
    }

    @Override
    public void playCardSuccess(String s) {
        //Log.e("s",s);
        dismissProgressDialog();
        if (type.equals("1")) {
            imageOneBase64 = "";
            homePageCamera.setImageResource(R.drawable.signcamera);
            AlertDialog.Builder builder = new AlertDialog.Builder(HomePageActivity.this);
            // 设置提示框的标题
            builder.setTitle("提示：").
                    // 设置提示框的图标
                    // setIcon(R.drawable.icon).
                    // 设置要显示的信息
                            setMessage(s).
                    // 设置确定按钮
                            setPositiveButton("确定", null);

            // 生产对话框
            AlertDialog alertDialog = builder.create();
            alertDialog.setCanceledOnTouchOutside(false);
            // 显示对话框
            alertDialog.show();
            alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.rgb(88, 190, 252));
            //ToastUtil.setToast("对比通过，报道成功！");
        } else {
            imageOneBase64 = "";
            homePageCamera.setImageResource(R.drawable.signcamera);
            AlertDialog.Builder builder = new AlertDialog.Builder(HomePageActivity.this);
            // 设置提示框的标题
            builder.setTitle("提示：").
                    // 设置提示框的图标
                    // setIcon(R.drawable.icon).
                    // 设置要显示的信息
                            setMessage(s).
                    // 设置确定按钮
                            setPositiveButton("确定", null);

            // 生产对话框
            AlertDialog alertDialog = builder.create();
            alertDialog.setCanceledOnTouchOutside(false);
            // 显示对话框
            alertDialog.show();
            alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.rgb(88, 190, 252));
            //ToastUtil.setToast("对比不通过，报道失败！");
        }
    }

    private void showPopupWindow() {
        backgroundAlpha(0.5f);
        View contentView = LayoutInflater.from(HomePageActivity.this).inflate(R.layout.view_popupwindow_more, null);
        mPopWindow = new PopupWindow(contentView);
        mPopWindow.setWidth(ViewGroup.LayoutParams.FILL_PARENT);
        mPopWindow.setHeight(ViewGroup.LayoutParams.FILL_PARENT);
        mPopWindow.setOutsideTouchable(true);
        mPopWindow.setFocusable(true);
        mPopWindow.setBackgroundDrawable(new BitmapDrawable());
        RelativeLayout RelativeLayoutPopupWindow = (RelativeLayout) contentView.findViewById(R.id.RelativeLayout_PopupWindow);
        RelativeLayout RelativeLayoutPopupWindowChangePassWord = (RelativeLayout) contentView.findViewById(R.id.RelativeLayout_PopupWindow_ChangePassWord);
        RelativeLayout RelativeLayoutPopupWindowSelectHistroy = (RelativeLayout) contentView.findViewById(R.id.RelativeLayout_PopupWindow_SelectHistroy);
        RelativeLayout RelativeLayoutPopupWindowMore = (RelativeLayout) contentView.findViewById(R.id.RelativeLayout_PopupWindow_More);
        RelativeLayoutPopupWindow.setOnClickListener(this);
        RelativeLayoutPopupWindowChangePassWord.setOnClickListener(this);
        RelativeLayoutPopupWindowSelectHistroy.setOnClickListener(this);
        RelativeLayoutPopupWindowMore.setOnClickListener(this);

        if (Build.VERSION.SDK_INT < 24) {
            mPopWindow.showAsDropDown(imageViewHistroy);
        } else {
            int[] location = new int[2];
            imageViewHistroy.getLocationOnScreen(location);
            int x = location[0];
            int y = location[1];
            if (Build.VERSION.SDK_INT == 25) {
                WindowManager wm = (WindowManager) mPopWindow.getContentView().getContext().getSystemService(Context.WINDOW_SERVICE);
                int screenHeight = wm.getDefaultDisplay().getHeight();
                mPopWindow.setHeight(screenHeight - location[1] - imageViewHistroy.getHeight());
            }
            mPopWindow.showAtLocation(imageViewHistroy, Gravity.NO_GRAVITY, 0, y + imageViewHistroy.getHeight());
        }

        mPopWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                backgroundAlpha(1f);
            }
        });

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.RelativeLayout_PopupWindow:
                mPopWindow.dismiss();
                backgroundAlpha(1f);
                break;
            case R.id.RelativeLayout_PopupWindow_ChangePassWord:
                mPopWindow.dismiss();
                backgroundAlpha(1f);
                intent.setClass(HomePageActivity.this, ChangePassWordActivity.class);
                startActivityForResult(intent, 0X111);
                break;
            case R.id.RelativeLayout_PopupWindow_SelectHistroy:
                mPopWindow.dismiss();
                backgroundAlpha(1f);
                intent.setClass(HomePageActivity.this, SignInDetailsActivity.class);
                startActivity(intent);
                break;
            case R.id.RelativeLayout_PopupWindow_More:
                mPopWindow.dismiss();
                backgroundAlpha(1f);
                intent.setClass(HomePageActivity.this, MoreActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        getWindow().setAttributes(lp);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
    }

    @Override
    public void playCardFail(String s) {
        dismissProgressDialog();
        imageOneBase64 = "";
        homePageCamera.setImageResource(R.drawable.signcamera);
        AlertDialog.Builder builder = new AlertDialog.Builder(HomePageActivity.this);
        // 设置提示框的标题
        builder.setTitle("提示：").
                // 设置提示框的图标
                // setIcon(R.drawable.icon).
                // 设置要显示的信息
                        setMessage(s).
                // 设置确定按钮
                        setPositiveButton("确定", null);

        // 生产对话框
        AlertDialog alertDialog = builder.create();
        alertDialog.setCanceledOnTouchOutside(false);
        // 显示对话框
        alertDialog.show();
        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.rgb(88, 190, 252));
        //ToastUtil.setToast(s);
    }

    @Override
    public void toUploadImageSuccess(UploadBean uploadBean) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date curDate = new Date(System.currentTimeMillis());
        String time = formatter.format(curDate);
        //Log.e("11111111111111","111111111111111");
        mPresenter.playCard(time, loginBean.getMemberId() + "", homePageAddress.getText().toString(), type, loginBean.getProjId(), loginBean.getEmtpId(), loginBean.getEmtpRolesId(), Longitude + "", latitude + "", uploadBean.getPicUrl(),loginBean.getMemberId() + "", loginBean.getUserName());
    }

    @Override
    public void toUploadImageFail(String failMsg) {
        dismissProgressDialog();
        AlertDialog.Builder builder = new AlertDialog.Builder(HomePageActivity.this);
        // 设置提示框的标题
        builder.setTitle("提示：").
                // 设置提示框的图标
                // setIcon(R.drawable.icon).
                // 设置要显示的信息
                        setMessage("网络异常，请稍候！").
                // 设置确定按钮
                        setPositiveButton("确定", null);

        // 生产对话框
        AlertDialog alertDialog = builder.create();
        alertDialog.setCanceledOnTouchOutside(false);
        // 显示对话框
        alertDialog.show();
        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.rgb(88, 190, 252));
        //ToastUtil.setToast("网络异常，请稍后！");
    }

    @Override
    public void checkIsFrozenSuccess(CheckIsFrozenBean checkIsFrozenBean) {
        dismissProgressDialog();
        AlertDialog.Builder builder = new AlertDialog.Builder(HomePageActivity.this);
        AlertDialog alertDialog;
        switch (checkIsFrozenBean.getStatus()){
            case "0":
                builder.setTitle("提示：").

                        setMessage(checkIsFrozenBean.getMessage()).
                        setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Bundle bundle = new Bundle();
                                bundle.putInt("getMemberId", loginBean.getMemberId());
                                bundle.putString("getProjId", loginBean.getProjId());
                                bundle.putString("getImgUrl", loginBean.getImgUrl());
                                FaceRecognition.getInstance().identifyFaceLiveSDK(Constants.RSA_KEY, Constants.PRIVATE_RSA, HomePageActivity.this, LiveStartActivity.class, liveCount, liveLevel, resultCallBack, getLiveList(), bundle, 3);
                            }
                        });
                alertDialog = builder.create();
                alertDialog.setCanceledOnTouchOutside(false);
                alertDialog.show();
                alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.rgb(88, 190, 252));
                break;
            case "1":
            case "2":
                builder.setTitle("提示：").

                        setMessage(checkIsFrozenBean.getMessages()).
                        setPositiveButton("确定", null);
                alertDialog = builder.create();
                alertDialog.setCanceledOnTouchOutside(false);
                alertDialog.show();
                alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.rgb(88, 190, 252));
                break;
            case "3":
                Bundle bundle = new Bundle();
                bundle.putInt("getMemberId", loginBean.getMemberId());
                bundle.putString("getProjId", loginBean.getProjId());
                bundle.putString("getImgUrl", loginBean.getImgUrl());
                FaceRecognition.getInstance().identifyFaceLiveSDK(Constants.RSA_KEY, Constants.PRIVATE_RSA, HomePageActivity.this, LiveStartActivity.class, liveCount, liveLevel, resultCallBack, getLiveList(), bundle, 3);
                break;
            default:
                break;
        }


    }

    @Override
    public void checkIsFrozenFail(String failMsg) {
        dismissProgressDialog();
        AlertDialog.Builder builder = new AlertDialog.Builder(HomePageActivity.this);
        // 设置提示框的标题
        builder.setTitle("提示：").
                // 设置提示框的图标
                // setIcon(R.drawable.icon).
                // 设置要显示的信息
                        setMessage(failMsg).
                // 设置确定按钮
                        setPositiveButton("确定", null);

        // 生产对话框
        AlertDialog alertDialog = builder.create();
        alertDialog.setCanceledOnTouchOutside(false);
        // 显示对话框
        alertDialog.show();
        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.rgb(88, 190, 252));
    }

    public class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(final BDLocation location) {
            Log.e("log", location.getLatitude() + "");
            Log.e("log", location.getLongitude() + "");
            Log.e("log", location.getAddress().address + "");
            Log.e("log", location.getLocType() + "");
            latitude = location.getLatitude();
            Longitude = location.getLongitude();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    homePageAddress.setText(location.getAddress().address + "");
                }
            });
        }


    }

    private void initLocation() {
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        //可选，默认高精度，设置定位模式，高精度，低功耗，仅设备

        option.setCoorType("bd09ll");
        //可选，默认gcj02，设置返回的定位结果坐标系

        int span = 10000;
        option.setScanSpan(span);
        //可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的

        option.setIsNeedAddress(true);
        //可选，设置是否需要地址信息，默认不需要

        option.setOpenGps(true);
        //可选，默认false,设置是否使用gps

        option.setLocationNotify(true);
        //可选，默认false，设置是否当GPS有效时按照1S/1次频率输出GPS结果

        option.setIsNeedLocationDescribe(true);
        //可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”

        option.setIsNeedLocationPoiList(true);
        //可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到

        option.setIgnoreKillProcess(false);
        //可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死

        option.SetIgnoreCacheException(false);
        //可选，默认false，设置是否收集CRASH信息，默认收集

        option.setEnableSimulateGps(false);
        //可选，默认false，设置是否需要过滤GPS仿真结果，默认需要
        mLocationClient.setLocOption(option);
    }

    @TargetApi(23)
    private void getPersimmions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ArrayList<String> permissions = new ArrayList<String>();
            /***
             * 定位权限为必须权限，用户如果禁止，则每次进入都会申请
             */
            // 定位精确位置
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                permissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
            }
            if (checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                permissions.add(Manifest.permission.ACCESS_COARSE_LOCATION);
            }
            // 读取电话状态权限
            if (checkSelfPermission(Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
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
            if (addPermission(permissions, Manifest.permission.READ_PHONE_STATE)) {
                permissionInfo += "Manifest.permission.READ_PHONE_STATE Deny \n";
            }
            if (addPermission(permissions, Manifest.permission.CAMERA)) {
                permissionInfo += "Manifest.permission.READ_PHONE_STATE Deny \n";
            }
            if (permissions.size() > 0) {
                requestPermissions(permissions.toArray(new String[permissions.size()]), SDK_PERMISSION_REQUEST);
            }
        }
    }

    @TargetApi(23)
    private boolean addPermission(ArrayList<String> permissionsList, String permission) {
        if (checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) { // 如果应用没有获得对应权限,则添加到列表中,准备批量申请
            if (shouldShowRequestPermissionRationale(permission)) {
                return true;
            } else {
                permissionsList.add(permission);
                return false;
            }

        } else {
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
        locationService = ((ProApplication) getApplication()).locationService;
        locationService.registerListener(myListener);
        //开启定位
        if (!mLocationClient.isStarted()) {
            mLocationClient.start();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //Log.e("resultCode",resultCode+"");
        switch (resultCode) {
            case 1:
                Intent intent = new Intent(HomePageActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
                break;

            default:
                break;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        dismissProgressDialog();
        //mPopWindow.dismiss();
    }

    @Override
    protected void onDestroy() {
        locationService.unregisterListener(myListener); //注销掉监听
        locationService.stop();
        mLocationClient.stop();
        super.onDestroy();

    }
}
