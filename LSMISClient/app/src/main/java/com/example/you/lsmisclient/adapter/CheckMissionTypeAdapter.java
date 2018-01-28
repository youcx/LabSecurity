package com.example.you.lsmisclient.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.you.lsmisclient.R;
import com.example.you.lsmisclient.bean.CheckMissionType;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by You on 2018/1/28.
 */

public class CheckMissionTypeAdapter extends RecyclerView.Adapter<CheckMissionTypeAdapter.CheckMissionTypeViewHolder>{

    //数据
    List<CheckMissionType> mDatas;
    //点击监听
    private MissionTypeItemClickListener itemClickListener;

    /**
     * 构造函数
     * @param mDatas
     */
    public CheckMissionTypeAdapter(List<CheckMissionType> mDatas) {
        this.mDatas = mDatas;
    }

    /**
     * 无参构造
     */
    public CheckMissionTypeAdapter() {
        mDatas=new ArrayList<>();
    }

    public List<CheckMissionType> getmDatas() {
        return mDatas;
    }

    public void setmDatas(List<CheckMissionType> mDatas) {
        this.mDatas = mDatas;
        //刷新item内容
        notifyDataSetChanged();
    }

    @Override
    public CheckMissionTypeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mission_type,parent,false);
        CheckMissionTypeViewHolder holder=new CheckMissionTypeViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final CheckMissionTypeViewHolder holder, int position) {

        //item点击
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(itemClickListener!=null)
                {
                    itemClickListener.onMissionTypeItemClickListener(v,mDatas.get(holder.getAdapterPosition()));
                }
            }
        });
        //
        holder.missionTypeName.setText(mDatas.get(position).getTypeName());
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    /**
     * 自定义ViewHolder
     */
    class CheckMissionTypeViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.mission_type_name)
        TextView missionTypeName;
        public CheckMissionTypeViewHolder(View view)
        {
            super(view);
            ButterKnife.bind(this,view);
        }
    }

    /**
     * item点击接口
     */
    public interface MissionTypeItemClickListener{
        void onMissionTypeItemClickListener(View view, CheckMissionType checkMissionType);
    }

    public void serMissionTypeItemListener(MissionTypeItemClickListener listener)
    {
        this.itemClickListener=listener;
    }
}
