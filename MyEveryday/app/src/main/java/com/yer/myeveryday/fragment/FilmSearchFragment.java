package com.yer.myeveryday.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yer.myeveryday.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FilmSearchFragment extends Fragment {

    private View view;

    public FilmSearchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view=inflater.inflate(R.layout.fragment_film_search, container, false);
        return view;
    }

}
