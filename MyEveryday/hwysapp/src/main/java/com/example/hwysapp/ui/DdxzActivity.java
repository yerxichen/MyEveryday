package com.example.hwysapp.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.BootstrapEditText;
import com.example.hwysapp.R;
import com.example.hwysapp.utils.Constants;
import com.example.hwysapp.utils.SpUtil;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import org.json.JSONException;
import org.json.JSONObject;

public class DdxzActivity extends BaseActivity {

    private BootstrapEditText ed;
    private BootstrapButton button;
    private String ddmc;

    @Override
    public void initParms(Bundle parms) {

    }

    @Override
    public void bindView() {
        setView("地点新增", R.layout.activity_ddxz);

    }

    @Override
    public void initView() {

        ed = findViewById(R.id.ed);

        button = findViewById(R.id.btn);
    }

    @Override
    public void doBusiness() {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ddmc = ed.getText().toString().trim();
                if ("".equals(ddmc)) {
                    showAlertDialog("地点名称不能为空！");
                    return;
                } else {
                    ddxz();
                }
            }
        });
    }

    public void ddxz() {
        showProgressDialog("操作进行中，请稍后...", true);
        OkGo.<String>post(Constants.URL + "dd/addDd.do")
                .tag(this)
                .params("yhm", SpUtil.get(mContext, "YHM", "").toString())
                .params("ddmc", ddmc)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        dismissProgressDialog();
                        try {
                            String s = response.body().toString();
                            JSONObject obj = new JSONObject(s);
                            String flag = obj.getString("FLAG");
                            if (flag.equals("1")) {
                                showAlertDialog("新增地点成功！");
                                setResult(1);
                            } else {
                                showAlertDialog("新增失败,请联系管理员！");
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
