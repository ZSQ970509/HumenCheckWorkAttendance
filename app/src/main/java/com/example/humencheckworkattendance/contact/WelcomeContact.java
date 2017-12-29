package com.example.humencheckworkattendance.contact;

import com.example.humencheckworkattendance.bean.ModelBean;
import com.example.humencheckworkattendance.bean.UpdateBean;

/**
 * Created by administration on 2017/9/4.
 */

public class WelcomeContact {


    public interface WelcomeView {
        void getVersionSuccess(UpdateBean updateBean);
        void getVersionFail(String failMsg);

        void getModelSuccess(String msg);
        void getModelFail(String failMsg);
    }

    public interface WelcomePresenter {
        void getVersion(String updateVersionCode);
        void getIsExistModel(String model);
    }
}
