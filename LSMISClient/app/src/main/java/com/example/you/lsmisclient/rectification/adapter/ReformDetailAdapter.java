package com.example.you.lsmisclient.rectification.adapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.you.lsmisclient.R;
import com.example.you.lsmisclient.http.HttpManager;
import com.example.you.lsmisclient.rectification.bean.CheckRecordDetail;
import com.example.you.lsmisclient.utils.PicShowActivity;
import com.jaeger.ninegridimageview.ItemImageClickListener;
import com.jaeger.ninegridimageview.NineGridImageView;
import com.jaeger.ninegridimageview.NineGridImageViewAdapter;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.http.Url;

/**
 * Created by You on 2018/3/4.
 */

public class ReformDetailAdapter  extends RecyclerView.Adapter<ReformDetailAdapter.ReformDetailViewHolder>{

    private final String TAG = "ReFormDetailAdapter";

    List<CheckRecordDetail> mDatas;

    //点击监听
    private BtnClickInterface mBtnClickInterface;

    private Context context;

    private NineGridImageViewAdapter<String> nineImgAdapter;

    //ViewGroup用于图片对话框父布局,在onCreateViewHolder里给他赋值
    ViewGroup parent = null;

    //imageBaseUrl
    private String imageBaseUrl;

    public String getImageBaseUrl() {
        return imageBaseUrl;
    }

    public void setImageBaseUrl(String imageBaseUrl) {
        this.imageBaseUrl = HttpManager.BASE_URL+imageBaseUrl;
    }

    /**
     * 构造函数
     * @param mDatas
     */
    public ReformDetailAdapter(List<CheckRecordDetail> mDatas,Context context) {
        this.mDatas = mDatas;
        this.context = context;
    }

    /**
     * 无参构造
     */
    public ReformDetailAdapter(Context context) {
        mDatas=new ArrayList<>();
        this.context = context;
    }

    public List<CheckRecordDetail> getmDatas() {
        return mDatas;
    }

    public void setmDatas(List<CheckRecordDetail> mDatas) {
        this.mDatas = mDatas;
        notifyDataSetChanged();
    }

    @Override
    public ReformDetailViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        this.parent = parent;

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

        //图片获取
        String[] picFileList = mDatas.get(position).getPicFilenameList();

        //拼接成图片url，存入List
        List<String> picLists= new ArrayList<String>();
        for(String picFileName:picFileList){
            picLists.add(imageBaseUrl+picFileName);
        }

        nineImgAdapter = new NineGridImageViewAdapter<String>() {
            @Override
            protected void onDisplayImage(Context context, ImageView imageView, String s) {
                Glide.with(context).load(s).placeholder(R.mipmap.background4).into(imageView);
            }


        };

        holder.nineGridImageView.setAdapter(nineImgAdapter);
        holder.nineGridImageView.setImagesData(picLists);
        holder.nineGridImageView.setItemImageClickListener(new ItemImageClickListener() {
            @Override
            public void onItemImageClick(Context context, ImageView imageView, int index, List list) {
                Log.i(TAG,""+list.get(index));
                //showPicDialog(""+list.get(index));
                Intent intent = new Intent(context, PicShowActivity.class);
                intent.putExtra("url",""+list.get(index));
                context.startActivity(intent);

            }
        });






    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    private void showPicDialog(String url){
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_show_pic,parent,false);
        ImageView img = (ImageView) view.findViewById(R.id.pic_of_dialog);
        Glide.with(context).load(url).placeholder(R.mipmap.background4).into(img);


//
        final AlertDialog dialog = new AlertDialog.Builder(context,R.style.Theme_AppCompat).setView(view).create();
        dialog.show();

        //Window

        //dialog.getWindow().setAttributes(new WindowManager.LayoutParams());

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

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

        //LinearLayout

        @BindView(R.id.big_pic)
        ImageView bigPic;
        @BindView(R.id.nine_grid_img)
        NineGridImageView nineGridImageView;


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
