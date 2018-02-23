package com.example.hwysapp.ui;

import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.hwysapp.R;
import com.example.hwysapp.adapter.DdlbAdapter;
import com.example.hwysapp.adapter.KhlbAdapter;
import com.example.hwysapp.utils.Constants;
import com.example.hwysapp.utils.DividerGridItemDecoration;
import com.example.hwysapp.utils.RecyclerViewDividerUtil;
import com.example.hwysapp.utils.SpUtil;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class KhlbActivity extends BaseActivity {

    private RecyclerView recyclerView;
    private KhlbAdapter adapter;
    private List<JSONObject> mList=new ArrayList<>();
    @Override
    public void initParms(Bundle parms) {

    }

    @Override
    public void bindView() {
        setView("客户列表",R.layout.activity_khlb);

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
               Intent in=new Intent(mContext,KhxzActivity.class);
               startActivityForResult(in,1);
           }
       });

       recyclerView.setLayoutManager(new GridLayoutManager(mContext,3));
       recyclerView.addItemDecoration(new DividerGridItemDecoration(mContext));
       recyclerView.setAdapter(adapter=new KhlbAdapter(R.layout.activity_khlb_item,mList));
       adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
           @Override
           public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
               try {
                   JSONObject obj= (JSONObject) adapter.getData().get(position);
                   String khxm=obj.getString("khxm");
                   Intent in=new Intent(mContext,HwysxzActivity.class);
                   in.putExtra("khxm",khxm);
                   startActivity(in);
               } catch (JSONException e) {
                   e.printStackTrace();
               }

           }
       });
       getData();
    }

    public void getData(){
        showProgressDialog("加载中...",true);
        OkGo.<String>post(Constants.URL+"kh/queryKh.do")
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
            recyclerView.setAdapter(adapter=new KhlbAdapter(R.layout.activity_khlb_item,mList));
            getData();

        }
    }
}
