package com.example.you.lsmisclient.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.you.lsmisclient.R;
import com.example.you.lsmisclient.bean.LabLevel;
import com.example.you.lsmisclient.list.LevelListActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by You on 2018/1/21.
 */

public class LevelAdapter extends RecyclerView.Adapter<LevelAdapter.LevelViewHolder> {

    //数据
    List<LabLevel> mDatas;
    //
    private LevelItemClickListener levelItemClickListener=null;

    /**
     * 构造函数
     * @param mDatas
     */
    public LevelAdapter(List<LabLevel> mDatas) {
        this.mDatas = mDatas;
    }

    /**
     * 无参构造
     */
    public LevelAdapter() {
        mDatas=new ArrayList<>();
    }

    /**
     * 设置数据
     * @param mDatas
     */
    public void setmDatas(List<LabLevel> mDatas) {
        this.mDatas = mDatas;
        //刷新item内容
        notifyDataSetChanged();
    }

    /**
     *  创建ViewHolder,被LayoutManager调用
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public LevelViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //item界面布局
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_level,parent,false);
        LevelViewHolder holder=new LevelViewHolder(view);
        return holder;
    }

    /**
     * 将数据与界面进行绑定的操作，设置每个item的数据等
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(final LevelViewHolder holder, int position) {
        //item点击处理
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(levelItemClickListener!=null)
                {
                    levelItemClickListener.onLevelItemClickListener(v,mDatas.get(holder.getAdapterPosition()));
                }
            }
        });
        //数据设置
        holder.levelName.setText(mDatas.get(position).getLevelName());
        holder.labCount.setText(mDatas.get(position).getLabCount());
        holder.levelIcon.setImageResource(R.mipmap.rocket_72px);
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
     * 自定义ViewHolder，持有每个item的界面元素
     */
    class LevelViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.level_name)
        TextView levelName;
        @BindView(R.id.lab_count_level_tv)
        TextView labCount;
        @BindView(R.id.level_icon)
        ImageView levelIcon;
        public LevelViewHolder(View view)
        {
            super(view);
            ButterKnife.bind(this,view);
        }
    }

    /**
     * item点击接口
     */
    public interface LevelItemClickListener{
        void onLevelItemClickListener(View view,LabLevel labLevel);
    }

    /**
     * 设置item点击监听
     * @param listener
     */
    public void setLevelItemCLickListener(LevelItemClickListener listener)
    {
        this.levelItemClickListener=listener;
    }
}
