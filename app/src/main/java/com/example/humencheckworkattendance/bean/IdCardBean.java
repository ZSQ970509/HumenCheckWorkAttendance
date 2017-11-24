package com.example.humencheckworkattendance.bean;

import java.io.Serializable;

/**
 * Created by administration on 2017/9/28.
 */

public class IdCardBean implements Serializable {

    /**
     * picUrl : /UpFile/HeadPic/2017/1110/20171110114956.jpg
     * name : 张翔
     * gender : 男
     * address : 福建省福州市仓山区金山大道618号桔园洲台江园19座
     * idnumber : 360103198301010732
     */

    private String picUrl;
    private String name;
    private String gender;
    private String address;
    private String idnumber;

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getIdnumber() {
        return idnumber;
    }

    public void setIdnumber(String idnumber) {
        this.idnumber = idnumber;
    }
}
