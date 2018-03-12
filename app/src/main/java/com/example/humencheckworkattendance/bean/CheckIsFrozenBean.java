package com.example.humencheckworkattendance.bean;

/**
 * Created by Administrator on 2018/3/12.
 */

public class CheckIsFrozenBean {

    /**
     * message : 该项目合同将于2018年03月14日到期，请及时缴费，以免影响您的打卡！
     * status : 0
     * messages :
     */

    private String message;
    private String status;
    private String messages;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessages() {
        return messages;
    }

    public void setMessages(String messages) {
        this.messages = messages;
    }
}
