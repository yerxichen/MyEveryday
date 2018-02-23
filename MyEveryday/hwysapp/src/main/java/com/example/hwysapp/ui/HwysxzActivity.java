package com.example.hwysapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.BootstrapEditText;
import com.bigkoo.pickerview.TimePickerView;
import com.blankj.utilcode.util.TimeUtils;
import com.example.hwysapp.R;
import com.example.hwysapp.adapter.DdlbAdapter;
import com.example.hwysapp.adapter.MySpinnerAdapter;
import com.example.hwysapp.utils.Constants;
import com.example.hwysapp.utils.DateUtil;
import com.example.hwysapp.utils.SpUtil;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

public class HwysxzActivity extends BaseActivity {

    private BootstrapEditText ed1,ed2,ed3;
    private BootstrapButton button,btnadd;
    private Spinner spinner1,spinner2;
    private String khxm,yssj,ysqd="",yszd="",jg,bz;
    private TimePickerView pvTime;
    private JSONArray mArray;
    private MySpinnerAdapter adapter;


    @Override
    public void initParms(Bundle parms) {
        khxm=parms.getString("khxm");
    }

    @Override
    public void bindView() {
        setView("客户："+khxm, R.layout.activity_hwysxz);

    }

    @Override
    public void initView() {

        ed1 = findViewById(R.id.ed1);
        ed2 = findViewById(R.id.ed2);
        ed3 = findViewById(R.id.ed3);
        spinner1=findViewById(R.id.spinner1);
        spinner2=findViewById(R.id.spinner2);
        button = findViewById(R.id.btn);
        btnadd=findViewById(R.id.btn_add);
    }

    @Override
    public void doBusiness() {
        mArray=new JSONArray();
        adapter=new MySpinnerAdapter(mContext,mArray);
        spinner1.setAdapter(adapter);
        spinner2.setAdapter(adapter);
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                try {
                    ysqd=mArray.getJSONObject(i).getString("ddmc");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                try {
                    yszd=mArray.getJSONObject(i).getString("ddmc");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        //时间选择器
         pvTime = new TimePickerView.Builder(mContext, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                ed1.setText(DateUtil.getDateFormat(date));
            }
        }).setType(new boolean[]{true,true,true,false,false,false}).setRange(2000,2050).build();

        ed1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pvTime.show();
            }
        });

        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in=new Intent(mContext,DdxzActivity.class);
                startActivityForResult(in,1);
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                yssj = ed1.getText().toString().trim();
                jg = ed2.getText().toString().trim();
                bz = ed3.getText().toString().trim();
                if ("".equals(yssj)) {
                    showAlertDialog("运输日期不能为空！");
                    return;
                }
                if ("".equals(jg)) {
                    showAlertDialog("价格不能为空！");
                    return;
                }
                if ("".equals(ysqd)) {
                    showAlertDialog("运输起点不能为空！");
                    return;
                }
                if ("".equals(yszd)) {
                    showAlertDialog("运输终点不能为空！");
                    return;
                }
                hwysxz();
            }
        });

        getData();
    }

    public void hwysxz() {
        showProgressDialog("操作进行中，请稍后...", true);
        OkGo.<String>post(Constants.URL + "hwys/hwysxz.do")
                .tag(this)
                .params("yhm", SpUtil.get(mContext, "YHM", "").toString())
                .params("khxm", khxm)
                .params("yssj", yssj)
                .params("ysqd", ysqd)
                .params("yszd", yszd)
                .params("jg", jg)
                .params("bz", bz)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        dismissProgressDialog();
                        try {
                            String s = response.body().toString();
                            JSONObject obj = new JSONObject(s);
                            String flag = obj.getString("FLAG");
                            if (flag.equals("1")) {
                                showAlertDialog("新增成功！");
                                SpUtil.put(mContext,"ysqd",ysqd);
                                SpUtil.put(mContext,"yszd",yszd);
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

    public void getData(){
        showProgressDialog("数据初始化中...",true);
        OkGo.<String>post(Constants.URL+"dd/queryDd.do")
                .tag(this)
                .params("yhm", SpUtil.get(mContext,"YHM","").toString())
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        dismissProgressDialog();
                        try {
                            String s=response.body().toString();
                            JSONObject obj=new JSONObject(s);
                            String flag=obj.getString("FLAG");
                            if(flag.equals("1")){
                                JSONArray arr=obj.getJSONArray("DATA");
                                mArray=arr;
                                adapter=new MySpinnerAdapter(mContext,mArray);
                                spinner1.setAdapter(adapter);
                                if (SpUtil.contains(mContext,"ysqd")){
                                    String s1=SpUtil.get(mContext,"ysqd","").toString();
                                    for(int i=0;i<mArray.length();i++){
                                        if(s1.equals(mArray.getJSONObject(i).getString("ddmc"))){
                                            spinner1.setSelection(i);
                                        }
                                    }
                                }
                                spinner2.setAdapter(adapter);
                                if (SpUtil.contains(mContext,"yszd")){
                                    String s1=SpUtil.get(mContext,"yszd","").toString();
                                    for(int i=0;i<mArray.length();i++){
                                        if(s1.equals(mArray.getJSONObject(i).getString("ddmc"))){
                                            spinner2.setSelection(i);
                                        }
                                    }
                                }

                            }else{
                                showAlertDialog("初始化失败,请联系管理员");
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


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==1){
            getData();
        }
    }
}
