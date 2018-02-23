package com.yer.myeveryday.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.blankj.utilcode.util.ToastUtils;
import com.blankj.utilcode.util.Utils;
import com.yer.myeveryday.R;
import com.yer.myeveryday.entity.Zdy;
import com.yer.myeveryday.utils.ToastUtil;

public class ZdglxzActivity extends BaseActivity {
    private EditText ed1,ed2,ed3;
    private BootstrapButton subButton;
    private boolean boo=false;

    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1000:
                    if(boo){
                        ToastUtils.showShort("插入数据成功！");
                    }

            }
        }
    };
    @Override
    public void initParms(Bundle parms) {

    }

    @Override
    public void bindView() {
        setView("字典新增", R.layout.activity_zdgl_add);
    }

    @Override
    public void initView() {
        ed1= (EditText) findViewById(R.id.ed1);
        ed2= (EditText) findViewById(R.id.ed2);
        ed3= (EditText) findViewById(R.id.ed3);
        subButton= (BootstrapButton) findViewById(R.id.subButton);
    }

    @Override
    public void doBusiness() {

        subButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//               new Thread(new Runnable() {
//                   @Override
//                   public void run() {
//                       Log.d("my", "run: 插入数据");
//                       //插入数据库
//                       Zdy zdy = new Zdy();
//                       zdy.setDm(ed1.getText().toString());
//                       zdy.setDmfl(ed2.getText().toString());
//                       zdy.setMc(ed3.getText().toString());
//                       boo=zdy.save();
//                       Message msg=new Message();
//                       msg.what=1000;
//
//                   }
//               }).start();
                //插入数据库
                Zdy zdy = new Zdy();
                zdy.setDm(ed1.getText().toString());
                zdy.setDmfl(ed2.getText().toString());
                zdy.setMc(ed3.getText().toString());
                boo=zdy.save();
                Log.d("my", "boo: "+boo);
                if(boo){
                    ToastUtil.showToast(mContext,R.drawable.toast_success,"新增成功");
                }else{
                    ToastUtil.showToast(mContext,R.drawable.toast_error,"新增失败");
                }
            }
        });
    }
}
