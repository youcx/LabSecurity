package com.example.you.lsmisclient.lab.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.example.you.lsmisclient.R;

/**
 * Created by You on 2018/1/31.
 */

public class HazardListAdapter extends BaseExpandableListAdapter{
    String[] parentData;
    String[][][] childData;
    private Context context;

    public HazardListAdapter(String[] parentData,String[][][] childData, Context context ) {
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
        HazardGroupViewHolder groupViewHolder;
        convertView= LayoutInflater.from(context).inflate(R.layout.item_node_parent_hazard,parent,false);
        groupViewHolder=new HazardGroupViewHolder();
        groupViewHolder.tvTitle=(TextView) convertView.findViewById(R.id.parent_node_name);
        convertView.setTag(groupViewHolder);

        // groupViewHolder=(GroupViewHolder) convertView.getTag();

        groupViewHolder.tvTitle.setText(parentData[groupPosition]);
        return convertView;
    }

    //  获得子项显示的view
    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        HazardChildViewHolder childViewHolder;
        convertView=LayoutInflater.from(context).inflate(R.layout.item_node_child_hazard,parent,false);
        childViewHolder=new HazardChildViewHolder();
        childViewHolder.tvTitle=(TextView) convertView.findViewById(R.id.child_node_name);
        childViewHolder.countTv=(TextView) convertView.findViewById(R.id.child_node_count);
        convertView.setTag(childViewHolder);

        //   childViewHolder=(ChildViewHolder) convertView.getTag();

        childViewHolder.tvTitle.setText(childData[groupPosition][childPosition][0]);
        childViewHolder.countTv.setText(childData[groupPosition][childPosition][1]);
        return convertView;
    }

    //  子项是否可选中，如果需要设置子项的点击事件，需要返回true
    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    static class HazardGroupViewHolder {
        TextView tvTitle;
    }
    static class HazardChildViewHolder {
        TextView tvTitle;
        TextView countTv;
    }

}
