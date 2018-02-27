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

public class HwysKhlbAdapter extends BaseQuickAdapter<JSONObject,BaseViewHolder> {
    public HwysKhlbAdapter(int layoutResId, @Nullable List<JSONObject> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, JSONObject item) {
        try {
            String khxm=item.getString("khxm");
            String khx=khxm.substring(0,1);
            String khm=khxm.substring(1);
            helper.setText(R.id.tv1,khx);
            helper.setText(R.id.tv2,khm);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
