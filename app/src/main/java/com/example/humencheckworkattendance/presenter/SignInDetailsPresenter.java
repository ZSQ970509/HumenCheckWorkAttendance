package com.example.humencheckworkattendance.presenter;

import com.example.humencheckworkattendance.base.BasePresenter;
import com.example.humencheckworkattendance.bean.HistroyBean;
import com.example.humencheckworkattendance.contact.SignInDetailsContact;
import com.example.humencheckworkattendance.model.SignInDetailsModel;
import com.example.humencheckworkattendance.mvp.IModel;
import com.example.humencheckworkattendance.ui.activity.SignInDetailsActivity;

import java.util.HashMap;

/**
 * Created by administration on 2017/9/4.
 */

public class SignInDetailsPresenter extends BasePresenter<SignInDetailsActivity> implements SignInDetailsContact.SignInDetailsPresenter{
    @Override
    public HashMap<String, IModel> getiModelMap() {
        return loadModelMap(new SignInDetailsModel());
    }

    @Override
    public HashMap<String, IModel> loadModelMap(IModel... models) {
        HashMap<String, IModel> map = new HashMap<>();
        map.put("getHistroy", models[0]);
        return map;
    }

    @Override
    public void getHistroy(String startTime, String page, String memberId) {
        ((SignInDetailsModel) getiModelMap().get("getHistroy")).getHistroy(startTime, page,memberId,new SignInDetailsModel.getHistroyInfoHint(){


            @Override
            public void successInfo(HistroyBean histroyBean) {
                getIView().getHistroySuccess(histroyBean);
            }

            @Override
            public void failInfo(String str) {
                getIView().getHistroyFail(str);
            }
        }) ;
    }
}
