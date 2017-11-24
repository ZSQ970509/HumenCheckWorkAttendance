package com.example.humencheckworkattendance.transformer;


import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by gaosheng on 2016/11/6.
 * 23:28
 * com.example.gaosheng.myapplication.transformer
 */

public class CommonTransStringformer<T> implements Observable.Transformer<T, T> {

    @Override
    public Observable<T> call(Observable<T> tansFormerObservable) {

        return tansFormerObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(ErrorTransformerbak2.<T>getInstance());


    }

}

