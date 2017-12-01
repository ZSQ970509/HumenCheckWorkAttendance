package com.example.humencheckworkattendance.contact;

import com.example.humencheckworkattendance.bean.EmtpBean;
import com.example.humencheckworkattendance.bean.EmtpRolesBean;
import com.example.humencheckworkattendance.bean.TeamListBean;
import com.example.humencheckworkattendance.bean.UserBean;

/**
 * Created by administration on 2017/9/4.
 */

public class InsertHumenContact {


    public interface InsertHumenView {
        void getTeamSuccess(TeamListBean teamBeanList);
        void getTeamFail(String failMsg);
        void getInsertHumenSuccess(String s);
        void getInsertHumenSuccessFail(String failMsg);
        void getcheckHumenIsExistSuccess(String s);
        void getcheckHumenIsExistFail(String failMsg);
        void getEmtpSuccess(EmtpBean emtpBean);
        void getEmtpFail(String failMsg);
        void getDictByTypeSuccess(EmtpBean emtpBean);
        void getDictByTypeFail(String failMsg);
        void getEmtpRolesSuccess(EmtpRolesBean emtpRolesBean);
        void getEmtpRolesFail(String failMsg);
        void getStreamMemberByIdCardSuccess(UserBean userBean);
        void getStreamMemberByIdCardFail(String failMsg);
        void getHeadSuccess(byte []bytes);
        void getHeadFail(String failMsg);

    }

    public interface InsertHumenPresenter {
        void getTeam(String projId);
        void insertHumen(String userId, String idCard, String streamName, String mobile, String headImage, String iDCardPathImage,String iDCardBackPathImage, String emtpId, String rolesId,String gender, String projId,String validateCode, String streamTitle ,String statusType,String userTypeId,String admUserId,String admUserName);
        void checkHumenIsExist(String phone,String idcard);
        void getEmtp();
        void getEmtpRoles(String emtpId);
        void getDictByType();
        void getStreamMemberByIdCard(String idCard);
        void loadHead(String imgSrc);
    }
}
