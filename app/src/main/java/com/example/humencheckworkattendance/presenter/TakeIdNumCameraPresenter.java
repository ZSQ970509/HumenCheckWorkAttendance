package com.example.humencheckworkattendance.presenter;

import com.example.humencheckworkattendance.base.BasePresenter;
import com.example.humencheckworkattendance.contact.TakeIdNumCameraContact;
import com.example.humencheckworkattendance.model.LoginModel;
import com.example.humencheckworkattendance.mvp.IModel;
import com.example.humencheckworkattendance.ui.activity.TakeIdNumCameraActivity;

import java.util.HashMap;

/**
 * Created by administration on 2017/9/4.
 */

public class TakeIdNumCameraPresenter extends BasePresenter<TakeIdNumCameraActivity> implements TakeIdNumCameraContact.TakeIdNumCameraPresenter{
    @Override
    public HashMap<String, IModel> getiModelMap() {
        return loadModelMap(new LoginModel());
    }

    @Override
    public HashMap<String, IModel> loadModelMap(IModel... models) {
        HashMap<String, IModel> map = new HashMap<>();
        map.put("TakeIdNumCamera", models[0]);
        return map;
    }




}
