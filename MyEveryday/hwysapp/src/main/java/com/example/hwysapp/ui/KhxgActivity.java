package com.example.hwysapp.ui;

import android.os.Bundle;
import android.view.View;

import com.beardedhen.androidbootstrap.BootstrapEditText;
import com.example.hwysapp.R;
import com.example.hwysapp.utils.Constants;
import com.example.hwysapp.utils.SpUtil;
import com.example.hwysapp.utils.TipDialogUtil;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton;

import org.json.JSONException;
import org.json.JSONObject;

public class KhxgActivity extends BaseActivity {

    private BootstrapEditText ed1,ed2;
    private QMUIRoundButton button;
    private String id,khxm,khdh;

    @Override
    public void initParms(Bundle parms) {
        id=parms.getString("id");
        khxm=parms.getString("khxm");
        khdh=parms.getString("khdh");
    }

    @Override
    public void bindView() {
        setView("客户修改", R.layout.activity_khxg);

    }

    @Override
    public void initView() {

        ed1 = findViewById(R.id.ed1);
        ed1.setText(khxm);
        ed2 = findViewById(R.id.ed2);
        ed2.setText(khdh);
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
                    khxg();
                }
            }
        });
    }

    public void khxg() {
        showProgressDialog("操作进行中，请稍后...", true);
        OkGo.<String>post(Constants.URL + "kh/updateKh.do")
                .tag(this)
                .params("yhm", SpUtil.get(mContext, "YHM", "").toString())
                .params("khxm", khxm)
                .params("id", id)
                .params("khdh",khdh)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        dismissProgressDialog();
                        try {
                            String s = response.body().toString();
                            JSONObject obj = new JSONObject(s);
                            String flag = obj.getString("FLAG");
                            if (flag.equals("1")) {
                                TipDialogUtil.succss(mContext,"修改成功！");
                                setResult(1);
                            } else{
                                showAlertDialog("修改失败！请联系管理员");
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
