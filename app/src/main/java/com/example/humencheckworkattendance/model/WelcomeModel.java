package com.example.humencheckworkattendance.model;

import android.support.annotation.NonNull;

import com.example.humencheckworkattendance.ProApplication;
import com.example.humencheckworkattendance.base.BaseModel;
import com.example.humencheckworkattendance.bean.ModelBean;
import com.example.humencheckworkattendance.bean.UpdateBean;
import com.example.humencheckworkattendance.exception.ApiException;
import com.example.humencheckworkattendance.subscriber.CommonSubscriber;
import com.example.humencheckworkattendance.transformer.CommonTransformer;
import com.example.humencheckworkattendance.utils.LogUtils;

/**
 * Created by administration on 2017/9/12.
 */

public class WelcomeModel extends BaseModel{
    //登录
    public void getVersion(@NonNull String updateVersionCode, @NonNull final WelcomeModel.getVersionInfoHint infoHint){
        if (infoHint == null) {
            throw new RuntimeException("InfoHint不能为空");
        }
        httpService.getVersion("com.hc.android.mobileattendance",updateVersionCode)
                //.compose(view.<BaseHttpResult<LoginBean>>bind())
                .compose(new CommonTransformer<UpdateBean>())
                .subscribe(new CommonSubscriber<UpdateBean>(ProApplication.getmContext()) {
                    @Override
                    public void onNext(UpdateBean data) {
                        //LogUtils.d("sss",data.toString());
                        infoHint.successInfo(data);
                    }

                    @Override
                    protected void onError(ApiException e) {
                        super.onError(e);
                        LogUtils.d("sss",e.getMessage().toString());
                        infoHint.failInfo(e.message);
                    }
                });
    }

    //获取设备是否被允许使用App
    public void getIsExistIMEI(@NonNull String updateVersionCode, @NonNull final WelcomeModel.getIsExistIMEIInfoHint infoHint){
        if (infoHint == null) {
            throw new RuntimeException("InfoHint不能为空");
        }
        httpService.getIsExistIMEI(updateVersionCode)
                //.compose(view.<BaseHttpResult<LoginBean>>bind())
                .compose(new CommonTransformer())
                .subscribe(new CommonSubscriber<String>(ProApplication.getmContext()) {
                    @Override
                    public void onNext(String data) {
                        infoHint.successInfo(data);
                    }

                    @Override
                    protected void onError(ApiException e) {
                        super.onError(e);
                        LogUtils.d("sss",e.getMessage().toString());
                        infoHint.failInfo(e.message);
                    }
                });
    }
   /* //获取app版本
    public void getVersion(@NonNull String updateVersionCode, @NonNull final WelcomeModel.getVersionInfoHint infoHint){
        if (infoHint == null) {
            throw new RuntimeException("InfoHint不能为空");
        }
        httpService.getVersion("com.hc.android.mobileattendance",updateVersionCode)
               // .compose(view.<BaseHttpResult<UpdateBean>>bind())
                .compose(new CommonTransformer<UpdateBean>())
                .subscribe(new CommonSubscriber<UpdateBean>(ProApplication.getmContext()) {


                    @Override
                    public void onNext(UpdateBean updateBean) {
                        infoHint.successInfo(updateBean);
                    }

                    @Override
                    protected void onError(ApiException e) {
                        super.onError(e);
                        LogUtils.d("sss",e.getMessage().toString());
                        infoHint.failInfo(e.message);
                    }
                });
    }*/
    //通过接口产生信息回调
    public interface getVersionInfoHint {
        void successInfo(UpdateBean updateBean);

        void failInfo(String str);

    }
    //通过接口产生信息回调
    public interface getIsExistIMEIInfoHint {
        void successInfo(String modelBean);
        void failInfo(String str);
    }
}
