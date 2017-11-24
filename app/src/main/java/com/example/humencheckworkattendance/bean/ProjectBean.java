package com.example.humencheckworkattendance.bean;

import java.util.ArrayList;

/**
 * Created by administration on 2017/10/10.
 */

public class ProjectBean {

    private java.util.ArrayList<ListBean> List;

    public ArrayList<ListBean> getList() {
        return List;
    }

    public void setList(ArrayList<ListBean> List) {
        this.List = List;
    }

    public static class ListBean {
        /**
         * name : 鼎丰国际广场工程
         * id : 10013
         */

        private String name;
        private int id;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }
}
