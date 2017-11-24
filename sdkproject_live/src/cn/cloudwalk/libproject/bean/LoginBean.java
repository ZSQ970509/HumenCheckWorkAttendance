package cn.cloudwalk.libproject.bean;

import java.io.Serializable;

/**
 * Created by administration on 2017/9/11.
 */

public class LoginBean implements Serializable{


    /**
     * userType : worker
     * memberId : 120
     * imgUrl : /UpFile/HeadPic/2017/0925/20170925114242.jpg
     * userName : 郑书强
     * ProjId : 30342
     * UserAccount : 17301370712
     * EmtpId :
     * EmtpRolesId :
     */

    private String userType;
    private int memberId;
    private String imgUrl;
    private String userName;
    private String ProjId;
    private String UserAccount;
    private String EmtpId;
    private String EmtpRolesId;
    private String userTypeId;

    public String getUserTypeId() {
        return userTypeId;
    }

    public void setUserTypeId(String userTypeId) {
        this.userTypeId = userTypeId;
    }
    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getProjId() {
        return ProjId;
    }

    public void setProjId(String ProjId) {
        this.ProjId = ProjId;
    }

    public String getUserAccount() {
        return UserAccount;
    }

    public void setUserAccount(String UserAccount) {
        this.UserAccount = UserAccount;
    }

    public String getEmtpId() {
        return EmtpId;
    }

    public void setEmtpId(String EmtpId) {
        this.EmtpId = EmtpId;
    }

    public String getEmtpRolesId() {
        return EmtpRolesId;
    }

    public void setEmtpRolesId(String EmtpRolesId) {
        this.EmtpRolesId = EmtpRolesId;
    }
}
