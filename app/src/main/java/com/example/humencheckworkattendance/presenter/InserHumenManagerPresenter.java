package com.example.humencheckworkattendance.presenter;


import com.example.humencheckworkattendance.base.BasePresenter;
import com.example.humencheckworkattendance.bean.EmtpBean;
import com.example.humencheckworkattendance.bean.EmtpRolesBean;
import com.example.humencheckworkattendance.bean.TeamListBean;
import com.example.humencheckworkattendance.bean.UserBean;
import com.example.humencheckworkattendance.contact.InsertHumenContact;
import com.example.humencheckworkattendance.model.InsertHumenModel;
import com.example.humencheckworkattendance.mvp.IModel;
import com.example.humencheckworkattendance.ui.activity.InsertHumenManagerActivity;

import java.util.HashMap;

/**
 * Created by administration on 2017/9/4.
 */

public class InserHumenManagerPresenter extends BasePresenter<InsertHumenManagerActivity> implements InsertHumenContact.InsertHumenPresenter {
    @Override
    public HashMap<String, IModel> getiModelMap() {
        return loadModelMap(new InsertHumenModel());
    }

    @Override
    public HashMap<String, IModel> loadModelMap(IModel... models) {
        HashMap<String, IModel> map = new HashMap<>();
        map.put("InsertHumen", models[0]);
        return map;
    }

    @Override
    public void getTeam(String projId) {
        ((InsertHumenModel) getiModelMap().get("InsertHumen")).getTeam(projId,new InsertHumenModel.getTeamInfoHint() {


            @Override
            public void successInfo(TeamListBean teamBeanList) {
                getIView().getTeamSuccess(teamBeanList);
            }

            @Override
            public void failInfo(String str) {
                getIView().getTeamFail(str);
            }
        });

    }

    @Override
    public void insertHumen(String userId, String idCard, String streamName, String mobile, String headImage, String iDCardPathImage,String iDCardBackPathImage, String emtpId, String rolesId,String gender, String projId, String validateCode, String streamTitle ,String statusType,String userTypeId) {
        ((InsertHumenModel) getiModelMap().get("InsertHumen")).insertHumen(userId,idCard,streamName,mobile,headImage,iDCardPathImage,iDCardBackPathImage,emtpId,rolesId,gender,projId,validateCode,streamTitle,statusType,userTypeId,new InsertHumenModel.InsertHumenInfoHint() {

            @Override
            public void successInfo(String str) {
                getIView().getInsertHumenSuccess(str);
            }

            @Override
            public void failInfo(String str) {
                getIView().getInsertHumenSuccessFail(str);
            }
        });
    }

    @Override
    public void checkHumenIsExist(String phone,String idcard) {
        ((InsertHumenModel) getiModelMap().get("InsertHumen")).checkHumenIsExist(phone,idcard ,new InsertHumenModel.checkHumenIsExistInfoHint() {

            @Override
            public void successInfo(String str) {
                getIView().getcheckHumenIsExistSuccess(str);
            }

            @Override
            public void failInfo(String str) {
                getIView().getcheckHumenIsExistFail(str);
            }
        });
    }

    @Override
    public void getEmtp() {
        ((InsertHumenModel) getiModelMap().get("InsertHumen")).getEmtp(new InsertHumenModel.getEmtpInfoHint() {

            @Override
            public void successInfo(EmtpBean emtpBean) {
                getIView().getEmtpSuccess(emtpBean);
            }

            @Override
            public void failInfo(String str) {
                getIView().getEmtpFail(str);
            }
        });
    }

    @Override
    public void getEmtpRoles(String emtpId) {
        ((InsertHumenModel) getiModelMap().get("InsertHumen")).getEmtpRoles(emtpId,new InsertHumenModel.getEmtpRoleInfoHint() {

            @Override
            public void successInfo(EmtpRolesBean emtpRolesBean) {
                getIView().getEmtpRolesSuccess(emtpRolesBean);
            }

            @Override
            public void failInfo(String str) {
                getIView().getEmtpRolesFail(str);
            }
        });
    }

    @Override
    public void getDictByType() {
        ((InsertHumenModel) getiModelMap().get("InsertHumen")).getDictByType(new InsertHumenModel.getDictByTypeInfoHint(){

            @Override
            public void successInfo(EmtpBean emtpBean) {
                getIView().getDictByTypeSuccess(emtpBean);
            }

            @Override
            public void failInfo(String str) {
                getIView().getDictByTypeFail(str);
            }
        });
    }
    @Override
    public void getStreamMemberByIdCard(String idCard){
        ((InsertHumenModel) getiModelMap().get("InsertHumen")).getStreamMemberByIdCard(idCard,new InsertHumenModel.getStreamMemberByIdCardInfoHint(){

            @Override
            public void successInfo(UserBean userBean) {
                getIView().getStreamMemberByIdCardSuccess(userBean);
            }

            @Override
            public void failInfo(String str) {
                getIView().getStreamMemberByIdCardFail(str);
            }
        });
    }
    @Override
    public void loadHead(String imgSrc) {
        ((InsertHumenModel) getiModelMap().get("InsertHumen")).loadHeader(imgSrc ,new InsertHumenModel.loadHeaderInfoHint() {


            @Override
            public void successInfo(byte[] bytes) {
                getIView().getHeadSuccess(bytes);
            }

            @Override
            public void failInfo(String str) {
                getIView().getHeadFail(str);
            }
        });

    };
}
