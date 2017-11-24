package com.example.humencheckworkattendance.presenter;

import com.example.humencheckworkattendance.base.BasePresenter;
import com.example.humencheckworkattendance.bean.SubmitHumenBean;
import com.example.humencheckworkattendance.contact.SubmitHumenMessageContact;
import com.example.humencheckworkattendance.model.SubmitHumenMessageModel;
import com.example.humencheckworkattendance.mvp.IModel;
import com.example.humencheckworkattendance.ui.activity.SubmitHumenMessageActivity;

import java.util.HashMap;

/**
 * Created by administration on 2017/9/4.
 */

public class SubmitHumenMessagePresenter extends BasePresenter<SubmitHumenMessageActivity> implements SubmitHumenMessageContact.SubmitHumenMessagePresenter{
    @Override
    public HashMap<String, IModel> getiModelMap() {
        return loadModelMap(new SubmitHumenMessageModel());
    }

    @Override
    public HashMap<String, IModel> loadModelMap(IModel... models) {
        HashMap<String, IModel> map = new HashMap<>();
        map.put("submitHumenMessage", models[0]);
        return map;
    }
    @Override
    public void getStreamMemberList(String memberId, String page, String pageSize) {
        ((SubmitHumenMessageModel) getiModelMap().get("submitHumenMessage")).getStreamMemberList(memberId, page,pageSize,new SubmitHumenMessageModel.getStreamMemberListInfoHint(){


            @Override
            public void successInfo(SubmitHumenBean submitHumenBean) {
                getIView().getStreamMemberListSuccess(submitHumenBean);
            }

            @Override
            public void failInfo(String str) {
                getIView().getStreamMemberListFail(str);
            }
        });
    }
    @Override
    public void subitStreamMember(String id) {
        ((SubmitHumenMessageModel) getiModelMap().get("submitHumenMessage")).subitStreamMember(id,new SubmitHumenMessageModel.subitStreamMemberInfoHint(){


            @Override
            public void successInfo(String s) {
                getIView().subitStreamMemberSuccess(s);
            }

            @Override
            public void failInfo(String str) {
                getIView().subitStreamMemberFail(str);
            }
        });
    }
    @Override
    public void rollBackMember(String id){
        ((SubmitHumenMessageModel) getiModelMap().get("submitHumenMessage")).rollBackMember(id,new SubmitHumenMessageModel.rollBackMemberInfoHint(){


            @Override
            public void successInfo(String s) {
                getIView().rollBackMemberSuccess(s);
            }

            @Override
            public void failInfo(String str) {
                getIView().rollBackMemberFail(str);
            }
        });
    }
    @Override
    public void checkDeleteStreamMember(String id){
        ((SubmitHumenMessageModel) getiModelMap().get("submitHumenMessage")).checkDeleteStreamMember(id,new SubmitHumenMessageModel.checkDeleteStreamMemberInfoHint(){


            @Override
            public void successInfo(String s) {
                getIView().checkDeleteStreamMemberSuccess(s);
            }

            @Override
            public void failInfo(String str) {
                getIView().checkDeleteStreamMemberFail(str);
            }
        });
    }
    @Override
    public void deleteStreamMember(String id){
        ((SubmitHumenMessageModel) getiModelMap().get("submitHumenMessage")).deleteStreamMember(id,new SubmitHumenMessageModel.deleteStreamMemberInfoHint(){


            @Override
            public void successInfo(String s) {
                getIView().deleteStreamMemberSuccess(s);
            }

            @Override
            public void failInfo(String str) {
                getIView().deleteStreamMemberFail(str);
            }
        });
    }
    @Override
    public void getErrorStreamMember(String id){
        ((SubmitHumenMessageModel) getiModelMap().get("submitHumenMessage")).getErrorStreamMember(id,new SubmitHumenMessageModel.getErrorStreamMemberInfoHint(){


            @Override
            public void successInfo(String s) {
                getIView().getErrorStreamMemberSuccess(s);
            }

            @Override
            public void failInfo(String str) {
                getIView().getErrorStreamMemberFail(str);
            }
        });
    }
}
