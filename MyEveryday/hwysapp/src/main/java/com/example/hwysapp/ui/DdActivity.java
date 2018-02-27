package com.example.hwysapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.BootstrapEditText;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.hwysapp.R;
import com.example.hwysapp.adapter.DdlbAdapter;
import com.example.hwysapp.utils.Constants;
import com.example.hwysapp.utils.SpUtil;
import com.example.hwysapp.utils.TipDialogUtil;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.qmuiteam.qmui.util.QMUIDisplayHelper;
import com.qmuiteam.qmui.widget.dialog.QMUIBottomSheet;
import com.qmuiteam.qmui.widget.popup.QMUIPopup;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static android.widget.ListPopupWindow.WRAP_CONTENT;

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
       adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
           @Override
           public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
               JSONObject obj = (JSONObject) adapter.getData().get(position);

               try {
                   String id = obj.getString("id");
                   String ddmc = obj.getString("ddmc");
                   showSimpleBottomSheetList(id,ddmc);
               } catch (JSONException e) {
                   e.printStackTrace();
               }


           }
       });
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
                                mList.clear();
                                JSONArray arr=obj.getJSONArray("DATA");
                                if (arr.length() > 0) {
                                    for(int i=0;i<arr.length();i++){
                                        mList.add(arr.getJSONObject(i));
                                    }
                                }else{
                                    TipDialogUtil.succss(mContext,"无数据！");
                                }

                                adapter.notifyDataSetChanged();

                            }else{
                                showAlertDialog("查询失败！请联系管理员");
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

    private void showSimpleBottomSheetList(final String id,final String ddmc) {
        new QMUIBottomSheet.BottomListSheetBuilder(mContext)
                .addItem(R.drawable.xg,"修改","")
                .addItem(R.drawable.sc,"删除","")
                .setOnSheetItemClickListener(new QMUIBottomSheet.BottomListSheetBuilder.OnSheetItemClickListener() {
                    @Override
                    public void onClick(QMUIBottomSheet dialog, View itemView, int position, String tag) {
                        dialog.dismiss();
                        switch (position){
                            case 0:
                                Intent in=new Intent(mContext,DdxgActivity.class);
                                in.putExtra("id",id);
                                in.putExtra("ddmc",ddmc);
                                startActivityForResult(in,1);
                                break;
                            case 1:
                                ddsc(id);
                                break;
                        }
                    }
                })
                .build()
                .show();
    }

    public void ddsc(String id) {
        showProgressDialog("操作进行中，请稍后...", true);
        OkGo.<String>post(Constants.URL + "dd/delDd.do")
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
