package com.yer.myeveryday.ui;

import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.callback.ItemDragAndSwipeCallback;
import com.chad.library.adapter.base.listener.OnItemDragListener;
import com.chad.library.adapter.base.listener.OnItemSwipeListener;
import com.yer.myeveryday.R;
import com.yer.myeveryday.adapter.ZdgllbAdapter;
import com.yer.myeveryday.entity.Zdy;
import com.yer.myeveryday.utils.ToastUtil;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

public class ZdglActivity extends BaseActivity {

    private TextView tv_add;
    private List<Zdy> mList=new ArrayList<>();
    private RecyclerView recyclerView;
    private ZdgllbAdapter adpter;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    adpter=new ZdgllbAdapter(R.layout.activity_zdgl,mList);
                    recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
                    recyclerView.setAdapter(adpter);
                    break;
            }
        }
    };
    @Override
    public void initParms(Bundle parms) {

    }

    @Override
    public void bindView() {
        setView("字典管理", R.layout.activity_zdgl);
    }

    @Override
    public void initView() {
        tv_add= (TextView) findViewById(R.id.add);
        tv_add.setVisibility(View.VISIBLE);
        recyclerView= (RecyclerView) findViewById(R.id.recyclerview);
    }

    @Override
    public void doBusiness() {
        tv_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in=new Intent(mContext,ZdglxzActivity.class);
                startActivityForResult(in,1);
            }
        });
        //查询数据库字典列表
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                mList= DataSupport.findAll(Zdy.class);
//                Message message=new Message();
//                message.what=1;
//                handler.sendMessage(message);
//            }
//        }).start();
        mList= DataSupport.findAll(Zdy.class);
        adpter=new ZdgllbAdapter(R.layout.activity_zdgl_item,mList);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(adpter);

        // 开启滑动删除
        adpter.enableSwipeItem();
        ItemDragAndSwipeCallback itemDragAndSwipeCallback = new ItemDragAndSwipeCallback(adpter);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(itemDragAndSwipeCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
        adpter.setOnItemSwipeListener(new OnItemSwipeListener() {
            @Override
            public void onItemSwipeStart(RecyclerView.ViewHolder viewHolder, int pos) {
                //Log.d("my", "onItemSwipeStart: 1 ");
            }

            @Override
            public void clearView(RecyclerView.ViewHolder viewHolder, int pos) {
               // Log.d("my", "onItemSwipeStart: 2 ");
            }

            @Override
            public void onItemSwiped(RecyclerView.ViewHolder viewHolder, int pos) {
               // Log.d("my", "on1: "+pos);
               // Log.d("my", "onaRRY: "+mList.get(pos).getId());
                //数据库删除该条记录
                int co=DataSupport.delete(Zdy.class, mList.get(pos).getId());
                if(co>0){

                }

            }

            @Override
            public void onItemSwipeMoving(Canvas canvas, RecyclerView.ViewHolder viewHolder, float dX, float dY, boolean isCurrentlyActive) {
              //  Log.d("my", "onItemSwipeStart: 4 ");
            }
        });

    }
}
