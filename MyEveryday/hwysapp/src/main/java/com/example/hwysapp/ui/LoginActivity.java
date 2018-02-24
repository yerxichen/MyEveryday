package com.example.hwysapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.BootstrapEditText;
import com.example.hwysapp.R;
import com.example.hwysapp.utils.Constants;
import com.example.hwysapp.utils.SpUtil;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends BaseActivity {

    private EditText ed1,ed2;
    private Button btn_lg;
    private TextView tv1;
    private int cs=1;
    private RelativeLayout relativeLayout;
    @Override
    public void initParms(Bundle parms) {

    }

    @Override
    public void bindView() {
        relativeLayout=findViewById(R.id.relativeLayout);
        relativeLayout.setVisibility(View.GONE);
        setView("登录",R.layout.activity_login);
    }

    @Override
    public void initView() {
        cs=1;
        tv1=findViewById(R.id.tv1);
        ed1=findViewById(R.id.ed1);
        ed2=findViewById(R.id.ed2);
        btn_lg=findViewById(R.id.btn_lg);
        if(SpUtil.contains(mContext,"YHM")&&SpUtil.contains(mContext,"MM")){
            ed1.setText(SpUtil.get(mContext,"YHM","").toString());
            ed2.setText(SpUtil.get(mContext,"MM","").toString());
        }

    }

    @Override
    public void doBusiness() {
        tv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in=new Intent(mContext,ZcActivity.class);
                startActivity(in);
            }
        });
        btn_lg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });
    }

    public void login(){
        String yhm=ed1.getText().toString().trim();
        String mm=ed2.getText().toString().trim();
        showProgressDialog("登录中...",true);

        OkGo.<String>post(Constants.URL+"login/login.do")
                .tag(this)
                .params("yhm",yhm)
                .params("mm",mm)
                .execute(new StringCallback() {
                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        dismissProgressDialog();
                        showAlertDialog("网络异常");
                    }

                    @Override
                    public void onSuccess(Response<String> response) {
                        dismissProgressDialog();

                        try {
                            String s=response.body().toString();
                            JSONObject obj=new JSONObject(s);
                            String flag = obj.getString("FLAG");

                            if(flag.equals("1")){
                                String id = obj.getString("ID");
                                if(id.equals("")){
                                    showAlertDialog("用户名或密码错误！");
                                }else{
                                    JSONArray arrObj=obj.getJSONArray("DATA");
                                    JSONObject resObj=arrObj.getJSONObject(0);
                                    SpUtil.put(mContext,"YHM",resObj.getString("yhm"));
                                    SpUtil.put(mContext,"XM",resObj.getString("xm"));
                                    SpUtil.put(mContext,"MM",resObj.getString("mm"));
                                    Intent in=new Intent(mContext,MainActivity.class);
                                    startActivity(in);
                                    finish();
                                }

                            }else{
                                if(cs==1){
                                    cs++;
                                    login();
                                }else{
                                    showAlertDialog("登录失败！");
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });
    }
}
