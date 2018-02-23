package com.yer.myeveryday.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yer.myeveryday.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FilmHomeFragment extends Fragment {

    private View view;
    private RecyclerView recyclerView;
    public FilmHomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view=inflater.inflate(R.layout.fragment_film, container, false);
        recyclerView= (RecyclerView) view.findViewById(R.id.recyclerview);

        return view;
    }

}
