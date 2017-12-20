package com.example.humencheckworkattendance.contact;

/**
 * Created by administration on 2017/9/4.
 */

public class ChangePassWordContact {


    public interface ChangePassWordView {
        void ChangePassWordSuccess(String s);
        void ChangePassWordFail(String failMsg);
    }

    public interface ChangePassWordPresenter {
        void ChangePassWord(String oldPassword,String newPassword,String account, String userType);
    }
}
