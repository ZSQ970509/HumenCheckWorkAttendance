package com.example.humencheckworkattendance.utils;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.example.humencheckworkattendance.ProApplication;


/**
 * Created by ZHT on 2017/6/1.
 * 版本相关工具类
 */

public class VersionUtils {

    private VersionUtils() {
        throw new IllegalArgumentException("cannot create VersionUtils constructor!");
    }

    private static PackageInfo getPackageInfo() {
        PackageInfo info = null;
        try {
            PackageManager manager = ProApplication.getmContext().getPackageManager();
            info = manager.getPackageInfo( ProApplication.getmContext().getPackageName(), PackageManager.GET_CONFIGURATIONS);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return info;
    }

    public static String getPackageName() {
        return getPackageInfo().packageName;
    }

    /**
     * 获取应用版本名
     * @return 版本名
     */
    public static String getVersionName() {
        return "版本：V"+getPackageInfo().versionName+"（Beta-11:50）";
    }

    /**
     * 获取应用版本号
     * @return 版本号
     */
    public static int getVersionCode() {
        return getPackageInfo().versionCode;
    }
}
