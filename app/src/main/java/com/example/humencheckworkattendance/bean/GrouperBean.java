package com.example.humencheckworkattendance.bean;

import java.util.ArrayList;

/**
 * Created by administration on 2017/9/22.
 */

public class GrouperBean {

    private ArrayList<ListBean> List;

    public ArrayList<ListBean> getList() {
        return List;
    }

    public void setList(ArrayList<ListBean> List) {
        this.List = List;
    }

    public static class ListBean {
        /**
         * name : 小吴
         * id : 3
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
