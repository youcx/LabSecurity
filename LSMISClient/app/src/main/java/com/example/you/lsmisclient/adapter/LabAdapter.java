package com.example.you.lsmisclient.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.you.lsmisclient.R;
import com.example.you.lsmisclient.bean.Lab;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by You on 2018/1/22.
 */

public class LabAdapter extends RecyclerView.Adapter<LabAdapter.LabViewHolder>{

    //item点击
    private LabItemClickListener labItemClickListener=null;
    //数据
    List<Lab> mDatas;

    /**
     * 构造函数
     * @param mDatas
     */
    public LabAdapter(List<Lab> mDatas) {
        this.mDatas = mDatas;
    }

    /**
     * 无参构造
     */
    public LabAdapter() {
        mDatas=new ArrayList<>();
    }

    /**
     * 设置数据
     * @param mDatas
     */
    public void setmDatas(List<Lab> mDatas) {
        this.mDatas = mDatas;
        //刷新item内容
        notifyDataSetChanged();
    }

    @Override
    public LabViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //item界面布局
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lab,parent,false);
        LabViewHolder holder=new LabViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final LabViewHolder holder, int position) {
        //item点击处理
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(labItemClickListener!=null)
                {
                    labItemClickListener.onLabItemClickListener(v,mDatas.get(holder.getAdapterPosition()));
                }
            }
        });
        //
        holder.labName.setText(mDatas.get(position).getLabName());
        holder.labCollege.setText(mDatas.get(position).getDepartmentName());
        holder.labLevel.setText(mDatas.get(position).getMainLevelName());
        holder.labHazard.setText(mDatas.get(position).getLabHazard());
        holder.labSafePerson.setText(mDatas.get(position).getManagerName());
        holder.labTeacher.setText(mDatas.get(position).getResponserName());

    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    class LabViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.lab_name)
        TextView labName;
        @BindView(R.id.lab_college)
        TextView labCollege;
        @BindView(R.id.lab_level)
        TextView labLevel;
        @BindView(R.id.lab_hazard)
        TextView labHazard;
        @BindView(R.id.lab_safeperson)
        TextView labSafePerson;
        @BindView(R.id.lab_teacher)
        TextView labTeacher;
        public LabViewHolder(View view)
        {
            super(view);
            ButterKnife.bind(this,view);
        }
    }

    /**
     * item点击监听接口
     */
    public interface LabItemClickListener{
        void onLabItemClickListener(View view,Lab lab);
    }

    /**
     * 设置item点击监听
     * @param listener
     */
    public void setLabItemClickListener(LabItemClickListener listener)
    {
        this.labItemClickListener=listener;
    }
}
