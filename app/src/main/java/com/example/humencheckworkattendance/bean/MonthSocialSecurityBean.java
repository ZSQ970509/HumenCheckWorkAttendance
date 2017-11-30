package com.example.humencheckworkattendance.bean;

import java.util.List;

/**
 * Created by Administrator on 2017-11-28.
 */

public class MonthSocialSecurityBean {


    /**
     * MothList : [{"StreamName":"黄世标","StreamId":250,"Status":1},{"StreamName":"黄世标","StreamId":250,"Status":1},{"StreamName":"黄世标","StreamId":250,"Status":1},{"StreamName":"黄世标","StreamId":250,"Status":1},{"StreamName":"黄世标","StreamId":250,"Status":1},{"StreamName":"黄世标","StreamId":250,"Status":1},{"StreamName":"黄世标","StreamId":250,"Status":1}]
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
         * StreamId : 250
         * Status : 1
         */

        private String StreamName;
        private String StreamId;
        private int Status;

        public String getStreamName() {
            return StreamName;
        }

        public void setStreamName(String StreamName) {
            this.StreamName = StreamName;
        }

        public String getStreamId() {
            return StreamId;
        }

        public void setStreamId(String StreamId) {
            this.StreamId = StreamId;
        }

        public int getStatus() {
            return Status;
        }

        public void setStatus(int Status) {
            this.Status = Status;
        }
    }
}
