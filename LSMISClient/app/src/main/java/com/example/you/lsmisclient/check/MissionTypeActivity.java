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
import com.example.you.lsmisclient.adapter.CheckMissionTypeAdapter;
import com.example.you.lsmisclient.bean.CheckMissionType;
import com.example.you.lsmisclient.list.LabListActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MissionTypeActivity extends AppCompatActivity implements View.OnClickListener{
    @BindView(R.id.mission_type_toolbar)
    Toolbar missionTypeToolbar;
    @BindView(R.id.toolbar_textview)
    TextView toolbarTextView;
    @BindView(R.id.mission_type_recyclerview)
    RecyclerView missionTypeRecyclerView;
    //适配器
    CheckMissionTypeAdapter checkMissionTypeAdapter;
    //数据
    List<CheckMissionType> datas;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mission_type);
        //bind
        ButterKnife.bind(this);
        toolbarTextView.setText("任务类型选择");
        setSupportActionBar(missionTypeToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        missionTypeToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //init
        initdatas();
        //适配器初始化
        checkMissionTypeAdapter=new CheckMissionTypeAdapter(datas);
        checkMissionTypeAdapter.serMissionTypeItemListener(new CheckMissionTypeAdapter.MissionTypeItemClickListener() {
            @Override
            public void onMissionTypeItemClickListener(View view, CheckMissionType checkMissionType) {
                Intent intent=new Intent(getBaseContext(),SelectCheckMissionActivity.class);
                startActivity(intent);
            }
        });
        //设置布局
          missionTypeRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        missionTypeRecyclerView.setAdapter(checkMissionTypeAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.daily_mission:
                Intent intent=new Intent(this, SelectCheckMissionActivity.class);
                startActivity(intent);
                break;
        }
    }

    private void initdatas()
    {
        datas=new ArrayList<CheckMissionType>();
        datas.add(new CheckMissionType("学校检查A"));
    }
}
