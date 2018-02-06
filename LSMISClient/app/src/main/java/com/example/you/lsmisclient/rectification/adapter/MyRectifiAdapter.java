package com.example.you.lsmisclient.rectification.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.you.lsmisclient.R;
import com.example.you.lsmisclient.rectification.bean.MyRectification;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by You on 2018/2/6.
 */

public class MyRectifiAdapter extends  RecyclerView.Adapter<MyRectifiAdapter.MyRectifiViewHolder>{

    //数据
    List<MyRectification> mDatas;
    //点击监听
    private MyRectifiItemClickListener myRectifiItemClickListener;

    /**
     * 构造函数
     * @param mDatas
     */
    public MyRectifiAdapter(List<MyRectification> mDatas) {
        this.mDatas = mDatas;
    }

    /**
     * 无参构造
     */
    public MyRectifiAdapter() {
        mDatas=new ArrayList<>();
    }

    public List<MyRectification> getmDatas() {
        return mDatas;
    }

    public void setmDatas(List<MyRectification> mDatas) {
        this.mDatas = mDatas;
    }

    @Override
    public MyRectifiViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_my_rectifi,parent,false);
        MyRectifiViewHolder holder=new MyRectifiViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyRectifiViewHolder holder, int position) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(myRectifiItemClickListener!=null)
                {
                    myRectifiItemClickListener.onMyRectifiItemClickListener(v,mDatas.get(holder.getAdapterPosition()));
                }
            }
        });
        holder.descripionTv.setText(mDatas.get(position).getDescrip());
        holder.remainTimeTv.setText(mDatas.get(position).getRemainTime());
        holder.checkTimeTv.setText(mDatas.get(position).getCheckTime());
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    /**
     * 自定义ViewHolder
     */
    class MyRectifiViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.description_tv)
        TextView descripionTv;
        @BindView(R.id.remain_time_tv)
        TextView remainTimeTv;
        @BindView(R.id.check_time_tv)
        TextView checkTimeTv;
        public MyRectifiViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    /**
     * item点击
     */
    public interface MyRectifiItemClickListener{
        void onMyRectifiItemClickListener(View view,MyRectification myRectification);
    }

    public void setMyRectifiItemListener(MyRectifiItemClickListener listener)
    {
        this.myRectifiItemClickListener=listener;
    }
}
