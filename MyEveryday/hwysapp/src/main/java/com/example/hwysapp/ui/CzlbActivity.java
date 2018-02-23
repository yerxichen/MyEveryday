package com.example.hwysapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import com.example.hwysapp.R;
import com.example.hwysapp.adapter.ArrSpinnerAdapter;
import com.example.hwysapp.adapter.CzlbAdapter;
import com.example.hwysapp.adapter.DdlbAdapter;
import com.example.hwysapp.adapter.KhxmSpinnerAdapter;
import com.example.hwysapp.adapter.MySpinnerAdapter;
import com.example.hwysapp.utils.Constants;
import com.example.hwysapp.utils.SpUtil;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class CzlbActivity extends BaseActivity {
    private static final String[] YEARARR={"全部","2018年","2019年","2020年","2021年","2022年","2023年","2024年","2025年","2026年","2027年","2028年","2029年","2030年"};
    private static final String[] MONTHARR={"全部","1月","2月","3月","4月","5月","6月","7月","8月","9月","10月","11月","12月"};
    private Spinner sp1,sp2,sp3;
    private KhxmSpinnerAdapter adapter1;
    private ArrSpinnerAdapter adapter2,adapter3;
    private RecyclerView recyclerView;
    private CzlbAdapter adapter;
    private List<JSONObject> mList=new ArrayList<>();
    private JSONArray mArray;
    private String khxm="",year,month="全部";
    private boolean isSp1First=true;
    private boolean isSp2First=true;
    private boolean isSp3First=true;

    @Override
    public void initParms(Bundle parms) {

    }

    @Override
    public void bindView() {
        setView("账单",R.layout.activity_czlb);

    }

    @Override
    public void initView() {
        Calendar ca=Calendar.getInstance();
        year=String.valueOf(ca.get(Calendar.YEAR));
        month=String.valueOf(ca.get(Calendar.MONTH)+1);
//        add=findViewById(R.id.add);
//        add.setVisibility(View.VISIBLE);
        recyclerView=findViewById(R.id.recyclerview);
        sp1=findViewById(R.id.spinner1);
        sp2=findViewById(R.id.spinner2);
        sp3=findViewById(R.id.spinner3);
    }

    @Override
    public void doBusiness() {
        mArray=new JSONArray();
        adapter1=new KhxmSpinnerAdapter(mContext,mArray);
        sp1.setAdapter(adapter1);
        sp2.setAdapter(adapter2=new ArrSpinnerAdapter(YEARARR,mContext));

        sp3.setAdapter(adapter3=new ArrSpinnerAdapter(MONTHARR,mContext));


        sp1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                try {
                    khxm=mArray.getJSONObject(i).getString("khxm");
                    if(!isSp1First){
                        getData();
                    }
                    isSp1First=false;


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        sp1.setSelection(0,false);

        sp2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                year=YEARARR[i].replace("年","");
                if(!isSp2First){
                    getData();
                }
                isSp2First=false;

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        for(int i=0;i<YEARARR.length;i++){
            if(year.equals(YEARARR[i].replace("年",""))){
                sp2.setSelection(i,false);
            }
        }

        sp3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                month=MONTHARR[i].replace("月","");
                if(!isSp3First){
                    getData();
                }
                isSp3First=false;

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        for(int i=0;i<MONTHARR.length;i++){
            if(month.equals(String.valueOf(MONTHARR[i]))){
                sp3.setSelection(i,false);
            }
        }
//        sp3.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                month=MONTHARR[i].replace("月","");
//                getData();
//            }
//        });
       recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
       recyclerView.setAdapter(adapter=new CzlbAdapter(R.layout.activity_czlb_item,mList));

        getKhxm();
    }

    public void getData(){
        showProgressDialog("加载中...",true);
        OkGo.<String>post(Constants.URL+"hwys/hwyscx.do")
                .tag(this)
                .params("yhm", SpUtil.get(mContext,"YHM","").toString())
                .params("khxm", khxm)
                .params("year", year)
                .params("month", month)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        dismissProgressDialog();
                        try {
                            String s=response.body().toString();
                            Log.d(TAG, "onSuccess: "+s);
                            JSONObject obj=new JSONObject(s);

                            String flag=obj.getString("FLAG");
                            if(flag.equals("1")){
                                JSONArray arr=obj.getJSONArray("DATA");
                                if (arr.length()>0){
                                    mList=new ArrayList<>();
                                    for(int i=0;i<arr.length();i++){
                                        mList.add(arr.getJSONObject(i));
                                        //adapter.addData(arr.getJSONObject(i));
                                    }
                                    recyclerView.setAdapter(adapter=new CzlbAdapter(R.layout.activity_czlb_item,mList));

                                }else {
                                    showAlertDialog("无数据！");
                                    mList=new ArrayList<>();
                                    recyclerView.setAdapter(adapter=new CzlbAdapter(R.layout.activity_czlb_item,mList));
                                }


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

    public void getKhxm(){
       // showProgressDialog("加载中...",true);
        OkGo.<String>post(Constants.URL+"kh/queryKh.do")
                .tag(this)
                .params("yhm", SpUtil.get(mContext,"YHM","").toString())
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                       // dismissProgressDialog();
                        try {
                            String s=response.body().toString();
                            JSONObject obj=new JSONObject(s);
                            String flag=obj.getString("FLAG");
                            if(flag.equals("1")){
                                JSONArray arr=obj.getJSONArray("DATA");
                                mArray=arr;
                                adapter1=new KhxmSpinnerAdapter(mContext,mArray);
                                sp1.setAdapter(adapter1);
                                khxm=mArray.getJSONObject(0).getString("khxm");
                                getData();

                            }else{
                                showAlertDialog("查询失败,请联系管理员");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
//                        Log.d(TAG, "onSuccess: "+response.body().toString());
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        //dismissProgressDialog();
                        showAlertDialog("网络异常");
                    }
                });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==1){
            mList=new ArrayList<>();
            recyclerView.setAdapter(adapter=new CzlbAdapter(R.layout.activity_czlb_item,mList));
            //getData();

        }
    }
}
