package com.example.humencheckworkattendance.contact;

import com.example.humencheckworkattendance.bean.SubmitHumenBean;

/**
 * Created by administration on 2017/9/4.
 */

public class SubmitHumenMessageContact {
    public interface SubmitHumenMessageView {
       void getStreamMemberListSuccess(SubmitHumenBean submitHumenBean);
       void getStreamMemberListFail(String failMsg);
        void subitStreamMemberSuccess(String s);
        void subitStreamMemberFail(String failMsg);
        void rollBackMemberSuccess(String s);
        void rollBackMemberFail(String failMsg);
        void checkDeleteStreamMemberSuccess(String s);
        void checkDeleteStreamMemberFail(String failMsg);
        void deleteStreamMemberSuccess(String s);
        void deleteStreamMemberFail(String failMsg);
        void getErrorStreamMemberSuccess(String s);
        void getErrorStreamMemberFail(String failMsg);
    }

    public interface SubmitHumenMessagePresenter {
        void getStreamMemberList(String memberId, String page, String pageSize);
        void  subitStreamMember(String id,String admUserId, String admUserName);
        void rollBackMember(String id,String admUserId, String admUserName);
        void checkDeleteStreamMember(String id);
        void deleteStreamMember(String id,String admUserId, String admUserName);
        void getErrorStreamMember(String id);
    }
}
