package com.example.humencheckworkattendance.utils;

import android.Manifest;
import android.app.Activity;

import com.joker.api.Permissions4M;
import com.joker.api.apply.PermissionsChecker;
import com.joker.api.wrapper.ListenerWrapper;
import com.joker.api.wrapper.PermissionWrapper;

/**
 * Created by k on 2017/9/20.
 */

public class PermissionManager {

    public static final int CALL_PHONE_CODE  = 200;
    public static final int WRITE_EXTERNAL_STORAGE_CODE  = 300;
    public static final int CAMERA_CODE = 400;
    public static final int SMS_CODE = 500;
    public static final int RECORD_AUDIO_CODE = 600;
    public static final int SENSORS_CODE = 700;
    public static final int LOCATION_CODE = 800;
    public static final int CALENDAR_CODE = 900;
    public static final int PHONE_STATE_CODE = 1000;

    private  PermissionManager manager;

    public static PermissionWrapper wrapper;

    public PermissionManager(Activity activity) {
        wrapper = (PermissionWrapper) Permissions4M.get(activity);
    }

    public  PermissionManager getInstance(Activity activity) {

        if (manager == null) {
            manager = new PermissionManager(activity);
        }

        return manager;

    }


    public void requestPermissions(final String[] permissions, final int[] requestCodes, PermissionRequestListener permissionRequestListener   ,PermissionPageListener permissionPageListener) {


        // 是否强制弹出权限申请对话框，建议设置为 true，默认为 true
        // .requestForce(true)
        // 是否支持 5.0 权限申请，默认为 false
        // .requestUnderM(false)
        // 权限，单权限申请仅只能填入一个
        wrapper.requestPermissions().requestPermissions(permissions)
                // 权限码
                .requestCodes(requestCodes)
                // 如果需要使用 @PermissionNonRationale 注解的话，建议添加如下一行
                // 返回的 intent 是跳转至**系统设置页面**
                // .requestPageType(Permissions4M.PageType.MANAGER_PAGE)
                // 返回的 intent 是跳转至**手机管家页面**
                // .requestPageType(Permissions4M.PageType.ANDROID_SETTING_PAGE)
                // 权限请求结果
                .requestListener(permissionRequestListener)
                // 二次请求时回调
               // .requestCustomRationaleListener(permissionCustomRationaleListener)
                .requestCustomRationaleListener(new PermissionCustomRationaleListener() {
                    @Override
                    public void permissionCustomRationale(int code) {
                        switch (code){//这里继续添加别的权限
                            case PermissionManager.PHONE_STATE_CODE:
                                Permissions4M.get(wrapper.getActivity())
                                        .requestOnRationale()
                                        .requestPermissions(Manifest.permission.RECORD_AUDIO)
                                        .requestCodes(PermissionManager.PHONE_STATE_CODE)
                                        .request();
                                break;
                            case PermissionManager.LOCATION_CODE:
                                Permissions4M.get(wrapper.getActivity())
                                        .requestOnRationale()
                                        .requestPermissions(Manifest.permission.ACCESS_FINE_LOCATION)
                                        .requestCodes(PermissionManager.LOCATION_CODE)
                                        .request();
                                break;
                            case PermissionManager.WRITE_EXTERNAL_STORAGE_CODE:
                                Permissions4M.get(wrapper.getActivity())
                                        .requestOnRationale()
                                        .requestPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                                        .requestCodes(PermissionManager.WRITE_EXTERNAL_STORAGE_CODE)
                                        .request();
                                break;
                            case PermissionManager.RECORD_AUDIO_CODE:
                                Permissions4M.get(wrapper.getActivity())
                                        .requestOnRationale()
                                        .requestPermissions(Manifest.permission.RECORD_AUDIO)
                                        .requestCodes(PermissionManager.RECORD_AUDIO_CODE)
                                        .request();
                                break;
                            case PermissionManager.CAMERA_CODE://拍照
                                Permissions4M.get(wrapper.getActivity())
                                        .requestOnRationale()
                                        .requestPermissions(Manifest.permission.CAMERA)
                                        .requestCodes(PermissionManager.CAMERA_CODE)
                                        .request();
                                break;
                            case PermissionManager.CALL_PHONE_CODE:
                                Permissions4M.get(wrapper.getActivity())
                                        .requestOnRationale()
                                        .requestPermissions(Manifest.permission.CALL_PHONE)
                                        .requestCodes(PermissionManager.CALL_PHONE_CODE)
                                        .request();
                                break;
                        }
                    }
                })

                // 权限完全被禁时回调函数中返回 intent 类型（手机管家界面）
                .requestPageType(Permissions4M.PageType.MANAGER_PAGE)
                // 权限完全被禁时回调函数中返回 intent 类型（系统设置界面）
                //.requestPageType(Permissions4M.PageType.ANDROID_SETTING_PAGE)
                // 权限完全被禁时回调，接口函数中的参数 Intent 是由上一行决定的

                .requestPage(permissionPageListener)
                .request();

    }


    public interface PermissionRequestListener extends ListenerWrapper.PermissionRequestListener{



    }

    public interface PermissionCustomRationaleListener extends ListenerWrapper.PermissionCustomRationaleListener{

    }

    public interface PermissionPageListener extends ListenerWrapper.PermissionPageListener{

    }


    public static boolean isPermissionGranted(Activity activity, String[] permisions){

        boolean status = true;

        for (int i =0;i<permisions.length;i++){
            if (!PermissionsChecker.isPermissionGranted(activity,permisions[i])){
                status=false;
                break;
            }
        }

        return status;
    }


}
