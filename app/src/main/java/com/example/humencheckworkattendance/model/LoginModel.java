package com.example.humencheckworkattendance.model;

import android.support.annotation.NonNull;

import com.example.humencheckworkattendance.ProApplication;
import com.example.humencheckworkattendance.base.BaseModel;
import com.example.humencheckworkattendance.bean.LoginBean;
import com.example.humencheckworkattendance.exception.ApiException;
import com.example.humencheckworkattendance.subscriber.CommonSubscriber;
import com.example.humencheckworkattendance.transformer.CommonTransformer;
import com.example.humencheckworkattendance.utils.LogUtils;

import okhttp3.ResponseBody;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by administration on 2017/9/8.
 */

public class LoginModel extends BaseModel{
    public void loadHeader(@NonNull String imgSrc, @NonNull final loadHeaderInfoHint infoHint){
        if (infoHint == null) {
            throw new RuntimeException("InfoHint不能为空");
        }
        httpService.loadHeader(imgSrc)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CommonSubscriber<ResponseBody>(ProApplication.getmContext()) {


                    @Override
                    public void onNext(ResponseBody responseBody) {
                        if (responseBody != null){
                            try {
                                infoHint.successInfo(responseBody.bytes());
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }
                    }

                    @Override
                    protected void onError(ApiException e) {
                        super.onError(e);
                        LogUtils.d("sss",e.getMessage().toString());
                        infoHint.failInfo(e.message);
                    }
                });
    }
    //登录
    public void login(@NonNull String userName, @NonNull String passWord,@NonNull String imei, @NonNull final loginInfoHint infoHint){
        if (infoHint == null) {
            throw new RuntimeException("InfoHint不能为空");
        }
        httpService.login(passWord,userName,imei)
                //.compose(view.<BaseHttpResult<LoginBean>>bind())
                .compose(new CommonTransformer<LoginBean>())
                .subscribe(new CommonSubscriber<LoginBean>(ProApplication.getmContext()) {
                    @Override
                    public void onNext(LoginBean data) {
                       // LogUtils.d("sss",data.toString());
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

    //通过接口产生信息回调
    public interface loginInfoHint {
        void successInfo(LoginBean loginBean);

        void failInfo(String str);

    }
    //通过接口产生信息回调
    public interface loadHeaderInfoHint {
        void successInfo(byte[] bytes);

        void failInfo(String str);

    }
}
