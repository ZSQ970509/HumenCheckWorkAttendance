package com.example.humencheckworkattendance.url;

/**
 * Created by GaoSheng on 2016/9/14.
 * 添加所有的接口
 */

public class UrlHelper {

   //public static String BASE_URL = "http://10.1.3.160:59525";
    //public static String BASE_URL = "http://10.1.3.86:8056";
   // public static String BASE_URL = "http://120.35.11.49:4545";
   // public static String BASE_URL = "http://10.1.3.86:7878";
    public static String BASE_URL = "http://ydkq.jsqqy.com";
   // public static String BASE_URL = "http://192.168.1.81:4545";
   // public static String BASE_URL = " http://10.1.3.222:1212";

    public static class API{

        public static final String API ="/Services";

        //登录
        public static final String Login ="/DataService.ashx?action=login";
        //打卡
        public static final String playCard ="/DataService.ashx?action=signAttend";
        //打卡记录
        public static final String getHistroy ="/DataService.ashx?action=month";
        //获取班组信息
        public static final String getTeam ="/DataService.ashx?action=getTeamByProjId";
        //上传图片64位
        public static final String upLoadImage64 ="/DataService.ashx?action=upLoadPhoto";
        //上传图片64位
        //public static final String upLoadImage64 ="/Handler/VsmSdkHandler.ashx?action=Tsetddddd1";
        //添加人员
        public static final String insertHumen ="/DataService.ashx?action=saveStreamMembers";
        //查看人员是否存在
        public static final String checkIsExist ="/DataService.ashx?action=checkMobile";
        //获取班组长人员
        public static final String getGrouper ="/DataService.ashx?action=getReckoner";
        //保存班组
        public static final String saveGroup ="/DataService.ashx?action=saveTeamForActionMember";
        //获取班组
        public static final String getGroup ="/DataService.ashx?action=getTeamByProjId";
        //保存班组长
        public static final String saveGrouper ="/DataService.ashx?action=saveTeamLeader";
        //修改密码
        public static final String changePassWord ="/DataService.ashx?action=changePassword";
        //获取app版本
        public static final String getVersion ="/DataService.ashx?action=getAppUpdate";
        //获取项目
        public static final String selectProject ="/DataService.ashx?action=getProjectList";
        //查询单位
        public static final String getEmtp ="/DataService.ashx?action=getEmtp";
        //查询单位下角色
        public static final String getEmtpRoles ="/DataService.ashx?action=getEmtpRoles";
        //查询职称
        public static final String  getDictByType = "/DataService.ashx?action=getDictByType";
        //人员审核进度
        public static final String getStreamMemberList ="/DataService.ashx?action=getStreamMemberList";
        //提交审核
        public static final String subitStreamMember ="/DataService.ashx?action=subitStreamMember";
        //撤销审核
        public static final String rollBackMember ="/DataService.ashx?action=withdrawStreamMember";
        //检查是否可删除用户
        public static final String checkDeleteStreamMember="/DataService.ashx?action=checkDeleteStreamMember";
        //删除用户信息
        public static final String deleteStreamMember="/DataService.ashx?action=deleteStreamMember";
        //根据身份证判断人员是否存在（存在返回人员信息）
        public static final String getStreamMemberByIdCard="/DataService.ashx?action=getStreamMemberByIdCard";
        //查询审核失败信息
        public static final String getErrorStreamMember ="/DataService.ashx?action=checkSubitStreamMemberFail";
        //检查是否冻结该人员活体检测
        public static final String checkIsFrozen ="/DataService.ashx?action=checkIsFrozen";
        //获取当日打卡情况
        public static final String getDayAttendanceList ="/DataService.ashx?action=getDayAttendanceList";
        //获取该项目下所有人员本月社保查询接口
        public static final String getMothSocialSecurityList ="/DataService.ashx?action=getMothSocialSecurityList";
        //添加社保信息接口
        public static final String addSocialSecurity ="/DataService.ashx?action=addSocialSecurity";
    }


}
