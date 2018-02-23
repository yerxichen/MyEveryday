package com.yer.myeveryday.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.yer.myeveryday.R;
import com.yer.myeveryday.adapter.CommonAdapter;
import com.yer.myeveryday.adapter.MyGridViewAdapter;
import com.yer.myeveryday.adapter.ViewHolder;
import com.yer.myeveryday.entity.GridViewItem;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends BaseActivity {

    private GridView gridView;
    private CommonAdapter adapter;
    private List<GridViewItem> list=new ArrayList<>();



    @Override
    public void initParms(Bundle parms) {

    }

    @Override
    public void bindView() {
        setView("主页",R.layout.activity_home);
    }

    @Override
    public void initView() {
        gridView= (GridView) findViewById(R.id.gridView);
    }


    @Override
    public void doBusiness() {
        list.add(new GridViewItem(R.drawable.home_srzc,"收入支出"));
        list.add(new GridViewItem(R.drawable.home_yyyl,"影音娱乐"));
        list.add(new GridViewItem(R.drawable.home_yyyl,"字典管理"));
        gridView.setAdapter(adapter=new CommonAdapter<GridViewItem>(mContext,list,R.layout.activity_home_item) {
            @Override
            public void convert(ViewHolder helper, GridViewItem item) {
                helper.setText(R.id.textView,item.getTitle());
                helper.setImageResource(R.id.ImageView,item.getImageRes());
            }
        });
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        Intent intent0=new Intent(mContext,JzxzActivity.class);
                        startActivity(intent0);
                        break;
                    case 1:
                        break;
                    case 2:
                        Intent intent3=new Intent(mContext,ZdglActivity.class);
                        startActivity(intent3);
                        break;
                }
            }
        });

    }
}
