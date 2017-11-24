package com.example.humencheckworkattendance.model;

import android.support.annotation.NonNull;

import com.example.humencheckworkattendance.ProApplication;
import com.example.humencheckworkattendance.base.BaseModel;
import com.example.humencheckworkattendance.bean.IdCardBean;
import com.example.humencheckworkattendance.bean.UploadBean;
import com.example.humencheckworkattendance.exception.ApiException;
import com.example.humencheckworkattendance.subscriber.CommonSubscriber;
import com.example.humencheckworkattendance.transformer.CommonTransformer;
import com.example.humencheckworkattendance.utils.LogUtils;

/**
 * Created by administration on 2017/9/8.
 */

public class ShowPhotoModel extends BaseModel{
    //上传身份证图片
    public void uploadImage64IdCard(@NonNull String img64,@NonNull String idcard, @NonNull final uploadImageIdCardInfoHint infoHint){
        if (infoHint == null) {
            throw new RuntimeException("InfoHint不能为空");
        }
        httpService.upLoadImage64IdCard(img64)
                // .compose(view.<BaseHttpResult<UploadBean>>bind())
                .compose(new CommonTransformer<IdCardBean>())
                .subscribe(new CommonSubscriber<IdCardBean>(ProApplication.getmContext()) {


                    @Override
                    public void onNext(IdCardBean idCardBean) {
                        // LogUtils.d("sss",uploadBean.toString());
                        infoHint.successInfo(idCardBean);
                    }
                    @Override
                    protected void onError(ApiException e) {
                        super.onError(e);
                        LogUtils.d("sss",e.getMessage().toString());
                        infoHint.failInfo(e.message);
                    }
                });
    }
    //上传图片
    public void uploadImage64(@NonNull String img64, @NonNull final uploadImageInfoHint infoHint){
        if (infoHint == null) {
            throw new RuntimeException("InfoHint不能为空");
        }
        httpService.upLoadImage64(img64)
               // .compose(view.<BaseHttpResult<UploadBean>>bind())
                .compose(new CommonTransformer<UploadBean>())
                .subscribe(new CommonSubscriber<UploadBean>(ProApplication.getmContext()) {


                    @Override
                    public void onNext(UploadBean uploadBean) {
                       // LogUtils.d("sss",uploadBean.toString());
                        infoHint.successInfo(uploadBean);
                    }
                    @Override
                    protected void onError(ApiException e) {
                        super.onError(e);
                        LogUtils.d("sss",e.getMessage().toString());
                        infoHint.failInfo(e.message);
                    }
                });
    }

    //通过接口产生信息回调
    public interface uploadImageInfoHint {
        void successInfo(UploadBean uploadBean);

        void failInfo(String str);

    }
    //通过接口产生信息回调
    public interface uploadImageIdCardInfoHint {
        void successInfo(IdCardBean idCardBean);

        void failInfo(String str);

    }
}
