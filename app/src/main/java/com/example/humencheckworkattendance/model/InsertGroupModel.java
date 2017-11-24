package com.example.humencheckworkattendance.model;

import android.support.annotation.NonNull;

import com.example.humencheckworkattendance.ProApplication;
import com.example.humencheckworkattendance.base.BaseModel;
import com.example.humencheckworkattendance.bean.GrouperBean;
import com.example.humencheckworkattendance.exception.ApiException;
import com.example.humencheckworkattendance.subscriber.CommonSubscriber;
import com.example.humencheckworkattendance.transformer.CommonTransformer;
import com.example.humencheckworkattendance.utils.LogUtils;

/**
 * Created by administration on 2017/9/8.
 */

public class InsertGroupModel extends BaseModel{
    //获取班组长
    public void getGrouper(@NonNull String projId , @NonNull final InsertGroupModel.getGrouperInfoHint infoHint){
        if (infoHint == null) {
            throw new RuntimeException("InfoHint不能为空");
        }
        httpService.getGrouper(projId)
                //.compose(view.<BaseHttpResult<GrouperBean>>bind())
                .compose(new CommonTransformer<GrouperBean>())
                .subscribe(new CommonSubscriber<GrouperBean>(ProApplication.getmContext()) {

                    @Override
                    public void onNext(GrouperBean grouperBean) {
                       // LogUtils.d("sss",grouperBean.toString());
                        infoHint.successInfo(grouperBean);
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
    public interface getGrouperInfoHint {
        void successInfo(GrouperBean grouperBean);

        void failInfo(String str);

    }
    //保存班组
    public void saveGroup(@NonNull String name,@NonNull String reckonerName,@NonNull String projId  ,@NonNull final InsertGroupModel.saveGroupInfoHint infoHint){
        if (infoHint == null) {
            throw new RuntimeException("InfoHint不能为空");
        }
        httpService.saveGroup(name,"0",reckonerName,projId)
               // .compose(view.<BaseHttpResult>bind())
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
    public interface saveGroupInfoHint {
        void successInfo(String s);

        void failInfo(String str);

    }
}
