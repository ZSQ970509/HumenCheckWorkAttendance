package com.example.humencheckworkattendance.presenter;

import com.example.humencheckworkattendance.base.BasePresenter;
import com.example.humencheckworkattendance.bean.UploadBean;
import com.example.humencheckworkattendance.contact.HomePageContact;
import com.example.humencheckworkattendance.model.HomePageModel;
import com.example.humencheckworkattendance.mvp.IModel;
import com.example.humencheckworkattendance.ui.activity.HomePageActivity;

import java.util.HashMap;

/**
 * Created by administration on 2017/9/4.
 */

public class HomePagePresenter extends BasePresenter<HomePageActivity> implements HomePageContact.HomePagePresenter{

    @Override
    public HashMap<String, IModel> getiModelMap() {
        return loadModelMap(new HomePageModel());
    }

    @Override
    public HashMap<String, IModel> loadModelMap(IModel... models) {
        HashMap<String, IModel> map = new HashMap<>();
        map.put("HomePage", models[0]);
        return map;
    }

    @Override
    public void checkIsFrozen(String memberId,String projId) {
        ((HomePageModel) getiModelMap().get("HomePage")).checkIsFrozen(memberId,projId,new HomePageModel.checkIsFrozenInfoHint() {
            @Override
            public void successInfo(String s) {
                getIView().checkIsFrozenSuccess(s);
            }

            @Override
            public void failInfo(String str) {
                getIView().checkIsFrozenFail(str);
            }
        });
    }
    @Override
    public void playCard(String time, String memberId, String address,String type,String projId,String emtpId,String emtpRolesId, String lng, String lat,String attendanceUrl,String admUserId,String admUserName) {
        ((HomePageModel) getiModelMap().get("HomePage")).playCard(time, memberId, address,type,projId,emtpId,emtpRolesId,lng,lat,attendanceUrl,admUserId,admUserName,new HomePageModel.playCardInfoHint() {
            @Override
            public void successInfo(String s) {
                getIView().playCardSuccess(s);
            }

            @Override
            public void failInfo(String str) {
                getIView().playCardFail(str);
            }
        });
    }
    @Override
    public void uploadImage64(String imageBase64) {
        ((HomePageModel) getiModelMap().get("HomePage")).uploadImage64(imageBase64,new HomePageModel.uploadImageInfoHint() {


            @Override
            public void successInfo(UploadBean uploadBean) {
                getIView().toUploadImageSuccess(uploadBean);
            }

            @Override
            public void failInfo(String str) {

                getIView().toUploadImageFail(str);
            }
        });

    }
}
