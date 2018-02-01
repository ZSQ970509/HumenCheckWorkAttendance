package com.example.humencheckworkattendance.presenter;

import com.example.humencheckworkattendance.base.BasePresenter;
import com.example.humencheckworkattendance.contact.ChangePassWordContact;
import com.example.humencheckworkattendance.model.ChangePassWordModel;
import com.example.humencheckworkattendance.mvp.IModel;
import com.example.humencheckworkattendance.ui.activity.ChangePassWordActivity;

import java.util.HashMap;

/**
 * Created by administration on 2017/9/4.
 */

public class ChangePassWordPresenter extends BasePresenter<ChangePassWordActivity> implements ChangePassWordContact.ChangePassWordPresenter{

    @Override
    public HashMap<String, IModel> getiModelMap() {
        return loadModelMap(new ChangePassWordModel());
    }

    @Override
    public HashMap<String, IModel> loadModelMap(IModel... models) {
        HashMap<String, IModel> map = new HashMap<>();
        map.put("ChangePassWord", models[0]);
        return map;
    }


    @Override
    public void ChangePassWord(String oldPassword, String newPassword, String account, String userType, String MemberId, String UserName) {
        ((ChangePassWordModel) getiModelMap().get("ChangePassWord")).changePassWord(oldPassword,newPassword,account,userType,MemberId, UserName,new ChangePassWordModel.ChangePassWordInfoHint() {


            @Override
            public void successInfo(String s) {
                getIView().ChangePassWordSuccess(s);
            }

            @Override
            public void failInfo(String str) {
                getIView().ChangePassWordFail(str);
            }
        });
    }
}
