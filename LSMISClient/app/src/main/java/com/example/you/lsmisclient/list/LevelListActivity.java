package com.example.you.lsmisclient.list;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.example.you.lsmisclient.R;
import com.example.you.lsmisclient.adapter.LevelAdapter;
import com.example.you.lsmisclient.bean.LabLevel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LevelListActivity extends AppCompatActivity {
    @BindView(R.id.level_list_recyclerview)
    RecyclerView levelListRecyclerView;
    //适配器
    LevelAdapter levelAdapter;
    //数据
    List<LabLevel> datas;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_list);
        //绑定控件
        ButterKnife.bind(this);
        //init
        initLevel();
        //
        levelAdapter=new LevelAdapter(datas);
        levelAdapter.setLevelItemCLickListener(new LevelAdapter.LevelItemClickListener() {
            @Override
            public void onLevelItemClickListener(View view, LabLevel labLevel) {
                Intent intent=new Intent(getBaseContext(),LabListActivity.class);
                startActivity(intent);
            }
        });
        //RecyclerView
        levelListRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        levelListRecyclerView.setAdapter(levelAdapter);
    }
    private void initLevel()
    {
        datas=new ArrayList<LabLevel>();
        datas.add(new LabLevel("国家级","3"));
        datas.add(new LabLevel("省级","5"));
        datas.add(new LabLevel("校级","22"));
        datas.add(new LabLevel("院级","25"));
        datas.add(new LabLevel("其他","3"));
    }
}
