package com.yer.myeveryday.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yer.myeveryday.R;
import com.yer.myeveryday.entity.GridViewItem;

import java.util.List;

/**
 * Created by 87990 on 2017/12/4.
 */

public class MyGridViewAdapter extends BaseQuickAdapter<GridViewItem,BaseViewHolder> {
    public MyGridViewAdapter(@LayoutRes int layoutResId, @Nullable List<GridViewItem> data) {
        super(layoutResId, data);
    }

    public MyGridViewAdapter(@Nullable List<GridViewItem> data) {
        super(data);
    }

    public MyGridViewAdapter(@LayoutRes int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, GridViewItem item) {
        helper.setImageResource(R.id.textView,item.getImageRes());
        helper.setText(R.id.textView,item.getTitle());
    }
}
