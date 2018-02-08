package com.example.humencheckworkattendance.base;


import com.example.humencheckworkattendance.exception.ApiException;
import com.example.humencheckworkattendance.exception.ExceptionEngine;

import rx.Subscriber;


/**
 * Created by gaosheng on 2016/11/6.
 */

public abstract class BaseSubscriber<T> extends Subscriber<T> {

    @Override
    public void onError(Throwable e) {
        onError(ExceptionEngine.handleException(e));
    }


    /**
     * @param e 错误的一个回调
     */
    protected abstract void onError(ApiException e);

}
