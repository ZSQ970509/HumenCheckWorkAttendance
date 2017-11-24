package com.example.humencheckworkattendance.bean;

import java.util.List;

/**
 * Created by administration on 2017/10/25.
 */

public class PlayCardTodayBean {


    /**
     * MothList : [{"StreamName":"黄世标","AttendanceMorning":2,"AttendanceAfternoon":2},{"StreamName":"丑","AttendanceMorning":2,"AttendanceAfternoon":2},{"StreamName":"张三","AttendanceMorning":2,"AttendanceAfternoon":2},{"StreamName":"茹浩恒","AttendanceMorning":2,"AttendanceAfternoon":2},{"StreamName":"田根群","AttendanceMorning":2,"AttendanceAfternoon":2},{"StreamName":"黄世标","AttendanceMorning":2,"AttendanceAfternoon":2},{"StreamName":"张翔","AttendanceMorning":2,"AttendanceAfternoon":2}]
     * total : 7
     */

    private int total;
    private List<MothListBean> MothList;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<MothListBean> getMothList() {
        return MothList;
    }

    public void setMothList(List<MothListBean> MothList) {
        this.MothList = MothList;
    }

    public static class MothListBean {
        /**
         * StreamName : 黄世标
         * AttendanceMorning : 2
         * AttendanceAfternoon : 2
         */

        private String StreamName;
        private int AttendanceMorning;
        private int AttendanceAfternoon;

        public String getStreamName() {
            return StreamName;
        }

        public void setStreamName(String StreamName) {
            this.StreamName = StreamName;
        }

        public int getAttendanceMorning() {
            return AttendanceMorning;
        }

        public void setAttendanceMorning(int AttendanceMorning) {
            this.AttendanceMorning = AttendanceMorning;
        }

        public int getAttendanceAfternoon() {
            return AttendanceAfternoon;
        }

        public void setAttendanceAfternoon(int AttendanceAfternoon) {
            this.AttendanceAfternoon = AttendanceAfternoon;
        }
    }
}
