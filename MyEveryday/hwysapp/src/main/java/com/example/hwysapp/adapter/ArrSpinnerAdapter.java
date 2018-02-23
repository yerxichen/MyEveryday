package com.example.hwysapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.example.hwysapp.R;

/**
 * Created by 87990 on 2018/2/19.
 */

public class ArrSpinnerAdapter extends BaseAdapter implements SpinnerAdapter {
    private String[] arr;
    private Context mContext;

    public ArrSpinnerAdapter(String[] arr, Context mContext) {
        this.arr = arr;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return arr.length;
    }

    @Override
    public Object getItem(int i) {
        return arr[i];
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = LayoutInflater.from(mContext).inflate(R.layout.spinner_text, null);
        TextView tv = (TextView) view.findViewById(R.id.tv);
        tv.setText(String.valueOf(arr[i]));
        return view;
    }

}
