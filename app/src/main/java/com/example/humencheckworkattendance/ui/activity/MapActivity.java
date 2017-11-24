package com.example.humencheckworkattendance.ui.activity;

import android.os.Bundle;
import android.view.View;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.UiSettings;
import com.baidu.mapapi.model.LatLng;
import com.example.humencheckworkattendance.R;
import com.example.humencheckworkattendance.base.BaseActivity;
import com.example.humencheckworkattendance.contact.MapContact;
import com.example.humencheckworkattendance.presenter.MapPresenter;

import butterknife.BindView;

public class MapActivity extends BaseActivity<MapPresenter> implements MapContact.MapView {
    @BindView(R.id.mapview)
    MapView mapView;
    BaiduMap baiduMap;
    boolean ifFrist = true;
    @Override
    protected MapPresenter loadPresenter() {
        return new MapPresenter();
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        Double Longitude = getIntent().getDoubleExtra("Longitude",0.0);
        Double latitude = getIntent().getDoubleExtra("latitude",0.0);
        if (ifFrist) {
            LatLng ll = new LatLng(latitude,
                    Longitude);
            MapStatusUpdate update = MapStatusUpdateFactory.newLatLng(ll);
            // 移动到某经纬度
            baiduMap.animateMapStatus(update);
            update = MapStatusUpdateFactory.zoomBy(7f);
            // 放大
            baiduMap.animateMapStatus(update);

            ifFrist = false;
        }
        // 显示个人位置图标
        MyLocationData.Builder builder = new MyLocationData.Builder();
        builder.latitude(latitude);
        builder.longitude(Longitude);
        MyLocationData data = builder.build();
        baiduMap.setMyLocationData(data);
       /* LatLng centerLatLng = new LatLng(latitude, Longitude);
        OverlayOptions polygonOption = new CircleOptions().center(centerLatLng).radius(50).fillColor(0xAAFFFF00);
        baiduMap.addOverlay(polygonOption);*/

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initView() {
        baiduMap = mapView.getMap();
        baiduMap.setMyLocationEnabled(true);
        UiSettings settings=baiduMap.getUiSettings();
        settings.setAllGesturesEnabled(false);
        settings.setZoomGesturesEnabled(true);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_map;
    }

    @Override
    protected void otherViewClick(View view) {

    }
    @Override
    protected void onDestroy() {
        // 释放资源
        mapView.onDestroy();
        baiduMap.setMyLocationEnabled(false);
        super.onDestroy();


    }

}
