package com.example.tanzeem.internity.StudentActivity;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tanzeem.internity.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ResultFragmentOne extends Fragment {


    public ResultFragmentOne() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_result_fragment_one, container, false);
    }

}
