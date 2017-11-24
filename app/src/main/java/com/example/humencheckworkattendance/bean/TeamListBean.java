package com.example.humencheckworkattendance.bean;

import java.util.ArrayList;

/**
 * Created by administration on 2017/9/19.
 */

public class TeamListBean {

    private ArrayList<ListBean> List;

    public ArrayList<ListBean> getList() {
        return List;
    }

    public void setList(ArrayList<ListBean> List) {
        this.List = List;
    }

    public static class ListBean {
        /**
         * id : 53
         * name : 水泥班
         */

        private int id;
        private String name;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
