package com.example.humencheckworkattendance.bean;

import java.io.Serializable;

/**
 * Created by administration on 2017/9/26.
 */

public class ModelBean implements Serializable{


    /**
     * constraint : true
     * new_version : 2
     * new_user_version : 2.0
     * apk_file_url : http://120.35.11.49:26969/UpdatePackage/201709261540584748.apk
     * update_log : 1.测试1 2.测试2 3.测试3 4.测试4
     * target_size : 39361036
     */

    private boolean constraint;
    private int new_version;
    private String new_user_version;
    private String apk_file_url;
    private String update_log;
    private String target_size;

    public boolean isConstraint() {
        return constraint;
    }

    public void setConstraint(boolean constraint) {
        this.constraint = constraint;
    }

    public int getNew_version() {
        return new_version;
    }

    public void setNew_version(int new_version) {
        this.new_version = new_version;
    }

    public String getNew_user_version() {
        return new_user_version;
    }

    public void setNew_user_version(String new_user_version) {
        this.new_user_version = new_user_version;
    }

    public String getApk_file_url() {
        return apk_file_url;
    }

    public void setApk_file_url(String apk_file_url) {
        this.apk_file_url = apk_file_url;
    }

    public String getUpdate_log() {
        return update_log;
    }

    public void setUpdate_log(String update_log) {
        this.update_log = update_log;
    }

    public String getTarget_size() {
        return target_size;
    }

    public void setTarget_size(String target_size) {
        this.target_size = target_size;
    }
}
