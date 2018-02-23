package com.example.hwysapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.example.hwysapp.R;

import org.json.JSONArray;
import org.json.JSONObject;

public class KhxmSpinnerAdapter extends BaseAdapter implements SpinnerAdapter {
    private JSONArray mArray;
    private Context mContext;

    public KhxmSpinnerAdapter(Context pContext, JSONArray mArray) {
        this.mContext = pContext;
        this.mArray = mArray;
    }

    @Override
    public int getCount() {
        return mArray.length();
    }

    @Override
    public JSONObject getItem(int position) {
        JSONObject obj = new JSONObject();
        try {
            obj = mArray.getJSONObject(position);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return obj;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        try {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.spinner_text, null);
            TextView tv = (TextView) convertView.findViewById(R.id.tv);
            tv.setText(mArray.getJSONObject(position).getString("khxm"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return convertView;
    }


}
