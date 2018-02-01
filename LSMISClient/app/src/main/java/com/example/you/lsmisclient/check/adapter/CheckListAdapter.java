package com.example.you.lsmisclient.check.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.example.you.lsmisclient.R;

import java.util.List;

/**
 * Created by You on 2018/1/30.
 */

public class CheckListAdapter extends BaseExpandableListAdapter {
    String[] parentData;
    String[][] childData;
    private Context context;

    public CheckListAdapter( String[] parentData,String[][] childData, Context context) {
        this.childData = childData;
        this.context = context;
        this.parentData = parentData;
    }

    //  获得某个父项的某个子项
    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return childData[groupPosition][childPosition];
    }

    //  获得父项的数量
    @Override
    public int getGroupCount() {
        return parentData.length;
    }

    //  获得某个父项的子项数目
    @Override
    public int getChildrenCount(int groupPosition) {
        return childData[groupPosition].length;
    }

    //  获得某个父项
    @Override
    public Object getGroup(int groupPosition) {
        return childData[groupPosition];
    }

    //  获得某个父项的id
    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    //  获得某个父项的某个子项的id
    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    //  按函数的名字来理解应该是是否具有稳定的id，这个方法目前一直都是返回false，没有去改动过
    @Override
    public boolean hasStableIds() {
        return false;
    }

    //  获得父项显示的view
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        GroupViewHolder groupViewHolder;
            convertView= LayoutInflater.from(context).inflate(R.layout.item_parent_node,parent,false);
            groupViewHolder=new GroupViewHolder();
            groupViewHolder.tvTitle=(TextView) convertView.findViewById(R.id.parent_node_name);
            convertView.setTag(groupViewHolder);

           // groupViewHolder=(GroupViewHolder) convertView.getTag();

        groupViewHolder.tvTitle.setText(parentData[groupPosition]);
        return convertView;
    }

    //  获得子项显示的view
    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        ChildViewHolder childViewHolder;
            convertView=LayoutInflater.from(context).inflate(R.layout.item_child_node,parent,false);
            childViewHolder=new ChildViewHolder();
            childViewHolder.tvTitle=(TextView) convertView.findViewById(R.id.child_node_name);
            convertView.setTag(childViewHolder);

         //   childViewHolder=(ChildViewHolder) convertView.getTag();

        childViewHolder.tvTitle.setText(childData[groupPosition][childPosition]);
        return convertView;
    }

    //  子项是否可选中，如果需要设置子项的点击事件，需要返回true
    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    static class GroupViewHolder {
        TextView tvTitle;
    }
    static class ChildViewHolder {
        TextView tvTitle;
    }
}
