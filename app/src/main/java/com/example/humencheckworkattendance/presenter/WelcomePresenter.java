package com.example.humencheckworkattendance.presenter;

import com.example.humencheckworkattendance.base.BasePresenter;
import com.example.humencheckworkattendance.bean.ModelBean;
import com.example.humencheckworkattendance.bean.UpdateBean;
import com.example.humencheckworkattendance.contact.WelcomeContact;
import com.example.humencheckworkattendance.model.WelcomeModel;
import com.example.humencheckworkattendance.mvp.IModel;
import com.example.humencheckworkattendance.ui.activity.WelcomeActivity;
import com.zhy.http.okhttp.utils.L;

import java.util.HashMap;

/**
 * Created by administration on 2017/9/4.
 */

public class WelcomePresenter extends BasePresenter<WelcomeActivity> implements WelcomeContact.WelcomePresenter{

    @Override
    public HashMap<String, IModel> getiModelMap() {
        return loadModelMap(new WelcomeModel());
    }

    @Override
    public HashMap<String, IModel> loadModelMap(IModel... models) {
        HashMap<String, IModel> map = new HashMap<>();
        map.put("Welcome", models[0]);
        return map;
    }

    @Override
    public void getVersion(String updateVersionCode) {
        ((WelcomeModel) getiModelMap().get("Welcome")).getVersion(updateVersionCode,new WelcomeModel.getVersionInfoHint() {
            @Override
            public void successInfo(UpdateBean updateBean) {
                getIView().getVersionSuccess(updateBean);
            }

            @Override
            public void failInfo(String str) {
                getIView().getVersionFail(str);
            }
        });
    }

    @Override
    public void getIsExistModel(String model) {
        L.e("平板型号："+model);
        ((WelcomeModel) getiModelMap().get("Welcome")).getIsExistIMEI(model,new WelcomeModel.getIsExistIMEIInfoHint() {
            @Override
            public void successInfo(String msg ) {
                getIView().getModelSuccess(msg);
            }

            @Override
            public void failInfo(String str) {
                getIView().getModelFail(str);
            }
        });
    }

}
