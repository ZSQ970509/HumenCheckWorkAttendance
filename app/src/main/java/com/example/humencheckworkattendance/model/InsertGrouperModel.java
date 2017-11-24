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

public class InsertGrouperModel extends BaseModel{
    //获取班组
    public void getGroup(@NonNull String projId  ,@NonNull final InsertGrouperModel.getGroupInfoHint infoHint){
        if (infoHint == null) {
            throw new RuntimeException("InfoHint不能为空");
        }
        httpService.getGroup(projId,"1")
               // .compose(view.<BaseHttpResult<GrouperBean>>bind())
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
    public interface getGroupInfoHint {
        void successInfo(GrouperBean grouperBean);

        void failInfo(String str);

    }
    //保存班组长
    public void saveGrouper(@NonNull String projId,@NonNull String UserName,@NonNull String teams ,@NonNull String teamText,@NonNull String name ,@NonNull String phone,@NonNull String idCard , @NonNull final InsertGrouperModel.saveGrouperInfoHint infoHint){
        if (infoHint == null) {
            throw new RuntimeException("InfoHint不能为空");
        }
        httpService.saveGrouper(projId,UserName,teams,teamText,"0",name,phone,idCard)
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
    public interface saveGrouperInfoHint {
        void successInfo(String s);

        void failInfo(String str);

    }
}
