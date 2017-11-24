package com.example.humencheckworkattendance.contact;

import com.example.humencheckworkattendance.bean.HistroyBean;

/**
 * Created by administration on 2017/9/4.
 */

public class SignInDetailsContact {
    public interface SignInDetailsView {
        void getHistroySuccess(HistroyBean histroyBean);
        void getHistroyFail(String failMsg);
    }

    public interface SignInDetailsPresenter {
        void getHistroy(String startTime, String page, String memberId);
    }
}
