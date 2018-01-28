package com.example.you.lsmisclient.check;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

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
    @BindView(R.id.select_mission_toolbar)
    Toolbar selectMissionToolbar;
    @BindView(R.id.toolbar_textview)
    TextView toolbarTextView;
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
        toolbarTextView.setText("检查任务选择");
        setSupportActionBar(selectMissionToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        selectMissionToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
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
        CheckMission checkMission=new CheckMission();
        checkMission.setMissionName("2017迎国庆实验室安全大检查");
        checkMission.setMissionDescription("本次检查是本年度第一次全面的安全检查，重点检查对象是国家重点实验室和危化品安全问题");
        checkMission.setMissionStartTime("10月1日");
        checkMission.setMissionEndTime("10月10日");
        checkMission.setMissionRemainingTime("还剩5天");
        checkMission.setMissionAllLabs("1273");
        checkMission.setMissionCheckedLabs("836");
        checkMission.setMissionRemainingLabs("837");
        checkMission.setAlreadlyCheckedCount("1665");
        checkMission.setInconformityCount("392");
        checkMission.setAlreadlyReformCount("329");
        checkMission.setInReformCount("183");
        datas.add(checkMission);
        datas.add(checkMission);
        datas.add(checkMission);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
//            case R.id.daily_mission:
//                Intent intent=new Intent(getBaseContext(), LabListActivity.class);
//                startActivity(intent);
//                break;
        }
    }
}
