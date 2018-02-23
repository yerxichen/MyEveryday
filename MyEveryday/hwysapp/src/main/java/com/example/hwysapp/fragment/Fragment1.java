package com.example.hwysapp.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.hwysapp.R;
import com.example.hwysapp.adapter.KhlbAdapter;
import com.example.hwysapp.adapter.MyGridViewAdapter;
import com.example.hwysapp.entity.GridItem;
import com.example.hwysapp.ui.CzlbActivity;
import com.example.hwysapp.ui.DdActivity;
import com.example.hwysapp.ui.KhlbActivity;
import com.example.hwysapp.ui.TempActivity;
import com.example.hwysapp.utils.Constants;
import com.example.hwysapp.utils.SpUtil;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment1 extends Fragment {

    private View view;

    private GridView gridView;
    private MyGridViewAdapter adapter;
    private List<GridItem> mList;
    public Fragment1() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view=inflater.inflate(R.layout.fragment_fragment1, container, false);
        mList=new ArrayList<>();
        init();
        gridView=view.findViewById(R.id.gridview);
        gridView.setAdapter(adapter=new MyGridViewAdapter(mList,getActivity()));
        gridView.setOnItemClickListener(mOnItemClickListener);
        return view;
    }

    public void init(){
        mList.add(new GridItem(R.drawable.wyjz,"我要记账"));
        mList.add(new GridItem(R.drawable.wycz,"我要查账"));
    }

    private GridView.OnItemClickListener mOnItemClickListener= new GridView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            switch (i){
                case 0:
                    Intent in=new Intent(getActivity(), KhlbActivity.class);
                    startActivity(in);
                    break;
                case 1:
                    Intent in1=new Intent(getActivity(), CzlbActivity.class);
                    startActivity(in1);
                    break;
            }
        }
    };
}
