package com.example.humencheckworkattendance.model;

import android.support.annotation.NonNull;

import com.example.humencheckworkattendance.ProApplication;
import com.example.humencheckworkattendance.base.BaseModel;
import com.example.humencheckworkattendance.bean.ProjectBean;
import com.example.humencheckworkattendance.exception.ApiException;
import com.example.humencheckworkattendance.subscriber.CommonSubscriber;
import com.example.humencheckworkattendance.transformer.CommonTransformer;
import com.example.humencheckworkattendance.utils.LogUtils;

/**
 * Created by administration on 2017/9/12.
 */

public class SelectProjectModel extends BaseModel{
    //登录
    public void searchProject(@NonNull String userId,@NonNull String userTypes,@NonNull String projName, @NonNull final SelectProjectModel.getSearchProjectInfoHint infoHint){
        if (infoHint == null) {
            throw new RuntimeException("InfoHint不能为空");
        }
        httpService.selectProject(userId,"manager",userTypes,projName)
                //.compose(view.<BaseHttpResult<LoginBean>>bind())
                .compose(new CommonTransformer<ProjectBean>())
                .subscribe(new CommonSubscriber<ProjectBean>(ProApplication.getmContext()) {
                    @Override
                    public void onNext(ProjectBean data) {
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
    public interface getSearchProjectInfoHint {
        void successInfo(ProjectBean  projectBean);

        void failInfo(String str);

    }
}
