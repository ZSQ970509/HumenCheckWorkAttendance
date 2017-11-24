package com.example.humencheckworkattendance.base;


import com.example.humencheckworkattendance.http.Http;
import com.example.humencheckworkattendance.http.HttpService;
import com.example.humencheckworkattendance.mvp.IModel;

/**
 * Created by gaosheng on 2016/12/1.
 * 23:13
 * com.example.gs.mvpdemo.base
 */

public class BaseModel implements IModel {
    protected static HttpService httpService;

    //初始化httpService
    static {
        httpService = Http.getHttpService();
    }

}
