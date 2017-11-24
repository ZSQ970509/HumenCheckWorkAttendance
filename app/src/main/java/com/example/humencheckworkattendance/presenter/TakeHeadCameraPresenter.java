package com.example.humencheckworkattendance.presenter;

import com.example.humencheckworkattendance.base.BasePresenter;
import com.example.humencheckworkattendance.contact.TakeHeadCameraContact;
import com.example.humencheckworkattendance.model.LoginModel;
import com.example.humencheckworkattendance.mvp.IModel;
import com.example.humencheckworkattendance.ui.activity.TakeHeadCameraActivity;

import java.util.HashMap;

/**
 * Created by administration on 2017/9/4.
 */

public class TakeHeadCameraPresenter extends BasePresenter<TakeHeadCameraActivity> implements TakeHeadCameraContact.TakeHeadCameraPresenter{
    @Override
    public HashMap<String, IModel> getiModelMap() {
        return loadModelMap(new LoginModel());
    }

    @Override
    public HashMap<String, IModel> loadModelMap(IModel... models) {
        HashMap<String, IModel> map = new HashMap<>();
        map.put("TakeHeadCamera", models[0]);
        return map;
    }




}
