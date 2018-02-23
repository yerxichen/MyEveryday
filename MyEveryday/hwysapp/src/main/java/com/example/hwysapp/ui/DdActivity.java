package com.example.hwysapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.BootstrapEditText;
import com.example.hwysapp.R;
import com.example.hwysapp.adapter.DdlbAdapter;
import com.example.hwysapp.utils.Constants;
import com.example.hwysapp.utils.SpUtil;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DdActivity extends BaseActivity {

    private RecyclerView recyclerView;
    private DdlbAdapter adapter;
    private List<JSONObject> mList=new ArrayList<>();
    @Override
    public void initParms(Bundle parms) {

    }

    @Override
    public void bindView() {
        setView("地点管理",R.layout.activity_dd);

    }

    @Override
    public void initView() {
        add=findViewById(R.id.add);
        add.setVisibility(View.VISIBLE);
        recyclerView=findViewById(R.id.recyclerview);
    }

    @Override
    public void doBusiness() {
       add.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent in=new Intent(mContext,DdxzActivity.class);
               startActivityForResult(in,1);
           }
       });

       recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
       recyclerView.setAdapter(adapter=new DdlbAdapter(R.layout.activity_dd_item,mList));

       getData();
    }

    public void getData(){
        showProgressDialog("加载中...",true);
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
                                for(int i=0;i<arr.length();i++){
                                    //mList.add(arr.getJSONObject(i));
                                    adapter.addData(arr.getJSONObject(i));
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



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==1){
            mList=new ArrayList<>();
            recyclerView.setAdapter(adapter=new DdlbAdapter(R.layout.activity_dd_item,mList));
            getData();

        }
    }
}
