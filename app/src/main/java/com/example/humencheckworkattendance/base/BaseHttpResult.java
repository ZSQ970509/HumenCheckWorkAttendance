package com.example.humencheckworkattendance.base;

import com.google.gson.annotations.SerializedName;

/**
 * Created by GaoSheng on 2016/10/21.
 * 抽取的一个基类的bean,直接在泛型中传data就行
 */

public class BaseHttpResult<T> {

    @SerializedName("result")
    private int StatusCode;

    private String StatusResult;

    private String msg;

    private T data;

    private String total;

    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getStatusCode() {
        return StatusCode;
    }

    public void setStatusCode(int statusCode) {
        StatusCode = statusCode;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        data = data;
    }

    public String getStatusResult() {
        return StatusResult;
    }

    public void setStatusResult(String statusResult) {
        StatusResult = statusResult;
    }

    @Override
    public String toString() {
        return "BaseHttpResult{" +
                "StatusCode=" + StatusCode +
                ", StatusResult='" + StatusResult + '\'' +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                ", token='" + token + '\'' +
                '}';
    }
}
