package com.example.you.lsmisclient.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.you.lsmisclient.adapter.CardAdapter;
import com.example.you.lsmisclient.R;
import com.example.you.lsmisclient.banner.GlideImageLoader;
import com.example.you.lsmisclient.bean.MyCard;
import com.example.you.lsmisclient.check.SecurityCheckActivity;
import com.example.you.lsmisclient.check.SelectCheckMissionActivity;
import com.example.you.lsmisclient.contacts.ContactsActivity;
import com.example.you.lsmisclient.contacts.UserActivity;
import com.example.you.lsmisclient.lab.LabActivity;
import com.example.you.lsmisclient.lab.LabOverViewActivity;
import com.example.you.lsmisclient.rectification.ReformDetailActivity;
import com.youth.banner.Banner;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
    //绑定控件
    @BindView(R.id.home_recycview)
    RecyclerView home_recycview;
    @BindView(R.id.banner)
    Banner banner;
    //recyclerView适配器
    CardAdapter mCardAdapter;
    //泛型数据
    List<MyCard> mDatas;



    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //初始化
        initData();
        //
        mCardAdapter=new CardAdapter(mDatas);
        //item点击事件
        mCardAdapter.setCardItemClickListener(new CardAdapter.CardItemClickListener() {
            @Override
            public void onCardItemClick(View view, MyCard myCard) {
                if(myCard.getCard_name().equals("实验室"))
                {
                    Intent intent=new Intent(getActivity(), LabActivity.class);
                    startActivity(intent);
                }else if(myCard.getCard_name().equals("安全检查"))
                {
                    Intent intent=new Intent(getActivity(), SecurityCheckActivity.class);
                    startActivity(intent);
                }else if(myCard.getCard_name().equals("责任人"))
                {
                    Intent intent =new Intent(getActivity(), UserActivity.class);
                    startActivity(intent);
                }else if(myCard.getCard_name().equals("安全总览"))
                {
                    Intent intent =new Intent(getActivity(), LabOverViewActivity.class);
                    startActivity(intent);
                }
            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_home, container, false);
        //绑定控件
        ButterKnife.bind(this,view);
        //设置recyclerview布局管理
        home_recycview.setLayoutManager(new GridLayoutManager(getActivity(),2));
        //设置适配器
        home_recycview.setAdapter(mCardAdapter);

        initMyBanner();

        return view;
    }

    /**
     * 设置轮播图
     */
    private void initMyBanner(){
        ArrayList<String> imagePath = new ArrayList<>();
       imagePath.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1523186938057&di=59d4d5a0cc2f1ae1472329f5fa2d0b53&imgtype=0&src=http%3A%2F%2Fwww.btobc.net%2Fimg%2Fservice-bn.jpg");
       imagePath.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1523187347817&di=72f86cd52851351a442f64363e839074&imgtype=0&src=http%3A%2F%2Fpic.qiantucdn.com%2F58pic%2F19%2F69%2F21%2F35S58PICIvJ_1024.jpg");
       imagePath.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1523187347816&di=408e26649ef6c1e9728667ee22ab1db3&imgtype=0&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F01edb3555ea8100000009af0ba36f5.jpg%401280w_1l_2o_100sh.jpg");
        //imagePath.add("http://119.29.201.35/files/labChangeFile/1523692303219d252.jpg");
       banner.setImageLoader(new GlideImageLoader());
        banner.setImages(imagePath);
        banner.start();

    }

    private void initData()
    {
        //数据初始化
        mDatas=new ArrayList<MyCard>();
        mDatas.add(new MyCard("实验室",R.mipmap.lab_128px));
        mDatas.add(new MyCard("责任人",R.mipmap.contacts_128px));
        mDatas.add(new MyCard("安全总览",R.mipmap.security_72px));
        mDatas.add(new MyCard("安全检查",R.mipmap.check_72px));
    }

}
