package com.example.humencheckworkattendance.presenter;

import com.example.humencheckworkattendance.base.BasePresenter;
import com.example.humencheckworkattendance.bean.LoginBean;
import com.example.humencheckworkattendance.contact.LoginContact;
import com.example.humencheckworkattendance.model.LoginModel;
import com.example.humencheckworkattendance.mvp.IModel;
import com.example.humencheckworkattendance.ui.activity.LoginActivity;

import java.util.HashMap;

/**
 * Created by administration on 2017/9/4.
 */

public class LoginPresenter  extends BasePresenter<LoginActivity> implements LoginContact.LoginPresenter{
    @Override
    public HashMap<String, IModel> getiModelMap() {
        return loadModelMap(new LoginModel());
    }

    @Override
    public HashMap<String, IModel> loadModelMap(IModel... models) {
        HashMap<String, IModel> map = new HashMap<>();
        map.put("login", models[0]);
        return map;
    }
    @Override
    public void loadHead(String imgSrc) {
        ((LoginModel) getiModelMap().get("login")).loadHeader(imgSrc ,new LoginModel.loadHeaderInfoHint() {


            @Override
            public void successInfo(byte[] bytes) {
                getIView().getHeadSuccess(bytes);
            }

            @Override
            public void failInfo(String str) {
                getIView().getHeadFail(str);
            }
        });

    };
    @Override
        public void login(String userName, String passWord) {
            ((LoginModel) getiModelMap().get("login")).login(userName, passWord, new LoginModel.loginInfoHint() {

                @Override
                public void successInfo(LoginBean loginBean) {
                    getIView().toHomePageActivity(loginBean);
                }

                @Override
                public void failInfo(String str) {
                    getIView().loginFail(str);
                }
            }) ;


    };




}
