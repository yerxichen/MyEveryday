package com.yer.myeveryday.utils;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


import com.beardedhen.androidbootstrap.TypefaceProvider;

import org.litepal.LitePal;
import org.litepal.LitePalApplication;
import org.litepal.tablemanager.Connector;

/**
 * Created by 87990 on 2017/12/1.
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        LitePal.initialize(this);
        SQLiteDatabase db = Connector.getDatabase();//注册litepal
        TypefaceProvider.registerDefaultIconSets();//注册bootstrap
    }
}
