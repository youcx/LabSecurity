package com.example.you.lsmisclient.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.you.lsmisclient.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class LabBaseInfoFragment extends Fragment {


    public LabBaseInfoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_lab_base_info, container, false);
    }

}
