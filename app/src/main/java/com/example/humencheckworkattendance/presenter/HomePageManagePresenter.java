package com.example.humencheckworkattendance.presenter;

import com.example.humencheckworkattendance.base.BasePresenter;
import com.example.humencheckworkattendance.contact.HomePageManageContact;
import com.example.humencheckworkattendance.model.HomePageModel;
import com.example.humencheckworkattendance.mvp.IModel;
import com.example.humencheckworkattendance.ui.activity.HomePageManageActivity;

import java.util.HashMap;

/**
 * Created by administration on 2017/9/4.
 */

public class HomePageManagePresenter extends BasePresenter<HomePageManageActivity> implements HomePageManageContact.HomePageManagePresenter{

    @Override
    public HashMap<String, IModel> getiModelMap() {
        return loadModelMap(new HomePageModel());
    }

    @Override
    public HashMap<String, IModel> loadModelMap(IModel... models) {
        HashMap<String, IModel> map = new HashMap<>();
        map.put("HomePageManage", models[0]);
        return map;
    }



}
