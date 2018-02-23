package com.yer.myeveryday.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseItemDraggableAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yer.myeveryday.R;
import com.yer.myeveryday.entity.Zdy;

import java.util.List;

/**
 * Created by 87990 on 2017/12/20.
 */

public class ZdgllbAdapter extends BaseItemDraggableAdapter<Zdy, BaseViewHolder> {


    public ZdgllbAdapter(@LayoutRes int layoutResId, @Nullable List<Zdy> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Zdy item) {
        helper.setText(R.id.tv1,String.valueOf(item.getId()));
        helper.setText(R.id.tv2,item.getDm());
        helper.setText(R.id.tv3,item.getDmfl());
        helper.setText(R.id.tv4,item.getMc());
    }
}
