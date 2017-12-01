package com.example.humencheckworkattendance.model;

import android.support.annotation.NonNull;

import com.example.humencheckworkattendance.ProApplication;
import com.example.humencheckworkattendance.base.BaseModel;
import com.example.humencheckworkattendance.bean.EmtpBean;
import com.example.humencheckworkattendance.bean.EmtpRolesBean;
import com.example.humencheckworkattendance.bean.TeamListBean;
import com.example.humencheckworkattendance.bean.UserBean;
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

public class InsertHumenModel extends BaseModel{
    //查询单位下角色
    public void getEmtpRoles(@NonNull String emtpId,@NonNull final InsertHumenModel.getEmtpRoleInfoHint infoHint){
        if (infoHint == null) {
            throw new RuntimeException("InfoHint不能为空");
        }
        httpService.getEmtpRoles(emtpId)
                // .compose(view.<BaseHttpResult<TeamListBean>>bind())
                .compose(new CommonTransformer<EmtpRolesBean>())
                .subscribe(new CommonSubscriber<EmtpRolesBean>(ProApplication.getmContext()) {
                    @Override
                    public void onNext(EmtpRolesBean emtpRolesBean) {
                       // LogUtils.d("sss",emtpRolesBean.toString());
                        infoHint.successInfo(emtpRolesBean);
                    }

                    @Override
                    protected void onError(ApiException e) {
                        super.onError(e);
                        LogUtils.d("sss",e.getMessage().toString());
                        infoHint.failInfo(e.message);
                    }
                });

    }
    //查看单位
    public void getEmtp(@NonNull final InsertHumenModel.getEmtpInfoHint infoHint){
        if (infoHint == null) {
            throw new RuntimeException("InfoHint不能为空");
        }
        httpService.getEmtp("1")
                // .compose(view.<BaseHttpResult<TeamListBean>>bind())
                .compose(new CommonTransformer<EmtpBean>())
                .subscribe(new CommonSubscriber<EmtpBean>(ProApplication.getmContext()) {
                    @Override
                    public void onNext(EmtpBean emtpBean) {
                       // LogUtils.d("sss",emtpBean.toString());
                        infoHint.successInfo(emtpBean);
                    }

                    @Override
                    protected void onError(ApiException e) {
                        super.onError(e);
                        LogUtils.d("sss",e.getMessage().toString());
                        infoHint.failInfo(e.message);
                    }
                });

    }
    //获取职称
    public void getDictByType(@NonNull final InsertHumenModel.getDictByTypeInfoHint infoHint){
        if (infoHint == null) {
            throw new RuntimeException("InfoHint不能为空");
        }
        httpService.getDictByType("1")
                // .compose(view.<BaseHttpResult<TeamListBean>>bind())
                .compose(new CommonTransformer<EmtpBean>())
                .subscribe(new CommonSubscriber<EmtpBean>(ProApplication.getmContext()) {
                    @Override
                    public void onNext(EmtpBean emtpBean) {
                      //  LogUtils.d("sss",emtpBean.toString());
                        infoHint.successInfo(emtpBean);
                    }

                    @Override
                    protected void onError(ApiException e) {
                        super.onError(e);
                        LogUtils.d("sss",e.getMessage().toString());
                        infoHint.failInfo(e.message);
                    }
                });

    }
    //根据身份证判断人员是否存在
    public void getStreamMemberByIdCard(@NonNull String idCard, @NonNull final InsertHumenModel.getStreamMemberByIdCardInfoHint infoHint){
        if (infoHint == null) {
            throw new RuntimeException("InfoHint不能为空");
        }
        httpService.getStreamMemberByIdCard(idCard)
                // .compose(view.<BaseHttpResult<TeamListBean>>bind())
                .compose(new CommonTransformer<UserBean>())
                .subscribe(new CommonSubscriber<UserBean>(ProApplication.getmContext()) {
                    @Override
                    public void onNext(UserBean userBean) {
                      //  LogUtils.d("sss",userBean.getImageUrl());
                        infoHint.successInfo(userBean);
                    }

                    @Override
                    protected void onError(ApiException e) {
                        super.onError(e);
                        LogUtils.d("sss",e.getMessage().toString());
                        infoHint.failInfo(e.message);
                    }
                });
    }
    //查看人员是否已存在
    public void checkHumenIsExist(@NonNull String phone , @NonNull String idcard , @NonNull final InsertHumenModel.checkHumenIsExistInfoHint infoHint){
        if (infoHint == null) {
            throw new RuntimeException("InfoHint不能为空");
        }
        httpService.checkIsExist(phone,idcard)
              //  .compose(view.<BaseHttpResult>bind())
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
    //添加人员
    public void insertHumen(@NonNull String userId,@NonNull String idCard,@NonNull String streamName,@NonNull String mobile,@NonNull String headImage,@NonNull String iDCardPathImage,@NonNull String iDCardBackPathImage,@NonNull String emtpId,@NonNull String rolesId,@NonNull String gender,@NonNull String projId,@NonNull String validateCode,@NonNull String streamTitle,@NonNull String statusType,@NonNull String userTypeId,@NonNull String admUserId,@NonNull String admUserName,@NonNull final InsertHumenModel.InsertHumenInfoHint infoHint){
        if (infoHint == null) {
            throw new RuntimeException("InfoHint不能为空");
        }
        httpService.insertHumen(userId,idCard,streamName,mobile,headImage,iDCardPathImage,iDCardBackPathImage,"123456",emtpId,rolesId,gender,projId,validateCode,streamTitle,statusType,userTypeId,admUserId,admUserName)
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
    //打卡历史记录
    public void getTeam(@NonNull String projId ,@NonNull final InsertHumenModel.getTeamInfoHint infoHint){
        if (infoHint == null) {
            throw new RuntimeException("InfoHint不能为空");
        }
        httpService.getTeam(projId,"1")
               // .compose(view.<BaseHttpResult<TeamListBean>>bind())
                .compose(new CommonTransformer<TeamListBean>())
                .subscribe(new CommonSubscriber<TeamListBean>(ProApplication.getmContext()) {
                    @Override
                    public void onNext(TeamListBean TeamBeanList) {
                       // LogUtils.d("sss",TeamBeanList.toString());
                        infoHint.successInfo(TeamBeanList);
                    }

                    @Override
                    protected void onError(ApiException e) {
                        super.onError(e);
                        LogUtils.d("sss",e.getMessage().toString());
                        infoHint.failInfo(e.message);
                    }
                });
    }
    public void loadHeader(@NonNull String imgSrc, @NonNull final InsertHumenModel.loadHeaderInfoHint infoHint){
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
    //通过接口产生信息回调
    public interface getEmtpRoleInfoHint {
        void successInfo(EmtpRolesBean emtpRolesBean);

        void failInfo(String str);

    }
    //通过接口产生信息回调
    public interface getEmtpInfoHint {
        void successInfo(EmtpBean emtpBean);

        void failInfo(String str);

    }
    //通过接口产生信息回调
    public interface getDictByTypeInfoHint{
        void successInfo(EmtpBean emtpBean);

        void failInfo(String str);
    }
    //通过接口产生信息回调
    public interface checkHumenIsExistInfoHint {
        void successInfo(String str);

        void failInfo(String str);

    }
    //通过接口产生信息回调
    public interface InsertHumenInfoHint {
        void successInfo(String str);

        void failInfo(String str);

    }
    //通过接口产生信息回调
    public interface getTeamInfoHint {
        void successInfo(TeamListBean teamBeanList);

        void failInfo(String str);

    }
    //通过接口产生信息回调
    public interface getStreamMemberByIdCardInfoHint{
        void successInfo(UserBean userBean);

        void failInfo(String str);
    }
    //通过接口产生信息回调
    public interface loadHeaderInfoHint {
        void successInfo(byte[] bytes);

        void failInfo(String str);

    }
}
