package com.yer.myeveryday.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yer.myeveryday.entity.Dyxx;

import java.util.List;

/**
 * Created by yer on 2017/10/18.
 */

public class FilmAdapter extends BaseQuickAdapter<Dyxx,BaseViewHolder> {

    public FilmAdapter(@LayoutRes int layoutResId, @Nullable List<Dyxx> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Dyxx item) {

    }
}
