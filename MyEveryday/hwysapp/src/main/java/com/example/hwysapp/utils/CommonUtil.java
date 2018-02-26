package com.example.hwysapp.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

/**
 * Created by 87990 on 2018/2/26.
 */

public class CommonUtil {

    public static int getVersionCode(Context mContext) {
        // 获取packagemanager的实例
        int version = 0;
        try {
            PackageManager packageManager = mContext.getPackageManager();
            //getPackageName()是你当前程序的包名
            PackageInfo packInfo = packageManager.getPackageInfo(mContext.getPackageName(), 0);
            version = packInfo.versionCode;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return version;
    }

    public static String getVersionName(Context mContext) {
        // 获取packagemanager的实例
        String version = "";
        try {
            PackageManager packageManager = mContext.getPackageManager();
            //getPackageName()是你当前程序的包名
            PackageInfo packInfo = packageManager.getPackageInfo(mContext.getPackageName(), 0);
            version = packInfo.versionName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return version;
    }
}
