package com.example.you.lsmisclient.check.fragment;


import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.you.lsmisclient.R;
import com.example.you.lsmisclient.check.CheckActivity;
import com.example.you.lsmisclient.check.adapter.CheckListAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.texy.treeview.TreeNode;
import me.texy.treeview.TreeView;


/**
 * A simple {@link Fragment} subclass.
 */
public class CheckListFragment extends Fragment {
    //bind
    @BindView(R.id.check_list_expandLV)
    ExpandableListView checkListExpandTV;

    //数据
    String[] item1=null;
    String[][] item2=null;
    //adapter
    private CheckListAdapter checkListAdapter;

    public CheckListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_check_list,container, false);
        //bind
        ButterKnife.bind(this,view);
        //初始化数据
        initDatas();
        checkListAdapter=new CheckListAdapter(item1,item2,getActivity());
        checkListExpandTV.setAdapter(checkListAdapter);
        checkListExpandTV.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
              //  Toast.makeText(getActivity(),"点击了"+item2[groupPosition][childPosition],Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(getActivity(), CheckActivity.class);
                startActivity(intent);
                return false;
            }
        });
        return view;
    }

    private void initDatas()
    {
        item1=new String[12];
        item2=new String[12][6];
        for(int i=0;i<12;i++)
        {
            item1[i]="组织结构:"+i;
            for(int j=0;j<6;j++)
            {
                item2[i][j]="子目录"+j;
            }
        }
    }



}
