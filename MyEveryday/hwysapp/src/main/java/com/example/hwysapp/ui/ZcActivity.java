package com.example.hwysapp.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.BootstrapEditText;
import com.beardedhen.androidbootstrap.api.view.BootstrapTextView;
import com.example.hwysapp.R;
import com.example.hwysapp.utils.Constants;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import org.json.JSONException;
import org.json.JSONObject;

public class ZcActivity extends BaseActivity {

    private BootstrapEditText ed1,ed2,ed3;
    private BootstrapButton button;
    private String yhm,xm,mm;

    @Override
    public void initParms(Bundle parms) {

    }

    @Override
    public void bindView() {
        setView("注册",R.layout.activity_zc);
    }

    @Override
    public void initView() {
        ed1=findViewById(R.id.ed1);
        ed2=findViewById(R.id.ed2);
        ed3=findViewById(R.id.ed3);
        button=findViewById(R.id.btn_zc);
    }

    @Override
    public void doBusiness() {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                yhm=ed1.getText().toString();
                xm=ed2.getText().toString();
                mm=ed3.getText().toString();
                if("".equals(yhm.trim())){
                    showToast("手机号码不能为空");
                    return;
                }
                if("".equals(xm.trim())){
                    showToast("姓名不能为空");
                    return;
                }
                if("".equals(mm.trim())){
                    showToast("密码不能为空");
                    return;
                }
                yzzc();

            }
        });
    }

    public void yzzc(){
        showProgressDialog("正在注册用户，请稍后...",true);
        OkGo.<String>post(Constants.URL+"login/zc.do")
                .tag(this)
                .params("yhm",yhm)
                .params("xm",xm)
                .params("mm",mm)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        dismissProgressDialog();
                        try {
                            String s=response.body().toString();
                            JSONObject obj=new JSONObject(s);
                            String flag=obj.getString("FLAG");
                            if(flag.equals("1")){
                                showAlertDialog(obj.getString("DATA"));
                            }else{
                                showAlertDialog("注册失败,请联系管理员");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
//                        Log.d(TAG, "onSuccess: "+response.body().toString());
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        dismissProgressDialog();
                        showAlertDialog("网络异常");
                    }
                });
    }
}
