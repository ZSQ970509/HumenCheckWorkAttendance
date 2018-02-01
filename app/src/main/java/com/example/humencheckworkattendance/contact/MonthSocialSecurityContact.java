package com.example.humencheckworkattendance.contact;


import com.example.humencheckworkattendance.bean.MonthSocialSecurityBean;

/**
 * Created by administration on 2017/9/4.
 */

public class MonthSocialSecurityContact {


    public interface MonthSocialSecurityView {
        void getMonthSocialSecurityListSuccess(MonthSocialSecurityBean monthSocialSecurityBean);
        void getMonthSocialSecurityListFail(String failMsg);

        void addSocialSecuritySuccess(String empty);
        void addSocialSecurityFail(String failMsg);
    }

    public interface MonthSocialSecurityPresenter {
        void getMonthSocialSecurityList(String project, String page);
        void addSocialSecurity(String proId, String userId, String imgUrl, String admUserId , String admUserName);
    }
}
