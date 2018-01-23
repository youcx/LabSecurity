package com.example.you.lsmisclient.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.you.lsmisclient.adapter.CardAdapter;
import com.example.you.lsmisclient.R;
import com.example.you.lsmisclient.bean.MyCard;
import com.example.you.lsmisclient.check.SecurityCheckActivity;
import com.example.you.lsmisclient.check.SelectCheckMissionActivity;
import com.example.you.lsmisclient.contacts.ContactsActivity;
import com.example.you.lsmisclient.lab.LabActivity;

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
                    Intent intent=new Intent(getActivity(), SelectCheckMissionActivity.class);
                    startActivity(intent);
                }else if(myCard.getCard_name().equals("责任人"))
                {
                    Intent intent =new Intent(getActivity(), ContactsActivity.class);
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
        home_recycview.setLayoutManager(new LinearLayoutManager(getActivity()));
        //设置适配器
        home_recycview.setAdapter(mCardAdapter);
        return view;
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
