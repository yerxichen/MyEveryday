package com.yer.myeveryday.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by 87990 on 2017/12/19.
 */

public class TestAdapter<T> extends CommonAdapter<T> {


    public TestAdapter(Context mContext, List<T> mDatas, int mItemLayoutId) {
        super(mContext, mDatas, mItemLayoutId);
    }

    @Override
    public void convert(ViewHolder helper, T item) {

    }
}
