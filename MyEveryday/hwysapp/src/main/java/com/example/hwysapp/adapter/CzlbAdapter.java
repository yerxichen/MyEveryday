package com.example.hwysapp.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.hwysapp.R;
import com.example.hwysapp.utils.MyUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by 87990 on 2018/2/16.
 */

public class CzlbAdapter extends BaseQuickAdapter<JSONObject,BaseViewHolder> {
    public CzlbAdapter(int layoutResId, @Nullable List<JSONObject> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, JSONObject item) {
        try {
            helper.setText(R.id.tv1, MyUtil.Date2Day(item.getString("yssj")));
            helper.setText(R.id.tv2,item.getString("ysqd")+"=="+item.getString("yszd"));
            helper.setText(R.id.tv3,"￥"+item.getString("jg"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
