package com.example.humencheckworkattendance.presenter;

import com.example.humencheckworkattendance.base.BasePresenter;
import com.example.humencheckworkattendance.bean.ProjectBean;
import com.example.humencheckworkattendance.contact.SelectProjectContact;
import com.example.humencheckworkattendance.model.SelectProjectModel;
import com.example.humencheckworkattendance.mvp.IModel;
import com.example.humencheckworkattendance.ui.activity.SelectProjectActivity;

import java.util.HashMap;

/**
 * Created by administration on 2017/9/4.
 */

public class SelectProjectPresenter extends BasePresenter<SelectProjectActivity> implements SelectProjectContact.SelectProjectPresenter{

    @Override
    public HashMap<String, IModel> getiModelMap() {
        return loadModelMap(new SelectProjectModel());
    }

    @Override
    public HashMap<String, IModel> loadModelMap(IModel... models) {
        HashMap<String, IModel> map = new HashMap<>();
        map.put("SelectProject", models[0]);
        return map;
    }



    @Override
    public void seachProject(String userId,String userTypes,String projName) {
        ((SelectProjectModel) getiModelMap().get("SelectProject")).searchProject(userId,userTypes,projName,new SelectProjectModel.getSearchProjectInfoHint() {

            @Override
            public void successInfo(ProjectBean projectBean) {
                getIView().SeachProjectSuccess(projectBean);
            }

            @Override
            public void failInfo(String str) {
                getIView().SeachProjectFail(str);
            }
        });
    }
}
