package com.example.hwysapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.hwysapp.R;
import com.example.hwysapp.adapter.HwysKhlbAdapter;
import com.example.hwysapp.adapter.KhlbAdapter;
import com.example.hwysapp.utils.Constants;
import com.example.hwysapp.utils.DividerGridItemDecoration;
import com.example.hwysapp.utils.SpUtil;
import com.example.hwysapp.utils.TipDialogUtil;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.qmuiteam.qmui.widget.dialog.QMUIBottomSheet;

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

       recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
       recyclerView.addItemDecoration(new DividerGridItemDecoration(mContext));
       recyclerView.setAdapter(adapter=new KhlbAdapter(R.layout.activity_khlb_item,mList));
       adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
           @Override
           public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
               try {
                   JSONObject obj= (JSONObject) adapter.getData().get(position);
                   String khxm=obj.getString("khxm");
                   String khdh=obj.getString("khdh");
                   String id=obj.getString("id");
                   showSimpleBottomSheetList(id,khxm,khdh);
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
                                mList.clear();
                                if(arr.length()>0){
                                    for(int i=0;i<arr.length();i++){
                                        mList.add(arr.getJSONObject(i));
                                    }
                                }else {
                                    TipDialogUtil.succss(mContext,"无数据！");

                                }

                                adapter.notifyDataSetChanged();
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
            getData();

        }
    }

    private void showSimpleBottomSheetList(final String id,final String khxm,final String khdh) {
        new QMUIBottomSheet.BottomListSheetBuilder(mContext)
                .addItem(R.drawable.xg,"修改","")
                .addItem(R.drawable.sc,"删除","")
                .setOnSheetItemClickListener(new QMUIBottomSheet.BottomListSheetBuilder.OnSheetItemClickListener() {
                    @Override
                    public void onClick(QMUIBottomSheet dialog, View itemView, int position, String tag) {
                        dialog.dismiss();
                        switch (position){
                            case 0:
                                Intent in=new Intent(mContext,KhxgActivity.class);
                                in.putExtra("id",id);
                                in.putExtra("khxm",khxm);
                                in.putExtra("khdh",khdh);
                                startActivityForResult(in,1);
                                break;
                            case 1:
                                khsc(id);
                                break;
                        }
                    }
                })
                .build()
                .show();
    }

    public void khsc(String id) {
        showProgressDialog("操作进行中，请稍后...", true);
        OkGo.<String>post(Constants.URL + "kh/delKh.do")
                .tag(this)
                .params("id", id)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        dismissProgressDialog();
                        try {
                            String s = response.body().toString();
                            JSONObject obj = new JSONObject(s);
                            String flag = obj.getString("FLAG");
                            if (flag.equals("1")) {
                                TipDialogUtil.succss(mContext,"删除成功！");

                                getData();
                            } else {
                                TipDialogUtil.succss(mContext,"删除失败,请联系管理员！");

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
