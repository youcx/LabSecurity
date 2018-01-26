package com.example.you.lsmisclient.check;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.you.lsmisclient.R;
import com.example.you.lsmisclient.adapter.CheckMissionAdapter;
import com.example.you.lsmisclient.bean.CheckMission;
import com.example.you.lsmisclient.list.LabListActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SelectCheckMissionActivity extends AppCompatActivity implements View.OnClickListener{

    @BindView(R.id.check_mission_recyclerview)
    RecyclerView checkMissionRecyclerView;
    //适配器
    CheckMissionAdapter missionAdapter;
    //数据
    List<CheckMission> datas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_check_mission);
        //bind
        ButterKnife.bind(this);
        //init
        inidatas();
        //适配器初始化
        missionAdapter=new CheckMissionAdapter(datas);
        missionAdapter.setCheckMissionItemListener(new CheckMissionAdapter.CheckMissionItemClickListener() {
            @Override
            public void onCheckMissionItemClickListener(View view, CheckMission checkMission) {
                Intent intent=new Intent(getBaseContext(), LabListActivity.class);
                startActivity(intent);
            }
        });
        //设置布局
        checkMissionRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        checkMissionRecyclerView.setAdapter(missionAdapter);
    }
    private void inidatas()
    {
        datas=new ArrayList<>();
        datas.add(new CheckMission("检查任务A"));
        datas.add(new CheckMission("检查任务B"));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.daily_mission:
                Intent intent=new Intent(getBaseContext(), LabListActivity.class);
                startActivity(intent);
                break;
        }
    }
}
