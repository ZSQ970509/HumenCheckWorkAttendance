package com.example.humencheckworkattendance.contact;

import com.example.humencheckworkattendance.bean.CheckIsFrozenBean;
import com.example.humencheckworkattendance.bean.UploadBean;

/**
 * Created by administration on 2017/9/4.
 */

public class HomePageContact {
    public interface HomePageView {
        void playCardSuccess(String s);
        void playCardFail(String s);
        void toUploadImageSuccess(UploadBean uploadBean);
        void toUploadImageFail(String failMsg);
        void checkIsFrozenSuccess(CheckIsFrozenBean checkIsFrozenBean);
        void checkIsFrozenFail(String failMsg);
    }

    public interface HomePagePresenter {
        void playCard(String time, String memberId, String address,String type,String projId,String emtpId,String emtpRolesId, String lng, String lat,String attendanceUrl,String admUserId,String admUserName);
        void uploadImage64(String imageBase64);
        void checkIsFrozen(String memberId,String projId);
    }
}
