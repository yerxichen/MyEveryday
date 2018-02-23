package com.example.hwysapp.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.hwysapp.R;
import com.example.hwysapp.utils.Constants;
import com.example.hwysapp.utils.SpUtil;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class LoginHomeActivity extends AppCompatActivity {
    private int cs=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_login_home);
        cs=1;
        login();
    }

    public void login() {
        if (SpUtil.contains(LoginHomeActivity.this, "YHM") && SpUtil.contains(LoginHomeActivity.this, "MM")) {
            String yhm = SpUtil.get(LoginHomeActivity.this,"YHM","").toString();
            String mm = SpUtil.get(LoginHomeActivity.this,"MM","").toString();

            OkGo.<String>post(Constants.URL + "login/login.do")
                    .tag(this)
                    .params("yhm", yhm)
                    .params("mm", mm)
                    .execute(new StringCallback() {
                        @Override
                        public void onError(Response<String> response) {
                            super.onError(response);
                        }

                        @Override
                        public void onSuccess(Response<String> response) {

                            try {
                                String s = response.body().toString();
                                JSONObject obj = new JSONObject(s);
                                String flag = obj.getString("FLAG");


                                if (flag.equals("1")) {
                                    String id = obj.getString("ID");
                                    if (id.equals("")) {
                                        Intent in = new Intent(LoginHomeActivity.this, LoginActivity.class);
                                        startActivity(in);
                                    } else {
                                        JSONArray arrObj = obj.getJSONArray("DATA");
                                        JSONObject resObj = arrObj.getJSONObject(0);
                                        SpUtil.put(LoginHomeActivity.this, "YHM", resObj.getString("yhm"));
                                        SpUtil.put(LoginHomeActivity.this, "XM", resObj.getString("xm"));
                                        SpUtil.put(LoginHomeActivity.this, "MM", resObj.getString("mm"));
                                        Intent in = new Intent(LoginHomeActivity.this, MainActivity.class);
                                        startActivity(in);
                                    }

                                } else {
                                    if(cs==1){
                                        cs++;
                                        login();
                                    }else{
                                        Intent in = new Intent(LoginHomeActivity.this, LoginActivity.class);
                                        startActivity(in);
                                    }
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    });
        }else {
            Intent in = new Intent(LoginHomeActivity.this, LoginActivity.class);
            startActivity(in);
        }
    }

}
