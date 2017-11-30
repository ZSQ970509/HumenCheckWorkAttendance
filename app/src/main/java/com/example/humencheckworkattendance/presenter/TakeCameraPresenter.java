package com.example.humencheckworkattendance.presenter;

import com.example.humencheckworkattendance.base.BasePresenter;
import com.example.humencheckworkattendance.contact.TakeCameraContact;
import com.example.humencheckworkattendance.mvp.IModel;
import com.example.humencheckworkattendance.ui.activity.TakeCameraActivity;

import java.util.HashMap;

/**
 * Created by Administrator on 2017-11-28.
 */

public class TakeCameraPresenter extends BasePresenter<TakeCameraActivity> implements TakeCameraContact.TakeCameraPresenter{
    @Override
    public HashMap<String, IModel> getiModelMap() {
        return null;
    }

    @Override
    public HashMap<String, IModel> loadModelMap(IModel... models) {
        return null;
    }
}
