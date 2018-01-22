package com.example.you.lsmisclient.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.you.lsmisclient.R;
import com.example.you.lsmisclient.bean.LabHazard;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by You on 2018/1/21.
 */

public class HazardAdapter extends RecyclerView.Adapter<HazardAdapter.HazardViewHolder>{
    //数据
    List<LabHazard> mDatas;
    //点击
    private HazardItemClickListener hazardItemClickListener=null;
    /**
     * 构造函数
     * @param mDatas
     */
    public HazardAdapter(List<LabHazard> mDatas) {
        this.mDatas = mDatas;
    }

    /**
     * 无参构造
     */
    public HazardAdapter() {
        mDatas=new ArrayList<>();
    }

    /**
     * 数据设置
     * @param mDatas
     */
    public void setmDatas(List<LabHazard> mDatas) {
        this.mDatas = mDatas;
        //刷新item内容
        notifyDataSetChanged();
    }

    /**
     * 创建ViewHolder,被LayoutManager调用
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public HazardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //item界面布局
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_hazard,parent,false);
        HazardViewHolder holder=new HazardViewHolder(view);
        return holder;
    }

    /**
     * 将数据与界面进行绑定的操作，设置每个item的数据等
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(final HazardViewHolder holder, int position) {

        //item点击处理
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(hazardItemClickListener!=null)
                {
                    hazardItemClickListener.onHazardItemClick(v,mDatas.get(holder.getAdapterPosition()));
                }
            }
        });
        //填充item数据
        holder.hazardIcon.setImageResource(R.mipmap.caution_72px);
        holder.hazardName.setText(mDatas.get(position).getHazardName());
        holder.labCount.setText(mDatas.get(position).getLabCount());
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
     * 自定义ViewHolder，持有每个item的界面元素
     */
    class HazardViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.hazard_icon)
        ImageView hazardIcon;
        @BindView(R.id.hazard_name)
        TextView hazardName;
        @BindView(R.id.lab_count_hazard)
        TextView labCount;
        public HazardViewHolder(View view)
        {
            super(view);
            ButterKnife.bind(this,view);
        }
    }

    /**
     * item点击接口
     */
    public interface HazardItemClickListener{
        void onHazardItemClick(View view,LabHazard labHazard);
    }

    /**
     * 设置item点击监听
     * @param listener
     */
    public void setHazardItemClickListener(HazardItemClickListener listener)
    {
        this.hazardItemClickListener=listener;
    }
}
