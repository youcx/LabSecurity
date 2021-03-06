package com.example.you.lsmisclient.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.you.lsmisclient.R;
import com.example.you.lsmisclient.bean.CheckMission;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by You on 2018/1/25.
 */

public class CheckMissionAdapter extends RecyclerView.Adapter<CheckMissionAdapter.CheckMissionViewHolder>{

    //数据
    private List<CheckMission> mDatas;
    //点击监听
    private CheckMissionItemClickListener checkMissionItemClickListener;

    /**
     * 构造函数
     * @param mDatas
     */
    public CheckMissionAdapter(List<CheckMission> mDatas) {

        this.mDatas = mDatas;
    }

    /**
     * 无参构造
     */
    public CheckMissionAdapter() {
        mDatas=new ArrayList<>();
    }

    /**
     * 设置数据
     * @param mDatas
     */
    public void setmDatas(List<CheckMission> mDatas) {
        this.mDatas = mDatas;
        //刷新item内容
        notifyDataSetChanged();
    }

    public List<CheckMission> getmDatas() {
        return mDatas;
    }

    @Override
    public CheckMissionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_check_mission,parent,false);
        CheckMissionViewHolder holder=new CheckMissionViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final CheckMissionViewHolder holder, int position) {
        //item点击
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkMissionItemClickListener!=null)
                {
                    checkMissionItemClickListener.onCheckMissionItemClickListener(v,mDatas.get(holder.getAdapterPosition()));
                }
            }
        });

        holder.checkMissionName.setText(mDatas.get(position).getTaskTitle());
        holder.missionDescription.setText(mDatas.get(position).getTaskDesc());
        holder.missionStartTime.setText(mDatas.get(position).getCheckBeginTime().substring(0,10));
        holder.missionEndTime.setText(mDatas.get(position).getCheckEndTime().substring(0,10));
        if(mDatas.get(position).getLeftDay()>-1)
            holder.missionRemainingTime.setText("还剩"+mDatas.get(position).getLeftDay()+"天");
        else
            holder.missionRemainingTime.setText("已过期");
        holder.missionAllLabs.setText(""+mDatas.get(position).getIncLabCount());
        holder.missionCheckedLabs.setText(""+mDatas.get(position).getCheckedLabCount());
        holder.missionRemainingLabs.setText(""+(mDatas.get(position).getIncLabCount()-mDatas.get(position).getCheckedLabCount()));
        holder.alreadlyCheckedCount.setText(""+mDatas.get(position).getTotalCheckTitleCount());
        holder.inconformityCount.setText(""+mDatas.get(position).getUnMatchTitleCount());
        holder.alreadlyReformCount.setText(""+(mDatas.get(position).getUnMatchTitleCount()-mDatas.get(position).getChangingTitleCount()));
        holder.inReformCount.setText(""+mDatas.get(position).getChangingTitleCount());



    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    /**
     * 自定义ViewHolder
     */
    class CheckMissionViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.check_mission_name)
        TextView checkMissionName;
        @BindView(R.id.mission_description)
        TextView missionDescription;
        @BindView(R.id.mission_start_time)
        TextView missionStartTime;
        @BindView(R.id.mission_end_time)
        TextView missionEndTime;
        @BindView(R.id.mission_remaining_time)
        TextView missionRemainingTime;
        @BindView(R.id.mission_all_labs)
        TextView missionAllLabs;
        @BindView(R.id.mission_checked_labs)
        TextView missionCheckedLabs;
        @BindView(R.id.mission_remaining_labs)
        TextView missionRemainingLabs;
        @BindView(R.id.alreadly_checked_count)
        TextView alreadlyCheckedCount;
        @BindView(R.id.inconformity_count)
        TextView inconformityCount;
        @BindView(R.id.alreadly_reform_count)
        TextView alreadlyReformCount;
        @BindView(R.id.in_reform_count)
        TextView inReformCount;
        public CheckMissionViewHolder(View view)
        {
            super(view);
            ButterKnife.bind(this,view);
        }
    }

    /**
     * item点击接口
     */
    public interface CheckMissionItemClickListener{
        void onCheckMissionItemClickListener(View view,CheckMission checkMission);
    }

    public void setCheckMissionItemListener(CheckMissionItemClickListener listener){
        this.checkMissionItemClickListener=listener;
    }
}
