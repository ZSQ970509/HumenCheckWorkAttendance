package com.example.humencheckworkattendance.presenter;

import com.example.humencheckworkattendance.base.BasePresenter;
import com.example.humencheckworkattendance.contact.MoreContact;
import com.example.humencheckworkattendance.model.MoreModel;
import com.example.humencheckworkattendance.mvp.IModel;
import com.example.humencheckworkattendance.ui.activity.MoreActivity;

import java.util.HashMap;

/**
 * Created by administration on 2017/9/4.
 */

public class MorePresenter extends BasePresenter<MoreActivity> implements MoreContact.MorePresenter{

    @Override
    public HashMap<String, IModel> getiModelMap() {
        return loadModelMap(new MoreModel());
    }

    @Override
    public HashMap<String, IModel> loadModelMap(IModel... models) {
        HashMap<String, IModel> map = new HashMap<>();
        map.put("More", models[0]);
        return map;
    }



}
