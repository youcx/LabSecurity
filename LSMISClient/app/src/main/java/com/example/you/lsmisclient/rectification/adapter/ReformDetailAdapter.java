package com.example.you.lsmisclient.rectification.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.you.lsmisclient.R;
import com.example.you.lsmisclient.rectification.bean.ReformDetail;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by You on 2018/3/4.
 */

public class ReformDetailAdapter  extends RecyclerView.Adapter<ReformDetailAdapter.ReformDetailViewHolder>{

    List<ReformDetail> mDatas;
    //点击监听
    private BtnClickInterface mBtnClickInterface;

    /**
     * 构造函数
     * @param mDatas
     */
    public ReformDetailAdapter(List<ReformDetail> mDatas) {
        this.mDatas = mDatas;
    }

    /**
     * 无参构造
     */
    public ReformDetailAdapter() {
        mDatas=new ArrayList<>();
    }

    public List<ReformDetail> getmDatas() {
        return mDatas;
    }

    public void setmDatas(List<ReformDetail> mDatas) {
        this.mDatas = mDatas;
        notifyDataSetChanged();
    }

    @Override
    public ReformDetailViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_reform_detail,parent,false);
        ReformDetailViewHolder holder=new ReformDetailViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ReformDetailViewHolder holder,final  int position) {
        holder.reformItemDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mBtnClickInterface!=null)
                {
                    mBtnClickInterface.onclick(v,position);
                }
            }
        });
        holder.checkResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mBtnClickInterface!=null)
                {
                    mBtnClickInterface.onclick(v,position);
                }
            }
        });
        holder.pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mBtnClickInterface!=null)
                {
                    mBtnClickInterface.onclick(v,position);
                }
            }
        });
        holder.document.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mBtnClickInterface!=null)
                {
                    mBtnClickInterface.onclick(v,position);
                }
            }
        });
        //整改相关
        holder.reformResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mBtnClickInterface!=null)
                {
                    mBtnClickInterface.onclick(v,position);
                }
            }
        });
        holder.reformPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mBtnClickInterface!=null)
                {
                    mBtnClickInterface.onclick(v,position);
                }
            }
        });
        holder.reformDocument.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mBtnClickInterface!=null)
                {
                    mBtnClickInterface.onclick(v,position);
                }
            }
        });
        holder.reformBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mBtnClickInterface!=null)
                {
                    mBtnClickInterface.onclick(v,position);
                }
            }
        });
        holder.reformTitle.setText("第一次整改");
        holder.reformItem.setText("1.1.1 有校级实验室安全工作领导机构，由校领导作为负责人，相关职能部门参与，设办公室");
        holder.reformNeirong.setText(mDatas.get(position).getQuestionDesc());
        holder.checkPerson.setText("检查人："+mDatas.get(position).getRecordMemberName());
        holder.checkTime.setText("检查时间："+mDatas.get(position).getPubTime());
        holder.reformTime.setText("整改期限："+mDatas.get(position).getAdviceChangeTime());

    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    /**
     * 自定义ViewHolder
     */
    class ReformDetailViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.reform_item_detail)
        ImageView reformItemDetail;
        @BindView(R.id.check_result)
        TextView checkResult;
        @BindView(R.id.pic)
        TextView pic;
        @BindView(R.id.document)
        TextView document;
        //
        @BindView(R.id.reform_title)
        TextView reformTitle;
        @BindView(R.id.reform_item)
        TextView reformItem;
        @BindView(R.id.reform_neirong)
        TextView reformNeirong;
        @BindView(R.id.check_person)
        TextView checkPerson;
        @BindView(R.id.check_time)
        TextView checkTime;
        @BindView(R.id.reform_time)
        TextView reformTime;
        //整改
        @BindView(R.id.reform_result)
        TextView reformResult;
        @BindView(R.id.reform_pic)
        TextView reformPic;
        @BindView(R.id.reform_document)
        TextView reformDocument;
        @BindView(R.id.reform_btn)
        Button reformBtn;
        public ReformDetailViewHolder(View view) {
            super(view);
            ButterKnife.bind(this,view);
        }
    }

    /**
     * 处理item中各种点击
     */
    public interface BtnClickInterface{
         void onclick(View view,int postion);

    }

    public void setOnBtnClick(BtnClickInterface listeer){
        this.mBtnClickInterface=listeer;
    }


}
