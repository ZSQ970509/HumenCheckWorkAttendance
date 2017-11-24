package com.example.humencheckworkattendance.presenter;

import com.example.humencheckworkattendance.base.BasePresenter;
import com.example.humencheckworkattendance.bean.GrouperBean;
import com.example.humencheckworkattendance.contact.InsertGroupContact;
import com.example.humencheckworkattendance.model.InsertGroupModel;
import com.example.humencheckworkattendance.mvp.IModel;
import com.example.humencheckworkattendance.ui.activity.InsertGroupActivity;

import java.util.HashMap;

/**
 * Created by administration on 2017/9/4.
 */

public class InsertGroupPresenter extends BasePresenter<InsertGroupActivity> implements InsertGroupContact.InsertGroupPresenter{
    @Override
    public HashMap<String, IModel> getiModelMap() {
        return loadModelMap(new InsertGroupModel());
    }

    @Override
    public HashMap<String, IModel> loadModelMap(IModel... models) {
        HashMap<String, IModel> map = new HashMap<>();
        map.put("InsertGroup", models[0]);
        return map;
    }
    @Override
    public void getGrouper(String projId) {
        ((InsertGroupModel) getiModelMap().get("InsertGroup")).getGrouper(projId,new InsertGroupModel.getGrouperInfoHint() {


            @Override
            public void successInfo(GrouperBean grouperBean) {
                getIView().getGrouperSuccess(grouperBean);
            }

            @Override
            public void failInfo(String str) {
                getIView().getGrouperFail(str);
            }
        });
    }

    @Override
    public void saveGroup(String name,String reckonerName,String projId) {
        ((InsertGroupModel) getiModelMap().get("InsertGroup")).saveGroup(name,reckonerName,projId,new InsertGroupModel.saveGroupInfoHint() {


            @Override
            public void successInfo(String s) {
                getIView().saveGroupSuccess(s);
            }

            @Override
            public void failInfo(String str) {
                getIView().saveGroupFail(str);
            }
        });
    }





}
