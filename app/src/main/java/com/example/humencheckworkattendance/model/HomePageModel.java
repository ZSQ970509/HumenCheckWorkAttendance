package com.example.humencheckworkattendance.model;

import android.support.annotation.NonNull;

import com.example.humencheckworkattendance.ProApplication;
import com.example.humencheckworkattendance.base.BaseModel;
import com.example.humencheckworkattendance.bean.CheckIsFrozenBean;
import com.example.humencheckworkattendance.bean.PlayCardBean;
import com.example.humencheckworkattendance.bean.UploadBean;
import com.example.humencheckworkattendance.exception.ApiException;
import com.example.humencheckworkattendance.subscriber.CommonSubscriber;
import com.example.humencheckworkattendance.transformer.CommonTransformer;
import com.example.humencheckworkattendance.utils.LogUtils;

/**
 * Created by administration on 2017/9/12.
 */

public class HomePageModel extends BaseModel{
    //打卡
    public void checkIsFrozen(@NonNull String memberId,@NonNull String projId,@NonNull final HomePageModel.checkIsFrozenInfoHint infoHint){
        if (infoHint == null) {
            throw new RuntimeException("InfoHint不能为空");
        }
        httpService.checkIsFrozen(memberId,projId)
                //.compose(view.<BaseHttp,Result>bind())
                .compose(new CommonTransformer())
                .subscribe(new CommonSubscriber<CheckIsFrozenBean>(ProApplication.getmContext()) {
                    @Override
                    public void onNext(CheckIsFrozenBean checkIsFrozenBean) {
                        //LogUtils.e("sss",s.toString());
                        infoHint.successInfo(checkIsFrozenBean);
                    }

                    @Override
                    protected void onError(ApiException e) {
                        super.onError(e);
                        LogUtils.d("sss",e.getMessage().toString());
                        infoHint.failInfo(e.message);
                    }
                });
    }
    //打卡
    public void playCard(@NonNull String time, @NonNull String memberId, @NonNull String address, @NonNull String  type, @NonNull String  projId, @NonNull String emtpId, @NonNull String emtpRolesId,@NonNull String lng,@NonNull  String lat,@NonNull String attendanceUrl,@NonNull String admUserId,@NonNull String admUserName,@NonNull final HomePageModel.playCardInfoHint infoHint){
        if (infoHint == null) {
            throw new RuntimeException("InfoHint不能为空");
        }
        httpService.playCard("1",time,memberId,address,type,projId,emtpId,emtpRolesId,lng,lat,attendanceUrl,admUserId,admUserName)
                //.compose(view.<BaseHttpResult>bind())
                .compose(new CommonTransformer())
                .subscribe(new CommonSubscriber<PlayCardBean>(ProApplication.getmContext()) {
                    @Override
                    public void onNext(PlayCardBean s) {
                        //LogUtils.e("sss",s.toString());
                        infoHint.successInfo(s.getMessage());
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
    public void uploadImage64(@NonNull String img64, @NonNull final HomePageModel.uploadImageInfoHint infoHint){
        if (infoHint == null) {
            throw new RuntimeException("InfoHint不能为空");
        }
        httpService.upLoadImage64(img64)
                // .compose(view.<BaseHttpResult<UploadBean>>bind())
                .compose(new CommonTransformer<UploadBean>())
                .subscribe(new CommonSubscriber<UploadBean>(ProApplication.getmContext()) {


                    @Override
                    public void onNext(UploadBean uploadBean) {
                        //LogUtils.d("sss",uploadBean.toString());
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
    public interface checkIsFrozenInfoHint {
        void successInfo(CheckIsFrozenBean checkIsFrozenBean );

        void failInfo(String str);

    }
    //通过接口产生信息回调
    public interface uploadImageInfoHint {
        void successInfo(UploadBean uploadBean);

        void failInfo(String str);

    }

    //通过接口产生信息回调
    public interface playCardInfoHint {
        void successInfo(String s);

        void failInfo(String str);

    }
}
