package com.example.humencheckworkattendance.presenter;

import com.example.humencheckworkattendance.base.BasePresenter;
import com.example.humencheckworkattendance.bean.GrouperBean;
import com.example.humencheckworkattendance.contact.InsertGrouperContact;
import com.example.humencheckworkattendance.model.InsertGrouperModel;
import com.example.humencheckworkattendance.mvp.IModel;
import com.example.humencheckworkattendance.ui.activity.InsertGrouperActivity;

import java.util.HashMap;

/**
 * Created by administration on 2017/9/4.
 */

public class InsertGrouperPresenter extends BasePresenter<InsertGrouperActivity> implements InsertGrouperContact.InsertGrouperPresenter {
    @Override
    public HashMap<String, IModel> getiModelMap() {
        return loadModelMap(new InsertGrouperModel());
    }

    @Override
    public HashMap<String, IModel> loadModelMap(IModel... models) {
        HashMap<String, IModel> map = new HashMap<>();
        map.put("InsertGrouper", models[0]);
        return map;
    }


    @Override
    public void getGroup(String projectId) {
        ((InsertGrouperModel) getiModelMap().get("InsertGrouper")).getGroup(projectId,new InsertGrouperModel.getGroupInfoHint() {


            @Override
            public void successInfo(GrouperBean grouperBean) {
                getIView().getGroupSuccess(grouperBean);
            }

            @Override
            public void failInfo(String str) {
                getIView().getGroupFail(str);
            }
        });
    }

    @Override
    public void saveGrouper(String projId, String UserName, String teams, String teamText, String name, String phone, String idCard) {
        ((InsertGrouperModel) getiModelMap().get("InsertGrouper")).saveGrouper(projId, UserName, teams, teamText, name, phone, idCard, new InsertGrouperModel.saveGrouperInfoHint() {
            @Override
            public void successInfo(String s) {
                getIView().saveGrouperSuccess(s);
            }

            @Override
            public void failInfo(String str) {
                getIView().saveGrouperFail(str);
            }
        });
    }
}