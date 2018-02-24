package com.example.hwysapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.hwysapp.R;
import com.example.hwysapp.adapter.ArrSpinnerAdapter;
import com.example.hwysapp.adapter.CzlbAdapter;
import com.example.hwysapp.adapter.DdlbAdapter;
import com.example.hwysapp.adapter.KhxmSpinnerAdapter;
import com.example.hwysapp.adapter.MySpinnerAdapter;
import com.example.hwysapp.utils.Constants;
import com.example.hwysapp.utils.SpUtil;
import com.example.hwysapp.utils.TipDialogUti;
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
import java.util.Calendar;
import java.util.List;

import static android.widget.ListPopupWindow.WRAP_CONTENT;

public class CzlbActivity extends BaseActivity {
    private static final String[] YEARARR = {"2018年", "2019年", "2020年", "2021年", "2022年"};
    private static final String[] MONTHARR = {"全部", "1月", "2月", "3月", "4月", "5月", "6月", "7月", "8月", "9月", "10月", "11月", "12月"};
    private Spinner sp1, sp2, sp3;
    private KhxmSpinnerAdapter adapter1;
    private ArrSpinnerAdapter adapter2, adapter3;
    private RecyclerView recyclerView;
    private CzlbAdapter adapter;
    private List<JSONObject> mList = new ArrayList<>();
    private JSONArray mArray;
    private String khxm = "", year, month = "全部";
    private boolean isSp1First = true;
    private boolean isSp2First = true;
    private boolean isSp3First = true;
    private QMUIPopup mNormalPopup;

    @Override
    public void initParms(Bundle parms) {

    }

    @Override
    public void bindView() {
        setView("账单", R.layout.activity_czlb);

    }

    @Override
    public void initView() {
        Calendar ca = Calendar.getInstance();
        year = String.valueOf(ca.get(Calendar.YEAR));
        month = String.valueOf(ca.get(Calendar.MONTH) + 1);
//        add=findViewById(R.id.add);
//        add.setVisibility(View.VISIBLE);
        recyclerView = findViewById(R.id.recyclerview);

        sp1 = findViewById(R.id.spinner1);
        sp2 = findViewById(R.id.spinner2);
        sp3 = findViewById(R.id.spinner3);


    }

    @Override
    public void doBusiness() {
        mArray = new JSONArray();
        adapter1 = new KhxmSpinnerAdapter(mContext, mArray);
        sp1.setAdapter(adapter1);
        sp2.setAdapter(adapter2 = new ArrSpinnerAdapter(YEARARR, mContext));

        sp3.setAdapter(adapter3 = new ArrSpinnerAdapter(MONTHARR, mContext));


        sp1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                try {
                    khxm = mArray.getJSONObject(i).getString("khxm");
                    if (!isSp1First) {
                        getData();
                    }
                    isSp1First = false;


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        //sp1.setSelection(0,false);

        sp2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                year = YEARARR[i].replace("年", "");
                if (!isSp2First) {
                    getData();
                }
                isSp2First = false;

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        for (int i = 0; i < YEARARR.length; i++) {
            if (year.equals(YEARARR[i].replace("年", ""))) {
                sp2.setSelection(i, false);
            }
        }

        sp3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                month = MONTHARR[i].replace("月", "");
                if (!isSp3First) {
                    getData();
                }
                isSp3First = false;

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        for (int i = 0; i < MONTHARR.length; i++) {
            if (month.equals(String.valueOf(MONTHARR[i]))) {
                sp3.setSelection(i, false);
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
        adapter = new CzlbAdapter(R.layout.activity_czlb_item, mList);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                JSONObject obj = (JSONObject) adapter.getData().get(position);
                try {
                    String bz = obj.getString("bz");
                    initNormalPopupIfNeed(bz);
                    mNormalPopup.setAnimStyle(QMUIPopup.ANIM_GROW_FROM_CENTER);
                    mNormalPopup.setPreferredDirection(QMUIPopup.DIRECTION_NONE);
                    mNormalPopup.show(view);
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        });
        adapter.setOnItemLongClickListener(new BaseQuickAdapter.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(BaseQuickAdapter adapter, View view, int position) {
                JSONObject obj = (JSONObject) adapter.getData().get(position);
                try {
                    String id = obj.getString("id");
                    String yssj=obj.getString("yssj");
                    String ysqd=obj.getString("ysqd");
                    String yszd=obj.getString("yszd");
                    String jg=obj.getString("jg");
                    String bz=obj.getString("bz");
                    showSimpleBottomSheetList(id,yssj,ysqd,yszd,jg,bz);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return false;
            }
        });

        getKhxm();
    }

    public void getData() {
        showProgressDialog("加载中...", true);
        OkGo.<String>post(Constants.URL + "hwys/hwyscx.do")
                .tag(this)
                .params("yhm", SpUtil.get(mContext, "YHM", "").toString())
                .params("khxm", khxm)
                .params("year", year)
                .params("month", month)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        dismissProgressDialog();
                        try {
                            String s = response.body().toString();
                            Log.d(TAG, "onSuccess: " + s);
                            JSONObject obj = new JSONObject(s);

                            String flag = obj.getString("FLAG");
                            if (flag.equals("1")) {
                                JSONArray arr = obj.getJSONArray("DATA");
                                mList.clear();
                                if (arr.length() > 0) {

                                    for (int i = 0; i < arr.length(); i++) {
                                        mList.add(arr.getJSONObject(i));

                                        //adapter.addData(arr.getJSONObject(i));
                                    }
                                    adapter.notifyDataSetChanged();

                                    //recyclerView.setAdapter(adapter=new CzlbAdapter(R.layout.activity_czlb_item,mList));

                                } else {
                                    adapter.notifyDataSetChanged();
                                    TipDialogUti.succss(mContext,"无数据！");
                                }


                            } else {


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
    public void hwyssc(String id) {
        showProgressDialog("操作进行中，请稍后...", true);
        OkGo.<String>post(Constants.URL + "hwys/hwyssc.do")
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
                                TipDialogUti.succss(mContext,"删除成功！");

                                getData();
                            } else {
                                TipDialogUti.succss(mContext,"删除失败,请联系管理员！");

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
    public void getKhxm() {
        // showProgressDialog("加载中...",true);
        OkGo.<String>post(Constants.URL + "kh/queryKh.do")
                .tag(this)
                .params("yhm", SpUtil.get(mContext, "YHM", "").toString())
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        // dismissProgressDialog();
                        try {
                            String s = response.body().toString();
                            JSONObject obj = new JSONObject(s);
                            String flag = obj.getString("FLAG");
                            if (flag.equals("1")) {
                                JSONArray arr = obj.getJSONArray("DATA");
                                mArray = arr;
                                adapter1 = new KhxmSpinnerAdapter(mContext, mArray);
                                sp1.setAdapter(adapter1);
                                khxm = mArray.getJSONObject(0).getString("khxm");
                                getData();

                            } else {
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
        if (requestCode == 1) {
            getData();

        }
    }

    private void showSimpleBottomSheetList(final String id,final String yssj,final String ysqd,final String yszd,final String jg,final String bz) {
        new QMUIBottomSheet.BottomListSheetBuilder(mContext)
                .addItem("修改")
                .addItem("删除")
                .setOnSheetItemClickListener(new QMUIBottomSheet.BottomListSheetBuilder.OnSheetItemClickListener() {
                    @Override
                    public void onClick(QMUIBottomSheet dialog, View itemView, int position, String tag) {
                        dialog.dismiss();
                        switch (position){
                            case 0:
                                Intent in=new Intent(mContext,HwysxgActivity.class);
                                in.putExtra("id",id);
                                in.putExtra("yssj",yssj);
                                in.putExtra("ysqd",ysqd);
                                in.putExtra("yszd",yszd);
                                in.putExtra("jg",jg);
                                in.putExtra("bz",bz);
                                startActivityForResult(in,1);
                                break;
                            case 1:
                                hwyssc(id);
                                break;
                        }
                    }
                })
                .build()
                .show();
    }

    private void initNormalPopupIfNeed(String bz) {
        if (mNormalPopup == null) {
            mNormalPopup = new QMUIPopup(mContext, QMUIPopup.DIRECTION_NONE);
        }
        TextView textView = new TextView(mContext);
        textView.setLayoutParams(mNormalPopup.generateLayoutParam(
                QMUIDisplayHelper.dp2px(mContext, 200),
                WRAP_CONTENT
        ));
        textView.setLineSpacing(QMUIDisplayHelper.dp2px(mContext, 4), 1.0f);
        int padding = QMUIDisplayHelper.dp2px(mContext, 20);
        textView.setPadding(padding, padding, padding, padding);
        textView.setText(bz);
        textView.setTextColor(ContextCompat.getColor(mContext, R.color.black));
        mNormalPopup.setContentView(textView);
        mNormalPopup.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                //mActionButton1.setText(getContext().getResources().getString(R.string.popup_normal_action_button_text_show));
            }
        });

    }
}
