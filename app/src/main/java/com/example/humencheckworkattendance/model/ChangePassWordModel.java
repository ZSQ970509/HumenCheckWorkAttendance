package com.example.humencheckworkattendance.model;

import android.support.annotation.NonNull;

import com.example.humencheckworkattendance.ProApplication;
import com.example.humencheckworkattendance.base.BaseModel;
import com.example.humencheckworkattendance.exception.ApiException;
import com.example.humencheckworkattendance.subscriber.CommonSubscriber;
import com.example.humencheckworkattendance.transformer.CommonTransformer;
import com.example.humencheckworkattendance.utils.LogUtils;

/**
 * Created by administration on 2017/9/12.
 */

public class ChangePassWordModel extends BaseModel{
    //保存班组
    public void changePassWord(@NonNull String oldPassword, @NonNull String newPassword, @NonNull String account , @NonNull final ChangePassWordModel.ChangePassWordInfoHint infoHint){
        if (infoHint == null) {
            throw new RuntimeException("InfoHint不能为空");
        }
        httpService.changePassWord(oldPassword,newPassword,account)
                //.compose(view.<BaseHttpResult>bind())
                .compose(new CommonTransformer())
                .subscribe(new CommonSubscriber<String>(ProApplication.getmContext()) {


                    @Override
                    public void onNext(String s) {
                        infoHint.successInfo(s);
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
    public interface ChangePassWordInfoHint {
        void successInfo(String s);

        void failInfo(String str);

    }
}
