package com.example.hwysapp.utils;

import android.app.Application;

import com.blankj.utilcode.util.Utils;
import com.lzy.okgo.OkGo;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;


/**
 * Created by 87990 on 2017/12/1.
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Utils.init(this);
        OkGo.getInstance().init(this);
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        //全局的读取超时时间
        builder.readTimeout(500, TimeUnit.MILLISECONDS);
//全局的写入超时时间
        builder.writeTimeout(500, TimeUnit.MILLISECONDS);
//全局的连接超时时间
        builder.connectTimeout(500, TimeUnit.MILLISECONDS);
    }
}
