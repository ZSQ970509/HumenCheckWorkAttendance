package com.example.humencheckworkattendance.model;

import android.support.annotation.NonNull;

import com.example.humencheckworkattendance.ProApplication;
import com.example.humencheckworkattendance.base.BaseModel;
import com.example.humencheckworkattendance.bean.HistroyBean;
import com.example.humencheckworkattendance.exception.ApiException;
import com.example.humencheckworkattendance.subscriber.CommonSubscriber;
import com.example.humencheckworkattendance.transformer.CommonTransformer;
import com.example.humencheckworkattendance.utils.LogUtils;

/**
 * Created by administration on 2017/9/8.
 */

public class SignInDetailsModel extends BaseModel{
    //打卡历史记录
    public void getHistroy(@NonNull String startTime, @NonNull String page, @NonNull String memberId, @NonNull final getHistroyInfoHint infoHint){
        if (infoHint == null) {
            throw new RuntimeException("InfoHint不能为空");
        }
        httpService.getHistroy(startTime,page,memberId)
              //  .compose(view.<BaseHttpResult<HistroyBean>>bind())
                .compose(new CommonTransformer<HistroyBean>())
                .subscribe(new CommonSubscriber<HistroyBean>(ProApplication.getmContext()) {
                    @Override
                    public void onNext(HistroyBean histroyBeen) {
                      //  LogUtils.d("sss",histroyBeen.toString());
                        infoHint.successInfo(histroyBeen);
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
    public interface getHistroyInfoHint {
        void successInfo(HistroyBean histroyBean);

        void failInfo(String str);

    }

}
