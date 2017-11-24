package com.example.humencheckworkattendance.model;

import android.support.annotation.NonNull;

import com.example.humencheckworkattendance.ProApplication;
import com.example.humencheckworkattendance.base.BaseModel;
import com.example.humencheckworkattendance.bean.PlayCardTodayBean;
import com.example.humencheckworkattendance.exception.ApiException;
import com.example.humencheckworkattendance.subscriber.CommonSubscriber;
import com.example.humencheckworkattendance.transformer.CommonTransformer;
import com.example.humencheckworkattendance.utils.LogUtils;

/**
 * Created by administration on 2017/9/12.
 */

public class PlayCardTodayModel extends BaseModel{
    //获取当日打卡情况
    public void getDayAttendanceList(@NonNull String project, @NonNull String page, @NonNull final PlayCardTodayModel.getDayAttendanceListInfoHint infoHint){
        if (infoHint == null) {
            throw new RuntimeException("InfoHint不能为空");
        }
        httpService.getDayAttendanceList(project,page)
                //  .compose(view.<BaseHttpResult<HistroyBean>>bind())
                .compose(new CommonTransformer<PlayCardTodayBean>())
                .subscribe(new CommonSubscriber<PlayCardTodayBean>(ProApplication.getmContext()) {
                    @Override
                    public void onNext(PlayCardTodayBean playCardTodayBean) {
                        infoHint.successInfo(playCardTodayBean);
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
    public interface getDayAttendanceListInfoHint {
        void successInfo(PlayCardTodayBean playCardTodayBean);

        void failInfo(String str);

    }
}
