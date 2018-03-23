package com.example.you.lsmisclient.check.bean;

import android.view.View;
import android.widget.AdapterView;

import com.example.you.lsmisclient.treeview.TreeViewAdapter;
import com.example.you.lsmisclient.treeview.TreeViewItemClickListener;

/**
 * Created by You on 2018/3/18.
 */

public class CheckListItemClickListener extends TreeViewItemClickListener {

    public CheckListItemClickListener(TreeViewAdapter treeViewAdapter) {
        super(treeViewAdapter);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        super.onItemClick(parent, view, position, id);

    }
}
