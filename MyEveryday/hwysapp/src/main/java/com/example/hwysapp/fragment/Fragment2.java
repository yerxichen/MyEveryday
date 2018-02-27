package com.example.hwysapp.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.hwysapp.R;
import com.example.hwysapp.adapter.MyGridViewAdapter;
import com.example.hwysapp.entity.GridItem;
import com.example.hwysapp.ui.DdActivity;
import com.example.hwysapp.ui.KhlbActivity;
import com.example.hwysapp.ui.TempActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment2 extends Fragment {

    private View view;

    private GridView gridView;
    private MyGridViewAdapter adapter;
    private List<GridItem> mList=new ArrayList<>();
    public Fragment2() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_fragment2,container, false);
        init();
        gridView=view.findViewById(R.id.gridview);
        gridView.setAdapter(adapter=new MyGridViewAdapter(mList,getActivity()));
        gridView.setOnItemClickListener(mOnItemClickListener);
        return view;
    }

    public void init(){
        mList.add(new GridItem(R.drawable.khgl,"客户管理"));
        mList.add(new GridItem(R.drawable.ddgl,"地点管理"));
    }

    private GridView.OnItemClickListener mOnItemClickListener= new GridView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            switch (i){
                case 0:
                    Intent in1=new Intent(getActivity(), KhlbActivity.class);
                    startActivity(in1);
                    break;
                case 1:
                    Intent in=new Intent(getActivity(), DdActivity.class);
                    startActivity(in);
                    break;
            }
        }
    };
}
