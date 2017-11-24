package com.example.humencheckworkattendance.model;

import android.support.annotation.NonNull;

import com.example.humencheckworkattendance.ProApplication;
import com.example.humencheckworkattendance.base.BaseModel;
import com.example.humencheckworkattendance.bean.SubmitHumenBean;
import com.example.humencheckworkattendance.exception.ApiException;
import com.example.humencheckworkattendance.subscriber.CommonSubscriber;
import com.example.humencheckworkattendance.transformer.CommonTransformer;
import com.example.humencheckworkattendance.utils.LogUtils;

/**
 * Created by administration on 2017/9/8.
 */

public class SubmitHumenMessageModel extends BaseModel{
    //打卡历史记录
    public void getStreamMemberList(@NonNull String memberId, @NonNull String page, @NonNull String pageSize, @NonNull final SubmitHumenMessageModel.getStreamMemberListInfoHint infoHint){
        if (infoHint == null) {
            throw new RuntimeException("InfoHint不能为空");
        }
        httpService.getStreamMemberList(memberId,page,pageSize)
                //  .compose(view.<BaseHttpResult<HistroyBean>>bind())
                .compose(new CommonTransformer<SubmitHumenBean>())
                .subscribe(new CommonSubscriber<SubmitHumenBean>(ProApplication.getmContext()) {
                    @Override
                    public void onNext(SubmitHumenBean submitHumenBean) {
                        LogUtils.d("sss",submitHumenBean.toString());
                        infoHint.successInfo(submitHumenBean);
                    }

                    @Override
                    protected void onError(ApiException e) {
                        super.onError(e);
                        LogUtils.d("sss",e.getMessage().toString());
                        infoHint.failInfo(e.message);
                    }
                });
    }
    //提交审核
    public void subitStreamMember(@NonNull String id, @NonNull final SubmitHumenMessageModel.subitStreamMemberInfoHint infoHint){
        if (infoHint == null) {
            throw new RuntimeException("InfoHint不能为空");
        }
        httpService.subitStreamMember(id)
                //  .compose(view.<BaseHttpResult<HistroyBean>>bind())
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
    //撤销审核
    public void rollBackMember(@NonNull String id, @NonNull final SubmitHumenMessageModel.rollBackMemberInfoHint infoHint){
        if (infoHint == null) {
            throw new RuntimeException("InfoHint不能为空");
        }
        httpService.rollBackMember(id)
                //  .compose(view.<BaseHttpResult<HistroyBean>>bind())
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
    //检查人员是否可删除
    public void checkDeleteStreamMember(@NonNull String id, @NonNull final SubmitHumenMessageModel.checkDeleteStreamMemberInfoHint infoHint){
        if (infoHint == null) {
            throw new RuntimeException("InfoHint不能为空");
        }
        httpService.checkDeleteStreamMember(id)
                //  .compose(view.<BaseHttpResult<HistroyBean>>bind())
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
    //删除人员信息
    public void deleteStreamMember(@NonNull String id, @NonNull final SubmitHumenMessageModel.deleteStreamMemberInfoHint infoHint){
        if (infoHint == null) {
            throw new RuntimeException("InfoHint不能为空");
        }
        httpService.deleteStreamMember(id)
                //  .compose(view.<BaseHttpResult<HistroyBean>>bind())
                .compose(new CommonTransformer())
                .subscribe(new CommonSubscriber<String>(ProApplication.getmContext()) {
                    @Override
                    public void onNext(String s) {
                        infoHint.successInfo(s);
                    }

                    @Override
                    protected void onError(ApiException e) {
                        super.onError(e);
                       // LogUtils.d("sss",e.getMessage().toString());
                        infoHint.failInfo(e.message);
                    }
                });
    }
    //获取审核失败的信息
    public void getErrorStreamMember(@NonNull String id, @NonNull final SubmitHumenMessageModel.getErrorStreamMemberInfoHint infoHint){
        if (infoHint == null) {
            throw new RuntimeException("InfoHint不能为空");
        }
        httpService.getErrorStreamMember(id)
                //  .compose(view.<BaseHttpResult<HistroyBean>>bind())
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
    public interface getStreamMemberListInfoHint {
        void successInfo(SubmitHumenBean submitHumenBean);

        void failInfo(String str);

    }
    //通过接口产生信息回调
    public interface subitStreamMemberInfoHint {
        void successInfo(String str);

        void failInfo(String str);

    }
    //通过接口产生信息回调
    public interface rollBackMemberInfoHint {
        void successInfo(String str);

        void failInfo(String str);

    }
    //通过接口产生信息回调
    public interface checkDeleteStreamMemberInfoHint {
        void successInfo(String str);

        void failInfo(String str);

    }
    //通过接口产生信息回调
    public interface deleteStreamMemberInfoHint {
        void successInfo(String str);

        void failInfo(String str);

    }
    //通过接口产生信息回调
    public interface getErrorStreamMemberInfoHint {
        void successInfo(String str);

        void failInfo(String str);

    }
}
