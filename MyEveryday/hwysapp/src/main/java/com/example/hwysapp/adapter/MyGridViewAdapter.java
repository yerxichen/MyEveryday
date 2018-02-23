package com.example.hwysapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hwysapp.R;
import com.example.hwysapp.entity.GridItem;

import java.util.List;

/**
 * Created by 87990 on 2018/2/16.
 */

public class MyGridViewAdapter extends BaseAdapter {
    private List<GridItem> mList;
    private Context mContext;

    public MyGridViewAdapter(List<GridItem> mList, Context mContext) {
        this.mList = mList;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder=null;
        if(view==null){
            view= LayoutInflater.from(mContext).inflate(R.layout.grid_item,viewGroup,false);
            holder=new ViewHolder();
            holder.img=view.findViewById(R.id.img);
            holder.tv=view.findViewById(R.id.tv);
            view.setTag(holder);
        }else{
            holder= (ViewHolder) view.getTag();
        }
        holder.img.setImageResource(mList.get(i).getImgRes());
        holder.tv.setText(mList.get(i).getTitle());
        return view;
    }

    static class  ViewHolder{
        private ImageView img;
        private TextView tv;
    }
}
