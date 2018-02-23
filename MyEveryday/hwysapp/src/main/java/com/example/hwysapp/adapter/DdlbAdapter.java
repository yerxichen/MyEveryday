package com.example.hwysapp.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.hwysapp.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by 87990 on 2018/2/16.
 */

public class DdlbAdapter extends BaseQuickAdapter<JSONObject,BaseViewHolder> {
    public DdlbAdapter(int layoutResId, @Nullable List<JSONObject> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, JSONObject item) {
        try {
            helper.setText(R.id.tv1,item.getString("ddmc"));
            helper.setText(R.id.tv2,item.getString("djsj"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
