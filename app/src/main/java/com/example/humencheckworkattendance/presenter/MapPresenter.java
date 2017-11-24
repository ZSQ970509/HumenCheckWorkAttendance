package com.example.humencheckworkattendance.presenter;

import com.example.humencheckworkattendance.base.BasePresenter;
import com.example.humencheckworkattendance.contact.MapContact;
import com.example.humencheckworkattendance.model.MapModel;
import com.example.humencheckworkattendance.mvp.IModel;
import com.example.humencheckworkattendance.ui.activity.MapActivity;

import java.util.HashMap;

/**
 * Created by administration on 2017/9/4.
 */

public class MapPresenter extends BasePresenter<MapActivity> implements MapContact.MapPresenter{
    @Override
    public HashMap<String, IModel> getiModelMap() {
        return loadModelMap(new MapModel());
    }

    @Override
    public HashMap<String, IModel> loadModelMap(IModel... models) {
        HashMap<String, IModel> map = new HashMap<>();
        map.put("map", models[0]);
        return map;
    }





}
