package com.example.you.lsmisclient.list;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.example.you.lsmisclient.R;
import com.example.you.lsmisclient.adapter.HazardAdapter;
import com.example.you.lsmisclient.bean.LabHazard;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HazardListActivity extends AppCompatActivity {
    @BindView(R.id.hazard_list_recyclerview)
    RecyclerView hazardListRecyclerView;
    //适配器
    HazardAdapter hazardAdapter;
    //数据
    List<LabHazard> datas;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hazard_list); //绑定控件
        ButterKnife.bind(this);
        //init
        initHazard();
        //
        hazardAdapter=new HazardAdapter(datas);
        hazardAdapter.setHazardItemClickListener(new HazardAdapter.HazardItemClickListener() {
            @Override
            public void onHazardItemClick(View view, LabHazard labHazard) {
                Intent intent=new Intent(getBaseContext(),LabListActivity.class);
                startActivity(intent);
            }
        });
        //RecyclerView
        hazardListRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        hazardListRecyclerView.setAdapter(hazardAdapter);
    }
    private void initHazard()
    {
        datas=new ArrayList<LabHazard>();
        datas.add(new LabHazard("易燃易爆物","2"));
        datas.add(new LabHazard("放射性物质","23"));
        datas.add(new LabHazard("生物类物品","1"));
        datas.add(new LabHazard("特种设备","3"));
        datas.add(new LabHazard("压缩气体","5"));
        datas.add(new LabHazard("有毒物","6"));
        datas.add(new LabHazard("腐蚀品","5"));
    }
}
