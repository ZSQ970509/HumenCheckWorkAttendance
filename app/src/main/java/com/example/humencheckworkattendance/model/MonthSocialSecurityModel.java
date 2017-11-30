package com.example.humencheckworkattendance.model;

import android.support.annotation.NonNull;

import com.example.humencheckworkattendance.ProApplication;
import com.example.humencheckworkattendance.base.BaseModel;
import com.example.humencheckworkattendance.bean.MonthSocialSecurityBean;
import com.example.humencheckworkattendance.exception.ApiException;
import com.example.humencheckworkattendance.subscriber.CommonSubscriber;
import com.example.humencheckworkattendance.transformer.CommonTransformer;
import com.example.humencheckworkattendance.utils.LogUtils;

/**
 * Created by Administrator on 2017-11-28.
 */

public class MonthSocialSecurityModel extends BaseModel {
    //获取当日打卡情况
    public void getMonthSocialSecurityList(@NonNull String project, @NonNull String page, @NonNull final getMonthSocialSecurityListInfoHint infoHint){
        if (infoHint == null) {
            throw new RuntimeException("InfoHint不能为空");
        }
        httpService.getMonthSocialSecurityList(project,page)
                .compose(new CommonTransformer<MonthSocialSecurityBean>())
                .subscribe(new CommonSubscriber<MonthSocialSecurityBean>(ProApplication.getmContext()) {
                    @Override
                    public void onNext(MonthSocialSecurityBean monthSocialSecurityBean) {
                        infoHint.successInfo(monthSocialSecurityBean);
                    }

                    @Override
                    protected void onError(ApiException e) {
                        super.onError(e);
                        LogUtils.d("sss",e.getMessage());
                        infoHint.failInfo(e.message);
                    }
                });
    }
    //获取当日打卡情况
    public void addSocialSecurity(@NonNull String proId,@NonNull String userId, @NonNull String imgUrl, @NonNull final addSocialSecurityInfoHint infoHint){
        if (infoHint == null) {
            throw new RuntimeException("InfoHint不能为空");
        }
        httpService.addSocialSecurity(proId,userId,imgUrl)
                .compose(new CommonTransformer())
                .subscribe(new CommonSubscriber<String>(ProApplication.getmContext()) {
                    @Override
                    public void onNext(String empty) {
                        infoHint.successInfo(empty);
                    }

                    @Override
                    protected void onError(ApiException e) {
                        super.onError(e);
                        LogUtils.d("sss",e.getMessage());
                        infoHint.failInfo(e.message);
                    }
                });
    }
    //通过接口产生信息回调
    public interface getMonthSocialSecurityListInfoHint {
        void successInfo(MonthSocialSecurityBean monthSocialSecurityBean);
        void failInfo(String str);
    }

    //通过接口产生信息回调
    public interface addSocialSecurityInfoHint {
        void successInfo(String empty);
        void failInfo(String str);
    }
}
