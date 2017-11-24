package com.example.humencheckworkattendance.bean;

import java.util.List;

/**
 * Created by administration on 2017/9/14.
 */

public class HistroyBean {


    /**
     * MothList : [{"time":"09-29 14:31:15","attendance":"报道失败","address":"中国福建省福州市鼓楼区工业路451","projectAddress":"福建省","projLng":"119.052618","projLat":"26.112406","projName":null,"typeString":"人脸检测失败"},{"time":"09-29 14:31:07","attendance":"报道失败","address":"中国福建省福州市鼓楼区工业路451","projectAddress":"福建省","projLng":"119.052618","projLat":"26.112406","projName":null,"typeString":"人脸检测失败"},{"time":"09-29 14:31:03","attendance":"报道失败","address":"中国福建省福州市鼓楼区工业路451","projectAddress":"福建省","projLng":"119.052618","projLat":"26.112406","projName":null,"typeString":"人脸检测失败"},{"time":"09-29 12:04:07","attendance":"报道失败","address":"中国福建省福州市鼓楼区工业路451","projectAddress":"福建省","projLng":"119.052618","projLat":"26.112406","projName":null,"typeString":"不在时间范围内打卡"},{"time":"09-29 11:59:05","attendance":"报道失败","address":"中国福建省福州市鼓楼区工业路451","projectAddress":"福建省","projLng":"119.052618","projLat":"26.112406","projName":null,"typeString":"超出打卡范围"},{"time":"09-29 11:59:01","attendance":"报道失败","address":"中国福建省福州市鼓楼区工业路451","projectAddress":"福建省","projLng":"119.052618","projLat":"26.112406","projName":null,"typeString":"超出打卡范围"},{"time":"09-29 11:58:57","attendance":"报道失败","address":"中国福建省福州市鼓楼区工业路451","projectAddress":"福建省","projLng":"119.052618","projLat":"26.112406","projName":null,"typeString":"超出打卡范围"},{"time":"09-29 11:58:53","attendance":"报道失败","address":"中国福建省福州市鼓楼区工业路451","projectAddress":"福建省","projLng":"119.052618","projLat":"26.112406","projName":null,"typeString":"超出打卡范围"},{"time":"09-29 11:57:59","attendance":"报道失败","address":"中国福建省福州市鼓楼区工业路451","projectAddress":"福建省","projLng":"119.052618","projLat":"26.112406","projName":null,"typeString":"超出打卡范围"},{"time":"09-29 11:57:52","attendance":"报道失败","address":"中国福建省福州市鼓楼区工业路451","projectAddress":"福建省","projLng":"119.052618","projLat":"26.112406","projName":null,"typeString":"超出打卡范围"}]
     * total : 36
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
         * time : 09-29 14:31:15
         * attendance : 报道失败
         * address : 中国福建省福州市鼓楼区工业路451
         * projectAddress : 福建省
         * projLng : 119.052618
         * projLat : 26.112406
         * projName : null
         * typeString : 人脸检测失败
         */

        private String time;
        private String attendance;
        private String address;
        private String projectAddress;
        private String projLng;
        private String projLat;
        private Object projName;
        private String typeString;

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getAttendance() {
            return attendance;
        }

        public void setAttendance(String attendance) {
            this.attendance = attendance;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getProjectAddress() {
            return projectAddress;
        }

        public void setProjectAddress(String projectAddress) {
            this.projectAddress = projectAddress;
        }

        public String getProjLng() {
            return projLng;
        }

        public void setProjLng(String projLng) {
            this.projLng = projLng;
        }

        public String getProjLat() {
            return projLat;
        }

        public void setProjLat(String projLat) {
            this.projLat = projLat;
        }

        public Object getProjName() {
            return projName;
        }

        public void setProjName(Object projName) {
            this.projName = projName;
        }

        public String getTypeString() {
            return typeString;
        }

        public void setTypeString(String typeString) {
            this.typeString = typeString;
        }
    }
}
