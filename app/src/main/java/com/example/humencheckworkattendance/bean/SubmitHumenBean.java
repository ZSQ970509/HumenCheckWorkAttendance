package com.example.humencheckworkattendance.bean;

import java.util.List;

/**
 * Created by administration on 2017/10/25.
 */

public class SubmitHumenBean {
    /**
     * MothList : [{"streamName":"小花","idCard":"210422198301017910","status":0,"streamId":205},{"streamName":"天天","idCard":"51323319910119525x","status":0,"streamId":204},{"streamName":"花花","idCard":"510108199907052843","status":0,"streamId":203},{"streamName":"菜菜","idCard":"452123198510084657 ","status":0,"streamId":202}]
     * total : 4
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
         * streamName : 小花
         * idCard : 210422198301017910
         * status : 0
         * streamId : 205
         */

        private String streamName;
        private String idCard;
        private int status;
        private int streamId;

        public String getStreamName() {
            return streamName;
        }

        public void setStreamName(String streamName) {
            this.streamName = streamName;
        }

        public String getIdCard() {
            return idCard;
        }

        public void setIdCard(String idCard) {
            this.idCard = idCard;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getStreamId() {
            return streamId;
        }

        public void setStreamId(int streamId) {
            this.streamId = streamId;
        }
    }





}
