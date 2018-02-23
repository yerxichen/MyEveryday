package com.example.hwysapp.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.hwysapp.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by 87990 on 2018/2/14.
 */

public class ZdGridAdapter extends BaseQuickAdapter<JSONObject,BaseViewHolder> {
    public ZdGridAdapter(int layoutResId, @Nullable List<JSONObject> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, JSONObject item) {
        try {
            helper.setImageResource(R.id.imageview,item.getInt("imageId"));
            helper.setText(R.id.tv,item.getString("title"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
