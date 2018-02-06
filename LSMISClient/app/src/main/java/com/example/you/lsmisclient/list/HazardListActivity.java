package com.example.you.lsmisclient.list;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.you.lsmisclient.R;
import com.example.you.lsmisclient.adapter.HazardAdapter;
import com.example.you.lsmisclient.bean.LabHazard;
import com.example.you.lsmisclient.bean.Result;
import com.example.you.lsmisclient.http.HttpTask;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscriber;

public class HazardListActivity extends AppCompatActivity {
    @BindView(R.id.hazard_list_recyclerview)
    RecyclerView hazardListRecyclerView;
    @BindView(R.id.hazard_list_toolbar)
    Toolbar hazardListToolbar;
    @BindView(R.id.toolbar_textview)
    TextView toolbarTextView;
    //适配器
    HazardAdapter hazardAdapter;
    //数据
    List<LabHazard> datas;
    //http
    HttpTask mTask;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hazard_list);
        //绑定控件
        ButterKnife.bind(this);
        mTask=new HttpTask();
        toolbarTextView.setText("危险源分览");
        setSupportActionBar(hazardListToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        hazardListToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //init
        startGetHazardList();

    }

    private void startGetHazardList()
    {
        mTask.getMainDanger()
                .subscribe(new Subscriber<Result>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Result result) {
                        if(result!=null)
                        {
                            if(result.getStatus()==200)
                            {
                                initHazard(result.getData().toString());
                            }
                        }
                    }
                });
    }
    private void initHazard(String s)
    {
        try{
            JSONArray jsonArray=new JSONArray(s);
            datas=new ArrayList<>();
            for(int i=0;i<jsonArray.length();i++)
            {
                JSONObject jsonObject=(JSONObject) jsonArray.get(i);
                datas.add(new LabHazard(jsonObject.getString("dangerMainTypeName"),
                        jsonObject.getString("labNumb"),
                        jsonObject.getInt("dangerMainTypeId")));
            }
            //
            hazardAdapter=new HazardAdapter(datas);
            hazardAdapter.setHazardItemClickListener(new HazardAdapter.HazardItemClickListener() {
                @Override
                public void onHazardItemClick(View view, LabHazard labHazard) {
                    Intent intent=new Intent(getBaseContext(),LabListActivity.class);
                    Bundle bd=new Bundle();
                    bd.putInt("dangerMainTypeId",labHazard.getDangerMainTypeId());
                    bd.putString("task","fromHazard");
                    intent.putExtras(bd);
                    startActivity(intent);
                }
            });
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    //RecyclerView
                    hazardListRecyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext()));
                    hazardListRecyclerView.setAdapter(hazardAdapter);
                }
            });
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
