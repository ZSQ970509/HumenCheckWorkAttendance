package com.example.humencheckworkattendance.contact;

import com.example.humencheckworkattendance.bean.GrouperBean;

/**
 * Created by administration on 2017/9/4.
 */

public class InsertGroupContact {


    public interface InsertGroupView {
        void getGrouperSuccess(GrouperBean GrouperBeanList);
        void getGrouperFail(String failMsg);
        void saveGroupSuccess(String str);
        void saveGroupFail(String failMsg);
    }

    public interface InsertGroupPresenter {
       void getGrouper(String projectId);
        void saveGroup(String name,String reckonerName,String projId);
    }
}
