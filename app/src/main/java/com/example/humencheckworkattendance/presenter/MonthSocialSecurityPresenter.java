package com.example.humencheckworkattendance.presenter;

import com.example.humencheckworkattendance.base.BaseHttpResult;
import com.example.humencheckworkattendance.base.BasePresenter;
import com.example.humencheckworkattendance.bean.MonthSocialSecurityBean;
import com.example.humencheckworkattendance.contact.MonthSocialSecurityContact;
import com.example.humencheckworkattendance.model.MonthSocialSecurityModel;
import com.example.humencheckworkattendance.mvp.IModel;
import com.example.humencheckworkattendance.ui.activity.MonthSocialSecurityActivity;

import java.util.HashMap;

import cn.cloudwalk.libproject.util.LogUtil;

/**
 * Created by Administrator on 2017-11-28.
 */

public class MonthSocialSecurityPresenter extends BasePresenter<MonthSocialSecurityActivity> implements MonthSocialSecurityContact.MonthSocialSecurityPresenter{
    @Override
    public void getMonthSocialSecurityList(String project, String page) {
        LogUtil.logE("执行了!!!!!!!!getMonthSocialSecurityList");
        ((MonthSocialSecurityModel) getiModelMap().get("MonthSocialSecurity")).getMonthSocialSecurityList(project, page,new MonthSocialSecurityModel.getMonthSocialSecurityListInfoHint(){
            @Override
            public void successInfo(MonthSocialSecurityBean monthSocialSecurityBean) {
                getIView().getMonthSocialSecurityListSuccess(monthSocialSecurityBean);
            }

            @Override
            public void failInfo(String str) {
                getIView().getMonthSocialSecurityListFail(str);
            }
        });
    }

    @Override
    public void addSocialSecurity(String proId, String userId ,String imgUrl, String admUserId , String admUserName) {
        ((MonthSocialSecurityModel) getiModelMap().get("MonthSocialSecurity")).addSocialSecurity(proId, userId,imgUrl,admUserId,admUserName,new MonthSocialSecurityModel.addSocialSecurityInfoHint(){
            @Override
            public void successInfo(String empty) {
                getIView().addSocialSecuritySuccess(empty);
            }

            @Override
            public void failInfo(String str) {
                getIView().addSocialSecurityFail(str);
            }
        });
    }

    @Override
    public HashMap<String, IModel> getiModelMap() {
        return loadModelMap(new MonthSocialSecurityModel());
    }

    @Override
    public HashMap<String, IModel> loadModelMap(IModel... models) {
        HashMap<String, IModel> map = new HashMap<>();
        map.put("MonthSocialSecurity", models[0]);
        return map;
    }
}
