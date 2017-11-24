package com.example.humencheckworkattendance.presenter;

import com.example.humencheckworkattendance.base.BasePresenter;
import com.example.humencheckworkattendance.bean.IdCardBean;
import com.example.humencheckworkattendance.bean.UploadBean;
import com.example.humencheckworkattendance.contact.ShowPhotoContact;
import com.example.humencheckworkattendance.model.ShowPhotoModel;
import com.example.humencheckworkattendance.mvp.IModel;
import com.example.humencheckworkattendance.ui.activity.ShowPhotoActivity;

import java.util.HashMap;

/**
 * Created by administration on 2017/9/4.
 */

public class ShowPhotoPresenter extends BasePresenter<ShowPhotoActivity> implements ShowPhotoContact.ShowPhotoPresenter{
    @Override
    public HashMap<String, IModel> getiModelMap() {
        return loadModelMap(new ShowPhotoModel());
    }

    @Override
    public HashMap<String, IModel> loadModelMap(IModel... models) {
        HashMap<String, IModel> map = new HashMap<>();
        map.put("ShowPhoto", models[0]);
        return map;
    }


    @Override
    public void uploadImage64(String imageBase64) {
        ((ShowPhotoModel) getiModelMap().get("ShowPhoto")).uploadImage64(imageBase64,new ShowPhotoModel.uploadImageInfoHint() {


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
    @Override
    public void uploadImage64IdCard(String imageBase64,String idcard) {
        ((ShowPhotoModel) getiModelMap().get("ShowPhoto")).uploadImage64IdCard(imageBase64,idcard,new ShowPhotoModel.uploadImageIdCardInfoHint() {


            @Override
            public void successInfo(IdCardBean idCardBean) {
                getIView().toUploadImageIdCardSuccess(idCardBean);
            }

            @Override
            public void failInfo(String str) {

                getIView().toUploadImageIdCardFail(str);
            }
        });

    }

}