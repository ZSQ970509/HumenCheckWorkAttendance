package com.example.humencheckworkattendance.http;


import com.example.humencheckworkattendance.base.BaseHttpResult;
import com.example.humencheckworkattendance.bean.EmtpBean;
import com.example.humencheckworkattendance.bean.EmtpRolesBean;
import com.example.humencheckworkattendance.bean.GrouperBean;
import com.example.humencheckworkattendance.bean.HistroyBean;
import com.example.humencheckworkattendance.bean.IdCardBean;
import com.example.humencheckworkattendance.bean.LoginBean;
import com.example.humencheckworkattendance.bean.MonthSocialSecurityBean;
import com.example.humencheckworkattendance.bean.PlayCardTodayBean;
import com.example.humencheckworkattendance.bean.ProjectBean;
import com.example.humencheckworkattendance.bean.SubmitHumenBean;
import com.example.humencheckworkattendance.bean.TeamListBean;
import com.example.humencheckworkattendance.bean.UpdateBean;
import com.example.humencheckworkattendance.bean.UploadBean;
import com.example.humencheckworkattendance.bean.UserBean;
import com.example.humencheckworkattendance.url.UrlHelper;

import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Streaming;
import retrofit2.http.Url;
import rx.Observable;

/**
 * Created by GaoSheng on 2016/9/13.
 * 网络请求的接口都在这里
 */

public interface HttpService {
    //获取banner
    //String url = "";

    @FormUrlEncoded
    @POST(UrlHelper.API.API+UrlHelper.API.Login)
    Observable<BaseHttpResult<LoginBean>> login(@Field("password") String passWord, @Field("account") String userName,@Field("imei") String imei);

    @GET
    @Streaming
    Observable<ResponseBody> loadHeader(@Url String url);

    @FormUrlEncoded
    @POST(UrlHelper.API.API+UrlHelper.API.playCard)
    Observable<BaseHttpResult> playCard(@Field("inOutType") String inOutType, @Field("time") String time, @Field("memberId") String memberId, @Field("address") String address, @Field("type") String type, @Field("projId") String projId, @Field("emtpId") String emtpId, @Field("emtpRolesId") String emtpRolesId, @Field("lng") String lng, @Field("lat") String lat, @Field("attendanceUrl") String attendanceUrl);

    @FormUrlEncoded
    @POST(UrlHelper.API.API+UrlHelper.API.getHistroy)
    Observable<BaseHttpResult<HistroyBean>> getHistroy(@Field("startTime") String startTime, @Field("page") String page, @Field("memberId") String memberId);

    @FormUrlEncoded
    @POST(UrlHelper.API.API+UrlHelper.API.getTeam)
    Observable<BaseHttpResult<TeamListBean>> getTeam(@Field("projId") String projId, @Field("isMobile") String isMobile);

    @POST(UrlHelper.API.API+UrlHelper.API.upLoadImage64)
    Observable<BaseHttpResult<UploadBean>> upLoadImage64(@Body String img64);

    @POST(UrlHelper.API.API+UrlHelper.API.upLoadImage64)
    Observable<BaseHttpResult<IdCardBean>> upLoadImage64IdCard(@Body String img64);
    @FormUrlEncoded
    @POST(UrlHelper.API.API+UrlHelper.API.insertHumen)
    Observable<BaseHttpResult> insertHumen(@Field("userId") String userId,@Field("idCard") String idCard,@Field("streamName") String streamName,@Field("mobile") String mobile,@Field("headImage") String headImage,@Field("iDCardPathImage") String iDCardPathImage,@Field("iDCardBackPathImage") String  iDCardBackPathImage,@Field("password") String password,@Field("emtpId") String emtpId,@Field("emtpRolesId") String rolesId,@Field("gender") String gender,@Field("projId") String projId,@Field("validateCode") String validateCode,@Field("streamTitle") String streamTitle,@Field("statusType") String statusType,@Field("userTypeId") String userTypeId,@Field("admUserId") String admUserId,@Field("admUserName") String admUserName);

    @FormUrlEncoded
    @POST(UrlHelper.API.API+UrlHelper.API.checkIsExist)
    Observable<BaseHttpResult> checkIsExist(@Field("mobile") String mobile,@Field("idcard") String idcard);

    @FormUrlEncoded
    @POST(UrlHelper.API.API+UrlHelper.API.getGrouper)
    Observable<BaseHttpResult<GrouperBean>> getGrouper(@Field("projId") String projId);

    @FormUrlEncoded
    @POST(UrlHelper.API.API+UrlHelper.API.saveGroup)
    Observable<BaseHttpResult<GrouperBean>> saveGroup(@Field("name") String name,@Field("reckonerId") String reckonerId,@Field("reckonerName") String reckonerName,@Field("projId") String projId);

    @FormUrlEncoded
    @POST(UrlHelper.API.API+UrlHelper.API.getGroup)
    Observable<BaseHttpResult<GrouperBean>> getGroup(@Field("projId") String projId,@Field("isMobile") String isMobile);

    @FormUrlEncoded
    @POST(UrlHelper.API.API+UrlHelper.API.saveGrouper)
    Observable<BaseHttpResult<GrouperBean>> saveGrouper(@Field("projId") String projId,@Field("UserName") String UserName,@Field("teams") String teams,@Field("teamText") String teamText,@Field("id") String id,@Field("name") String name,@Field("phone") String phone,@Field("idCard") String idCard);

    @FormUrlEncoded
    @POST(UrlHelper.API.API+UrlHelper.API.changePassWord)
    Observable<BaseHttpResult> changePassWord(@Field("oldPassword") String oldPassword,@Field("newPassword") String newPassword,@Field("account") String account,@Field("userType") String userType);

    @FormUrlEncoded
    @POST(UrlHelper.API.API+UrlHelper.API.getVersion)
    Observable<BaseHttpResult<UpdateBean>> getVersion(@Field("packageName") String packageName, @Field("updateVersionCode") String updateVersionCode);

    @FormUrlEncoded
    @POST(UrlHelper.API.API+UrlHelper.API.selectProject)
    Observable<BaseHttpResult<ProjectBean>> selectProject(@Field("userId") String userId, @Field("userType") String userType,@Field("userTypes") String userTypes,@Field("projName") String projName);

    @FormUrlEncoded
    @POST(UrlHelper.API.API+UrlHelper.API.getEmtp)
    Observable<BaseHttpResult<EmtpBean>> getEmtp(@Field("test") String test);

    @FormUrlEncoded
    @POST(UrlHelper.API.API+UrlHelper.API.getEmtpRoles)
    Observable<BaseHttpResult<EmtpRolesBean>> getEmtpRoles(@Field("emtpId") String emtpId);

    @FormUrlEncoded
    @POST(UrlHelper.API.API+UrlHelper.API.getDictByType)
    Observable<BaseHttpResult<EmtpBean>> getDictByType(@Field("test") String test);

    @FormUrlEncoded
    @POST(UrlHelper.API.API+UrlHelper.API.getStreamMemberByIdCard)
    Observable<BaseHttpResult<UserBean>> getStreamMemberByIdCard(@Field("idCard") String idCard);

    @FormUrlEncoded
    @POST(UrlHelper.API.API+UrlHelper.API.getStreamMemberList)
    Observable<BaseHttpResult<SubmitHumenBean>> getStreamMemberList(@Field("memberId") String memberId, @Field("page") String page, @Field("pageSize") String pageSize);

    @FormUrlEncoded
    @POST(UrlHelper.API.API+UrlHelper.API.subitStreamMember)
    Observable<BaseHttpResult> subitStreamMember(@Field("id") String id);

    @FormUrlEncoded
    @POST(UrlHelper.API.API+UrlHelper.API.rollBackMember)
    Observable<BaseHttpResult> rollBackMember(@Field("id") String id);

    @FormUrlEncoded
    @POST(UrlHelper.API.API+UrlHelper.API.checkDeleteStreamMember)
    Observable<BaseHttpResult> checkDeleteStreamMember(@Field("id") String id);

    @FormUrlEncoded
    @POST(UrlHelper.API.API+UrlHelper.API.deleteStreamMember)
    Observable<BaseHttpResult> deleteStreamMember(@Field("id") String id);

    @FormUrlEncoded
    @POST(UrlHelper.API.API+UrlHelper.API.getErrorStreamMember)
    Observable<BaseHttpResult> getErrorStreamMember(@Field("id") String id);

    @FormUrlEncoded
    @POST(UrlHelper.API.API+UrlHelper.API.checkIsFrozen)
    Observable<BaseHttpResult> checkIsFrozen(@Field("id") String id,@Field("projId") String projId);

    @FormUrlEncoded
    @POST(UrlHelper.API.API+UrlHelper.API.getDayAttendanceList)
    Observable<BaseHttpResult<PlayCardTodayBean>> getDayAttendanceList(@Field("projId") String projId, @Field("page") String page);


    @FormUrlEncoded
    @POST(UrlHelper.API.API+UrlHelper.API.getMothSocialSecurityList)
    Observable<BaseHttpResult<MonthSocialSecurityBean>> getMonthSocialSecurityList(@Field("projId") String projId, @Field("page") String page);

    @FormUrlEncoded
    @POST(UrlHelper.API.API+UrlHelper.API.addSocialSecurity)
    Observable<BaseHttpResult>addSocialSecurity(@Field("projId") String projId, @Field("userId") String userId, @Field("imgUrl") String imgUrl);

}
