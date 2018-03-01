package com.example.humencheckworkattendance.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.humencheckworkattendance.R;
import com.example.humencheckworkattendance.base.BaseActivity;
import com.example.humencheckworkattendance.bean.IdCardBean;
import com.example.humencheckworkattendance.bean.UploadBean;
import com.example.humencheckworkattendance.contact.ShowPhotoContact;
import com.example.humencheckworkattendance.presenter.ShowPhotoPresenter;
import com.example.humencheckworkattendance.utils.ImageUtil;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.OnClick;
import uk.co.senab.photoview.PhotoView;

public class ShowPhotoActivity extends BaseActivity<ShowPhotoPresenter> implements ShowPhotoContact.ShowPhotoView{
    @BindView(R.id.img_camera_photo)
    PhotoView imgCameraPhoto;
    @BindView(R.id.imageView_Upload)
    ImageView imageViewUpload;
    @BindView(R.id.imageView_Back)
    ImageView imageViewBack;
    String imageBase64;
    Intent intent;
    @Override
    protected ShowPhotoPresenter loadPresenter() {
        return new ShowPhotoPresenter();
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
        Glide.with(ShowPhotoActivity.this).load(intent.getStringExtra("imgSrc"))//拿到头像本地存放路径
                .error(R.drawable.signheader)//失败默认
                .placeholder(R.drawable.signheader)
                .diskCacheStrategy(DiskCacheStrategy.NONE)//不单独缓存
                .skipMemoryCache(true)
                .into(imgCameraPhoto);
        imageBase64 = ImageUtil.convertIconToStringJPGE(ImageUtil.getLoacalBitmap(intent.getStringExtra("imgSrc")));
        //imageBase64 = ImageUtils.getImageStr(Environment.getExternalStorageDirectory() + "/surfingscene/shotDir/idnum.jpg");
       // Log.e("imageBase64",imageBase64);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_show_photo;
    }

    @Override
    protected void otherViewClick(View view) {

    }
    @OnClick({R.id.imageView_Upload,R.id.imageView_Back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imageView_Upload:
                showProgressDialogWithText("正在上传图片中");
                JSONObject param = new JSONObject();
                try {
                    param.put("pic", imageBase64);

                if(intent.getBooleanExtra("isIdCard",false)){
                    param.put("idcard", "true");
                    mPresenter.uploadImage64IdCard(param.toString());
                }else{
                    mPresenter.uploadImage64(param.toString());
                }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.imageView_Back:
                finish();
                break;
            default:
        }
    }

    @Override
    public void toUploadImageSuccess(UploadBean uploadBean) {
        dismissProgressDialog();
        //mIntent.putExtra("change01", "1000");
        // mIntent.putExtra("change02", "2000");
        // 设置结果，并进行传送
        intent.putExtra("url",uploadBean.getPicUrl());
        this.setResult(1, intent);
        this.finish();
    }

    @Override
    public void toUploadImageFail(String failMsg) {
        dismissProgressDialog();
        toast(failMsg);
    }

    @Override
    public void toUploadImageIdCardSuccess(IdCardBean idCardBean) {
        dismissProgressDialog();
        //mIntent.putExtra("change01", "1000");
        // mIntent.putExtra("change02", "2000");
        // 设置结果，并进行传送
        intent.putExtra("idCardBean",idCardBean);
        this.setResult(1, intent);
        this.finish();
    }

    @Override
    public void toUploadImageIdCardFail(String failMsg) {
        dismissProgressDialog();
        toast(failMsg);
    }
}
