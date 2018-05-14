package com.example.you.lsmisclient.rectification.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.you.lsmisclient.R;
import com.example.you.lsmisclient.http.HttpManager;
import com.example.you.lsmisclient.rectification.bean.ReviewResult;
import com.example.you.lsmisclient.utils.PicShowActivity;
import com.jaeger.ninegridimageview.ItemImageClickListener;
import com.jaeger.ninegridimageview.NineGridImageView;
import com.jaeger.ninegridimageview.NineGridImageViewAdapter;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.WeakHashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by You on 2018/4/11.
 */

public class ReviewDetailAdapter extends  RecyclerView.Adapter<ReviewDetailAdapter.ReviewDetailViewHolder>{

    List<ReviewResult> datas;

    //imgBaseUrl
    private String imgBaseUrl;
    private String imgAdaviceBaseUrl;

    private String TAG = "ReviewDetailAdapter";

    public String getImgBaseUrl() {
        return imgBaseUrl;
    }

    public void setImgBaseUrl(String imgBaseUrl) {
        this.imgBaseUrl = HttpManager.BASE_URL+imgBaseUrl;
    }

    public String getImgAdaviceBaseUrl() {
        return imgAdaviceBaseUrl;
    }

    public void setImgAdaviceBaseUrl(String imgAdaviceBaseUrl) {
        this.imgAdaviceBaseUrl = HttpManager.BASE_URL+imgAdaviceBaseUrl;
    }

    //点击监听
    private BtnClickInterface mBtnClickInterface;

    public ReviewDetailAdapter(List<ReviewResult> datas) {
        this.datas = datas;
    }

    public ReviewDetailAdapter() {
        datas = new ArrayList<>();
    }

    public List<ReviewResult> getDatas() {
        return datas;
    }

    public void setDatas(List<ReviewResult> datas) {
        this.datas = datas;
        notifyDataSetChanged();
    }

    public void setOneData(ReviewResult data){
        this.datas =  new ArrayList<>();
        datas.add(data);
        notifyDataSetChanged();
    }

    /**
     * 重置数据
     */
    public void reSetDatas(){
        this.datas = new ArrayList<>();
        notifyDataSetChanged();
    }

    @Override
    public ReviewDetailViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_review_detail,parent,false);
        ReviewDetailViewHolder holder = new ReviewDetailViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ReviewDetailViewHolder holder, final int position) {
        holder.btnChangeChecker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mBtnClickInterface!=null)
                    mBtnClickInterface.onclick(v,position);
            }
        });
        holder.btnReviewFail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mBtnClickInterface!=null)
                    mBtnClickInterface.onclick(v,position);
            }
        });
        holder.btnReviewSuccess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mBtnClickInterface!=null)
                    mBtnClickInterface.onclick(v,position);
            }
        });
        holder.reformTitle.setText("第一次整改");
        holder.reformItem.setText(datas.get(position).getAdvice().getTitleSerialNumber()+datas.get(position).getAdvice().getCheckTitle());
        holder.reformNeirong.setText(datas.get(position).getAdvice().getQuestionDesc());
        holder.checkPerson.setText("检查人："+datas.get(position).getAdvice().getRecordMemberName());
        holder.checkTime.setText("检查时间："+datas.get(position).getAdvice().getPubTime());
        holder.reformDeadline.setText("整改期限："+datas.get(position).getAdvice().getAdviceChangeTime());
        holder.reformContent.setText("整改内容："+datas.get(position).getChangeRecord().getChangeContent());
        holder.reformPerson.setText("整改人："+datas.get(position).getChangeRecord().getUploadMemberName());
        holder.reformTime.setText("整改时间："+datas.get(position).getChangeRecord().getUploadTime());

        //图片获取
        String[] checkPicFileList = datas.get(position).getAdvice().getPicFilenameList();
        String[] reformPicFileList = datas.get(position).getChangeRecord().getPicFilenameList();

        setNineGridImg(holder,checkPicFileList,reformPicFileList);




    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    /**
     * 设置九宫格图片
     * @param holder
     * @param checkPicFileList
     * @param reformPicFileList
     */
    private void setNineGridImg(ReviewDetailViewHolder holder,String[] checkPicFileList,String[] reformPicFileList){
        List<String> checkPicList = new ArrayList<>();
        List<String> reformPicList = new ArrayList<>();

        //拼接图片url
        for(String checkPicName:checkPicFileList){
            checkPicList.add(imgAdaviceBaseUrl+checkPicName);
        }
        for(String reformPicName:reformPicFileList){
            reformPicList.add(imgBaseUrl+reformPicName);
        }

        NineGridImageViewAdapter<String> nineGridImageViewAdapter = new NineGridImageViewAdapter<String>() {
            @Override
            protected void onDisplayImage(Context context, ImageView imageView, String s) {
                Log.i(TAG,s);
                Glide.with(context).load(s).placeholder(R.mipmap.background4).into(imageView);
            }
        };
        //checkPic
        holder.checkNineGridImg.setAdapter(nineGridImageViewAdapter);
        holder.checkNineGridImg.setImagesData(checkPicList);
        holder.checkNineGridImg.setItemImageClickListener(new ItemImageClickListener() {
            @Override
            public void onItemImageClick(Context context, ImageView imageView, int index, List list) {
                Intent intent = new Intent(context, PicShowActivity.class);
                intent.putExtra("url",""+list.get(index));
                context.startActivity(intent);
            }
        });

        //reformPic
        holder.reformNineGridImg.setAdapter(nineGridImageViewAdapter);
        holder.reformNineGridImg.setImagesData(reformPicList);
        holder.reformNineGridImg.setItemImageClickListener(new ItemImageClickListener() {
            @Override
            public void onItemImageClick(Context context, ImageView imageView, int index, List list) {
                Intent intent = new Intent(context, PicShowActivity.class);
                intent.putExtra("url",""+list.get(index));
                context.startActivity(intent);
            }
        });



    }

    /**
     * 自定义ViewHolder
     */
    class ReviewDetailViewHolder extends RecyclerView.ViewHolder{
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
        @BindView(R.id.reform_deadline)
        TextView reformDeadline;
        @BindView(R.id.reform_content)
        TextView reformContent;
        @BindView(R.id.reform_person)
        TextView reformPerson;
        @BindView(R.id.reform_time)
        TextView reformTime;

        //button
        @BindView(R.id.btn_change_checker)
        Button btnChangeChecker;
        @BindView(R.id.btn_review_fail)
        Button btnReviewFail;
        @BindView(R.id.btn_review_success)
        Button btnReviewSuccess;

        //PIC
        @BindView(R.id.check_nine_grid_img)
        NineGridImageView checkNineGridImg;
        @BindView(R.id.reform_nine_grid_img)
        NineGridImageView reformNineGridImg;

         public ReviewDetailViewHolder(View itemView) {
             super(itemView);
             ButterKnife.bind(this,itemView);
         }
     }

    /**
     * 点击接口
     */
    public interface BtnClickInterface{
        void onclick(View view,int postion);
     }

     public void setOnbtnClick(BtnClickInterface listener){
        this.mBtnClickInterface = listener;
     }
}
