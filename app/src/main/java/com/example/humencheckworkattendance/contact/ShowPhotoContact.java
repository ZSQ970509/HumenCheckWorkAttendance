package com.example.humencheckworkattendance.contact;

import com.example.humencheckworkattendance.bean.IdCardBean;
import com.example.humencheckworkattendance.bean.UploadBean;

/**
 * Created by administration on 2017/9/4.
 */

public class ShowPhotoContact {
    public interface ShowPhotoView {
        void toUploadImageSuccess(UploadBean uploadBean);
        void toUploadImageFail(String failMsg);
        void toUploadImageIdCardSuccess(IdCardBean idCardBean);
        void toUploadImageIdCardFail(String failMsg);
    }

    public interface ShowPhotoPresenter {
        void uploadImage64(String imageBase64);
        void uploadImage64IdCard(String imageBase64);
    }
}
