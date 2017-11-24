package com.example.humencheckworkattendance.transformer;


import com.example.humencheckworkattendance.exception.ErrorType;
import com.example.humencheckworkattendance.exception.ExceptionEngine;
import com.example.humencheckworkattendance.exception.ServerException;
import com.example.humencheckworkattendance.utils.LogUtils;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by gaosheng on 2016/11/6.
 * 23:00
 * com.example.gaosheng.myapplication.transformer
 */

public class ErrorTransformerbak2<T> implements Observable.Transformer<T, T> {

    private static ErrorTransformerbak2 errorTransformer = null;
    private static final String TAG = "ErrorTransformer";

    @Override
    public Observable<T> call(Observable<T> responseObservable) {

        return responseObservable.map(new Func1<T, T>() {
            @Override
            public T call(T httpResult) {

                if (httpResult == null) {
                    throw new ServerException(ErrorType.EMPTY_BEAN, "解析对象为空");
                }
                    LogUtils.e(TAG, httpResult.toString());

//                if (httpResult.getStatus() != ErrorType.SUCCESS)
//                    throw new ServerException(httpResult.getStatus(), httpResult.getMessage());
                return httpResult;
            }
        }).onErrorResumeNext(new Func1<Throwable, Observable<? extends T>>() {
            @Override
            public Observable<? extends T> call(Throwable throwable) {
                //ExceptionEngine为处理异常的驱动器throwable
                throwable.printStackTrace();
                return Observable.error(ExceptionEngine.handleException(throwable));
            }
        });

    }






    /**
     * @return 线程安全, 双层校验
     */
    public static <T> ErrorTransformerbak2<T> getInstance() {

        if (errorTransformer == null) {
            synchronized (ErrorTransformerbak2.class) {
                if (errorTransformer == null) {
                    errorTransformer = new ErrorTransformerbak2<>();
                }
            }
        }
        return errorTransformer;

    }
}
