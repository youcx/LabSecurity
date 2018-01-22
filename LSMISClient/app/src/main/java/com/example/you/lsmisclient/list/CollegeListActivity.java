package com.example.you.lsmisclient.list;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.you.lsmisclient.R;
import com.example.you.lsmisclient.adapter.CollegeAdapter;
import com.example.you.lsmisclient.bean.College;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CollegeListActivity extends AppCompatActivity {
    @BindView(R.id.college_list_recyclerview)
    RecyclerView collegeListRecyclerView;
    //适配器
    CollegeAdapter collegeAdapter;
    //数据
    List<College> mDatas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_college_list);
        //绑定控件
        ButterKnife.bind(this);
        //init
        initCollege();
        //适配器初始化
        collegeAdapter = new CollegeAdapter(mDatas);
        collegeAdapter.setCollegeItemClickListener(new CollegeAdapter.CollegeItemClickListener() {
            @Override
            public void onCollegeItemClick(View view, College college) {
                Intent intent=new Intent(getBaseContext(),LabListActivity.class);
                startActivity(intent);
            }
        });
        //设置布局
        collegeListRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        //设置适配器
        collegeListRecyclerView.setAdapter(collegeAdapter);
    }

    private void initCollege() {
        mDatas = new ArrayList<College>();
        mDatas.add(new College("信息工程学院", "6", "老师1"));
        mDatas.add(new College("生命学院", "5", "老师2"));
        mDatas.add(new College("材料学院", "10", "老师3"));
    }






}
