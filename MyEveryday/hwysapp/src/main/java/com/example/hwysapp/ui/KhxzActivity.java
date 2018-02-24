package com.example.hwysapp.ui;

import android.os.Bundle;
import android.view.View;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.BootstrapEditText;
import com.example.hwysapp.R;
import com.example.hwysapp.utils.Constants;
import com.example.hwysapp.utils.SpUtil;
import com.example.hwysapp.utils.TipDialogUti;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import org.json.JSONException;
import org.json.JSONObject;

public class KhxzActivity extends BaseActivity {

    private BootstrapEditText ed1,ed2;
    private BootstrapButton button;
    private String khxm,khdh;

    @Override
    public void initParms(Bundle parms) {

    }

    @Override
    public void bindView() {
        setView("客户新增", R.layout.activity_khxz);

    }

    @Override
    public void initView() {

        ed1 = findViewById(R.id.ed1);
        ed2 = findViewById(R.id.ed2);

        button = findViewById(R.id.btn);
    }

    @Override
    public void doBusiness() {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                khxm = ed1.getText().toString().trim();
                khdh =ed2.getText().toString().trim();
                if ("".equals(khxm)) {
                    showAlertDialog("客户姓名不能为空！");
                    return;
                } else {
                    ddxz();
                }
            }
        });
    }

    public void ddxz() {
        showProgressDialog("操作进行中，请稍后...", true);
        OkGo.<String>post(Constants.URL + "kh/addKh.do")
                .tag(this)
                .params("yhm", SpUtil.get(mContext, "YHM", "").toString())
                .params("khxm", khxm)
                .params("khdh", khdh)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        dismissProgressDialog();
                        try {
                            String s = response.body().toString();
                            JSONObject obj = new JSONObject(s);
                            String flag = obj.getString("FLAG");
                            if (flag.equals("1")) {
                                TipDialogUti.succss(mContext,"新增客户成功！");
                                setResult(1);
                            } else if(flag.equals("2")){
                                showAlertDialog("该用户已存在，请不要重复添加！");
                            }else {
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
