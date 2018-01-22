package com.example.you.lsmisclient.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.you.lsmisclient.R;
import com.example.you.lsmisclient.bean.College;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by You on 2018/1/21.
 */

public class CollegeAdapter extends RecyclerView.Adapter<CollegeAdapter.CollegeViewHolder> {

    //数据
    List<College> mDatas;
    //item点击
    private CollegeItemClickListener collegeItemClickListener=null;
    /**
     * 构造函数
     * @param datas
     */
    public CollegeAdapter(List<College> datas)
    {
        this.mDatas=datas;
    }

    /**
     * 无参构造
     */
    public CollegeAdapter()
    {
        mDatas=new ArrayList<>();

    }

    /**
     * 设置数据
     * @param mDatas
     */
    public void setmDatas(List<College> mDatas) {
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
    public CollegeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //item界面布局
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_college,parent,false);
        CollegeViewHolder holder=new CollegeViewHolder(view);
        return holder;
    }

    /**
     * 将数据与界面进行绑定的操作，设置每个item的数据等
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(final CollegeViewHolder holder, int position) {
        //item点击处理
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(collegeItemClickListener!=null)
                {
                    collegeItemClickListener.onCollegeItemClick(v,mDatas.get(holder.getAdapterPosition()));
                }
            }
        });

        //数据设置
        holder.collegeName.setText(mDatas.get(position).getCollegeName());
        holder.labCount.setText(mDatas.get(position).getLabCount());
        holder.collegeManager.setText(mDatas.get(position).getCollegeManager());
        holder.collegeIcon.setImageResource(R.mipmap.bookshelf_72px);
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
    class CollegeViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.college_name)
        TextView collegeName;
        @BindView(R.id.lab_count)
        TextView labCount;
        @BindView(R.id.college_manager)
        TextView collegeManager;
        @BindView(R.id.college_icon)
        ImageView collegeIcon;
        public CollegeViewHolder(View view)
        {
            super(view);
            ButterKnife.bind(this,view);
        }
    }

    /**
     * item点击监听接口
     */
    public interface CollegeItemClickListener{
        void onCollegeItemClick(View view,College college);
    }

    /**
     * 设置item点击监听
     * @param listener
     */
    public void setCollegeItemClickListener(CollegeItemClickListener listener)
    {
        this.collegeItemClickListener=listener;
    }
}
