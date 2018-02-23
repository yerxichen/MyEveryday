package com.yer.myeveryday.ui;

import android.app.Activity;
import android.os.Bundle;
import android.widget.GridView;

import com.yer.myeveryday.R;
import com.yer.myeveryday.entity.GridViewItem;

import java.util.List;

public class SrzcHomeActivity extends BaseActivity {

    private GridView gridView;
    private List<GridViewItem> itemList;
    @Override
    public void initParms(Bundle parms) {
        itemList.add(new GridViewItem(R.drawable.home_srzc,"我的账单"));
        itemList.add(new GridViewItem(R.drawable.home_srzc,"我要记账"));
    }

    @Override
    public void bindView() {
        setView("我的账本", R.layout.avtivity_srzc_home);
    }

    @Override
    public void initView() {
        gridView= (GridView) findViewById(R.id.gridView);
    }

    @Override
    public void doBusiness() {

    }
}
