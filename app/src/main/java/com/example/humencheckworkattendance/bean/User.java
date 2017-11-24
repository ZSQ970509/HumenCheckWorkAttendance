
package com.example.humencheckworkattendance.bean;

import java.io.Serializable;


public class User implements Serializable {

    private Long code;//操作码,0表示成功
    private String msg;//提示信息
    private Long uid;//用户id
    private String avatar;//用户头像
    private int sex;//用户性别
    private String province;//用户所在身份
    private String city;//用户所在城市
    private String county;//用户所在地区
    private String deliveryaddress ;//用户收货地址
    private String mobile ;//用户手机号

    private Boolean mDefault ;//默认选择

    public Boolean getmDefault() {
        return mDefault;
    }

    public void setmDefault(Boolean mDefault) {
        this.mDefault = mDefault;
    }

    public String getDeliveryaddress() {
        return deliveryaddress;
    }

    public void setDeliveryaddress(String deliveryaddress) {
        this.deliveryaddress = deliveryaddress;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    private String truename;//用户真实姓名
    private int homesite;//用户主页
    private int papertype;//证件类型

    private String identificationnumber;//证件号码
    private String certificatespicture;//证件图片

    public Long getCode() {
        return code;
    }

    public void setCode(Long code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getTruename() {
        return truename;
    }

    public void setTruename(String truename) {
        this.truename = truename;
    }

    public int getHomesite() {
        return homesite;
    }

    public void setHomesite(int homesite) {
        this.homesite = homesite;
    }

    public int getPapertype() {
        return papertype;
    }

    public void setPapertype(int papertype) {
        this.papertype = papertype;
    }

    public String getIdentificationnumber() {
        return identificationnumber;
    }

    public void setIdentificationnumber(String identificationnumber) {
        this.identificationnumber = identificationnumber;
    }

    public String getCertificatespicture() {
        return certificatespicture;
    }

    public void setCertificatespicture(String certificatespicture) {
        this.certificatespicture = certificatespicture;
    }

    @Override
    public String toString() {
        return "User{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", uid=" + uid +
                ", avatar='" + avatar + '\'' +
                ", sex=" + sex +
                ", province='" + province + '\'' +
                ", city='" + city + '\'' +
                ", county='" + county + '\'' +
                ", truename=" + truename +
                ", homesite=" + homesite +
                ", papertype=" + papertype +
                ", identificationnumber='" + identificationnumber + '\'' +
                ", certificatespicture='" + certificatespicture + '\'' +
                '}';
    }
}
