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
 * Created by You on 2018/3/10.
 */

public class InReformDetailAdapter extends RecyclerView.Adapter<InReformDetailAdapter.InReformViewHolder>{

    //数据
    List<ReformDetail> mDatas;
    //点击
    private BtnClickListener btnClickListener;

    /**
     * 构造函数
     * @param mDatas
     */
    public InReformDetailAdapter(List<ReformDetail> mDatas) {
        this.mDatas = mDatas;
    }

    /**
     * 无参构造
     */
    public InReformDetailAdapter() {
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
    public int getItemCount() {
        return mDatas.size();
    }

    @Override
    public InReformViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_reform_detail_for_checker,parent,false);
        InReformViewHolder holder=new InReformViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(InReformViewHolder holder,final int position) {

        holder.reformItemDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(btnClickListener!=null)
                {
                    btnClickListener.onclick(v,position);
                }
            }
        });
        holder.checkResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(btnClickListener!=null)
                {
                    btnClickListener.onclick(v,position);
                }
            }
        });
        holder.pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(btnClickListener!=null)
                {
                    btnClickListener.onclick(v,position);
                }
            }
        });
        holder.document.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(btnClickListener!=null)
                {
                    btnClickListener.onclick(v,position);
                }
            }
        });
        //检查点击
        holder.reviewResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(btnClickListener!=null)
                {
                    btnClickListener.onclick(v,position);
                }
            }
        });
        holder.reviewPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(btnClickListener!=null)
                {
                    btnClickListener.onclick(v,position);
                }
            }
        });
        holder.reviewDocument.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(btnClickListener!=null)
                {
                    btnClickListener.onclick(v,position);
                }
            }
        });
        holder.changeChecker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(btnClickListener!=null)
                {
                    btnClickListener.onclick(v,position);
                }
            }
        });
        holder.reviewFail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(btnClickListener!=null)
                {
                    btnClickListener.onclick(v,position);
                }
            }
        });
        holder.reviewSuccess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(btnClickListener!=null)
                {
                    btnClickListener.onclick(v,position);
                }
            }
        });

        holder.reformTitle.setText("第一次整改");
        holder.reformItem.setText(mDatas.get(position).getTitleSerialNumber()+mDatas.get(position).getCheckTitle());
        holder.reformNeirong.setText(mDatas.get(position).getQuestionDesc());
        holder.checkPerson.setText("检查人："+mDatas.get(position).getRecordMemberName());
        holder.checkTime.setText("检查时间："+mDatas.get(position).getPubTime());
        holder.reformTime.setText("整改期限："+mDatas.get(position).getAdviceChangeTime());
    }

    class InReformViewHolder extends RecyclerView.ViewHolder{
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
        //复查
        @BindView(R.id.review_result)
        TextView reviewResult;
        @BindView(R.id.review_pic)
        TextView reviewPic;
        @BindView(R.id.review_document)
        TextView reviewDocument;
        @BindView(R.id.btn_change_checker)
        Button changeChecker;
        @BindView(R.id.btn_review_fail)
        Button reviewFail;
        @BindView(R.id.btn_review_success)
        Button reviewSuccess;
        public InReformViewHolder(View view)
        {
            super(view);
            ButterKnife.bind(this,view);
        }
    }

    public interface BtnClickListener
    {
        void onclick(View view,int postion);
    }

    public void setOnBtnClickListener(BtnClickListener listener)
    {
        this.btnClickListener=listener;
    }
}
