package com.example.humencheckworkattendance.contact;

import com.example.humencheckworkattendance.bean.ProjectBean;

/**
 * Created by administration on 2017/9/4.
 */

public class SelectProjectContact {
    public interface SelectProjectView {
        void SeachProjectSuccess(ProjectBean projectBean);
        void SeachProjectFail(String s);
    }

    public interface SelectProjectPresenter {
       void seachProject(String userId,String userTypes,String projName);
    }
}
