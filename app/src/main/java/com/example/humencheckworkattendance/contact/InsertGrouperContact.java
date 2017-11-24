package com.example.humencheckworkattendance.contact;

import com.example.humencheckworkattendance.bean.GrouperBean;

/**
 * Created by administration on 2017/9/4.
 */

public class InsertGrouperContact {


    public interface InsertGrouperView {
        void getGroupSuccess(GrouperBean GrouperBeanList);
        void getGroupFail(String failMsg);
        void saveGrouperSuccess(String str);
        void saveGrouperFail(String failMsg);
    }

    public interface InsertGrouperPresenter {
        void getGroup(String projectId);
        void saveGrouper(String projId,String UserName,String teams,String teamText,String name,String phone,String idCard);
    }
}
