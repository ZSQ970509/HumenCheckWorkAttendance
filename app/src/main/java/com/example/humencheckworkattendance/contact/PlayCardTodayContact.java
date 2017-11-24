package com.example.humencheckworkattendance.contact;

import com.example.humencheckworkattendance.bean.PlayCardTodayBean;

/**
 * Created by administration on 2017/9/4.
 */

public class PlayCardTodayContact {


    public interface PlayCardTodayView {
        void getDayAttendanceListSuccess(PlayCardTodayBean playCardTodayBean);
        void getDayAttendanceListFail(String failMsg);
    }

    public interface PlayCardTodayPresenter {
        void getDayAttendanceList(String project, String page);
    }
}
