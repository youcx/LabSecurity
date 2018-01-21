package com.example.you.lsmisclient.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.you.lsmisclient.R;
import com.example.you.lsmisclient.bean.MyCard;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * used for RecyclerView
 * Created by You on 2018/1/16.
 */

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.MyViewHolder>{

    //item点击监听接口
    private CardItemClickListener cardItemClickListener=null;

    //数据
    List<MyCard> mDatas;


    /**
     * 构造方法
     * @param datas
     */
    public CardAdapter(List<MyCard> datas)
    {
        this.mDatas=datas;
    }

    /**
     * 无参构造
     */
    public CardAdapter() {
        mDatas=new ArrayList<>();
    }

    /**
     * 设置适配器数据
     * @param datas
     */
    public void setmDatas(List<MyCard> datas)
    {
        this.mDatas=datas;
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
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //item界面布局
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item,parent,false);
        MyViewHolder holder=new MyViewHolder(view);

        return holder;
    }



    /**
     * 将数据与界面进行绑定的操作，设置每个item的数据等
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        //item点击处理
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cardItemClickListener!=null)
                {
                    cardItemClickListener.onCardItemClick(v,mDatas.get(holder.getAdapterPosition()));
                }
            }
        });
        //设置每个item的图标和名字
        holder.item_img.setImageResource(mDatas.get(position).getIcon());
        holder.item_txtview.setText(mDatas.get(position).getCard_name());

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
    class MyViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.item_img)
        ImageView item_img;
        @BindView(R.id.item_txtview)
        TextView item_txtview;
        public MyViewHolder(View view)
        {
            super(view);
            ButterKnife.bind(this,view);
        }
    }

    /**
     * item点击监听接口
     */
    public interface CardItemClickListener{
        void onCardItemClick(View view,MyCard myCard);
    }

    /**
     * 设置item点击监听
     * @param listener
     */
    public void setCardItemClickListener(CardItemClickListener listener){
        this.cardItemClickListener=listener;
    }
}
