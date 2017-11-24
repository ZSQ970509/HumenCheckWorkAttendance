package com.example.humencheckworkattendance.presenter;

import com.example.humencheckworkattendance.base.BasePresenter;
import com.example.humencheckworkattendance.bean.PlayCardTodayBean;
import com.example.humencheckworkattendance.contact.PlayCardTodayContact;
import com.example.humencheckworkattendance.model.PlayCardTodayModel;
import com.example.humencheckworkattendance.mvp.IModel;
import com.example.humencheckworkattendance.ui.activity.PlayCardTodayActivity;

import java.util.HashMap;

/**
 * Created by administration on 2017/9/4.
 */

public class PlayCardTodayPresenter extends BasePresenter<PlayCardTodayActivity> implements PlayCardTodayContact.PlayCardTodayPresenter{

    @Override
    public HashMap<String, IModel> getiModelMap() {
        return loadModelMap(new PlayCardTodayModel());
    }

    @Override
    public HashMap<String, IModel> loadModelMap(IModel... models) {
        HashMap<String, IModel> map = new HashMap<>();
        map.put("PlayCardToday", models[0]);
        return map;
    }
    @Override
    public void getDayAttendanceList(String project, String page) {
        ((PlayCardTodayModel) getiModelMap().get("PlayCardToday")).getDayAttendanceList(project, page,new PlayCardTodayModel.getDayAttendanceListInfoHint(){


            @Override
            public void successInfo(PlayCardTodayBean submitHumenBean) {
                getIView().getDayAttendanceListSuccess(submitHumenBean);
            }

            @Override
            public void failInfo(String str) {
                getIView().getDayAttendanceListFail(str);
            }
        });
    }
}
