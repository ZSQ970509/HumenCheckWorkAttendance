package com.example.humencheckworkattendance.contact;

import com.example.humencheckworkattendance.bean.LoginBean;

/**
 * Created by administration on 2017/9/4.
 */

public class LoginContact {
    public interface LoginView {
         void toHomePageActivity(LoginBean loginBean);
         void loginFail(String failMsg);
            void getHeadSuccess(byte []bytes);
        void getHeadFail(String failMsg);
    }

    public interface LoginPresenter {
        void login(String userName, String passWord, String imei);
        void loadHead(String imgSrc);
    }
}
