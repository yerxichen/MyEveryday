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

public class DdxgActivity extends BaseActivity {

    private BootstrapEditText ed;
    private QMUIRoundButton button;
    private String id,ddmc;

    @Override
    public void initParms(Bundle parms) {
        id=parms.getString("id");
        ddmc=parms.getString("ddmc");
    }

    @Override
    public void bindView() {
        setView("地点修改", R.layout.activity_ddxg);

    }

    @Override
    public void initView() {

        ed = findViewById(R.id.ed);
        ed.setText(ddmc);
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
        OkGo.<String>post(Constants.URL + "dd/updateDd.do")
                .tag(this)
                .params("yhm", SpUtil.get(mContext, "YHM", "").toString())
                .params("id",id)
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
